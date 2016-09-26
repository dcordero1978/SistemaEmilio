package ni.gob.inss.sisinv.bussineslogic.service.catalogos;

import java.util.List;

import ni.gob.inss.barista.businesslogic.service.catalogos.CatalogoService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.Catalogo;
import ni.gob.inss.sisinv.model.entity.catalogo.MarcasModelos;

public interface CatalogoExtService extends CatalogoService {
	public List<MarcasModelos> obtenerListaMarcas()throws EntityNotFoundException;
	public List<MarcasModelos> obtenerListaModelos(Integer marcaId)throws EntityNotFoundException;
	public List<Catalogo> obtieneListaCatalogosPorRefTipoCatalogo(String refTipoCatalogo) throws EntityNotFoundException;
}
