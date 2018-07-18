package com.tandera.core.util;

import java.math.BigDecimal;

public  class Biblioteca {
     

	
	public static String criptoBigDecimalToString (BigDecimal  valor) {
		String  retorno;
		retorno = valor.toString()
				       .replace("1", "A")
				       .replace("2", "B")
				       .replace("3", "C")
				       .replace("4", "D")
				       .replace("5", "E")
				       .replace("6", "F")
				       .replace("7", "G")
				       .replace("8", "H")
				       .replace("9", "I")
				       .replace("0", "J")
				       .replace(".", "-");
		return retorno;
	}
	
	public static BigDecimal descriptoStringToBigDecimal (String  valor) {
		BigDecimal  retorno = BigDecimal.ZERO;
		try {
			retorno = new BigDecimal( valor.replace("A", "1")
				       .replace("B", "2")
				       .replace("C", "3")
				       .replace("D", "4")
				       .replace("E", "5")
				       .replace("F", "6")
				       .replace("G", "7")
				       .replace("H", "8")
				       .replace("I", "9")
				       .replace("J", "0")
				       .replace("-", "."));			
		} catch (Exception e) {
			;
		} ;
		
		return retorno;
		
	}	
}
