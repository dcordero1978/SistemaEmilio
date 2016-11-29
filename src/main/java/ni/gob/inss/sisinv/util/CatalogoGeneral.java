package ni.gob.inss.sisinv.util;

public enum CatalogoGeneral {
	DEPARTAMENTOS("DEP"),
	TIPO_BIENES("TBI"),
	MONEDA("MON"),
	TIPO_RESGUARDO("RSG"),
	ESTADO_FISICO("EFB"),
	CARACTERISTICA_TRANSPORTE_MAQUINARIA("MTT"),
	CARACTERISTICA_ARMA_FUEGO("CAF"),
	CARACTERISTICA_OBRA_ARTE("COA"),
	TIPO_COMBUSTIBLE("TCB"),
	COLORES("COL"),
	PROYECTOS("PRY"),
	TIPOSMOVIMIENTO("TMB"),
	TIPO_ACTIVO_ACTIVO_ESPECIAL("TCE"),
	TIPO_ACTIVO("TAC"),
	TIPO_MANTENIMIENTO("TMT");

	
	private final String catalogo ;
	
	CatalogoGeneral(String catalogo){
		this.catalogo = catalogo;
	}
	
	public String getCodigoCatalogo(){
		return this.catalogo;
	}
}