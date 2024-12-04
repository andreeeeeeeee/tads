// package aula;

class App {
    public static void main(String[] args) {
        Carro carro = new Carro(
                "Fiat",
                "Palio",
                16,
                75,
                Categoria.UTILITARIO);

        System.out.println(carro.toString());
        System.out.println(carro.categoria());

        Consorcio sim = Consorcio.simular(7000);
        // final float valorTotal = 7000 + sim.taxaAdministrativa() + sim.fundoReserva();
        // final float valorParcela = valorTotal / sim.parcelas();
        System.out.println(sim);
        System.out.println(sim.valorTotal());
        System.out.println(sim.valorParcela());
    }
}