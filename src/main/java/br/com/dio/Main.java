package br.com.dio;


import br.com.dio.expection.AccountNotFoundException;
import br.com.dio.expection.NoFundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;


import java.util.Arrays;
import java.util.Scanner;


import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;



public class Main {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Olá seja bem vindo ao Nexus Bank");

        while (true) {
            System.out.println("Selecione a operação desejada");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar uma investimento");
            System.out.println("3 - Fazer um investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar na conta");
            System.out.println("6 - Transferêcia entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar investimento");
            System.out.println("11 - Listar carteira de investimento");
            System.out.println("12 - Atualizar investimento");
            System.out.println("13 - Histórico de conta");
            System.out.println("14 - Bloquear conta");
            System.out.println("15 - Desbloquear conta");
            System.out.println("16 - Sair");
            String input = scanner.nextLine();
            if (input.isBlank()) {
                continue;
            }

            var option = Integer.parseInt(input);
            switch (option) {
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 3 -> createWalletInvestment();
                case 4 -> deposit();
                case 5 -> withdraw();
                case 6-> transferToAccount();
                case 7-> incInvestment();
                case 8-> rescueInvestment();
                case 9->
                    accountRepository.list().forEach(System.out::println);
                case 10->
                    investmentRepository.list().forEach(System.out::println);
                case 11->
                    investmentRepository.listWallets().forEach(System.out::println);
                case 12-> {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos reajustados");
                }
                case 13-> checkHistory();
                case 14 -> {
    System.out.println("Informe a chave Pix da conta que deseja bloquear:");
    var pixParaBloquear = scanner.next();
    try {
        accountRepository.blockAccount(pixParaBloquear);
        System.out.println("Conta bloqueada com sucesso!");
    } catch (AccountNotFoundException ex) {
        System.out.println(ex.getMessage());
    }
}
// ...existing code...
case 15 -> {
    System.out.println("Informe a chave Pix da conta que deseja desbloquear:");
    var pixParaDesbloquear = scanner.next();
    try {
        accountRepository.unblockAccount(pixParaDesbloquear);
        System.out.println("Conta desbloqueada com sucesso!");
    } catch (AccountNotFoundException ex) {
        System.out.println(ex.getMessage());
    }
}

                case 16->
                    System.exit(0);
                default ->
                    System.out.println("Opção Inválida");
            }

        }
    }
        private static void createAccount(){
            System.out.println("Informe as chaves pix (separadas por ';')");
            var pix = Arrays.stream(scanner.next().split(";")).toList();
            System.out.println("Informe o valor inicial de depósito");
            var amount = scanner.nextLong();
            var wallet = accountRepository.create(pix,amount);
            System.out.println("Conta criada" + wallet);
        }

    private static void createInvestment(){
        System.out.println("Infoeme a taxa de investimento");
        var tax = scanner.nextInt();
        System.out.println("Informe o valor inicial de depósito");
        var initialFunds = scanner.nextLong();
        var investment = investmentRepository.create(tax,initialFunds);
        System.out.println("Investimento criado" + investment);
    }

    private static void deposit() {
        System.out.println("Informe a chave pix da conta para depósito:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será depositado:");
        var amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());

        }
    }

        private static void withdraw() {
            System.out.println("Informe a chave pix da conta para saque:");
            var pix = scanner.next();
            System.out.println("Informe o valor que será sacado:");
            var amount = scanner.nextLong();
            try {
                accountRepository.withdraw(pix, amount);
            } catch (NoFundsEnoughException | AccountNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }


    private static void transferToAccount() {
        System.out.println("Informe a chave pix da conta de origem:");
        var source = scanner.next();
        System.out.println("Informe a chave pix da conta de destino:");
        var target = scanner.next();
        System.out.println("Informe o valor que será depositado:");
        var amount = scanner.nextLong();
        try {
            accountRepository.traferMoney(source,target,amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());

        }
    }

    private static void createWalletInvestment(){
        System.out.println("Informe a chave pix da conta:");
        var pix =scanner.next();
        var account = accountRepository.findByPix(pix);
        System.out.println("Informe o identificador do investimento");
        var investmentId = scanner.nextInt();
        var investmentWallet = investmentRepository.initInvestment(account,investmentId);
        System.out.println("Conta de investimento criada:"+ investmentWallet);
    }


    private static void incInvestment(){
        System.out.println("Informe a chave pix da conta para investimento:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será investido:");
        var amount = scanner.nextLong();
        try {
            investmentRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private static void rescueInvestment() {
        System.out.println("Informe a chave pix da conta para resgate do investimento:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será sacado:");
        var amount = scanner.nextLong();
        try {
            investmentRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private static void checkHistory(){
        System.out.println("Informe a chave pix da conta para verificar extrato:");
        var pix = scanner.next();
        AccountWallet wallet;
        try{
         var sortedHistory = accountRepository.getHistory(pix);
         sortedHistory.forEach((k,v)->{
             System.out.println(k.format(ISO_DATE_TIME));
             System.out.println(v.getFirst().transactionId());
             System.out.println(v.getFirst().description());
             System.out.println("R$" + (v.size()/100)+ "," +(v.size()%100));
         });
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

    }



