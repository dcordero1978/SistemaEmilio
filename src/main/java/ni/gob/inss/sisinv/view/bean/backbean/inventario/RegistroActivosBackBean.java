package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
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
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.TipoCatalogoExtService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosCaracteristicas;
import ni.gob.inss.sisinv.util.CatalogoGeneral;
import ni.gob.inss.sisinv.util.RegExpresionExtends;

@Named
@Scope("view")
public class RegistroActivosBackBean extends BaseBackBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Empleado filtroEmpleadoSeleccionado;
	private Integer hfId;
	private Boolean modoEdicion;
	
	private Integer ubicacionId;
	private List<Delegacion> listaUbicaciones;
	private Integer catalogoSecafId;
	private List<Secaf> listaCatalogoSecaf;
	private Integer marcaId;
	private List<MarcasModelos> listaMarcas;
	private String codigoColor;
	private List<Catalogo> listaColores;
	private Integer estadoFisicoId;
	private List<Catalogo> listaEstadoFisico;
	private Integer tipoResguardoId;
	private List<Catalogo> listaTipoResguardo;
	private Integer tipoMonedaId;
	private List<Catalogo> listaTipoMoneda;
	private Integer modeloId;
	private List<MarcasModelos> listaModelos;
	private List<Activos> listaActivosAsociados;
	private List<Catalogo> listaProyectos;

	
	private String codigoSecaf;
	private String noSerie;
	private String noLote;
	private String numeroProyecto;
	private String numeroBodega;
	private BigDecimal valor;
	private Date fechaAdquisicion;
	private String descripcionActivo;
	private String codigoSecundario;
	private Integer proyectoId;
	
	private String regExpSoloLetras;
	private String regExpSoloNumeros;
	private String regExpDecimales;
	private String regExpDescripcion;
	
	private Map<String, ActivosCaracteristicas> caracteristicas = new HashMap<>();
	
	private boolean activoEspecial;
	
	//Variables designadas a los activos especiales
	private String calibre;
	private String nombreObreArte;
	private String numeroMotor;
	private String numeroChasis;
	private String numeroCilindros;
	private String anio;
	private String placa;
	private String numeroPasajeros;
	private String capacidadCarga;
	private String combustible;
	
	private Activos activoSeleccionado;
	
	@Autowired
	EmpleadoService oEmpleadoService;
	
	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	SecafService oSecafService;
	
	@Autowired
	TipoCatalogoExtService oTipoCatalogoService;

	@Autowired
	CatalogoExtService oCatalogoService;
	
	@Autowired
	ActivoService oActivoService;
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.cargarListas();
		this.regExpSoloLetras = RegExpresionExtends.regExpSoloLetrasConEspacio;
		this.regExpSoloNumeros = RegExpresionExtends.regExpSoloNumeros;
		this.regExpDecimales = RegExpresionExtends.regExpDecimales;
		this.regExpDescripcion = RegExpresionExtends.regExpDescripcion;
		this.setActivoEspecial(false);
	}
	
	public void limpiar(){
		this.setFiltroEmpleadoSeleccionado(null);
		this.setHfId(null);
		this.setListaActivosAsociados(null);
		this.setActivoSeleccionado(null);
	}
	
	public void limpiarFormularioRegistro(){
		this.setUbicacionId(null);
		this.setCatalogoSecafId(null);
		this.setMarcaId(null);
		this.setCodigoColor(StringUtils.EMPTY);
		this.setEstadoFisicoId(null);
		this.setTipoResguardoId(null);
		this.setTipoMonedaId(null);
		this.setModeloId(null);
		this.setNoSerie(StringUtils.EMPTY);
		this.setNoLote(StringUtils.EMPTY);
		this.setNumeroBodega(StringUtils.EMPTY);
		this.setNumeroProyecto(StringUtils.EMPTY);
		this.setValor(null);
		this.setFechaAdquisicion(null);
		this.setDescripcionActivo(StringUtils.EMPTY);
		this.setCodigoSecundario(StringUtils.EMPTY);
		this.setProyectoId(null);
		this.setActivoEspecial(false);
	}
	
	public void iniciarFormularioRegistro(){
		this.cargarListasFormularioRegistro();
	}
	
	//TODO: SE REQUIERE REFACTORIZAR ESTE METODO 
	public void cargarListas(){
		try {
						
			this.listaCatalogoSecaf = oSecafService.buscar("");
			this.listaColores = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.COLORES.getCodigoCatalogo());
			this.listaEstadoFisico = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.ESTADO_FISICO.getCodigoCatalogo());
			this.listaTipoResguardo = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.TIPO_RESGUARDO.getCodigoCatalogo());
			this.listaTipoMoneda = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.MONEDA.getCodigoCatalogo());
			this.listaProyectos = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.PROYECTOS.getCodigoCatalogo());
			this.listaMarcas = oCatalogoService.obtenerListaMarcas();

			List<Catalogo> listaCaracteristicas =  oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.CARACTERISTICA_ARMA_FUEGO.getCodigoCatalogo()
																	,CatalogoGeneral.CARACTERISTICA_OBRA_ARTE.getCodigoCatalogo(),
																	CatalogoGeneral.CARACTERISTICA_TRANSPORTE_MAQUINARIA.getCodigoCatalogo());
			listaCaracteristicas.stream().forEach(caracteristica -> {
				ActivosCaracteristicas oCaracteristica = new ActivosCaracteristicas();
				oCaracteristica.setCreadoEl(this.getTimeNow());
				oCaracteristica.setCreadoEnIp(this.getRemoteIp());
				oCaracteristica.setCreadoPor(this.getUsuarioActual().getId());
				oCaracteristica.getCaracteristica().setCaracteristicaCod(caracteristica.getCodigo());
				//El Combustible es catalogo para el caso del transporte
				if(StringUtils.equalsIgnoreCase(caracteristica.getCodigo(), "CMB")){
					oCaracteristica.setEsCatalogo(true);
				}
				this.caracteristicas.put(caracteristica.getCodigo(), oCaracteristica);
			});
		} catch (EntityNotFoundException  e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListas", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	public void cargarListasFormularioRegistro(){
		try {
			Empleado oEmpleado = oEmpleadoService.obtener(this.getHfId());		
			this.listaUbicaciones =oDelegacionService.listaUbicacionesEmpleado(oEmpleado);
		}catch (EntityNotFoundException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
		}		
	}
	
	public void cargarListaModelos(){
		try{
			this.listaModelos = oCatalogoService.obtenerListaModelos(this.getMarcaId());
		}catch (EntityNotFoundException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
		}
	}

	public void cargarDatosFiltro(){
		try {
			if(filtroEmpleadoSeleccionado==null) throw new BusinessException(MessagesResults.SELECCIONE_UN_REGISTRO);
			Empleado oEmpleado = oEmpleadoService.obtener(filtroEmpleadoSeleccionado.getId());
			this.setHfId(oEmpleado.getId());
			cargarListaActivos();
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos", MessagesResults.ERROR_OBTENER, e);
		}catch(BusinessException e){
			mostrarMensajeError(e.getMessage());
		}
	}
	
	public void guardar(){
		try {
			Activos oActivo = new Activos();
			oActivo.setSecaf(oSecafService.obtener(this.getCatalogoSecafId()));
			oActivo.setDescripcion(this.getDescripcionActivo());
			oActivo.setMarcaId(this.getMarcaId());
			oActivo.setModeloId(this.getModeloId());
			oActivo.setSerie(this.getNoSerie());
			oActivo.setFechaAdquisicion(this.getFechaAdquisicion());
			oActivo.setEstadoFisicoId(this.getEstadoFisicoId());
			oActivo.setTipoResguardoId(this.getTipoResguardoId());
			oActivo.setNumeroBodega(this.getNumeroBodega());
			oActivo.setNumeroProyecto(this.getNumeroProyecto());
			oActivo.setPasivo(false);
			oActivo.setValor(this.getValor());
			oActivo.setTipoMoneda(this.getTipoMonedaId());
			oActivo.setLote(this.getNoLote());
			oActivo.setEmpleado(oEmpleadoService.obtener(this.getHfId()));
			oActivo.setUbicacion(oDelegacionService.obtener(this.getUbicacionId()));
			oActivo.setColor(this.getCodigoColor());
			oActivo.setCodigoSecundario(this.getCodigoSecundario());
			oActivo.setProyectoId(this.getProyectoId());
			oActivo.setCreadoEl(this.getTimeNow());
			oActivo.setCreadoEnIp(this.getRemoteIp());
			oActivo.setCreadoPor(this.getUsuarioActual().getId());
			oActivo.setEntidadId(this.getEntidadActual().getId());
			oActivo.setListaCaracteristicas(this.obtieneListaCaracteristicasActivos(oActivo));
			
			oActivoService.guardar(oActivo);
			RequestContext.getCurrentInstance().execute("PF('modalRegistroActivo').hide()");
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
			this.cargarListaActivos();
		} catch (BusinessException | DAOException e) {
			mostrarMensajeError(MessagesResults.ERROR_GUARDAR);
		}
	}
	
	
	private List<ActivosCaracteristicas> obtieneListaCaracteristicasActivos(Activos oActivo){
		aplicaValorActivoCaracteristica(this.getCalibre(), "CLB");
		aplicaValorActivoCaracteristica(this.getNombreObreArte(), "NOB");
		aplicaValorActivoCaracteristica(this.getNumeroMotor(), "NM");
		aplicaValorActivoCaracteristica(this.getNumeroChasis(), "NCH");
		aplicaValorActivoCaracteristica(this.getNumeroCilindros(), "NC");
		aplicaValorActivoCaracteristica(this.getAnio(), "ANIO");
		aplicaValorActivoCaracteristica(this.getPlaca(), "PL");
		aplicaValorActivoCaracteristica(this.getNumeroPasajeros(), "NP");
		aplicaValorActivoCaracteristica(this.getCapacidadCarga(), "CC");
		aplicaValorActivoCaracteristica(this.getCombustible(), "CMB");
		
		List<ActivosCaracteristicas> listaPropiedades = new ArrayList<ActivosCaracteristicas>();
		this.caracteristicas.entrySet().stream()
			.filter(map -> map.getValue().getValor()!=null).forEach(mapa -> {
				mapa.getValue().setActivo(oActivo);
				listaPropiedades.add(mapa.getValue());
		});
		
		return listaPropiedades;
	}
	
	private void aplicaValorActivoCaracteristica(String campo, String codigo){
		if(StringUtils.isNotEmpty(campo)){
			this.caracteristicas.get(codigo).setValor(campo);
		}
	}
	

	public void cargarListaActivos() throws EntityNotFoundException{
		 this.listaActivosAsociados = oActivoService.obtenerListaActivosPorEmpleado(this.getHfId());
	}
	
	public Empleado getFiltroEmpleadoSeleccionado() {
		return filtroEmpleadoSeleccionado;
	}

	public void setFiltroEmpleadoSeleccionado(Empleado filtroEmpleadoSeleccionado) {
		this.filtroEmpleadoSeleccionado = filtroEmpleadoSeleccionado;
	}

	public Integer getHfId() {
		return hfId;
	}

	public void setHfId(Integer hfId) {
		this.hfId = hfId;
	}


	public Boolean getModoEdicion() {
		return this.getHfId()!=null;
	}

	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public List<Delegacion> getListaUbicaciones() {
		return listaUbicaciones;
	}

	public void setListaUbicaciones(List<Delegacion> listaUbicaciones) {
		this.listaUbicaciones = listaUbicaciones;
	}

	public List<Secaf> getListaCatalogoSecaf() {
		return listaCatalogoSecaf;
	}

	public void setListaCatalogoSecaf(List<Secaf> listaCatalogoSecaf) {
		this.listaCatalogoSecaf = listaCatalogoSecaf;
	}

	public Integer getCatalogoSecafId() {
		return catalogoSecafId;
	}

	public void setCatalogoSecafId(Integer catalogoSecafId) {
		this.catalogoSecafId = catalogoSecafId;
	}

	public Integer getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Integer marcaId) {
		this.marcaId = marcaId;
	}

	public List<MarcasModelos> getListaMarcas() {
		return listaMarcas;
	}

	public void setListaMarcas(List<MarcasModelos> listaMarcas) {
		this.listaMarcas = listaMarcas;
	}

	public String getCodigoColor() {
		return codigoColor;
	}

	public void setCodigoColor(String codigoColor) {
		this.codigoColor = codigoColor;
	}

	public List<Catalogo> getListaColores() {
		return listaColores;
	}

	public void setListaColores(List<Catalogo> listaColores) {
		this.listaColores = listaColores;
	}

	
	public Integer getEstadoFisicoId() {
		return estadoFisicoId;
	}

	public void setEstadoFisicoId(Integer estadoFisicoId) {
		this.estadoFisicoId = estadoFisicoId;
	}

	public List<Catalogo> getListaEstadoFisico() {
		return listaEstadoFisico;
	}

	public void setListaEstadoFisico(List<Catalogo> listaEstadoFisico) {
		this.listaEstadoFisico = listaEstadoFisico;
	}

	public Integer getTipoResguardoId() {
		return tipoResguardoId;
	}

	public void setTipoResguardoId(Integer tipoResguardoId) {
		this.tipoResguardoId = tipoResguardoId;
	}

	public List<Catalogo> getListaTipoResguardo() {
		return listaTipoResguardo;
	}

	public void setListaTipoResguardo(List<Catalogo> listaTipoResguardo) {
		this.listaTipoResguardo = listaTipoResguardo;
	}

	public Integer getTipoMonedaId() {
		return tipoMonedaId;
	}

	public void setTipoMonedaId(Integer tipoMonedaId) {
		this.tipoMonedaId = tipoMonedaId;
	}

	public List<Catalogo> getListaTipoMoneda() {
		return listaTipoMoneda;
	}

	public void setListaTipoMoneda(List<Catalogo> listaTipoMoneda) {
		this.listaTipoMoneda = listaTipoMoneda;
	}

	public Integer getModeloId() {
		return modeloId;
	}

	public void setModeloId(Integer modeloId) {
		this.modeloId = modeloId;
	}

	public List<MarcasModelos> getListaModelos() {
		return listaModelos;
	}

	public void setListaModelos(List<MarcasModelos> listaModelos) {
		this.listaModelos = listaModelos;
	}

	public List<Activos> getListaActivosAsociados() {
		return listaActivosAsociados;
	}

	public void setListaActivosAsociados(List<Activos> listaActivosAsociados) {
		this.listaActivosAsociados = listaActivosAsociados;
	}

	public String getNoSerie() {
		return noSerie;
	}

	public void setNoSerie(String noSerie) {
		this.noSerie = noSerie;
	}

	public String getNoLote() {
		return noLote;
	}

	public void setNoLote(String noLote) {
		this.noLote = noLote;
	}

	public String getNumeroProyecto() {
		return numeroProyecto;
	}

	public void setNumeroProyecto(String numeroProyecto) {
		this.numeroProyecto = numeroProyecto;
	}

	public String getNumeroBodega() {
		return numeroBodega;
	}

	public void setNumeroBodega(String numeroBodega) {
		this.numeroBodega = numeroBodega;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public String getDescripcionActivo() {
		return descripcionActivo;
	}

	public void setDescripcionActivo(String descripcionActivo) {
		this.descripcionActivo = descripcionActivo;
	}

	public String getCodigoSecundario() {
		return codigoSecundario;
	}

	public void setCodigoSecundario(String codigoSecundario) {
		this.codigoSecundario = codigoSecundario;
	}

	public String getRegExpSoloLetras() {
		return regExpSoloLetras;
	}

	public String getRegExpSoloNumeros() {
		return regExpSoloNumeros;
	}

	public String getRegExpDecimales() {
		return regExpDecimales;
	}

	public String getRegExpDescripcion() {
		return regExpDescripcion;
	}

	public List<Catalogo> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(List<Catalogo> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public Integer getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Integer proyectoId) {
		this.proyectoId = proyectoId;
	}

	public boolean isActivoEspecial() {
		return activoEspecial;
	}

	public void setActivoEspecial(boolean activoEspecial) {
		this.activoEspecial = activoEspecial;
	}

	public String getCalibre() {
		return calibre;
	}

	public void setCalibre(String calibre) {
		this.calibre = calibre;
	}

	public String getNombreObreArte() {
		return nombreObreArte;
	}

	public void setNombreObreArte(String nombreObreArte) {
		this.nombreObreArte = nombreObreArte;
	}

	public String getNumeroMotor() {
		return numeroMotor;
	}

	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}

	public String getNumeroChasis() {
		return numeroChasis;
	}

	public void setNumeroChasis(String numeroChasis) {
		this.numeroChasis = numeroChasis;
	}

	public String getNumeroCilindros() {
		return numeroCilindros;
	}

	public void setNumeroCilindros(String numeroCilindros) {
		this.numeroCilindros = numeroCilindros;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNumeroPasajeros() {
		return numeroPasajeros;
	}

	public void setNumeroPasajeros(String numeroPasajeros) {
		this.numeroPasajeros = numeroPasajeros;
	}

	public String getCapacidadCarga() {
		return capacidadCarga;
	}

	public void setCapacidadCarga(String capacidadCarga) {
		this.capacidadCarga = capacidadCarga;
	}

	public String getCombustible() {
		return combustible;
	}

	public void setCombustible(String combustible) {
		this.combustible = combustible;
	}

	public Activos getActivoSeleccionado() {
		return activoSeleccionado;
	}

	public void setActivoSeleccionado(Activos activoSeleccionado) {
		this.activoSeleccionado = activoSeleccionado;
	}

	public String getCodigoSecaf() {
		return codigoSecaf;
	}

	public void setCodigoSecaf(String codigoSecaf) {
		this.codigoSecaf = codigoSecaf;
	}

}