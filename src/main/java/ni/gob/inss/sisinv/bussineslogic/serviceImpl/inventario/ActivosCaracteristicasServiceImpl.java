package ni.gob.inss.sisinv.bussineslogic.serviceImpl.inventario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.sisinv.bussineslogic.service.inventario.ActivosCaracteristicasService;
import ni.gob.inss.sisinv.model.dao.inventario.ActivosCaracteristicasDAO;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosCaracteristicas;

@Service
public class ActivosCaracteristicasServiceImpl implements ActivosCaracteristicasService {

	@Autowired
	ActivosCaracteristicasDAO oCaracteristicasDao;
	
	@Transactional
	@Override
	public List<ActivosCaracteristicas> obtieneListaCaracteristicasActivo(Integer activoId) {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("activoId", activoId));
		oSearch.addFilter(Filter.equal("pasivo", false));
		return oCaracteristicasDao.search(oSearch);
	}

}
