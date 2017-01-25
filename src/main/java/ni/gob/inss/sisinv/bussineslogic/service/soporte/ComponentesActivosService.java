package ni.gob.inss.sisinv.bussineslogic.service.soporte;

import java.util.List;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.model.entity.soporte.ComponentesActivos;

public interface ComponentesActivosService {
	public void guardar(ComponentesActivos oComponenteActivo) throws DAOException;
	public List<ComponentesActivos> obtenerListaComponentes(Integer activoId, Boolean esPasivo);
	public void modificar(ComponentesActivos oComponenteActivo) throws DAOException;
}
