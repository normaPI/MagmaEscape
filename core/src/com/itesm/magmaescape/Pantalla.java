/*
Esta clase Pantalla para que demas clases vayan heredando
Autor: Norma P Iturbide
*/

package com.itesm.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Pantalla implements Screen {
    public static final float ANCHO = 1280.0F;
    public static final float ALTO = 720.0F;
    protected OrthographicCamera camara = new OrthographicCamera(1280.0F, 720.0F);
    protected Viewport vista;
    protected SpriteBatch batch;

    public Pantalla() {
        this.camara.position.set(640.0F, 360.0F, 0.0F);
        this.camara.update();
        this.vista = new StretchViewport(1280.0F, 720.0F, this.camara);
        this.batch = new SpriteBatch();
    }

    protected void borrarPantalla() {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Gdx.gl.glClear(16384);
    }

    protected void borrarPantalla(float r, float g, float b) {
        Gdx.gl.glClearColor(r, g, b, 1.0F);
        Gdx.gl.glClear(16384);
    }

    public void resize(int width, int height) {
        this.vista.update(width, height);
    }

    public void hide() {
        this.dispose();
    }
}
