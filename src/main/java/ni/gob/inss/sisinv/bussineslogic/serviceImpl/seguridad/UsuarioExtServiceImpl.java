package ni.gob.inss.sisinv.bussineslogic.serviceImpl.seguridad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

import ni.gob.inss.barista.businesslogic.serviceImpl.seguridad.UsuarioServiceImpl;
import ni.gob.inss.barista.model.dao.seguridad.AutorizacionDAO;
import ni.gob.inss.barista.model.dao.seguridad.RoleDAO;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Autorizacion;
import ni.gob.inss.barista.model.entity.seguridad.Role;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;
import ni.gob.inss.sisinv.bussineslogic.service.seguridad.UsuarioExtService;

@Primary
@Service("UsuarioService")
public class UsuarioExtServiceImpl extends UsuarioServiceImpl implements UsuarioExtService{

	@Autowired
	AutorizacionDAO oAutorizacionDAO;
	
	@Autowired
	RoleDAO oRoleDAO;
	
	
	@Transactional
	@Override
	public boolean usuarioTieneAutorizacion(Usuario oUsuario, Entidad oEntidad, String codigoAutorizacion) {
		List<Role> listaRoles = this.listaRoles(oUsuario, oEntidad);
		Autorizacion oAutorizacion =  this.obtenerAutorizacionPorCodigo(codigoAutorizacion);
		List<Role> listaRolesAsociados = new ArrayList<Role>();
		oAutorizacion.getRolesAutorizacionesesById().forEach(autorizacion -> {
			Role oRole = autorizacion.getRolesByRolId();
			listaRoles.stream().filter(role -> role.getId().equals(oRole.getId())).forEach(role -> {
				listaRolesAsociados.add(role);
			});;
		});
		
		return !listaRolesAsociados.isEmpty();
	}
	
	@Transactional
	private Autorizacion obtenerAutorizacionPorCodigo(String codigo){
		Search oSearch = new Search();
		oSearch.addFilter(Filter.equal("codigo", codigo));
		oSearch.addFilter(Filter.equal("pasivo", false));
		
		return (Autorizacion) oAutorizacionDAO.searchUnique(oSearch);	
	}
	
	
}
