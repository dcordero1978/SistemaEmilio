package ni.gob.inss.sisinv.model.dao.catalogos;

import java.util.List;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;

public interface SecafDAO extends BaseGenericDAO<Secaf, Integer>{
	public List<Secaf> obtieneListaCatalogoSecafPorDescripcion(String descripcion);
}
