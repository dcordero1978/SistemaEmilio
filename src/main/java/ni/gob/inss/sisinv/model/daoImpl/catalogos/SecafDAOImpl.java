package ni.gob.inss.sisinv.model.daoImpl.catalogos;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.catalogos.SecafDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Secaf;

@Repository
public class SecafDAOImpl extends BaseGenericDAOImpl<Secaf, Integer> implements SecafDAO{

	@Override
	public List<Secaf> obtieneListaCatalogoSecafPorDescripcion(String descripcion) {
		Query oQuery =  this.sessionFactory.getCurrentSession().getNamedQuery("selectSecafPorDescripcion").setString("descripcion", descripcion);
		return oQuery.list();
	}

}
