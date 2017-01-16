package ni.gob.inss.sisinv.view.bean.backbean.soporte;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

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

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.MantenimientosService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.soporte.ActivosUsuario;
import ni.gob.inss.sisinv.model.entity.soporte.Mantenimientos;
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
	private List<Mantenimientos> listaMantenimientos;
	private List<ActivosUsuario> listaActivosUsuario;
	private ActivosUsuario equipoSeleccionado;
	private Empleado filtroEmpleadoSeleccionado; 
    private Date fechaInicio;
    private Date fechaFin;
    private String asunto;
    private Integer mantenimientoprogId;
    
    
    private Date fechaIniciotxt;
    private Date fechaFintxt;
    private String asuntotxt;
    private Integer estadoId=1000;
    
    private String txtEmpleado;
    private String txtcargoEmpleado;
    private String txtubicacionEmpleado;
    private String txtobservacion;
    private Integer HfEmpId;
    
    private Boolean ckLimpieza;
    private Boolean ckReemplazo;
    private Boolean ckDepuracion;
    private Boolean ckInstPrograma;
    private Boolean ckInstAntivirus;
    private Boolean ckActAntivirus;
    private Boolean ckEscAntivirus;
    private Boolean ckInstSOperativo;
    private Boolean ckBueno;
    private Boolean ckDanado;


	
	
	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	CatalogoExtService oCatalogoExtService;
	
	@Autowired
	MantenimientosService oMantenimientosService;
	
	@Autowired
	EmpleadoService oEmpleadoService;
	
	@PostConstruct
	public void init(){
		this.cargarListaDelegaciones();
		eventModel = new DefaultScheduleModel();	
		limpiarVentanaMto();
		cargarMantenimientosProgramados();
		cargarMantenimientosPorEstado();
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
	
	public void cargarMantenimientosPorEstado(){
		this.listaMantenimientos=oMantenimientosService.obtenerlistaMantenimientos(estadoId);
	}
	
	public void cargarFechasMto() throws EntityNotFoundException, ni.gob.inss.barista.model.dao.EntityNotFoundException{
		ProgramacionMantenimiento oMantenimiento = oMantenimientosService.obtener(mantenimientoprogId);
		this.setFechaInicio(oMantenimiento.getFechaInicio());
		this.setFechaFin(oMantenimiento.getFechaFin());
		RequestContext.getCurrentInstance().execute("PF('btnaddUser').enable();");
	}
	
	public void limpiarVentanaMto(){
		this.setDelegacionId(null);
		this.setAsunto(null);
		this.setFechaInicio(null);
		this.setFechaFin(null);
	}
	
	public void cargarDatosFiltro() throws EntityNotFoundException, ni.gob.inss.barista.model.dao.EntityNotFoundException{
		try {
			if(filtroEmpleadoSeleccionado==null) throw new BusinessException(MessagesResults.SELECCIONE_UN_REGISTRO);
				this.setTxtEmpleado(filtroEmpleadoSeleccionado.getNumeroEmpleado()+" - "+ filtroEmpleadoSeleccionado.getPrimerNombre()+ " "+filtroEmpleadoSeleccionado.getSegundoNombre()+ " "+filtroEmpleadoSeleccionado.getPrimerApellido()+" "+filtroEmpleadoSeleccionado.getSegundoApellido());
				this.setTxtcargoEmpleado(filtroEmpleadoSeleccionado.getCargo());
				this.setTxtubicacionEmpleado(filtroEmpleadoSeleccionado.getArea());
				Empleado oEmpleado = oEmpleadoService.obtener(filtroEmpleadoSeleccionado.getId());
				this.setHfEmpId(oEmpleado.getId()); 
				this.listaActivosUsuario = oMantenimientosService.obtenerListaActivosUsuarios(this.getHfEmpId());
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos", MessagesResults.ERROR_OBTENER, e);
		}catch(BusinessException e){
			mostrarMensajeError(e.getMessage());
		}
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

	public List<Mantenimientos> getListaMantenimientos() {
		return listaMantenimientos;
	}

	public void setListaMantenimientos(List<Mantenimientos> listaMantenimientos) {
		this.listaMantenimientos = listaMantenimientos;
	}

	public Integer getMantenimientoprogId() {
		return mantenimientoprogId;
	}

	public void setMantenimientoprogId(Integer mantenimientoprogId) {
		this.mantenimientoprogId = mantenimientoprogId;
	}

	public Empleado getFiltroEmpleadoSeleccionado() {
		return filtroEmpleadoSeleccionado;
	}

	public void setFiltroEmpleadoSeleccionado(Empleado filtroEmpleadoSeleccionado) {
		this.filtroEmpleadoSeleccionado = filtroEmpleadoSeleccionado;
	}

	public String getTxtEmpleado() {
		return txtEmpleado;
	}

	public void setTxtEmpleado(String txtEmpleado) {
		this.txtEmpleado = txtEmpleado;
	}

	public String getTxtcargoEmpleado() {
		return txtcargoEmpleado;
	}

	public void setTxtcargoEmpleado(String txtcargoEmpleado) {
		this.txtcargoEmpleado = txtcargoEmpleado;
	}

	public String getTxtubicacionEmpleado() {
		return txtubicacionEmpleado;
	}

	public void setTxtubicacionEmpleado(String txtubicacionEmpleado) {
		this.txtubicacionEmpleado = txtubicacionEmpleado;
	}

	public Integer getHfEmpId() {
		return HfEmpId;
	}

	public void setHfEmpId(Integer hfEmpId) {
		HfEmpId = hfEmpId;
	}

	public List<ActivosUsuario> getListaActivosUsuario() {
		return listaActivosUsuario;
	}

	public void setListaActivosUsuario(List<ActivosUsuario> listaActivosUsuario) {
		this.listaActivosUsuario = listaActivosUsuario;
	}

	public ActivosUsuario getEquipoSeleccionado() {
		return equipoSeleccionado;
	}

	public void setEquipoSeleccionado(ActivosUsuario equipoSeleccionado) {
		this.equipoSeleccionado = equipoSeleccionado;
	}

	public Boolean getCkLimpieza() {
		return ckLimpieza;
	}

	public void setCkLimpieza(Boolean ckLimpieza) {
		this.ckLimpieza = ckLimpieza;
	}

	public Boolean getCkReemplazo() {
		return ckReemplazo;
	}

	public void setCkReemplazo(Boolean ckReemplazo) {
		this.ckReemplazo = ckReemplazo;
	}

	public Boolean getCkDepuracion() {
		return ckDepuracion;
	}

	public void setCkDepuracion(Boolean ckDepuracion) {
		this.ckDepuracion = ckDepuracion;
	}

	public Boolean getCkInstPrograma() {
		return ckInstPrograma;
	}

	public void setCkInstPrograma(Boolean ckInstPrograma) {
		this.ckInstPrograma = ckInstPrograma;
	}

	public Boolean getCkInstAntivirus() {
		return ckInstAntivirus;
	}

	public void setCkInstAntivirus(Boolean ckInstAntivirus) {
		this.ckInstAntivirus = ckInstAntivirus;
	}

	public Boolean getCkActAntivirus() {
		return ckActAntivirus;
	}

	public void setCkActAntivirus(Boolean ckActAntivirus) {
		this.ckActAntivirus = ckActAntivirus;
	}

	public Boolean getCkEscAntivirus() {
		return ckEscAntivirus;
	}

	public void setCkEscAntivirus(Boolean ckEscAntivirus) {
		this.ckEscAntivirus = ckEscAntivirus;
	}

	public Boolean getCkInstSOperativo() {
		return ckInstSOperativo;
	}

	public void setCkInstSOperativo(Boolean ckInstSOperativo) {
		this.ckInstSOperativo = ckInstSOperativo;
	}

	public Boolean getCkBueno() {
		return ckBueno;
	}

	public void setCkBueno(Boolean ckBueno) {
		this.ckBueno = ckBueno;
	}

	public Boolean getCkDanado() {
		return ckDanado;
	}

	public void setCkDanado(Boolean ckDanado) {
		this.ckDanado = ckDanado;
	}

	public String getTxtobservacion() {
		return txtobservacion;
	}

	public void setTxtobservacion(String txtobservacion) {
		this.txtobservacion = txtobservacion;
	}

	

}
