package com.dyan.magmaescape;
/*
// Esta clase desarrolla el nivel 3 del juego
Autor: Norma P Iturbide
*/
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

public class PantallaNivel3 extends Pantalla{
    //
    private Juego juego;
    //Fondo
    private Texture texturaFondo;
    //Escena
    private Stage escenaMenu;

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

    // Boton PAUSE
    private Texture texturaPause;


    //contador
    private float tiempo=0;
    private TextoBlanco texto; //escribe texto en la pantalla negro

    //Caja
    private Array<Caja> arrCajas;
    private Texture texturaCaja;
    private float timerCrearCaja;
    private final float TIEMPO_CREAR_CAJA = 22;


    //Potenciador
    private Array<Potenciador> arrPotenciadores;
    private Texture texturaPotenciadores;
    private float timerCrearPotenciador;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_POTENCIADOR = 25;

    //Escena pausa
    private EscenaPausa escenaPausa;
    private ProcesadorEntrada procesadorEntrada;


    // Estado de Olivia
    private EstadoOlivia estadoOlivia = EstadoOlivia.CAMINADO;

    //Estado del juego
    private EstadoJuego estadoJuego=EstadoJuego.JUGANDO;

    public PantallaNivel3(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

        crearFondo();
        crearPause();
        crearOlivia();
        crearAshes();
        crearTexto();
        crearCaja();
        crearPotenciador();
        //recuperarMarcador();

        procesadorEntrada= new ProcesadorEntrada();
        Gdx.input.setInputProcessor(procesadorEntrada);

    }

    private void crearPotenciador() {
        texturaPotenciadores = new Texture("nivel3/Diamante2resize.png");
        arrPotenciadores = new Array<>();
    }

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnMario = new TextureRegionDrawable(texturaBoton);
        Texture texturaInverso = new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso = new TextureRegionDrawable(texturaInverso);
        return new Button(trdBtnMario, trdBtnInverso);
    }

    private void crearCaja() {
        texturaCaja= new Texture("nivel3/fuego.png");
        arrCajas=new Array<>();
    }

    private void crearTexto() {
        texto= new TextoBlanco("font/arcade2.fnt");

    }

    private void recuperarMarcador() {
        Preferences prefs = Gdx.app.getPreferences("TIEMPO");
        tiempo = prefs.getInteger("segundos",0);
    }

    private void crearFondo(){

        texturaFondo = new Texture("nivel3/fondoNivel31.png");
    }

    private void crearAshes() {
        texturaAshe = new Texture("nivel3/AsheVerde.png");
        //ashe = new Ashe(texturaAshe, ANCHO-140, 105);
        arrAshes = new Array<>();
    }

    private void crearOlivia() {
        Texture texturaOlivia = new Texture("nivel1/oliviaSprites.png");
        olivia = new Olivia(texturaOlivia,ANCHO/4-(texturaOlivia.getWidth()/4f),ALTO/4f);
    }
    private void crearPause() {
        texturaPause = new Texture("nivel1/button_pausa.png");
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


        //Dibujar Ashes
        for (Ashe ashe : arrAshes) {
            ashe.render(batch);
        }

        //Dibujar Cajas de Fuego
        for (Caja caja : arrCajas) {
            caja.render(batch);
        }


        //Dibujar Potenciadores
        for (Potenciador potenciador: arrPotenciadores) {
            potenciador.render(batch);
        }

        if (estadoOlivia == EstadoOlivia.MURIENDO){
            texto.mostrarMensaje(batch, "Sorry,  perdiste :(", ANCHO/2, ALTO-(ALTO*.20F));
            texto.mostrarMensaje(batch, "Tap para VOLVER A INTENTAR", 3*ANCHO/4, ALTO/4);
            texto.mostrarMensaje(batch, "Tap para ir a MENU", ANCHO/4, ALTO/4);
        }

        //dibujar contador de tiempo
        texto.mostrarMensaje(batch,"Meta  30s",ANCHO*.45F,.9F*ALTO);
        texto.mostrarMensaje(batch,"Tiempo  "+Integer.toString((int) tiempo),ANCHO*.85F,.9F*ALTO);

        if(estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==30){
            texto.mostrarMensaje(batch, "¡Has ganado! Has pasado el primer nivel", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para continuar...", ANCHO/2, ALTO/4);
        }


        //Dibujar la pausa
        if(estadoJuego == EstadoJuego.PAUSADO && escenaPausa!= null)
        {
            escenaPausa.draw();
        }

        batch.end();

    }

    private void actualizar(float delta) {
        if(estadoJuego==EstadoJuego.JUGANDO && estadoOlivia!=EstadoOlivia.MURIENDO && (int)tiempo<30) {
            actualizarFondo();
            actualizarAshes(delta);
            actualizarCajas(delta);
            actualizarPotenciadores(delta);
            tiempo= tiempo+(60*Gdx.graphics.getDeltaTime())/60;
        }
    }

    private void actualizarPotenciadores(float delta) {
        // Crear Potenciadores
        timerCrearPotenciador += delta;
        if (timerCrearPotenciador>=TIEMPO_CREAR_POTENCIADOR) {
            timerCrearPotenciador = 0;
            //Crear Potenciador
            float xPotenciador = MathUtils.random(ANCHO, ANCHO*1.5f);
            Potenciador potenciador = new Potenciador(texturaPotenciadores, xPotenciador, ALTO/1.35f);
            arrPotenciadores.add(potenciador);
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            colisionPotenRapidez();
        }

        // Mover los Potenciadores
        for (Potenciador potenciador: arrPotenciadores) {
            potenciador.moverIzquierda(delta);
        }
    }

    private void actualizarCajas(float delta) {
        //Crear Cajas de Fuego
        timerCrearCaja += delta;
        if (timerCrearCaja >= TIEMPO_CREAR_CAJA) {
            timerCrearCaja = 0;
            //Crear obstaculo
            float xCaja = MathUtils.random(ANCHO, ANCHO*1.5F);
            Caja caja = new Caja(texturaCaja, xCaja, ALTO/4);
            arrCajas.add(caja);
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            colisionCaja();
        }

        //Mover los obstaculos
        for (Caja caja : arrCajas) {
            caja.moverIzquierda(delta);
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

        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            probarColisionAshe();
        }

        // Mover los Ashes
        for (Ashe ashe: arrAshes) {
            ashe.moverIzquierda(delta);
        }
    }

    private void probarColisionAshe() {
        for (Ashe ashe : arrAshes) {
            //Gdx.app.log("Probando colision", "tengo miedo");
            if (olivia.sprite.getBoundingRectangle().overlaps(ashe.sprite.getBoundingRectangle())) {
                // Le pego
                olivia.setEstado(EstadoOlivia.MURIENDO);
                estadoOlivia = EstadoOlivia.MURIENDO;
                //olivia = null;
                Gdx.app.log("Probando colision con ashe", "YA LE PEGAMOS");
                break;
            }
        }
    }

    private void colisionCaja() {
        for (Caja caja : arrCajas) {
            //Gdx.app.log("Probando colision", "tengo miedo");
            if (olivia.sprite.getBoundingRectangle().overlaps(caja.sprite.getBoundingRectangle())){
                // Le pego
                olivia.setEstado(EstadoOlivia.MURIENDO);
                estadoOlivia = EstadoOlivia.MURIENDO;
                //olivia = null;
                Gdx.app.log("Probando colision de caja de fuego", "Ya se quemo");
                break;
            }
        }
    }

    private void colisionPotenRapidez() {
        for(Potenciador potenciador : arrPotenciadores){
            if (olivia.sprite.getBoundingRectangle().overlaps(potenciador.sprite.getBoundingRectangle())){

                Gdx.app.log("Probando colision con el potenciador", "TOCO DIAMANTE,CORRIENDO");
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

    private class ProcesadorEntrada implements InputProcessor {

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
            float xPause = texturaPause.getWidth()/2;
            float yPause = ALTO - 1.55f*altoPause;
            // Primero, verificar el boton de back

            Rectangle rectPuse = new Rectangle(xPause, yPause, anchoPause, altoPause);
            if(rectPuse.contains(v.x, v.y)) {
                // SALIY y guardar el marcador
                if (escenaPausa == null) escenaPausa = new EscenaPausa(vista);

                estadoJuego=EstadoJuego.PAUSADO;
                //Cambiar el procesador de entrada, ahra quien sera el procesador de entrada sera la escena de pausa
                Gdx.input.setInputProcessor(escenaPausa); //Detecta el click sobre el boton

                Preferences preferencias = Gdx.app.getPreferences("TIEMPO");
                preferencias.putInteger("segundos", (int) tiempo);
                preferencias.flush();


            }else


            if (estadoJuego==EstadoJuego.JUGANDO && estadoOlivia != EstadoOlivia.MURIENDO ){
                olivia.saltar(); // Top-Down
            }

            if (estadoOlivia == EstadoOlivia.MURIENDO){
                if (v.x >= ANCHO/2){
                    juego.setScreen(new PantallaNivel3(juego));
                }
                else
                    juego.setScreen(new PantallaMenu(juego));
            }

            if (estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==30 ){
                juego.setScreen(new PantallaNivel3Completo(juego));
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
            Button btnVolverJuego = crearBoton("menuPausa/btnVolverJuego.png", "menuPausa/btnVolverJuegoInverso.png");
            //agregar boton a la escena
            addActor(btnVolverJuego);
            btnVolverJuego.setPosition(ANCHO/2,.7F*ALTO,Align.center);

            btnVolverJuego.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event,x,y);
                    estadoJuego=EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(procesadorEntrada);
                }
            });

            Button btnVolverIntentar = crearBoton("menuPausa/btnVolverIntentar.png", "menuPausa/btnVolverIntentarInverso.png");
            //agregar boton a la escena
            addActor(btnVolverIntentar);
            btnVolverIntentar.setPosition(ANCHO/2,.5F*ALTO, Align.center);

            btnVolverIntentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    juego.setScreen(new PantallaNivel3(juego));
                }
            });

            Button btnMenuPrincipal = crearBoton("menuPausa/btnMenuPrincipal.png", "menuPausa/btnMenuPrincipalInverso.png");

            //agregar boton a la escena
            addActor(btnMenuPrincipal);
            btnMenuPrincipal.setPosition(ANCHO/2,.3F*ALTO, Align.center);

            btnMenuPrincipal.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    juego.setScreen(new PantallaMenu(juego));
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