package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.businesslogic.serviceImpl.catalogos.CatalogoServiceImpl;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.catalogos.CatalogoDAO;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CatalogoExtService;
import ni.gob.inss.sisinv.model.dao.catalogos.MarcaModeloDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;

@Primary
@Service("CatalogoService")
public class CatalogoExtServiceImpl extends CatalogoServiceImpl implements CatalogoExtService {
	@Autowired
	MarcaModeloDAO oMarcaModeloDAO;
	
	@Autowired
	CatalogoDAO oCatalogoDAO;	
	@Transactional
	@Override
	public List<MarcasModelos> obtenerListaMarcas() throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("padreId", 0));
		return oMarcaModeloDAO.search(oSearch);		
	}

	@Transactional
	@Override
	public List<MarcasModelos> obtenerListaModelos(Integer marcaId) throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("padreId", marcaId));
		return oMarcaModeloDAO.search(oSearch);
	}

	@Transactional
	@Override
	public List<Catalogo> obtieneListaCatalogosPorRefTipoCatalogo(String refTipoCatalogo) throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("refTipoCatalogo", refTipoCatalogo));
		return oCatalogoDAO.search(oSearch);
	}
	
}
