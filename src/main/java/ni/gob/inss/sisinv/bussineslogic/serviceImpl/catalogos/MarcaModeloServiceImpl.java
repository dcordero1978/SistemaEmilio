package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import com.googlecode.genericdao.search.Search;
import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.MarcaModeloService;
import ni.gob.inss.sisinv.model.dao.catalogos.MarcaModeloDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import com.googlecode.genericdao.search.Filter;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
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
    public List<MarcasModelos> buscar(String txtCriterio,String tipo,Integer idMarca) {
        return this.buscarPorEstado(txtCriterio,  (Boolean) null,tipo,idMarca);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<MarcasModelos> buscarPorEstado(String txtCriterio, Boolean estado,String tipo,Integer idMarca) {
        List<MarcasModelos> listaMarcas = null;

        Search oSearch = new Search();
        oSearch.addFilterILike("descripcion", "%"+txtCriterio+"%");

        if(estado != null)
        {
            oSearch.addFilter(Filter.equal("pasivo",estado));
        }

        if (tipo=="MARCA"){
            oSearch.addFilter(Filter.equal("padreId",0));
        } else {
            oSearch.addFilter(Filter.equal("padreId",idMarca));
        }


        oSearch.addSortAsc("descripcion");

        listaMarcas = oMarcaModeloDAO.search(oSearch);


    /*    if((txtCriterio==null || "".equalsIgnoreCase(txtCriterio)) && estado ==null ){
            listaMarcas = oMarcaModeloDAO.findAll();
        }else {
            listaMarcas = oMarcaModeloDAO.search(oSearch);
        }*/

        return listaMarcas;

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
