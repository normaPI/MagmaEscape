package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaNivel1 extends Pantalla {

    //
    private Juego juego;
    //Fondo
    private Texture texturaFondo;
    //Escena
    private Stage escenaMenu;


    public PantallaNivel1(Juego juego) {
        this.juego = juego;
    }
    @Override
    public void show() {

        crearNivel1();

    }

    private void crearNivel1() {
        escenaMenu=new Stage(vista);
        texturaFondo=new Texture("menuNiveles/nivel1/fondoNivel1jpg");


        Button btnMenu=crearBoton("menuNiveles/nivel1/button_menu.png");
        btnMenu.setPosition(10,ALTO, Align.center);
        escenaMenu.addActor(btnMenu);
        //Registrar el evento de click para el boton
        btnMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Cambiar pantalla
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        //ESCENA SE ENCARGA DE ATENDER LOS EVENTOS DE ENTRADA
        Gdx.input.setInputProcessor(escenaMenu);

    }

    private Button crearBoton(String archivo) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtn=new TextureRegionDrawable(texturaBoton);

        return new Button(trdBtn);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(1,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);

        batch.end();

        //Escena despues del FONDO
        escenaMenu.draw();

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