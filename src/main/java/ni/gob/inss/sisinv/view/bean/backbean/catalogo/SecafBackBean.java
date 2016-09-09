package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;
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
	
	private String hfId;
	private Integer noCuenta;
	private Integer noSubcuenta;
	private String letra;
	private String descripcionBien;
	private String codigoCbs;
	private String DescripcionCbs;
	private Boolean pasivo;
	private Boolean nuevoRegistro;
	
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
	
	private void cargarExpresionesRegulares(){
		regExpLetras = RegExpresionExtends.regExpSoloLetrasConEspacio;
		regExpNumeros = RegExpresionExtends.regExpSoloNumeros;
	}
	
	public void buscar(){
		try {
			this.setListaCatalogoSecaf(oSecafService.buscar(this.getTxtBusquedaCatalogoSecaf()));	;
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

	public String getHfId() {
		return hfId;
	}
	public void setHfId(String hfId) {
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
	
	

}
