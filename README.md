# Sincronização Receita

<!---Esses são exemplos. Veja https://shields.io para outras pessoas ou para personalizar este conjunto de escudos. Você pode querer incluir dependências, status do projeto e informações de licença aqui--->

![GitHub repo size](https://img.shields.io/github/repo-size/iuricode/README-template?style=for-the-badge)
![GitHub language count](https://img.shields.io/github/languages/count/iuricode/README-template?style=for-the-badge)
![GitHub forks](https://img.shields.io/github/forks/iuricode/README-template?style=for-the-badge)
![Bitbucket open issues](https://img.shields.io/bitbucket/issues/iuricode/README-template?style=for-the-badge)
![Bitbucket open pull requests](https://img.shields.io/bitbucket/pr-raw/iuricode/README-template?style=for-the-badge)

> Cenário de Negócio:
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de 
contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi 
já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, 
antes as 10:00 da manhã na abertura das agências.

### Requisito e Funcionalidade:
Usar o "serviço da receita" (fake) para processamento automático do arquivo.  

- [x] Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita arquivo.csv
- [x] Processa um arquivo CSV de entrada com o formato abaixo.
- [x] Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
- [X] Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma 
nova coluna
  
#### Formato do arquivo CSV
  
  ```
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I
3202;54001-2;0,00;P
3202;00321-2;34500,00;B
...
```

## 💻 Pré-requisitos

Antes de começar, verifique se você atendeu aos seguintes requisitos:
<!---Estes são apenas requisitos de exemplo. Adicionar, duplicar ou remover conforme necessário--->
* Você instalou a `Versão 8` do `Java` e possui a variável de ambiente `JAVA_HOME` configurada em sua máquina.
* Você tem uma máquina `Windows / Linux / MAC`.

## 🚀 Utilizando o Sincronização Receita

Para utiliza-lo vc deve executar o comando abaixo no terminal (Git Bash/CMD/Terminal/Console do Powershell) 
  na pasta do arquivo e indicar qual arquivo será processado:

```
java -jar SincronizacaoReceita <input-file>.csv
```
Ao finalizar o processamento do arquivo, será gerado um novo arquivo chamado `contas_processadas.csv` com uma nova coluna indicando se houve sucesso na sincronização com a receita.

#### Exemplo
 
  ```
agencia;conta;saldo;status;sucesso
0101;12226-8;3200,50;A;SIM
44166;10428-4;28000,00;A;NAO
```
  
## ☕ Como foi realizada a implementação

A implementação do serviço foi realizada utilizando Java com Spring Boot, e foi feito com base na leitura do arquivo e processamento linha a linha, para evitar 
  o consumo excessivo de memória, da mesma forma na hora de escrever o resultado no arquivo de saída.
  
  A única biblioteca utilizada foi o `lombok`para gerar os getter e setters da classe de modelo e também escrever os logs no terminal.

[⬆ Voltar ao topo](#nome-do-projeto)<br>
