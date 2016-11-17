package ni.gob.inss.sisinv.model.dao.inventario;

import java.util.List;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.inventario.Movimientos;

public interface MovimientosDAO extends BaseGenericDAO<Movimientos, Integer>{
	
public List<Movimientos> buscar(Integer activoId) throws EntityNotFoundException;
}

