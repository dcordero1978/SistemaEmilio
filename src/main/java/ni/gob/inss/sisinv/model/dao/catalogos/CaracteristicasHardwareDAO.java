package ni.gob.inss.sisinv.model.dao.catalogos;

import java.util.List;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;

public interface CaracteristicasHardwareDAO extends BaseGenericDAO<CaracteristicasHardware, Integer> {
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwareDisponiblePorTipoActivoId(Integer tipoActivoId);
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwareAgregadoPorTipoActivoId(Integer tipoActivoId);
}
