package ni.gob.inss.sisinv.bussineslogic.serviceImpl.soporte;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.MantenimientosService;
import ni.gob.inss.sisinv.model.dao.soporte.MantenimientosDAO;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

@Service
public class MantenimientosServiceImpl implements MantenimientosService  {
	
	@Autowired
	MantenimientosDAO oMantenimientosDAO;
	
	@Transactional
	@Override
	public  List<ProgramacionMantenimiento> buscar() throws EntityNotFoundException{
		return oMantenimientosDAO.findAll();
	}
	
	@Transactional
	@Override
	public void guardarProgramacion (ProgramacionMantenimiento oProgramacionMto) throws DAOException {
		oMantenimientosDAO.saveUpper(oProgramacionMto);
	}

}
