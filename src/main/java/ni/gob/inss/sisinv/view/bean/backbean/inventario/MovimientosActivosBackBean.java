package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException; 
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.inventario.MovimientosService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoEmpleadoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosEmpleados;
import ni.gob.inss.sisinv.model.entity.inventario.Movimientos;
import ni.gob.inss.sisinv.util.CatalogoGeneral;


@Named
@Scope("view")

public class MovimientosActivosBackBean extends BaseBackBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Catalogo> listaTiposMovimientos;
	private Integer tipoMovimientoId;
	private Date fecha;  
	private Date fechamax;
	private Empleado filtroEmpleadoSeleccionado; 
	private Activos filtroActivoSeleccionado;
	private String txtEmpleadoDest; 
	private String txtcargoEmpleadoDest;
	private String txtubicacionEmpleadoDest;
	private String txtcodigoInventario;
	private String txtcodigoSecundario;
	private String txtdescripcionBien;
	private String txtEmpleadoOrig;
	private String cargoEmpleadoOrig;
	private String ubicacionEmpleadoOrig;
	private String txtobservaciones;
	
	private String filtroCodSecaf;
	private String filtroCodSecundario;
	private String filtroDescripcionBien;
	private String filtroSerie;
	private List<Catalogo> listaEstadoFisico;
	private List<Catalogo> listaTipoResguardo;
	private List<Movimientos> listaMovimientos;
	private Integer ubicacionId;
	private Integer estadoFisicoId;
	private Integer tipoResguardoId;
	private Integer HfEmpOrigId;
	private Integer HFEmpDestId;
	private Integer HFActivoId;
	private String observaciones;
	private List<Activos> filtroListaActivos = new ArrayList<Activos>();
	private List<Delegacion> listaDelegaciones;
	private Date fechaMovimiento;
	
	@Autowired
	CatalogoExtService oCatalogoService;
	
	@Autowired
	ActivoService oActivoService;
	
	@Autowired
	ActivoEmpleadoService oActivoEmpleadoService;

	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	EmpleadoService oEmpleadoService; 
	
	@Autowired
	MovimientosService oMovimientosService;
	
	 
	@PostConstruct
	public void init(){
		this.cargarListas();
		this.cargarListaDelegaciones();
		this.valoresfechas();
	} 

	public void limpiarBusquedaActivo(){
		this.setEstadoFisicoId(null);
		this.setFiltroCodSecaf(null);
		this.setFiltroCodSecundario(null);
		this.setFiltroDescripcionBien(null);
		
	}
	
	private void cargarListas() {
		try {
			this.setListaTiposMovimientos(oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.TIPOSMOVIMIENTO.getCodigoCatalogo()));
			this.setListaEstadoFisico(oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.ESTADO_FISICO.getCodigoCatalogo()));
			this.setListaTipoResguardo(oCatalogoService.obtieneListaCatalogosPorRefTipoCatalogo(CatalogoGeneral.TIPO_RESGUARDO.getCodigoCatalogo()));
		} catch (EntityNotFoundException  e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListas", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	

	public void cargarListaDelegaciones(){
		try {
				this.setListaDelegaciones(oDelegacionService.buscarPorEstado("",false));								
			} catch (Exception e) {
            	mostrarMensajeError(this.getClass().getSimpleName(),"cargarListaDelegaciones()",MessagesResults.ERROR_OBTENER_LISTA, e);
			}
	}
	
	public void cargarDatosFiltro() throws EntityNotFoundException{
		try {
			if(filtroEmpleadoSeleccionado==null) throw new BusinessException(MessagesResults.SELECCIONE_UN_REGISTRO);
				this.setTxtEmpleadoDest(filtroEmpleadoSeleccionado.getNumeroEmpleado()+" - "+ filtroEmpleadoSeleccionado.getPrimerNombre()+ " "+filtroEmpleadoSeleccionado.getSegundoNombre()+ " "+filtroEmpleadoSeleccionado.getPrimerApellido()+" "+filtroEmpleadoSeleccionado.getSegundoApellido());
				this.setTxtcargoEmpleadoDest(filtroEmpleadoSeleccionado.getCargo());
				this.setTxtubicacionEmpleadoDest(filtroEmpleadoSeleccionado.getArea());
				Empleado oEmpleado = oEmpleadoService.obtener(filtroEmpleadoSeleccionado.getId());
				this.setHFEmpDestId(oEmpleado.getId()); 
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos", MessagesResults.ERROR_OBTENER, e);
		}catch(BusinessException e){
			mostrarMensajeError(e.getMessage());
		}
    }
	
	public void cargarDatosFiltroActivo(){
		try {
        this.setTxtcodigoInventario(filtroActivoSeleccionado.getCodigoInventario());
        this.setTxtcodigoSecundario(filtroActivoSeleccionado.getCodigoSecundario());
        this.setTxtdescripcionBien(filtroActivoSeleccionado.getDescripcion()+ ", Marca: "+filtroActivoSeleccionado.marca.descripcion+", Modelo: "+filtroActivoSeleccionado.modelo.descripcion);
        this.setTxtEmpleadoOrig(filtroActivoSeleccionado.empleado.getNumeroEmpleado()+" - "+filtroActivoSeleccionado.empleado.getPrimerNombre() + " "+filtroActivoSeleccionado.empleado.getSegundoNombre() + " "+filtroActivoSeleccionado.empleado.getPrimerApellido()+" "+filtroActivoSeleccionado.empleado.getSegundoApellido());
        this.setCargoEmpleadoOrig(filtroActivoSeleccionado.empleado.getCargo());
        this.setUbicacionEmpleadoOrig(filtroActivoSeleccionado.empleado.getArea());
        this.setHfEmpOrigId(filtroActivoSeleccionado.empleado.getId());
        this.setHFActivoId(filtroActivoSeleccionado.getId());
		cargarMovimientosActivos(filtroActivoSeleccionado.getId());
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatos", MessagesResults.ERROR_OBTENER, e);
		}
    }
	
	public void cargarMovimientosActivos(Integer activoId) throws EntityNotFoundException{
		this.setListaMovimientos(oMovimientosService.buscar(activoId));
	}
	
	
	public void busquedaAvanzadaActivos() throws EntityNotFoundException{
		this.setFiltroListaActivos(oActivoService.buscar(this.getFiltroCodSecaf(), this.getFiltroCodSecundario(), this.getFiltroDescripcionBien(),this.getFiltroSerie(),this.getUbicacionId(),this.getEstadoFisicoId(),this.getTipoResguardoId()));
	}

	
	public void guardar() throws DAOException, BusinessException{
		
		if(this.getHfEmpOrigId().equals(this.getHFEmpDestId())){
			mostrarMensajeError("El Empleado al que se le asignará el bien no puede ser el mismo");
		}else{
			try {
					Movimientos oMovimientos = new Movimientos();
					oMovimientos.setTipoMovimientoId(this.getTipoMovimientoId());
					oMovimientos.setEmpleadoIdOrigen(oEmpleadoService.obtener(this.getHfEmpOrigId()));
					oMovimientos.setEmpleadoIdDestino(oEmpleadoService.obtener(this.getHFEmpDestId())); 
					oMovimientos.setActivoId( oActivoService.obtener(this.getHFActivoId()));
					oMovimientos.setFechaMovimiento(this.getFechaMovimiento());
					oMovimientos.setObservaciones(this.getTxtobservaciones());
					oMovimientos.setPasivo(false);
					oMovimientos.setCreadoEl(this.getTimeNow());
					oMovimientos.setCreadoEnIp(this.getRemoteIp());
					oMovimientos.setCreadoPor(this.getUsuarioActual().getId());
					oMovimientos.setEntidadId(this.getEntidadActual().getId());
					
					Activos oActivos = new Activos();
					oActivos = oActivoService.obtener(this.getHFActivoId());
					oActivos.setEmpleado(oEmpleadoService.obtener(this.getHFEmpDestId()));
					oActivos.setModificadoEl(this.getTimeNow());
					oActivos.setModificadoEnIp(this.getRemoteIp());
					oActivos.setModificadoPor(this.getUsuarioActual().getId());
					
					ActivosEmpleados oActivoEmpleado = new ActivosEmpleados();
					oActivoEmpleado.setActivo_id(this.getHFActivoId());
					oActivoEmpleado.setEmpleado_id(this.getHFEmpDestId());
					oActivoEmpleado.setPasivo(false);
					oActivoEmpleado.setFechaAsignacion(this.getTimeNow());
					oActivoEmpleado.setCreadoEl(this.getTimeNow());
					oActivoEmpleado.setCreadoEnIp(this.getRemoteIp());
					oActivoEmpleado.setCreadoPor(this.getUsuarioActual().getId());
					
					oActivoService.actualizar(oActivos);
					oMovimientosService.guardar(oMovimientos);
					oActivoEmpleadoService.guardar(oActivoEmpleado);
					cargarMovimientosActivos(this.getHFActivoId());
					mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
					
					
			} catch (BusinessException | DAOException e) {
				mostrarMensajeError(MessagesResults.ERROR_GUARDAR);
			}
	}
	}
	
	public void cancelar(){
		this.setCargoEmpleadoOrig("");
		this.setFechaMovimiento(null);
		this.setTipoMovimientoId(null);
		this.setTxtcodigoInventario("");
		this.setTxtcodigoSecundario("");
		this.setFiltroCodSecundario("");
		this.setTxtdescripcionBien("");
		this.setTxtEmpleadoOrig("");
		this.setUbicacionEmpleadoOrig("");
		this.setTxtubicacionEmpleadoDest("");
		this.setTxtcargoEmpleadoDest("");
		this.setListaMovimientos(null);
		this.setHFActivoId(null);
		this.setTxtEmpleadoDest("");
	}

	
	private void valoresfechas() {
		fechamax = new Date();
	}

	public List<Catalogo> getListaTiposMovimientos() {
		return listaTiposMovimientos;
	}

	public void setListaTiposMovimientos(List<Catalogo> listaTiposMovimientos) {
		this.listaTiposMovimientos = listaTiposMovimientos;
	}

	public Integer getTipoMovimientoId() {
		return tipoMovimientoId;
	}

	public void setTipoMovimientoId(Integer tipoMovimientoId) {
		this.tipoMovimientoId = tipoMovimientoId;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechamax() {
		return fechamax;
	}

	public void setFechamax(Date fechamax) {
		this.fechamax = fechamax;
	}

	public Empleado getFiltroEmpleadoSeleccionado() {
		return filtroEmpleadoSeleccionado;
	}

	public void setFiltroEmpleadoSeleccionado(Empleado filtroEmpleadoSeleccionado) {
		this.filtroEmpleadoSeleccionado = filtroEmpleadoSeleccionado;
	}

	public String getTxtEmpleadoDest() {
		return txtEmpleadoDest;
	}

	public void setTxtEmpleadoDest(String txtEmpleadoDest) {
		this.txtEmpleadoDest = txtEmpleadoDest;
	}
		
	public String getTxtcargoEmpleadoDest() {
		return txtcargoEmpleadoDest;
	}

	public void setTxtcargoEmpleadoDest(String txtcargoEmpleadoDest) {
		this.txtcargoEmpleadoDest = txtcargoEmpleadoDest;
	}

	public String getTxtubicacionEmpleadoDest() {
		return txtubicacionEmpleadoDest;
	}

	public void setTxtubicacionEmpleadoDest(String txtubicacionEmpleadoDest) {
		this.txtubicacionEmpleadoDest = txtubicacionEmpleadoDest;
	}

	public Activos getFiltroActivoSeleccionado() {
		return filtroActivoSeleccionado;
	}

	public void setFiltroActivoSeleccionado(Activos filtroActivoSeleccionado) {
		this.filtroActivoSeleccionado = filtroActivoSeleccionado;
	}

	public String getFiltroCodSecaf() {
		return filtroCodSecaf;
	}

	public void setFiltroCodSecaf(String filtroCodSecaf) {
		this.filtroCodSecaf = filtroCodSecaf;
	}

	public String getFiltroCodSecundario() {
		return filtroCodSecundario;
	}

	public void setFiltroCodSecundario(String filtroCodSecundario) {
		this.filtroCodSecundario = filtroCodSecundario;
	}

	public String getFiltroDescripcionBien() {
		return filtroDescripcionBien;
	}

	public void setFiltroDescripcionBien(String filtroDescripcionBien) {
		this.filtroDescripcionBien = filtroDescripcionBien;
	}

	public String getFiltroSerie() {
		return filtroSerie;
	}

	public void setFiltroSerie(String filtroSerie) {
		this.filtroSerie = filtroSerie;
	}

	public List<Catalogo> getListaEstadoFisico() {
		return listaEstadoFisico;
	}

	public void setListaEstadoFisico(List<Catalogo> listaEstadoFisico) {
		this.listaEstadoFisico = listaEstadoFisico;
	}

	public List<Catalogo> getListaTipoResguardo() {
		return listaTipoResguardo;
	}

	public void setListaTipoResguardo(List<Catalogo> listaTipoResguardo) {
		this.listaTipoResguardo = listaTipoResguardo;
	}
	
	public Integer getUbicacionId() {
		return ubicacionId;
	}

	public void setUbicacionId(Integer ubicacionId) {
		this.ubicacionId = ubicacionId;
	}

	public Integer getEstadoFisicoId() {
		return estadoFisicoId;
	}

	public void setEstadoFisicoId(Integer estadoFisicoId) {
		this.estadoFisicoId = estadoFisicoId;
	}

	public Integer getTipoResguardoId() {
		return tipoResguardoId;
	}

	public void setTipoResguardoId(Integer tipoResguardoId) {
		this.tipoResguardoId = tipoResguardoId;
	}
	
	public List<Activos> getFiltroListaActivos() {
		return filtroListaActivos;
	}

	public void setFiltroListaActivos(List<Activos> filtroListaActivos) {
		this.filtroListaActivos = filtroListaActivos;
	}
	
	public List<Delegacion> getListaDelegaciones() {
		return listaDelegaciones;
	}

	public void setListaDelegaciones(List<Delegacion> listaDelegaciones) {
		this.listaDelegaciones = listaDelegaciones;
	}

	public String getTxtcodigoInventario() {
		return txtcodigoInventario;
	}

	public void setTxtcodigoInventario(String txtcodigoInventario) {
		this.txtcodigoInventario = txtcodigoInventario;
	}

	public String getTxtcodigoSecundario() {
		return txtcodigoSecundario;
	}

	public void setTxtcodigoSecundario(String txtcodigoSecundario) {
		this.txtcodigoSecundario = txtcodigoSecundario;
	}

	public String getTxtdescripcionBien() {
		return txtdescripcionBien;
	}

	public void setTxtdescripcionBien(String txtdescripcionBien) {
		this.txtdescripcionBien = txtdescripcionBien;
	}
	

	public String getTxtEmpleadoOrig() {
		return txtEmpleadoOrig;
	}

	public void setTxtEmpleadoOrig(String txtEmpleadoOrig) {
		this.txtEmpleadoOrig = txtEmpleadoOrig;
	}

	public String getCargoEmpleadoOrig() {
		return cargoEmpleadoOrig;
	}

	public void setCargoEmpleadoOrig(String cargoEmpleadoOrig) {
		this.cargoEmpleadoOrig = cargoEmpleadoOrig;
	}

	public String getUbicacionEmpleadoOrig() {
		return ubicacionEmpleadoOrig;
	}

	public void setUbicacionEmpleadoOrig(String ubicacionEmpleadoOrig) {
		this.ubicacionEmpleadoOrig = ubicacionEmpleadoOrig;
	}

	public Integer getHfEmpOrigId() {
		return HfEmpOrigId;
	}

	public void setHfEmpOrigId(Integer hfEmpOrigId) {
		HfEmpOrigId = hfEmpOrigId;
	}

	public Integer getHFEmpDestId() {
		return HFEmpDestId;
	}

	public void setHFEmpDestId(Integer hfEmpDestId) {
		HFEmpDestId = hfEmpDestId;
	}

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public Integer getHFActivoId() {
		return HFActivoId;
	}

	public void setHFActivoId(Integer hFActivoId) {
		HFActivoId = hFActivoId;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<Movimientos> getListaMovimientos() {
		return listaMovimientos;
	}

	public void setListaMovimientos(List<Movimientos> listaMovimientos) {
		this.listaMovimientos = listaMovimientos;
	}

	public String getTxtobservaciones() {
		return txtobservaciones;
	}

	public void setTxtobservaciones(String txtobservaciones) {
		this.txtobservaciones = txtobservaciones;
	}

	
}
