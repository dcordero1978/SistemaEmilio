package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CaracteristicasHardwareService;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;

@Named
@Scope("view")
public class CaracteristicasHardwareBackBean extends BaseBackBean implements Serializable {

	private Integer caracteristicaPadreId;
	private CaracteristicasHardware oCaracteristicaHardwareSeleccionado;
	private String filtroDescripcion;
	private List<CaracteristicasHardware> listaCaracteristicasHardwarePadre = new ArrayList<CaracteristicasHardware>();
	private List<CaracteristicasHardware> listaGeneralCaracteristicas = new ArrayList<CaracteristicasHardware>();
	private static final long serialVersionUID = 1L;

	@Autowired CaracteristicasHardwareService oCaracteristicasHardwareService;
	
	@PostConstruct
	public void init(){
		this.cargarListas();
	}
	
	public void cargarListas(){
	 try {
		this.listaCaracteristicasHardwarePadre = oCaracteristicasHardwareService.listaCaracteristicasHardwarePadre();
		this.listaGeneralCaracteristicas = oCaracteristicasHardwareService.listaCaracteristicasPorDescripcion(this.getFiltroDescripcion(), null);
	} catch (EntityNotFoundException e) {
		mostrarMensajeError(this.getClass().getSimpleName(), "cagarListas", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}

	public List<CaracteristicasHardware> getListaCaracteristicasHardwarePadre() {
		return listaCaracteristicasHardwarePadre;
	}

	public Integer getCaracteristicaPadreId() {
		return caracteristicaPadreId;
	}

	public void setCaracteristicaPadreId(Integer caracteristicaPadreId) {
		this.caracteristicaPadreId = caracteristicaPadreId;
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
	
	
}
