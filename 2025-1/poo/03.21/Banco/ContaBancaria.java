package Banco;

public class ContaBancaria {
    /*
     * nome + cpf
     * saldo
     * lista de transacoes
     * 
     * +depoistar
     * +sacar
     * +extrato
     */
    private String nome;
    private String cpf;
    private int saldo;
    private String transacoes;

    public ContaBancaria(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.saldo = 0;
        this.transacoes = "";
    }

    public String toString() {
        String string = "Cliente: " + nome + "\nCPF:" + cpf;
        string += "\nSaldo R$" + (float) (saldo / 100);

        return string;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public void sacar(int valor) {
        if (valor < 0) {
            return;
        }
        this.transacoes += "\n\nSaldo anterior: R$" + (float) this.saldo / 100;
        if (valor > this.saldo) {
            this.transacoes += "\nValor retirado: R$" + (float) this.saldo / 100;
            this.saldo = 0;
        } else {
            this.transacoes += "\nValor retirado: R$" + (float) valor / 100;
            this.saldo -= valor;
        }
        this.transacoes += "\nSaldo final: R$" + (float) this.saldo / 100;
    }

    public void depositar(int valor) {
        if (valor < 0) {
            return;
        }
        this.transacoes += "\nSaldo anterior: R$" + (float) this.saldo / 100;
        this.transacoes += "\nValor depositado: R$" + (float) valor / 100;

        this.saldo += valor;
        this.transacoes += "\nSaldo final: R$" + (float) this.saldo / 100;
    }

    public String extrato() {
        return "Transações:\n"+this.transacoes;
    }
}
