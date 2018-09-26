package tandera;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.tandera.core.config.PersistenceConfig;
import com.tandera.core.dao.springjpa.MascaraPrecoRepository;
import com.tandera.core.model.comercial.Categoria;
import com.tandera.core.model.comercial.MascaraPreco;

public class TesteEstado {
	
	
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
	//RepositorioLancamento dao = context.getBean(RepositorioLancamento.class);
	//EstadoRepository dao = context.getBean(EstadoRepository.class);
	MascaraPrecoRepository dao = context.getBean(MascaraPrecoRepository.class);
	
	
	
	public List<MascaraPreco> Lista(Integer id){
		 /*Optional ret = FIndById(id);
		 
		 if (ret.isPresent()){
			 return ret.get();
		 }
		*/ 
		return dao.findAll();
		
	}

	public static void main(String[] args) {
		

		TesteEstado classeTeste = new TesteEstado();
		
		//System.out.println(estado.ListaEstado(1));
		List <MascaraPreco> lista = classeTeste.Lista(0);
		//lista.forEach(f->System.out.println(f.getId()));
		
		lista.forEach(l -> System.out.println(l.getMascaraComValor()));
		
		
	}

}
