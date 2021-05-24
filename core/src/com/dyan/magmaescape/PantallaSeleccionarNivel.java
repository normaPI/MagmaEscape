/*
Esta clase representa la pantalla para la seleccion de nivel
Autor: Amauri Elian Perez
*/
package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaSeleccionarNivel extends Pantalla {
    private Juego juego;

    //Fondo
    private Texture texturaFondo;

    //Escena
    private Stage escenaMenuSeleccionNivel;



    public PantallaSeleccionarNivel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenuNiveles();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void crearMenuNiveles() {
        texturaFondo= new Texture("menuNiveles/fondoSeleccionNivel.png");

        escenaMenuSeleccionNivel= new Stage(vista);

        //actores botones...
        Button btnNivel1 = crearBoton("menuNiveles/button_nivel1.png", "menuNiveles/button_nivel1-2.png");
        btnNivel1.setPosition(ANCHO*.20F,3*ALTO/6, Align.center);
        //agregar boton a la escena
        escenaMenuSeleccionNivel.addActor(btnNivel1);
        btnNivel1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaNivel1(juego));
            }
        });

        Button btnNivel2 = crearBoton("menuNiveles/button_nivel2BLOCK.png", "menuNiveles/button_nivel2-2.png");
        btnNivel2.setPosition(ANCHO*.5F,3*ALTO/6,Align.center);
        escenaMenuSeleccionNivel.addActor(btnNivel2);
        btnNivel2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaNivel2(juego));
            }
        });

        Button btnNivel3 = crearBoton("menuNiveles/button_nivel3BLOCK.png", "menuNiveles/button_nivel3-2.png");
        btnNivel3.setPosition(ANCHO*.80F,3*ALTO/6,Align.center);
        escenaMenuSeleccionNivel.addActor(btnNivel3);
        btnNivel3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaNivel3(juego));
            }
        } );

        Button btnRegresar = crearBoton("configuracion/button_regresar.png", "configuracion/button_regresar.png");
        btnRegresar.setPosition(ANCHO/6,ALTO/6, Align.center);
        escenaMenuSeleccionNivel.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });



        Gdx.input.setInputProcessor(escenaMenuSeleccionNivel);

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
        borrarPantalla(1,1,1);
        batch.setProjectionMatrix(camara.combined);

        //inicia dibujo
        batch.begin();

        batch.draw(texturaFondo,0,0);

        batch.end();

        escenaMenuSeleccionNivel.draw();
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
