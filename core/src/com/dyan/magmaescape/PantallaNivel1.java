/*
Esta clase representa el primer nivel
Autor: Carlos Daniel Castañeda
*/

package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaNivel1 extends Pantalla {

    //
    private Juego juego;
    //Fondo
    private Texture texturaFondo;

    //sprite de Olivia
    private Sprite oliviaSprite;

    private Olivia olivia;
    private float xFondo=0;

    //Enemigo (Ashes)
    //private Ashe ashe;
    private Array<Ashe> arrAshes;
    private Texture texturaAshe;
    private float timerCreaAshe;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_ASHE = 4;

    //Animacion (Peces)
    private Array<Pez> arrPeces;
    private Texture texturaPeces;
    private float timerCrearPez;
    private final float TIEMPO_CREAR_PEZ = 6;

    //Escena pausa
    private EscenaPausa escenaPausa;
    private ProcesadorEntrada procesadorEntrada;

    // Boton PAUSE
    private Texture texturaPause;


    //contador
    private float tiempo=0;
    private Texto texto; //escribe texto en la pantalla


    // Estado de Olivia
    private EstadoOlivia estadoOlivia = EstadoOlivia.CAMINADO;

    //Estado del juego
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;



    public PantallaNivel1(Juego juego) {
        this.juego = juego;
        //Leer la preferencia musicOn y si esta prendida llamar a juego reproducir
        Preferences prefs = Gdx.app.getPreferences("MusicPreference");
        boolean musicOn = prefs.getBoolean("musicOn", true);
        if(musicOn){
            juego.reproducir(Juego.TipoMusica.NIVELES);
        }
    }

    @Override
    public void show() {

        crearFondo();
        crearPause();
        crearOlivia();
        crearAshes();
        crearPeces();
        crearTexto();
        //recuperarMarcador();

        procesadorEntrada = new ProcesadorEntrada();
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

    }

    private void crearPeces() {
        texturaPeces = new Texture("nivel1/peces.png");
        arrPeces = new Array<>();
    }

    private void crearTexto() {
        texto = new Texto("font/arcade2.fnt");
    }

    private void recuperarMarcador() {
        Preferences prefs = Gdx.app.getPreferences("TIEMPO");
        tiempo = prefs.getInteger("segundos",0);
    }

    private void crearFondo(){

        texturaFondo = new Texture("nivel1/fondNivel1.jpg");
    }

    private void crearAshes() {
        texturaAshe = new Texture("nivel1/AsheRojo.png");
        //ashe = new Ashe(texturaAshe, ANCHO-140, 105);
        arrAshes = new Array<>();
    }

    private void crearOlivia() {
        Texture texturaOlivia = new Texture("nivel1/oliviaSprites.png");
        olivia = new Olivia(texturaOlivia,ANCHO/4-(texturaOlivia.getWidth()/4f),ALTO/4f, 250, 150);
    }
    private void crearPause() {

        texturaPause = new Texture("nivel1/button_pausa.png");
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

        // actualizar
        actualizar(delta);

        borrarPantalla(1,0,1);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        //dibujando fondo
        batch.draw(texturaFondo,xFondo,0);
        batch.draw(texturaFondo, xFondo + texturaFondo.getWidth(), 0);
        batch.draw(texturaPause, .03f*ANCHO, .85F*ALTO);

       olivia.render(batch);

        if (estadoOlivia == EstadoOlivia.PAUSA){
            texto.mostrarMensaje(batch, "PAUSA", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para continuar...", 3*ANCHO/4, ALTO/4);
            texto.mostrarMensaje(batch, "Tap para ir a menu", ANCHO/4, ALTO/4);
        }

        //Dibujar Ashes
        for (Ashe ashe : arrAshes) {
            ashe.render(batch);
        }
        //Dibujar Peces
        for (Pez pez : arrPeces) {
            pez.render(batch);
        }

        if (estadoOlivia == EstadoOlivia.MURIENDO){
            texto.mostrarMensaje(batch, "PERDISTE:(", ANCHO/2, ALTO-(ALTO*.20F));
            texto.mostrarMensaje(batch, "Tap para volver a intentar", 3*ANCHO/4, ALTO/4);
            texto.mostrarMensaje(batch, "Tap para ir a menu", ANCHO/4, ALTO/4);
        }

        //dibujar contador de tiempo
        texto.mostrarMensaje(batch,"Meta  30s",ANCHO*.45F,.9F*ALTO);
        texto.mostrarMensaje(batch,"Tiempo  "+Integer.toString((int) tiempo),ANCHO*.85F,.9F*ALTO);

        if(estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==30){
            texto.mostrarMensaje(batch, "¡Has ganado! Has pasado el primer nivel", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para continuar...", ANCHO/2, ALTO/4);
            Preferences preferencias = Gdx.app.getPreferences("NIVEL2");
            preferencias.putInteger("edoNivel2", 1);
            preferencias.flush();
        }
        //Dibujar la pausa
        if(estadoJuego == EstadoJuego.PAUSADO && escenaPausa!= null)
        {
            escenaPausa.draw();
        }



        batch.end();

    }

    private void actualizar(float delta) {
        if(estadoJuego== EstadoJuego.JUGANDO  && estadoOlivia != EstadoOlivia.MURIENDO && (estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo<30) ){
            actualizarFondo();
            actualizarAshes(delta);
            actualizarPeces(delta);
            tiempo= tiempo+(60*Gdx.graphics.getDeltaTime())/60;
        }
    }

    private void actualizarPeces(float delta) {
        //Crear Cajas de Fuego
        timerCrearPez += delta;
        if (timerCrearPez >= TIEMPO_CREAR_PEZ) {
            timerCrearPez = 0;
            //Crear elemento
            float xPez = MathUtils.random(ANCHO, ANCHO*1.5F);
            Pez pez = new Pez(texturaPeces, xPez, ALTO/15);
            arrPeces.add(pez);
        }
        //Mover los elementos (peces)
        //for (Caja caja : arrCajas){

        for (int i=arrPeces.size-1; i>=0; i--){
            Pez pez =arrPeces.get(i);
            pez.moverIzquierda(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (pez.sprite.getX() < -60) {
                arrPeces.removeIndex(i);
            }
        }
    }
    private void actualizarAshes(float delta) {
        // Crear Ashes
        timerCreaAshe += delta;
        if (timerCreaAshe>=TIEMPO_CREAR_ASHE) {
            timerCreaAshe = 0;
            //Crear Enemigo
            float xAshe = MathUtils.random(ANCHO, ANCHO*1.5f);
            Ashe ashe = new Ashe(texturaAshe, xAshe, ALTO/4f, -300);
            arrAshes.add(ashe);
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego== EstadoJuego.JUGANDO){
            probarColisiones();
        }

        // Mover los Ashes
        //for (Ashe ashe: arrAshes)
        for (int i=arrAshes.size-1; i>=0; i--){
            Ashe ashe = arrAshes.get(i);
            ashe.moverIzquierda(delta);
            //Prueba si los ashes deben desaparecer, porque salieron de la pantalla
            if (ashe.sprite.getX() < -60) {
                //Borrar el objeto
                arrAshes.removeIndex(i);
            }
        }
    }

    // Prueba la colision de olivia vs ashes
    private void probarColisiones() {
        for (Ashe ashe: arrAshes) {
            //Gdx.app.log("Probando colision", "tengo miedo");
            if (olivia.sprite.getBoundingRectangle().overlaps(ashe.sprite.getBoundingRectangle())){
                // Le pego
                olivia.setEstado(EstadoOlivia.MURIENDO);
                estadoOlivia = EstadoOlivia.MURIENDO;
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

    public class ProcesadorEntrada implements InputProcessor{

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

            float anchoPause = texturaPause.getWidth();
            float altoPause = texturaPause.getHeight();
            float xPause = .03F*ANCHO;
            float yPause = .85F*ALTO;
            // Primero, verificar el boton de back

            Rectangle rectPuse = new Rectangle(xPause, yPause, anchoPause, altoPause);
            if(rectPuse.contains(v.x, v.y)){
                // SALIY y guardar el marcador
                if (escenaPausa == null) escenaPausa = new EscenaPausa(vista);

                estadoJuego= EstadoJuego.PAUSADO;
                //Cambiar el procesador de entrada, ahra quien sera el procesador de entrada sera la escena de pausa
                Gdx.input.setInputProcessor(escenaPausa); //Detecta el click sobre el boton

                Preferences preferencias = Gdx.app.getPreferences("TIEMPO");
                preferencias.putInteger("segundos", (int) tiempo);
                preferencias.flush();

                //juego.setScreen(new PantallaMenuPausa(juego));

            }else

            if (estadoOlivia == EstadoOlivia.PAUSA ){

                if (v.x >= ANCHO/2){
                    estadoOlivia = EstadoOlivia.CAMINADO;
                }
                else
                    juego.setScreen(new PantallaMenu(juego));

            }

            if (estadoJuego==EstadoJuego.JUGANDO && estadoOlivia != EstadoOlivia.MURIENDO){
                olivia.saltar(); // Top-Down
            }

            if (estadoOlivia == EstadoOlivia.MURIENDO){
               // juego.reproducir(Juego.TipoMusica.MUERTE);
                if (v.x >= ANCHO/2){
                    juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL1));
                }
                else
                {
                    juego.reproducir(Juego.TipoMusica.MENU);
                    juego.setScreen(new PantallaCargando(juego,Pantallas.MENU));
                }
            }

            if (estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==30 ){
                juego.setScreen(new PantallaNivel1Completo(juego));
            }

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

    //Creacion de la escena pausa al apretar el boton
    private class EscenaPausa extends Stage
    {
        private Texture texturaFondo;

        public EscenaPausa(Viewport vista)
        {
            super(vista);
            //añadir la textura de pausa
            texturaFondo= new Texture("pausa/fondoPausa.png");
            Image imgFondo= new Image(texturaFondo);
            imgFondo.setPosition(ANCHO/2,ALTO/2, Align.center);
            addActor(imgFondo);

            //adicion de botones
            //actores
            Button btnVolverJuego = crearBoton("menuPausa/button_volver-al-juego.png", "menuPausa/button_volver-al-juego-2.png");
            //agregar boton a la escena
            addActor(btnVolverJuego);
            btnVolverJuego.setPosition(ANCHO/2,.7F*ALTO,Align.center);

            btnVolverJuego.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event,x,y);
                    estadoJuego= PantallaNivel1.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(procesadorEntrada);
                }
            });

            Button btnVolverIntentar = crearBoton("menuPausa/button_volver-a-intentar.png", "menuPausa/button_volver-a-intentar-2.png");
            //agregar boton a la escena
            addActor(btnVolverIntentar);
            btnVolverIntentar.setPosition(ANCHO/2,.5F*ALTO, Align.center);

            btnVolverIntentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL1));
                }
            });

            Button btnMenuPrincipal = crearBoton("menuPausa/button_menu-principal.png", "menuPausa/button_menu-principal-2.png");

            //agregar boton a la escena
            addActor(btnMenuPrincipal);
            btnMenuPrincipal.setPosition(ANCHO/2,.3F*ALTO, Align.center);

            btnMenuPrincipal.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    juego.reproducir(Juego.TipoMusica.MENU);
                    juego.setScreen(new PantallaCargando(juego,Pantallas.MENU));
                }
            });

        }
    }
    private enum EstadoJuego
    {
        JUGANDO,
        PAUSADO
    }

}