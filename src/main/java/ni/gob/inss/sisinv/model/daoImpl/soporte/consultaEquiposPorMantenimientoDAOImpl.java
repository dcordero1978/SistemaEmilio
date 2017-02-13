package ni.gob.inss.sisinv.model.daoImpl.soporte;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.soporte.consultaEquiposPorMantenimientoDAO;
import ni.gob.inss.sisinv.model.entity.soporte.ProgramacionMantenimiento;

@Repository
public class consultaEquiposPorMantenimientoDAOImpl extends BaseGenericDAOImpl<ProgramacionMantenimiento, Integer> implements consultaEquiposPorMantenimientoDAO{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> buscarMantenimiento(Integer tipoMantenimiento, Integer delegacion, java.util.Date fechaInicioI, java.util.Date fechaInicioF, java.util.Date fechaEntregaI, java.util.Date fechaEntregaF){
		StringBuilder SQLwhere = new StringBuilder();
		if (tipoMantenimiento != null)
			SQLwhere.append("and ");
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(
	"select me.id, " + 
			"me.tipo_mantenimiento_id,  tm.descripcion as descripcion_tipo_mantenimiento, " +
			"me.responsable_soporte_id, coalesce(rs.primer_nombre, '') || ' ' || coalesce(rs.segundo_nombre, '') || ' ' || coalesce(rs.primer_apellido, '') || ' ' || coalesce(rs.segundo_apellido, '') as nombre_responsable_soporte, " + 
			"me.delegacion_id, d.descripcion as nombre_delegacion, " +
			"me.entidad_id, ent.nombre as nombre_entidad, " +
			"me.fecha_inicio, me.fecha_entrega " +
	"from soportetecnico.mantenimiento_equipo as me left join " +
			//"catalogo.empleado e on e.id = me.empleado_id inner join " +
			"catalogo.empleado rs on rs.id = me.responsable_soporte_id left join " +
			//"catalogo.empleado ts on ts.id = me.tecnico_soporte_id inner join  " +
			"(select * from catalogo.catalogo where ref_tipo_catalogo = 'TME') tm on tm.id = me.tipo_mantenimiento_id inner join " +
			"catalogo.delegacion d on d.id = me.delegacion_id inner join " +
			"infraestructura.entidad ent on ent.id = me.entidad_id " +
	"where me.pasivo = false and " +
			"me.tipo_mantenimiento_id = COALESCE(:pTipoMantenimiento, me.tipo_mantenimiento_id) and " +
			"me.delegacion_id = COALESCE(:pDelegacion, me.delegacion_id) and " +
			"COALESCE(me.fecha_inicio, current_date) between COALESCE(:pFechaInicioI, COALESCE(me.fecha_inicio, current_date)) and COALESCE(:pFechaInicioF, COALESCE(me.fecha_inicio, current_date)) and " +
			"COALESCE(me.fecha_entrega,current_date) between COALESCE(:pFechaEntregaI, COALESCE(me.fecha_entrega,current_date)) and COALESCE(:pFechaEntregaF, COALESCE(me.fecha_entrega,current_date)) " +
	"group by me.id,  " +
			"me.tipo_mantenimiento_id, tm.descripcion, " +
			"me.responsable_soporte_id, coalesce(rs.primer_nombre, '') || ' ' || coalesce(rs.segundo_nombre, '') || ' ' || coalesce(rs.primer_apellido, '') || ' ' || coalesce(rs.segundo_apellido, '') , " + 
			"me.delegacion_id, d.descripcion, " +
			"me.entidad_id, ent.nombre, " +
			"me.delegacion_id, me.fecha_inicio, me.fecha_entrega");
		
		//sqlQuery.setResultTransformer(Transformers.aliasToBean(ActivosUsuario.class));
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		//sqlQuery.setInteger("pTipoMantenimiento", tipoMantenimiento);
		sqlQuery.setParameter("pTipoMantenimiento", tipoMantenimiento, StandardBasicTypes.INTEGER);
		sqlQuery.setParameter("pDelegacion", delegacion, StandardBasicTypes.INTEGER);
		sqlQuery.setParameter("pFechaInicioI", fechaInicioI, StandardBasicTypes.DATE);
		sqlQuery.setParameter("pFechaInicioF", fechaInicioF, StandardBasicTypes.DATE);
		sqlQuery.setParameter("pFechaEntregaI", fechaEntregaI, StandardBasicTypes.DATE);
		sqlQuery.setParameter("pFechaEntregaF", fechaEntregaF, StandardBasicTypes.DATE);
		
		return sqlQuery.list();
		
	}

}
