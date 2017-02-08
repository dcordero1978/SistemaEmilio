package ni.gob.inss.sisinv.model.dao.soporte;

import java.util.List;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.sisinv.model.entity.soporte.ActivosUsuario;
import ni.gob.inss.sisinv.model.entity.soporte.Mantenimientos;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

public interface MantenimientosDAO extends BaseGenericDAO<ProgramacionMantenimiento, Integer> {

	public List<Mantenimientos> listaMantenimientos(Integer estadoId);

	public List<ActivosUsuario> listaActivosUsuario(Integer empleadoId, Integer tipoMantenimientoId);



}
