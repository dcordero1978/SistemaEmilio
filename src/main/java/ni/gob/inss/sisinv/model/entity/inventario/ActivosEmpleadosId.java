package ni.gob.inss.sisinv.model.entity.inventario;

import java.io.Serializable;

public class ActivosEmpleadosId implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer activoId;
	private Integer empleadoId;
	
	public Integer getActivoId() {
		return activoId;
	}
	public void setActivoId(Integer activoId) {
		this.activoId = activoId;
	}
	public Integer getEmpleadoId() {
		return empleadoId;
	}
	public void setEmpleadoId(Integer empleadoId) {
		this.empleadoId = empleadoId;
	}
	

}
