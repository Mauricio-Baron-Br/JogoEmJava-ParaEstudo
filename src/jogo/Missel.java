
package jogo;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Missel {
	private Image imagem;
	private int x, y;
	private int largura, altura;
	private boolean isVisivel;

	private static final int LARGURA_TELA = 500;
	private static final int VELOCIDADE = 2;

	public Missel(int x, int y) {
		this.x = x;
		this.y = y;
		ImageIcon referencia = new ImageIcon("res\\missel.png");
		imagem = referencia.getImage();
		altura = imagem.getHeight(null);
		largura = imagem.getWidth(null);
		isVisivel = true;

	}

	public void mexer() {
		this.x += VELOCIDADE;
		if (this.x > LARGURA_TELA) {
			isVisivel = true;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisivel() {
		return isVisivel;
	}

	public void setVisivel(boolean isVisivel) {
		this.isVisivel = isVisivel;
	}

	public Image getImagem() {
		return imagem;
	}

	public Rectangle getBounds() {

		return new Rectangle(x, y, largura, altura);

	}

}
