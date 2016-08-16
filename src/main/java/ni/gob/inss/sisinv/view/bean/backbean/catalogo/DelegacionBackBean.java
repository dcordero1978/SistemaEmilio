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

@Named
@Scope("view")
public class DelegacionBackBean extends BaseBackBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String txtBusquedaDelegacionByNombre;
	private String nombreDelegacion;
	private boolean nuevoRegistro;
	private Integer hfId;
	private List<Catalogo> listaDepartamentos;
	private TiposCatalogo catalogoDepartamento;
	private final static int CATALOGO_DEPARTAMENTO = 1;
	
	@Autowired
	TipoCatalogoService oTipoCatalogoService;
	
	@Autowired
	CatalogoService oCatalogoService;
	
	
	@PostConstruct
	public void init(){
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
	
	public void guardarNuevaDelegacion(){
		
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

}
