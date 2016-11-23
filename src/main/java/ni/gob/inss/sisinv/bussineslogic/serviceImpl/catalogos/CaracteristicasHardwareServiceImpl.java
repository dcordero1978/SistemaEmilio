package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.Sort;

import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CaracteristicasHardwareService;
import ni.gob.inss.sisinv.model.dao.catalogos.CaracteristicasHardwareDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;

@Service
public class CaracteristicasHardwareServiceImpl implements CaracteristicasHardwareService{

	@Autowired CaracteristicasHardwareDAO oCaracteristicasHardwareDao;
	
	@Transactional
	@Override
	public List<CaracteristicasHardware> listaCaracteristicasHardwarePadre() throws EntityNotFoundException{
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("pasivo", false));
		oSearch.addFilter(Filter.isNull("caracteristicaPadreId"));
		oSearch.addSort(Sort.asc("descripcion"));
		return oCaracteristicasHardwareDao.search(oSearch);
	}

	@Transactional
	@Override
	public List<CaracteristicasHardware> listaCaracteristicasPorDescripcion(String descripcion, Boolean pasivo)
			throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.ilike("descripcion", "%"+descripcion+"%"));
		oSearch.addFilter(Filter.equal("pasivo", pasivo));
		oSearch.addSort(Sort.asc("descripcion"));
		return oCaracteristicasHardwareDao.search(oSearch);
	}


}
