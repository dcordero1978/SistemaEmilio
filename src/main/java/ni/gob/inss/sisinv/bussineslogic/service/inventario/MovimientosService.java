package ni.gob.inss.sisinv.bussineslogic.service.inventario;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.model.entity.inventario.Movimientos;

public interface MovimientosService {

	public void guardar(Movimientos oMovimientos) throws EntityNotFoundException, DAOException;
	
	public List<Movimientos> buscar(Integer activoId) throws EntityNotFoundException, ni.gob.inss.barista.model.dao.EntityNotFoundException;

}
