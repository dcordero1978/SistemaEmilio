package ni.gob.inss.sisinv.bussineslogic.serviceImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.bussineslogic.service.SecafService;
import ni.gob.inss.sisinv.model.dao.SecafDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;

@Service
public class SecafServiceImpl implements SecafService {

	@Autowired
	private SecafDAO oSecafDAO;
	
	@Transactional
	@Override
	public List<Secaf> buscar(String txtCriterio) throws EntityNotFoundException {
		String txtBusqueda = StringUtils.isEmpty(txtCriterio) ? "" : txtCriterio;
		
		Search oSearch = new Search();		
		oSearch.addFilterOr(Filter.ilike("descripcionCbs", "%"+txtBusqueda+"%"),Filter.ilike("descripcionBe", "%"+txtBusqueda+"%"));		
		oSearch.addSortDesc("descripcionBe");
		
		return oSecafDAO.search(oSearch);
	}

	@Transactional
	@Override
	public Secaf obtener(int id) throws EntityNotFoundException {
		return oSecafDAO.find(id);		
	}

	@Transactional
	@Override
	public void agregar(Secaf oSecaf) throws DAOException, BusinessException {
		oSecafDAO.saveUpper(oSecaf);		
	}
	
	@Transactional
	@Override
	public void actualizar(Secaf oSecaf) throws DAOException, BusinessException {
		oSecafDAO.updateUpper(oSecaf);		
	}

}
