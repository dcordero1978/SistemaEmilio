package ni.gob.inss.sisinv.model.entity.soporte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import ni.gob.inss.barista.model.entity.EntityBase;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

@Entity
@Table(name = "componentes_activos", schema = "soportetecnico")
@SequenceGenerator(allocationSize=1,name="ComponentesActivosSequence",sequenceName="soportetecnico.componentes_activos_id_seq")
public class ComponentesActivos extends EntityBase implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activoId;
	private Activos oActivo;
	private Integer componenteId;
	private Boolean pasivo;


	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(generator="ComponentesActivosSequence",strategy=GenerationType.SEQUENCE)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "activo_id", nullable = false)
	public Integer getActivoId() {
		return this.activoId;
	}

	public void setActivoId(Integer activoId) {
		this.activoId = activoId;
	}
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	public Activos getoActivo() {
		return oActivo;
	}

	public void setoActivo(Activos oActivo) {
		this.oActivo = oActivo;
	}

	@Column(name = "componente_id", nullable = false)
	public Integer getComponenteId() {
		return this.componenteId;
	}

	public void setComponenteId(Integer componenteId) {
		this.componenteId = componenteId;
	}

	@Column(name = "pasivo")
	public Boolean getPasivo() {
		return this.pasivo;
	}

	public void setPasivo(Boolean pasivo) {
		this.pasivo = pasivo;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37).append(activoId).append(componenteId).append(id).append(pasivo).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComponentesActivos))
			return false;
		ComponentesActivos oComponenteActivo = (ComponentesActivos) obj;
		return new EqualsBuilder()
				.append(activoId, oComponenteActivo.activoId)
				.append(componenteId, oComponenteActivo.componenteId)
				.append(id, oComponenteActivo.id)
				.append(pasivo, oComponenteActivo.pasivo)
				.isEquals();
	}
	
	

}
