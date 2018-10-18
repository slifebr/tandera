package com.tandera.core.util;

import java.math.BigDecimal;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import edu.porgamdor.util.desktop.ss.SSCaixaCombinacao;
import edu.porgamdor.util.desktop.ss.SSCampoNumero;
import edu.porgamdor.util.desktop.ss.SSCampoTexto;

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
	
	public static boolean temValorValido(SSCampoTexto campo) {
		boolean retorno = false;
	    retorno =  campo.getText() != null && !campo.getText().trim().isEmpty();

	    return retorno;
	}
	
	public static boolean temValorValido(SSCaixaCombinacao campo) {
		boolean retorno = false;
	    retorno =  campo.getText() != null && !campo.getText().trim().isEmpty();
	    return retorno;
	}	
	
	public static boolean temValorValido(SSCampoNumero campo, String tipo) {
		boolean retorno = false;
	    retorno =  campo.getText() != null && !campo.getText().trim().isEmpty();
	    
	    if (retorno && tipo.contains("IN")) {  
	    	// S = String | I = Integer | N = Decimal  | D = Data
	    	if (tipo.compareTo("I") == 0) {
	    		retorno = (Integer.parseInt(campo.getText()) <= 0);
	    	} else if (tipo.compareTo("N") == 0) {
		    	retorno = (new BigDecimal(campo.getText()).compareTo(BigDecimal.ZERO) > 0);
	    	}
	    }
	    return retorno;
	}
	
	public static String converteValor(String valor) {
		
		String retorno = valor.replace(".", "").replace(",", ".") ;
		
		return retorno;
	}
}
