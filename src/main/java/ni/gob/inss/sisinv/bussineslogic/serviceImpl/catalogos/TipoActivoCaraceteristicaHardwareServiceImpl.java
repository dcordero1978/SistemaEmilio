package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.TipoActivoCaraceteristicaHardwareService;
import ni.gob.inss.sisinv.model.dao.catalogos.TipoActivoCaracteristicasHardwareDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.TipoActivoCaracteristicasHardware;

@Service
public class TipoActivoCaraceteristicaHardwareServiceImpl implements TipoActivoCaraceteristicaHardwareService{

	@Autowired
	TipoActivoCaracteristicasHardwareDAO oTipoActivoCaracteristicaHardwareDao;
	
	@Transactional
	@Override
	public TipoActivoCaracteristicasHardware obtieneTipoActivoCaracteristicaHardware(Integer tipoActivoId, Integer caracteristicaPadreId) {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("tipoActivoId", tipoActivoId));
		oSearch.addFilter(Filter.equal("caracteristicaPadreId", caracteristicaPadreId));
		return (TipoActivoCaracteristicasHardware) oTipoActivoCaracteristicaHardwareDao.searchUnique(oSearch);
	}

	@Transactional
	@Override
	public void guardar(TipoActivoCaracteristicasHardware oTipoActivoCaracteristicaHardware) throws DAOException {
		oTipoActivoCaracteristicaHardwareDao.saveUpper(oTipoActivoCaracteristicaHardware);
		
	}

	@Transactional
	@Override
	public void actualizar(TipoActivoCaracteristicasHardware oTipoActivoCaracteristicaHardware) throws DAOException {
		oTipoActivoCaracteristicaHardwareDao.updateUpper(oTipoActivoCaracteristicaHardware);
	}
	
}
