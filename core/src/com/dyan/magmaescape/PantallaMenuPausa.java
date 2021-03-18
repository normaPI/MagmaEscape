package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaMenuPausa extends Pantalla {
    private Juego juego;
    //Fondo
    private Texture texturaFondo;
    //Escena
    private Stage escenaMenuPausa;

    public PantallaMenuPausa(Juego juego) {this.juego=juego;}

    @Override
    public void show() {
        crearMenuPausa();
    }

    private void crearMenuPausa() {
        texturaFondo= new Texture("menuPausa/MenuPausa.png");

        escenaMenuPausa= new Stage(vista);

        //actores
        Button btnVolverJuego = crearBoton("menuPausa/btnVolverJuego.png", "menuPausa/btnVolverJuegoInverso.png");
        btnVolverJuego.setPosition(ANCHO/2,4*ALTO/6, Align.center);
        //agregar boton a la escena
        escenaMenuPausa.addActor(btnVolverJuego);
        btnVolverJuego.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaNivel1(juego));
            }
        });

        Button btnVolverIntentar = crearBoton("menuPausa/btnVolverIntentar.png", "menuPausa/btnVolverIntentarInverso.png");
        btnVolverIntentar.setPosition(ANCHO/2,3*ALTO/6, Align.center);
        //agregar boton a la escena
        escenaMenuPausa.addActor(btnVolverIntentar);
        btnVolverIntentar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaNivel1(juego));
            }
        });

        Button btnMenuPrincipal = crearBoton("menuPausa/btnMenuPrincipal.png", "menuPausa/btnMenuPrincipalInverso.png");
        btnMenuPrincipal.setPosition(ANCHO/2,2*ALTO/6, Align.center);
        //agregar boton a la escena
        escenaMenuPausa.addActor(btnMenuPrincipal);
        btnMenuPrincipal.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaMenuPausa);


    }

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton= new Texture(archivo);
        TextureRegionDrawable trdBtnMario =new TextureRegionDrawable(texturaBoton);

        //Inverso
        Texture texturaInverso= new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso= new TextureRegionDrawable(texturaInverso);
        return new Button(trdBtnMario,trdBtnInverso);
    }


    @Override
    public void render(float delta) {
        borrarPantalla(.5f,0,0);

        batch.setProjectionMatrix(this.camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.end();
        escenaMenuPausa.draw();
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
