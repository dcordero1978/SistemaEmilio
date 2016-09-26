package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.model.dao.catalogos.ActivoDAO;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

@Service
public class ActivoServiceImpl implements ActivoService {

	@Autowired
	ActivoDAO oActivoDAO;
	
	@Transactional
	@Override
	public List<Activos> obtenerListaActivosPorEmpleado(Integer empleadoId) throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("empleadoId", empleadoId));
		return oActivoDAO.search(oSearch);		
	}

}
