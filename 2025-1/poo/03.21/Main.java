import Banco.ContaBancaria;

public class Main {
    public static void main(String[] args) {
        ContaBancaria contaBancaria = new ContaBancaria("Andre Bastilhos", "05014013007");
        System.out.println(contaBancaria);
        System.out.println();
        contaBancaria.depositar(1000);
        System.out.println(contaBancaria);
        System.out.println(contaBancaria.getSaldo());
        System.out.println();
        contaBancaria.sacar(1000);
        System.out.println(contaBancaria);
        System.out.println(contaBancaria.getSaldo());
        System.out.println();
        System.out.println(contaBancaria.extrato());
    }
}
