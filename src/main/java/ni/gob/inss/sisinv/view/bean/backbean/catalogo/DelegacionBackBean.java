package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.catalogos.CatalogoService;
import ni.gob.inss.barista.businesslogic.service.catalogos.TipoCatalogoService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;

@Named
@Scope("view")
public class DelegacionBackBean extends BaseBackBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String txtBusquedaDelegacionByNombre;
	private String nombreDelegacion;
	private boolean nuevoRegistro;
	private Integer hfId;
	private List<TiposCatalogo> listaDepartamentos;
	private Catalogo catalogoDepartamento;
	private final static int CATALOGO_DEPARTAMENTO = 1;
	
	@Autowired
	TipoCatalogoService oTipoCatalogoService;
	
	@Autowired
	CatalogoService oCatalogoService;
	
	@PostConstruct
	public void init(){
		cargarListaDepartamentos();
	}
	
	
	public void cargarListaDepartamentos(){
		try {
				catalogoDepartamento =  oCatalogoService.obtener(CATALOGO_DEPARTAMENTO);
				if(catalogoDepartamento==null){
					throw new Exception("Ocurrio un error al cargar la lista de Departamentos");					
				}else{
					this.setListaDepartamentos(oCatalogoService.obtenerTipoCatalogo(catalogoDepartamento));
				}				
			} catch (Exception e) {
				mostrarMensajeError(e.getMessage());
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

	public List<TiposCatalogo> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<TiposCatalogo> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public Catalogo getCatalogoDepartamento() {
		return catalogoDepartamento;
	}

	public void setCatalogoDepartamento(Catalogo catalogoDepartamento) {
		this.catalogoDepartamento = catalogoDepartamento;
	}
	
		
			

}
