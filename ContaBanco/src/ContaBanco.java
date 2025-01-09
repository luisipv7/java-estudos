import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ContaBanco {
    int numeroConta;
    String agencia;
    String nomeCliente;
    double saldo;

    // Método para ler o arquivo JSON e converter em um mapa
    private static Map<String, Map<String, Object>> lerArquivoJSON(String caminho) throws IOException {
        String path = System.getProperty("user.dir") + "/" + caminho;
    
        // Verifica se o arquivo existe
        if (!Files.exists(Paths.get(path))) {
            System.out.println("Arquivo de contas não encontrado. Criando um novo arquivo...");
            Files.createDirectories(Paths.get(path).getParent());
            Files.write(Paths.get(path), "{}".getBytes());
        }
    
        // Lê o conteúdo do arquivo JSON
        String content = new String(Files.readAllBytes(Paths.get(path)));
        System.out.println("Conteúdo do arquivo JSON: " + content);
    
        Map<String, Map<String, Object>> contas = new HashMap<>();
    
        // Remove as chaves externas do JSON e converte o conteúdo
        content = content.trim().replaceAll("\\s+", ""); // Remove espaços extras
        content = content.substring(1, content.length() - 1); // Remove as chaves externas {}
    
        if (content.isEmpty()) {
            return contas; // Retorna mapa vazio se o JSON estiver vazio
        }
    
        // Processa cada entrada do JSON
        String[] entries = content.split("},");
        for (String entry : entries) {
            String[] keyValue = entry.split(":\\{", 2); // Divide a chave e os valores
            if (keyValue.length < 2) continue;
    
            String key = keyValue[0].replaceAll("\"", ""); // Remove aspas da chave
            String[] valores = keyValue[1].replaceAll("[{}\"]", "").split(",");
    
            Map<String, Object> conta = new HashMap<>();
            for (String valor : valores) {
                String[] campoValor = valor.split(":");
                if (campoValor.length < 2) continue;
    
                String campo = campoValor[0].trim();
                String valorCampo = campoValor[1].trim();
    
                if (campo.equals("saldo")) {
                    conta.put(campo, Double.parseDouble(valorCampo));
                } else {
                    conta.put(campo, valorCampo);
                }
            }
    
            contas.put(key, conta);
        }
    
        System.out.println("Chaves carregadas do JSON: " + contas.keySet());
        return contas;
    }
    
    // Método para salvar o mapa atualizado como arquivo JSON
    private static void salvarArquivoJSON(String caminho, Map<String, Map<String, Object>> contas) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder("{");

        for (Map.Entry<String, Map<String, Object>> entry : contas.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\": {");
            Map<String, Object> conta = entry.getValue();

            for (Map.Entry<String, Object> campo : conta.entrySet()) {
                jsonBuilder.append("\"").append(campo.getKey()).append("\": ");
                if (campo.getValue() instanceof String) {
                    jsonBuilder.append("\"").append(campo.getValue()).append("\"");
                } else {
                    jsonBuilder.append(campo.getValue());
                }
                jsonBuilder.append(", ");
            }
            jsonBuilder.delete(jsonBuilder.length() - 2, jsonBuilder.length()); // Remove a última vírgula
            jsonBuilder.append("}, ");
        }
        jsonBuilder.delete(jsonBuilder.length() - 2, jsonBuilder.length()); // Remove a última vírgula
        jsonBuilder.append("}");

        Files.write(Paths.get(System.getProperty("user.dir") + caminho), jsonBuilder.toString().getBytes());
    }

    // Método de depósito
    public void depositar(double valor, int numeroAgencia, int numeroConta) {
        try {
            Map<String, Map<String, Object>> contas = lerArquivoJSON("src/mocks/contas.json");
            String key = String.format("%03d%08d", numeroAgencia, numeroConta);

            if (contas.containsKey(key)) {
                Map<String, Object> conta = contas.get(key);
                double saldoAtual = (double) conta.get("saldo");
                saldoAtual += valor;
                conta.put("saldo", saldoAtual);

                contas.put(key, conta);
                salvarArquivoJSON("src/mocks/contas.json", contas);

                System.out.println("Depósito realizado com sucesso!");
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método de saque
    public void sacar(double valor, int numeroAgencia, int numeroConta) {
        try {
            Map<String, Map<String, Object>> contas = lerArquivoJSON("src/mocks/contas.json");
            String key = String.format("%03d%08d", numeroAgencia, numeroConta);

            if (contas.containsKey(key)) {
                Map<String, Object> conta = contas.get(key);
                double saldoAtual = (double) conta.get("saldo");

                if (saldoAtual >= valor) {
                    saldoAtual -= valor;
                    conta.put("saldo", saldoAtual);

                    contas.put(key, conta);
                    salvarArquivoJSON("src/mocks/contas.json", contas);

                    System.out.println("Saque realizado com sucesso!");
                } else {
                    System.out.println("Saldo insuficiente.");
                }
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método de transferência
    public void transferir(double valor, int numeroAgenciaOrigem, int numeroContaOrigem, int numeroAgenciaDestino, int numeroContaDestino) {
        try {
            Map<String, Map<String, Object>> contas = lerArquivoJSON("src/mocks/contas.json");
            String keyOrigem = numeroAgenciaOrigem + "" + numeroContaOrigem;
            String keyDestino = numeroAgenciaDestino + "" + numeroContaDestino;

            if (contas.containsKey(keyOrigem) && contas.containsKey(keyDestino)) {
                Map<String, Object> contaOrigem = contas.get(keyOrigem);
                Map<String, Object> contaDestino = contas.get(keyDestino);

                double saldoOrigem = (double) contaOrigem.get("saldo");
                double saldoDestino = (double) contaDestino.get("saldo");

                if (saldoOrigem >= valor) {
                    saldoOrigem -= valor;
                    saldoDestino += valor;

                    contaOrigem.put("saldo", saldoOrigem);
                    contaDestino.put("saldo", saldoDestino);

                    contas.put(keyOrigem, contaOrigem);
                    contas.put(keyDestino, contaDestino);

                    salvarArquivoJSON("src/mocks/contas.json", contas);

                    System.out.println("Transferência realizada com sucesso!");
                } else {
                    System.out.println("Saldo insuficiente.");
                }
            } else {
                System.out.println("Conta de origem ou destino não encontrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método de consulta de saldo
    public void consultarSaldo(int numeroAgencia, int numeroConta) {
        try {
            Map<String, Map<String, Object>> contas = lerArquivoJSON("src/mocks/contas.json");
            String key = String.format("%03d%08d", numeroAgencia, numeroConta);

            System.out.println("Chave buscada: " + key);
            System.out.println("Chaves disponíveis: " + contas.keySet());

            if (contas.containsKey(key)) {
                Map<String, Object> conta = contas.get(key);
                double saldoAtual = (double) conta.get("saldo");
                System.out.println("Saldo atual: " + saldoAtual);
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para abrir conta
    public void abrirConta(int numeroAgencia, int numeroConta, String nomeCliente, double saldoInicial) {
        try {
            Map<String, Map<String, Object>> contas = lerArquivoJSON("src/mocks/contas.json");
            String key = String.format("%03d%08d", numeroAgencia, numeroConta);

            if (contas.containsKey(key)) {
                System.out.println("Conta já existe!");
            } else {
                Map<String, Object> novaConta = new HashMap<>();
                novaConta.put("numeroAgencia", String.valueOf(numeroAgencia));
                novaConta.put("numeroConta", String.valueOf(numeroConta));
                novaConta.put("nomeCliente", nomeCliente);
                novaConta.put("saldo", saldoInicial);

                contas.put(key, novaConta);
                salvarArquivoJSON("src/mocks/contas.json", contas);

                System.out.println("Conta aberta com sucesso!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
