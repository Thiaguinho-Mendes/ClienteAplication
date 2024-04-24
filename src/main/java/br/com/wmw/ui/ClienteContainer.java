package br.com.wmw.ui;

import java.sql.SQLException;

import br.com.wmw.domain.Cliente;
import br.com.wmw.domain.TipoPessoa;
import br.com.wmw.service.ClienteService;
import br.com.wmw.util.Imagens;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.gfx.Color;

public class ClienteContainer extends Container {

	private ImageControl background;
	
	private Label lbCadastroCliente;
	private Label lbCodigo;
	private Label lbName;
	private Label lbTipoPessoa;
	private Label lbCpfCnpj;
	private Label lbTelefone;
	private Label lbEmail;

	private Edit editCodigo;
	private Edit editName;
	private Edit cbTipoPessoa;
	private Edit editCpfCnpj;
	private Edit editTelefone;
	private Edit editEmail;

	private Button btVoltar;
	private Button btSalvar;
	private Button btDeletar;

	public ClienteContainer(Cliente c) {
		
		this.background = new ImageControl(Imagens.fundo);
		this.background.scaleToFit = true;
		this.background.hwScale = true;
		this.background.centerImage = true;
		this.background.strechImage = true;
		
		this.lbCadastroCliente = new Label("CLIENTE");
		this.lbCadastroCliente.transparentBackground = true;
		this.lbCadastroCliente.set3d(true);
		this.lbCadastroCliente.setHighlighted(true);

		this.lbCodigo = new Label("Código");
		this.lbCodigo.transparentBackground = true;
		this.lbCodigo.setHighlighted(true);
		this.editCodigo = new Edit();
		this.editCodigo.setEditable(false);
		this.editCodigo.setText(c.getCodigo().toString());

		this.lbName = new Label("Nome");
		this.lbName.set3d(true);
		this.lbName.setHighlighted(true);
		this.lbName.transparentBackground = true;
		this.editName = new Edit();
		this.editName.setEditable(false);
		this.editName.setText(c.getNome());

		this.lbTipoPessoa = new Label("Tipo Pessoa");
		this.lbTipoPessoa.transparentBackground = true;
		this.lbTipoPessoa.setHighlighted(true);
		this.cbTipoPessoa = new Edit();
		this.cbTipoPessoa.setEditable(false);
		this.cbTipoPessoa.setText(c.getTipoPessoaOriginal().name());

		this.lbCpfCnpj = new Label("CPF/CNPJ");
		this.lbCpfCnpj.transparentBackground = true;
		this.lbCpfCnpj.setHighlighted(true);
		if(c.getTipoPessoaOriginal().name().equals(TipoPessoa.FISICA.name())) {
			this.editCpfCnpj = new Edit("999.999.999-99");
		} else {
			this.editCpfCnpj = new Edit("99.999.999/9999-99");
		}
		this.editCpfCnpj.setEditable(false);
		this.editCpfCnpj.setText(c.getCpfCnpj());

		this.lbTelefone = new Label("Telefone");
		this.lbTelefone.transparentBackground = true;
		this.lbTelefone.setHighlighted(true);
		this.editTelefone = new Edit("(99)99999-9999");
		this.editTelefone.setText(c.getTelefone());

		this.lbEmail = new Label("E-mail");
		this.lbEmail.transparentBackground = true;
		this.lbEmail.setHighlighted(true);
		this.editEmail = new Edit();
		this.editEmail.setText(c.getEmail());

		this.btVoltar = new Button("Voltar");
		this.btVoltar.transparentBackground = true;
		this.btVoltar.setBackForeColors(Color.WHITE, Color.WHITE);
		this.btSalvar = new Button("Salvar");
		this.btSalvar.transparentBackground = true;
		this.btSalvar.setBackForeColors(Color.WHITE, Color.WHITE);
		this.btDeletar = new Button("Deletar");
		this.btDeletar.transparentBackground = true;
		this.btDeletar.setBackForeColors(Color.WHITE, Color.WHITE);

		btSalvar.addPressListener((e) -> {
			c.setTelefone(editTelefone.getText());
			if (c.getTelefone().isEmpty()) {
				new MessageBox("Atenção", lbTelefone.getText() + " é obrigatório!").popup();
				return;
			}
			c.setEmail(editEmail.getText());
			update(c);
		});

		btVoltar.addPressListener((e) -> {
			MainWindow.getMainWindow().swap(new ListarContainer());
		});

		btDeletar.addPressListener((e) -> {
			delete(c);
		});

	}

	@Override
	public void initUI() {
		add(background, LEFT, TOP, FILL, FILL);
		add(lbCadastroCliente, CENTER, TOP + 15);
		add(lbCodigo, LEFT + 15, AFTER + 30);
		add(editCodigo, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(lbName, LEFT + 15, AFTER + 30);
		add(editName, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(lbTipoPessoa, LEFT + 15, AFTER + 30);
		add(cbTipoPessoa, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(lbCpfCnpj, LEFT + 15, AFTER + 30);
		add(editCpfCnpj, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(lbTelefone, LEFT + 15, AFTER + 30);
		add(editTelefone, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(lbEmail, LEFT + 15, AFTER + 30);
		add(editEmail, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(btSalvar, RIGHT - 15, BOTTOM - 15);
		add(btVoltar, LEFT + 15, BOTTOM - 15);
		add(btDeletar, CENTER, BOTTOM - 15);
	}

	private void update(Cliente c) {
		ClienteService service = new ClienteService();
		try {
			service.update(c);
			new MessageBox("Informação", "Cliente atualizado com sucesso!").popup();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void delete(Cliente c) {
		ClienteService service = new ClienteService();
		try {
			service.deleteClienteByApp(c.getCpfCnpj());
			new MessageBox("Informação", "Cliente deletado com sucesso!").popup();
			MainWindow.getMainWindow().swap(new ListarContainer());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
