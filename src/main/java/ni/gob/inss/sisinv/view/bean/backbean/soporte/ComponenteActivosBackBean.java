package ni.gob.inss.sisinv.view.bean.backbean.soporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CaracteristicasHardwareService;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.ComponentesActivosService;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.model.entity.soporte.ComponentesActivos;

@Named
@Scope("view")
public class ComponenteActivosBackBean extends BaseBackBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 private Empleado filtroEmpleadoSeleccionado;
	 private List<Activos> listaActivosTipoEquipo = new ArrayList<Activos>();
	 private List<ComponentesActivos> listaComponentesAsociados = new ArrayList<ComponentesActivos>();
	 private List<CaracteristicasHardware> listaComponentesDisponibles = new ArrayList<CaracteristicasHardware>();
	 private List<CaracteristicasHardware> listaComponentesHijos = new ArrayList<CaracteristicasHardware>();
	 
	 private ComponentesActivos componenteAsociadoSeleccionado;
	 private Activos equipoSeleccionado;
	 private Integer componentePadreId;
	 
	@Autowired
	ActivoService oActivoService;
	@Autowired CaracteristicasHardwareService oCaracteristicasHardwareService;
	@Autowired ComponentesActivosService oComponentesActivosService; 
	
	@PostConstruct
	public void init(){
	}
	
	public void cargarDatosFiltro(){
		this.listaActivosTipoEquipo =  oActivoService.obtenerListaDeActivosClasificadosComoEquiposInformaticos(filtroEmpleadoSeleccionado.getId(), false);
    }
	
	public void cargarListaComponentesPadresDisponibles(){
		Integer oTipoActivoId = equipoSeleccionado.getCatalogoSecaf().getTipoActivo();
		this.listaComponentesDisponibles =  oCaracteristicasHardwareService.obtieneListaCaracteristicasHardwareAsociadoActivo(oTipoActivoId);	
		this.listaComponentesAsociados = oComponentesActivosService.obtenerListaComponentes(this.equipoSeleccionado.getId(), Boolean.FALSE);
	}
	
	public void cargarListaComponentesHijos(){
		this.listaComponentesHijos = oCaracteristicasHardwareService.obtieneListaCaracteristicasHardwarePorPadreId(Boolean.FALSE, this.getComponentePadreId());
	}
	
	public void mostrarModal(){
			try {
				if(this.listaComponentesDisponibles.isEmpty()){
					throw new BusinessException("NO HAY COMPONENTES DISPONIBLES A ASOCIAR PARA ESTE EQUIPO");
				}else{
					RequestContext.getCurrentInstance().execute("PF('modalComponentesDisponibles').show()");
				}
			} catch (BusinessException e) {
				mostrarMensajeInfo(e.getMessage());
			}
	}
	
	public void asociarComponente(CaracteristicasHardware oComponenteDisponibleSeleccionado){
		 try {
			 ComponentesActivos oComponenteActivo = new ComponentesActivos();
		 
			 oComponenteActivo.setActivoId(equipoSeleccionado.getId());
			 oComponenteActivo.setComponenteId(oComponenteDisponibleSeleccionado.getId());
			 oComponenteActivo.setPasivo(Boolean.FALSE);
			 oComponenteActivo.setCreadoEl(this.getTimeNow());
			 oComponenteActivo.setCreadoPor(this.getUsuarioActual().getId());
			 oComponenteActivo.setCreadoEnIp(this.getRemoteIp());
			 oComponentesActivosService.guardar(oComponenteActivo);
			 mostrarMensajeInfo("SE HA ASOCIADO CORRECTAMENTE EL COMPONENTE AL EQUIPO");
			 this.cargarDatosComponentesEquipo();
			
		} catch (DAOException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "asociarComponente", "LO SENTIMOS, HA OCURRIDO UN ERROR AL ASOCIAR EL COMPONENTE AL EQUIPO", e);
		}
	}
	
	public void removerComponente(){
		try {
			if(componenteAsociadoSeleccionado == null){
				throw new BusinessException("SELECCIONE UN COMPONENTE DE LA LISTA ");
			}else{
				componenteAsociadoSeleccionado.setModificadoEl(this.getTimeNow());
				componenteAsociadoSeleccionado.setModificadoEnIp(this.getRemoteIp());
				componenteAsociadoSeleccionado.setModificadoPor(this.getUsuarioActual().getId());
				componenteAsociadoSeleccionado.setPasivo(Boolean.TRUE);
				oComponentesActivosService.modificar(componenteAsociadoSeleccionado);
				this.cargarDatosComponentesEquipo();
				mostrarMensajeInfo("SE HA REMOVIDO SATISFACTORIAMENTE EL COMPONENTE");
			}
		} catch (DAOException e ) {
			mostrarMensajeError(this.getClass().getSimpleName(), "removerComponente", "LO SENTIMOS, HA OCURRIDO UN ERROR AL REMOVER EL COMPONENTE", e);
		}catch(BusinessException e){
			mostrarMensajeError(e.getMessage());
		}
		
	}
	
	public void cargarDatosComponentesEquipo(){
		this.cargarListaComponentesPadresDisponibles();
		this.listaComponentesAsociados = oComponentesActivosService.obtenerListaComponentes(this.equipoSeleccionado.getId(), Boolean.FALSE);
	}

	public Empleado getFiltroEmpleadoSeleccionado() {
		return filtroEmpleadoSeleccionado;
	}

	public void setFiltroEmpleadoSeleccionado(Empleado filtroEmpleadoSeleccionado) {
		this.filtroEmpleadoSeleccionado = filtroEmpleadoSeleccionado;
	}

	public List<Activos> getListaActivosTipoEquipo() {
		return listaActivosTipoEquipo;
	}

	public List<ComponentesActivos> getListaComponentesAsociados() {
		return listaComponentesAsociados;
	}
	
	public ComponentesActivos getComponenteAsociadoSeleccionado() {
		return componenteAsociadoSeleccionado;
	}

	public void setComponenteAsociadoSeleccionado(ComponentesActivos componenteAsociadoSeleccionado) {
		this.componenteAsociadoSeleccionado = componenteAsociadoSeleccionado;
	}

	public Activos getEquipoSeleccionado() {
		return equipoSeleccionado;
	}

	public void setEquipoSeleccionado(Activos equipoSeleccionado) {
		this.equipoSeleccionado = equipoSeleccionado;
	}

	public List<CaracteristicasHardware> getListaComponentesDisponibles() {
		return listaComponentesDisponibles;
	}

	public Integer getComponentePadreId() {
		return componentePadreId;
	}

	public void setComponentePadreId(Integer componentePadreId) {
		this.componentePadreId = componentePadreId;
	}

	public List<CaracteristicasHardware> getListaComponentesHijos() {
		return listaComponentesHijos;
	}

	public void setListaComponentesHijos(List<CaracteristicasHardware> listaComponentesHijos) {
		this.listaComponentesHijos = listaComponentesHijos;
	}

}
