package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;

@Named
@Scope("view")
public class HistorialActivosBackBean extends BaseBackBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@PostConstruct
	public void init(){
		
	}

	
	public void limpiar(){
		
	}
	
	
	}
