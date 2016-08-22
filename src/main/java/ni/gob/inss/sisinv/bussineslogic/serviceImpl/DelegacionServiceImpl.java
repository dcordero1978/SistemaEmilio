package ni.gob.inss.sisinv.bussineslogic.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.bussineslogic.service.DelegacionService;
import ni.gob.inss.sisinv.model.dao.DelegacionDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;

@Service
public class DelegacionServiceImpl implements DelegacionService{
	
	@Autowired
	DelegacionDAO oDelegacionDAO;

	@Transactional
	@Override
	public void agregar(Delegacion oDelegacion) throws DAOException, BusinessException {
			oDelegacionDAO.saveUpper(oDelegacion);		
	}

	@Override
	public void actualizar(Delegacion oDelegacion) throws DAOException, BusinessException {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public List<Delegacion> buscar(String txtCriterio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delegacion obtener(int id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
