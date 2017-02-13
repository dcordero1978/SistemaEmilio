package ni.gob.inss.sisinv.view.bean.backbean.soporte;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.MantenimientosService;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.consultaEquiposPorMantenimientoService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.soporte.Mantenimientos;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

@Named
@Scope("view")
public class consultaEquiposPorMantenimientoBackBean extends BaseBackBean{
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private List<ProgramacionMantenimiento> listaMantenimiento;
	private List<Mantenimientos> listaMantenimientos;
	private List<Map<String, Object>> listaMantenimientosRealizados;
	private Mantenimientos selectedMantenimientos;
	private Map<String, Object> selectedMantenimientosRealizados;
	private List<Delegacion> listaDelegaciones;
	private List<Catalogo> listaTipoMantenimiento;
	
	private Integer mantenimientoprogId;
	private Integer cmdDelegacionId;
	private Integer cmdTipoMantenimiento;
	private Integer cmbFiltroFechas;
	
	private Date fechaIni;
    private Date fechaFin;
    
	@Autowired
	MantenimientosService oMantenimientosService;
	
	@Autowired
	consultaEquiposPorMantenimientoService oConsultaEquiposPorMantenimientoService;
	
	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	CatalogoExtService oCatalogoService;
	
	@PostConstruct
	public void init(){
		//cargaListaMantenimiento();
		cargarListaDelegaciones();
		cargarListaTipoMantenimiento();
		//cargaListaMantenimientoRealizados();
		
	}
	
	private void cargaListaMantenimiento(){
		this.listaMantenimiento = oMantenimientosService.obtenerListaProgramacionMantenimientoActivo();
	}
	
	private void cargaListaMantenimientoRealizados(){
		
		this.listaMantenimientosRealizados = oConsultaEquiposPorMantenimientoService.buscarMantenimiento(null, null, null, null, null, null);
		this.listaMantenimientosRealizados.clear();
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
	
	public void cargarListaTipoMantenimiento(){
		try {
				this.listaTipoMantenimiento=oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo("TME");								
			} catch (Exception e) {
            	mostrarMensajeError(this.getClass().getSimpleName(),"cargarListaDelegaciones()",MessagesResults.ERROR_OBTENER_LISTA, e);
			}
	}
	
	public void buscarMantenimientosRealizados(){
		switch (cmbFiltroFechas)
		{
			case 1: this.listaMantenimientosRealizados = oConsultaEquiposPorMantenimientoService.buscarMantenimiento(cmdTipoMantenimiento, cmdDelegacionId, fechaIni, fechaFin, null, null);
			case 2: this.listaMantenimientosRealizados = oConsultaEquiposPorMantenimientoService.buscarMantenimiento(cmdTipoMantenimiento, cmdDelegacionId, null, null, fechaIni, fechaFin);
			default : this.listaMantenimientosRealizados = oConsultaEquiposPorMantenimientoService.buscarMantenimiento(cmdTipoMantenimiento, cmdDelegacionId, null, null, null, null);
		}
	}
	public void abrirModalMantenimientos(){
		cmdTipoMantenimiento = null;
		cmdDelegacionId = null;
		fechaIni = null;
		fechaFin = null;
		listaMantenimientosRealizados = null;
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

	public List<Map<String, Object>> getListaMantenimientosRealizados() {
		return listaMantenimientosRealizados;
	}

	public void setListaMantenimientosRealizados(List<Map<String, Object>> listaMantenimientosRealizados) {
		this.listaMantenimientosRealizados = listaMantenimientosRealizados;
	}

	public Map<String, Object> getSelectedMantenimientosRealizados() {
		return selectedMantenimientosRealizados;
	}

	public void setSelectedMantenimientosRealizados(Map<String, Object> selectedMantenimientosRealizados) {
		this.selectedMantenimientosRealizados = selectedMantenimientosRealizados;
	}

	public List<Catalogo> getListaTipoMantenimiento() {
		return listaTipoMantenimiento;
	}

	public void setListaTipoMantenimiento(List<Catalogo> listaTipoMantenimiento) {
		this.listaTipoMantenimiento = listaTipoMantenimiento;
	}

	public Integer getCmdTipoMantenimiento() {
		return cmdTipoMantenimiento;
	}

	public void setCmdTipoMantenimiento(Integer cmdTipoMantenimiento) {
		this.cmdTipoMantenimiento = cmdTipoMantenimiento;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getCmbFiltroFechas() {
		return cmbFiltroFechas;
	}

	public void setCmbFiltroFechas(Integer cmbFiltroFechas) {
		this.cmbFiltroFechas = cmbFiltroFechas;
	}

	
}
