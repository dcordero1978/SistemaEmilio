package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import java.util.List;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

public interface ActivoService {
	public List<Activos> obtenerListaActivosPorEmpleado(Integer EmpleadoId) throws EntityNotFoundException;
}
