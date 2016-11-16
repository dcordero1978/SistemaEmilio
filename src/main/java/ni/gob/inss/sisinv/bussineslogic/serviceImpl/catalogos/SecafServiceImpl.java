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
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.SecafService;
import ni.gob.inss.sisinv.model.dao.catalogos.SecafDAO;
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
		oSearch.addFilterOr(Filter.ilike("descripcionCbs", "%"+txtBusqueda+"%"));
		oSearch.addFilter(Filter.notEqual("descripcionCbs", StringUtils.EMPTY));
		oSearch.addSortAsc("descripcionCbs");
		
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
		validaCodigoCbsUnico(oSecaf.getCbs());
		validaCatalogoPropiedadesUnicas(oSecaf.getCuenta(), oSecaf.getSubcuenta(), oSecaf.getDigitoAuxiliar(), oSecaf.getObjeto());
		oSecafDAO.saveUpper(oSecaf);		
	}
	
	@Transactional
	@Override
	public void actualizar(Secaf oSecaf) throws DAOException, BusinessException {
		oSecafDAO.updateUpper(oSecaf);		
	}
	
	public void validaCodigoCbsUnico(String codigoCbs) throws BusinessException{
		if(obtieneCatalogoCodigoCbsUnico(codigoCbs)!=null){
			throw new BusinessException("Ya existe un registro con el Codigo CBS digitado");
		}
	}
	
	public void validaCatalogoPropiedadesUnicas(int noCuenta, int noSubcuenta, String digitoAuxiliar, int codigoObjeto) throws BusinessException{
		if(obtieneCatalogoSecafUnico(noCuenta, noSubcuenta, digitoAuxiliar, codigoObjeto) != null){
			throw new BusinessException("Ya existe un registro con las propiedades No.Cuenta,Subcuenta No,Letra, Objeto");
		}
	}
	
	@Transactional
	private Secaf obtieneCatalogoSecafUnico(int noCuenta, int noSubCuenta, String letra, int codObjeto){
		Search oSearch = new Search();
		Filter unicoObjeto = Filter.and(Filter.equal("cuenta", noCuenta),
							Filter.equal("subcuenta",noSubCuenta),
							Filter.equal("digitoAuxiliar", letra),
							Filter.equal("objeto", codObjeto));
		oSearch.addFilter(unicoObjeto);
		return (Secaf) oSecafDAO.searchUnique(oSearch);	
	}
	
	@Transactional
	private Secaf obtieneCatalogoCodigoCbsUnico(String codigoCbs){
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("cbs", codigoCbs));
		return (Secaf) oSecafDAO.searchUnique(oSearch);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Secaf> buscar(String descripcion, String codigo, Integer tipoCatalogo) {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.ilike("descripcionCbs", "%"+descripcion+"%"));
		oSearch.addFilter(Filter.ilike("cbs", "%"+codigo+"%"));
		oSearch.addFilter(Filter.equal("tipoBien", tipoCatalogo));
		return oSecafDAO.search(oSearch);
	}

}
