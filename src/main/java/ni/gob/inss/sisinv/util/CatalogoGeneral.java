package ni.gob.inss.sisinv.util;

public enum CatalogoGeneral {
	DEPARTAMENTOS(1),
	TIPO_IDENTIFICACION(2),
	TIPO_BIENES(3);
	
	private final int catalogo ;
	
	CatalogoGeneral(int catalogo){
		this.catalogo = catalogo;
	}
	
	public int getCatalogoId(){
		return this.catalogo;
	}
}
