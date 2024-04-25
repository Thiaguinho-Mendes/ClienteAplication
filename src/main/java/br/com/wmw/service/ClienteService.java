package br.com.wmw.service;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.wmw.domain.Cliente;
import br.com.wmw.domain.Origem;
import br.com.wmw.domain.TipoPessoa;
import br.com.wmw.restAPI.RestApi;
import br.com.wmw.restAPI.RestApi.Response;
import br.com.wmw.ui.CpfCnpjValidator;
import totalcross.io.IOException;
import totalcross.json.JSONObject;
import totalcross.net.HttpStream;
import totalcross.net.HttpStream.Options;
import totalcross.net.URI;
import totalcross.net.UnknownHostException;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class ClienteService {

	public void create(Cliente cliente) throws SQLException {
		String sql = "INSERT INTO cliente (nome, tipoPessoa, cpfCnpj, telefone, email, origem) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = DatabaseManager.getConnection().prepareStatement(sql);
		ps.setString(1, cliente.getNome());
		ps.setString(2, cliente.getTipoPessoaOriginal().name());
		ps.setString(3, cliente.getCpfCnpj());
		ps.setString(4, cliente.getTelefone());
		ps.setString(5, cliente.getEmail());
		ps.setString(6, cliente.getOrigem());
		ps.executeUpdate();
		ps.close();
	}

	public ArrayList<Cliente> findAll() throws SQLException {
		ArrayList<Cliente> clientes = new ArrayList<>();
		String sql = "SELECT codigo, nome, tipoPessoa, cpfCnpj, telefone, email, origem FROM cliente";
		try (Statement ps = DatabaseManager.getConnection().createStatement()) {

			try (ResultSet rs = ps.executeQuery(sql)) {

				while (rs.next()) {
					Cliente cliente = new Cliente();
					cliente.setCodigo(rs.getInt(1));
					cliente.setNome(rs.getString(2).toString());
					cliente.setTipoPessoaOriginal(TipoPessoa.valueOf(rs.getString(3)));
					cliente.setCpfCnpj(rs.getString(4));
					cliente.setTelefone(rs.getString(5));
					cliente.setEmail(rs.getString(6));
					cliente.setOrigemOriginal(Origem.valueOf(rs.getString(7)));
					clientes.add(cliente);
				}
			}
		}

		return clientes;

	}

	public Cliente findByCpfCnpj(String cpfCnpj) throws SQLException {
		String sql = "SELECT codigo, nome, tipoPessoa, cpfCnpj, telefone, email, origem FROM cliente WHERE cpfCnpj = ?";
		try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement(sql)) {
			ps.setString(1, cpfCnpj);
			try (ResultSet rs = ps.executeQuery()) {

				Cliente cliente = new Cliente();
				cliente.setCodigo(rs.getInt(1));
				cliente.setNome(rs.getString(2).toString());
				cliente.setTipoPessoaOriginal(TipoPessoa.valueOf(rs.getString(3)));
				cliente.setCpfCnpj(rs.getString(4));
				cliente.setTelefone(rs.getString(5));
				cliente.setEmail(rs.getString(6));
				cliente.setOrigemOriginal(Origem.valueOf(rs.getString(7)));

				return cliente;
			}

		}

	}

	public void deleteClienteByApp(String cpfCnpj) throws SQLException {
		String sql = "DELETE FROM cliente WHERE cpfCnpj = ?";
		try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement(sql)) {
			ps.setString(1, cpfCnpj);
			ps.executeUpdate();

			if (checkClienteExistsWeb(cpfCnpj)) {
				RestApi.deleteClienteByWeb(cpfCnpj);
			}
		}
	}

	public void update(Cliente cliente) throws SQLException {
		String sql = "UPDATE cliente SET telefone = ?, email = ? WHERE cpfCnpj = ?";
		try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement(sql)) {
			ps.setString(1, cliente.getTelefone());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getCpfCnpj());
			ps.executeUpdate();
		}

	}

	public void recebeDados(Response<Cliente> response) throws SQLException {
		response.listData.forEach(c -> {
			Cliente cliente = new Cliente();
			cliente.setCodigo(c.getCodigo());
			cliente.setNome(c.getNome());
			cliente.setTipoPessoaOriginal(c.getTipoPessoaOriginal());
			cliente.setCpfCnpj(c.getCpfCnpj());
			cliente.setTelefone(c.getTelefone());
			cliente.setEmail(c.getEmail());
			cliente.setOrigemOriginal(c.getOrigemOriginal());

			try {
				if (!existCpfCnpj(cliente.getCpfCnpj())) {
					create(cliente);
				} else {
					update(cliente);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		});

		syncClientes();
	}

	@SuppressWarnings("resource")
	public void enviaDados(String url, Options options) throws SQLException, UnknownHostException, IOException {
		ArrayList<Cliente> clientes = findAll();

		for (Cliente c : clientes) {
			JSONObject jsonCliente = new JSONObject();
			jsonCliente.put("nome", c.getNome());
			jsonCliente.put("tipoPessoa", c.getTipoPessoa());
			if(c.getTipoPessoaOriginal().equals(TipoPessoa.JURIDICA)) {
				jsonCliente.put("cpfCnpj", CpfCnpjValidator.cleanCpfCnpjRuc(c.getCpfCnpj()));
			} else {
				jsonCliente.put("cpfCnpj", c.getCpfCnpj());
			}
			jsonCliente.put("telefone", c.getTelefone());
			jsonCliente.put("email", c.getEmail());
			jsonCliente.put("origem", c.getOrigemOriginal().name());

			options.data = jsonCliente.toString();
			HttpStream http = new HttpStream(new URI(url), options);
			http.close();

		}
		;
	}

	public boolean existCpfCnpj(String cpfCnpj) throws SQLException {
		String sql = "SELECT 1 FROM cliente WHERE cpfCnpj = ?";
		try (PreparedStatement ps = DatabaseManager.getConnection().prepareStatement(sql)) {
			ps.setString(1, cpfCnpj);
			try (ResultSet rs = ps.executeQuery()){
				return rs.next();
			}
		}
	}

	public boolean checkClienteExistsWeb(String cpfCnpj) {
		try {
			return RestApi.checkClienteExists(cpfCnpj);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void syncClientes() throws SQLException {
		ArrayList<Cliente> clientesApp = findAll();
		for (Cliente clienteApp : clientesApp) {
			if (!checkClienteExistsWeb(clienteApp.getCpfCnpj())) {
				deleteClienteByApp(clienteApp.getCpfCnpj());

			}
		}
	}

}
