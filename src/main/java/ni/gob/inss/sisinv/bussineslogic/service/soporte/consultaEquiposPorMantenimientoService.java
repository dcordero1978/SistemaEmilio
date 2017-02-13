package ni.gob.inss.sisinv.bussineslogic.service.soporte;

import java.util.List;
import java.util.Map;

public interface consultaEquiposPorMantenimientoService {
	
	public List<Map<String, Object>> buscarMantenimiento(Integer tipoMantenimiento, Integer delegacion, java.util.Date fechaInicioI, java.util.Date fechaInicioF, java.util.Date fechaEntregaI, java.util.Date fechaEntregaF);

}
