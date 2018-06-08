package com.tandera.teste;

import java.math.BigDecimal;

import com.tandera.core.util.Biblioteca;

public class TesteBibliotecaUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigDecimal valor = new BigDecimal("2550.45");
		String valorStr = Biblioteca.criptoBigDecimalToString(valor);
        System.out.println(valor + " : "+ valorStr);
        
        BigDecimal valor2 = Biblioteca.descriptoStringToBigDecimal(valorStr);
        System.out.println(valorStr + " : "+ valor2);
	}

}
