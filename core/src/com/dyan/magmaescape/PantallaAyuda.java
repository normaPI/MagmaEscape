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

public class PantallaAyuda extends Pantalla {
    private Juego juego;
    //Fondo
    private Texture texturaFondo;

    //Escena
    private Stage escenaAyuda;


    public PantallaAyuda(Juego juego) {
        this.juego = juego;
    }

    public void show() {
        texturaFondo= new Texture("acercaDe/fondoAyuda.png");

        escenaAyuda= new Stage(vista);

        //actores
        Button btnRegresar = crearBoton("acercaDe/button_regresar.png", "acercaDe/button_regresarInverso.png");
        btnRegresar.setPosition(ANCHO/5,ALTO/6, Align.center);
        //agregar boton a la escena
        escenaAyuda.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaAyuda);

    }

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton= new Texture(archivo);
        TextureRegionDrawable trdBtnMario =new TextureRegionDrawable(texturaBoton);

        //Inverso
        Texture texturaInverso= new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso= new TextureRegionDrawable(texturaInverso);
        return new Button(trdBtnMario,trdBtnInverso);
    }

    public void render(float delta) {
        borrarPantalla(.5f,0,0);

        batch.setProjectionMatrix(this.camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.end();
        escenaAyuda.draw();
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}
