package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class PantallaNivel2 extends Pantalla {

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
    private Array<Ashe> arrAshes;
    private Texture texturaAshe;
    private float timerCreaAshe;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_ASHE = 4;

    //Obstaculo (Cajas de fuego)
    private Array<Caja> arrCajas;
    private Texture texturaCajas;
    private float timerCrearCaja;
    private final float TIEMPO_CREAR_CAJA = 22;

    //Potenciador
    private Array<Potenciador> arrPotenciadores;
    private Texture texturaPotenciadores;
    private float timerCrearPotenciador;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_POTENCIADOR = 25;

    // Boton PAUSE
    private Texture texturaPause;

    //contador
    private float tiempo=0;
    private Texto texto; //escribe texto en la pantalla

    // Estado de Juego
    private EstadoOlivia estadoOlivia = EstadoOlivia.CAMINADO;

    public PantallaNivel2(Juego juego) {
        this.juego=juego;
    }
    @Override
    public void show() {
        crearFondo();
        crearPause();
        crearOlivia();
        crearAshes();
        crearTexto();
        crearCajas();
        crearPotenciador();

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearPotenciador() {
        texturaPotenciadores = new Texture("nivel2/diamante.png");
        arrPotenciadores = new Array<>();
    }

    private void crearCajas() {
        texturaCajas = new Texture("nivel2/cajasFuego.png");
        arrCajas = new Array<>();
    }

    private void crearTexto() {
        texto = new Texto("font/arcade2.fnt");
    }

    private void recuperarMarcador() {
        Preferences prefs = Gdx.app.getPreferences("TIEMPO");
        tiempo = prefs.getInteger("segundos",0);
    }

    private void crearFondo(){

        texturaFondo = new Texture("nivel2/fondoNivel2.jpg");
    }

    private void crearAshes() {
        texturaAshe = new Texture("nivel2/AsheAzul.png");
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

        if (estadoOlivia == EstadoOlivia.PAUSA){
            texto.mostrarMensaje(batch, "PAUSA", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para CONTINUAR", 3*ANCHO/4, ALTO/4);
            texto.mostrarMensaje(batch, "Tap para ir a MENU", ANCHO/4, ALTO/4);
        }

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
        texto.mostrarMensaje(batch,"Meta  60s",ANCHO*.45F,.9F*ALTO);
        texto.mostrarMensaje(batch,"Tiempo  "+Integer.toString((int) tiempo),ANCHO*.85F,.9F*ALTO);

        if(estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==60){
            texto.mostrarMensaje(batch, "¡Has ganado! Has pasado el segundo nivel", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para continuar...", ANCHO/2, ALTO/4);
        }


        batch.end();
    }

    private void actualizar(float delta) {
        if(estadoOlivia != EstadoOlivia.PAUSA && estadoOlivia != EstadoOlivia.MURIENDO && (estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo<60) ){
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
            Caja caja = new Caja(texturaCajas, xCaja, ALTO/4);
            arrCajas.add(caja);
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
            Ashe ashe = new Ashe(texturaAshe, xAshe, ALTO/4);
            arrAshes.add(ashe);
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO){
            probarColisiones();
        }

        // Mover los Ashes
        for (Ashe ashe: arrAshes) {
            ashe.moverIzquierda(delta);
        }

    }

    // Prueba la colision de olivia vs ashes
    private void probarColisiones() {
        colisionAshe();
        colisionCaja();
    }

    private void colisionCaja() {
        for (Caja caja : arrCajas) {
            //Gdx.app.log("Probando colision", "tengo miedo");
            if (olivia.sprite.getBoundingRectangle().overlaps(caja.sprite.getBoundingRectangle())){
                // Le pego
                olivia.setEstado(EstadoOlivia.MURIENDO);
                estadoOlivia = EstadoOlivia.MURIENDO;
                //olivia = null;
                Gdx.app.log("Probando colision", "YA LE PEGAMOS");
                break;
            }
        }
    }

    private void colisionAshe() {
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
            if(rectPuse.contains(v.x, v.y)){
                // SALIY y guardar el marcador
                estadoOlivia = EstadoOlivia.PAUSA;
                olivia.setEstado(EstadoOlivia.PAUSA);
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

            if (estadoOlivia != EstadoOlivia.PAUSA && estadoOlivia != EstadoOlivia.MURIENDO ){
                olivia.saltar(); // Top-Down
            }

            if (estadoOlivia == EstadoOlivia.MURIENDO){
                if (v.x >= ANCHO/2){
                    juego.setScreen(new PantallaNivel2(juego));
                }
                else
                    juego.setScreen(new PantallaMenu(juego));
            }

            if (estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==60 ){
                juego.setScreen(new PantallaNivel2Completo(juego));
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

}
