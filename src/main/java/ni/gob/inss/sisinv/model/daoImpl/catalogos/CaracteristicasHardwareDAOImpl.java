package ni.gob.inss.sisinv.model.daoImpl.catalogos;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.catalogos.CaracteristicasHardwareDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;

@Repository
public class CaracteristicasHardwareDAOImpl extends BaseGenericDAOImpl<CaracteristicasHardware,Integer> implements CaracteristicasHardwareDAO  {

	@SuppressWarnings("unchecked")
	@Override
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwareDisponiblePorTipoActivoId(Integer tipoActivoId) {
		String nativeQuery = "select tcaracteristicas.* from catalogo.caracteristicas_hardware  tcaracteristicas "
						+ "left join catalogo.tipo_activo_caracteristicas_hardware tactivocaracteristicas "
						+ "on tcaracteristicas.id = tactivocaracteristicas.caracteristica_padre_id "
						+ "and tactivocaracteristicas.tipo_activo_id = :tipoActivoId "
						+ "and tactivocaracteristicas.pasivo is false "
						+ "where tcaracteristicas.caracteristica_padre_id is null "
						+ "and tcaracteristicas.pasivo is false  "
						+ "and tactivocaracteristicas.id is null "
						+ "order by descripcion";
		Session oSession = this.sessionFactory.getCurrentSession();
		return oSession.createSQLQuery(nativeQuery).addEntity(CaracteristicasHardware.class).setInteger("tipoActivoId", tipoActivoId).list();
	}

	
	@Override
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwareAgregadoPorTipoActivoId(
			Integer tipoActivoId) {
		String nativeQuery = "select tcaracteristicas.* from catalogo.caracteristicas_hardware  tcaracteristicas "
								+ "inner join catalogo.tipo_activo_caracteristicas_hardware tactivocaracteristicas  "
								+ "on tcaracteristicas.id = tactivocaracteristicas.caracteristica_padre_id  "
								+ "and tactivocaracteristicas.tipo_activo_id = :tipoActivoId  and tactivocaracteristicas.pasivo is false "
								+ "and tcaracteristicas.pasivo is false";
		Session oSession = this.sessionFactory.getCurrentSession();
		
		return oSession.createSQLQuery(nativeQuery).addEntity(CaracteristicasHardware.class).setInteger("tipoActivoId", tipoActivoId).list();
	}

}
