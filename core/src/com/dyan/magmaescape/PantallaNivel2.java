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

public class PantallaNivel2 extends Pantalla {

    private Juego juego;

    //Fondo
    private Texture texturaFondo;
    private  Texture texturaNeblina;
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
    private final float TIEMPO_CREAR_CAJA = 18;

    //Potenciador
    private Array<PotenciadorLentitud> arrPotenciadores;
    private Texture texturaPotenciadores;
    private float timerCrearPotenciador;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_POTENCIADOR = 21;

    // Boton PAUSE
    private Texture texturaPause;
    // PAUSA
    private EscenaPausa escenaPausa;
    private ProcesadorEntrada procesadorEntrada;
    //contador
    private float tiempo=0;
    private Texto texto; //escribe texto en la pantalla
    private Texto potentLentitudtxt;
    // Estado de Juego
    private EstadoOlivia estadoOlivia = EstadoOlivia.CAMINADO;

    //Potenciador Lentitud
    private boolean coliPotenLenti=false;
    float tiempoColision=0;
    float tiempoLentitud=0;


    public PantallaNivel2(Juego juego) {
        this.juego=juego;
        juego.reproducir(Juego.TipoMusica.NIVELES);
    }
    @Override
    public void show() {
        crearFondo();
        crearFondoNeblina();
        crearPause();
        crearOlivia();
        crearAshes();
        crearTexto();
        crearPotentLentitudTxt();
        crearCajas();
        crearPotenciador();

        procesadorEntrada = new ProcesadorEntrada();
        Gdx.input.setInputProcessor(procesadorEntrada);
    }

    private void crearFondoNeblina() {
        texturaNeblina= new Texture("nivel3/Nube35.png");
    }

    private void crearPotenciador() {
        texturaPotenciadores = new Texture("nivel2/diamante.png");
        arrPotenciadores = new Array<>();
    }

    private void crearCajas() {
        texturaCajas = new Texture("nivel2/fuegos.png");
        arrCajas = new Array<>();
    }

    private void crearTexto() {
        texto = new Texto("font/arcade2.fnt");
    }
    private void crearPotentLentitudTxt() {
        potentLentitudtxt = new Texto("font/arcade2.fnt");
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
        arrAshes = new Array<>();
    }

    private void crearOlivia() {
        Texture texturaOlivia = new Texture("nivel1/oliviaSprites.png");
        olivia = new Olivia(texturaOlivia,ANCHO/4-(texturaOlivia.getWidth()/4f),ALTO/4f,250,170);
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

        /*if (estadoOlivia == EstadoOlivia.PAUSA){
            texto.mostrarMensaje(batch, "PAUSA", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para CONTINUAR", 3*ANCHO/4, ALTO/4);
            texto.mostrarMensaje(batch, "Tap para ir a MENU", ANCHO/4, ALTO/4);
        }*/

        //Dibujar Ashes
        for (Ashe ashe : arrAshes) {
            ashe.render(batch);
        }
        //Dibujar Cajas de Fuego
        for (Caja caja : arrCajas) {
            caja.render(batch);
        }
        //Dibujar Potenciadores
        for (PotenciadorLentitud potenciadorLentitud : arrPotenciadores) {
            potenciadorLentitud.render(batch);
        }

        batch.draw(texturaNeblina,ANCHO-texturaNeblina.getWidth(),ALTO*.15F);

        if (estadoOlivia == EstadoOlivia.MURIENDO){
            texto.mostrarMensaje(batch, "Sorry,  perdiste :(", ANCHO/2, ALTO-(ALTO*.20F));
            texto.mostrarMensaje(batch, "Tap para VOLVER A INTENTAR", 3*ANCHO/4, ALTO/4);
            texto.mostrarMensaje(batch, "Tap para ir a MENU", ANCHO/4, ALTO/4);
        }

        //dibujar contador de tiempo
        texto.mostrarMensaje(batch,"Meta  60s",ANCHO*.45F,.9F*ALTO);
        texto.mostrarMensaje(batch,"Tiempo  "+Integer.toString((int) tiempo),ANCHO*.85F,.9F*ALTO);

        //dibujar cuando tiempo del potenciador
        if(coliPotenLenti){
            potentLentitudtxt.mostrarMensaje(batch,"Lentitud",ANCHO*.13F,.65F*ALTO);
            potentLentitudtxt.mostrarMensaje(batch,"Activada "+(int)(tiempoLentitud),ANCHO*.13F,.60F*ALTO);
        }


        if(estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==60){
            texto.mostrarMensaje(batch, "¡Has ganado! Has pasado el segundo nivel", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para continuar...", ANCHO/2, ALTO/4);
        }

        batch.end();

        // Dibujar la PAUSA
        if(estadoOlivia == EstadoOlivia.PAUSA && escenaPausa != null) {
            escenaPausa.draw();
        }

    }

    private void actualizar(float delta) {
        if(estadoOlivia != EstadoOlivia.PAUSA && estadoOlivia != EstadoOlivia.MURIENDO && (estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo<60) ){
            actualizarFondo();
            actualizarAshes(delta);
            actualizarCajas(delta);
            actualizarPotenciadores(delta);
            actualizarTiempoPotenciador();
            tiempo= tiempo+(60*Gdx.graphics.getDeltaTime())/60;
        }
    }

    private void actualizarTiempoPotenciador() {
        if(coliPotenLenti){
            tiempoLentitud = tiempoLentitud +(60*Gdx.graphics.getDeltaTime())/60;
        }else{
            //potentLentitudtxt=null;
        }
    }

    private void actualizarPotenciadores(float delta) {
        // Crear Potenciadores
        timerCrearPotenciador += delta;
        if (timerCrearPotenciador>=TIEMPO_CREAR_POTENCIADOR) {
            timerCrearPotenciador = 0;
            //Crear Potenciador
            float xPotenciador = MathUtils.random(ANCHO, ANCHO*1.5f);
            PotenciadorLentitud potenciadorLentitud = new PotenciadorLentitud(texturaPotenciadores, xPotenciador, ALTO/1.6f);
            arrPotenciadores.add(potenciadorLentitud);
        }
        // Mover los Potenciadores
        for (PotenciadorLentitud potenciadorLentitud : arrPotenciadores) {
            potenciadorLentitud.moverIzquierda(delta);
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
        //for (Caja caja : arrCajas){

        for (int i=arrCajas.size-1; i>=0; i--){
            Caja caja =arrCajas.get(i);
            caja.moverIzquierda(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (caja.sprite.getX() < -60) {
                arrCajas.removeIndex(i);
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
            Ashe ashe = new Ashe(texturaAshe, xAshe, ALTO/4,-350);
            arrAshes.add(ashe);
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO){
            probarColisiones();
        }

        // Mover los Ashes
        for (int i=arrAshes.size-1; i>=0; i--){
            Ashe ashe = arrAshes.get(i);
        if (coliPotenLenti){
                if(tiempoLentitud>9.0){
                   Gdx.app.log("TIEMPO ACABADO ", "El potenciador acabo");
                    ashe.setPotenLentitud(false);
                    coliPotenLenti=false;
                    Gdx.app.log("Normalidad", "El float es:"+ashe.getpotenLentitud());

                } else {
                    ashe.setPotenLentitud(true);
                }
        }
            ashe.moverIzquierda(delta);
            if (ashe.getX() < -60) {
               arrAshes.removeIndex(i);
            }
        }

    }

    // Prueba la colision de olivia vs ashes
    private void probarColisiones() {
        colisionAshe();
        colisionCaja();
        colisionPotenLentitud();
    }

    private void colisionPotenLentitud() {
        for(PotenciadorLentitud potenciadorLentitud : arrPotenciadores){
            int i=0;
            if (olivia.sprite.getBoundingRectangle().overlaps(potenciadorLentitud.sprite.getBoundingRectangle())){
                coliPotenLenti=true;
                arrPotenciadores.removeIndex(i);
                tiempoColision=this.tiempo;
                Gdx.app.log("Probando colision", "TOCO DIAMANTE,CORRIENDO");
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
        xFondo-=4.5f;
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
                //PONER LA PAUSA
                if (escenaPausa == null) {    //Inicializacion Lazy
                    escenaPausa = new EscenaPausa(vista);
                }
                estadoOlivia = EstadoOlivia.PAUSA;
                // CAMBIAR el procesador de entrada
                Gdx.input.setInputProcessor(escenaPausa);   //Para detectar los clicks sobre los botones

            }else

            if (estadoOlivia != EstadoOlivia.PAUSA && estadoOlivia != EstadoOlivia.MURIENDO ){

                olivia.saltar(); // Top-Down
            }

            if (estadoOlivia == EstadoOlivia.MURIENDO){
                juego.reproducir(Juego.TipoMusica.MUERTE);
                if (v.x >= ANCHO/2){
                    juego.setScreen(new PantallaNivel2(juego));
                }
                else
                {
                    juego.reproducir(Juego.TipoMusica.MENU);
                    juego.setScreen(new PantallaMenu(juego));

                }
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

    //La escena que se muestra cuando el jugador pausa el juego
    private class EscenaPausa extends Stage {
        private Texture texturaFondo;

        public EscenaPausa(Viewport vista) {
            super(vista);       //Pasa la vista al constructor de Stage
            texturaFondo = new Texture("nivel2/fondoPausa.png");
            Image imgFondo= new Image(texturaFondo);
            imgFondo.setPosition(ANCHO/2,ALTO/2, Align.center);
            addActor(imgFondo);

            //Boton Volver a Jugar
            Button btnJugar = crearBoton("menuPausa/btnVolverJuego.png", "menuPausa/btnVolverJuegoInverso.png");
            btnJugar.setPosition(ANCHO / 2, 0.6f * ALTO, Align.center);
            addActor(btnJugar);
            //Registrar el evento de click para el boton
            btnJugar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //QUITAR LA PAUSA
                    if (estadoOlivia == EstadoOlivia.PAUSA) {
                        estadoOlivia = EstadoOlivia.CAMINADO;
                        Gdx.input.setInputProcessor(procesadorEntrada);
                    }
                }
            });

            //BOTON IR A MENU PRINCIPAL
            Button btnMenu = crearBoton("menuPausa/btnMenuPrincipal.png", "menuPausa/btnMenuPrincipalInverso.png");
            btnMenu.setPosition(ANCHO / 2, 0.4F * ALTO, Align.center);
            addActor(btnMenu);
            //Registrar el evento de click para el boton
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.reproducir(Juego.TipoMusica.MENU);
                    juego.setScreen(new PantallaMenu(juego));
                }
            });
        }
        private Button crearBoton(String archivo, String archivoInverso) {
            Texture texturaBoton= new Texture(archivo);
            TextureRegionDrawable trdBtn =new TextureRegionDrawable(texturaBoton);
            //Inverso
            Texture texturaInverso= new Texture(archivoInverso);
            TextureRegionDrawable trdBtnInverso= new TextureRegionDrawable(texturaInverso);
            return new Button(trdBtn,trdBtnInverso);
        }
    }
}
