package dominio;

import datatypes.DTRegistrosOtorgados;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "registro_otorgado")
public class RegistroOtorgado extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_registro_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_regotorgado_tiporegistro"))
    private TipoRegistro tipoRegistro;

    @Column(nullable = false)
    private int cantidad;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patrocinio_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_regotorgado_patrocinio"))
    private Patrocinio patrocinio;

    protected RegistroOtorgado() {}

    public RegistroOtorgado(TipoRegistro tipoRegistro, int cantidad) {
        this.tipoRegistro = tipoRegistro;
        this.cantidad = cantidad;
    }

    // Getters
    public TipoRegistro getTipoRegistro() { return tipoRegistro; }
    public int getCantidad() { return cantidad; }
    public Patrocinio getPatrocinio() { return patrocinio; }

    // Setters
    public void setPatrocinio(Patrocinio patrocinio) {
        this.patrocinio = patrocinio;
    }

    // DTO conversion
    public DTRegistrosOtorgados toDTRegistrosOtorgados() {
        return new DTRegistrosOtorgados(tipoRegistro.obtenerDTTipoRegistro(), cantidad);
    }
}
