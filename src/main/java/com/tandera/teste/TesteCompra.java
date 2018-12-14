package com.tandera.teste;

import java.io.Console;
import java.math.BigDecimal;

public class TesteCompra {

	public static void main(String[] args) {
		//AbstractApplicationContext context = new Configc("springconfig.xml");
       // EmployeeRepository repository = context.getBean(EmployeeRepository.class);
 		
      BigDecimal vl = new BigDecimal("92.9800004");
      System.out.println(vl.ROUND_HALF_UP);
      BigDecimal vl2 =vl.setScale(2,BigDecimal.ROUND_HALF_UP);
      System.out.println(vl2);
      System.out.println(vl);

      System.out.println(String.format("%012.3f", 89.994589));   
      
	}

}
