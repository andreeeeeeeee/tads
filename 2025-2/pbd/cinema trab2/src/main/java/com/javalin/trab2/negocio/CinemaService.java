package com.javalin.trab2.negocio;

import java.sql.SQLException;
import java.util.List;

import com.javalin.trab2.persistencia.ClienteDAO;
import com.javalin.trab2.persistencia.FilmeDAO;
import com.javalin.trab2.persistencia.IngressoDAO;
import com.javalin.trab2.persistencia.SessaoDAO;

public class CinemaService {

  private final ClienteDAO clienteDAO;
  private final SessaoDAO sessaoDAO;
  private final IngressoDAO ingressoDAO;
  private final FilmeDAO filmeDAO;

  public CinemaService() {
    this.clienteDAO = new ClienteDAO();
    this.sessaoDAO = new SessaoDAO();
    this.ingressoDAO = new IngressoDAO();
    this.filmeDAO = new FilmeDAO();
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

  public List<Filme> listarFilmes() throws SQLException {
    return filmeDAO.listar();
  }

  public Filme buscarFilmePorId(int id) throws SQLException {
    return filmeDAO.buscarPorId(id);
  }

  public boolean adicionarFilme(String titulo, int duracao, String classificacao, String sinopse)
      throws SQLException {
    return filmeDAO.adicionar(titulo, duracao, classificacao, sinopse);
  }

  public boolean excluirFilme(int id) throws SQLException {
    return filmeDAO.excluir(id);
  }

  public List<String> buscarFilmesPorDiretor(String nomeDiretor) throws SQLException {
    return filmeDAO.buscarPorDiretor(nomeDiretor);
  }

  public int contarIngressosVendidos(int sessaoId) throws SQLException {
    return ingressoDAO.contarIngressosVendidos(sessaoId);
  }

  public List<Poltrona> listarAssentosDisponiveis(int sessaoId) throws SQLException {
    return ingressoDAO.listarAssentosDisponiveis(sessaoId);
  }

  public boolean venderIngresso(String cpf, int sessaoId, int poltronaId, double valor) throws SQLException {
    return ingressoDAO.venderIngresso(cpf, sessaoId, poltronaId, valor);
  }

  public RelatorioOcupacao gerarRelatorioOcupacao(int sessaoId) throws SQLException {
    return ingressoDAO.gerarRelatorioOcupacao(sessaoId);
  }
}
