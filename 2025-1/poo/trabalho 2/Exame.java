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
      if (agenda.getData().equals(dataHora)) {
        for (Horario horario : agenda.getHorarios()) {
          if (horario.getDataHora().equals(dataHora)) {
            horario.setOcupado(true);
            break;
          }
        }
      }
    }

    setDataHora(dataHora);
  }
}
