package raphael.dbc.sicredi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import raphael.dbc.sicredi.service.FileProcessService;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class SincronizacaoReceitaApplication {

	public static void main(String[] args) throws IOException {
		log.info("Iniciando o Serviço de SincronizacaoReceita...");
		log.info("Args: {}", args);

		FileProcessService fileProcessService = new FileProcessService();
		fileProcessService.readFile(args[0]);
		log.info("Sincronização com a Receita finalizada com sucesso!");

		SpringApplication.run(SincronizacaoReceitaApplication.class, args);
	}

}
