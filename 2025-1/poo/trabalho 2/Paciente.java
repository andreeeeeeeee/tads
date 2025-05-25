
import java.util.Date;

public class Paciente extends Pessoa {
  private Date dtNascimento;
  private Sexo sexo;
  private TipoSanguineo tipoSanguineo;
  private final HistoricoConsultas historicoConsultas;

  public Paciente(
      String nome,
      String cpf,
      String email,
      String telefone,
      Sexo sexo,
      TipoSanguineo tipoSanguineo) {
    super(nome, cpf, email, telefone);

    this.sexo = sexo;
    this.tipoSanguineo = tipoSanguineo;
    this.historicoConsultas = new HistoricoConsultas();
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
