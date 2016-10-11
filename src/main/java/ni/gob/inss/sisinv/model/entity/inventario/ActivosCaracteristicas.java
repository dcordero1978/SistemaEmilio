package  ni.gob.inss.sisinv.model.entity.inventario;
// Generated 10-10-2016 04:13:07 PM by Hibernate Tools 4.3.4.Final

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import ni.gob.inss.barista.model.entity.EntityBase;

/**
 * ActivosCaracteristicas generated by hbm2java
 */
@Entity
@Table(name = "activos_caracteristicas", schema = "inventario")
public class ActivosCaracteristicas extends EntityBase  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private ActivosCaracteristicasId primaryKey = new ActivosCaracteristicasId();
	private String valor;
	private boolean esCatalogo;
	
	@EmbeddedId
	@AssociationOverrides({@AssociationOverride(name="activoId",joinColumns= @JoinColumn(name="activo_id"))})
	@AttributeOverrides({@AttributeOverride(name = "caracteristicaCod", column = @Column(name = "caracteristica_cod", nullable = false, length = 20)) })
	public ActivosCaracteristicasId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ActivosCaracteristicasId primaryKey) {
		this.primaryKey = primaryKey;
	}

	
	@Column(name = "valor", nullable = false, length = 50)
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Column(name = "es_catalogo", nullable = false)
	public boolean isEsCatalogo() {
		return this.esCatalogo;
	}

	public void setEsCatalogo(boolean esCatalogo) {
		this.esCatalogo = esCatalogo;
	}

	@Transient
	public Activos getActivo() {
		return primaryKey.getActivoId();
	}

	public void setActivo(Activos activo) {
		primaryKey.setActivoId(activo);
	}
	
}
