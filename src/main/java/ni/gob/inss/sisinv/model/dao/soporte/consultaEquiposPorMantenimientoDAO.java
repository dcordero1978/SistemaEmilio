package ni.gob.inss.sisinv.model.dao.soporte;

import java.util.List;
import java.util.Map;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

public interface consultaEquiposPorMantenimientoDAO extends BaseGenericDAO<ProgramacionMantenimiento, Integer> {
	
	public List<Map<String, Object>> buscarMantenimiento(Integer tipoMantenimiento, Integer delegacion, java.util.Date fechaInicioI, java.util.Date fechaInicioF, java.util.Date fechaEntregaI, java.util.Date fechaEntregaF);

}
