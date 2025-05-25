public enum TipoConsulta {
  CLINICA("Consulta Clínica"),
  ODONTOLOGICA("Consulta Odontológica"),
  CARDIOLOGICA("Consulta Cardiológica");

  private final String descricao;

  TipoConsulta(String descricao) {
    this.descricao = descricao;
  }

  public String getDescricao() {
    return this.descricao;
  }
}