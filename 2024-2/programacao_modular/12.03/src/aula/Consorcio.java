record Consorcio(
        float valorBem,
        float fundoReserva,
        float taxaAdministrativa,
        int parcelas) {

    static Consorcio simular(int valor) {
        final int parcelas = 60;
        final float taxaAdministrativa = 0.1f;
        final float fundoReserva = 0.05f;

        return new Consorcio(
                valor,
                valor * fundoReserva,
                valor * taxaAdministrativa,
                parcelas);
    }

    float valorTotal() {
        return valorBem + taxaAdministrativa + fundoReserva;
    }

    float valorParcela() {
        return valorTotal() / parcelas;
    }
}