package ni.gob.inss.sisinv.model.dao.catalogos;

import java.util.List;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;

public interface ActivoDAO extends BaseGenericDAO<Activos, Integer>  {
	public String consecutivoActivoPorCuentaSubcuenta(Integer cuenta, Integer subCuenta) throws BusinessException;

	public List<Activos> ListadoActivosFiltro(Integer delegacionId, String codActivo, String descripcion, Integer estadoFisicoI);
}
