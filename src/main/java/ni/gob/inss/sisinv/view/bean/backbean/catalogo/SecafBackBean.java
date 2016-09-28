package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
import ni.gob.inss.barista.view.utils.web.MessagesResults;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.SecafService;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.TipoCatalogoExtService;
import ni.gob.inss.sisinv.bussineslogic.service.seguridad.UsuarioExtService;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;
import ni.gob.inss.sisinv.util.CatalogoGeneral;
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
	private String 	digitoAuxiliar;
	private String codigoCbs;
	private String DescripcionCbs;
	private Boolean pasivo;
	private Integer codigoObjeto;
	private Boolean nuevoRegistro;
	private Integer tipoBien;
	private Integer gasto;
	
	private Secaf catalogoSeleccionado; 
	
	private String regExpLetras;
	private String regExpNumeros;
	
	private List<Secaf> listaCatalogoSecaf;
	private List<Catalogo> listaTipoBienes;
	
	private boolean autorizadoParaEditar;
	
	@Autowired
	private SecafService oSecafService;
	
	@Autowired
	private TipoCatalogoExtService oTipoCatalogoService;
	
	@Autowired
	private UsuarioExtService oUsuarioService;
	@PostConstruct
	public void init(){
		limpiar();
		this.cargarExpresionesRegulares();
		this.cargarListaCatalogo();
		this.buscar();
		autorizadoParaEditar =  oUsuarioService.usuarioTieneAutorizacion(this.getUsuarioActual(), this.getEntidadActual(), "ESEC");
	}
	
	public void limpiar(){
		this.setNoCuenta(null);
		this.setNoSubcuenta(null);
		this.setDigitoAuxiliar("");
		this.setCodigoCbs("");
		this.setDescripcionCbs("");
		this.setTipoBien(null);
		this.setPasivo(null);
		this.setNuevoRegistro(true);
		this.setHfId(null);
		this.setTxtBusquedaCatalogoSecaf("");
		this.setCodigoObjeto(null);
		this.setGasto(null);
	}
	
	public void cargarListaCatalogo(){		
		try {
			TiposCatalogo catalogoTipoBienes = oTipoCatalogoService.obtenerTipoCatalogoPorCodigo(CatalogoGeneral.TIPO_BIENES.getCodigoCatalogo());
			this.listaTipoBienes = oTipoCatalogoService.obtenerCatalogos(catalogoTipoBienes);				
		} catch (Exception e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarListaCatalogo", MessagesResults.ERROR_OBTENER_LISTA, e);
		}
	}
	
	public void guardarOactualizar(){
		try{
			if(this.getHfId()==null){
				guardar();
			}else{
				actualizar();
			}
			this.cargarDatosSecaf(this.getHfId());
			this.setTxtBusquedaCatalogoSecaf("");
			this.buscar();
		}catch(BusinessException e){
			mostrarMensajeError(e.getMessage());
		}catch (DAOException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "guardarOactualizar", MessagesResults.ERROR_GUARDAR, e);
		}
	}
	
	public void editar(){
		if(catalogoSeleccionado==null){
			mostrarMensajeError(MessagesResults.SELECCIONE_UN_REGISTRO);
		}else{
			cargarDatosSecaf(catalogoSeleccionado.getId());
		}
	}
	
	private void cargarDatosSecaf(Integer secafId){
		Secaf oSecaf;
		try {
			oSecaf = oSecafService.obtener(secafId);
			this.setHfId(oSecaf.getId());
			this.setNoCuenta(oSecaf.getCuenta());
			this.setNoSubcuenta(oSecaf.getSubcuenta());
			this.setDigitoAuxiliar(oSecaf.getDigitoAuxiliar());
			this.setCodigoObjeto(oSecaf.getObjeto());
			this.setCodigoCbs(oSecaf.getCbs());
			this.setTipoBien(oSecaf.getTipoBien());
			this.setDescripcionCbs(oSecaf.getDescripcionCbs());
			this.setPasivo(oSecaf.isPasivo());
			this.setGasto(oSecaf.getGasto());
			this.setNuevoRegistro(false);
		} catch (EntityNotFoundException e) {
			mostrarMensajeError(this.getClass().getSimpleName(), "cargarDatosSecaf", MessagesResults.ERROR_OBTENER, e);
		}
		
		
	}
	
	private void guardar() throws BusinessException, DAOException{
		Secaf oSecaf =this.obtieneCatalogoSecafPreparado();
		try {
			oSecaf.setPasivo(false);
			oSecaf.setCreadoEl(this.getTimeNow());
			oSecaf.setCreadoPor(this.getUsuarioActual().getId());
			oSecaf.setCreadoEnIp(this.getRemoteIp());
			oSecafService.agregar(oSecaf);
			mostrarMensajeInfo(MessagesResults.EXITO_GUARDAR);
			this.setHfId(oSecaf.getId());
		} catch (BusinessException e) {
			throw e;			 
		}catch(DAOException e){
			throw e;			
		}
		
	}
	
	private void actualizar() throws BusinessException, DAOException{
		Secaf oSecaf = obtieneCatalogoSecafPreparado();
		try {
			oSecaf.setPasivo(this.getPasivo());
			oSecaf.setModificadoEl(this.getTimeNow());
			oSecaf.setModificadoEnIp(this.getRemoteIp());
			oSecaf.setModificadoPor(this.getUsuarioActual().getId());		
			oSecafService.actualizar(oSecaf);
			mostrarMensajeInfo(MessagesResults.EXITO_MODIFICAR);
			this.setHfId(oSecaf.getId());
		}catch (BusinessException e) {
			throw e;			 
		}catch(DAOException e){
			throw e;
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
			oSecaf.setDigitoAuxiliar(this.getDigitoAuxiliar());
			oSecaf.setObjeto(this.getCodigoObjeto());			
			oSecaf.setCbs(this.getCodigoCbs());
			oSecaf.setTipoBien(this.getTipoBien());
			oSecaf.setDescripcionCbs(this.getDescripcionCbs());
			oSecaf.setGasto(this.getGasto());
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
	
	public String getDigitoAuxiliar() {
		return digitoAuxiliar;
	}

	public void setDigitoAuxiliar(String digitoAuxiliar) {
		this.digitoAuxiliar = digitoAuxiliar;
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

	public List<Catalogo> getListaTipoBienes() {
		return listaTipoBienes;
	}

	public void setListaTipoBienes(List<Catalogo> listaTipoBienes) {
		this.listaTipoBienes = listaTipoBienes;
	}

	public Integer getTipoBien() {
		return tipoBien;
	}

	public void setTipoBien(Integer tipoBien) {
		this.tipoBien = tipoBien;
	}

	public Integer getGasto() {
		return gasto;
	}

	public void setGasto(Integer gasto) {
		this.gasto = gasto;
	}

	public boolean isAutorizadoParaEditar() {
		return autorizadoParaEditar;
	}
	
}
