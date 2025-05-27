
import java.util.Date;

public class Paciente extends Pessoa {
  private Date dtNascimento;
  private Sexo sexo;
  private TipoSanguineo tipoSanguineo;
  private final HistoricoConsultas historicoConsultas;

  public Paciente(
      String nome,
      String cpf,
      Date dtNascimento,
      String email,
      String telefone,
      Sexo sexo,
      TipoSanguineo tipoSanguineo) {
    super(nome, cpf, email, telefone);

    this.dtNascimento = dtNascimento;
    this.sexo = sexo;
    this.tipoSanguineo = tipoSanguineo;
    this.historicoConsultas = new HistoricoConsultas();
  }

  @Override
  public String toString() {
    return "Paciente: " + getNome() +
        "\n - CPF: " + getCpf() +
        "\n - Data de Nascimento: " + dtNascimento +
        "\n - Sexo: " + sexo.getDescricao() +
        "\n - Tipo Sanguíneo: " + tipoSanguineo.getDescricao() +
        "\n - Contato: " + getEmail() + ", " + getTelefone();
  }

  public Date getDtNascimento() {
    return this.dtNascimento;
  }

  public void setDtNascimento(Date dtNascimento) {
    this.dtNascimento = dtNascimento;
  }

  public Sexo getSexo() {
    return this.sexo;
  }

  public void setSexo(Sexo sexo) {
    this.sexo = sexo;
  }

  public TipoSanguineo getTipoSanguineo() {
    return this.tipoSanguineo;
  }

  public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
    this.tipoSanguineo = tipoSanguineo;
  }

  public HistoricoConsultas getHistoricoConsultas() {
    return this.historicoConsultas;
  }

  public void addConsulta(Consulta consulta) {
    historicoConsultas.addConsulta(consulta);
  }
}
