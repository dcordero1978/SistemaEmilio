package ni.gob.inss.sisinv.bussineslogic.serviceImpl.inventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.inventario.ActivosBajaService;
import ni.gob.inss.sisinv.model.dao.inventario.ActivosBajasDAO;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosBajas;

@Service
public class ActivosBajasServiceImpl implements ActivosBajaService {
	
	@Autowired
	ActivosBajasDAO oActivosBajasDAO;
	
	@Transactional
	@Override
	public void guardar(ActivosBajas oActivosBajas)throws javax.persistence.EntityNotFoundException, DAOException {
		oActivosBajasDAO.saveUpper(oActivosBajas);
	}

}
