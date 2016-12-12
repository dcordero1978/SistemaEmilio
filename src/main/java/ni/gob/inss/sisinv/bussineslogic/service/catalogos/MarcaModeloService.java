package ni.gob.inss.sisinv.bussineslogic.service.catalogos;


import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;

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
public interface MarcaModeloService {

    public void agregar(MarcasModelos oMarcaModelo) throws DAOException, BusinessException;

    public void actualizar(MarcasModelos oMarcaModelo) throws DAOException, BusinessException;

    public List<MarcasModelos> buscar(String txtCriterio,String tipo,Integer idMarca);

    public List<MarcasModelos> buscarPorEstado(String txtCriterio, Boolean estado,String tipo,Integer idMarca);

    public MarcasModelos obtener(int id) throws EntityNotFoundException;

    public void eliminar(MarcasModelos oMarcasModelos);
}
