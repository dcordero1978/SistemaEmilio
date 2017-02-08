package ni.gob.inss.sisinv.bussineslogic.serviceImpl.soporte;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ni.gob.inss.sisinv.bussineslogic.service.soporte.consultaEquiposPorMantenimientoService;
import ni.gob.inss.sisinv.model.dao.soporte.consultaEquiposPorMantenimientoDAO;



@Service
public class consultaEquiposPorMantenimientoServiceImpl implements consultaEquiposPorMantenimientoService{
	
	@Autowired
	consultaEquiposPorMantenimientoDAO oConsultaEquiposPorMantenimientoDAO;
	
	@Transactional
	@Override
	public List<Map<String, Object>> buscarMantenimiento(Integer tipoMantenimiento, String nombreResponsableSoporte, Integer delegacion, java.util.Date fechaIni, java.util.Date fechaFin){
		
		return oConsultaEquiposPorMantenimientoDAO.buscarMantenimiento(tipoMantenimiento, nombreResponsableSoporte, delegacion, fechaIni, fechaFin);
	
	}

}
