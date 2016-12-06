package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.model.entity.catalogo.TipoActivoCaracteristicasHardware;

public interface TipoActivoCaraceteristicaHardwareService {
	public TipoActivoCaracteristicasHardware obtieneTipoActivoCaracteristicaHardware(Integer tipoActivoId, Integer caracteristicaPadreId);
	public void guardar(TipoActivoCaracteristicasHardware oTipoActivoCaracteristicaHardware) throws DAOException;
	public void actualizar(TipoActivoCaracteristicasHardware oTipoActivoCaracteristicaHardware) throws DAOException;
}
