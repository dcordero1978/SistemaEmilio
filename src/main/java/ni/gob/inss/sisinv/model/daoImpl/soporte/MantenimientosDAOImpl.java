package ni.gob.inss.sisinv.model.daoImpl.soporte;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.soporte.MantenimientosDAO;
import ni.gob.inss.sisinv.model.entity.soporte.ActivosUsuario;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActivosUsuario> listaActivosUsuario(Integer empleadoId, Integer tipoMantenimientoId){
		List<ActivosUsuario> listaActivosUsuario = sessionFactory.getCurrentSession().createSQLQuery("select empleadoId,numeroEmpleado,nombreEmpleado,cargo,area, ubicacion, activoId,codigoInventario,digitoAuxiliar, codigoSecundario, descripcionBien, marca,modelo, serie,color, fechaAdquisicion, estadoFisico,tipoResguardo, "+
																									 " numeroProyecto, numeroBodega, valor, tipoMoneda, lote, estado,tipoActivo, tipoActivoDesc, tipoMantenimiento, tipoMantenimientoDesc from soportetecnico.activosasociadosusuario(:pEmpleadoId,:pTipoMantenimiento)")
																					 .addScalar("empleadoId")
																					 .addScalar("numeroEmpleado")
																					 .addScalar("nombreEmpleado")
																					 .addScalar("cargo")
																					 .addScalar("area")
																					 .addScalar("ubicacion")
																					 .addScalar("activoId")
																					 .addScalar("codigoInventario")
																					 .addScalar("digitoAuxiliar")
																					 .addScalar("codigoSecundario")
																					 .addScalar("descripcionBien")
																					 .addScalar("marca")
																					 .addScalar("modelo")
																					 .addScalar("serie")
																					 .addScalar("color")
																					 .addScalar("fechaAdquisicion")
																					 .addScalar("estadoFisico")
																					 .addScalar("tipoResguardo")
																					 .addScalar("numeroProyecto")
																					 .addScalar("numeroBodega")
																					 .addScalar("valor")
																					 .addScalar("tipoMoneda")
																					 .addScalar("lote")
																					 .addScalar("estado")
																					 .addScalar("tipoActivo")
																					 .addScalar("tipoActivoDesc")
																					 .addScalar("tipoMantenimiento")
																					 .addScalar("tipoMantenimientoDesc")
																					 .setResultTransformer(Transformers.aliasToBean(ActivosUsuario.class))
																					 .setInteger("pEmpleadoId", empleadoId)
																					 .setInteger("pTipoMantenimiento", tipoMantenimientoId)
																					 .list();
		return listaActivosUsuario;
	}
}
