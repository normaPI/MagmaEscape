/*
Esta clase representa a la aplicacion que corre, el objeto esta vivo durante toda la aplicacion
Autor: Norma P Iturbide
*/

package com.dyan.magmaescape;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {
	SpriteBatch batch;
	Texture img;

	//Musica
	private Music menuM;
	private Music nivel;

	private Sound morir;
	private final AssetManager manager;

	public Juego()
	{
		manager= new AssetManager();
	}

	public void create()
	{
		this.setScreen(new PantallaMenu(this));
		reproducir(TipoMusica.MENU);
	}

	public void reproducir(TipoMusica tipo)
	{

		switch (tipo)
		{
			case MENU:
				manager.clear();
				manager.load("musica/menu.wav", Music.class);
				manager.finishLoading();
				menuM=manager.get("musica/menu.wav");
				menuM.play();
				menuM.setLooping(true);
				menuM.setVolume(1);
				break;

			case NIVELES:
				manager.clear();
				manager.load("musica/niveles.wav",Music.class);
				manager.finishLoading();
				nivel=manager.get("musica/niveles.wav");
				nivel.play();
				nivel.setLooping(true);
				menuM.setVolume(1);
				break;

			case MUERTE:
				manager.load("musica/youlose.wav",Sound.class);
				manager.finishLoading();
				morir=manager.get("musica/youlose.wav");
				morir.play();
				nivel.stop();
				break;



		}



	}


	public enum TipoMusica
	{
		MENU,
		NIVELES,
		MUERTE
	}
}

