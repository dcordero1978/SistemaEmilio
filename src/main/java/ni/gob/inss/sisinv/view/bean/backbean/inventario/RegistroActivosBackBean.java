package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.SecafService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.util.CatalogoGeneral;

@Scope("view")
@Named
public class RegistroActivosBackBean extends BaseBackBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Empleado filtroEmpleadoSeleccionado;
	private Integer empleadoId;
	private String filtroDescripcionCbs;
	private String filtroCodigoCbs;
	private List<Secaf> listaCatalogoSecaf = new ArrayList<Secaf>();
	private List<MarcasModelos> listaMarcas = new ArrayList<MarcasModelos>();
	private List<MarcasModelos> listaModelos = new ArrayList<MarcasModelos>();
	private List<Catalogo> listaEstadoFisico = new ArrayList<Catalogo>();
	private List<Catalogo> listaTipoResguardo = new ArrayList<Catalogo>();
	private List<Catalogo> listaColores = new ArrayList<Catalogo>();
	private List<Catalogo> listaTipoMoneda = new ArrayList<Catalogo>();
	private List<Catalogo> listaProyectos = new ArrayList<Catalogo>(); 
	private List<Delegacion> listaUbicaciones = new ArrayList<Delegacion>(); 
	private List<Activos> listaActivosUsuario = new ArrayList<Activos>();
	private List<Catalogo> listaTipoActivoEspecial = new ArrayList<Catalogo>();
	
	private Secaf catalogoSecafSeleccionado;
	private Activos oActivo;
	private Integer ubicacionId;
	@SuppressWarnings("unused")
	private boolean usuarioSeleccionado;
	private Activos activoSeleccionado;
	private String codigoTipoActivoEspecial;
	
	private String caracteristicaCalibre;
	private String caracteristicaNombreObraArte;
	private String caracteristicaNumeroMotor;
	private String caracteristicaNumeroChasis;
	private String caracteristicaNumeroCilindros;
	private String caracteristicaAnio;
	private String caracteristicaPlaca;
	private String caracteristicaNumeroPasajeros;
	private String caracteristicaCapacidadCarga;
	private String caracteristicaTipoComBustible;
	
	@Autowired EmpleadoService oEmpleadoService;
	
	@Autowired SecafService oSecafService;
	
	@Autowired CatalogoExtService oCatalogoService;
	
	@Autowired DelegacionService oDelegacionService;
	
	@Autowired ActivoService oActivoService;
	
	@PostConstruct
	public void init(){
		this.oActivo = new Activos();
		this.cargarListas();
	}	
	
	private void cargarListas(){
		try {
			this.listaMarcas = oCatalogoService.obtenerListaMarcas();
			this.listaEstadoFisico = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.ESTADO_FISICO.getCodigoCatalogo());
			this.listaTipoResguardo = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.TIPO_RESGUARDO.getCodigoCatalogo());
			this.listaColores = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.COLORES.getCodigoCatalogo());
			this.listaTipoMoneda = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.MONEDA.getCodigoCatalogo());
			this.listaProyectos = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.PROYECTOS.getCodigoCatalogo());
			this.cargarListaTipoActivosEspeciales();
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
		}
	}
	
	public void cargarListaModelosPorMarcaId(){
		try {
		 this.listaModelos = oCatalogoService.obtenerListaModelos(oActivo.getMarcaId());
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
		}
	}
	
	public void cargarListaUbicacionesPorEmpleado(){
		this.listaUbicaciones =oDelegacionService.listaUbicacionesEmpleado(filtroEmpleadoSeleccionado);
	}
	
	//CARGA LOS DATOS DEL MODAL DE EMPLEADO.
	public void cargarDatosFiltro(){
		try {
		if(filtroEmpleadoSeleccionado==null) throw new BusinessException(MessagesResults.SELECCIONE_UN_REGISTRO);
			Empleado oEmpleado = oEmpleadoService.obtener(filtroEmpleadoSeleccionado.getId());
			this.setEmpleadoId(oEmpleado.getId());
			cargarListaUbicacionesPorEmpleado();
			this.cargarListaActivosAsociadosUsuario();
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos", MessagesResults.ERROR_OBTENER, e);
		}catch(BusinessException e){
			mostrarMensajeError(e.getMessage());
		}
	}
	
	public void cargarListaTipoActivosEspeciales(){
		try {
			this.listaTipoActivoEspecial = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.TIPO_ACTIVO_ACTIVO_ESPECIAL.getCodigoCatalogo());
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
		}
	}
	
	public void cargarListaActivosAsociadosUsuario() throws EntityNotFoundException{
		this.listaActivosUsuario = oActivoService.obtenerListaActivosPorEmpleado(this.filtroEmpleadoSeleccionado.getId());
	}
	
	public void limpiarModalBusquedaCbs(){
		this.setFiltroDescripcionCbs("");
		this.setFiltroDescripcionCbs("");
		this.listaCatalogoSecaf = new ArrayList<Secaf>();
	}
	
	public void aplicaObjetoSecafAobjetoActivo(){
		limpiarModalBusquedaCbs();
		oActivo.setSecaf(catalogoSecafSeleccionado);
	}
	
	public void guardarOactualizar(){
		try {
			oActivo.setEmpleado(this.filtroEmpleadoSeleccionado);
			oActivo.setUbicacion(oDelegacionService.obtener(this.getUbicacionId()));
			oActivo.setEntidadId(this.getEntidadActual().getId());
			if(activoSeleccionado==null){
				guardar();
			}else{
				actualizar();
			}
			
			this.cargarListaActivosAsociadosUsuario();
			limpiar();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void guardar(){
		try {
			oActivo.setCreadoEl(this.getTimeNow());
			oActivo.setCreadoEnIp(this.getRemoteIp());
			oActivo.setCreadoPor(this.getUsuarioActual().getId());
			
			oActivoService.guardar(oActivo);
			
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
		} catch (BusinessException | DAOException e) {
			mostrarMensajeError(MessagesResults.ERROR_GUARDAR);
		}
	}
	
	public void actualizar(){
		try {
			oActivo.setModificadoEl(this.getTimeNow());
			oActivo.setModificadoEnIp(this.getRemoteIp());
			oActivo.setCreadoPor(this.getUsuarioActual().getId());
			oActivoService.actualizar(oActivo);
			mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
		} catch (DAOException e) {
			mostrarMensajeError(MessagesResults.ERROR_MODIFICAR);
		}
	}
	
	public void editar(){
		if(activoSeleccionado !=null){
			oActivo = activoSeleccionado;
			catalogoSecafSeleccionado = oActivo.getCatalogoSecaf();
			ubicacionId = oActivo.getUbicacionId();
		}else{
			mostrarMensajeError("POR FAVOR SELECCIONE UN REGISTRO DE LA LISTA DE ACTIVOS.");
		}
	}
	
	public void limpiar(){
		this.oActivo = new Activos();
		this.setUbicacionId(null);
		this.catalogoSecafSeleccionado=null;
	}
	
	public void buscarBienes(){
		this.listaCatalogoSecaf = oSecafService.buscar(this.getFiltroDescripcionCbs(), this.getFiltroCodigoCbs(), null);
	}

	public Empleado getFiltroEmpleadoSeleccionado() {
		return filtroEmpleadoSeleccionado;
	}

	public void setFiltroEmpleadoSeleccionado(Empleado filtroEmpleadoSeleccionado) {
		this.filtroEmpleadoSeleccionado = filtroEmpleadoSeleccionado;
	}

	public Integer getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(Integer empleadoId) {
		this.empleadoId = empleadoId;
	}

	public String getFiltroDescripcionCbs() {
		return StringUtils.defaultString(filtroDescripcionCbs) ;
	}

	public void setFiltroDescripcionCbs(String filtroDescripcionCbs) {
		this.filtroDescripcionCbs = filtroDescripcionCbs;
	}

	public String getFiltroCodigoCbs() {
		return StringUtils.defaultString(filtroCodigoCbs);
	}

	public void setFiltroCodigoCbs(String filtroCodigoCbs) {
		this.filtroCodigoCbs = filtroCodigoCbs;
	}

	public List<Secaf> getListaCatalogoSecaf() {
		return listaCatalogoSecaf;
	}

	public Secaf getCatalogoSecafSeleccionado() {
		return catalogoSecafSeleccionado;
	}

	public void setCatalogoSecafSeleccionado(Secaf catalogoSecafSeleccionado) {
		this.catalogoSecafSeleccionado = catalogoSecafSeleccionado;
	}

	public List<MarcasModelos> getListaMarcas() {
		return listaMarcas;
	}

	public Activos getoActivo() {
		return oActivo;
	}

	public void setoActivo(Activos oActivo) {
		this.oActivo = oActivo;
	}

	public List<MarcasModelos> getListaModelos() {
		return listaModelos;
	}

	public List<Catalogo> getListaEstadoFisico() {
		return listaEstadoFisico;
	}

	public List<Catalogo> getListaTipoResguardo() {
		return listaTipoResguardo;
	}


	public List<Catalogo> getListaColores() {
		return listaColores;
	}

	public List<Catalogo> getListaTipoMoneda() {
		return listaTipoMoneda;
	}

	public List<Catalogo> getListaProyectos() {
		return listaProyectos;
	}

	public List<Delegacion> getListaUbicaciones() {
		return listaUbicaciones;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public boolean isUsuarioSeleccionado() {
		return this.filtroEmpleadoSeleccionado != null;
	}

	public List<Activos> getListaActivosUsuario() {
		return listaActivosUsuario;
	}

	public Activos getActivoSeleccionado() {
		return activoSeleccionado;
	}

	public void setActivoSeleccionado(Activos activoSeleccionado) {
		this.activoSeleccionado = activoSeleccionado;
	}

	public List<Catalogo> getListaTipoActivoEspecial() {
		return listaTipoActivoEspecial;
	}

	public String getCodigoTipoActivoEspecial() {
		return codigoTipoActivoEspecial;
	}

	public void setCodigoTipoActivoEspecial(String codigoTipoActivoEspecial) {
		this.codigoTipoActivoEspecial = codigoTipoActivoEspecial;
	}

	public String getCaracteristicaCalibre() {
		return caracteristicaCalibre;
	}

	public void setCaracteristicaCalibre(String caracteristicaCalibre) {
		this.caracteristicaCalibre = caracteristicaCalibre;
	}

	public String getCaracteristicaNombreObraArte() {
		return caracteristicaNombreObraArte;
	}

	public void setCaracteristicaNombreObraArte(String caracteristicaNombreObraArte) {
		this.caracteristicaNombreObraArte = caracteristicaNombreObraArte;
	}

	public String getCaracteristicaNumeroMotor() {
		return caracteristicaNumeroMotor;
	}

	public void setCaracteristicaNumeroMotor(String caracteristicaNumeroMotor) {
		this.caracteristicaNumeroMotor = caracteristicaNumeroMotor;
	}

	public String getCaracteristicaNumeroChasis() {
		return caracteristicaNumeroChasis;
	}

	public void setCaracteristicaNumeroChasis(String caracteristicaNumeroChasis) {
		this.caracteristicaNumeroChasis = caracteristicaNumeroChasis;
	}

	public String getCaracteristicaNumeroCilindros() {
		return caracteristicaNumeroCilindros;
	}

	public void setCaracteristicaNumeroCilindros(String caracteristicaNumeroCilindros) {
		this.caracteristicaNumeroCilindros = caracteristicaNumeroCilindros;
	}

	public String getCaracteristicaAnio() {
		return caracteristicaAnio;
	}

	public void setCaracteristicaAnio(String caracteristicaAnio) {
		this.caracteristicaAnio = caracteristicaAnio;
	}

	public String getCaracteristicaPlaca() {
		return caracteristicaPlaca;
	}

	public void setCaracteristicaPlaca(String caracteristicaPlaca) {
		this.caracteristicaPlaca = caracteristicaPlaca;
	}

	public String getCaracteristicaNumeroPasajeros() {
		return caracteristicaNumeroPasajeros;
	}

	public void setCaracteristicaNumeroPasajeros(String caracteristicaNumeroPasajeros) {
		this.caracteristicaNumeroPasajeros = caracteristicaNumeroPasajeros;
	}

	public String getCaracteristicaCapacidadCarga() {
		return caracteristicaCapacidadCarga;
	}

	public void setCaracteristicaCapacidadCarga(String caracteristicaCapacidadCarga) {
		this.caracteristicaCapacidadCarga = caracteristicaCapacidadCarga;
	}

	public String getCaracteristicaTipoComBustible() {
		return caracteristicaTipoComBustible;
	}

	public void setCaracteristicaTipoComBustible(String caracteristicaTipoComBustible) {
		this.caracteristicaTipoComBustible = caracteristicaTipoComBustible;
	}
	
}