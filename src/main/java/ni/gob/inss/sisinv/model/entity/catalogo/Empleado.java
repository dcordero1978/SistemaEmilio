package ni.gob.inss.sisinv.model.entity.catalogo;

// Generated 07-12-2016 04:38:35 PM by Hibernate Tools 4.3.4.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;

@Entity
@Table(name = "empleado", schema = "catalogo")
@SequenceGenerator(name="Empleado_SEQ", sequenceName="catalogo.empleado_id_seq", allocationSize=1)
public class Empleado extends EntityBase{

	private static final long serialVersionUID = 2225725153178619039L;
	private Integer id;
	private Delegacion delegacion;
	private Integer delegacionId;
	private String nombres;
	private String apellidos;
	
	private Integer tipoIdentificacion;
	private String nroIdentificacion;
	private Boolean pasivo;


	@Id
	@Column(name = "id", unique = true, nullable = false, columnDefinition="serial")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Empleado_SEQ")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "delegacion_id")
	public Delegacion getDelegacion() {
		return this.delegacion;
	}

	public void setDelegacion(Delegacion delegacion) {
		this.delegacion = delegacion;
	}

	@Column(name = "nombres", nullable = false, length = 50)
	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@Column(name = "apellidos", length = 50, nullable=false)
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos= apellidos;
	}

	
	@Column(name = "tipo_identificacion", nullable = false)
	public Integer getTipoIdentificacion() {
		return this.tipoIdentificacion;
	}

	public void setTipoIdentificacion(Integer tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	@Column(name = "nro_identificacion", nullable = false, length = 20)
	public String getNroIdentificacion() {
		return this.nroIdentificacion;
	}

	public void setNroIdentificacion(String nroIdentificacion) {
		this.nroIdentificacion = nroIdentificacion;
	}

	@Column(name = "pasivo", nullable = false)
	public Boolean getPasivo() {
		return this.pasivo;
	}

	public void setPasivo(Boolean pasivo) {
		this.pasivo = pasivo;
	}

	@Column(name="delegacion_id", insertable=false, updatable=false)
	public Integer getDelegacionId() {
		return delegacionId;
	}

	public void setDelegacionId(Integer delegacionId) {
		this.delegacionId = delegacionId;
	}
	
	
	
}
