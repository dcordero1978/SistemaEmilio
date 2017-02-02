package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.core.jasperclient.JasperRestService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.inventario.MovimientosService;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.model.entity.inventario.Movimientos;

@Named
@Scope("view")
public class HistorialActivosBackBean extends BaseBackBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String txtcodigoInventario;
	private String txtcodigoSecundario;
	private String txtdescripcionBien;
	private String txtSerie;
	private Integer hfActivoId;
	private Activos filtroActivoSeleccionado;
	private List<Movimientos> listaMovimientos;
	
	@Autowired
	JasperRestService oJasperReportService;
	
	@Autowired
	MovimientosService oMovimientosService;
	
	
	public void init(){
		RequestContext.getCurrentInstance().execute("PF('btnReporte').disable()");
	}
	
	
	//
	public void cancelar(){
		this.setTxtcodigoInventario("");
		this.setTxtcodigoSecundario("");
		this.setTxtdescripcionBien("");
		this.setTxtSerie("");
		RequestContext.getCurrentInstance().execute("PF('btnReporte').disable()");
		this.setListaMovimientos(null);
		
	}
	public void cargarDatosFiltroActivo(){
		RequestContext.getCurrentInstance().execute("PF('btnReporte').enable()");
		this.setHfActivoId(filtroActivoSeleccionado.getId());
        this.setTxtcodigoInventario(filtroActivoSeleccionado.getCodigoInventario());
        this.setTxtcodigoSecundario(filtroActivoSeleccionado.getCodigoSecundario());
        this.setTxtdescripcionBien(filtroActivoSeleccionado.getDescripcion()+ ", Marca: "+filtroActivoSeleccionado.marca.descripcion+", Modelo: "+filtroActivoSeleccionado.modelo.descripcion);
        this.setTxtSerie(filtroActivoSeleccionado.getSerie());
        cargarMovimientosActivos(filtroActivoSeleccionado.getId());
        RequestContext.getCurrentInstance().execute("PF('btnReporte').enable()");
        
    }

	public void cargarMovimientosActivos(Integer activoId){
		try{
			this.setListaMovimientos(oMovimientosService.buscar(activoId));
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos", MessagesResults.ERROR_OBTENER, e);
		}
	}
	
	public void imprimeReporteListadoMovimientos(){
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("psEntidad",this.getEntidadActual().getId().toString());
		parametros.put("psActivoId",this.getHfActivoId().toString());
		parametros.put("psUsuario",this.getUsuarioActual().getUsername().toString());
		
		
		//TODO: ESTE ES EL ID DEL REPORTE DEL CUAL SE OBTIENE EL ENCABEZADO. PENDIENTE MEJORAR
		parametros.put("IdReporte","5");
		try {
			oJasperReportService.getReport("/reports/reports/Inventario/rpt_movimientos_activos", "pdf", parametros, "HistoralMovimientosActivos");
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "imprimeReporteListadoMovimientos", "OCURRIO UN ERROR AL GENERAR EL REPORTE", e);
		}
	}

	public String getTxtcodigoInventario() {
		return txtcodigoInventario;
	}


	public void setTxtcodigoInventario(String txtcodigoInventario) {
		this.txtcodigoInventario = txtcodigoInventario;
	}


	public String getTxtcodigoSecundario() {
		return txtcodigoSecundario;
	}


	public void setTxtcodigoSecundario(String txtcodigoSecundario) {
		this.txtcodigoSecundario = txtcodigoSecundario;
	}


	public String getTxtdescripcionBien() {
		return txtdescripcionBien;
	}


	public void setTxtdescripcionBien(String txtdescripcionBien) {
		this.txtdescripcionBien = txtdescripcionBien;
	}


	public Activos getFiltroActivoSeleccionado() {
		return filtroActivoSeleccionado;
	}


	public void setFiltroActivoSeleccionado(Activos filtroActivoSeleccionado) {
		this.filtroActivoSeleccionado = filtroActivoSeleccionado;
	}

	public Integer getHfActivoId() {
		return hfActivoId;
	}

	public void setHfActivoId(Integer hfActivoId) {
		this.hfActivoId = hfActivoId;
	}

	public List<Movimientos> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<Movimientos> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}


	public String getTxtSerie() {
		return txtSerie;
	}


	public void setTxtSerie(String txtSerie) {
		this.txtSerie = txtSerie;
	}
	
	

}
