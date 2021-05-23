/*
Esta clase representa a la pantalla Historia
Autor: Yised Denisse Apolonio
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

public class PantallaHistoria extends Pantalla
{
    //Referencia al juego principal
    private Juego juego;
    //Fondo
    private Texture texturaFondo;
    //Escena
    private Stage escenaMenu;

    public PantallaHistoria(Juego juego) {
            this.juego = juego;
    }

    @Override
    public void show() {
        crearHistoria();
        //1: bloquear la tecla de back
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void crearHistoria() {
        escenaMenu=new Stage(vista);

        texturaFondo=new Texture("acercaDe/historiaInicio.png");
        //Boton regresar
        Button btnRegresar=crearBoton("acercaDe/button_regresar.png", "acercaDe/button_regresarInverso.png");
        btnRegresar.setPosition(ANCHO/6,ALTO/6, Align.center);
        escenaMenu.addActor(btnRegresar);
        //Registrar el evento de click para el boton
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Cambiar pantalla
                juego.setScreen(new PantallaAcerca(juego));
            }
        });

        //BOTON HISTORIA FINAL
        Button btnHistoria=crearBoton("nivel1/button_continuar.png", "nivel1/button_continuarInv.png");
        btnHistoria.setPosition(2.5f*ANCHO/3,ALTO/6, Align.center);
        escenaMenu.addActor(btnHistoria);
        //Registrar el evento de click para el boton
        btnHistoria.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaHistoriaFinal(juego));
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

    @Override
    public void render(float delta) {
        borrarPantalla(1,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.end();

        // Escena (DESPUES DEL FONDO)
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
