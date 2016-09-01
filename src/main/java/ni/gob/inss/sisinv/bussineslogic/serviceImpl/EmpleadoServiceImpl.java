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
import ni.gob.inss.sisinv.bussineslogic.service.EmpleadoService;
import ni.gob.inss.sisinv.model.dao.DelegacionDAO;
import ni.gob.inss.sisinv.model.dao.EmpleadoDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoDAO oEmpleadoDAO;
	
	@Autowired
	private DelegacionDAO oDelegacionDAO;
	
	@Transactional
	@Override
	public void agregar(Empleado oEmpleado) throws DAOException, BusinessException {
		oEmpleadoDAO.saveUpper(oEmpleado);
	}

	@Transactional
	@Override
	public Empleado obtener(int empleadoId) throws EntityNotFoundException {
		return oEmpleadoDAO.find(empleadoId);
	}

	@Transactional
	@Override
	public void actualizar(Empleado oEmpleado) throws DAOException, BusinessException {
		oEmpleadoDAO.updateUpper(oEmpleado);		
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Empleado> buscar(String criterioBusqueda, Integer delegacionId) throws EntityNotFoundException {
		String txtBusqueda = StringUtils.isEmpty(criterioBusqueda) ? "" : criterioBusqueda;
		Search oSearch = new Search();
		
		if(delegacionId != null){
			oSearch.addFilter(Filter.equal("delegacionId", delegacionId));
		}
		oSearch.addFilterOr(Filter.ilike("nombres", "%"+txtBusqueda+"%"),
								Filter.ilike("apellidos", "%"+txtBusqueda+"%"));
		
		
		return oEmpleadoDAO.search(oSearch);			
		
	}
	

}
