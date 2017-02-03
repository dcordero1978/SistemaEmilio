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
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Entity
@Table(name="mantenimiento_equipo", schema="soportetecnico")
@SequenceGenerator(name="Mantenimientoequipo_SEQ", sequenceName="soportetecnico.mantenimiento_equipo_id_seq", allocationSize=1)
public class MantenimientoEquipo extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Catalogo tipoMantenimientoId;
	private Empleado responsableSoporteId;
	private Integer areaId;
	private Delegacion delegacionId;
	private Empleado tecnicoSoporteId;
	private String  observaciones;
	private Date    fechaInicio;
	private Date    fechaEntrega;
	private Boolean pasivo;
	private Integer entidadId;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Mantenimientoequipo_SEQ")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="tipo_mantenimiento_id")
	public Catalogo getTipoMantenimientoId() {
		return tipoMantenimientoId;
	}
	
	public void setTipoMantenimientoId(Catalogo tipoMantenimientoId) {
		this.tipoMantenimientoId = tipoMantenimientoId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="responsable_soporte_id")
	public Empleado getResponsableSoporteId() {
		return responsableSoporteId;
	}
	public void setResponsableSoporteId(Empleado responsableSoporteId) {
		this.responsableSoporteId = responsableSoporteId;
	}
	
	@Column(name="area_id", nullable=false)
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="delegacion_id")
	public Delegacion getDelegacionId() {
		return delegacionId;
	}
	public void setDelegacionId(Delegacion delegacionId) {
		this.delegacionId = delegacionId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="tecnico_soporte_id")
	public Empleado getTecnicoSoporteId() {
		return tecnicoSoporteId;
	}
	public void setTecnicoSoporteId(Empleado tecnicoSoporteId) {
		this.tecnicoSoporteId = tecnicoSoporteId;
	}
	
	@Column(name="observaciones", length=500)
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio", nullable=false, length=29)
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_entrega", length=29)
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	
	@Column(name="pasivo", nullable=false)
	public Boolean getPasivo() {
		return pasivo;
	}
	public void setPasivo(Boolean pasivo) {
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
