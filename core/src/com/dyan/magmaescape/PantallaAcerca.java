package com.dyan.magmaescape;

import com.badlogic.gdx.Screen;

public class PantallaAcerca extends Pantalla {
    private Juego juego;

    public PantallaAcerca(Juego juego) {
        this.juego = juego;
    }

    public void show() {
    }

    public void render(float delta) {
        this.borrarPantalla(1.0F, 0.0F, 0.0F);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}
