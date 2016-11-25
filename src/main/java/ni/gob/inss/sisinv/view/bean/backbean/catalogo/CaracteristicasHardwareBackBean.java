package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

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
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CaracteristicasHardwareService;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;

@Named
@Scope("view")
public class CaracteristicasHardwareBackBean extends BaseBackBean implements Serializable {

	private CaracteristicasHardware oCaracteristicaHardwareSeleccionado;
	private CaracteristicasHardware oCaracteristica;
	private String filtroDescripcion;
	private List<CaracteristicasHardware> listaCaracteristicasHardwarePadre = new ArrayList<CaracteristicasHardware>();
	private List<CaracteristicasHardware> listaGeneralCaracteristicas = new ArrayList<CaracteristicasHardware>();
	private static final long serialVersionUID = 1L;

	@Autowired CaracteristicasHardwareService oCaracteristicasHardwareService;
	
	@PostConstruct
	public void init(){
		this.oCaracteristica = new CaracteristicasHardware();
		this.cargarListas();
	}
	
	public void guardarOActualizar(){
		oCaracteristica.setCreadoEl(this.getTimeNow());
		oCaracteristica.setCreadoEnIp(this.getRemoteIp());
		oCaracteristica.setCreadoPor(this.getUsuarioActual().getId());
		if(oCaracteristica.getId() == null){
			guardar();
		}else{
			actualizar();
		}
	}
	
	public void guardar(){
		try {
			oCaracteristicasHardwareService.guardar(oCaracteristica);
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
		} catch (BusinessException | DAOException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "guardar", MessagesResults.ERROR_GUARDAR, e);
		}
	}
	
	public void actualizar(){
		try {
			oCaracteristicasHardwareService.actualizar(oCaracteristica);
			mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
		} catch (BusinessException | DAOException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "Actualizar", MessagesResults.ERROR_MODIFICAR, e);
		}
	}
	
	public void cargarListas(){
	 try {
		this.listaCaracteristicasHardwarePadre = oCaracteristicasHardwareService.listaCaracteristicasHardwarePadre();
		this.buscar();
	} catch (EntityNotFoundException e) {
		mostrarMensajeError(this.getClass().getSimpleName(), "cagarListas", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	public List<CaracteristicasHardware> obtieneListaCaracteristicasAsociadas(){
		try {
			if(this.oCaracteristica.getId()!=null){
				return oCaracteristicasHardwareService.listaCaracteristicasHardwarePorPadreId(this.oCaracteristica.getId());
			}
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
		}
		return null;
	}
	
	public void buscar() throws EntityNotFoundException{
		this.listaGeneralCaracteristicas = oCaracteristicasHardwareService.listaCaracteristicasPorDescripcion(this.getFiltroDescripcion(), null);
	}
	
	public void agregar(){
		this.oCaracteristica = new CaracteristicasHardware();
	}
	
	public void editar(){
		if(this.oCaracteristicaHardwareSeleccionado !=null){
			this.oCaracteristica = this.oCaracteristicaHardwareSeleccionado;
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
	
}
