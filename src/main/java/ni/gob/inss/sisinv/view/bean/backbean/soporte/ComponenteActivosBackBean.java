package ni.gob.inss.sisinv.view.bean.backbean.soporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

@Named
@Scope("view")
public class ComponenteActivosBackBean extends BaseBackBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 private Empleado filtroEmpleadoSeleccionado;
	 private List<Activos> listaActivosTipoEquipo = new ArrayList<Activos>();
	
	@Autowired
	ActivoService oActivoService;
	 
	@PostConstruct
	public void init(){
	}
	
	public void cargarDatosFiltro(){
		this.listaActivosTipoEquipo =  oActivoService.obtenerListaDeActivosClasificadosComoEquiposInformaticos(filtroEmpleadoSeleccionado.getId(), false);
    }

	public Empleado getFiltroEmpleadoSeleccionado() {
		return filtroEmpleadoSeleccionado;
	}

	public void setFiltroEmpleadoSeleccionado(Empleado filtroEmpleadoSeleccionado) {
		this.filtroEmpleadoSeleccionado = filtroEmpleadoSeleccionado;
	}

	public List<Activos> getListaActivosTipoEquipo() {
		return listaActivosTipoEquipo;
	}

}
