package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CaracteristicasHardwareService;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;
import ni.gob.inss.sisinv.util.RegExpresionExtends;

@Named
@Scope("view")
public class CaracteristicasHardwareBackBean extends BaseBackBean implements Serializable {

	private CaracteristicasHardware oCaracteristicaHardwareSeleccionado;
	private CaracteristicasHardware oCaracteristica;
	private CaracteristicasHardware oCaracteristicaHardwareHijaSeleccionada;
	private CaracteristicasHardware oCaracteristicaHija;
	private String filtroDescripcion;
	private String regExpDescripcion = RegExpresionExtends.regExpDescripcion;
	private List<CaracteristicasHardware> listaCaracteristicasHardwarePadre = new ArrayList<CaracteristicasHardware>();
	private List<CaracteristicasHardware> listaGeneralCaracteristicas = new ArrayList<CaracteristicasHardware>();
	private List<CaracteristicasHardware> listaCaracteristicasHijas = new ArrayList<CaracteristicasHardware>();
	private static final long serialVersionUID = 1L;

	@Autowired CaracteristicasHardwareService oCaracteristicasHardwareService;
	
	@PostConstruct
	public void init(){
		this.oCaracteristica = new CaracteristicasHardware();
		this.cargarListas();
	}
	
	public void guardarOActualizar(){
	
		if(oCaracteristica.getId() == null){
			guardar();
		}else{
			actualizar();
		}
	}
	
	public void guardar(){
		oCaracteristica.setCreadoEl(this.getTimeNow());
		oCaracteristica.setCreadoEnIp(this.getRemoteIp());
		oCaracteristica.setCreadoPor(this.getUsuarioActual().getId());
		try {
			oCaracteristicasHardwareService.guardar(oCaracteristica);
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
		} catch (BusinessException | DAOException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "guardar", MessagesResults.ERROR_GUARDAR, e);
		}
	}
	
	public void actualizar(){
		try {
			oCaracteristica.setModificadoEl(this.getTimeNow());
			oCaracteristica.setModificadoEnIp(this.getRemoteIp());
			oCaracteristica.setModificadoPor(this.getUsuarioActual().getId());
			oCaracteristicasHardwareService.actualizar(oCaracteristica);
			mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
		} catch (BusinessException | DAOException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "Actualizar", MessagesResults.ERROR_MODIFICAR, e);
		}
	}
	
	public void guardarOActualizarCaracteristicaHija(){
		if(oCaracteristicaHija.getId() == null){
			guardarCaracteristicaHija();
		}else{
			actualizarCaracteristicaHija();
		}
		this.crearNuevaCaracteristicaHija();
		RequestContext.getCurrentInstance().execute("PF('datosCaracteristicaHija').hide()");
	}
	
	public void guardarCaracteristicaHija(){
		try {
			oCaracteristicaHija.setCreadoEl(this.getTimeNow());
			oCaracteristicaHija.setCreadoEnIp(this.getRemoteIp());
			oCaracteristicaHija.setCreadoPor(this.getUsuarioActual().getId());
			oCaracteristicaHija.setCaracteristicaPadreId(this.oCaracteristica.getId());
			oCaracteristicasHardwareService.guardar(oCaracteristicaHija);
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
		} catch (BusinessException | DAOException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "guardarCaracteristicaHija",MessagesResults.ERROR_GUARDAR, e);
		}
	}
	
	public void actualizarCaracteristicaHija(){
		try {
			oCaracteristicaHija.setModificadoEl(this.getTimeNow());
			oCaracteristicaHija.setModificadoEnIp(this.getRemoteIp());
			oCaracteristicaHija.setModificadoPor(this.getUsuarioActual().getId());
			oCaracteristicasHardwareService.actualizar(oCaracteristicaHija);
			mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
		} catch (BusinessException | DAOException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "actualizarCaracteristicaHija", MessagesResults.ERROR_MODIFICAR, e);

		}
	}
	
	public void crearNuevaCaracteristicaHija(){
		oCaracteristicaHija = new CaracteristicasHardware();
	}
	
	public void editarCaracteristicaHija(){
		if(oCaracteristicaHardwareHijaSeleccionada != null){
			oCaracteristicaHija = oCaracteristicaHardwareHijaSeleccionada;
			RequestContext.getCurrentInstance().execute("PF('datosCaracteristicaHija').show()");
		}else{
			mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
		}
		
	}
	
	public void cargarListas(){
	 try {
		this.listaCaracteristicasHardwarePadre = oCaracteristicasHardwareService.listaCaracteristicasHardwarePadre(null);
		
		this.buscar();
	} catch (EntityNotFoundException e) {
		mostrarMensajeError(this.getClass().getSimpleName(), "cagarListas", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	private void cargarListaCaracteristicasHijas(){
		this.listaCaracteristicasHijas = oCaracteristicasHardwareService.obtieneListaCaracteristicasHardwarePorPadreId(null, this.oCaracteristica.getId());
	}
	
	public void buscar() throws EntityNotFoundException{
		this.listaGeneralCaracteristicas = oCaracteristicasHardwareService.listaCaracteristicasPorDescripcion(this.getFiltroDescripcion(), null);
	}
	
	public void agregar(){
		this.oCaracteristica = new CaracteristicasHardware();
		this.listaCaracteristicasHijas.clear();
	}
	
	public void editar(){
		if(this.oCaracteristicaHardwareSeleccionado !=null){
			this.oCaracteristica = this.oCaracteristicaHardwareSeleccionado;
			this.cargarListaCaracteristicasHijas();
		}else{
			mostrarMensajeInfo(MessagesResults.SELECCIONE_UN_REGISTRO);
		}
	}
	
	public List<CaracteristicasHardware> getListaCaracteristicasHardwarePadre() {
		return listaCaracteristicasHardwarePadre;
	}

	public List<CaracteristicasHardware> getListaGeneralCaracteristicas() {
		return listaGeneralCaracteristicas;
	}

	public CaracteristicasHardware getoCaracteristicaHardwareSeleccionado() {
		return oCaracteristicaHardwareSeleccionado;
	}

	public void setoCaracteristicaHardwareSeleccionado(CaracteristicasHardware oCaracteristicaHardwareSeleccionado) {
		this.oCaracteristicaHardwareSeleccionado = oCaracteristicaHardwareSeleccionado;
	}

	public String getFiltroDescripcion() {
		return StringUtils.defaultString(filtroDescripcion);
	}

	public void setFiltroDescripcion(String filtroDescripcion) {
		this.filtroDescripcion = filtroDescripcion;
	}

	public CaracteristicasHardware getoCaracteristica() {
		return oCaracteristica;
	}

	public void setoCaracteristica(CaracteristicasHardware oCaracteristica) {
		this.oCaracteristica = oCaracteristica;
	}

	public List<CaracteristicasHardware> getListaCaracteristicasHijas() {
		return listaCaracteristicasHijas;
	}

	public CaracteristicasHardware getoCaracteristicaHardwareHijaSeleccionada() {
		return oCaracteristicaHardwareHijaSeleccionada;
	}

	public void setoCaracteristicaHardwareHijaSeleccionada(
			CaracteristicasHardware oCaracteristicaHardwareHijaSeleccionada) {
		this.oCaracteristicaHardwareHijaSeleccionada = oCaracteristicaHardwareHijaSeleccionada;
	}

	public CaracteristicasHardware getoCaracteristicaHija() {
		return oCaracteristicaHija;
	}

	public void setoCaracteristicaHija(CaracteristicasHardware oCaracteristicaHija) {
		this.oCaracteristicaHija = oCaracteristicaHija;
	}

	public String getRegExpDescripcion() {
		return regExpDescripcion;
	}
}