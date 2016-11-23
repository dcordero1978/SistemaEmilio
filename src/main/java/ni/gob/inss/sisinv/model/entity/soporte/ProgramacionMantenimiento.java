package ni.gob.inss.sisinv.model.entity.soporte;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ni.gob.inss.barista.model.entity.EntityBase;
@Entity
@Table(name="programacion_mantenimiento", schema="soportetecnico")
@SequenceGenerator(name="Programacionmantenimiento_SEQ", sequenceName="soportetecnico.programacion_mantenimiento_id_seq", allocationSize=1)
public class ProgramacionMantenimiento extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer delegacion_Id;
	private String asunto;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer estadoMto;
	private boolean pasivo;
	private Integer entidadId;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Programacionmantenimiento_SEQ")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="delegacion_id", nullable=true)
	public Integer getDelegacion_Id() {
		return delegacion_Id;
	}
	public void setDelegacion_Id(Integer delegacion) {
		this.delegacion_Id = delegacion;
	}
	
	@Column(name="asunto", nullable=false)
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio", nullable=false,length=29)
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_fin", nullable=false,length=29)
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	@Column(name="estado_mto", nullable=false)
	public Integer getEstadoMto() {
		return estadoMto;
	}
	public void setEstadoMto(Integer estadoMto) {
		this.estadoMto = estadoMto;
	}
	
	@Column(name="pasivo", nullable=false)
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
