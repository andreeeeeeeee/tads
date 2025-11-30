package com.javalin.trab2.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javalin.trab2.negocio.Cliente;
import com.javalin.trab2.negocio.RelatorioClienteFrequente;

public class ClienteDAO {

  private final ConexaoPostgreSQL conexaoBanco;

  public ClienteDAO() {
    this.conexaoBanco = new ConexaoPostgreSQL();
  }

  public void inserir(Cliente cliente) throws SQLException {
    String sql = "INSERT INTO cliente (cpf, nome) VALUES (?, ?)";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cliente.getCpf());
      stmt.setString(2, cliente.getNome());

      stmt.executeUpdate();
    }
  }

  public Cliente buscarPorCpf(String cpf) throws SQLException {
    String sql = "SELECT cpf, nome FROM cliente WHERE cpf = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cpf);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Cliente cliente = new Cliente();
        cliente.setCpf(rs.getString("cpf"));
        cliente.setNome(rs.getString("nome"));
        return cliente;
      }
    }
    return null;
  }

  public List<Cliente> listar() throws SQLException {
    String sql = "SELECT cpf, nome FROM cliente ORDER BY nome";
    List<Cliente> clientes = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Cliente cliente = new Cliente();
        cliente.setCpf(rs.getString("cpf"));
        cliente.setNome(rs.getString("nome"));
        clientes.add(cliente);
      }
    }
    return clientes;
  }

  public void atualizar(Cliente cliente) throws SQLException {
    String sql = "UPDATE cliente SET nome = ? WHERE cpf = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cliente.getNome());
      stmt.setString(2, cliente.getCpf());

      stmt.executeUpdate();
    }
  }

  public void excluir(String cpf) throws SQLException {
    String sql = "DELETE FROM cliente WHERE cpf = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cpf);
      stmt.executeUpdate();
    }
  }

  public List<RelatorioClienteFrequente> relatorioClientesFrequentes() throws SQLException {
    String sql = """
        SELECT cpf, nome, quantidade_ingressos, total_gasto, media_por_ingresso
        FROM relatorio_clientes_frequentes
        """;

    List<RelatorioClienteFrequente> relatorio = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        RelatorioClienteFrequente item = new RelatorioClienteFrequente();
        item.setCpf(rs.getString("cpf"));
        item.setNome(rs.getString("nome"));
        item.setQuantidadeIngressos(rs.getInt("quantidade_ingressos"));
        item.setTotalGasto(rs.getDouble("total_gasto"));
        item.setValorMedioIngresso(rs.getDouble("media_por_ingresso"));
        relatorio.add(item);
      }
    }
    return relatorio;
  }
}
