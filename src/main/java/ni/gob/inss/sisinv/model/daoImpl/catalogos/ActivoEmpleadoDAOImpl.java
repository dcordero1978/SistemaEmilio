package ni.gob.inss.sisinv.model.daoImpl.catalogos;

import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoEmpleadoService;
import ni.gob.inss.sisinv.model.dao.catalogos.ActivoEmpleadoDAO;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosEmpleados;

@Repository
public class ActivoEmpleadoDAOImpl extends BaseGenericDAOImpl<ActivosEmpleados, Integer> implements ActivoEmpleadoDAO{

}
