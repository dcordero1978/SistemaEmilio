package ni.gob.inss.sisinv.model.entity.soporte;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

@Entity
@Table(name="historial_estado_equipos", schema="soportetecnico")
@SequenceGenerator(name="HistorialEstadoEquipos_SEQ", sequenceName="soportetecnico.historial_estado_equipos_id_seq", allocationSize=1)
public class HistorialEstadoEquipos extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Activos equipoId;
	private String  codigoEstado;
	private Date 	fechaMovimiento;
	private String	observaciones;
	private boolean	pasivo;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HistorialEstadoEquipos_SEQ")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="equipo_id", nullable=false)
	public Activos getEquipoId() {
		return equipoId;
	}
	public void setEquipoId(Activos equipoId) {
		this.equipoId = equipoId;
	}
	
	@Column(name="codigo_estado",nullable=false, length=20)
	public String getCodigoEstado() {
		return codigoEstado;
	}
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_movimiento", nullable=false, length=29)
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
	@Column(name="observaciones", length=300)
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	@Column(name="pasivo", nullable=false)
	public boolean isPasivo() {
		return pasivo;
	}
	public void setPasivo(boolean pasivo) {
		this.pasivo = pasivo;
	}
	
	

}
