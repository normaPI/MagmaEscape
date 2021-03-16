package com.dyan.magmaescape;

import com.badlogic.gdx.Screen;

public class PantallaSeleccionarNivel extends Pantalla {
    private Juego juego;

    public PantallaSeleccionarNivel(Juego juego) {
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

    //este es un comentario
    // confirmacion del comentario
}
