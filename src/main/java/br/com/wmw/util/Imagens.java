package br.com.wmw.util;

import totalcross.ui.image.Image;

public class Imagens {

	public Imagens() {
	}

	public static Image background;
	public static Image logo;
	public static Image backgroundLogo;
	public static Image fundo;
	
	public static void loadImagens() {
		
		try {
			background = new Image("Imagens/ic_adaptive_launcher_shell_background.png");
			logo = new Image("Imagens/Logo_5.png");
			backgroundLogo = new Image("Imagens/logoback1.png");
			fundo = new Image("Imagens/fundo.png");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
