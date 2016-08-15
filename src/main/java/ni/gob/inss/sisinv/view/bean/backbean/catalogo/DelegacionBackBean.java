package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;

@Named
@Scope("view")
public class DelegacionBackBean extends BaseBackBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String txtBusquedaDelegacionByNombre;
	private String nombreDelegacion;
	
	@PostConstruct
	public void init(){
		
	}
	
	public void buscarDelegacionByName(){
		
	}
	
	public void crearDelegacion(){
	
	}
	
	public void editarDelegacion(){
	
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
	
	

}
