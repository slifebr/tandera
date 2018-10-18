package tandera;

import java.math.BigDecimal;

public class TesteEstado {
	

	public static void main(String[] args) {
		
		System.out.println(new BigDecimal ("10000.0"));
		String lixo = "1.010.000,0".replace(".", "").replace(",", ".");
		System.out.println(lixo);
		System.out.println(new BigDecimal (lixo));
		
	}

}
