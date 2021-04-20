package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    //sprite de Olivia
    private Sprite oliviaSprite;

    private Olivia olivia1;
    private Objeto objeto1;
    private float xFondo=0;


    public PantallaNivel1(Juego juego) {
        this.juego = juego;
    }
    @Override
    public void show() {

        crearNivel1();
        crearOlivia();

    }

    private void crearOlivia() {
        Texture texturaOlivia=new Texture("nivel1/Olivia.png");
        olivia1=new Olivia(texturaOlivia,10,ALTO/4.5f);
    }

    private void crearNivel1() {
        escenaMenu=new Stage(vista);
        texturaFondo=new Texture("nivel1/fondNivel1.jpg");


        Button btnMenu=crearBoton("nivel1/button_menu.png","nivel1/button_menuInverso.png");
        btnMenu.setPosition(10,645);
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

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton=new Texture(archivo);
        TextureRegionDrawable trdBtn=new TextureRegionDrawable(texturaBoton);

        //Inverso
        Texture texturaBotonInverso=new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso=new TextureRegionDrawable(texturaBotonInverso);

        return new Button(trdBtn,trdBtnInverso);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(1,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);

        batch.draw(texturaFondo,xFondo,0);
        batch.draw(texturaFondo, xFondo + texturaFondo.getWidth(), 0);
        actualizar();

       olivia1.render(batch);
        batch.end();

        //Escena despues del FONDO
        escenaMenu.draw();

    }

    private void actualizar() {
        actualizarFondo();
    }

    private void actualizarFondo() {
        xFondo-=3;
        if(xFondo<=-texturaFondo.getWidth()) {
            xFondo=0;
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