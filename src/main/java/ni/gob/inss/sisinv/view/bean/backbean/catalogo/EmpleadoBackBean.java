package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.EmpleadoService;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Named
@Scope("view")
public class EmpleadoBackBean extends BaseBackBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nombres;
	private String apellidos;	
	private String txtBusquedaEmpleado;
	private Integer hfId;
	
	private List<Empleado> listaEmpleados;
	private Empleado empleadoSeleccionado;
	
	@Autowired
	private EmpleadoService oEmpleado;
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.buscar();
		
	}
	
	public void limpiar(){
		this.setNombres("");
		this.setApellidos("");
		this.setTxtBusquedaEmpleado("");
		this.setEmpleadoSeleccionado(null);		
	}
	
	public void buscar(){
		try{
			this.setListaEmpleados(oEmpleado.buscar(this.getTxtBusquedaEmpleado()));
			if(this.getListaEmpleados().isEmpty()){
				mostrarMensajeInfo("No se encontrarón resultados para esta búsqueda");
			}
		}catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "buscar()", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
		
	}	

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTxtBusquedaEmpleado() {
		return txtBusquedaEmpleado;
	}

	public void setTxtBusquedaEmpleado(String txtBusquedaEmpleado) {
		this.txtBusquedaEmpleado = txtBusquedaEmpleado;
	}

	public Integer getHfId() {
		return hfId;
	}

	public void setHfId(Integer hfId) {
		this.hfId = hfId;
	}

	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public Empleado getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
	}

	public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}

	
}
