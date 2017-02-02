package ni.gob.inss.sisinv.model.entity.inventario;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Entity
@Table(name="activos_bajas", schema="inventario")
@SequenceGenerator(name="Bajas_SEQ", sequenceName="inventario.activos_bajas_id_seq", allocationSize=1)

public class ActivosBajas extends EntityBase implements Serializable  {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Activos activoId;
	private Empleado empleadoIdOrigen;
	private Delegacion ubicacionIdOrigen;
	private Empleado empleadoIdDestino;
	private Delegacion ubicacionIdDestino;
	private Catalogo tipoDiagnostico;
	private Integer diagnosticoId;
	private Empleado usuarioAutorizaBaja;
	private String observaciones;
	private boolean pasivo;
	private Integer entidadId;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Bajas_SEQ")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activo_id", nullable=false)
	public Activos getActivoId() {
		return activoId;
	}
	public void setActivoId(Activos activoId) {
		this.activoId = activoId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="empleado_id_origen", nullable=false)
	public Empleado getEmpleadoIdOrigen() {
		return empleadoIdOrigen;
	}
	public void setEmpleadoIdOrigen(Empleado empleadoIdOrigen) {
		this.empleadoIdOrigen = empleadoIdOrigen;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ubicacion_id_origen", nullable=false)
	public Delegacion getUbicacionIdOrigen() {
		return ubicacionIdOrigen;
	}
	public void setUbicacionIdOrigen(Delegacion ubicacionIdOrigen) {
		this.ubicacionIdOrigen = ubicacionIdOrigen;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="empleado_id_destino", nullable=false)
	public Empleado getEmpleadoIdDestino() {
		return empleadoIdDestino;
	}
	public void setEmpleadoIdDestino(Empleado empleadoIdDestino) {
		this.empleadoIdDestino = empleadoIdDestino;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ubicacion_id_destino", nullable=false)
	public Delegacion getUbicacionIdDestino() {
		return ubicacionIdDestino;
	}
	public void setUbicacionIdDestino(Delegacion ubicacionIdDestino) {
		this.ubicacionIdDestino = ubicacionIdDestino;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="tipo_diagnostico", insertable=false, updatable=false)
	public Catalogo getTipoDiagnostico() {
		return tipoDiagnostico;
	}
	public void setTipoDiagnostico(Catalogo tipoDiagnostico) {
		this.tipoDiagnostico = tipoDiagnostico;
	}
	
	@Column(name="diagnostico_id")
	public Integer getDiagnosticoId() {
		return diagnosticoId;
	}
	public void setDiagnosticoId(Integer diagnosticoId) {
		this.diagnosticoId = diagnosticoId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="usuario_autoriza_baja", nullable=false)
	public Empleado getUsuarioAutorizaBaja() {
		return usuarioAutorizaBaja;
	}
	public void setUsuarioAutorizaBaja(Empleado usuarioAutorizaBaja) {
		this.usuarioAutorizaBaja = usuarioAutorizaBaja;
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
	
	@Column(name="entidad_id", nullable=false)
	public Integer getEntidadId() {
		return entidadId;
	}
	public void setEntidadId(Integer entidadId) {
		this.entidadId = entidadId;
	}
	
}
