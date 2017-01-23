package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import java.util.List;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

public interface ActivoService {
	public void guardar(Activos oActivo) throws BusinessException, EntityNotFoundException, DAOException;
	public void actualizar(Activos oActivo) throws BusinessException, DAOException;
	public List<Activos> obtenerListaActivosPorEmpleado(Integer EmpleadoId) throws EntityNotFoundException;
	public List<Activos> buscar(Integer delegacionId, String codActivo, String descripcion, Integer estadoFisicoId,Integer proyectoId);
	public List<Activos> buscar(String codigoSecaf, String codigoSecundario,String descripcionBien, String Serie,Integer ubicacionId, Integer estadoFisicoId, Integer tipoResguardoId);
	public Activos obtener(int activoId) throws EntityNotFoundException;
	public List<Activos> obtenerListaDeActivosClasificadosComoEquiposInformaticos(Integer empleadoId, boolean pasivo);
}
 