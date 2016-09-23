package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.SecafService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.TipoCatalogoExtService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;
import ni.gob.inss.sisinv.util.CatalogoGeneral;

@Named
@Scope("view")
public class RegistroActivosBackBean extends BaseBackBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<Empleado> listaEmpleados;
	private String busquedaEmpleado;
	private Empleado empleadoSeleccionado;
	private Integer hfId;
	private Boolean modoEdicion;
	
	private String nombreCompleto;
	private String numeroEmpleado;
	
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
	
	
	@Autowired
	EmpleadoService oEmpleadoService;
	
	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	SecafService oSecafService;
	
	@Autowired
	TipoCatalogoExtService oTipoCatalogoService;
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.cargarListaEmpleados();
	}
	
	public void limpiar(){
		this.setEmpleadoSeleccionado(null);
		this.setBusquedaEmpleado(StringUtils.EMPTY);
		this.setNombreCompleto(StringUtils.EMPTY);
		this.setNumeroEmpleado(StringUtils.EMPTY);
		this.setHfId(null);
	}
	
	public void limpiarFormularioRegistro(){
		this.setUbicacionId(null);
		this.setListaUbicaciones(null);
		this.setListaCatalogoSecaf(null);
		this.setCatalogoSecafId(null);
		this.setMarcaId(null);
		this.setListaMarcas(null);
		this.setCodigoColor(StringUtils.EMPTY);
		this.setListaColores(null);
		this.setEstadoFisicoId(null);
		this.setListaEstadoFisico(null);
		this.setTipoResguardoId(null);
		this.setListaTipoResguardo(null);
		this.setTipoMonedaId(null);
		this.setListaTipoMoneda(null);
	}
	
	public void iniciarFormularioRegistro(){
		this.cargarListasFormularioRegistro();
	}
	public void cargarListaEmpleados(){
		try {
			this.setListaEmpleados(oEmpleadoService.buscar(this.getBusquedaEmpleado(), null));
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaEmpleados", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	public void cargarListasFormularioRegistro(){
		try {
			Empleado oEmpleado = oEmpleadoService.obtener(this.getHfId());		
			TiposCatalogo catalogoColor = null;
			TiposCatalogo catalogoEstadoFisico = null;
			TiposCatalogo catalogoTipoResguardo = null;
			TiposCatalogo catalogoTipoMoneda = null;
			
			this.listaUbicaciones =oDelegacionService.listaUbicacionesPorDepartamento(oEmpleado.getDelegacion().getDepartamentoId());
			this.listaCatalogoSecaf = oSecafService.buscar("");
			catalogoColor = oTipoCatalogoService.obtenerTipoCatalogoPorCodigo(CatalogoGeneral.COLORES.getCodigoCatalogo());
			this.listaColores = oTipoCatalogoService.obtenerCatalogos(catalogoColor);
			catalogoEstadoFisico  = oTipoCatalogoService.obtenerTipoCatalogoPorCodigo(CatalogoGeneral.ESTADO_FISICO.getCodigoCatalogo());
			this.listaEstadoFisico = oTipoCatalogoService.obtenerCatalogos(catalogoEstadoFisico);
			catalogoTipoResguardo = oTipoCatalogoService.obtenerTipoCatalogoPorCodigo(CatalogoGeneral.TIPO_RESGUARDO.getCodigoCatalogo());
			this.listaTipoResguardo = oTipoCatalogoService.obtenerCatalogos(catalogoTipoResguardo);
			catalogoTipoMoneda = oTipoCatalogoService.obtenerTipoCatalogoPorCodigo(CatalogoGeneral.MONEDA.getCodigoCatalogo());
			this.listaTipoMoneda = oTipoCatalogoService.obtenerCatalogos(catalogoTipoMoneda);
		} catch (EntityNotFoundException | BusinessException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
		}		
	}
	
	
	
	public void cargarDatos(){
		try {
			if(empleadoSeleccionado==null) throw new BusinessException(MessagesResults.SELECCIONE_UN_REGISTRO);
			Empleado oEmpleado = oEmpleadoService.obtener(empleadoSeleccionado.getId());
			this.setNombreCompleto(oEmpleado.getPrimerNombre() + " "+ oEmpleado.getSegundoNombre() + " " +oEmpleado.getPrimerApellido()+" "+oEmpleado.getSegundoApellido());
			this.setNumeroEmpleado(oEmpleado.getNumeroEmpleado());
			this.setHfId(oEmpleado.getId());			
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos", MessagesResults.ERROR_OBTENER, e);
		}catch(BusinessException e){
			mostrarMensajeError(e.getMessage());
		}
	}

	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public String getBusquedaEmpleado() {
		return busquedaEmpleado;
	}

	public void setBusquedaEmpleado(String busquedaEmpleado) {
		this.busquedaEmpleado = busquedaEmpleado;
	}

	public Empleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}

	public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}

	public Integer getHfId() {
		return hfId;
	}

	public void setHfId(Integer hfId) {
		this.hfId = hfId;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
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
	
	
}
