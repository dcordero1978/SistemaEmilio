package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.Sort;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.MarcaModeloService;
import ni.gob.inss.sisinv.model.dao.catalogos.MarcaModeloDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import com.googlecode.genericdao.search.Filter;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>SISINV</b></br>
 * <b>Copyright (c) 2016 MEFCCA.</b></br>
 * <b>Todos los derechos reservados.</b></br>
 * </br>
 *
 * @author CRISTHIAM JESUS MERCADO OBANDO
 * @version 1.0, 14/10/2016
 * @since 1.0 *
 */
@Service
public class MarcaModeloServiceImpl   implements MarcaModeloService {

    @Autowired
    private MarcaModeloDAO oMarcaModeloDAO;


    @Transactional
    @Override
    public void agregar(MarcasModelos oMarcaModelo) throws DAOException, BusinessException {
        oMarcaModeloDAO.saveUpper(oMarcaModelo);
    }
    
    @Transactional
    @Override
    public void actualizar(MarcasModelos oMarcaModelo) throws DAOException, BusinessException {
        oMarcaModeloDAO.updateUpper(oMarcaModelo);
    }

    @Transactional
    @Override
    public List<MarcasModelos> buscarMarcasOModelos(String txtCriterio,Integer marcaId, Boolean obtenerPasivos) {
        Search oSearch = new Search();
        oSearch.addFilterILike("descripcion",StringUtils.join("%",txtCriterio,"%"));
        oSearch.addFilter(Filter.equal("padreId", marcaId));
        oSearch.addFilter(Filter.equal("pasivo", obtenerPasivos));
        oSearch.addSort(Sort.asc("descripcion"));
    	return oMarcaModeloDAO.search(oSearch);
    }

    @Transactional
    @Override
    public MarcasModelos obtener(int id) throws EntityNotFoundException {
        return oMarcaModeloDAO.find(id);
    }

    @Transactional
    public void eliminar(MarcasModelos oMarcasModelos) {
        oMarcaModeloDAO.remove(oMarcasModelos);
    }

}
