package br.com.wmw.MainWindow;

import java.sql.SQLException;

import br.com.wmw.service.DatabaseManager;
import br.com.wmw.ui.InicioContainer;
import br.com.wmw.util.TelaInicial;
import totalcross.sys.Settings;
import totalcross.ui.MainWindow;

public class TelaMainWindow extends MainWindow {

	public TelaMainWindow() throws SQLException {
		setUIStyle(Settings.MATERIAL_UI);
		DatabaseManager.loadTabelas();
	}

	static {
		Settings.activationId = "Thiaguinho Bank";
		Settings.appVersion = "1.0.0";
		Settings.iosCFBundleIdentifier = "br.com.wmw.ClienteAplication";
	}

	public void initUI() {
		TelaInicial inicial = new TelaInicial();
		InicioContainer inicioContainer = new InicioContainer();
		inicial.popupNonBlocking();
		inicioContainer.transparentBackground = true;
		add(inicioContainer, LEFT, TOP, FILL, FILL);
	}

}
