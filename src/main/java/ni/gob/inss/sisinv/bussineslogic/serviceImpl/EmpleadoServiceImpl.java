package ni.gob.inss.sisinv.bussineslogic.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.bussineslogic.service.EmpleadoService;
import ni.gob.inss.sisinv.model.dao.EmpleadoDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoDAO oEmpleadoDAO;
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Empleado> buscar(String criterioBusqueda) {
		List<Empleado> listaEmpleado = null;
		Search oSearch = new Search();
		oSearch.addFilterOr(
				Filter.like("nombres", "%"+criterioBusqueda+"%"),
				Filter.like("apellidos", "%"+criterioBusqueda+"%"));
		oSearch.addSortDesc("nombres");
		if(criterioBusqueda==null || "".equals(criterioBusqueda)){
			listaEmpleado = oEmpleadoDAO.findAll(); 
		}else{
			listaEmpleado = oEmpleadoDAO.search(oSearch);
		}
		return listaEmpleado;
	}
	
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

	

}
