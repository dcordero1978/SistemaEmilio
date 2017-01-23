package ni.gob.inss.sisinv.bussineslogic.serviceImpl.catalogos;

import java.util.ArrayList;
import java.util.Date;
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
import ni.gob.inss.barista.model.dao.catalogos.CatalogoDAO;
import ni.gob.inss.barista.model.dao.seguridad.ParametroDAO;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.barista.model.entity.seguridad.Parametro;
import ni.gob.inss.sisinv.bussineslogic.service.catalogos.ActivoService;
import ni.gob.inss.sisinv.model.dao.catalogos.ActivoDAO;
import ni.gob.inss.sisinv.model.dao.catalogos.ActivoEmpleadoDAO;
import ni.gob.inss.sisinv.model.entity.inventario.Activos;
import ni.gob.inss.sisinv.model.entity.inventario.ActivosEmpleados;

@Service
public class ActivoServiceImpl implements ActivoService {

	@Autowired
	ActivoDAO oActivoDAO;
	
	@Autowired
	ParametroDAO oParametroDAO;
	
	@Autowired
	CatalogoDAO oCatalogoDAO;
	
	@Autowired
	ActivoEmpleadoDAO oActivoEmpleadoDAO;

	@Transactional
	@Override
	public List<Activos> obtenerListaActivosPorEmpleado(Integer empleadoId) throws EntityNotFoundException {
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("empleadoId", empleadoId));
		oSearch.addSort(Sort.asc("descripcion"));
		return oActivoDAO.search(oSearch);		
	}

	@Transactional
	@Override
	public void guardar(Activos oActivo) throws BusinessException, DAOException {
		String codigoInventario  = this.generarCodigoInventario(oActivo);
		oActivo.setCodigoInventario(codigoInventario);
		oActivoDAO.saveUpper(oActivo);
		
		ActivosEmpleados oActivoEmpleado = new ActivosEmpleados();
		oActivoEmpleado.setEmpleado_id(oActivo.getEmpleado().getId());
		oActivoEmpleado.setCreadoEl(oActivo.getCreadoEl());
		oActivoEmpleado.setCreadoEnIp(oActivo.getCreadoEnIp());
		oActivoEmpleado.setCreadoPor(oActivo.getCreadoPor());
		oActivoEmpleado.setPasivo(Boolean.FALSE);
		oActivoEmpleado.setFechaAsignacion(new Date());
		oActivoEmpleado.setActivo_id(oActivo.getId());
		
		oActivoEmpleadoDAO.saveUpper(oActivoEmpleado);
	}

	//TODO: GENERAR PARAMETRO/CODIGO_DEPTO/CUENTA/SUBCUENTA/CONSECUTIVO
	@Transactional
	private synchronized String generarCodigoInventario(Activos oActivo) throws EntityNotFoundException, BusinessException{
		String codigoInventario = null;
		String consecutivo = null;
		Parametro oParametro = null;
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("codigo", "CODIGO_PRESUPUESTO"));
		oParametro = (Parametro) oParametroDAO.searchUnique(oSearch);
		Catalogo departamento = oCatalogoDAO.find(oActivo.getEmpleado().getDelegacion().getDepartamentoId());
		consecutivo = oActivoDAO.consecutivoActivoPorCuentaSubcuenta(oActivo.getSecaf().getCuenta(), oActivo.getSecaf().getSubcuenta()); 
		codigoInventario =oParametro.getValor() + "-"+departamento.getCodigo()+"-" + oActivo.getSecaf().getCuenta()+"-"+oActivo.getSecaf().getSubcuenta()+"-"+consecutivo;
		return codigoInventario;
	}

	//PARA REALIZAR LAS DIFERENTES BUSQUEDAS DE CONSULTA GENERAL
	@Transactional
	@Override
	public List<Activos> buscar(Integer delegacionId, String codActivo, String descripcion, Integer estadoFisicoId, Integer proyectoId) {
			List<Activos> listaActivos = new ArrayList<Activos>();
			listaActivos = oActivoDAO.ListadoActivosFiltro( delegacionId,  codActivo,  descripcion, estadoFisicoId,proyectoId); 
			return listaActivos;		
	}
	
	@Transactional
	@Override
	public List<Activos> buscar(String codigoSecaf, String codigoSecundario,String descripcionBien, String Serie,Integer ubicacionId, Integer estadoFisicoId, Integer tipoResguardoId){
		return oActivoDAO.buscar(codigoSecaf, codigoSecundario, descripcionBien, Serie, ubicacionId, estadoFisicoId, tipoResguardoId);
	} 
	
	@Transactional
	@Override
	public Activos obtener(int activoId) throws EntityNotFoundException {
		return oActivoDAO.find(activoId);
	}

	@Transactional
	@Override
	public void actualizar(Activos oActivo) throws BusinessException, DAOException {
		oActivoDAO.updateUpper(oActivo);
	}

	@Transactional
	@Override
	public List<Activos> obtenerListaDeActivosClasificadosComoEquiposInformaticos(Integer empleadoId, boolean pasivo) {
		return oActivoDAO.obtenerListaDeActivosClasificadosComoEquiposInformaticos(empleadoId, pasivo);
	}

}
