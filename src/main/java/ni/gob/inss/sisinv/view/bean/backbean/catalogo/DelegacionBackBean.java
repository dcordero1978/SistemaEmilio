package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.catalogos.CatalogoService;
import ni.gob.inss.barista.businesslogic.service.catalogos.TipoCatalogoService;
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
	
	private String nombreDelegacion;
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
		
	}
	
	public void crearDelegacion(){
	
	}
	
	public void editarDelegacion(){
	
	}
	
	public void guardarOrActualizar(){
		//TODO: POR EL MOMENTO SOLO TENEMOS EL METODO GUARDAR, ESTE METODO MANEJARA AMBOS GUARDAR Y ACTUALIZAR
		this.guardar();
	}
	
	public void limpiar(){
		this.setTxtBusquedaDelegacionByNombre("");
		this.setNuevoRegistro(true);
		this.setHfId(null);
		this.setNombreDelegacion("");
		this.setDepartamentoId(null);
		this.setPasivo(false);
	}
	
	public void guardar(){
		try {
			Delegacion oDelegacion = new Delegacion();
			//Por ser nueva Delegacion por defecto es activo.
			oDelegacion.setPasivo(false); 
			oDelegacion.setNombre(this.getNombreDelegacion());
			oDelegacion.setDepartamentoId(this.getDepartamentoId());
			oDelegacionService.agregar(oDelegacion);
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
		} catch (Exception e) {
            mostrarMensajeError(this.getClass().getSimpleName(),"guardarNuevaDelegacion",MessagesResults.ERROR_GUARDAR, e);

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

	public String getNombreDelegacion() {
		return nombreDelegacion;
	}

	public void setNombreDelegacion(String nombreDelegacion) {
		this.nombreDelegacion = nombreDelegacion;
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
