import java.util.Date;

public class Consulta implements IAgendavel {
  private Date dataHora;
  private TipoConsulta tipo;
  private Medico medico;
  private Paciente paciente;
  private Prontuario prontuario;

  public Consulta(TipoConsulta tipo, Medico medico, Paciente paciente) {
    this.tipo = tipo;
    this.medico = medico;
    this.paciente = paciente;
  }

  @Override
  public String toString() {
    return tipo.getDescricao() + " " +
        "em " + dataHora +
        ", por " + medico.getNome() + "(CRM: " + medico.getCrm() + ")" +
        ", ao paciente " + paciente.getNome() + "(CPF: " + paciente.getCpf() + ")" +
        ".\n" + (prontuario != null ? prontuario.toString() : "");
  }

  public Date getDataHora() {
    return this.dataHora;
  }

  public void setDataHora(Date dataHora) {
    this.dataHora = dataHora;
  }

  public TipoConsulta getTipo() {
    return this.tipo;
  }

  public void setTipo(TipoConsulta tipo) {
    this.tipo = tipo;
  }

  public Medico getMedico() {
    return this.medico;
  }

  public void setMedico(Medico medico) {
    this.medico = medico;
  }

  public Paciente getPaciente() {
    return this.paciente;
  }

  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }

  public Prontuario getProntuario() {
    return this.prontuario;
  }

  public void setProntuario(Prontuario prontuario) {
    this.prontuario = prontuario;
  }

  public void encerrar(String sintomas, String examesSolicitados, String medicamentos) {
    setProntuario(new Prontuario(this, sintomas, examesSolicitados, medicamentos));
    paciente.addConsulta(this);
  }

  @Override
  public void agendar(Date dataHora) throws AgendaNaoDisponivelException {
    if (!this.medico.isHorarioDisponivel(dataHora)) {
      throw new AgendaNaoDisponivelException();
    }

    for (AgendaDisponibilidade agenda : this.medico.getAgendas()) {
      // Comparar apenas o dia, mês e ano
      if (medico.isMesmoDia(agenda.getData(), dataHora)) {
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