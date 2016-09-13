package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.catalogos.CatalogoService;
import ni.gob.inss.barista.businesslogic.service.catalogos.TipoCatalogoService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.EmpleadoService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.util.RegExpresionExtends;

@Named
@Scope("view")
public class EmpleadoBackBean extends BaseBackBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nombres;
	private String apellidos;	
	private String txtBusquedaEmpleado;
	private Integer hfId;
	private String nroIdentificacion;
	private Integer delegacionId;
	private boolean pasivo;
	
	private Integer delegacionBusquedaEmpleado;
	
	private List<Empleado> listaEmpleados;
	private Empleado empleadoSeleccionado;
	private boolean nuevoRegistro;
	
	private List<Delegacion> listaDelegaciones;
	
	private String regExpLetras;
	private String regExpCedula;
	
	
	@Autowired
	private EmpleadoService oEmpleadoService;
	
	@Autowired
	TipoCatalogoService oTipoCatalogoService;
	
	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	CatalogoService oCatalogoService;
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.cargarListaDelegaciones();
		this.cargaValidaciones();
		this.buscar();		
	}
	
	public void cargaValidaciones(){
		regExpLetras = RegExpresionExtends.regExpSoloLetrasConEspacio;	
		regExpCedula = RegExpresionExtends.regExpCedula;
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
		this.setNroIdentificacion("");
		this.setPasivo(false);
		this.setDelegacionId(null);
		this.setHfId(null);
	}
	
	public void buscar(){
		try{
			this.listaEmpleados = oEmpleadoService.buscar(this.getTxtBusquedaEmpleado(), this.getDelegacionBusquedaEmpleado());
			//this.setListaEmpleados(oEmpleadoService.buscar(this.getTxtBusquedaEmpleado()));
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
		}else{
			this.actualizar();
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
	
	public void actualizar(){
		Delegacion oDelegacion;
		try {
			
			Empleado oEmpleado = oEmpleadoService.obtener(this.getHfId());
			oDelegacion = oDelegacionService.obtener(this.getDelegacionId());
			oEmpleado.setNombres(this.getNombres());
			oEmpleado.setApellidos(this.getApellidos());
			oEmpleado.setNroIdentificacion(this.getNroIdentificacion());
			oEmpleado.setDelegacion(oDelegacion);
			oEmpleado.setPasivo(this.isPasivo());
			oEmpleado.setModificadoEl(this.getTimeNow());
			oEmpleado.setModificadoEnIp(this.getRemoteIp());
			oEmpleado.setModificadoPor(this.getUsuarioActual().getId());
			
			oEmpleadoService.actualizar(oEmpleado);
			mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
			this.setHfId(oEmpleado.getId());			
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "actualizar", MessagesResults.ERROR_MODIFICAR, e);

		}
	}
	
	public void guardar(){
		Delegacion oDelegacion;
		try {
			oDelegacion = oDelegacionService.obtener(this.getDelegacionId());
			Empleado oEmpleado = new Empleado();
			oEmpleado.setNombres(this.getNombres());
			oEmpleado.setApellidos(this.getApellidos());
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
			this.setPasivo(oEmpleado.getPasivo());
			this.setNuevoRegistro(false);
			this.setHfId(oEmpleado.getId());
			
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

	public String getRegExpLetras() {
		return regExpLetras;
	}

	public Integer getDelegacionBusquedaEmpleado() {
		return delegacionBusquedaEmpleado;
	}

	public void setDelegacionBusquedaEmpleado(Integer delegacionBusquedaEmpleado) {
		this.delegacionBusquedaEmpleado = delegacionBusquedaEmpleado;
	}

	public String getRegExpCedula() {
		return regExpCedula;
	}	
	
}
