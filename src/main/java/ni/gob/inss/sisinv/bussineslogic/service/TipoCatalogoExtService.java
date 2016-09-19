package ni.gob.inss.sisinv.bussineslogic.service;

import ni.gob.inss.barista.businesslogic.service.BusinessException;
import ni.gob.inss.barista.businesslogic.service.catalogos.TipoCatalogoService;
import ni.gob.inss.barista.model.dao.EntityNotFoundException;
import ni.gob.inss.barista.model.entity.catalogo.TiposCatalogo;

public interface TipoCatalogoExtService extends TipoCatalogoService{
	public TiposCatalogo obtenerTipoCatalogoPorCodigo(String codigo) throws EntityNotFoundException,BusinessException;
}
