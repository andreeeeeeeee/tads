package com.javalin.trab1.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javalin.trab1.negocio.Cliente;
import com.javalin.trab1.negocio.RelatorioClienteFrequente;

public class ClienteDAO {

  private final ConexaoPostgreSQL conexaoBanco;

  public ClienteDAO() {
    this.conexaoBanco = new ConexaoPostgreSQL();
  }

  public void inserir(Cliente cliente) throws SQLException {
    String sql = "INSERT INTO cliente (cpf, nome, email, telefone) VALUES (?, ?, ?, ?)";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cliente.getCpf());
      stmt.setString(2, cliente.getNome());
      stmt.setString(3, cliente.getEmail());
      stmt.setString(4, cliente.getTelefone());

      stmt.executeUpdate();
    }
  }

  public Cliente buscarPorCpf(String cpf) throws SQLException {
    String sql = "SELECT cpf, nome, email, telefone FROM cliente WHERE cpf = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cpf);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Cliente cliente = new Cliente();
        cliente.setCpf(rs.getString("cpf"));
        cliente.setNome(rs.getString("nome"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        return cliente;
      }
    }
    return null;
  }

  public List<Cliente> listar() throws SQLException {
    String sql = "SELECT cpf, nome, email, telefone FROM cliente ORDER BY nome";
    List<Cliente> clientes = new ArrayList<>();

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Cliente cliente = new Cliente();
        cliente.setCpf(rs.getString("cpf"));
        cliente.setNome(rs.getString("nome"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        clientes.add(cliente);
      }
    }
    return clientes;
  }

  public void atualizar(Cliente cliente) throws SQLException {
    String sql = "UPDATE cliente SET nome = ?, email = ?, telefone = ? WHERE cpf = ?";

    try (Connection conexao = conexaoBanco.getConexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {

      stmt.setString(1, cliente.getNome());
      stmt.setString(2, cliente.getEmail());
      stmt.setString(3, cliente.getTelefone());
      stmt.setString(4, cliente.getCpf());

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
        SELECT c.cpf, c.nome,
               COUNT(i.id) as quantidade_ingressos,
               SUM(i.valor) as total_gasto,
               AVG(i.valor) as valor_medio_ingresso
        FROM cliente c
        INNER JOIN ingresso i ON c.cpf = i.cpf
        GROUP BY c.cpf, c.nome
        HAVING COUNT(DISTINCT i.sessao_id) >= 2
        ORDER BY total_gasto DESC
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
        item.setValorMedioIngresso(rs.getDouble("valor_medio_ingresso"));
        relatorio.add(item);
      }
    }
    return relatorio;
  }
}
