package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;

public class PantallaCargando extends Pantalla {

    private Juego juego;
    private Pantallas screen;
    private Texture texturaCargando;
    private Sprite spriteCargando;
    private TextoBlanco textoBlanco;

    private float timerPasar;
    private static final float TIEMPO_ENTRE_FRAMES = 1f;
    //private float timerAnimacion = TIEMPO_ENTRE_FRAMES;

    public PantallaCargando(Juego juego, Pantallas screen){
        this.juego=juego;
        this.screen=screen;
    }

    @Override
    public void show() {
        texturaCargando=new Texture("cargando/loading.png");
        spriteCargando= new Sprite(texturaCargando);
        spriteCargando.setPosition(ANCHO/2-spriteCargando.getWidth()/2,ALTO/2-spriteCargando.getHeight()/2);
        textoBlanco=new TextoBlanco("font/arcade2.fnt");


    }

    @Override
    public void render(float delta) {

        actualizarNiveles(delta);
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //dibujando fondo
        spriteCargando.draw(batch);
        textoBlanco.mostrarMensaje(batch,"Cargando...",ANCHO/2,.2f*ALTO);
        spriteCargando.rotate(1.5f);
        batch.end();
    }

    private void actualizarNiveles(float delta) {
        timerPasar+=delta;

        if(timerPasar>= TIEMPO_ENTRE_FRAMES)
        {
            switch(screen){
                case MENU:
                    juego.setScreen(new PantallaMenu(juego));
                    break;
                case NIVEL1:
                    juego.setScreen(new PantallaNivel1(juego));
                    break;
                case NIVEL2:
                    juego.setScreen(new PantallaNivel2(juego));
                    break;
                case NIVEL3:
                    juego.setScreen(new PantallaNivel3(juego));
                    break;
                case SELECCION:
                    Preferences prefs = Gdx.app.getPreferences("MusicPreference");
                    boolean musicOn = prefs.getBoolean("musicOn", true);
                    if(musicOn){
                        juego.detener(Juego.TipoMusica.NIVELES);
                        juego.reproducir(Juego.TipoMusica.MENU);
                    }
                    juego.setScreen(new PantallaSeleccionarNivel(juego));
                    break;
            }
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
