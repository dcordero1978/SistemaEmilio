package ni.gob.inss.sisinv.model.daoImpl.catalogos;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.catalogos.EmpleadoDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

@Repository
public class EmpleadoDAOImpl extends BaseGenericDAOImpl<Empleado, Integer> implements EmpleadoDAO {
	
	@Override
	public List<Empleado> buscar(String criterioNombre, Integer delegacionId, Boolean pasivo, 
								String cargo, String area,String numeroIdentificacion,
								String numeroEmpleado) {
		
		Criteria oCriteriaCount = sessionFactory.getCurrentSession().createCriteria(Empleado.class);
		oCriteriaCount.setProjection(Projections.rowCount());
		Long cantidadResultados = (Long) oCriteriaCount.uniqueResult();
		
		Criteria oCriteria = sessionFactory.getCurrentSession().createCriteria(Empleado.class);
	
		if(!StringUtils.isEmpty(criterioNombre)){
			oCriteria.add(Restrictions.ilike("nombreCompleto", "%"+criterioNombre+"%"));
		} 
		if(!StringUtils.isEmpty(cargo)){
			oCriteria.add(Restrictions.ilike("cargo", "%"+cargo+"%"));
		}
		if(!StringUtils.isEmpty(area)){
			oCriteria.add(Restrictions.ilike("area", "%"+area+"%"));
		}
		if(!StringUtils.isEmpty(numeroIdentificacion)){
			oCriteria.add(Restrictions.eq("nroIdentificacion", numeroIdentificacion));
		}
		if(!StringUtils.isEmpty(numeroEmpleado)){
			oCriteria.add(Restrictions.eq("numeroEmpleado", numeroEmpleado));
		}
		if(delegacionId != null){
			oCriteria.add(Restrictions.eq("delegacionId", delegacionId));
		}
		if(pasivo != null){
			oCriteria.add(Restrictions.eq("pasivo", pasivo));
		}
		
		oCriteria.setFirstResult(0);
		oCriteria.setMaxResults(cantidadResultados.intValue());
		return oCriteria.list();
	}
	
	

}
