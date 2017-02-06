package ni.gob.inss.sisinv.model.entity.soporte;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;

@Entity
@Table(name="flujo_estado_mantenimiento", schema="soportetecnico")
@SequenceGenerator(name="FlujoEstadoMantenimiento_SEQ", sequenceName="soportetecnico.flujo_estado_mantenimiento_id_seq", allocationSize=1)
public class FlujoMantenimiento extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer  id;
	private String codigoEstadoInicial;
	private String codigoEstadoFinal;
	private Integer tipoMantenimientoId;
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
	
	@Column(name="codigo_estado_inicial", nullable=false, length=2)
	public String getCodigoEstadoInicial() {
		return codigoEstadoInicial;
	}
	public void setCodigoEstadoInicial(String codigoEstadoInicial) {
		this.codigoEstadoInicial = codigoEstadoInicial;
	}
	
	@Column(name="codigo_estado_final", nullable=false, length=2)
	public String getCodigoEstadoFinal() {
		return codigoEstadoFinal;
	}
	public void setCodigoEstadoFinal(String codigoEstadoFinal) {
		this.codigoEstadoFinal = codigoEstadoFinal;
	}
	
	@Column(name="tipo_matenimiento_id", nullable=false)
	public Integer getTipoMantenimientoId() {
		return tipoMantenimientoId;
	}
	public void setTipoMantenimientoId(Integer tipoMantenimientoId) {
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
