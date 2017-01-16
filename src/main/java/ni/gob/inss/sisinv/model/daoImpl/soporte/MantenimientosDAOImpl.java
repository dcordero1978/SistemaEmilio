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
	public List<ActivosUsuario> listaActivosUsuario(Integer empleadoId){
		List<ActivosUsuario> listaActivosUsuario = sessionFactory.getCurrentSession().createSQLQuery("select empleado_id as empleadoId,numero_empleado as numeroEmpleado, nombre_empleado as nombreEmpleado,cargo, area, ubicacion,id as activoId, codigo_inventario as codigoInventario, digito_auxiliar as digitoAuxiliar, codigo_secundario as codigoSecundario, "+
																									 " descripcion_bien as descripcionBien, marca, modelo, serie, color, fecha_adquisicion as fechaAdquisicion, estado_fisico as estadoFisico, tipo_resguardo as tipoResguardo, numero_proyecto as numeroProyecto, numero_bodega as numeroBodega, valor, "+
																									 " tipo_moneda as tipoMoneda, lote,estado, tipo_activo as tipoActivo,tipoactivo as tipoActivoDesc, tipo_mantenimiento as tipoMantenimiento, tipomantenimiento as tipoMantenimientoDesc from inventario.vw_bienesxempleado where empleado_id=:pEmpleadoId  and tipo_mantenimiento=1004")
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
																					 .list();
		return listaActivosUsuario;
	}
}
