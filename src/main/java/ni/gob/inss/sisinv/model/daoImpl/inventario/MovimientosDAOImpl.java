package ni.gob.inss.sisinv.model.daoImpl.inventario;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.inventario.MovimientosDAO;
import ni.gob.inss.sisinv.model.entity.inventario.Movimientos;

@Repository
public class MovimientosDAOImpl extends BaseGenericDAOImpl<Movimientos, Integer> implements MovimientosDAO {



	@SuppressWarnings("unchecked")
	@Override
	public List<Movimientos> buscar(Integer activoId) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteriaCount = session.createCriteria(Movimientos.class);
		criteriaCount.setProjection(Projections.rowCount());
		Long count = (Long) criteriaCount.uniqueResult();
		
		Criteria criteria =session.createCriteria(Movimientos.class);
		
		criteria.add(Restrictions.eq("activosId", activoId));
		criteria.setFirstResult(0);
		criteria.setMaxResults(count.intValue());
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
		
	}
 
}
