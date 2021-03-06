package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.Sort;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.model.dao.DAOException;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.CaracteristicasHardwareService;
import ni.gob.inss.sisinv.model.dao.catalogos.CaracteristicasHardwareDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.CaracteristicasHardware;

@Service
public class CaracteristicasHardwareServiceImpl implements CaracteristicasHardwareService{

	@Autowired CaracteristicasHardwareDAO oCaracteristicasHardwareDao;
	
	@Transactional
	@Override
	public List<CaracteristicasHardware> listaCaracteristicasHardwarePadre(Boolean obtenerPasivos, String descripcion) throws EntityNotFoundException{
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("pasivo",  obtenerPasivos));
		oSearch.addFilter(Filter.isNull("caracteristicaPadreId"));
		oSearch.addFilter(Filter.ilike("descripcion", "%"+descripcion+"%"));
		oSearch.addSort(Sort.asc("descripcion"));
		return oCaracteristicasHardwareDao.search(oSearch);
	}
	
	@Transactional
	@Override
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwarePorPadreId(Boolean obtenerPasivos, Integer caracteristicaPadreId) {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("pasivo", obtenerPasivos));
		oSearch.addFilter(Filter.equal("caracteristicaPadreId", caracteristicaPadreId));
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

	@Transactional
	@Override
	public void guardar(CaracteristicasHardware oCaracteristicaHardware) throws BusinessException, DAOException {
		if(!this.obtieneListaCaracteristicasHardwarePorDescripcion(Boolean.FALSE, oCaracteristicaHardware.getDescripcion()).isEmpty()){
			throw new BusinessException("YA EXISTE UN REGISTRO CON ESTA DESCRIPCION.");
		}
		oCaracteristicasHardwareDao.saveUpper(oCaracteristicaHardware);
	}

	@Transactional
	@Override
	public void actualizar(CaracteristicasHardware oCaracteristicaHardware) throws BusinessException, DAOException {
		oCaracteristicasHardwareDao.updateUpper(oCaracteristicaHardware);
	}

	@Transactional
	@Override
	public List<CaracteristicasHardware> listaCaracteristicasHardwarePorPadreId(Integer padreId) throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("caracteristicaPadreId", padreId));
		return oCaracteristicasHardwareDao.search(oSearch);
	}
	
	@Transactional
	@Override
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwareDisponiblePorTipoActivoId(Integer tipoActivoId) {
		return oCaracteristicasHardwareDao.obtieneListaCaracteristicasHardwareDisponiblePorTipoActivoId(tipoActivoId);
	}

	@Transactional
	@Override
	public CaracteristicasHardware obtieneCaracteritisticaHardwarePorId(Integer caracteristicaId) {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("id", caracteristicaId));
		return (CaracteristicasHardware) oCaracteristicasHardwareDao.searchUnique(oSearch);
	}

	@Transactional
	@Override
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwareAsociadoActivo(Integer tipoActivoId) {
		return oCaracteristicasHardwareDao.obtieneListaCaracteristicasHardwareAgregadoPorTipoActivoId(tipoActivoId);
	}
	
	@Transactional
	@Override
	public List<CaracteristicasHardware> obtieneListaCaracteristicasHardwarePorDescripcion(Boolean obtenerPasivo, String descripcion){
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("descripcion", descripcion));
		oSearch.addFilter(Filter.equal("pasivo", obtenerPasivo));
		return oCaracteristicasHardwareDao.search(oSearch);
	}
}
