/*
Esta clase representa a la pantalla Acerca De
Autor: Yised Denise Apolonio
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

public class PantallaAcerca extends Pantalla
{
    //Referencia al juego principal
    private Juego juego;
    //Fondo
    private Texture texturaFondo;
    //Escena
    private Stage escenaMenu;

    public PantallaAcerca(Juego juego) {
        this.juego = juego;
    }

    public void show() {
        crearAcercaDe();
        //1: bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void crearAcercaDe() {
        escenaMenu=new Stage(vista);

        texturaFondo=new Texture("acercaDe/fondoAcercaD.png");

        //Boton regresar
        Button btnRegresar=crearBoton("configuracion/button_regresar.png", "configuracion/button_regresar-2.png");
        btnRegresar.setPosition(ANCHO/6,ALTO/6, Align.center);
        escenaMenu.addActor(btnRegresar);
        //Registrar el evento de click para el boton
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Cambiar pantalla
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        //BOTON HISTORIA
        Button btnHistoria=crearBoton("acercaDe/button_historia.png", "acercaDe/button_historia-2.png");
        btnHistoria.setPosition(2.5f*ANCHO/3,ALTO/6, Align.center);
        escenaMenu.addActor(btnHistoria);
        //Registrar el evento de click para el boton
        btnHistoria.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                juego.setScreen(new PantallaHistoria(juego));
            }
        });
        //ESCENA SE ENCARGA DE ATENDER LOS EVENTOS DE ENTRADA
        Gdx.input.setInputProcessor(escenaMenu);
    }

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtn=new TextureRegionDrawable(texturaBoton);
        // inverso
        Texture texturaInverso = new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso = new TextureRegionDrawable(texturaInverso);
        return new Button(trdBtn, trdBtnInverso);
    }

    public void render(float delta) {
        borrarPantalla(1,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.end();

        // Escena (DESPUES DEL FONDO)
        escenaMenu.draw();

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
