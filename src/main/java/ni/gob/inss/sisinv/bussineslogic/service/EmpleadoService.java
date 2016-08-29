package ni.gob.inss.sisinv.bussineslogic.service;

import java.util.List;

import ni.gob.inss.sisinv.model.entity.catalogo.Empleado;

public interface EmpleadoService {
	public List<Empleado> buscar(String criterioBusqueda);
}
