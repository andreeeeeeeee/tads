public class Prontuario {
  private Consulta detalhesConsulta;
  private String sintomas;
  private String examesSolicitados;
  private String medicamentos;

  public Prontuario(Consulta detalhesConsulta, String sintomas, String examesSolicitados, String medicamentos) {
    this.detalhesConsulta = detalhesConsulta;
    this.sintomas = sintomas;
    this.examesSolicitados = examesSolicitados;
    this.medicamentos = medicamentos;
  }

  @Override
  public String toString() {
    return "";
  }

  public Consulta getDetalhesConsulta() {
    return this.detalhesConsulta;
  }

  public void setDetalhesConsulta(Consulta detalhesConsulta) {
    this.detalhesConsulta = detalhesConsulta;
  }

  public String getSintomas() {
    return this.sintomas;
  }

  public void setSintomas(String sintomas) {
    this.sintomas = sintomas;
  }

  public String getExamesSolicitados() {
    return this.examesSolicitados;
  }

  public void setExamesSolicitados(String examesSolicitados) {
    this.examesSolicitados = examesSolicitados;
  }

  public String getMedicamentos() {
    return this.medicamentos;
  }

  public void setMedicamentos(String medicamentos) {
    this.medicamentos = medicamentos;
  }

}
