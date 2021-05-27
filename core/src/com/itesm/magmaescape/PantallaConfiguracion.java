/*
Esta clase representa el primer nivel
Autor: Carlos Daniel Castañeda
*/


package com.itesm.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaConfiguracion extends Pantalla {
    private Juego juego;
    private Texture texturaFondo;
    private Stage escenaMenu;

    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
    }

    public void show() {
        this.crearConfig();
        //1: bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void crearConfig() {
        this.escenaMenu = new Stage(this.vista);
        this.texturaFondo = new Texture("configuracion/pantallaConfiguracion.png");
        Button btnRegresar = this.crearBoton("configuracion/button_regresar.png","configuracion/button_regresar-2.png");
        btnRegresar.setPosition(ANCHO/6,ALTO/6, Align.center);
        this.escenaMenu.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                PantallaConfiguracion.this.juego.setScreen(new PantallaMenu(PantallaConfiguracion.this.juego));
            }
        });

        Texture texturaOff = new Texture("configuracion/btnMute.png");
        TextureRegionDrawable trdOff = new TextureRegionDrawable(texturaOff);

        Texture texturaOn = new Texture("configuracion/btnSonido.png");
        TextureRegionDrawable trdOn = new TextureRegionDrawable(texturaOn);


        Button.ButtonStyle styleOn= new Button.ButtonStyle(trdOn,trdOff,trdOff);
        Button.ButtonStyle styleOff= new Button.ButtonStyle(trdOff,trdOn,trdOn);


        Button btnMute = this.crearBoton("configuracion/btnMute.png","configuracion/btnSonido.png");
        //Leer la preferencia de music on y si esta en false, cambiar el estilo del boto
        Preferences prefs = Gdx.app.getPreferences("MusicPreference");
        boolean musicOn = prefs.getBoolean("musicOn", true);
        if(musicOn){
            btnMute.setStyle(styleOn);
        }else{
            btnMute.setStyle(styleOff);
        }

        btnMute.setPosition(ANCHO/2, ALTO/2, 1);
        this.escenaMenu.addActor(btnMute);
        btnMute.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                //leer el estado actual de music on
                //si musicOn esta es true, cambiarlo a false y detener la musica
                Preferences prefs = Gdx.app.getPreferences("MusicPreference");
                boolean musicOn = prefs.getBoolean("musicOn", true);
                if(musicOn){
                    prefs.putBoolean("musicOn", false);
                    prefs.flush();
                    juego.detener(Juego.TipoMusica.MENU);

                }else {
                    prefs.putBoolean("musicOn", true);
                    prefs.flush();
                    juego.reproducir(Juego.TipoMusica.MENU);
                }

            }
        });
        Gdx.input.setInputProcessor(this.escenaMenu);
    }

    private Button crearBoton(String archivo,String archivoInverso) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtn = new TextureRegionDrawable(texturaBoton);
      //Boton Invero
        Texture texturaBotonInverso = new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso = new TextureRegionDrawable(texturaBotonInverso);
        return new Button(trdBtn,trdBtnInverso);
    }

    public void render(float delta) {
        this.borrarPantalla(1.0F, 0.0F, 1.0F);
        this.batch.setProjectionMatrix(this.camara.combined);
        this.batch.begin();
        this.batch.draw(this.texturaFondo, 0.0F, 0.0F);
        this.batch.end();
        this.escenaMenu.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK))
        {
            //regresar a la pantalla anterior  (cierta accion)
            juego.setScreen(new PantallaMenu(juego));
        }
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}