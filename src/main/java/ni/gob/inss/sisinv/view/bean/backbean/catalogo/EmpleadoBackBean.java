package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.catalogos.TipoCatalogoService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.EmpleadoService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.util.CatalogoGeneral;

@Named
@Scope("view")
public class EmpleadoBackBean extends BaseBackBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nombres;
	private String apellidos;	
	private String txtBusquedaEmpleado;
	private Integer hfId;
	private Integer tipoIdentificacion;
	private String nroIdentificacion;
	private Integer delegacionId;
	private boolean pasivo;
	
	private List<Empleado> listaEmpleados;
	private Empleado empleadoSeleccionado;
	private boolean nuevoRegistro;
	
	private List<Catalogo> listaTipoIdentificacion;
	private List<Delegacion> listaDelegaciones;
	
	@Autowired
	private EmpleadoService oEmpleadoService;
	
	@Autowired
	TipoCatalogoService oTipoCatalogoService;
	
	@Autowired
	DelegacionService oDelegacionService;
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.cargarListaTipoIdentificacion();
		this.cargarListaDelegaciones();
		this.buscar();
		
	}
	
	public void cargarListaTipoIdentificacion(){
		try {
			TiposCatalogo catalogoTipoIdentificacion= oTipoCatalogoService.obtener(CatalogoGeneral.TIPO_IDENTIFICACION.getCatalogoId());
			this.listaTipoIdentificacion = oTipoCatalogoService.obtenerCatalogos(catalogoTipoIdentificacion);
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaTipoIdentificacion", MessagesResults.ERROR_OBTENER_LISTA, e);
			
		}
	}
	
	public void cargarListaDelegaciones(){
		try{
			this.listaDelegaciones =oDelegacionService.buscar("");
		}catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaDelegaciones", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
		 
	}
	
	public void limpiar(){
		this.setNombres("");
		this.setApellidos("");
		this.setTxtBusquedaEmpleado("");
		this.setEmpleadoSeleccionado(null);
		this.setNuevoRegistro(true);
		this.setTipoIdentificacion(null);
		this.setNroIdentificacion("");
		this.setPasivo(false);
		this.setDelegacionId(null);
	}
	
	public void buscar(){
		try{
			this.setListaEmpleados(oEmpleadoService.buscar(this.getTxtBusquedaEmpleado()));
			if(this.getListaEmpleados().isEmpty()){
				mostrarMensajeInfo("No se encontrarón resultados para esta búsqueda");
			}
		}catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "buscar()", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
		
	}
	
	public void guardarOrActualizar(){
		if(this.getHfId()==null){
			this.guardar();			
		}
		this.cargarDatosEmpleado(this.getHfId());
		this.setTxtBusquedaEmpleado("");
		this.buscar();
	}
	
	public void editar(){
		if(empleadoSeleccionado ==null){
			mostrarMensajeInfo(MessagesResults.SELECCIONE_UN_REGISTRO);
		}else{
			cargarDatosEmpleado(empleadoSeleccionado.getId());
		}
	}
	
	public void guardar(){
		Delegacion oDelegacion;
		try {
			oDelegacion = oDelegacionService.obtener(this.getDelegacionId());
			Empleado oEmpleado = new Empleado();
			oEmpleado.setNombres(this.getNombres());
			oEmpleado.setApellidos(this.getApellidos());
			oEmpleado.setTipoIdentificacion(this.getTipoIdentificacion());
			oEmpleado.setNroIdentificacion(this.getNroIdentificacion());
			oEmpleado.setDelegacion(oDelegacion);
			oEmpleado.setCreadoPor(this.getUsuarioActual().getId());
			oEmpleado.setCreadoEl(this.getTimeNow());
			oEmpleado.setCreadoEnIp(this.getRemoteIp());
			oEmpleado.setPasivo(false);
			oEmpleadoService.agregar(oEmpleado);			
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
			this.setHfId(oEmpleado.getId());
			
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "guardar", MessagesResults.ERROR_GUARDAR, e);
		}		
		
	}
	
	public void cargarDatosEmpleado(Integer empleadoId){
		try {
			Empleado oEmpleado = oEmpleadoService.obtener(empleadoId);
			this.setNombres(oEmpleado.getNombres());
			this.setApellidos(oEmpleado.getApellidos());
			this.setDelegacionId(oEmpleado.getDelegacion().getId());
			this.setNroIdentificacion(oEmpleado.getNroIdentificacion());
			this.setTipoIdentificacion(oEmpleado.getTipoIdentificacion());
			this.setPasivo(oEmpleado.getPasivo());
			this.setNuevoRegistro(false);
			
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatosEmpleado", MessagesResults.ERROR_OBTENER, e);
		}
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTxtBusquedaEmpleado() {
		return txtBusquedaEmpleado;
	}

	public void setTxtBusquedaEmpleado(String txtBusquedaEmpleado) {
		this.txtBusquedaEmpleado = txtBusquedaEmpleado;
	}

	public Integer getHfId() {
		return hfId;
	}

	public void setHfId(Integer hfId) {
		this.hfId = hfId;
	}

	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public Empleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}

	public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}

	public boolean isNuevoRegistro() {
		return nuevoRegistro;
	}

	public void setNuevoRegistro(boolean nuevoRegistro) {
		this.nuevoRegistro = nuevoRegistro;
	}

	public Integer getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(Integer tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public List<Catalogo> getListaTipoIdentificacion() {
		return listaTipoIdentificacion;
	}

	public void setListaTipoIdentificacion(List<Catalogo> listaTipoIdentificacion) {
		this.listaTipoIdentificacion = listaTipoIdentificacion;
	}

	public String getNroIdentificacion() {
		return nroIdentificacion;
	}

	public void setNroIdentificacion(String nroIdentificacion) {
		this.nroIdentificacion = nroIdentificacion;
	}

	public List<Delegacion> getListaDelegaciones() {
		return listaDelegaciones;
	}

	public void setListaDelegaciones(List<Delegacion> listaDelegaciones) {
		this.listaDelegaciones = listaDelegaciones;
	}

	public Integer getDelegacionId() {
		return delegacionId;
	}

	public void setDelegacionId(Integer delegacionId) {
		this.delegacionId = delegacionId;
	}

	public boolean isPasivo() {
		return pasivo;
	}

	public void setPasivo(boolean pasivo) {
		this.pasivo = pasivo;
	}
	
}
