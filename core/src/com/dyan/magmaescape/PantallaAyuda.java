package com.dyan.magmaescape;

import com.badlogic.gdx.Screen;

public class PantallaAyuda extends Pantalla {
    private Juego juego;

    public PantallaAyuda(Juego juego) {
        this.juego = juego;
    }

    public void show() {
    }

    public void render(float delta) {
        this.borrarPantalla(1.0F, 1.0F, 0.0F);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}
