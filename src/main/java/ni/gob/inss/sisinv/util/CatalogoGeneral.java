package ni.gob.inss.sisinv.util;

public enum CatalogoGeneral {
	DEPARTAMENTOS("DEP"),
	TIPO_BIENES("TIPBI");
	
	private final String catalogo ;
	
	CatalogoGeneral(String catalogo){
		this.catalogo = catalogo;
	}
	
	public String getCodigoCatalogo(){
		return this.catalogo;
	}
}
