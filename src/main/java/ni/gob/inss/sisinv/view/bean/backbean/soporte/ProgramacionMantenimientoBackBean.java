package ni.gob.inss.sisinv.view.bean.backbean.soporte;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.MantenimientosService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

@Named
@Scope("view")
public class ProgramacionMantenimientoBackBean extends BaseBackBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private Integer delegacionId;
	private List<Delegacion> listaDelegaciones;
	private List<ProgramacionMantenimiento> listaMtoProgramado;
    private Date fechaInicio;
    private Date fechaFin;
    private String asunto;
    
    private Date fechaIniciotxt;
    private Date fechaFintxt;
    private String asuntotxt;
   
    
	
	
	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	CatalogoExtService oCatalogoExtService;
	
	@Autowired
	MantenimientosService oMantenimientosService;
	
	@PostConstruct
	public void init(){
		this.cargarListaDelegaciones();
		eventModel = new DefaultScheduleModel();	
		limpiarVentanaMto();
		cargarMantenimientosProgramados();
	}


	public void cargarListaDelegaciones(){
		try {
				this.listaDelegaciones=oDelegacionService.buscarPorEstado("",false);								
			} catch (Exception e) {
            	mostrarMensajeError(this.getClass().getSimpleName(),"cargarListaDelegaciones()",MessagesResults.ERROR_OBTENER_LISTA, e);
			}
	}
	
	public void cargarMantenimientosProgramados(){
		this.listaMtoProgramado=oMantenimientosService.buscar();
		eventModel.clear();
		this.listaMtoProgramado.forEach(resultado->{
			this.getEventModel().addEvent(new DefaultScheduleEvent(resultado.getAsunto(), resultado.getFechaInicio(),resultado.getFechaFin()));
		});
	}
	
	public void limpiarVentanaMto(){
		this.setDelegacionId(null);
		this.setAsunto(null);
		this.setFechaInicio(null);
		this.setFechaFin(null);
	}
	
	public void guardar(){
		try{
			if(this.getDelegacionId()==null){
				mostrarMensajeError("Debe seleccionar una delegacion");
				RequestContext.getCurrentInstance().execute("PF('winProgMto').show()");
			}else{
				ProgramacionMantenimiento oProgramacionMantenimiento = new ProgramacionMantenimiento();
				oProgramacionMantenimiento.setDelegacion_Id(delegacionId);
				oProgramacionMantenimiento.setAsunto(asunto);
				oProgramacionMantenimiento.setFechaInicio(this.getFechaInicio());
				oProgramacionMantenimiento.setFechaFin(this.getFechaFin());
				oProgramacionMantenimiento.setEstadoMto(1000);
				oProgramacionMantenimiento.setPasivo(false);
				oProgramacionMantenimiento.setCreadoEl(this.getTimeNow());
				oProgramacionMantenimiento.setCreadoEnIp(this.getRemoteIp());
				oProgramacionMantenimiento.setCreadoPor(this.getUsuarioActual().getId());
				oProgramacionMantenimiento.setEntidadId(this.getEntidadActual().getId());
				
				oMantenimientosService.guardarProgramacion(oProgramacionMantenimiento);
				RequestContext.getCurrentInstance().execute("PF('winProgMto').hide()");
				mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
				limpiarVentanaMto();
				cargarMantenimientosProgramados();
			}
			
		} catch (DAOException e) {
			mostrarMensajeError(MessagesResults.ERROR_GUARDAR);
		}
	}
	
	
	public ScheduleModel getEventModel() {
		return eventModel;
	}


	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}


	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}
	
	public void onEventSelect(SelectEvent selectEvent) {
		 event = (ScheduleEvent) selectEvent.getObject();
		 this.setAsuntotxt(event.getTitle());
		 this.setFechaIniciotxt(event.getStartDate());
		 this.setFechaFintxt(event.getEndDate());
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
    	 this.setFechaInicio((Date) selectEvent.getObject());
    	 this.setFechaFin((Date) selectEvent.getObject());
    }
     
    
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


	public Integer getDelegacionId() {
		return delegacionId;
	}


	public void setDelegacionId(Integer delegacionId) {
		this.delegacionId = delegacionId;
	}


	public List<Delegacion> getListaDelegaciones() {
		return listaDelegaciones;
	}


	public void setListaDelegaciones(List<Delegacion> listaDelegaciones) {
		this.listaDelegaciones = listaDelegaciones;
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	public String getAsunto() {
		return asunto;
	}


	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}


	public List<ProgramacionMantenimiento> getListaMtoProgramado() {
		return listaMtoProgramado;
	}


	public void setListaMtoProgramado(List<ProgramacionMantenimiento> listaMtoProgramado) {
		this.listaMtoProgramado = listaMtoProgramado;
	}


	public Date getFechaIniciotxt() {
		return fechaIniciotxt;
	}


	public void setFechaIniciotxt(Date fechaIniciotxt) {
		this.fechaIniciotxt = fechaIniciotxt;
	}


	public Date getFechaFintxt() {
		return fechaFintxt;
	}


	public void setFechaFintxt(Date fechaFintxt) {
		this.fechaFintxt = fechaFintxt;
	}


	public String getAsuntotxt() {
		return asuntotxt;
	}


	public void setAsuntotxt(String asuntotxt) {
		this.asuntotxt = asuntotxt;
	}
	
	

}