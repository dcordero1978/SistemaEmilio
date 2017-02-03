package ni.gob.inss.sisinv.model.entity.soporte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;

@Entity
@Table(name="flujo_estado_mantenimiento", schema="soportetecnico")
@SequenceGenerator(name="FlujoEstadoMantenimiento_SEQ", sequenceName="soportetecnico.flujo_estado_mantenimiento_id_seq", allocationSize=1)
public class FlujoMantenimiento extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer  id;
	private Catalogo codigoEstadoInicial;
	private Catalogo codigoEstadoFinal;
	private Catalogo tipoMantenimientoId;
	private Boolean  pasivo;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FlujoEstadoMantenimiento_SEQ")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Column(name="codigo_estado_inicial", nullable=false, length=2)
	public Catalogo getCodigoEstadoInicial() {
		return codigoEstadoInicial;
	}
	public void setCodigoEstadoInicial(Catalogo codigoEstadoInicial) {
		this.codigoEstadoInicial = codigoEstadoInicial;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Column(name="codigo_estado_final", nullable=false, length=2)
	public Catalogo getCodigoEstadoFinal() {
		return codigoEstadoFinal;
	}
	public void setCodigoEstadoFinal(Catalogo codigoEstadoFinal) {
		this.codigoEstadoFinal = codigoEstadoFinal;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Column(name="tipo_matenimiento_id", nullable=false)
	public Catalogo getTipoMantenimientoId() {
		return tipoMantenimientoId;
	}
	public void setTipoMantenimientoId(Catalogo tipoMantenimientoId) {
		this.tipoMantenimientoId = tipoMantenimientoId;
	}
	
	@Column(name="pasivo", nullable=false)
	public Boolean getPasivo() {
		return pasivo;
	}
	public void setPasivo(Boolean pasivo) {
		this.pasivo = pasivo;
	}

	
}
