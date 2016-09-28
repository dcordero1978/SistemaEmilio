package ni.gob.inss.sisinv.model.entity.catalogo;

import java.util.List;

// Generated 07-12-2016 04:38:35 PM by Hibernate Tools 4.3.4.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

@Entity
@Table(name = "empleado", schema = "catalogo")
@SequenceGenerator(name="Empleado_SEQ", sequenceName="catalogo.empleado_id_seq", allocationSize=1)
public class Empleado extends EntityBase{

	private static final long serialVersionUID = 2225725153178619039L;
	private Integer id;
	private Delegacion delegacion;
	private Integer delegacionId;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String numeroEmpleado;	
	private String nroIdentificacion;
	private Boolean pasivo;
	private String area;
	private String cargo;
	private Boolean ingresadoManual;
	private List<Activos> listaActivos;


	@Id
	@Column(name = "id", unique = true, nullable = false, columnDefinition="serial")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Empleado_SEQ")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "delegacion_id")
	public Delegacion getDelegacion() {
		return this.delegacion;
	}

	public void setDelegacion(Delegacion delegacion) {
		this.delegacion = delegacion;
	}

	@Column(name="primer_nombre", length=25, nullable = false)
	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	@Column(name="segundo_nombre", length=25)
	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	@Column(name="primer_apellido", length=25, nullable = false)
	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	@Column(name="segundo_apellido", length=25)
	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	@Column(name = "numero_identificacion", nullable = false, length = 20)
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

	@Column(name="numero_empleado", nullable= false, length=8)
	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	@OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
	public List<Activos> getListaActivos() {
		return listaActivos;
	}

	public void setListaActivos(List<Activos> listaActivos) {
		this.listaActivos = listaActivos;
	}

	@Column(name="area", insertable=false, updatable=false)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name="cargo", insertable=false, updatable=false)
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Column(name="ingresado_manual")
	public Boolean getIngresadoManual() {
		return ingresadoManual;
	}

	public void setIngresadoManual(Boolean ingresadoManual) {
		this.ingresadoManual = ingresadoManual;
	}
	
	
	
}
