package ni.gob.inss.sisinv.bussineslogic.service.inventario;

import javax.persistence.EntityNotFoundException;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosBajas;

public interface ActivosBajaService {

	void guardar(ActivosBajas oActivosBajas) throws EntityNotFoundException, DAOException;
	

}
