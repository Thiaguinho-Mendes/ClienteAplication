package br.com.wmw.ui;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.wmw.domain.Cliente;
import br.com.wmw.domain.TipoPessoa;
import br.com.wmw.service.ClienteService;
import br.com.wmw.util.Imagens;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Grid;
import totalcross.ui.ImageControl;
import totalcross.ui.MainWindow;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.Event;
import totalcross.ui.event.GridEvent;
import totalcross.ui.gfx.Color;
import totalcross.util.UnitsConverter;

public class ListarContainer extends Container {

	private ArrayList<Cliente> clientes = new ArrayList<>();
	private Grid grid;
	private Button btVoltar;
	private int GAP = UnitsConverter.toPixels(DP + 8);
	
	private ImageControl background;

	public ListarContainer() {}

	@Override
	public void initUI() {
		this.background = new ImageControl(Imagens.fundo);
		this.background.scaleToFit = true;
		this.background.hwScale = true;
		this.background.centerImage = true;
		this.background.strechImage = true;
		add(background, LEFT, TOP, FILL, FILL);

		String[] gridCaptions = { "Código", "Nome", "Tipo Pessoa", "CPF/CNPJ", "Telefone", "E-mail" };
		int gridWidths[] = { -20, -100, -50, -50, -50, -100 };
		int gridAligns[] = { LEFT, LEFT, LEFT, LEFT, LEFT, LEFT };

		this.grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
		this.grid.verticalLineStyle = Grid.VERT_LINE;

		btVoltar = new Button("Voltar");
		this.btVoltar.transparentBackground = true;
		this.btVoltar.setBackForeColors(Color.WHITE, Color.WHITE);

		add(grid, LEFT + GAP, TOP + GAP, FILL - GAP, FILL - GAP * 9);
		add(btVoltar, LEFT + GAP, BOTTOM - GAP, FILL - GAP, PREFERRED);

		try {
			listarClientes();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		btVoltar.addPressListener((e) -> {
			MainWindow.getMainWindow().swap(new InicioContainer());
		});

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(Event e) {
		switch (e.type) {
		case GridEvent.SELECTED_EVENT:
			if (e.target == grid) {
				carregarCliente();
			}
			break;

		default:
			break;
		}
		super.onEvent(e);
	}

	public void listarClientes() throws SQLException {

		ClienteService service = new ClienteService();
		clientes = service.findAll();

		if (clientes.size() > 0) {
			String items[][] = new String[clientes.size()][];
			for (int i = 0; i < clientes.size(); i++) {
				Cliente cliente = clientes.get(i);
				items[i] = new String[] { cliente.getCodigo().toString(), cliente.getNome().toString(),
						cliente.getTipoPessoaOriginal().name(), cliente.getCpfCnpj().toString(),
						cliente.getTelefone().toString(), cliente.getEmail().toString() };
			}
			grid.setItems(items);
		} else {
			new MessageBox("Atenção", "Nenhum cliente cadastrado.").popup();
		}
	}

	public void carregarCliente() {
		Cliente cliente = new Cliente();
		String[] selectedItem = grid.getSelectedItem();

		if(selectedItem != null) {
			for (int i = 0; i < selectedItem.length; i++) {
				if (i == 0) {
					cliente.setCodigo(Integer.parseInt(selectedItem[i]));
				} else if (i == 1) {
					cliente.setNome(selectedItem[i]);
				} else if (i == 2) {
					cliente.setTipoPessoaOriginal(TipoPessoa.valueOf(selectedItem[i]));
				} else if (i == 3) {
					cliente.setCpfCnpj(selectedItem[i]);
				} else if (i == 4) {
					cliente.setTelefone(selectedItem[i]);
				} else if (i == 5) {
					cliente.setEmail(selectedItem[i]);
				} 
			}
			MainWindow.getMainWindow().swap(new ClienteContainer(cliente));
		}
	}
}
