package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaConfiguracion extends Pantalla {
    private Juego juego;
    private Texture texturaFondo;
    private Stage escenaMenu;

    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
    }

    public void show() {
        this.crearConfig();
    }

    private void crearConfig() {
        this.escenaMenu = new Stage(this.vista);
        this.texturaFondo = new Texture("configuracion/fondoConfiguracion.jpg");
        Button btnRegresar = this.crearBoton("configuracion/button_regresar.png","configuracion/button_regresarInverso.png");
        btnRegresar.setPosition(256.0F, 144.0F, 1);
        this.escenaMenu.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                PantallaConfiguracion.this.juego.setScreen(new PantallaMenu(PantallaConfiguracion.this.juego));
            }
        });


        Button btnMute = this.crearBoton("configuracion/btnMute.png","configuracion/btnSonido.png");
        btnMute.setPosition(640.0F, 400.0F, 1);
        this.escenaMenu.addActor(btnMute);
        btnMute.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
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
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}