package ni.gob.inss.sisinv.bussineslogic.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.bussineslogic.service.DelegacionService;
import ni.gob.inss.sisinv.model.dao.DelegacionDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;

@Service
public class DelegacionServiceImpl implements DelegacionService{
	
	@Autowired
	private DelegacionDAO oDelegacionDAO;

	@Transactional
	@Override
	public void agregar(Delegacion oDelegacion) throws DAOException, BusinessException {
			oDelegacionDAO.saveUpper(oDelegacion);		
	}

	@Override
	public void actualizar(Delegacion oDelegacion) throws DAOException, BusinessException {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Delegacion> buscar(String txtCriterio) {
		List<Delegacion> listaDelegaciones = null;
		
		Search oSearch = new Search();
		oSearch.addFilterILike("nombre", "%"+txtCriterio+"%");
		oSearch.addSortAsc("nombre");
		if(txtCriterio==null || "".equalsIgnoreCase(txtCriterio)){
			listaDelegaciones = oDelegacionDAO.findAll();
		}else{
			listaDelegaciones = oDelegacionDAO.search(oSearch);
		}
		
		return listaDelegaciones;		
	}

	
	@Override
	public Delegacion obtener(int id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
