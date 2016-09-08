package ni.gob.inss.sisinv.view.bean.backbean.catalogo;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import ni.gob.inss.barista.view.bean.backbean.BaseBackBean;

/**
 * 
 * @author fbriceno
 *
 */
@Named
@Scope("view")
public class SecafBackBean extends BaseBackBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String hfId;
	private Integer noCuenta;
	private Integer noSubcuenta;
	private String letra;
	private String descripcionBien;
	private String codigoCbs;
	private String DescripcionCbs;
	private Boolean pasivo;
	private Boolean nuevoRegistro;
	
	@PostConstruct
	public void init(){
		limpiar();
	}
	
	//Este metodo limpia los datos de pantalla
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
	
	
	
	
}
