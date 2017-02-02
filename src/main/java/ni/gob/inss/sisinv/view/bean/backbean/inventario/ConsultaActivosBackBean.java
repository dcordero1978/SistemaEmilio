package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.core.jasperclient.JasperRestService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.util.CatalogoGeneral;

@Named
@Scope("view")
                 
public class ConsultaActivosBackBean extends BaseBackBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Delegacion> listaDelegaciones;
	private List<Catalogo> listaEstadoFisico;
	private List<Catalogo> listaProyectos;
	private Integer delegacionId;
	private Integer estadoFisicoId;
	private Integer proyectoId;
	private String txtBusquedaActivoByCodigo;
	private String txtBusquedaActivoByDescripcion;
	private List<Activos> listaActivos;

	

	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	ActivoService oActivoService;
	
	@Autowired
	CatalogoExtService oCatalogoService;
	
	@Autowired
	JasperRestService oJasperReportService;
	
	@PostConstruct
	public void init(){
		RequestContext.getCurrentInstance().execute("PF('btnReporte').disable()");
		this.cargarListaDelegaciones();
		this.cargarListas();
	} 
	

	public void cargarListas(){
		try {
			this.listaEstadoFisico = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.ESTADO_FISICO.getCodigoCatalogo());	
			this.listaProyectos = oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.PROYECTOS.getCodigoCatalogo());
		} catch (EntityNotFoundException  e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListas", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}

	public void imprimeReporteListadoActivos(){
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("psEntidad",this.getEntidadActual().getId().toString());
		parametros.put("psUsuario",this.getUsuarioActual().getUsername());
		parametros.put("psDelegacionId",StringUtils.defaultIfBlank(this.getDelegacionId() == null ? null : this.getDelegacionId().toString(), "0"));
		parametros.put("psEstadoFisicoId",StringUtils.defaultIfBlank(this.getEstadoFisicoId() == null ? null : this.getEstadoFisicoId().toString() , "0"));
		parametros.put("psProyectoId",StringUtils.defaultIfBlank(this.getProyectoId() == null ? null : this.getProyectoId().toString(), "0"));
		parametros.put("psCodigo",StringUtils.defaultIfBlank(this.getTxtBusquedaActivoByCodigo(), ""));
		parametros.put("psDescripcion",StringUtils.defaultIfBlank(this.getTxtBusquedaActivoByDescripcion(), ""));
		
		//TODO: ESTE ES EL ID DEL REPORTE DEL CUAL SE OBTIENE EL ENCABEZADO. PENDIENTE MEJORAR
		parametros.put("IdReporte","4");
		
		try {
			oJasperReportService.getReport("/reports/reports/Inventario/listado_consulta_bienes", "pdf", parametros, "ListadoConsultaBienes");
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "imprimeReporteListadoActivos", "OCURRIO UN ERROR AL GENERAR EL REPORTE", e);
		}
	}
	
	public void limpiar(){
		this.setTxtBusquedaActivoByCodigo(null);
		this.setTxtBusquedaActivoByDescripcion(null);
		this.setDelegacionId(null);
		this.setEstadoFisicoId(null);
		this.setProyectoId(null);
		this.setListaActivos(null);
		RequestContext.getCurrentInstance().execute("PF('btnReporte').disable()");
	}
	
	public void cargarListaDelegaciones(){
		try {
				this.listaDelegaciones=oDelegacionService.buscarPorEstado("",false);								
			} catch (Exception e) {
            	mostrarMensajeError(this.getClass().getSimpleName(),"cargarListaDelegaciones()",MessagesResults.ERROR_OBTENER_LISTA, e);
			}
	}
	
	public void buscarActivo(){
		try{ 
			this.listaActivos = oActivoService.buscar( this.getDelegacionId(), this.txtBusquedaActivoByCodigo, this.txtBusquedaActivoByDescripcion,this.estadoFisicoId, this.proyectoId);
			RequestContext.getCurrentInstance().execute("PF('btnReporte').enable()");
			if(this.listaActivos.isEmpty()){
				mostrarMensajeInfo("No se han encontrado resultados con el criterio de Búsqueda ingresada.");
			}
		}catch(Exception e){
			mostrarMensajeError(this.getClass().getSimpleName(), "buscarActivo", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
		
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

	public String getTxtBusquedaActivoByCodigo() {
		return txtBusquedaActivoByCodigo;
	}

	public void setTxtBusquedaActivoByCodigo(String txtBusquedaActivoByCodigo) {
		this.txtBusquedaActivoByCodigo = txtBusquedaActivoByCodigo;
	}

	public String getTxtBusquedaActivoByDescripcion() {
		return txtBusquedaActivoByDescripcion;
	}

	public void setTxtBusquedaActivoByDescripcion(String txtBusquedaActivoByDescripcion) {
		this.txtBusquedaActivoByDescripcion = txtBusquedaActivoByDescripcion;
	}

	public List<Activos> getListaActivos() {
		return listaActivos;
	}

	public void setListaActivos(List<Activos> listaActivos) {
		this.listaActivos = listaActivos;
	}

	public Integer getEstadoFisicoId() {
		return estadoFisicoId;
	}

	public void setEstadoFisicoId(Integer estadoFisicoId) {
		this.estadoFisicoId = estadoFisicoId;
	}

	public List<Catalogo> getListaEstadoFisico() {
		return listaEstadoFisico;
	}

	public void setListaEstadoFisico(List<Catalogo> listaEstadoFisico) {
		this.listaEstadoFisico = listaEstadoFisico;
	}


	public List<Catalogo> getListaProyectos() {
		return listaProyectos;
	}


	public void setListaProyectos(List<Catalogo> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}


	public Integer getProyectoId() {
		return proyectoId;
	}


	public void setProyectoId(Integer proyectoId) {
		this.proyectoId = proyectoId;
	}

}
