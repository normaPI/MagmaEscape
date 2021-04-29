/*
Esta clase representa el primer nivel
Autor: Carlos Daniel Castañeda
*/

package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class PantallaNivel1 extends Pantalla {

    //
    private Juego juego;
    //Fondo
    private Texture texturaFondo;
    //Escena
    private Stage escenaMenu;

    //sprite de Olivia
    private Sprite oliviaSprite;

    private Olivia olivia;
    private Objeto objeto1;
    private float xFondo=0;

    //Enemigo (Ashes)
    //private Ashe ashe;
    private Array<Ashe> arrAshes;
    private Texture texturaAshe;
    private float timerCreaAshe;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_ASHE = 3;




    //contador
    private float tiempo=0;
    private Texto texto; //escribe texto en la pantalla

    public PantallaNivel1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

        //crearNivel1();
        crearFondo();
        crearOlivia();
        crearAshes();
        crearTexto();

        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void crearTexto() {
        texto= new Texto("font/arcade.fnt");

    }

    private void crearFondo(){

        texturaFondo = new Texture("nivel1/fondNivel1.jpg");
    }

    private void crearAshes() {
        texturaAshe = new Texture("nivel1/Ashe.png");
        //ashe = new Ashe(texturaAshe, ANCHO-140, 105);
        arrAshes = new Array<>();
    }

    private void crearOlivia() {
        Texture texturaOlivia = new Texture("nivel1/oliviaSprites.png");
        olivia = new Olivia(texturaOlivia,ANCHO/2-(texturaOlivia.getWidth()/4f),ALTO/4f);
    }

    /*private void crearNivel1() {
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
                juego.setScreen(new PantallaMenuPausa(juego));
            }
        });


        //ESCENA SE ENCARGA DE ATENDER LOS EVENTOS DE ENTRADA
        Gdx.input.setInputProcessor(escenaMenu);

    }*/

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
        // actualizar
        actualizar(delta);

        borrarPantalla(1,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //dibujando fondo
        batch.draw(texturaFondo,xFondo,0);
        batch.draw(texturaFondo, xFondo + texturaFondo.getWidth(), 0);

       olivia.render(batch);

        //Dibujar Ashes
        //ashe.render(batch);
        for (Ashe ashe : arrAshes) {
            ashe.render(batch);
        }

        //dibujar contador de tiempo
        texto.mostrarMensaje(batch,Integer.toString((int) tiempo),ANCHO*.95F,.9F*ALTO);
        batch.end();

        //Escena despues del FONDO
        //escenaMenu.draw();

    }

    private void actualizar(float delta) {

        actualizarFondo();
        actualizarAshes(delta);
        if(olivia.getEstado()!=EstadoOlivia.MURIENDO)
        {
            tiempo= tiempo+(60*Gdx.graphics.getDeltaTime())/60;
        }
    }

    private void actualizarAshes(float delta) {
        // Crear Ashes
        timerCreaAshe += delta;
        if (timerCreaAshe>=TIEMPO_CREAR_ASHE) {
            timerCreaAshe = 0;
            //Crear Enemigo
            float xAshe = MathUtils.random(ANCHO, ANCHO*1.5f);
            Ashe ashe = new Ashe(texturaAshe, xAshe, ALTO/4f);
            arrAshes.add(ashe);
        }

        if(olivia!=null){
            probarColisiones();
        }

        // Mover los Ashes
        for (Ashe ashe: arrAshes) {
            ashe.moverIzquierda(delta);
        }
    }

    // Prueba la colision de olivia vs ashes
    private void probarColisiones() {
        for (Ashe ashe: arrAshes) {
            //Gdx.app.log("Probando colision", "tengo miedo");
            if (olivia.sprite.getBoundingRectangle().overlaps(ashe.sprite.getBoundingRectangle())){
                // Le pego
                olivia.setEstado(EstadoOlivia.MURIENDO);
                //olivia = null;
                Gdx.app.log("Probando colision", "YA LE PEGAMOS");
                break;
            }





        }
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

    private class ProcesadorEntrada implements InputProcessor{

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);

            /*if (v.x >= ANCHO/2){
                // Dispara!!
                Bola bola = new Bola(texturaBola, mario.getSprite().getX(),  mario.getSprite().getY());
                arrBolas.add(bola);

            }
            else {*/
                olivia.saltar(); // Top-Down


            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }
}