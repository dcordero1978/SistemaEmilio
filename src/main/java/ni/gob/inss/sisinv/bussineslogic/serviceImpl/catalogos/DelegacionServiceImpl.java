package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.DelegacionService;
import ni.gob.inss.sisinv.model.dao.catalogos.DelegacionDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

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
	@Transactional
	public void actualizar(Delegacion oDelegacion) throws DAOException, BusinessException {
		oDelegacionDAO.updateUpper(oDelegacion);
		
	}
	

	@Transactional
	@Override
	public List<Delegacion> buscar(String txtCriterio) {
		
		return this.buscarPorEstado(txtCriterio,  (Boolean) null);
	}

	@Transactional
	@Override
	public Delegacion obtener(int id) throws EntityNotFoundException {
		return oDelegacionDAO.find(id);		
	}

	@Transactional
	@Override
	public List<Delegacion> listaUbicacionesPorDepartamento(Integer departamentoId) {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("esUbicacion", true));
		oSearch.addFilter(Filter.equal("departamentoId", departamentoId));
		return oDelegacionDAO.search(oSearch);		
	}
	
	@Transactional
	@Override
	public List<Delegacion> listaUbicacionesEmpleado(Empleado oEmpleado){
		List<Delegacion> listaUbicaciones = new ArrayList<Delegacion>();
		if(this.listaUbicacionesPorDepartamento(oEmpleado.getDelegacion().getDepartamentoId()).isEmpty()){
			listaUbicaciones.add(oEmpleado.getDelegacion());
			return listaUbicaciones;
		}else{
			return this.listaUbicacionesPorDepartamento(oEmpleado.getDelegacion().getDepartamentoId()); 
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Delegacion> buscarPorEstado(String txtCriterio, Boolean estado) {
		List<Delegacion> listaDelegaciones = null;
		
		Search oSearch = new Search();
		oSearch.addFilterILike("descripcion", "%"+txtCriterio+"%");
		
		if(estado != null)
		{		
			oSearch.addFilter(Filter.equal("pasivo",estado));
		}
		
		oSearch.addSortAsc("descripcion");
		
		listaDelegaciones = oDelegacionDAO.search(oSearch);
		
		
		if((txtCriterio==null || "".equalsIgnoreCase(txtCriterio)) && estado ==null ){
			listaDelegaciones = oDelegacionDAO.findAll();
		}else {
			listaDelegaciones = oDelegacionDAO.search(oSearch);
		}
		
		return listaDelegaciones;
				
	}



}
