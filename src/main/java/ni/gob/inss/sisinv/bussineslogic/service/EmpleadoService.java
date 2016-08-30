package ni.gob.inss.sisinv.bussineslogic.service;

import java.util.List;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

public interface EmpleadoService {
	public List<Empleado> buscar(String criterioBusqueda);
	public void agregar(Empleado oEmpleado) throws DAOException, BusinessException;
	public Empleado obtener(int empleadoId) throws ni.gob.inss.barista.model.dao.EntityNotFoundException;
}
