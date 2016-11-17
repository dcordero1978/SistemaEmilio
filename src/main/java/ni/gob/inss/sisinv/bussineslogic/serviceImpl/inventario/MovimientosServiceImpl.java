package ni.gob.inss.sisinv.bussineslogic.serviceImpl.inventario;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.inventario.MovimientosService;
import ni.gob.inss.sisinv.model.dao.inventario.MovimientosDAO;
import ni.gob.inss.sisinv.model.entity.inventario.Movimientos;

@Service
public class MovimientosServiceImpl implements MovimientosService {
	

	@Autowired
	MovimientosDAO oMovimientosDAO;
	
	@Transactional
	@Override
	public List<Movimientos> buscar(Integer activoId) throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("activosId", activoId));
		return oMovimientosDAO.search(oSearch);
	}

	@Transactional
	@Override
	public void guardar(Movimientos oMovimientos) throws javax.persistence.EntityNotFoundException, DAOException {
		oMovimientosDAO.saveUpper(oMovimientos);
		
	}

}
