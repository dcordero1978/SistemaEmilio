package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.Sort;

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
		//EL NUMERO 0 DENOTA QUE ES PADRE
		oSearch.addFilter(Filter.equal("padreId", 0));
		oSearch.addFilter(Filter.equal("pasivo", false));
		oSearch.addSort(Sort.asc("descripcion"));
		return oMarcaModeloDAO.search(oSearch);		
	}

	@Transactional
	@Override
	public List<MarcasModelos> obtenerListaModelos(Integer marcaId) throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("padreId", marcaId));
		oSearch.addFilter(Filter.equal("pasivo", false));
		oSearch.addSort(Sort.asc("descripcion"));
		return oMarcaModeloDAO.search(oSearch);
	}

	@Transactional
	@Override
	public List<Catalogo> obtieneListaCatalogosPorRefTipoCatalogo(String refTipoCatalogo) throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilterOr(Filter.equal("refTipoCatalogo", refTipoCatalogo));
		oSearch.addFilter(Filter.equal("pasivo", false));
		oSearch.addSort(Sort.asc("orden"));
		return oCatalogoDAO.search(oSearch);
	}

	@Transactional
	@Override
	public List<Catalogo> obtieneListaCatalogosPorRefTipoCatalogo(String... refTipoCatalogo)throws EntityNotFoundException {
		Search oSearch = new Search();
		Filter[] filtros =new Filter[refTipoCatalogo.length];
		 
		IntStream.range(0, refTipoCatalogo.length).forEach(iterador -> {
			filtros[iterador] = new Filter("refTipoCatalogo", refTipoCatalogo[iterador]);
		});
		oSearch.addFilterOr(filtros);
		/*Arrays.stream(refTipoCatalogo).forEach(tipoCatalogo -> {
		 	oSearch.addFilter(Filter.or(new Filter("refTipoCatalogo",tipoCatalogo)));
		});*/
		return oCatalogoDAO.search(oSearch);
	}

	@Override
	@Transactional
	public Catalogo obtieneCatalogoPorCodigo(String codigo) {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("codigo", codigo));
		oSearch.addFilter(Filter.equal("pasivo", false));
		return (Catalogo) oCatalogoDAO.searchUnique(oSearch);
	}
	
}
