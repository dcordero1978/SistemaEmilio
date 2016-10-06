package ni.gob.inss.sisinv.model.entity.inventario;

import java.io.Serializable;

import javax.persistence.Column;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ActivosCaracteristicasId implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer activoId;
	private String caracteristicaCodigo;
	
	@Column(name="activo_id", insertable = false, updatable = false)
	public Integer getActivoId() {
		return activoId;
	}
	
	public void setActivoId(Integer activoId) {
		this.activoId = activoId;
	}
	
	@Column(name="caracteristica_cod", insertable = false, updatable = false)
	public String getCaracteristicaCodigo() {
		return caracteristicaCodigo;
	}
	
	public void setCaracteristicaCodigo(String caracteristicaCodigo) {
		this.caracteristicaCodigo = caracteristicaCodigo;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==this) return true;
		if(!(o instanceof ActivosCaracteristicasId)){
			return false;
		}
		
		ActivosCaracteristicasId oActivoCaracteristicasId = (ActivosCaracteristicasId) o;
		
		return new EqualsBuilder()
				.append(activoId, oActivoCaracteristicasId.activoId)
				.append(caracteristicaCodigo, oActivoCaracteristicasId.caracteristicaCodigo).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,37).append(activoId).append(caracteristicaCodigo).toHashCode();
	}
	
	
	
}
