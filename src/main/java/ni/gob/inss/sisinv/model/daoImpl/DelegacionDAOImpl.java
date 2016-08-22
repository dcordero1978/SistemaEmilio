package ni.gob.inss.sisinv.model.daoImpl;

import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.DelegacionDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;

@Repository
public class DelegacionDAOImpl extends BaseGenericDAOImpl<Delegacion, Integer> implements DelegacionDAO {

}
