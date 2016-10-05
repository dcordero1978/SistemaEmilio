package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import java.util.List;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.catalogo.Delegacion;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

public interface DelegacionService {
	
	public void agregar(Delegacion oDelegacion) throws DAOException, BusinessException;
	
	public void actualizar(Delegacion oDelegacion) throws DAOException, BusinessException;
	
	public List<Delegacion> buscar(String txtCriterio);
	
	public Delegacion obtener(int id) throws EntityNotFoundException;

	public List<Delegacion> listaUbicacionesPorDepartamento(Integer departamentoId);
	
	public List<Delegacion> listaUbicacionesEmpleado(Empleado oEmpleado);
	
	public List<Delegacion> buscarPorEstado(String txtCriterio, Boolean estado);
	
}
