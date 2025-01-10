import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Bem vindo ao Banco BetaGama!");
        System.out.println("Selecione uma opção:");

        ContaBanco conta = new ContaBanco();

        System.out.println("1 - Abrir Conta");
        System.out.println("2 - Depositar");
        System.out.println("3 - Sacar");
        System.out.println("4 - Consultar saldo");
        System.out.println("5 - Transferir");
        System.out.println("6 - Sair");

        Scanner scanner = new Scanner(System.in);

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                System.out.println("Digite o número da agência:");
                int numeroAgencia = scanner.nextInt();
                System.out.println("Digite o número da conta:");
                int numeroConta = scanner.nextInt();
                System.out.println("Digite o nome do titular:");
                scanner.nextLine(); // Consumir a quebra de linha pendente
                String nomeTitular = scanner.nextLine();
                System.out.println("Digite o saldo inicial:");
                String saldoStr = scanner.next().replace(",", ".");
                double saldoInicial = Double.parseDouble(saldoStr);
                conta.abrirConta(numeroAgencia, numeroConta, nomeTitular, saldoInicial);
                break;
            case 2:
                System.out.println("Digite o valor do depósito:");
                double valorDeposito = scanner.nextDouble();
                System.out.println("Digite o número da agência:");
                int numeroAgenciaDeposito = scanner.nextInt();
                System.out.println("Digite o número da conta:");
                int numeroContaDeposito = scanner.nextInt();
                conta.depositar(valorDeposito, numeroAgenciaDeposito, numeroContaDeposito);
                break;
            case 3:
                System.out.println("Digite o valor do saque:");
                double valorSaque = scanner.nextDouble();
                System.out.println("Digite o número da agência:");
                int numeroAgenciaSaque = scanner.nextInt();
                System.out.println("Digite o número da conta:");
                int numeroContaSaque = scanner.nextInt();
                conta.sacar(valorSaque, numeroAgenciaSaque, numeroContaSaque);
                break;
            case 4:
                System.out.println("Digite o número da agência:");
                int numeroAgenciaSaldo = scanner.nextInt();
                System.out.println("Digite o número da conta:");
                int numeroContaSaldo = scanner.nextInt();
                conta.consultarSaldo(numeroAgenciaSaldo, numeroContaSaldo);
                break;
            case 5:
                System.out.println("Digite o número da agência de origem:");
                int numeroAgenciaOrigem = scanner.nextInt();
                System.out.println("Digite o número da conta de origem:");
                int numeroContaOrigem = scanner.nextInt();
                System.out.println("Digite o número da agência de destino:");
                int numeroAgenciaDestino = scanner.nextInt();
                System.out.println("Digite o número da conta de destino:");
                int numeroContaDestino = scanner.nextInt();
                System.out.println("Digite o valor da transferência:");
                double valorTransferencia = scanner.nextDouble();
                conta.transferir(valorTransferencia, numeroAgenciaOrigem, numeroContaOrigem, numeroAgenciaDestino, numeroContaDestino);
                break;
            case 6:
                System.out.println("Obrigado por utilizar o Banco BetaGama!");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }
}
