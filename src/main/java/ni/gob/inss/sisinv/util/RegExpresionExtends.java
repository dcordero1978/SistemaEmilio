package ni.gob.inss.sisinv.util;

import ni.gob.inss.barista.model.utils.RegExpresion;

public class RegExpresionExtends extends RegExpresion{
	public static final String regExpCedula = "\\d{13}[A-Za-z]";
	public static final String maskCedula = "999-999999-9999a";
}
