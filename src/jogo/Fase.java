package jogo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {

	private Image fundo;
	private Nave nave;
	private Timer timer;
	private List<Inimigo> inimigo;
	private boolean emJogo;

	private int[][] coordenadas = { { 2380, 29 }, { 2600, 59 }, { 1380, 89 }, { 780, 109 }, { 580, 139 }, { 880, 239 },
			{ 790, 259 }, { 760, 50 }, { 790, 150 }, { 1980, 209 }, { 560, 45 }, { 510, 70 }, { 930, 159 }, { 590, 80 },
			{ 530, 60 }, { 940, 59 }, { 990, 30 }, { 920, 200 }, { 900, 259 }, { 660, 50 }, { 540, 90 }, { 810, 220 },
			{ 860, 20 }, { 740, 180 }, { 820, 128 }, { 490, 170 }, { 700, 30 }, { 920, 300 }, { 856, 328 },
			{ 456, 320 } };

	public Fase() {

		setDoubleBuffered(true);
		setFocusable(true);

		addKeyListener(new TecladoAdapter());

		ImageIcon referencia = new ImageIcon("res\\fundo.png");
		fundo = referencia.getImage();
		nave = new Nave();
		emJogo = true;
		inicializaInimigos();
		timer = new Timer(5, this);
		timer.start();
	}

	public void inicializaInimigos() {
		inimigo = new ArrayList<Inimigo>();
		for (int i = 0; i < coordenadas.length; i++) {
			inimigo.add(new Inimigo(coordenadas[i][0], coordenadas[i][1]));
		}
	}

	public void paint(Graphics g) {
		Graphics2D graficos = (Graphics2D) g;
		graficos.drawImage(fundo, 0, 0, null);
		if (emJogo) {

			graficos.drawImage(nave.getImagem(), nave.getX(), nave.getY(), this);

			List<Missel> misseis = nave.getMisseis();

			for (int i = 0; i < misseis.size(); i++) {
				Missel m = (Missel) misseis.get(i);
				graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);
			}
			for (int i = 0; i < inimigo.size(); i++) {
				Inimigo in = inimigo.get(i);
				graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);
			}
			graficos.setColor(Color.WHITE);
			graficos.drawString("Inimigos: "+ inimigo.size(),5,15);
		}else {
			ImageIcon fimJogo = new ImageIcon("res\\game_over.jpg");
			graficos.drawImage(fimJogo.getImage(),0,0,null);
		}

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (inimigo.size() == 0) {
			emJogo = false;
		}
		List<Missel> misseis = nave.getMisseis();

		for (int i = 0; i < misseis.size(); i++) {
			Missel m = (Missel) misseis.get(i);
			if (m.isVisivel()) {
				m.mexer();
			} else {
				misseis.remove(i);
			}
		}
		for (int i = 0; i < inimigo.size(); i++) {
			Inimigo in = inimigo.get(i);
			if (in.isVisivel()) {
				in.mexer();
			} else {
				inimigo.remove(i);
			}
		}
		nave.mexer();
		checarColisoes();
		repaint();
	}

	public void checarColisoes() {
		Rectangle formaNave = nave.getBounds();
		Rectangle formaInimigo;
		Rectangle formaMissel;

		for (int i = 0; i < inimigo.size(); i++) {
			Inimigo tempInimigo = inimigo.get(i);
			formaInimigo = tempInimigo.getBounds();
			if (formaNave.intersects(formaInimigo)) {
				nave.setVisivel(false);
				tempInimigo.setVisivel(false);
				emJogo = false;
			}
		}
		List<Missel> misseis = nave.getMisseis();
		for (int i = 0; i < misseis.size(); i++) {
			Missel tempMissel = misseis.get(i);
			formaMissel = tempMissel.getBounds();
			for (int j = 0; j < inimigo.size(); j++) {
				Inimigo tempInimigo = inimigo.get(j);
				formaInimigo = tempInimigo.getBounds();
				if (formaMissel.intersects(formaInimigo)) {
					tempInimigo.setVisivel(false);
					tempMissel.setVisivel(false);
				}

			}
		}

	}

	private class TecladoAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				emJogo = true;
				nave = new Nave();
				inicializaInimigos();
			}
			nave.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			nave.keyReleased(e);
		}

	}

}
