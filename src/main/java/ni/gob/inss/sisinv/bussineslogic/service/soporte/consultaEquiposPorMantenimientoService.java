package ni.gob.inss.sisinv.bussineslogic.service.soporte;

import java.util.List;
import java.util.Map;

public interface consultaEquiposPorMantenimientoService {
	
	public List<Map<String, Object>> buscarMantenimiento(Integer tipoMantenimiento, String nombreResponsableSoporte, Integer delegacion, java.util.Date fechaIni, java.util.Date fechaFin);

}
