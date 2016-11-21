package ni.gob.inss.sisinv.bussineslogic.service.inventario;

import java.util.List;

import ni.gob.inss.sisinv.model.entity.inventario.ActivosCaracteristicas;

public interface ActivosCaracteristicasService {
	public List<ActivosCaracteristicas> obtieneListaCaracteristicasActivo(Integer activoId);
}
