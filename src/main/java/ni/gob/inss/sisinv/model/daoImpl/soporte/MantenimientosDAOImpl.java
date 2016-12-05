package ni.gob.inss.sisinv.model.daoImpl.soporte;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.soporte.MantenimientosDAO;
import ni.gob.inss.sisinv.model.entity.soporte.Mantenimientos;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;


@Repository
public class MantenimientosDAOImpl  extends BaseGenericDAOImpl<ProgramacionMantenimiento, Integer> implements MantenimientosDAO{

	@SuppressWarnings("unchecked") 
	@Override
	public List<Mantenimientos> listaMantenimientos(Integer estadoId){
		Integer estado= estadoId;
		List<Mantenimientos> listaMantenimientos = sessionFactory.getCurrentSession().createSQLQuery("select id, asunto,delegacion,fechaInicio,fechaFin,estadoId,estado from soportetecnico.vw_lista_mantenimientos where estadoId=:pEstado order by fechaInicio ")
																		.addScalar("id")
																		.addScalar("asunto")
																		.addScalar("delegacion")
																		.addScalar("fechaInicio")
																		.addScalar("fechaFin")
																		.addScalar("estadoId")
																		.addScalar("estado")
																		.setResultTransformer(Transformers.aliasToBean(Mantenimientos.class))
																		.setInteger("pEstado", estado)
																		.list();
																		
		return listaMantenimientos;
		
	}
}
