package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

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
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.EmpleadoService;
import ni.gob.inss.sisinv.model.dao.catalogos.EmpleadoDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

	@Autowired
	private EmpleadoDAO oEmpleadoDAO;

	
	@Transactional
	@Override
	public void agregar(Empleado oEmpleado) throws DAOException, BusinessException {
		validaEmpleado(oEmpleado);
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
		oSearch.addFilterOr(Filter.ilike("primerNombre", "%"+txtBusqueda+"%"),
								Filter.ilike("segundoNombre", "%"+txtBusqueda+"%"),
								Filter.ilike("primerApellido", "%"+txtBusqueda+"%"),
								Filter.ilike("segundoApellido", "%"+txtBusqueda+"%"));
		
		
		return oEmpleadoDAO.search(oSearch);			
		
	}
	
	public void validaEmpleado(Empleado oEmpleado) throws BusinessException{
		if(this.obtieneEmpleadoPorNumeroEmpleado(oEmpleado.getNumeroEmpleado())!=null){
			throw new BusinessException("Ya existe un empleado con este número");
		}else if(this.obtieneEmpleadoPorCedula(oEmpleado.getNroIdentificacion())!=null){
			throw new BusinessException("Ya existe un empleado con este Número de Cedula");
		}
	}
	
	@Transactional
	private Empleado obtieneEmpleadoPorNumeroEmpleado(String numeroEmpleado){
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("numeroEmpleado", numeroEmpleado));
		return  (Empleado) oEmpleadoDAO.searchUnique(oSearch);		
	}
	
	@Transactional
	private Empleado obtieneEmpleadoPorCedula(String numeroCedula){
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("nroIdentificacion", numeroCedula));
		return (Empleado) oEmpleadoDAO.searchUnique(oSearch);	
	}

}
