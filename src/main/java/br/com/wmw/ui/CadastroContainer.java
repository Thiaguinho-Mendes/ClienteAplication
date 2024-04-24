package br.com.wmw.ui;

import java.sql.SQLException;

import br.com.wmw.domain.Cliente;
import br.com.wmw.domain.Origem;
import br.com.wmw.domain.TipoPessoa;
import br.com.wmw.service.ClienteService;
import br.com.wmw.util.Imagens;
import totalcross.io.IOException;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;


public class CadastroContainer extends Container {

	private ImageControl background;

	private Label lbCadastroCliente;
	private Label lbName;
	private Label lbTipoPessoa;
	private Label lbCpfCnpj;
	private Label lbTelefone;
	private Label lbEmail;

	private Edit editName;
	private ComboBox cbTipoPessoa;
	private Edit editCpf;
	private Edit editCnpj;
	private Edit editTelefone;
	private Edit editEmail;

	private Button btVoltar;
	private Button btSalvar;

	ClienteService service = new ClienteService();

	public CadastroContainer() {
		
		this.background = new ImageControl(Imagens.fundo);
		this.background.scaleToFit = true;
		this.background.hwScale = true;
		this.background.centerImage = true;
		this.background.strechImage = true;
		
		this.lbCadastroCliente = new Label("CADASTRO DE CLIENTE");
		this.lbCadastroCliente.transparentBackground = true;
		this.lbCadastroCliente.set3d(true);
		this.lbCadastroCliente.setHighlighted(true);
		
		
		this.lbName = new Label("Nome");
		this.lbName.set3d(true);
		this.lbName.setHighlighted(true);
		this.lbName.transparentBackground = true;
		this.editName = new Edit();

		this.lbTipoPessoa = new Label("Tipo Pessoa");
		this.lbTipoPessoa.transparentBackground = true;
		this.lbTipoPessoa.setHighlighted(true);
		this.setCbTipoPessoa(new ComboBox());
		for (TipoPessoa tipo : TipoPessoa.values()) {
			this.getCbTipoPessoa().add(tipo.name());
		}

		this.lbCpfCnpj = new Label("CPF/CNPJ");
		this.lbCpfCnpj.transparentBackground = true;
		this.lbCpfCnpj.setHighlighted(true);
		this.setEditCpf(new Edit("999.999.999-99"));
		this.getEditCpf().setValidChars("0123456789");
		this.editCnpj = new Edit("99.999.999/9999-99");
		this.editCnpj.setValidChars("0123456789");
		this.editCnpj.setVisible(false);

		this.lbTelefone = new Label("Telefone");
		this.lbTelefone.transparentBackground = true;
		this.lbTelefone.setHighlighted(true);
		this.editTelefone = new Edit("(99)99999-9999");
		this.editTelefone.setValidChars("0123456789");

		this.lbEmail = new Label("E-mail");
		this.lbEmail.transparentBackground = true;
		this.lbEmail.setHighlighted(true);
		this.editEmail = new Edit();

		this.btVoltar = new Button("Voltar");
		this.btVoltar.transparentBackground = true;
		this.btVoltar.setBackForeColors(Color.WHITE, Color.WHITE);

		try {
			Image imCadastro = new Image("Imagens/addperson.png");
			this.btSalvar = new Button("Salvar",imCadastro.scaledBy(0.2,0.2), LEFT,10);
			this.btSalvar.transparentBackground = true;
			this.btSalvar.setBackForeColors(Color.WHITE, Color.WHITE);
		} catch (IOException | ImageException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void initUI() {
		add(background, LEFT, TOP, FILL, FILL);
		add(lbCadastroCliente, CENTER, TOP + 15);
		add(lbName, LEFT + 15, AFTER + 30);
		add(editName, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(lbTipoPessoa, LEFT + 15, AFTER + 30);
		add(getCbTipoPessoa(), AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(lbCpfCnpj, LEFT + 15, AFTER + 30);
		add(getEditCpf(), AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(editCnpj, lbCpfCnpj.getWidth() + 30, lbCpfCnpj.getHeight() + 220, FILL - 15, 60);
		add(lbTelefone, LEFT + 15, AFTER + 30);
		add(editTelefone, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(lbEmail, LEFT + 15, AFTER + 30);
		add(editEmail, AFTER + 25, AFTER - 45, FILL - 15, 60);
		add(btSalvar, RIGHT - 15, BOTTOM - 15);
		add(btVoltar, LEFT + 15, BOTTOM - 15);

		btVoltar.addPressListener((e) -> {
			MainWindow.getMainWindow().swap(new InicioContainer());
		});

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(Event e) {
		switch (e.type) {
		case ControlEvent.PRESSED:
			if (e.target == btSalvar) {
				save();
			}
			break;
		case ControlEvent.FOCUS_IN:
			if (e.target == getCbTipoPessoa()) {
				if (this.getCbTipoPessoa().getValue() == TipoPessoa.JURIDICA.name()) {
					this.editCnpj.setVisible(true);
					this.getEditCpf().setVisible(false);
					this.getEditCpf().clear();
				} else {
					this.getEditCpf().setVisible(true);
					this.editCnpj.setVisible(false);
					this.editCnpj.clear();
				}
			}

		default:
			break;
		}
		super.onEvent(e);
	}

	private void save() {
		String name = editName.getText();
		if (name.isEmpty()) {
			new MessageBox("Atenção", lbName.getText() + " é obrigatório!").popup();
			return;
		}
		String tipoPessoa = getCbTipoPessoa().getSelectedItem().toString();
		if (tipoPessoa.isEmpty()) {
			new MessageBox("Atenção", lbTipoPessoa.getText() + " é obrigatório!").popup();
			return;
		}

		String cpfCnpj;
		if (tipoPessoa == TipoPessoa.FISICA.name()) {
			cpfCnpj = getEditCpf().getText();
			if(!CpfCnpjValidator.isCpfValid(cpfCnpj)) {
				new MessageBox("Atenção", "CPF inválido!").popup();
				return;
			}
		} else {
			cpfCnpj = editCnpj.getText();
			if(!CpfCnpjValidator.isCnpjValid(cpfCnpj)) {
				new MessageBox("Atenção", "CNPJ inválido!").popup();
				return;
			}
		}

		if (cpfCnpj.isEmpty()) {
			new MessageBox("Atenção", lbCpfCnpj.getText() + " é obrigatório!").popup();
			return;
		} 
		String telefone = editTelefone.getTextWithoutMask();
		if (telefone.isEmpty()) {
			new MessageBox("Atenção", lbTelefone.getText() + " é obrigatório!").popup();
			return;
		}
		String email = editEmail.getText();

		Cliente cliente = new Cliente(name, TipoPessoa.valueOf(tipoPessoa), cpfCnpj, telefone, email, Origem.APP);

		try {
			if (!service.existCpfCnpj(cpfCnpj)) {
				service.create(cliente);
				new MessageBox("Informação", "Cliente cadastrado com sucesso!").popup();
				MainWindow.getMainWindow().swap(new InicioContainer());
			} else {
				new MessageBox("Informação", lbCpfCnpj.getText() + " já cadastrado!").popup();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return;

	}

	public Edit getEditCpf() {
		return editCpf;
	}

	public void setEditCpf(Edit editCpf) {
		this.editCpf = editCpf;
	}

	public ComboBox getCbTipoPessoa() {
		return cbTipoPessoa;
	}

	public void setCbTipoPessoa(ComboBox cbTipoPessoa) {
		this.cbTipoPessoa = cbTipoPessoa;
	}

}
