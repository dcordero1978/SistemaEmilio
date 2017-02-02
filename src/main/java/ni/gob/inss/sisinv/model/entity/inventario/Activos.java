package ni.gob.inss.sisinv.model.entity.inventario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;

/**
 * Activos generated by hbm2java
 */
@Entity
@Table(name = "activos", schema = "inventario", uniqueConstraints = @UniqueConstraint(columnNames = "codigo_inventario"))
@SequenceGenerator(name="Activos_SEQ", sequenceName="inventario.activos_id_seq", allocationSize=1)
public class Activos extends EntityBase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer secafId;
	private Secaf catalogoSecaf; 
	private String codigoInventario;
	private String codigoSecundario;
	@NotNull(message="Por favor digite la descripción del activo.")
	private String descripcion;
	private Integer marcaId;
	private Integer modeloId;
	private String serie;
	private Date fechaAdquisicion;
	private Integer estadoFisicoId;
	private Integer tipoResguardoId;
	private String numeroProyecto;
	private String numeroBodega;
	private boolean pasivo;
	private BigDecimal valor;
	private Integer tipoMoneda;
	private String lote;
	private Integer empleadoId;
	public Empleado empleado;
	private Delegacion ubicacion;
	private Secaf secaf;
	private String color;
	private Integer proyectoId;
	public MarcasModelos marca; 
	public MarcasModelos modelo;
	public Catalogo estadoFisico;
	private Integer ubicacionId;
	public Catalogo colorCatalogo;
	private Integer entidadId;
	private Catalogo tipoResguardo;
	private Boolean baja;
	private List<ActivosCaracteristicas> listaCaracteristicas = new ArrayList<ActivosCaracteristicas>();

	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="Activos_SEQ")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "secaf_id", nullable = false, insertable=false,updatable=false)
	public Integer getSecafId() {
		return this.secafId;
	}

	public void setSecafId(Integer secafId) {
		this.secafId = secafId;
	}

	@Column(name = "codigo_inventario", unique = true, nullable = false, length = 30)
	public String getCodigoInventario() {
		return this.codigoInventario;
	}

	public void setCodigoInventario(String codigoInventario) {
		this.codigoInventario = codigoInventario;
	}

	@Column(name = "codigo_secundario", length = 30)
	public String getCodigoSecundario() {
		return this.codigoSecundario;
	}

	public void setCodigoSecundario(String codigoSecundario) {
		this.codigoSecundario = codigoSecundario;
	}

	@Column(name = "descripcion", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "marca_id", nullable = false)
	public Integer getMarcaId() {
		return this.marcaId;
	}

	public void setMarcaId(Integer marcaId) {
		this.marcaId = marcaId;
	}

	@Column(name = "modelo_id", nullable = false)
	public Integer getModeloId() {
		return this.modeloId;
	}

	public void setModeloId(Integer modeloId) {
		this.modeloId = modeloId;
	}

	@Column(name = "serie", length = 50)
	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_adquisicion", length = 29)
	public Date getFechaAdquisicion() {
		return this.fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	@Column(name = "estado_fisico_id", nullable = false)
	public Integer getEstadoFisicoId() {
		return this.estadoFisicoId;
	}

	public void setEstadoFisicoId(Integer estadoFisicoId) {
		this.estadoFisicoId = estadoFisicoId;
	}

	@Column(name = "tipo_resguardo_id", nullable = false)
	public Integer getTipoResguardoId() {
		return this.tipoResguardoId;
	}

	public void setTipoResguardoId(Integer tipoResguardoId) {
		this.tipoResguardoId = tipoResguardoId;
	}

	@Column(name = "numero_proyecto", length = 20)
	public String getNumeroProyecto() {
		return this.numeroProyecto;
	}

	public void setNumeroProyecto(String numeroProyecto) {
		this.numeroProyecto = numeroProyecto;
	}

	@Column(name = "numero_bodega", length = 20)
	public String getNumeroBodega() {
		return this.numeroBodega;
	}

	public void setNumeroBodega(String numeroBodega) {
		this.numeroBodega = numeroBodega;
	}

	@Column(name = "pasivo", nullable = false)
	public boolean isPasivo() {
		return this.pasivo;
	}

	public void setPasivo(boolean pasivo) {
		this.pasivo = pasivo;
	}

	
	@Column(name = "valor", precision = 10)
	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Column(name = "tipo_moneda", nullable = false)
	public Integer getTipoMoneda() {
		return this.tipoMoneda;
	}

	public void setTipoMoneda(Integer tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	@Column(name = "lote", length = 20)
	public String getLote() {
		return this.lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empleado_id")
	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ubicacion_id")
	public Delegacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Delegacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="secaf_id")
	public Secaf getSecaf() {
		return secaf;
	}

	public void setSecaf(Secaf secaf) {
		this.secaf = secaf;
	}

	@Column(name="color", length=50, nullable=false)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name="empleado_id", insertable = false, updatable = false)
	public Integer getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(Integer empleadoId) {
		this.empleadoId = empleadoId;
	}

	@Column(name="proyecto_id")
	public Integer getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Integer proyectoId) {
		this.proyectoId = proyectoId;
	}


	@Column(name="ubicacion_id", insertable = false, updatable = false)
	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="marca_id", insertable=false, updatable=false)
	public MarcasModelos getMarca() {
		return marca;
	}

	public void setMarca(MarcasModelos marca) {
		this.marca = marca;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="modelo_id", insertable=false, updatable=false)
	public MarcasModelos getModelo() {
		return modelo;
	}

	public void setModelo(MarcasModelos modelo) {
		this.modelo = modelo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="estado_fisico_id", insertable=false, updatable=false)
	public Catalogo getEstadoFisico() {
		return estadoFisico;
	}

	public void setEstadoFisico(Catalogo estadoFisico) {
		this.estadoFisico = estadoFisico;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="color", referencedColumnName="codigo", insertable=false, updatable=false)
	public Catalogo getColorCatalogo() {
		return colorCatalogo;
	}

	public void setColorCatalogo(Catalogo colorCatalogo) {
		this.colorCatalogo = colorCatalogo;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval=true, mappedBy="caracteristica.activoId")
	public List<ActivosCaracteristicas> getListaCaracteristicas() {
		return listaCaracteristicas;
	}

	public void setListaCaracteristicas(List<ActivosCaracteristicas> listaCaracteristicas) {
		this.listaCaracteristicas = listaCaracteristicas;
	}

	@Column(name="entidad_id", nullable=false)
	public Integer getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(Integer entidadId) {
		this.entidadId = entidadId;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="secaf_id", insertable=false, updatable=false)
	public Secaf getCatalogoSecaf() {
		return catalogoSecaf;
	}

	public void setCatalogoSecaf(Secaf catalogoSecaf) {
		this.catalogoSecaf = catalogoSecaf;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="tipo_resguardo_id", insertable=false, updatable=false)
	public Catalogo getTipoResguardo() {
		return tipoResguardo;
	}

	public void setTipoResguardo(Catalogo tipoResguardo) {
		this.tipoResguardo = tipoResguardo;
	}

	@Column(name="baja", nullable=false)
	public Boolean getBaja() {
		return baja;
	}

	public void setBaja(Boolean baja) {
		this.baja = baja;
	}

	
	
}
