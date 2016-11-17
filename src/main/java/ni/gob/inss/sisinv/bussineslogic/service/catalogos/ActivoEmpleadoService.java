package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosEmpleados;

public interface ActivoEmpleadoService {
	public void guardar(ActivosEmpleados oActivoEmpleado) throws BusinessException, EntityNotFoundException, DAOException;
}
