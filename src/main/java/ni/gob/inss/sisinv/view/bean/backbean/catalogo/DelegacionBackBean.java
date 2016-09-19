package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.catalogos.CatalogoService;
import ni.gob.inss.barista.businesslogic.service.catalogos.TipoCatalogoService;
import ni.gob.inss.barista.businesslogic.service.core.auditoria.AuditoriaService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.DelegacionService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;

@Named
@Scope("view")
public class DelegacionBackBean extends BaseBackBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private String txtBusquedaDelegacionByNombre;
	private boolean nuevoRegistro;
	private List<Catalogo> listaDepartamentos;
	private TiposCatalogo catalogoDepartamento;
	
	private List<Delegacion> listaDelegaciones;
	private Delegacion delegacionSeleccionada;
	
	private String descripcionDelegacion;
	private Integer hfId;
	private boolean pasivo;
	private Integer departamentoId;
	
	private final static int CATALOGO_DEPARTAMENTO = 1;
	
	@Autowired
	TipoCatalogoService oTipoCatalogoService;
	
	@Autowired
	CatalogoService oCatalogoService;
	
	@Autowired
	DelegacionService oDelegacionService;
	
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.cargarListaDepartamentos();
		this.buscarDelegacionByName();
	}
	
	public void cargarListaDepartamentos(){
		try {
				this.catalogoDepartamento = oTipoCatalogoService.obtener(CATALOGO_DEPARTAMENTO);				
				this.listaDepartamentos = oTipoCatalogoService.obtenerCatalogos(this.catalogoDepartamento); 								
			} catch (Exception e) {
            	mostrarMensajeError(this.getClass().getSimpleName(),"cargarListaDepartamentos(Catalogo oCatalogo)",MessagesResults.ERROR_OBTENER_LISTA, e);
			}
	}
	
	public void buscarDelegacionByName(){
		try{
			this.listaDelegaciones = oDelegacionService.buscar(this.txtBusquedaDelegacionByNombre);
			if(this.listaDelegaciones.isEmpty()){
				mostrarMensajeInfo("No se han encontrado resultados con el criterio de Búsqueda ingresada.");
			}
		}catch(Exception e){
			mostrarMensajeError(this.getClass().getSimpleName(), "buscarDelegacionByName", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
		
	}
	
	public void editar(){
		if(this.getDelegacionSeleccionada()!=null){
			this.cargarDatosDelegacion(this.getDelegacionSeleccionada().getId());
		}else{
			mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
		}		
	}
	
	public void actualizar(){
		try {
			Delegacion oDelegacion = new Delegacion();
			
			oDelegacion = oDelegacionService.obtener(this.getHfId());
			oDelegacion.setDescripcion(this.getDescripcionDelegacion());
			oDelegacion.setPasivo(this.isPasivo());
			oDelegacion.setModificadoEl(this.getTimeNow());
			oDelegacion.setModificadoPor(this.getUsuarioActual().getId());
			oDelegacion.setModificadoEnIp(this.getRemoteIp());
			
			oDelegacionService.actualizar(oDelegacion);
			mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
			
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "actualizar", MessagesResults.ERROR_MODIFICAR, e);
		}
	}
	
	public void guardarOrActualizar(){
		if(this.getHfId()==null){
			this.guardar();
		}else{
			this.actualizar();
		}		
		this.cargarDatosDelegacion(this.getHfId());
		this.setTxtBusquedaDelegacionByNombre("");
		this.buscarDelegacionByName();
	}
	
	public void limpiar(){
		this.setTxtBusquedaDelegacionByNombre("");
		this.setNuevoRegistro(true);
		this.setHfId(null);
		this.setDescripcionDelegacion("");
		this.setDepartamentoId(null);
		this.setPasivo(false);
		this.setDelegacionSeleccionada(null);		
	}
	
	public void guardar(){
		try {
			Delegacion oDelegacion = new Delegacion();
			//Por ser nueva Delegacion por defecto es activo.			 
			oDelegacion.setDescripcion(this.getDescripcionDelegacion());
			oDelegacion.setDepartamentoId(this.getDepartamentoId());
			oDelegacion.setCreadoPor(this.getUsuarioActual().getId());
			oDelegacion.setCreadoEl(this.getTimeNow());
			oDelegacion.setCreadoEnIp(this.getRemoteIp());
			oDelegacion.setPasivo(false);
			oDelegacionService.agregar(oDelegacion);
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);	
		
			this.setHfId(oDelegacion.getId());
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(),"guardarNuevaDelegacion",MessagesResults.ERROR_GUARDAR, e);

		} 
	}
	
	public void cargarDatosDelegacion(Integer delegacionId){
		try {
			Delegacion oDelegacion = oDelegacionService.obtener(delegacionId);
			
			this.setDescripcionDelegacion(oDelegacion.getDescripcion());
			this.setDepartamentoId(oDelegacion.getDepartamentoId());
			this.setHfId(oDelegacion.getId());
			this.setPasivo(oDelegacion.getPasivo());
			this.setNuevoRegistro(false);
			
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "obtenerDelegacion", MessagesResults.ERROR_OBTENER, e);
		}
	}
	
	

	public List<Delegacion> getListaDelegaciones() {
		return listaDelegaciones;
	}

	public void setListaDelegaciones(List<Delegacion> listaDelegaciones) {
		this.listaDelegaciones = listaDelegaciones;
	}

	
	public Delegacion getDelegacionSeleccionada() {
		return delegacionSeleccionada;
	}

	public void setDelegacionSeleccionada(Delegacion delegacionSeleccionada) {
		this.delegacionSeleccionada = delegacionSeleccionada;
	}

	public String getDescripcionDelegacion() {
		return descripcionDelegacion;
	}

	public void setDescripcionDelegacion(String descripcionDelegacion) {
		this.descripcionDelegacion = descripcionDelegacion;
	}

	public String getTxtBusquedaDelegacionByNombre() {
		return txtBusquedaDelegacionByNombre;
	}

	public void setTxtBusquedaDelegacionByNombre(String txtBusquedaDelegacionByNombre) {
		this.txtBusquedaDelegacionByNombre = txtBusquedaDelegacionByNombre;
	}

	public boolean isNuevoRegistro() {
		return nuevoRegistro;
	}

	public void setNuevoRegistro(boolean nuevoRegistro) {
		this.nuevoRegistro = nuevoRegistro;
	}

	public Integer getHfId() {
		return hfId;
	}

	public void setHfId(Integer hfId) {
		this.hfId = hfId;
	}

	public List<Catalogo> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public boolean isPasivo() {
		return pasivo;
	}

	public void setPasivo(boolean pasivo) {
		this.pasivo = pasivo;
	}

	public Integer getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(Integer departamentoId) {
		this.departamentoId = departamentoId;
	}				
	
	
}
