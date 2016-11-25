package ni.gob.inss.sisinv.view.bean.backbean.soporte;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;

public class SoporteActivosBackBean extends BaseBackBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer HFActivoId;
	
	@Autowired
	ActivoService oActivoService;
	
	
	@PostConstruct
	public void init(){
		
	}

	public ActivoService getoActivoService() {
		return oActivoService;
	}

	public void setoActivoService(ActivoService oActivoService) {
		this.oActivoService = oActivoService;
	}

	public Integer getHFActivoId() {
		return HFActivoId;
	}

	public void setHFActivoId(Integer hFActivoId) {
		HFActivoId = hFActivoId;
	}


}
