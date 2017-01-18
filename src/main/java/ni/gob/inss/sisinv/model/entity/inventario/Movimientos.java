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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Entity
@Table(name="movimientos_bienes", schema="inventario")
@SequenceGenerator(name="Movimientos_SEQ", sequenceName="inventario.movimientos_bienes_id_seq", allocationSize=1)
public class Movimientos extends EntityBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer tipoMovimientoId;
	private Empleado empleadoIdOrigen;
	private Empleado empleadoIdDestino;
	private Activos activoId;
	private Date 	fechaMovimiento;
	private String 	observaciones;
	private boolean pasivo;
	private Integer entidadId;
	private Integer activosId;
	public Catalogo tipoMovimiento;
	public Empleado idEmpleadoOrigen;
	public Empleado idEmpleadoDestino;
	
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
	public Integer getTipoMovimientoId() {
		return tipoMovimientoId;
	}
	public void setTipoMovimientoId(Integer tipoMovimientoId) {
		this.tipoMovimientoId = tipoMovimientoId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empleado_id_origen", nullable=false)
	public Empleado getEmpleadoIdOrigen() {
		return empleadoIdOrigen;
	}
	public void setEmpleadoIdOrigen(Empleado empleadoIdOrigen) {
		this.empleadoIdOrigen = empleadoIdOrigen;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empleado_id_destino", nullable=false)
	public Empleado getEmpleadoIdDestino() {
		return empleadoIdDestino;
	}
	public void setEmpleadoIdDestino(Empleado empleadoIdDestino) {
		this.empleadoIdDestino = empleadoIdDestino;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="activo_id", nullable=false)
	public Activos getActivoId() {
		return activoId;
	}
	public void setActivoId(Activos activoId) {
		this.activoId = activoId;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_movimiento", nullable=false, length = 29)
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
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

	@Column(name="activo_id", nullable=false,insertable=false, updatable=false)
	public Integer getActivosId() {
		return activosId;
	}
	public void setActivosId(Integer activosId) {
		this.activosId = activosId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="tipo_movimiento_id", insertable=false, updatable=false)
	public Catalogo getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(Catalogo tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="empleado_id_origen", insertable=false, updatable=false)
	public Empleado getIdEmpleadoOrigen() {
		return idEmpleadoOrigen;
	}
	public void setIdEmpleadoOrigen(Empleado idEmpleadoOrigen) {
		this.idEmpleadoOrigen = idEmpleadoOrigen;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="empleado_id_destino", insertable=false, updatable=false)
	public Empleado getIdEmpleadoDestino() {
		return idEmpleadoDestino;
	}
	public void setIdEmpleadoDestino(Empleado idEmpleadoDestino) {
		this.idEmpleadoDestino = idEmpleadoDestino;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37)
				.append(activoId)
				.append(activosId)
				.append(empleadoIdDestino)
				.append(empleadoIdOrigen)
				.append(entidadId)
				.append(fechaMovimiento)
				.append(id)
				.append(idEmpleadoDestino)
				.append(idEmpleadoOrigen)
				.append(observaciones)
				.append(pasivo)
				.append(tipoMovimiento)
				.append(tipoMovimientoId)
				.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Movimientos)) {
			return false;
		}
		Movimientos oMovimiento = (Movimientos) obj;
		
		return new EqualsBuilder()
					.append(activoId, oMovimiento.activoId)
					.append(activosId, oMovimiento.activosId)
					.append(empleadoIdDestino, oMovimiento.empleadoIdDestino)
					.append(empleadoIdOrigen, oMovimiento.empleadoIdOrigen)
					.append(entidadId, oMovimiento.entidadId)
					.append(fechaMovimiento, oMovimiento.fechaMovimiento)
					.append(id,oMovimiento.id)
					.append(idEmpleadoDestino, oMovimiento.idEmpleadoDestino)
					.append(idEmpleadoOrigen,oMovimiento.idEmpleadoOrigen )
					.append(observaciones, oMovimiento.observaciones)
					.append(pasivo, oMovimiento.pasivo)
					.append(tipoMovimiento, oMovimiento.tipoMovimiento)
					.append(tipoMovimientoId, oMovimiento.tipoMovimientoId).isEquals();				
	}
	
}
