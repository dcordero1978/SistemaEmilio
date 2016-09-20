package ni.gob.inss.sisinv.model.entity.inventario;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ActivosCaracteristicasId implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer activoId;
	private Integer caracteristicaId;
	
	public Integer getActivoId() {
		return activoId;
	}
	public void setActivoId(Integer activoId) {
		this.activoId = activoId;
	}
	public Integer getCaracteristicaId() {
		return caracteristicaId;
	}
	public void setCaracteristicaId(Integer caracteristicaId) {
		this.caracteristicaId = caracteristicaId;
	}
	
	
	
}
