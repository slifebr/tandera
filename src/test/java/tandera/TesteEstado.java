package tandera;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tandera.core.config.PersistenceConfig;
import com.tandera.core.dao.springjpa.EstadoRepository;
import com.tandera.core.model.comercial.Estado;
import com.tandera.core.model.enuns.StatusOrcamento;

public class TesteEstado {
	
	
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
	//RepositorioLancamento dao = context.getBean(RepositorioLancamento.class);
	EstadoRepository dao = context.getBean(EstadoRepository.class);
	
	public List<Estado> ListaEstado(Integer id){
		 /*Optional ret = FIndById(id);
		 
		 if (ret.isPresent()){
			 return ret.get();
		 }
		*/ 
		return dao.findAll();
		
	}

	public static void main(String[] args) {
		



		TesteEstado estado = new TesteEstado();
		
		//System.out.println(estado.ListaEstado(1));
		List <StatusOrcamento> lista = Arrays.asList(StatusOrcamento.values());
		//lista.forEach(f->System.out.println(f.getId()));
		
		lista.forEach(l -> System.out.println(l.getDescricao()));
		
		
	}

}
