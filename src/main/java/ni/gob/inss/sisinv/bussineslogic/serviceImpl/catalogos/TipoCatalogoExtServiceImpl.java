package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.serviceImpl.catalogos.TipoCatalogoServiceImpl;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.catalogos.TipoCatalogoDAO;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.TipoCatalogoExtService;

@Service("TipoCatalogoService")
@Primary
public class TipoCatalogoExtServiceImpl extends TipoCatalogoServiceImpl implements TipoCatalogoExtService {

	@Autowired
	private TipoCatalogoDAO oTipoCatalogoDAO;
	
	@Transactional
	@Override
	public TiposCatalogo obtenerTipoCatalogoPorCodigo(String codigo) throws EntityNotFoundException, BusinessException{
		TiposCatalogo tipoCatalogo = null;
		Search oSearch = new Search();
		if("".equalsIgnoreCase(codigo)) throw new BusinessException("El parametro codigo no puede ser vacio.");
		
		oSearch.addFilter(Filter.equal("codigo", codigo));
		tipoCatalogo = (TiposCatalogo) oTipoCatalogoDAO.searchUnique(oSearch);
		return tipoCatalogo;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<TiposCatalogo> obtenerListaCatalogoPorCodigo(String... codigo) throws EntityNotFoundException {
		Search oSearch = new Search();
		Filter[] oFilters = new Filter[codigo.length];
		IntStream.range(0, codigo.length).forEach(iterador -> {
			oFilters[iterador] = new Filter("codigo", codigo[iterador]);
		});
		oSearch.addFilterOr(oFilters);
		return oTipoCatalogoDAO.search(oSearch);
	}
	
}
