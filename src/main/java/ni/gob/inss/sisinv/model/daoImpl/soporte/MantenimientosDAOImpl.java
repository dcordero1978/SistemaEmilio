package ni.gob.inss.sisinv.model.daoImpl.soporte;

import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.soporte.MantenimientosDAO;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

@Repository
public class MantenimientosDAOImpl  extends BaseGenericDAOImpl<ProgramacionMantenimiento, Integer> implements MantenimientosDAO{

}
