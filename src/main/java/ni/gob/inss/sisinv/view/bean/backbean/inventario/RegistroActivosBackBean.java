package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Named
@Scope("view")
public class RegistroActivosBackBean extends BaseBackBean {
	
	private List<Empleado> listaEmpleados;
	private String busquedaEmpleado;
	private Empleado empleadoSeleccionado;
	
	@Autowired
	EmpleadoService oEmpleadoService;
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.cargarListaEmpleados();
	}
	
	public void limpiar(){
		this.setEmpleadoSeleccionado(null);
		this.setBusquedaEmpleado("");
	}
	
	public void cargarListaEmpleados(){
		try {
			this.setListaEmpleados(oEmpleadoService.buscar(this.getBusquedaEmpleado(), null));
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaEmpleados", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}

	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public String getBusquedaEmpleado() {
		return busquedaEmpleado;
	}

	public void setBusquedaEmpleado(String busquedaEmpleado) {
		this.busquedaEmpleado = busquedaEmpleado;
	}

	public Empleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}

	public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}	
	
}
