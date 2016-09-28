package ni.gob.inss.sisinv.model.daoImpl.catalogos;

import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.catalogos.SecafDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;

@Repository
public class SecafDAOImpl extends BaseGenericDAOImpl<Secaf, Integer> implements SecafDAO{

}