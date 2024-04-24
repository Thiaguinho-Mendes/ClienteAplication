package br.com.wmw.ui;

import br.com.wmw.restAPI.RestApi;
import br.com.wmw.util.Imagens;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.ImageControl;
import totalcross.ui.MainWindow;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class InicioContainer extends Container {

	private Button btCadastrar;
	private Button btListar;
	private Button btSync;

	public void initUI() {
		ImageControl backgroundLogo = new ImageControl(Imagens.backgroundLogo);
		backgroundLogo.scaleToFit = true;
		backgroundLogo.hwScale = true;
		backgroundLogo.centerImage = true;
		backgroundLogo.strechImage = true;
		
		ImageControl logo = new ImageControl(Imagens.logo);		
		logo.scaleToFit = true;
		logo.centerImage = true;
		logo.transparentBackground = true;
		

		try {
			Image imCadastro = new Image("Imagens/icone_adicionar-.png");
			btCadastrar = new Button("Cadastrar", imCadastro.scaledBy(0.2,0.2), LEFT,10);
			btCadastrar.transparentBackground = true;
			btCadastrar.setBackForeColors(Color.WHITE, Color.WHITE);
			
			Image imListar = new Image("Imagens/ic_docs_front_purpleheart.png");
			btListar = new Button("Clientes", imListar.scaledBy(0.2,0.2), LEFT,10);
			btListar.transparentBackground = true;
			btListar.setBackForeColors(Color.WHITE, Color.WHITE);
			
			Image imSync = new Image("Imagens/sync.png");
			btSync = new Button("", imSync.scaledBy(0.1,0.1), LEFT, 1);
			btSync.transparentBackground = true;
			btSync.setBackForeColors(Color.WHITE, Color.WHITE);
			btSync.setBorder(BORDER_NONE);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		

		add(backgroundLogo, LEFT, TOP, FILL, FILL);
		add(logo, CENTER, CENTER, PARENTSIZE + 50, PARENTSIZE + 50);
		add(btCadastrar, RIGHT - 15, BOTTOM - 15);
		add(btListar, LEFT + 15, BOTTOM - 15);
		add(btSync, RIGHT, TOP);

		btCadastrar.addPressListener((e) -> {
			MainWindow mainWindow = MainWindow.getMainWindow();
			if (mainWindow != null) {
				mainWindow.swap(new CadastroContainer());
			}
		});

		btListar.addPressListener((e) -> {
			MainWindow mainWindow = MainWindow.getMainWindow();
			if (mainWindow != null) {
				mainWindow.swap(new ListarContainer());
			};
		});

		btSync.addPressListener((e) -> {
			RestApi api = new RestApi();
			api.popup();
		});
	}

}
