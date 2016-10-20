package ni.gob.inss.sisinv.model.entity.inventario;

import java.io.Serializable;
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
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Entity
@Table(name="movimientos_bienes", schema="inventario")
@SequenceGenerator(name="Movimientos_SEQ", sequenceName="inventario.movimientos_bienes_id_seq", allocationSize=1)
public class Movimientos extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer tipo_movimiento_id;
	private Empleado empleado_id_origen;
	private Empleado empleado_id_destino;
	private Integer activo_id;
	private Date 	fecha_movimiento;
	private String 	observaciones;
	private boolean pasivo;
	private Integer entidadId;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Movimientos_SEQ")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="tipo_movimiento_id", nullable=false)
	public Integer getTipo_movimiento_id() {
		return tipo_movimiento_id;
	}
	public void setTipo_movimiento_id(Integer tipo_movimiento_id) {
		this.tipo_movimiento_id = tipo_movimiento_id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empleado_id_origen", nullable=false, insertable=false,updatable=false)
	public Empleado getEmpleado_id_origen() {
		return empleado_id_origen;
	}
	public void setEmpleado_id_origen(Empleado empleado_id_origen) {
		this.empleado_id_origen = empleado_id_origen;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empleado_id_destino", nullable=false, insertable=false,updatable=false)
	public Empleado getEmpleado_id_destino() {
		return empleado_id_destino;
	}
	public void setEmpleado_id_destino(Empleado empleado_id_destino) {
		this.empleado_id_destino = empleado_id_destino;
	}
	
	@Column(name="activo_id", nullable=false, insertable=false,updatable=false)
	public Integer getActivo_id() {
		return activo_id;
	}
	public void setActivo_id(Integer activo_id) {
		this.activo_id = activo_id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_movimiento", nullable=false, length = 29)
	public Date getFecha_movimiento() {
		return fecha_movimiento;
	}
	public void setFecha_movimiento(Date fecha_movimiento) {
		this.fecha_movimiento = fecha_movimiento;
	}
	
	@Column(name = "observaciones", length = 300)
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	@Column(name = "pasivo", nullable = false)
	public boolean isPasivo() {
		return pasivo;
	}
	public void setPasivo(boolean pasivo) {
		this.pasivo = pasivo;
	}
	
	@Column(name="entidad_id", nullable=false)
	public Integer getEntidadId() {
		return entidadId;
	}
	public void setEntidadId(Integer entidadId) {
		this.entidadId = entidadId;
	}
	

	
}
