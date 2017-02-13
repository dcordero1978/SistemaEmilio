package ni.gob.inss.sisinv.view.bean.backbean.soporte;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.MantenimientosService;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

@Named
@Scope("view")
public class mantenimientoActivosBackBean extends BaseBackBean{
	
	private static final long serialVersionUID = 1L;
	
	private List<ProgramacionMantenimiento> listaMantenimiento;
	
	@Autowired
	MantenimientosService oMantenimientosService;
	
	@PostConstruct
	public void init(){
		cargaListaMantenimiento();	
	}
	
	private void cargaListaMantenimiento(){
		this.listaMantenimiento = oMantenimientosService.obtenerListaProgramacionMantenimientoActivo();
	}

	public List<ProgramacionMantenimiento> getListaMantenimiento() {
		return listaMantenimiento;
	}

	public void setListaMantenimiento(List<ProgramacionMantenimiento> listaMantenimiento) {
		this.listaMantenimiento = listaMantenimiento;
	}

}
