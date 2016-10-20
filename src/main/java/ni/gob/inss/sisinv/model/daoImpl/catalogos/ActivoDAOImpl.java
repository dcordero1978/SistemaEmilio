package ni.gob.inss.sisinv.model.daoImpl.catalogos;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.catalogos.ActivoDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
@Repository
public class ActivoDAOImpl extends BaseGenericDAOImpl<Activos, Integer> implements ActivoDAO {

	@Override
	public String consecutivoActivoPorCuentaSubcuenta(Integer cuenta, Integer subCuenta) throws BusinessException {
		 Long consecutivo = (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Activos as tactivo "
		 													+ "inner join tactivo.secaf as tsecaf "		 													
		 													+ "where tsecaf.cuenta= :pCuenta "
		 													+ "and tsecaf.subcuenta = :pSubcuenta")
		 .setInteger("pCuenta", cuenta).setInteger("pSubcuenta",subCuenta).uniqueResult();
		 
		 if(consecutivo == null){
			 throw new BusinessException("ERROR AL GENERAR EL CONSECUTIVO.");
		 }
		int generado = consecutivo.intValue() + 1;
		return String.valueOf(generado);
	}
	
	@Override
	public List<Activos> ListadoActivosFiltro(Integer delegacionId, String codActivo, String descripcion, Integer estadoFisicoId){ 
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteriaCount = session.createCriteria(Activos.class);
		criteriaCount.setProjection(Projections.rowCount());
		Long count = (Long) criteriaCount.uniqueResult();
		
		
		Criteria criteria = session.createCriteria(Activos.class);
		criteria.setFirstResult(0);
		criteria.setMaxResults(count.intValue());
		if(delegacionId!=null){criteria.add(Restrictions.eq("ubicacionId", delegacionId));}
		if(codActivo!=null){criteria.add(Restrictions.ilike("codigoInventario", "%"+codActivo+"%"));}
		if(descripcion!=null){criteria.add(Restrictions.ilike("descripcion", "%"+descripcion+"%"));}
		if(estadoFisicoId!=null){criteria.add(Restrictions.eq("estadoFisicoId",estadoFisicoId));}
		
		List<Activos> lista = criteria.list();
		
		return lista;
		
	}
	
	@Override
	public List<Activos> buscar(String codigoSecaf, String codigoSecundario,String descripcionBien, String Serie,Integer ubicacionId, Integer estadoFisicoId, Integer tipoResguardoId){
		
		Criteria oCriteriaCount = sessionFactory.getCurrentSession().createCriteria(Activos.class);
		oCriteriaCount.setProjection(Projections.rowCount());
		Long cantidadResultados = (Long) oCriteriaCount.uniqueResult();
		
		Criteria oCriteria = sessionFactory.getCurrentSession().createCriteria(Activos.class);
		
		if(!StringUtils.isEmpty(codigoSecaf)){
			oCriteria.add(Restrictions.ilike("codigoInventario", "%"+codigoSecaf+"%"));
		}
		
		if(!StringUtils.isEmpty(codigoSecundario)){
			oCriteria.add(Restrictions.ilike("codigoSecundario", "%"+codigoSecundario+"%"));
		}
		
		if(!StringUtils.isEmpty(descripcionBien)){
			oCriteria.add(Restrictions.ilike("descripcion", "%"+descripcionBien+"%"));
		}
		
		if(ubicacionId!=null){
			oCriteria.add(Restrictions.eq("ubicacionId",ubicacionId));
		}
		
		if(estadoFisicoId!=null){
			oCriteria.add(Restrictions.eq("estadoFisicoId", estadoFisicoId));
		}
		
		if(tipoResguardoId!=null){
			oCriteria.add(Restrictions.eq("tipoResguardoId", tipoResguardoId));
		}
		
		oCriteria.setFirstResult(0);
		oCriteria.setMaxResults(cantidadResultados.intValue());
		return oCriteria.list();
		 
	}
	
	
}
