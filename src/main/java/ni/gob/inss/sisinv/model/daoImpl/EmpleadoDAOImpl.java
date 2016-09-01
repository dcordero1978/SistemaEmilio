package ni.gob.inss.sisinv.model.daoImpl;

import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.EmpleadoDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Repository
public class EmpleadoDAOImpl extends BaseGenericDAOImpl<Empleado, Integer> implements EmpleadoDAO {

}
