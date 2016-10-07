package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.util.RegExpresionExtends;

@Named
@Scope("view")
                 
public class ConsultaActivosBackBean extends BaseBackBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Delegacion> listaDelegaciones;
	private Integer delegacionId;
	private String txtBusquedaActivoByCodigo;
	private String txtBusquedaActivoByDescripcion;
	private List<Activos> listaActivos;
	private String regExpSoloNumeros;

	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	ActivoService oActivoService;
	
	
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.cargarListaDelegaciones();
		this.buscarActivo();
		this.setRegExpSoloNumeros(RegExpresionExtends.regExpSoloNumeros);
	} 
	
	public void limpiar(){
		this.setTxtBusquedaActivoByCodigo(null);
		this.setTxtBusquedaActivoByDescripcion(null);
		this.setDelegacionId(null);
		
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
			this.listaActivos = oActivoService.buscar( this.getDelegacionId(), this.txtBusquedaActivoByCodigo, this.txtBusquedaActivoByDescripcion);
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

	public String getRegExpSoloNumeros() {
		return regExpSoloNumeros;
	}

	public void setRegExpSoloNumeros(String regExpSoloNumeros) {
		this.regExpSoloNumeros = regExpSoloNumeros;
	}
	

}
