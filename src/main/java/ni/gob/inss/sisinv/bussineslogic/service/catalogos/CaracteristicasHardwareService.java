package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import java.util.List;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;

public interface CaracteristicasHardwareService {
	public List<CaracteristicasHardware> listaCaracteristicasHardwarePadre(Boolean obtenerPasivos) throws EntityNotFoundException;
	public List<CaracteristicasHardware> listaCaracteristicasPorDescripcion(String descripcion, Boolean pasivo) throws EntityNotFoundException;
	public void guardar(CaracteristicasHardware oCaracteristicaHardware) throws BusinessException, DAOException;
	public void actualizar(CaracteristicasHardware oCaracteristicaHardware) throws BusinessException, DAOException;
	List<CaracteristicasHardware> listaCaracteristicasHardwarePorPadreId(Integer padreId)
			throws EntityNotFoundException;
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwareDisponiblePorTipoActivoId(Integer tipoActivoId);
	public CaracteristicasHardware obtieneCaracteritisticaHardwarePorId(Integer caracteristicaId);
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwareAsociadoActivo(Integer tipoActivoId);
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwarePorPadreId(Boolean obtenerPasivos, Integer caracteristicaPadreId);
	List<CaracteristicasHardware> obtieneListaCaracteristicasHardwarePorDescripcion(Boolean obtenerPasivo,
			String descripcion);
}
