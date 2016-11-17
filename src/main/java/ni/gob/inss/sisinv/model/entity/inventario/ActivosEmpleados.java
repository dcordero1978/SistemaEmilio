package ni.gob.inss.sisinv.model.entity.inventario;
// Generated 09-20-2016 12:00:41 PM by Hibernate Tools 4.3.4.Final


import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;

/**
 * ActivosEmpleados generated by hbm2java
 */
@Entity
@Table(name="activos_empleados", schema="inventario")
@SequenceGenerator(name="ActivosEmpleados_SEQ", sequenceName="inventario.activos_empleados_id_seq", allocationSize=1)
public class ActivosEmpleados  extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activo_id;
	private Integer empleado_id;
    private boolean pasivo;
    private Date fechaAsignacion;

    @Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ActivosEmpleados_SEQ")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
   
    @Column(name="activo_id", nullable=false, updatable=false)
    public Integer getActivo_id() {
		return activo_id;
	}
	public void setActivo_id(Integer activo_id) {
		this.activo_id = activo_id;
	}
	
	@Column(name="empleado_id", nullable=false, updatable=false)
	public Integer getEmpleado_id() {
		return empleado_id;
	}
	public void setEmpleado_id(Integer empleado_id) {
		this.empleado_id = empleado_id;
	}
	
	@Column(name="pasivo", nullable=false)
    public boolean isPasivo() {
        return this.pasivo;
    }
    
    public void setPasivo(boolean pasivo) {
        this.pasivo = pasivo;
    }

    
    @Column(name="fecha_asignacion", nullable=false, length=29)
    public Date getFechaAsignacion() {
        return this.fechaAsignacion;
    }
    
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

}