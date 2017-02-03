package ni.gob.inss.sisinv.view.bean.backbean.soporte;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.MantenimientosService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.soporte.Mantenimientos;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

@Named
@Scope("view")
public class mantenimientoEquiposBackBean extends BaseBackBean{
	
	private static final long serialVersionUID = 1L;
	
	private List<ProgramacionMantenimiento> listaMantenimiento;
	private List<Mantenimientos> listaMantenimientos;
	private Mantenimientos selectedMantenimientos;
	private List<Delegacion> listaDelegaciones;
	
	private Integer mantenimientoprogId;
	private Integer cmdDelegacionId;
	
	private Date fechaInicio;
    private Date fechaFin;
	
	@Autowired
	MantenimientosService oMantenimientosService;
	
	@Autowired
	DelegacionService oDelegacionService;
	
	@PostConstruct
	public void init(){
		cargaListaMantenimiento();
		cargarListaDelegaciones();
	}
	
	private void cargaListaMantenimiento(){
		this.listaMantenimiento = oMantenimientosService.obtenerListaProgramacionMantenimientoActivo();
	}
	
	public void cargarMantenimientosPorEstado(){
		this.listaMantenimientos=oMantenimientosService.obtenerlistaMantenimientos(1000);
	}

	public void cargarListaDelegaciones(){
		try {
				this.listaDelegaciones=oDelegacionService.buscarPorEstado("",false);								
			} catch (Exception e) {
            	mostrarMensajeError(this.getClass().getSimpleName(),"cargarListaDelegaciones()",MessagesResults.ERROR_OBTENER_LISTA, e);
			}
	}
	
	public void abrirModalMantenimientos(){
		RequestContext.getCurrentInstance().execute("PF('modalMantenimientosProgramados').show()");
	}
	
	public void cerrarModalMantenimientos(){
		RequestContext.getCurrentInstance().execute("PF('modalMantenimientosProgramados').hide()");
	}
	
	public List<ProgramacionMantenimiento> getListaMantenimiento() {
		return listaMantenimiento;
	}

	public void setListaMantenimiento(List<ProgramacionMantenimiento> listaMantenimiento) {
		this.listaMantenimiento = listaMantenimiento;
	}

	public Integer getMantenimientoprogId() {
		return mantenimientoprogId;
	}

	public void setMantenimientoprogId(Integer mantenimientoprogId) {
		this.mantenimientoprogId = mantenimientoprogId;
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

	public List<Mantenimientos> getListaMantenimientos() {
		return listaMantenimientos;
	}

	public void setListaMantenimientos(List<Mantenimientos> listaMantenimientos) {
		this.listaMantenimientos = listaMantenimientos;
	}

	public Mantenimientos getSelectedMantenimientos() {
		return selectedMantenimientos;
	}

	public void setSelectedMantenimientos(Mantenimientos selectedMantenimientos) {
		this.selectedMantenimientos = selectedMantenimientos;
	}

	public Integer getCmdDelegacionId() {
		return cmdDelegacionId;
	}

	public void setCmdDelegacionId(Integer cmdDelegacionId) {
		this.cmdDelegacionId = cmdDelegacionId;
	}

	public List<Delegacion> getListaDelegaciones() {
		return listaDelegaciones;
	}

	public void setListaDelegaciones(List<Delegacion> listaDelegaciones) {
		this.listaDelegaciones = listaDelegaciones;
	}
	
	
}
