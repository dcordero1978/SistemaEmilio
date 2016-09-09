package ni.gob.inss.sisinv.bussineslogic.service;

import java.util.List;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;

public interface SecafService {
	public List<Secaf> buscar(String txtCriterio) throws EntityNotFoundException;
}
