package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.SecafService;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;
import ni.gob.inss.sisinv.util.RegExpresionExtends;

/**
 * 
 * @author fbriceno
 *
 */
@Named
@Scope("view")
public class SecafBackBean extends BaseBackBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String txtBusquedaCatalogoSecaf;
	
	private Integer hfId;
	private Integer noCuenta;
	private Integer noSubcuenta;
	private String letra;
	private String descripcionBien;
	private String codigoCbs;
	private String DescripcionCbs;
	private Boolean pasivo;
	private Integer codigoObjeto;
	private Boolean nuevoRegistro;
	
	private Secaf catalogoSeleccionado; 
	
	private String regExpLetras;
	private String regExpNumeros;
	
	private List<Secaf> listaCatalogoSecaf;
	
	@Autowired
	private SecafService oSecafService;
	
	@PostConstruct
	public void init(){
		limpiar();
		this.cargarExpresionesRegulares();
		this.buscar();
	}
	
	public void limpiar(){
		this.setNoCuenta(null);
		this.setNoSubcuenta(null);
		this.setLetra("");
		this.setDescripcionBien("");
		this.setCodigoCbs("");
		this.setDescripcionCbs("");
		this.setPasivo(null);
		this.setNuevoRegistro(true);
		this.setHfId(null);
		this.setTxtBusquedaCatalogoSecaf("");
	}
	
	public void guardarOactualizar(){
		if(this.getHfId()==null){
			guardar();
		}else{
			actualizar();
		}
	}
	
	public void editar(){
		if(catalogoSeleccionado==null){
			mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
		}else{
			cargarDatosSecaf();
		}
	}
	
	private void cargarDatosSecaf(){
		Secaf oSecaf;
		try {
			oSecaf = oSecafService.obtener(catalogoSeleccionado.getId());
			this.setHfId(oSecaf.getId());
			this.setNoCuenta(oSecaf.getCuenta());
			this.setNoSubcuenta(oSecaf.getSubcuenta());
			this.setLetra(oSecaf.getLetra());
			this.setCodigoObjeto(oSecaf.getObjeto());
			this.setDescripcionBien(oSecaf.getDescripcionBe());
			this.setCodigoCbs(oSecaf.getCbs());
			this.setDescripcionCbs(oSecaf.getDescripcionCbs());
			this.setPasivo(oSecaf.isPasivo());
			this.setNuevoRegistro(false);
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatosSecaf", MessagesResults.ERROR_OBTENER, e);
		}
		
		
	}
	
	private void guardar(){
		Secaf oSecaf =this.obtieneCatalogoSecafPreparado();
		try {
			oSecaf.setPasivo(false);
			oSecaf.setCreadoEl(this.getTimeNow());
			oSecaf.setCreadoPor(this.getUsuarioActual().getId());
			oSecaf.setCreadoEnIp(this.getRemoteIp());
			oSecafService.agregar(oSecaf);
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
			this.setHfId(oSecaf.getId());
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "guardar", MessagesResults.ERROR_GUARDAR, e); 
		}
		
	}
	
	private void actualizar(){
		Secaf oSecaf = obtieneCatalogoSecafPreparado();
		try {
			oSecaf.setPasivo(this.getPasivo());
			oSecaf.setModificadoEl(this.getTimeNow());
			oSecaf.setModificadoEnIp(this.getRemoteIp());
			oSecaf.setModificadoPor(this.getUsuarioActual().getId());		
			oSecafService.actualizar(oSecaf);
			mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
			this.setHfId(oSecaf.getId());
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "actualizar", MessagesResults.ERROR_MODIFICAR, e);			
		}
	}
	
	private Secaf obtieneCatalogoSecafPreparado(){
		Secaf oSecaf = null ;
		try {
			if(this.getHfId()==null){
				oSecaf = new Secaf();
			}else {
				oSecaf = oSecafService.obtener(this.getHfId());
			}
			oSecaf.setCuenta(this.getNoCuenta());
			oSecaf.setSubcuenta(this.getNoSubcuenta());
			oSecaf.setLetra(this.getLetra());
			oSecaf.setObjeto(this.getCodigoObjeto());
			oSecaf.setDescripcionBe(this.getDescripcionBien());
			oSecaf.setCbs(this.getCodigoCbs());
			oSecaf.setDescripcionCbs(this.getDescripcionCbs());
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "obtieneCatalogoSecafPreparado", MessagesResults.ERROR_OBTENER, e);
		}
		return oSecaf;
	}  
	
	private void cargarExpresionesRegulares(){
		regExpLetras = RegExpresionExtends.regExpSoloLetrasConEspacio;
		regExpNumeros = RegExpresionExtends.regExpSoloNumeros;
	}
	
	public void buscar(){
		try {
			this.setListaCatalogoSecaf(oSecafService.buscar(this.getTxtBusquedaCatalogoSecaf()));
			if(this.getListaCatalogoSecaf().isEmpty()){
				mostrarMensajeInfo("No se encontrarón resultados para esta búsqueda");
			}			
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "buscar",e.getMessage(), e);
		}
	}
	
	public Boolean getNuevoRegistro() {
		return nuevoRegistro;
	}
	
	public void setNuevoRegistro(Boolean nuevoRegistro) {
		this.nuevoRegistro = nuevoRegistro;
	}

	public Integer getHfId() {
		return hfId;
	}

	public void setHfId(Integer hfId) {
		this.hfId = hfId;
	}

	public Integer getNoCuenta() {
		return noCuenta;
	}
	public void setNoCuenta(Integer noCuenta) {
		this.noCuenta = noCuenta;
	}
	public Integer getNoSubcuenta() {
		return noSubcuenta;
	}
	public void setNoSubcuenta(Integer noSubcuenta) {
		this.noSubcuenta = noSubcuenta;
	}
	public String getLetra() {
		return letra;
	}
	public void setLetra(String letra) {
		this.letra = letra;
	}
	public String getDescripcionBien() {
		return descripcionBien;
	}
	public void setDescripcionBien(String descripcionBien) {
		this.descripcionBien = descripcionBien;
	}
	public String getCodigoCbs() {
		return codigoCbs;
	}
	public void setCodigoCbs(String codigoCbs) {
		this.codigoCbs = codigoCbs;
	}
	public String getDescripcionCbs() {
		return DescripcionCbs;
	}
	public void setDescripcionCbs(String descripcionCbs) {
		DescripcionCbs = descripcionCbs;
	}
	public Boolean getPasivo() {
		return pasivo;
	}
	public void setPasivo(Boolean pasivo) {
		this.pasivo = pasivo;
	}

	public List<Secaf> getListaCatalogoSecaf() {
		return listaCatalogoSecaf;
	}

	public void setListaCatalogoSecaf(List<Secaf> listaCatalogoSecaf) {
		this.listaCatalogoSecaf = listaCatalogoSecaf;
	}

	public String getTxtBusquedaCatalogoSecaf() {
		return txtBusquedaCatalogoSecaf;
	}

	public void setTxtBusquedaCatalogoSecaf(String txtBusquedaCatalogoSecaf) {
		this.txtBusquedaCatalogoSecaf = txtBusquedaCatalogoSecaf;
	}

	public String getRegExpLetras() {
		return regExpLetras;
	}

	public String getRegExpNumeros() {
		return regExpNumeros;
	}

	public Integer getCodigoObjeto() {
		return codigoObjeto;
	}

	public void setCodigoObjeto(Integer codigoObjeto) {
		this.codigoObjeto = codigoObjeto;
	}

	public Secaf getCatalogoSeleccionado() {
		return catalogoSeleccionado;
	}

	public void setCatalogoSeleccionado(Secaf catalogoSeleccionado) {
		this.catalogoSeleccionado = catalogoSeleccionado;
	}	

	
	
}
