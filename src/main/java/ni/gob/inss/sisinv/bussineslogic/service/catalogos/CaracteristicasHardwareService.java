package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import java.util.List;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;

public interface CaracteristicasHardwareService {
	public List<CaracteristicasHardware> listaCaracteristicasHardwarePadre() throws EntityNotFoundException;
	public List<CaracteristicasHardware> listaCaracteristicasPorDescripcion(String descripcion, Boolean pasivo) throws EntityNotFoundException;
}
