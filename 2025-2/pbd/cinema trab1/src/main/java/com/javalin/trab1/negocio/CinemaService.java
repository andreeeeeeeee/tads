package com.javalin.trab1.negocio;

import java.sql.SQLException;
import java.util.List;

import com.javalin.trab1.persistencia.ClienteDAO;
import com.javalin.trab1.persistencia.IngressoDAO;
import com.javalin.trab1.persistencia.SessaoDAO;

public class CinemaService {

  private final ClienteDAO clienteDAO;
  private final SessaoDAO sessaoDAO;
  private final IngressoDAO ingressoDAO;

  public CinemaService() {
    this.clienteDAO = new ClienteDAO();
    this.sessaoDAO = new SessaoDAO();
    this.ingressoDAO = new IngressoDAO();
  }

  public List<RelatorioClienteFrequente> gerarRelatorioClientesFrequentes() throws SQLException {
    return clienteDAO.relatorioClientesFrequentes();
  }

  public List<ConflitoDeSessao> verificarSessoesSimultaneas() throws SQLException {
    return sessaoDAO.verificarSessoesSimultaneas();
  }

  public int simularOcupacaoSessao(int sessaoId, double percentualOcupacao) throws SQLException {
    if (percentualOcupacao < 0 || percentualOcupacao > 100) {
      throw new IllegalArgumentException("Percentual deve estar entre 0 e 100");
    }
    return sessaoDAO.simularOcupacaoSessao(sessaoId, percentualOcupacao);
  }

  public int ajustarPrecosBaixaOcupacao() throws SQLException {
    return sessaoDAO.ajustarPrecosBaixaOcupacao();
  }

  public boolean remanejarPoltrona(String cpf, int sessaoId, int poltronaAntigaId, int poltronaNova)
      throws SQLException {
    return ingressoDAO.remanejarPoltrona(cpf, sessaoId, poltronaAntigaId, poltronaNova);
  }

  public boolean isPoltronaOcupada(int sessaoId, int poltronaId) throws SQLException {
    return ingressoDAO.isPoltronaOcupada(sessaoId, poltronaId);
  }

  public double calcularPercentualOcupacao(int sessaoId) throws SQLException {
    return ingressoDAO.calcularPercentualOcupacao(sessaoId);
  }

  public void cadastrarCliente(Cliente cliente) throws SQLException {
    clienteDAO.inserir(cliente);
  }

  public Cliente buscarClientePorCpf(String cpf) throws SQLException {
    return clienteDAO.buscarPorCpf(cpf);
  }

  public List<Sessao> listarSessoesDisponiveis() throws SQLException {
    return sessaoDAO.listarSessoesDisponiveis();
  }
}
