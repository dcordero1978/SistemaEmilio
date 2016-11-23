package ni.gob.inss.sisinv.model.entity.soporte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

@Entity
@Table(name="soporte_activo",schema="soportetecnico")
@SequenceGenerator(name="SoporteActivo_SEQ", sequenceName="soportetecnico.soporte_activo_id_seq", allocationSize=1)
public class SoporteActivo extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Empleado empleadoId;
	private Activos activoId;
	private Integer estadoEquipo;
	private boolean pasivo;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SoporteActivo_SEQ")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="empleado_id", nullable=false, updatable=false)
	public Empleado getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(Empleado empleadoId) {
		this.empleadoId = empleadoId;
	}
	
	@Column(name="activo_id", nullable=false, updatable=false)
	public Activos getActivoId() {
		return activoId;
	}
	public void setActivoId(Activos activoId) {
		this.activoId = activoId;
	}
	
	@Column(name="estado_equipo", nullable=false)
	public Integer getEstadoEquipo() {
		return estadoEquipo;
	}
	public void setEstadoEquipo(Integer estadoEquipo) {
		this.estadoEquipo = estadoEquipo;
	}
	
	@Column(name="pasivo", nullable=false)
	public boolean isPasivo() {
		return pasivo;
	}
	public void setPasivo(boolean pasivo) {
		this.pasivo = pasivo;
	}
	
	
	

}
