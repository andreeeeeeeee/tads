import java.util.Date;

public class Exame implements IAgendavel {
  private Date dataHora;
  private String nome;
  private Responsavel responsavel;
  private String laudo;

  public Exame(String nome, Responsavel responsavel) {
    this.nome = nome;
    this.responsavel = responsavel;
  }

  /*
   * return tipo.getDescricao() + " " +
   * "em " + dataHora +
   * ", por " + medico.getNome() + "(CRM: " + medico.getCrm() + ")" +
   * ", ao paciente " + paciente.getNome() + "(CPF: " + paciente.getCpf() + ")" +
   * ".\n" + (prontuario != null ? prontuario.toString() : "");
   */
  @Override
  public String toString() {
    return nome +
        " em: " + dataHora +
        ", por " + responsavel.getNome() +
        " ("
        + (responsavel instanceof Medico ? "CRM: " + ((Medico) responsavel).getCrm() : "CPF: " + responsavel.getCpf())
        + ").\nLaudo: " + (laudo != null ? laudo : "Pendente");
  }

  public Date getDataHora() {
    return dataHora;
  }

  public void setDataHora(Date dataHora) {
    this.dataHora = dataHora;
  }

  public String getNome() {
    return this.nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Responsavel getResponsavel() {
    return this.responsavel;
  }

  public void setResponsavel(Responsavel responsavel) {
    this.responsavel = responsavel;
  }

  public String getLaudo() {
    return this.laudo;
  }

  public void setLaudo(String laudo) {
    this.laudo = laudo;
  }

  public void encerrar(String laudo) {
    setLaudo(laudo);
  }

  @Override
  public void agendar(Date dataHora) throws AgendaNaoDisponivelException {
    if (!this.responsavel.isHorarioDisponivel(dataHora)) {
      throw new AgendaNaoDisponivelException();
    }

    for (AgendaDisponibilidade agenda : this.responsavel.getAgendas()) {
      if (this.responsavel.isMesmoDia(agenda.getData(), dataHora)) { // compara só o dia
        for (Horario horario : agenda.getHorarios()) {
          if (horario.getDataHora().equals(dataHora)) { // compara data e hora
            horario.setOcupado(true);
            break;
          }
        }
      }
    }

    setDataHora(dataHora);
  }

}
