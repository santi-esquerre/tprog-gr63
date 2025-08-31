#!/usr/bin/env bash
set -euo pipefail

# ----- Parámetros (podés exportarlos antes de correr el script) -----
PG_VERSION="${PG_VERSION:-16}"
CONTAINER_NAME="${CONTAINER_NAME:-tpgr63-pg}"
HOST_PORT="${PG_PORT:-5432}"
DB_NAME="${DB_NAME:-tpgr63eventosuy}"
DB_USER="${DB_USER:-tpgr63eventosuyadmin}"
DB_PASS="${DB_PASS:-admin}"
POSTGRES_SUPERPASS="${POSTGRES_SUPERPASS:-postgres}"   # password del superusuario postgres
DATA_DIR="${DATA_DIR:-$HOME/postgres-data/pg${PG_VERSION}}"

# ----- Helpers -----
in_path() { command -v "$1" >/dev/null 2>&1; }

port_busy() {
  # true si el puerto está ocupado
  ss -ltn 2>/dev/null | awk '{print $4}' | grep -q ":$1$" || nc -z 127.0.0.1 "$1" >/dev/null 2>&1
}

wait_healthy() {
  echo -n "Esperando a que PostgreSQL esté 'healthy'"
  for _ in $(seq 1 60); do
    status="$(docker inspect --format='{{.State.Health.Status}}' "$CONTAINER_NAME" 2>/dev/null || echo starting)"
    if [[ "$status" == "healthy" ]]; then echo " ✓"; return 0; fi
    echo -n "."
    sleep 2
  done
  echo
  echo "ERROR: el contenedor no quedó healthy a tiempo."
  docker logs "$CONTAINER_NAME" || true
  exit 1
}

psql_super() {
  sudo docker exec -i "$CONTAINER_NAME" psql -v ON_ERROR_STOP=1 -U postgres -d postgres "$@"
}

# ----- 1) Instalar Docker si falta (Debian/Ubuntu) -----
if ! in_path docker; then
  echo "[+] Instalando Docker CE..."
  sudo apt-get update -y
  sudo apt-get install -y ca-certificates curl gnupg lsb-release
  sudo install -m 0755 -d /etc/apt/keyrings
  curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
  echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian $(. /etc/os-release; echo "$VERSION_CODENAME") stable" \
    | sudo tee /etc/apt/sources.list.d/docker.list >/dev/null
  sudo apt-get update -y
  sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
fi

# ----- 2) Asegurar Docker corriendo -----
if ! sudo docker info >/dev/null 2>&1; then
  if command -v systemctl >/dev/null 2>&1; then
    sudo systemctl enable --now docker
  else
    sudo service docker start
  fi
fi

# ----- 3) Puerto libre / directorio de datos -----
if port_busy "$HOST_PORT"; then
  echo "[!] Puerto $HOST_PORT ocupado, usando 5433."
  HOST_PORT=5433
fi
mkdir -p "$DATA_DIR"

# ----- 4) Pull + crear/arrancar contenedor -----
echo "[+] Pull postgres:$PG_VERSION"
sudo docker pull "postgres:$PG_VERSION"

if docker ps -a --format '{{.Names}}' | grep -qx "$CONTAINER_NAME"; then
  echo "[=] Contenedor ya existe: $CONTAINER_NAME"
  sudo docker start "$CONTAINER_NAME" >/dev/null || true
else
  echo "[+] Creando contenedor $CONTAINER_NAME (puerto host $HOST_PORT → 5432, datos en $DATA_DIR)"
  sudo docker run -d --name "$CONTAINER_NAME" \
    -e POSTGRES_PASSWORD="$POSTGRES_SUPERPASS" \
    -p "$HOST_PORT:5432" \
    -v "$DATA_DIR:/var/lib/postgresql/data" \
    --health-cmd="pg_isready -U postgres || exit 1" \
    --health-interval=10s --health-timeout=5s --health-retries=5 \
    --restart unless-stopped \
    "postgres:$PG_VERSION" >/dev/null
fi

wait_healthy

# ----- 5) Crear/actualizar usuario y base (idempotente, sin DO $$ ... $$) -----
echo "[+] Creando usuario y base si no existen..."

# ROLE
ROLE_EXISTS="$(psql_super -Atc "SELECT 1 FROM pg_roles WHERE rolname='${DB_USER}'" || true)"
if [[ "$ROLE_EXISTS" != "1" ]]; then
  psql_super -c "CREATE ROLE ${DB_USER} LOGIN PASSWORD '${DB_PASS}';"
else
  # asegurar login y password (por si cambian)
  psql_super -c "ALTER ROLE ${DB_USER} WITH LOGIN PASSWORD '${DB_PASS}';"
fi

# DATABASE
DB_EXISTS="$(psql_super -Atc "SELECT 1 FROM pg_database WHERE datname='${DB_NAME}'" || true)"
if [[ "$DB_EXISTS" != "1" ]]; then
  psql_super -c "CREATE DATABASE ${DB_NAME} OWNER ${DB_USER};"
fi

# ----- 6) Smoke rápido -----
sudo docker exec -i "$CONTAINER_NAME" psql -U "$DB_USER" -d "$DB_NAME" -c "SELECT current_database() AS db, current_user AS usr, version() AS version;" | sed 's/^/    /'

# ----- 7) Salida -----
echo
echo "PostgreSQL listo."
echo "  Contenedor : $CONTAINER_NAME"
echo "  Puerto     : $HOST_PORT"
echo "  DB         : $DB_NAME"
echo "  Usuario    : $DB_USER"
echo "  JDBC       : jdbc:postgresql://localhost:$HOST_PORT/$DB_NAME"
echo
echo "Tips:"
echo "  docker logs -f $CONTAINER_NAME"
echo "  docker exec -it $CONTAINER_NAME psql -U $DB_USER -d $DB_NAME"
