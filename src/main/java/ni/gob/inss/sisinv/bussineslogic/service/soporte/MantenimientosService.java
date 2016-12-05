package ni.gob.inss.sisinv.bussineslogic.service.soporte;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.model.entity.soporte.Mantenimientos;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

public interface MantenimientosService {

	public void guardarProgramacion(ProgramacionMantenimiento oProgramacionMto) throws DAOException;

	public List<ProgramacionMantenimiento> buscar() throws EntityNotFoundException;

	public List<Mantenimientos> obtenerlistaMantenimientos(Integer estadoId);

	public ProgramacionMantenimiento obtener(int mantenimientoId) throws EntityNotFoundException, ni.gob.inss.barista.model.dao.EntityNotFoundException;


}
