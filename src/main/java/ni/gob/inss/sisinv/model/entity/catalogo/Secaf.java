package ni.gob.inss.sisinv.model.entity.catalogo;
// Generated 09-06-2016 02:07:33 PM by Hibernate Tools 4.3.4.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ni.gob.inss.barista.model.entity.EntityBase;

/**
 * Secaf generated by hbm2java
 */
@Entity
@Table(name = "secaf", schema = "catalogo")
public class Secaf extends EntityBase implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer cuenta;
	private Integer subcuenta;
	private String letra;
	private Integer objeto;
	private String descripcionBe;
	private String cbs;
	private String descripcionCbs;	
	private boolean pasivo;

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cuenta", nullable = false)
	public Integer getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(Integer cuenta) {
		this.cuenta = cuenta;
	}

	@Column(name = "subcuenta", nullable = false)
	public Integer getSubcuenta() {
		return this.subcuenta;
	}

	public void setSubcuenta(Integer subcuenta) {
		this.subcuenta = subcuenta;
	}

	@Column(name = "letra", nullable = false, length = 1)
	public String getLetra() {
		return this.letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	@Column(name = "objeto")
	public Integer getObjeto() {
		return this.objeto;
	}

	public void setObjeto(Integer objeto) {
		this.objeto = objeto;
	}

	@Column(name = "descripcion_be", nullable = false, length = 250)
	public String getDescripcionBe() {
		return this.descripcionBe;
	}

	public void setDescripcionBe(String descripcionBe) {
		this.descripcionBe = descripcionBe;
	}

	@Column(name = "cbs", nullable = false, length = 20)
	public String getCbs() {
		return this.cbs;
	}

	public void setCbs(String cbs) {
		this.cbs = cbs;
	}

	@Column(name = "descripcion_cbs", nullable = false, length = 250)
	public String getDescripcionCbs() {
		return this.descripcionCbs;
	}

	public void setDescripcionCbs(String descripcionCbs) {
		this.descripcionCbs = descripcionCbs;
	}


	@Column(name = "pasivo", nullable = false)
	public boolean isPasivo() {
		return this.pasivo;
	}

	public void setPasivo(boolean pasivo) {
		this.pasivo = pasivo;
	}

}
