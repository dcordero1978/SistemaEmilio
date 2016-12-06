package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CaracteristicasHardwareService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.TipoActivoCaraceteristicaHardwareService;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;
import ni.gob.inss.sisinv.model.entity.catalogo.TipoActivoCaracteristicasHardware;
import ni.gob.inss.sisinv.util.CatalogoGeneral;

@Scope("view")
@Named
public class EquipoInformaticoBackBean extends BaseBackBean implements Serializable {
	private List<Catalogo> listaTipoEquipoInformatico;
	private Catalogo equipoInformaticoSeleccionado;
	private DualListModel<CaracteristicasHardware> listaCaracteristicasHardware = new DualListModel<CaracteristicasHardware>();
	private List<CaracteristicasHardware> listaHardwareDisponible = new ArrayList<CaracteristicasHardware>();
	private List<CaracteristicasHardware> listaHardwareAgregado = new ArrayList<CaracteristicasHardware>();
	private Catalogo oTipoActivo;
	private static final long serialVersionUID = 1L;
	
	@Autowired
	CatalogoExtService oCatalogoService;
	@Autowired
	CaracteristicasHardwareService oCaracteristicasHardwareService;
	@Autowired
	TipoActivoCaraceteristicaHardwareService oTipoActivoCaracteristicaHardwareService;
	
	@PostConstruct
	public void init(){
		oTipoActivo = new Catalogo();
		this.cargarListas();
	}
	
	public void cargarCaracteristicas(){
		this.listaHardwareDisponible = oCaracteristicasHardwareService.obtieneListaCaracteristicasHardwareDisponiblePorTipoActivoId(oTipoActivo.getId());
		this.listaHardwareAgregado = oCaracteristicasHardwareService.obtieneListaCaracteristicasHardwareAsociadoActivo(oTipoActivo.getId());
		this.listaCaracteristicasHardware = new DualListModel<>(listaHardwareDisponible, listaHardwareAgregado);
	}
	
	public void editar(){
		if(this.equipoInformaticoSeleccionado==null){
			mostrarMensajeInfo(MessagesResults.SELECCIONE_UN_REGISTRO);
		}else{
			oTipoActivo = equipoInformaticoSeleccionado;
			cargarCaracteristicas();
		}
	}
	
	public void limpiar(){
		this.oTipoActivo = new Catalogo();
		this.listaHardwareAgregado.clear();
		this.listaHardwareDisponible.clear();
	}
	
	public void guardar(TransferEvent event){
		if(event.isAdd()){ //TODO: AGREGAR ELEMENTO
			event.getItems().stream().forEach(caracteristica -> {
				 Integer caracteristicaId = Integer.valueOf(caracteristica.toString());
				 TipoActivoCaracteristicasHardware oAsociacion =  oTipoActivoCaracteristicaHardwareService
						 .obtieneTipoActivoCaracteristicaHardware(oTipoActivo.getId(),caracteristicaId );
				 if(oAsociacion == null){
					 try {
						 oAsociacion = new TipoActivoCaracteristicasHardware();
						 oAsociacion.setTipoActivoId(oTipoActivo.getId());
						 oAsociacion.setCreadoEl(this.getTimeNow());
						 oAsociacion.setCreadoEnIp(this.getRemoteIp());
						 oAsociacion.setCreadoPor(this.getUsuarioActual().getId());
						 oAsociacion.setCaracteristicaPadreId(caracteristicaId);
						 oAsociacion.setPasivo(Boolean.FALSE);
						 oCaracteristicasHardwareService.obtieneCaracteritisticaHardwarePorId(caracteristicaId);
						oTipoActivoCaracteristicaHardwareService.guardar(oAsociacion);
					} catch (DAOException e) {
						mostrarMensajeError(MessagesResults.ERROR_GUARDAR);
					}
				 }else{
					 if(Boolean.TRUE.equals(oAsociacion.getPasivo())){
						 oAsociacion.setPasivo(Boolean.FALSE);
						 oAsociacion.setModificadoEl(this.getTimeNow());
						 oAsociacion.setModificadoEnIp(this.getRemoteIp());
						 oAsociacion.setModificadoPor(this.getUsuarioActual().getId());
						 try {
							oTipoActivoCaracteristicaHardwareService.actualizar(oAsociacion);
						} catch (DAOException e) {
							mostrarMensajeError(MessagesResults.ERROR_MODIFICAR);
						}
					 } 
				 }
				 mostrarMensajeInfo("SE HA GUARDADO CORRECTAMENTE EL REGISTRO");
			});
		}else{ //TODO: REMOVER ELEMENTO
			event.getItems().stream().forEach(caracteristica -> {
				 Integer caracteristicaId = Integer.valueOf(caracteristica.toString());
				 TipoActivoCaracteristicasHardware oAsociacion =  oTipoActivoCaracteristicaHardwareService
						 .obtieneTipoActivoCaracteristicaHardware(oTipoActivo.getId(),caracteristicaId);
				 if(Boolean.FALSE.equals(oAsociacion.getPasivo())){
					 oAsociacion.setPasivo(Boolean.TRUE);
					 oAsociacion.setModificadoEl(this.getTimeNow());
					 oAsociacion.setModificadoEnIp(this.getRemoteIp());
					 oAsociacion.setModificadoPor(this.getUsuarioActual().getId());
					 try {
						oTipoActivoCaracteristicaHardwareService.actualizar(oAsociacion);
						mostrarMensajeInfo("SE HA REMOVIDO CORRECTAMENTE EL REGISTRO");
					} catch (DAOException e) {
						mostrarMensajeError(MessagesResults.ERROR_MODIFICAR);
					}
				 }
			});
			
		}
	}
	
	public void cargarListas(){
		try {
		 listaTipoEquipoInformatico = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.TIPO_ACTIVO.getCodigoCatalogo());
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(MessagesResults.ERROR_OBTENER_LISTA);
			e.printStackTrace();
		}
	}

	public List<Catalogo> getListaTipoEquipoInformatico() {
		return listaTipoEquipoInformatico;
	}

	public void setListaTipoEquipoInformatico(List<Catalogo> listaTipoEquipoInformatico) {
		this.listaTipoEquipoInformatico = listaTipoEquipoInformatico;
	}

	public Catalogo getEquipoInformaticoSeleccionado() {
		return equipoInformaticoSeleccionado;
	}

	public void setEquipoInformaticoSeleccionado(Catalogo equipoInformaticoSeleccionado) {
		this.equipoInformaticoSeleccionado = equipoInformaticoSeleccionado;
	}

	public DualListModel<CaracteristicasHardware> getListaCaracteristicasHardware() {
		return listaCaracteristicasHardware;
	}

	public void setListaCaracteristicasHardware(DualListModel<CaracteristicasHardware> listaCaracteristicasHardware) {
		this.listaCaracteristicasHardware = listaCaracteristicasHardware;
	}

	public Catalogo getoTipoActivo() {
		return oTipoActivo;
	}

	public void setoTipoActivo(Catalogo oTipoActivo) {
		this.oTipoActivo = oTipoActivo;
	}

}
