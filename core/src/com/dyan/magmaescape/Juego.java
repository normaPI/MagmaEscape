/*
Esta clase representa a la aplicacion que corre, el objeto esta vivo durante toda la aplicacion
Autor: Norma P Iturbide
*/

package com.dyan.magmaescape;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {
	SpriteBatch batch;
	Texture img;

	public Juego() {
	}

	public void create() {
		this.setScreen(new PantallaMenu(this));
	}
}

