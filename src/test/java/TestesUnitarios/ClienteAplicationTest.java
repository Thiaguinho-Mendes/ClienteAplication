package TestesUnitarios;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;

import br.com.wmw.domain.Cliente;
import br.com.wmw.domain.Origem;
import br.com.wmw.domain.TipoPessoa;
import br.com.wmw.service.ClienteService;
import br.com.wmw.ui.CpfCnpjValidator;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteAplicationTest {
	
	ClienteService service = new ClienteService();
	

	@Test
	@Order(1)
	void deveRetornarTrueClientesCadastrado() throws SQLException {
		Cliente cliente1 = new Cliente("Maria", TipoPessoa.FISICA, "867.947.249-28", "48999999999", "", Origem.APP);
		Cliente cliente2 = new Cliente("João", TipoPessoa.JURIDICA, "01.647.466/0001-46", "48888888888", "", Origem.APP);
		if(CpfCnpjValidator.isCpfValid(cliente1.getCpfCnpj()) && !service.existCpfCnpj(cliente1.getCpfCnpj())) {
			service.create(cliente1);
		}
		if (CpfCnpjValidator.isCnpjValid(cliente2.getCpfCnpj()) && !service.existCpfCnpj(cliente2.getCpfCnpj())) {
			service.create(cliente2);
		}
		assertTrue(service.existCpfCnpj(cliente1.getCpfCnpj()));
		assertTrue(service.existCpfCnpj(cliente2.getCpfCnpj()));
	}
	
	@Test
	void deveRetornarFalseClientesCadastradoComCpfInnvalido() throws SQLException {
		Cliente cliente1 = new Cliente("Maria", TipoPessoa.FISICA, "111.111.111-11", "48999999999", "", Origem.APP);
		if(CpfCnpjValidator.isCpfValid(cliente1.getCpfCnpj()) && !service.existCpfCnpj(cliente1.getCpfCnpj())) {
			service.create(cliente1);
		}
		assertFalse(service.existCpfCnpj(cliente1.getCpfCnpj()));
	}
	
	@Test
	void deveRetornarTrueClientesCadastradoComCpfValido() throws SQLException {
		Cliente cliente1 = new Cliente("Romeu", TipoPessoa.FISICA, "810.703.709-08", "48999999999", "", Origem.APP);
		if(CpfCnpjValidator.isCpfValid(cliente1.getCpfCnpj()) && !service.existCpfCnpj(cliente1.getCpfCnpj())) {
			service.create(cliente1);
		} 
		assertTrue(service.existCpfCnpj(cliente1.getCpfCnpj()));
	}
	
	@Order(4)
	@Test
	void deveRetornarTrueClientesCadastradoComCnpjValido() throws SQLException {
		Cliente cliente1 = new Cliente("Julieta", TipoPessoa.JURIDICA, "49.391.651/0001-49", "48888888888", "", Origem.APP);
		if(CpfCnpjValidator.isCnpjValid(cliente1.getCpfCnpj()) && !service.existCpfCnpj(cliente1.getCpfCnpj())) {
			service.create(cliente1);
		} 
		assertTrue(service.existCpfCnpj(cliente1.getCpfCnpj()));
	}
	
	@Test
	void deveRetornarFalseClientesCadastradoComCnpjfInnvalido() throws SQLException {
		Cliente cliente1 = new Cliente("Mario", TipoPessoa.JURIDICA, "11.111.111/0001-11", "48999999999", "", Origem.APP);
		if(CpfCnpjValidator.isCnpjValid(cliente1.getCpfCnpj()) && !service.existCpfCnpj(cliente1.getCpfCnpj())) {
			service.create(cliente1);
		}
		assertFalse(service.existCpfCnpj(cliente1.getCpfCnpj()));
	}
	
	@Test
	@Order(5)
	void devePermitirDeletarCliente() throws SQLException {
		Cliente cliente1 = new Cliente("Julieta", TipoPessoa.JURIDICA, "49.391.651/0001-49", "48888888888", "", Origem.APP);
		Cliente cliente2 = new Cliente("João", TipoPessoa.JURIDICA, "01.647.466/0001-46", "48888888888", "", Origem.APP);
		service.deleteClienteByApp(cliente1.getCpfCnpj());
		service.deleteClienteByApp(cliente2.getCpfCnpj());
		assertFalse(service.existCpfCnpj(cliente1.getCpfCnpj()));
		assertFalse(service.existCpfCnpj(cliente2.getCpfCnpj()));
	}
	
	@Test
	@Order(3)
	void devePermitirAtualizarCliente() throws SQLException {
		Cliente cliente1 = service.findByCpfCnpj("867.947.249-28");
		cliente1.setEmail("Maria@maria.com");
		service.update(cliente1);
		cliente1 = service.findByCpfCnpj("867.947.249-28");
		assertTrue(cliente1.getEmail().equals("Maria@maria.com"));
	}
	
	@Test
	void deveRetornarFalseClientesCadastradoSemNome() throws SQLException {
		Cliente cliente1 = new Cliente(null, TipoPessoa.FISICA, "123.456.789-00", "48999999999", "", Origem.APP);
		assertThrows(SQLException.class, new Executable() {
		    @Override
		    public void execute() throws Throwable {
		        service.create(cliente1);
		    }
		});
	}
	
	@Test
	void deveRetornarFalseClientesCadastradoSemTipoPessoa() throws SQLException {
		Cliente cliente1 = new Cliente("Maria", null, "123.456.789-00", "48999999999", "", Origem.APP);
		assertThrows(NullPointerException.class, new Executable() {
		    @Override
		    public void execute() throws Throwable {
		        service.create(cliente1);
		    }
		});
	}
	
	@Test
	void deveRetornarFalseClientesCadastradoSemCpfCnpj() throws SQLException {
		Cliente cliente1 = new Cliente(null, TipoPessoa.FISICA, null, "48999999999", "", Origem.APP);
		assertThrows(SQLException.class, new Executable() {
		    @Override
		    public void execute() throws Throwable {
		        service.create(cliente1);
		    }
		});
	}
	
	@Test
	void deveRetornarFalseClientesCadastradoSemTelefone() throws SQLException {
		Cliente cliente1 = new Cliente(null, TipoPessoa.FISICA, "123.456.789-00", null, "", Origem.APP);
		assertThrows(SQLException.class, new Executable() {
		    @Override
		    public void execute() throws Throwable {
		        service.create(cliente1);
		    }
		});
	}
	
}
