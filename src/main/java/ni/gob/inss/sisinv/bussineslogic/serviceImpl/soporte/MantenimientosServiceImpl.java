package ni.gob.inss.sisinv.bussineslogic.serviceImpl.soporte;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.soporte.MantenimientosService;
import ni.gob.inss.sisinv.model.dao.soporte.MantenimientosDAO;
import ni.gob.inss.sisinv.model.entity.soporte.ActivosUsuario;
import ni.gob.inss.sisinv.model.entity.soporte.MantenimientoEquipo;
import ni.gob.inss.sisinv.model.entity.soporte.MantenimientoEquipoDetalle;
import ni.gob.inss.sisinv.model.entity.soporte.Mantenimientos;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;


@Service
public class MantenimientosServiceImpl implements MantenimientosService  {
	
	@Autowired
	MantenimientosDAO oMantenimientosDAO;
	
	@Transactional
	@Override
	public  List<ProgramacionMantenimiento> buscar() throws EntityNotFoundException{
		return oMantenimientosDAO.findAll();
	}
	
	@Transactional
	@Override
	public List<Mantenimientos> obtenerlistaMantenimientos(Integer estadoId){
		return oMantenimientosDAO.listaMantenimientos(estadoId);
	}

	
	@Transactional
	@Override
	public void guardarProgramacion (ProgramacionMantenimiento oProgramacionMto) throws DAOException {
		oMantenimientosDAO.saveUpper(oProgramacionMto);
	}
	
	@Transactional
	@Override
	public ProgramacionMantenimiento obtener(int mantenimientoId) throws ni.gob.inss.barista.model.dao.EntityNotFoundException  {
		return oMantenimientosDAO.find(mantenimientoId);
	}
	
	@Transactional
	@Override
	public List<ActivosUsuario> obtenerListaActivosUsuarios(Integer empleadoId, Integer tipoMantenimientoId){ 
		return oMantenimientosDAO.listaActivosUsuario(empleadoId, tipoMantenimientoId);
	}
	
	@Transactional
	@Override
	public void guardarFichaMantenimientoMaestro(MantenimientoEquipo oMantenimientoEquipo) throws DAOException{
		oMantenimientosDAO.saveUpper(oMantenimientoEquipo);
	}

	@Transactional
	@Override
	public void guardarFichaMantenimientoDetalle(MantenimientoEquipoDetalle oMantenimientoEquipoDetalle) throws DAOException{
		oMantenimientosDAO.saveUpper(oMantenimientoEquipoDetalle);
	}
	

}
