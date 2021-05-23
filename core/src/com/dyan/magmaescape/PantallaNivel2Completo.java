package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaNivel2Completo extends Pantalla{

    private Juego juego;

    private Texture texturaFondo;
    //Escena
    private Stage escenaNivelCompleto2;

    public PantallaNivel2Completo(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        this.escenaNivelCompleto2 = new Stage(this.vista);
        texturaFondo=new Texture("nivel2/finNivel2.jpg");

        Button btnVolverMenu = crearBoton("nivel1/button_continuar.png", "nivel1/button_continuarInv.png");
        btnVolverMenu.setPosition(ANCHO*.85f,ALTO-(ALTO*.90F), Align.center);
        //agregar boton a la escena
        escenaNivelCompleto2.addActor(btnVolverMenu);
        btnVolverMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaSeleccionarNivel(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaNivelCompleto2);
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

        escenaNivelCompleto2.draw();
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
