// package aula;

record Carro(
        String fabricante,
        String modelo,
        int cilindrada,
        int potencia,
        Categoria categoria) {

    public String toString() {
        return fabricante + ' ' + modelo + ' ' + (float) cilindrada / 10 + " " + potencia + "cv";
    }
}
