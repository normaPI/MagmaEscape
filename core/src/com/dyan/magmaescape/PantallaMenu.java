package com.dyan.magmaescape;
/*
Esta clase representa a la pantalla Menu Principal
Autor: Norma P Iturbide
*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaMenu extends Pantalla {
    private Juego juego;
    private Texture texturaFondo;
    private Stage escenaMenu;

    public PantallaMenu(Juego juego) {

        this.juego = juego;

    }

    public void show() {
        this.crearMenu();
    }

    private void crearMenu() {
        this.texturaFondo = new Texture("menuPrincipal/fondo.png");
        this.escenaMenu = new Stage(this.vista);

        Button btnJugar = this.crearBoton("menuPrincipal/button_jugar.png", "menuPrincipal/button_jugar-2.png");
        btnJugar.setPosition(3*ANCHO/4, 3*ALTO/6, 1);
        this.escenaMenu.addActor(btnJugar);
        btnJugar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                PantallaMenu.this.juego.setScreen(new PantallaSeleccionarNivel(PantallaMenu.this.juego));
            }
        });
        Button btnAcerca = this.crearBoton("menuPrincipal/button_acerca-de.png", "menuPrincipal/button_acerca-de-2.png");
        btnAcerca.setPosition(ANCHO/4, 4*ALTO/6, 1);
        this.escenaMenu.addActor(btnAcerca);
        btnAcerca.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                PantallaMenu.this.juego.setScreen(new PantallaAcerca(PantallaMenu.this.juego));
            }
        });
        Button btnAyuda = this.crearBoton("menuPrincipal/button_ayuda.png", "menuPrincipal/button_ayuda-2.png");
        btnAyuda.setPosition(ANCHO/4, 3*ALTO/6, 1);
        this.escenaMenu.addActor(btnAyuda);
        btnAyuda.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                PantallaMenu.this.juego.setScreen(new PantallaAyuda(PantallaMenu.this.juego));
            }
        });
        Button btnConfiguracion = this.crearBoton("menuPrincipal/button_configuracion.png", "menuPrincipal/button_configuracion-2.png");
        btnConfiguracion.setPosition(ANCHO/4, 2*ALTO/6, 1);
        this.escenaMenu.addActor(btnConfiguracion);
        btnConfiguracion.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                PantallaMenu.this.juego.setScreen(new PantallaConfiguracion(PantallaMenu.this.juego));
            }
        });


        Gdx.input.setInputProcessor(this.escenaMenu);
    }

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnMario = new TextureRegionDrawable(texturaBoton);
        Texture texturaInverso = new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso = new TextureRegionDrawable(texturaInverso);
        return new Button(trdBtnMario, trdBtnInverso);
    }

    public void render(float delta) {
        this.borrarPantalla(0.0F, 1.0F, 1.0F);
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

