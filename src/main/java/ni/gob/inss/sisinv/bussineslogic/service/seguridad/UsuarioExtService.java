package ni.gob.inss.sisinv.bussineslogic.service.seguridad;

import ni.gob.inss.barista.businesslogic.service.seguridad.UsuarioService;
import ni.gob.inss.barista.model.entity.infraestructura.Entidad;
import ni.gob.inss.barista.model.entity.seguridad.Usuario;

public interface UsuarioExtService extends UsuarioService{
	public boolean usuarioTieneAutorizacion(Usuario usuario, Entidad oEntidad,  String codigoAutorizacion);
}
