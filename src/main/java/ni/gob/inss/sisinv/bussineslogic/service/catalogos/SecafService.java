package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import java.util.List;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;

public interface SecafService {
	public List<Secaf> buscar(String txtCriterio) throws EntityNotFoundException;
	public Secaf obtener(int id) throws EntityNotFoundException;
	public void agregar(Secaf oSecaf) throws DAOException, BusinessException;
	public void actualizar(Secaf oSecaf) throws DAOException,BusinessException;
}
