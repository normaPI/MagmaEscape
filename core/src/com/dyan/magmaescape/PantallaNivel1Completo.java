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

public class PantallaNivel1Completo extends Pantalla {

    private Juego juego;

    private Texture texturaFondo;
    //Escena
    private Stage escenaNivelCompleto1;


    public PantallaNivel1Completo(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        this.escenaNivelCompleto1 = new Stage(this.vista);
        texturaFondo=new Texture("nivel1/mapaNivel1.jpg");

        Button btnVolverMenu = crearBoton("menuPausa/btnMenuPrincipal.png", "menuPausa/btnMenuPrincipalInverso.png");
        btnVolverMenu.setPosition(ANCHO*.85f,ALTO-(ALTO*.90F), Align.center);
        //agregar boton a la escena
        escenaNivelCompleto1.addActor(btnVolverMenu);
        btnVolverMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaNivelCompleto1);
    }

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnMario = new TextureRegionDrawable(texturaBoton);
        Texture texturaInverso = new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso = new TextureRegionDrawable(texturaInverso);
        return new Button(trdBtnMario, trdBtnInverso);
    }

    @Override
    public void render(float delta) {

        borrarPantalla(1,0,1);
        batch.setProjectionMatrix(this.camara.combined);
        batch.begin();
        batch.draw(texturaFondo,0,0);

        batch.end();

        escenaNivelCompleto1.draw();
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
