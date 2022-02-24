package raphael.dbc.sicredi.service;

import lombok.extern.slf4j.Slf4j;
import raphael.dbc.sicredi.model.ClienteConta;
import raphael.dbc.sicredi.util.ValueFormatUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
public class FileProcessService {

    private static final String SEPARATOR = ";";
    private static final String FILE_OUTPUT_NAME = "contas_processadas.csv";

    public void readFile(String fileName) {
        log.info("Iniciando o processamento do arquivo {}", fileName);

        Path path = Paths.get(fileName);

        if (!Files.isReadable(path)) {
            log.warn("Não foi possível abrir o arquivo...");
            return;
        }

        try (Stream<String> lines = Files.lines(path)) {
            lines.skip(1).parallel().forEach(this::lineProcessor);

        } catch (IOException e) {
            log.error("Erro ao ler o arquivo {}", fileName, e);
        }
    }

    private void lineProcessor(String line) {
        try {
            log.info("Processando linha {} do arquivo...", line);
            if (line == null || line.isEmpty()) return;

            String[] lineArray = line.split(SEPARATOR);

            if (lineArray.length != 4) {
                log.error("O número de parâmetros na linha não é reconhecido");
                return;
            }

            ClienteConta clienteConta = new ClienteConta();
            clienteConta.setAgencia(lineArray[0]);
            clienteConta.setConta(lineArray[1]);
            clienteConta.setSaldo(ValueFormatUtil.stringToDouble(lineArray[2]));
            clienteConta.setStatus(lineArray[3]);
            this.sendClienteContaToReceita(clienteConta);

        } catch (Exception e) {
            log.error("Erro ao processar a linha {}", line, e);
        }
    }

    private void sendClienteContaToReceita(ClienteConta clienteConta) {
        ReceitaService receitaService = new ReceitaService();
        try {
            log.info("Enviando Cliente com conta {} de saldo {} para a receita...", clienteConta.getConta(), clienteConta.getSaldo());
            Boolean resultado = receitaService.atualizarConta(
                    clienteConta.getAgencia(),
                    clienteConta.getConta().replace("-", ""),
                    clienteConta.getSaldo(),
                    clienteConta.getStatus());

            log.info("Valor {} atualizado com {}", clienteConta, resultado ? "Sucesso" : "Falha");

            this.writeResultOnFile(clienteConta, resultado);
        } catch (Exception e) {
            log.error("Erro ao processar o valor {}", clienteConta, e);
        }

    }

    private void writeResultOnFile(ClienteConta clienteConta, Boolean resultado) {
        StringBuilder sb = new StringBuilder();
        File file = new File(FILE_OUTPUT_NAME);

        try {
            log.info("Escrevendo resultado no arquivo {}...", FILE_OUTPUT_NAME);
            if (!file.exists()) {
                Path newFilePath = Paths.get(FILE_OUTPUT_NAME);
                Files.createFile(newFilePath);
                this.writeOnLineFile("agencia;conta;saldo;status;sucesso", file);
            }

            sb.append(clienteConta.getAgencia());
            sb.append(";");
            sb.append(clienteConta.getConta());
            sb.append(";");
            sb.append(ValueFormatUtil.doubleToString(clienteConta.getSaldo()));
            sb.append(";");
            sb.append(clienteConta.getStatus());
            sb.append(";");
            sb.append(resultado ? "SIM" : "NAO");

            this.writeOnLineFile(sb.toString(), file);
            log.info("Resultado com valor {} escrito no arquivo com sucesso!", clienteConta);
        } catch (Exception e) {
            log.error("Erro ao criar o arquivo...", e);
        }
    }

    private synchronized void writeOnLineFile(String line, File file) {
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(line);
        } catch (IOException e) {
            log.error("Erro ao escrever a linha {} no arquivo...", line, e);
        }
    }
}
