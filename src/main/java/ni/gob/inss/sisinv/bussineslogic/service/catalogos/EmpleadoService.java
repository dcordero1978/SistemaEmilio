package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import java.util.List;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

public interface EmpleadoService {
	public List<Empleado> buscar(String criterioBusqueda, Integer delegacionId) throws EntityNotFoundException;
	public List<Empleado> buscar(String criterioBusqueda, Integer delegacionId, Boolean pasivo) throws EntityNotFoundException;
	public List<Empleado> buscar(String criterioNombre, Integer delegacion, Boolean pasivo, String cargo,  String area, String numeroIdentificacion, String numeroEmpleado) throws EntityNotFoundException;
	public void agregar(Empleado oEmpleado) throws DAOException, BusinessException;
	public Empleado obtener(int empleadoId) throws ni.gob.inss.barista.model.dao.EntityNotFoundException;
	public void actualizar(Empleado oEmpleado)throws DAOException, BusinessException;
	public List<Empleado> buscarTecnicosSoporte() throws EntityNotFoundException;
}
