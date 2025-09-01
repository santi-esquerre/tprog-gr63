package dominio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Otorga extends BaseEntity {
	@Column(nullable = false)
	private int cantidad;
	protected Otorga() {}
	
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_registro_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_ortoga_tiporegistro"))
	private TipoRegistro tipoRegistro; // Asociación con TipoRegistro
	
	public Otorga(int cant, TipoRegistro tr) {
		this.cantidad = cant;
		this.tipoRegistro = tr;
	}
	public int getCantidad() { return cantidad; }
	
}
