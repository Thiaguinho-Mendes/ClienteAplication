package br.com.wmw.util;

import totalcross.ui.ImageControl;
import totalcross.ui.Window;
import totalcross.ui.anim.ControlAnimation;
import totalcross.ui.anim.FadeAnimation;

public class TelaInicial extends Window {

	private ImageControl logo;
	private ImageControl background;
	

	
	public TelaInicial() {}
	
	protected void onPopup() {
		
		Imagens.loadImagens();
		background = new ImageControl(Imagens.background);
		logo = new ImageControl(Imagens.logo);
		
		background.scaleToFit = true;
		background.hwScale = true;
		background.centerImage = true;
		background.strechImage = true;
		add(background, LEFT, TOP, FILL, FILL);
		
		logo.scaleToFit = true;
		logo.centerImage = true;
		logo.transparentBackground = true;
		add(logo, CENTER, CENTER, PARENTSIZE + 50, PARENTSIZE + 50);
		
		FadeAnimation.create(logo, true, null, 6000).then(FadeAnimation.create(logo, false, this::onAnimation, 3000)).start();
		
		FadeAnimation.create(background, true, null, 6000).then(FadeAnimation.create(background, true, this::onAnimation, 3000)).start();
		
	}
	
	protected void onAnimation(ControlAnimation anim) {
		this.unpop();
	}
	
}
