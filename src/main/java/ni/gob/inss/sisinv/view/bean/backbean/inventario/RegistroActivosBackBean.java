package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Named
@Scope("view")
public class RegistroActivosBackBean extends BaseBackBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<Empleado> listaEmpleados;
	private String busquedaEmpleado;
	private Empleado empleadoSeleccionado;
	private Integer hfId;
	
	private String nombreCompleto;
	private String numeroEmpleado;
	
	@Autowired
	EmpleadoService oEmpleadoService;
	
	@PostConstruct
	public void init(){
		this.limpiar();
		this.cargarListaEmpleados();
	}
	
	public void limpiar(){
		this.setEmpleadoSeleccionado(null);
		this.setBusquedaEmpleado(StringUtils.EMPTY);
		this.setNombreCompleto(StringUtils.EMPTY);
		this.setNumeroEmpleado(StringUtils.EMPTY);
		this.setHfId(null);
	}
	
	public void cargarListaEmpleados(){
		try {
			this.setListaEmpleados(oEmpleadoService.buscar(this.getBusquedaEmpleado(), null));
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaEmpleados", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	public void cargarDatos(){
		try {
			if(empleadoSeleccionado==null) throw new BusinessException(MessagesResults.SELECCIONE_UN_REGISTRO);
			Empleado oEmpleado = oEmpleadoService.obtener(empleadoSeleccionado.getId());
			this.setNombreCompleto(oEmpleado.getPrimerNombre() + " "+ oEmpleado.getSegundoNombre() + " " +oEmpleado.getPrimerApellido()+" "+oEmpleado.getSegundoApellido());
			this.setNumeroEmpleado(oEmpleado.getNumeroEmpleado());
			this.setHfId(oEmpleado.getId());			
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos", MessagesResults.ERROR_OBTENER, e);
		}catch(BusinessException e){
			mostrarMensajeError(e.getMessage());
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

	public Integer getHfId() {
		return hfId;
	}

	public void setHfId(Integer hfId) {
		this.hfId = hfId;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

}
