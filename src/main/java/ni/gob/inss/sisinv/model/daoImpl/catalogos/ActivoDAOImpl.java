package ni.gob.inss.sisinv.model.daoImpl.catalogos;

import org.springframework.stereotype.Repository;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.daoImpl.BaseGenericDAOImpl;
import ni.gob.inss.sisinv.model.dao.catalogos.ActivoDAO;
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
	
}
