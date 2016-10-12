package ni.gob.inss.sisinv.model.dao.catalogos;

import java.util.List;

import ni.gob.inss.barista.model.dao.BaseGenericDAO;
import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

public interface EmpleadoDAO extends BaseGenericDAO<Empleado, Integer>{
	public List<Empleado> buscar(String criterioNombre, Integer delegacion, Boolean pasivo, String cargo,  String area, String numeroIdentificacion, String numeroEmpleado);
	
}
