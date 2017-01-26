package ni.gob.inss.sisinv.bussineslogic.serviceImpl.soporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.ComponentesActivosService;
import ni.gob.inss.sisinv.model.dao.soporte.ComponentesActivosDAO;
import ni.gob.inss.sisinv.model.entity.soporte.ComponentesActivos;

@Service
public class ComponentesActivosServiceImpl implements ComponentesActivosService {

	@Autowired ComponentesActivosDAO oComponentesActivosDAO;
	
	@Transactional
	@Override
	public void guardar(ComponentesActivos oComponenteActivo) throws DAOException {
		oComponentesActivosDAO.saveUpper(oComponenteActivo);
	}

	@Transactional
	@Override
	public List<ComponentesActivos> obtenerListaComponentes(Integer activoId, Boolean esPasivo) {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("activoId", activoId));
		oSearch.addFilterEqual("pasivo", esPasivo);
		return (List<ComponentesActivos>) oComponentesActivosDAO.search(oSearch);
	}

	@Transactional
	@Override
	public void modificar(ComponentesActivos oComponenteActivo) throws DAOException {
		oComponentesActivosDAO.updateUpper(oComponenteActivo);
	}
	
}
