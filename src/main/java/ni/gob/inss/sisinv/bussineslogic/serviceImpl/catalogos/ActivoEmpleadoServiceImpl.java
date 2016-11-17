package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoEmpleadoService;
import ni.gob.inss.sisinv.model.dao.catalogos.ActivoEmpleadoDAO;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosEmpleados;

@Service
public class ActivoEmpleadoServiceImpl implements ActivoEmpleadoService {
	
	@Autowired
	ActivoEmpleadoDAO oActivoEmpleadoDAO;
	
	@Transactional
	@Override
	public void guardar(ActivosEmpleados oActivoEmpleado) throws BusinessException, DAOException {
		oActivoEmpleadoDAO.saveUpper(oActivoEmpleado);
	} 

}
