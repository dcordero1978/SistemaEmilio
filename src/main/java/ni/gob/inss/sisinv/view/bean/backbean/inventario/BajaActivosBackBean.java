package ni.gob.inss.sisinv.view.bean.backbean.inventario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.bussineslogic.service.inventario.ActivosBajaService;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosBajas;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.core.jasperclient.JasperRestService;
import ni.gob.inss.barista.businesslogic.service.seguridad.ParametroService;

@Named
@Scope("view")
public class BajaActivosBackBean extends BaseBackBean implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String txtcodigoInventario;
	private String txtcodigoSecundario;
	private String txtdescripcionBien;
	private String txtSerie;
	private String txtEmpleadoOrig;
	private String cargoEmpleadoOrig;
	private String ubicacionEmpleadoOrig;
	private String txtEmpleadoDest;
	private String cargoEmpleadoDest;
	private String ubicacionEmpleadoDest;
	private Integer hfActivoId;
	private Integer hfEmpOrigId;
	private Integer hfEmpDestId;
	private Integer hfUbicacionId;
	private String txtObservaciones;
	private Activos filtroActivoSeleccionado;
	private Empleado empleadoResponsable;
	private Integer hfUbicacionDestId;
	private Integer parametroRespBodega;
	private Integer hfBajaId;
	private Integer idtipoResguardo;
	
	static final Integer idBodegaBaja = 38;
	static final Integer idResponsableBodega= 13;
	static final String  codigoResguardoBodega="B";

	@Autowired
	DelegacionService oDelegacionService;
	
	@Autowired
	ParametroService oParametroService;
	
	@Autowired
	EmpleadoService oEmpleadoService;
	
	@Autowired
	ActivoService oActivoService;
	
	@Autowired
	ActivosBajaService oActivoBajaService;
	
	@Autowired
	CatalogoExtService oCatalogoExtService;
	
	@Autowired
	JasperRestService oJasperReportService;
	
	@PostConstruct
	public void init(){
		RequestContext.getCurrentInstance().execute("PF('btnBaja').disable()");
		RequestContext.getCurrentInstance().execute("PF('btnDiagnostico').disable()");
		RequestContext.getCurrentInstance().execute("PF('btnReporte').disable()");
		this.setHfUbicacionDestId(idBodegaBaja);
		this.setParametroRespBodega(idResponsableBodega);
		this.cargarBodegaBaja();
		this.cargarResponsableBodega();
		this.cargarTipoResguardo();
	}
	
	public void cancelar(){
		this.setTxtcodigoInventario("");
		this.setTxtcodigoSecundario("");
		this.setTxtdescripcionBien("");
		this.setTxtSerie("");
		this.setTxtEmpleadoOrig("");
		this.setCargoEmpleadoOrig("");
		this.setUbicacionEmpleadoOrig("");
		this.setTxtEmpleadoDest("");
		this.setCargoEmpleadoDest("");
		this.setUbicacionEmpleadoDest("");
		this.setHfActivoId(null);
		this.setHfEmpOrigId(null);
		this.setTxtObservaciones("");
		this.setFiltroActivoSeleccionado(null);
		this.setHfActivoId(null);
		this.setHfEmpDestId(null);
		this.setHfEmpOrigId(null);
		this.setHfUbicacionDestId(null);
		this.setHfUbicacionId(null);
	}
	
	
	public void cargarBodegaBaja(){
		try{
		this.setUbicacionEmpleadoDest(oDelegacionService.obtener(hfUbicacionDestId).getDescripcion());
		}catch (EntityNotFoundException  e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarBodegaBaja", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	public void cargarTipoResguardo(){
			this.setIdtipoResguardo(oCatalogoExtService.obtieneCatalogoPorCodigo(codigoResguardoBodega).getId());
	}
	
	public void cargarResponsableBodega(){
		try{
			this.setHfEmpDestId(Integer.valueOf(oParametroService.obtenerParametroPorId(parametroRespBodega).getValor()));
			this.cargarEmpleadoRespBodegaBaja();
		}catch(EntityNotFoundException e){
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarResponsableBodega", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	public void cargarEmpleadoRespBodegaBaja(){
		try{
			this.setEmpleadoResponsable(oEmpleadoService.obtener(hfEmpDestId));
			this.setTxtEmpleadoDest(this.getEmpleadoResponsable().getNombreCompleto());
			this.setCargoEmpleadoDest(this.getEmpleadoResponsable().getCargo());
		}catch(EntityNotFoundException e){
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarEmpleadoRespBodegaBaja", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	public void cargarDatosFiltroActivo(){
		this.setHfActivoId(filtroActivoSeleccionado.getId());
        this.setTxtcodigoInventario(filtroActivoSeleccionado.getCodigoInventario());
        this.setTxtcodigoSecundario(filtroActivoSeleccionado.getCodigoSecundario());
        this.setTxtSerie(filtroActivoSeleccionado.getSerie());
        this.setHfUbicacionId(filtroActivoSeleccionado.getUbicacionId());
        this.setTxtdescripcionBien(filtroActivoSeleccionado.getDescripcion()+ ", Marca: "+filtroActivoSeleccionado.marca.descripcion+", Modelo: "+filtroActivoSeleccionado.modelo.descripcion);
        this.setTxtEmpleadoOrig(filtroActivoSeleccionado.empleado.getNombreCompleto());
        this.setCargoEmpleadoOrig(filtroActivoSeleccionado.empleado.getCargo());
        this.setUbicacionEmpleadoOrig(filtroActivoSeleccionado.empleado.getArea());
        this.setHfEmpOrigId(filtroActivoSeleccionado.empleado.getId());
        RequestContext.getCurrentInstance().execute("PF('btnBaja').enable()");
    }
	
	public void imprimeReporteBaja(){
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("psEntidad",this.getEntidadActual().getId().toString());
		parametros.put("psUsuario",this.getUsuarioActual().getUsername());
		parametros.put("psBajaId",this.getHfBajaId().toString());
		
		
		//TODO: ESTE ES EL ID DEL REPORTE DEL CUAL SE OBTIENE EL ENCABEZADO. PENDIENTE MEJORAR
		parametros.put("IdReporte","6");
		
		try {
			oJasperReportService.getReport("/reports/reports/Inventario/acta_baja_bienes", "pdf", parametros, "INV006");
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "imprimeReporteBaja", "OCURRIO UN ERROR AL GENERAR EL REPORTE", e);
		}
	}
	
	public void guardar(){
		try{
			ActivosBajas oActivosBajas = new ActivosBajas();
			oActivosBajas.setActivoId(oActivoService.obtener(hfActivoId));
			oActivosBajas.setEmpleadoIdOrigen(oEmpleadoService.obtener(hfEmpOrigId));
			oActivosBajas.setUbicacionIdOrigen(oDelegacionService.obtener(hfUbicacionId));
			oActivosBajas.setEmpleadoIdDestino(oEmpleadoService.obtener(hfEmpDestId));
			oActivosBajas.setUbicacionIdDestino(oDelegacionService.obtener(hfUbicacionDestId));
			oActivosBajas.setObservaciones(this.getTxtObservaciones());
			oActivosBajas.setPasivo(false);
			oActivosBajas.setCreadoEl(this.getTimeNow());
			oActivosBajas.setCreadoEnIp(this.getRemoteIp());
			oActivosBajas.setCreadoPor(this.getUsuarioActual().getId());
			oActivosBajas.setEntidadId(this.getEntidadActual().getId());
			
			Activos oActivos = new Activos();
			oActivos = oActivoService.obtener(this.getHfActivoId());
			oActivos.setEmpleado(oEmpleadoService.obtener(hfEmpDestId));
			oActivos.setUbicacion(oDelegacionService.obtener(hfUbicacionDestId));
			oActivos.setTipoResguardo(oCatalogoExtService.obtener(idtipoResguardo));
			oActivos.setPasivo(true);
			oActivos.setBaja(true);
			oActivos.setModificadoEl(this.getTimeNow());
			oActivos.setModificadoEnIp(this.getRemoteIp());
			oActivos.setModificadoPor(this.getUsuarioActual().getId());
			
			oActivoService.actualizar(oActivos);
			oActivoBajaService.guardar(oActivosBajas);
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);	
			RequestContext.getCurrentInstance().execute("PF('btnBaja').disable()");
			this.setHfBajaId(oActivosBajas.getId());
			RequestContext.getCurrentInstance().execute("PF('btnDiagnostico').enable()");
		}catch (BusinessException | DAOException e) {
			mostrarMensajeError(MessagesResults.ERROR_GUARDAR);
		}
		
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
	
	public Activos getFiltroActivoSeleccionado() {
		return filtroActivoSeleccionado;
	}

	public void setFiltroActivoSeleccionado(Activos filtroActivoSeleccionado) {
		this.filtroActivoSeleccionado = filtroActivoSeleccionado;
	}

	public String getTxtSerie() {
		return txtSerie;
	}

	public void setTxtSerie(String txtSerie) {
		this.txtSerie = txtSerie;
	}

	public Integer getHfActivoId() {
		return hfActivoId;
	}

	public void setHfActivoId(Integer hfActivoId) {
		this.hfActivoId = hfActivoId;
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

	public String getTxtEmpleadoDest() {
		return txtEmpleadoDest;
	}

	public void setTxtEmpleadoDest(String txtEmpleadoDest) {
		this.txtEmpleadoDest = txtEmpleadoDest;
	}

	public String getCargoEmpleadoDest() {
		return cargoEmpleadoDest;
	}

	public void setCargoEmpleadoDest(String cargoEmpleadoDest) {
		this.cargoEmpleadoDest = cargoEmpleadoDest;
	}

	public String getUbicacionEmpleadoDest() {
		return ubicacionEmpleadoDest;
	}

	public void setUbicacionEmpleadoDest(String ubicacionEmpleadoDest) {
		this.ubicacionEmpleadoDest = ubicacionEmpleadoDest;
	}

	public Integer getHfEmpOrigId() {
		return hfEmpOrigId;
	}

	public void setHfEmpOrigId(Integer hfEmpOrigId) {
		this.hfEmpOrigId = hfEmpOrigId;
	}

	public String getTxtObservaciones() {
		return txtObservaciones;
	}

	public void setTxtObservaciones(String txtObservaciones) {
		this.txtObservaciones = txtObservaciones;
	}

	public Integer getHfEmpDestId() {
		return hfEmpDestId;
	}

	public void setHfEmpDestId(Integer hfEmpDestId) {
		this.hfEmpDestId = hfEmpDestId;
	}

	public Empleado getEmpleadoResponsable() {
		return empleadoResponsable;
	}

	public void setEmpleadoResponsable(Empleado empleadoResponsable) {
		this.empleadoResponsable = empleadoResponsable;
	}

	public Integer getHfUbicacionId() {
		return hfUbicacionId;
	}

	public void setHfUbicacionId(Integer hfUbicacionId) {
		this.hfUbicacionId = hfUbicacionId;
	}

	public Integer getHfUbicacionDestId() {
		return hfUbicacionDestId;
	}

	public void setHfUbicacionDestId(Integer hfUbicacionDestId) {
		this.hfUbicacionDestId = hfUbicacionDestId;
	}

	public Integer getParametroRespBodega() {
		return parametroRespBodega;
	}

	public void setParametroRespBodega(Integer parametroRespBodega) {
		this.parametroRespBodega = parametroRespBodega;
	}

	public Integer getHfBajaId() {
		return hfBajaId;
	}

	public void setHfBajaId(Integer hfBajaId) {
		this.hfBajaId = hfBajaId;
	}

	public Integer getIdtipoResguardo() {
		return idtipoResguardo;
	}

	public void setIdtipoResguardo(Integer idtipoResguardo) {
		this.idtipoResguardo = idtipoResguardo;
	}


}
