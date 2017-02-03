package ni.gob.inss.sisinv.model.entity.soporte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

@Entity
@Table(name="mantenimiento_equipo_detalle", schema="soportetecnico")
@SequenceGenerator(name="MantenimientoEquipoDetalle_SEQ", sequenceName="soportetecnico.mantenimiento_equipo_detalle_id_seq", allocationSize=1)
public class MantenimientoEquipoDetalle extends EntityBase implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private MantenimientoEquipo mantenimientoEquipoId;
	private Activos equipoId;
	private Boolean limpieza;
	private Catalogo estadoEquipo;
	private Boolean depuracionPrograma;
	private Boolean instalacionPrograma;
	private Boolean instalacionCambioSO;
	private Boolean instalacionCambioAntivirus;
	private Boolean actualizacionAntivirus;
	private Boolean escaneoVirus;
	private Boolean reemplazoComponentes;
	private Boolean configuracionPerfilUsuario;
	private Boolean configuracionCorreo;
	private Boolean configuracionImpresora;
	private Boolean configuracionCambioClaveAcceso;
	private Boolean pasivo;
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="soportetecnico.mantenimiento_equipo_detalle_id_seq")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Column(name="mantenimiento_equipo_id", nullable=false)
	public MantenimientoEquipo getMantenimientoEquipoId() {
		return mantenimientoEquipoId;
	}
	public void setMantenimientoEquipoId(MantenimientoEquipo mantenimientoEquipoId) {
		this.mantenimientoEquipoId = mantenimientoEquipoId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Column(name="equipo_id", nullable=false)
	public Activos getEquipoId() {
		return equipoId;
	}
	public void setEquipoId(Activos equipoId) {
		this.equipoId = equipoId;
	}
	
	@Column(name="limpieza")
	public Boolean getLimpieza() {
		return limpieza;
	}
	public void setLimpieza(Boolean limpieza) {
		this.limpieza = limpieza;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Column(name="estado_equipo")
	public Catalogo getEstadoEquipo() {
		return estadoEquipo;
	}
	public void setEstadoEquipo(Catalogo estadoEquipo) {
		this.estadoEquipo = estadoEquipo;
	}
	
	@Column(name="depuracion_programa")
	public Boolean getDepuracionPrograma() {
		return depuracionPrograma;
	}
	public void setDepuracionPrograma(Boolean depuracionPrograma) {
		this.depuracionPrograma = depuracionPrograma;
	}
	
	@Column(name="instalacion_programa")
	public Boolean getInstalacionPrograma() {
		return instalacionPrograma;
	}
	public void setInstalacionPrograma(Boolean instalacionPrograma) {
		this.instalacionPrograma = instalacionPrograma;
	}
	
	@Column(name="instalacion_cambio_so")
	public Boolean getInstalacionCambioSO() {
		return instalacionCambioSO;
	}
	public void setInstalacionCambioSO(Boolean instalacionCambioSO) {
		this.instalacionCambioSO = instalacionCambioSO;
	}
	
	@Column(name="instalacion_cambio_antivirus")
	public Boolean getInstalacionCambioAntivirus() {
		return instalacionCambioAntivirus;
	}
	public void setInstalacionCambioAntivirus(Boolean instalacionCambioAntivirus) {
		this.instalacionCambioAntivirus = instalacionCambioAntivirus;
	}
	
	@Column(name="actualizacion_antivirus")
	public Boolean getActualizacionAntivirus() {
		return actualizacionAntivirus;
	}
	public void setActualizacionAntivirus(Boolean actualizacionAntivirus) {
		this.actualizacionAntivirus = actualizacionAntivirus;
	}
	
	@Column(name="escaneo_antivirus")
	public Boolean getEscaneoVirus() {
		return escaneoVirus;
	}
	public void setEscaneoVirus(Boolean escaneoVirus) {
		this.escaneoVirus = escaneoVirus;
	}
	
	@Column(name="reemplazo_componentes")
	public Boolean getReemplazoComponentes() {
		return reemplazoComponentes;
	}
	public void setReemplazoComponentes(Boolean reemplazoComponentes) {
		this.reemplazoComponentes = reemplazoComponentes;
	}
	
	@Column(name="configuracion_perfil_usuario")
	public Boolean getConfiguracionPerfilUsuario() {
		return configuracionPerfilUsuario;
	}
	public void setConfiguracionPerfilUsuario(Boolean configuracionPerfilUsuario) {
		this.configuracionPerfilUsuario = configuracionPerfilUsuario;
	}
	
	@Column(name="configuracion_correo")
	public Boolean getConfiguracionCorreo() {
		return configuracionCorreo;
	}
	public void setConfiguracionCorreo(Boolean configuracionCorreo) {
		this.configuracionCorreo = configuracionCorreo;
	}
	
	@Column(name="configuracion_impresora")
	public Boolean getConfiguracionImpresora() {
		return configuracionImpresora;
	}
	public void setConfiguracionImpresora(Boolean configuracionImpresora) {
		this.configuracionImpresora = configuracionImpresora;
	}
	
	@Column(name="configuracion_cambio_clave_acceso")
	public Boolean getConfiguracionCambioClaveAcceso() {
		return configuracionCambioClaveAcceso;
	}
	public void setConfiguracionCambioClaveAcceso(Boolean configuracionCambioClaveAcceso) {
		this.configuracionCambioClaveAcceso = configuracionCambioClaveAcceso;
	}
	
	@Column(name="pasivo")
	public Boolean getPasivo() {
		return pasivo;
	}
	public void setPasivo(Boolean pasivo) {
		this.pasivo = pasivo;
	}
	
	
}
