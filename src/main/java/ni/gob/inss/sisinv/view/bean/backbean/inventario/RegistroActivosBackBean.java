package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.SecafService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.TipoCatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.inventario.ActivosCaracteristicasService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosCaracteristicas;
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
	private List<TiposCatalogo> listaTipoActivoEspecial = new ArrayList<TiposCatalogo>();
	private Map<String, ActivosCaracteristicas> caracteristicas = new HashMap<String, ActivosCaracteristicas>();
	
	private Secaf catalogoSecafSeleccionado;
	private Activos oActivo;
	private Integer ubicacionId;
	@SuppressWarnings("unused")//Usado en la vista
	private boolean usuarioSeleccionado;
	private Activos activoSeleccionado;
	private String codigoTipoActivoEspecial;
	
	private ActivosCaracteristicas caracteristicaCalibre = new ActivosCaracteristicas();
	private ActivosCaracteristicas caracteristicaNombreObraArte = new ActivosCaracteristicas();
	//CARACTERISTICAS ESPECIALES DE MAQUINARIA O TRANSPORTE
	private ActivosCaracteristicas caracteristicaNumeroMotor = new ActivosCaracteristicas();
	private ActivosCaracteristicas caracteristicaNumeroChasis = new ActivosCaracteristicas();
	private ActivosCaracteristicas caracteristicaNumeroCilindros = new ActivosCaracteristicas();
	private ActivosCaracteristicas caracteristicaAnio = new ActivosCaracteristicas();
	private ActivosCaracteristicas caracteristicaPlaca = new ActivosCaracteristicas();
	private ActivosCaracteristicas caracteristicaNumeroPasajeros = new ActivosCaracteristicas();
	private ActivosCaracteristicas caracteristicaCapacidadCarga = new ActivosCaracteristicas();
	private ActivosCaracteristicas caracteristicaTipoComBustible = new ActivosCaracteristicas();
	
	@SuppressWarnings("unused") //Usado en la vista
	private boolean seleccionCaracteristicaArmaDeFuego;
	@SuppressWarnings("unused")//Usado en la vista
	private boolean seleccionCaracteristicaTransporte;
	@SuppressWarnings("unused")//Usado en la vista
	private boolean seleccionCaracteristicaObraArte;
	
	@Autowired EmpleadoService oEmpleadoService;
	
	@Autowired SecafService oSecafService;
	
	@Autowired CatalogoExtService oCatalogoService;
	
	@Autowired DelegacionService oDelegacionService;
	
	@Autowired ActivoService oActivoService;
	
	@Autowired TipoCatalogoExtService oTipoCatalogoService;
	
	@Autowired ActivosCaracteristicasService oCaracteristicasService;
	
	@PostConstruct
	public void init(){
		this.oActivo = new Activos();
		this.cargarListas();
		inicializarCaracteristicas();
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
			this.listaTipoActivoEspecial =
					oTipoCatalogoService.obtenerListaCatalogoPorCodigo(CatalogoGeneral.CARACTERISTICA_ARMA_FUEGO.getCodigoCatalogo()
					,CatalogoGeneral.CARACTERISTICA_OBRA_ARTE.getCodigoCatalogo()
					,CatalogoGeneral.CARACTERISTICA_TRANSPORTE_MAQUINARIA.getCodigoCatalogo());
					
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
		}
	}
	
	public List<ActivosCaracteristicas> obtenerCaracteristicasEspeciales(Integer activoId){
		return oCaracteristicasService.obtieneListaCaracteristicasActivo(activoId);
	}
	
	private void inicializarCaracteristicas(){
		ActivosCaracteristicas oCaracteristicas = new ActivosCaracteristicas();
		oCaracteristicas.setCreadoEl(this.getTimeNow());
		oCaracteristicas.setCreadoEnIp(this.getRemoteIp());
		oCaracteristicas.setCreadoPor(this.getUsuarioActual().getId());
		
		caracteristicaCalibre = oCaracteristicas;
		caracteristicaCalibre.getCaracteristica().setCaracteristicaCod("CLB");
		caracteristicaNombreObraArte = oCaracteristicas;
		caracteristicaNombreObraArte.getCaracteristica().setCaracteristicaCod("NOB");
		caracteristicaNumeroMotor = oCaracteristicas;
		caracteristicaNumeroMotor.getCaracteristica().setCaracteristicaCod("NM");
		caracteristicaNumeroChasis = oCaracteristicas;
		caracteristicaNumeroChasis.getCaracteristica().setCaracteristicaCod("NCH");
		caracteristicaNumeroCilindros = oCaracteristicas;
		caracteristicaNumeroCilindros.getCaracteristica().setCaracteristicaCod("NC");
		caracteristicaAnio = oCaracteristicas;
		caracteristicaAnio.getCaracteristica().setCaracteristicaCod("ANIO");
		caracteristicaPlaca = oCaracteristicas;
		caracteristicaPlaca.getCaracteristica().setCaracteristicaCod("PL");
		caracteristicaNumeroPasajeros = oCaracteristicas;
		caracteristicaNumeroPasajeros.getCaracteristica().setCaracteristicaCod("NP");
		caracteristicaCapacidadCarga = oCaracteristicas;
		caracteristicaCapacidadCarga.getCaracteristica().setCaracteristicaCod("CC");
		caracteristicaTipoComBustible = oCaracteristicas;
		caracteristicaTipoComBustible.getCaracteristica().setCaracteristicaCod("CMB");
		caracteristicaTipoComBustible.setEsCatalogo(true);		
	}
	
	private List<ActivosCaracteristicas> obtieneListaCaracteristicas(Activos oActivo){		
		ArrayList<ActivosCaracteristicas> listaCaracteristicasEspecialesActivo = new ArrayList<ActivosCaracteristicas>();
		if(isSeleccionCaracteristicaArmaDeFuego()){
			if(!StringUtils.isBlank(caracteristicaCalibre.getValor())){
				caracteristicaCalibre.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaCalibre);
			}
		}else if(isSeleccionCaracteristicaObraArte()){
			if(!StringUtils.isBlank(caracteristicaNombreObraArte.getValor())){
				caracteristicaNombreObraArte.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaNombreObraArte);
			}
		}else if(isSeleccionCaracteristicaTransporte()){
			if(!StringUtils.isBlank(caracteristicaNumeroMotor.getValor())){
				caracteristicaNumeroMotor.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaNumeroMotor);
			}
			if(!StringUtils.isBlank(caracteristicaNumeroChasis.getValor())){
				caracteristicaNumeroChasis.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaNumeroChasis);
			}
			if(!StringUtils.isBlank(caracteristicaNumeroCilindros.getValor())){
				caracteristicaNumeroCilindros.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaNumeroCilindros);
			}
			if(!StringUtils.isBlank(caracteristicaAnio.getValor())){
				caracteristicaAnio.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaAnio);
			}
			if(!StringUtils.isBlank(caracteristicaPlaca.getValor())){
				caracteristicaPlaca.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaPlaca);
			}
			if(!StringUtils.isBlank(caracteristicaNumeroPasajeros.getValor())){
				caracteristicaNumeroPasajeros.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaNumeroPasajeros);
			}
			if(!StringUtils.isBlank(caracteristicaCapacidadCarga.getValor())){
				caracteristicaCapacidadCarga.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaCapacidadCarga);
			}
			if(!StringUtils.isBlank(caracteristicaTipoComBustible.getValor())){
				caracteristicaTipoComBustible.getCaracteristica().setActivoId(oActivo);
				listaCaracteristicasEspecialesActivo.add(caracteristicaTipoComBustible);
			}			
		}
		
		return listaCaracteristicasEspecialesActivo;
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
			oActivo.setListaCaracteristicas(obtieneListaCaracteristicas(oActivo));
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
		} catch (DAOException | BusinessException e) {
			mostrarMensajeError(MessagesResults.ERROR_MODIFICAR);
		}
	}
	
	public void editar(){
		if(activoSeleccionado !=null){
			oActivo = activoSeleccionado;
			cargarListaModelosPorMarcaId();
			catalogoSecafSeleccionado = oActivo.getCatalogoSecaf();
			ubicacionId = oActivo.getUbicacionId();
			List<ActivosCaracteristicas> listCaracteristicasEspeciales =  obtenerCaracteristicasEspeciales(oActivo.getId());
			procesarListaCaracteristicas(listCaracteristicasEspeciales);
		}else{
			mostrarMensajeError("POR FAVOR SELECCIONE UN REGISTRO DE LA LISTA DE ACTIVOS.");
		}
	}

	public void limpiar(){
		this.oActivo = new Activos();
		this.activoSeleccionado = null;
		this.setUbicacionId(null);
		this.catalogoSecafSeleccionado=null;
		this.caracteristicas.clear();
	}
	
	private void procesarListaCaracteristicas(List<ActivosCaracteristicas> listaCaracteristicasEspeciales){
		if(!listaCaracteristicasEspeciales.isEmpty()){
			ActivosCaracteristicas oActivosCaractteristicas = listaCaracteristicasEspeciales.get(0);
			String codigo = oActivosCaractteristicas.getCaracteristica().getCaracteristicaCod();
			Catalogo oCatalogo=  oCatalogoService.obtieneCatalogoPorCodigo(codigo);
			this.setCodigoTipoActivoEspecial(oCatalogo.getRefTipoCatalogo());
			
			listaCaracteristicasEspeciales.stream().forEach(caracteristica -> {
				
			});
			
		}
		
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

	public List<TiposCatalogo> getListaTipoActivoEspecial() {
		return listaTipoActivoEspecial;
	}

	public String getCodigoTipoActivoEspecial() {
		return codigoTipoActivoEspecial;
	}

	public void setCodigoTipoActivoEspecial(String codigoTipoActivoEspecial) {
		this.codigoTipoActivoEspecial = codigoTipoActivoEspecial;
	}

	public ActivosCaracteristicas getCaracteristicaCalibre() {
		return caracteristicaCalibre;
	}

	public ActivosCaracteristicas getCaracteristicaNombreObraArte() {
		return caracteristicaNombreObraArte;
	}

	public ActivosCaracteristicas getCaracteristicaNumeroMotor() {
		return caracteristicaNumeroMotor;
	}

	public ActivosCaracteristicas getCaracteristicaNumeroChasis() {
		return caracteristicaNumeroChasis;
	}

	public ActivosCaracteristicas getCaracteristicaNumeroCilindros() {
		return caracteristicaNumeroCilindros;
	}

	public ActivosCaracteristicas getCaracteristicaAnio() {
		return caracteristicaAnio;
	}

	public ActivosCaracteristicas getCaracteristicaPlaca() {
		return caracteristicaPlaca;
	}

	public ActivosCaracteristicas getCaracteristicaNumeroPasajeros() {
		return caracteristicaNumeroPasajeros;
	}

	public ActivosCaracteristicas getCaracteristicaCapacidadCarga() {
		return caracteristicaCapacidadCarga;
	}

	public ActivosCaracteristicas getCaracteristicaTipoComBustible() {
		return caracteristicaTipoComBustible;
	}

	public boolean isSeleccionCaracteristicaArmaDeFuego() {
		return StringUtils.equals(this.getCodigoTipoActivoEspecial(), CatalogoGeneral.CARACTERISTICA_ARMA_FUEGO.getCodigoCatalogo());
	}

	public boolean isSeleccionCaracteristicaTransporte() {
		return StringUtils.equals(this.getCodigoTipoActivoEspecial(), CatalogoGeneral.CARACTERISTICA_TRANSPORTE_MAQUINARIA.getCodigoCatalogo());
	}

	public boolean isSeleccionCaracteristicaObraArte() {
		return StringUtils.equals(this.getCodigoTipoActivoEspecial(), CatalogoGeneral.CARACTERISTICA_OBRA_ARTE.getCodigoCatalogo());
	}
}