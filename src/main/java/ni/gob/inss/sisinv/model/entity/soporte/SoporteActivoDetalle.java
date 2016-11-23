package ni.gob.inss.sisinv.model.entity.soporte;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Entity
@Table(name="soporte_activo_detalle", schema="soportetecnico")
@SequenceGenerator(name="SoporteActivoDetalle_SEQ", sequenceName="soportetecnico.soporte_activo_detalle_id_seq", allocationSize=1)
public class SoporteActivoDetalle extends EntityBase  implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private SoporteActivo soporteActivoId;
	private Integer tipoMovimientoId;
	private Date fechaMovimiento;
	private Date horaMovimiento;
	private Empleado empleadoResponsable;
	private String observaciones;
	private boolean pasivo;
	
    @Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SoporteActivoDetalle_SEQ")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
		
	@Column(name="soporte_activo_id", nullable=true, updatable=false)
	public SoporteActivo getSoporteActivoId() {
		return soporteActivoId;
	}
	public void setSoporteActivoId(SoporteActivo soporteActivoId) {
		this.soporteActivoId = soporteActivoId;
	}
	
	@Column(name="tipo_movimiento_id", nullable=false)
	public Integer getTipoMovimientoId() {
		return tipoMovimientoId;
	}
	public void setTipoMovimientoId(Integer tipoMovimientoId) {
		this.tipoMovimientoId = tipoMovimientoId;
	}
	
	@Column(name="fecha_movimiento", nullable=false)
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
	@Column(name="hora_movimiento", nullable=false)
	public Date getHoraMovimiento() {
		return horaMovimiento;
	}
	public void setHoraMovimiento(Date horaMovimiento) {
		this.horaMovimiento = horaMovimiento;
	}
	
	@Column(name="empleado_responsable", nullable=false)
	public Empleado getEmpleadoResponsable() {
		return empleadoResponsable;
	}
	public void setEmpleadoResponsable(Empleado empleadoResponsable) {
		this.empleadoResponsable = empleadoResponsable;
	}
	
	@Column(name="observaciones", nullable=false)
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
