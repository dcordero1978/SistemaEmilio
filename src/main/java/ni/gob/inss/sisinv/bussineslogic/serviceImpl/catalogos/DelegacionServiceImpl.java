package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

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
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Delegacion> buscar(String txtCriterio) {
		List<Delegacion> listaDelegaciones = null;
		
		Search oSearch = new Search();
		oSearch.addFilterILike("descripcion", "%"+txtCriterio+"%");
		oSearch.addSortDesc("descripcion");
		if(txtCriterio==null || "".equalsIgnoreCase(txtCriterio)){
			listaDelegaciones = oDelegacionDAO.findAll();
		}else{
			listaDelegaciones = oDelegacionDAO.search(oSearch);
		}
		
		return listaDelegaciones;		
	}

	@Transactional
	@Override
	public Delegacion obtener(int id) throws EntityNotFoundException {
		return oDelegacionDAO.find(id);		
	}

	@Override
	public List<Delegacion> listaDelegacionesPorDepartamento(Integer departamentoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public List<Delegacion> listaUbicacionesPorDepartamento(Integer departamentoId) {
		Search oSearch = new Search();
		oSearch.addFilterAnd(Filter.equal("departamentoId", departamentoId),Filter.equal("esUbicacion", true));
		return oDelegacionDAO.search(oSearch);		
	}
	
	

}
