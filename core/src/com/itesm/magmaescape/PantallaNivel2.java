package com.itesm.magmaescape;

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
    private Array<com.itesm.magmaescape.Ashe> arrAshes;
    private Texture texturaAshe;
    private float timerCreaAshe;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_ASHE = 4;

    //Obstaculo (Cajas de fuego)
    private Array<Caja> arrCajas;
    private Texture texturaCajas;
    private float timerCrearCaja;
    private final float TIEMPO_CREAR_CAJA = 12;

    //Aves
    private Array<Ave> arrAves;
    private Texture texturaAves;
    private float timerCrearAve;
    private final float TIEMPO_CREAR_AVE = 5;


    //Monos
    private Array<com.itesm.magmaescape.Mono> arrMonos;
    private Texture texturaMono;
    private float timerCrearMono;
    private final float TIEMPO_CREAR_MONO =6;
    boolean primerMono=true;



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
    private com.itesm.magmaescape.Texto texto; //escribe texto en la pantalla
    private com.itesm.magmaescape.Texto potentLentitudtxt;
    // Estado de Juego
    private com.itesm.magmaescape.EstadoOlivia estadoOlivia = com.itesm.magmaescape.EstadoOlivia.CAMINADO;

    //Potenciador Lentitud
    private boolean coliPotenLenti=false;
    float tiempoColision=0;
    float tiempoLentitud=0;


    public PantallaNivel2(Juego juego) {
        this.juego=juego;
        Preferences prefs = Gdx.app.getPreferences("MusicPreference");
        boolean musicOn = prefs.getBoolean("musicOn", true);
        if(musicOn){
            juego.reproducir(Juego.TipoMusica.NIVELES);
        }
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
        crearAves();
        crearMonos();

        procesadorEntrada = new ProcesadorEntrada();
        Gdx.input.setInputProcessor(procesadorEntrada);
    }

    private void crearMonos() {
        texturaMono=new Texture("nivel2/monkey.png");
        arrMonos=new Array<>();
    }

    private void crearAves() {
        texturaAves=new Texture("nivel2/aves.png");
        arrAves=new Array<>();
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
        texto = new com.itesm.magmaescape.Texto("font/arcade2.fnt");
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


        //Dibujar Ashes
        for (com.itesm.magmaescape.Ashe ashe : arrAshes) {
            ashe.render(batch);
        }
        //Dibujar aves
        for (Ave ave:arrAves) {
            ave.render(batch);
        }
        //Dibujar monos
        for (com.itesm.magmaescape.Mono mono:arrMonos) {
            mono.render(batch);
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

        if (estadoOlivia == com.itesm.magmaescape.EstadoOlivia.MURIENDO){
            texto.mostrarMensaje(batch, "PERDISTE:(", ANCHO/2, ALTO-(ALTO*.20F));
            texto.mostrarMensaje(batch, "Tap para volver a intentar", 3*ANCHO/4, ALTO/4);
            texto.mostrarMensaje(batch, "Tap para ir a menu", ANCHO/4, ALTO/4);
        }

        //dibujar contador de tiempo
        texto.mostrarMensaje(batch,"Meta  60s",ANCHO*.45F,.9F*ALTO);
        texto.mostrarMensaje(batch,"Tiempo  "+Integer.toString((int) tiempo),ANCHO*.85F,.9F*ALTO);

        //dibujar cuando tiempo del potenciador
        if(coliPotenLenti){
            potentLentitudtxt.mostrarMensaje(batch,"Lentitud",ANCHO*.13F,.65F*ALTO);
            potentLentitudtxt.mostrarMensaje(batch,"Activada "+(8-(int)(tiempoLentitud)),ANCHO*.13F,.60F*ALTO);
        }


        if(estadoOlivia != com.itesm.magmaescape.EstadoOlivia.MURIENDO && (int)tiempo==60){
            texto.mostrarMensaje(batch, "¡Has ganado! Has pasado el segundo nivel", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para continuar...", ANCHO/2, ALTO/4);
            Preferences preferencias = Gdx.app.getPreferences("TIEMPO");
            preferencias.putInteger("segundos", (int) tiempo);
            preferencias.flush();

            Preferences preferencias3 = Gdx.app.getPreferences("NIVEL3");
            preferencias3.putInteger("edoNivel3", 1);
            preferencias3.flush();

        }

        batch.end();

        // Dibujar la PAUSA
        if(estadoOlivia == com.itesm.magmaescape.EstadoOlivia.PAUSA && escenaPausa != null) {
            escenaPausa.draw();
        }

    }

    private void actualizar(float delta) {
        if(estadoOlivia != com.itesm.magmaescape.EstadoOlivia.PAUSA && estadoOlivia != com.itesm.magmaescape.EstadoOlivia.MURIENDO && (estadoOlivia != com.itesm.magmaescape.EstadoOlivia.MURIENDO && (int)tiempo<60) ){
            actualizarFondo();
            actualizarAshes(delta);
            actualizarCajas(delta);
            actualizarPotenciadores(delta);
            actualizarTiempoPotenciador();
            actualizarAves(delta);
            actualizarMonos(delta);
            tiempo= tiempo+(60*Gdx.graphics.getDeltaTime())/60;
        }
    }

    private void actualizarMonos(float delta) {
        timerCrearMono+= delta;



        if (timerCrearMono>=TIEMPO_CREAR_MONO) {
            timerCrearMono = 0;
            //Crear Enemigo
            float xMono = MathUtils.random(ANCHO, ANCHO*1.1f);

            com.itesm.magmaescape.Mono mono = new com.itesm.magmaescape.Mono(texturaMono,xMono,ALTO*0.03f);
            arrMonos.add(mono);
        }


        // Mover los Ashes
        for (int i=arrMonos.size-1; i>=0; i--){
            Mono mono = arrMonos.get(i);
            mono.moverIzquierda(delta);
            if (mono.sprite.getX() < -60) {
                arrMonos.removeIndex(i);
            }
        }

    }

    private void actualizarAves(float delta) {
        timerCrearAve+= delta;
        if (timerCrearAve>=TIEMPO_CREAR_AVE) {
            timerCrearAve = 0;
            //Crear Enemigo
            float xAve = MathUtils.random(ANCHO, ANCHO*1.5f);
            Ave ave = new Ave(texturaAves,xAve,ALTO/1.22f);
            arrAves.add(ave);
        }


        // Mover los Ashes
        for (int i=arrAves.size-1; i>=0; i--){
            Ave ave = arrAves.get(i);
            ave.moverIzquierda(delta);
            if (ave.sprite.getX() < -60) {
                arrAves.removeIndex(i);
            }
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
            com.itesm.magmaescape.Ashe ashe = new com.itesm.magmaescape.Ashe(texturaAshe, xAshe, ALTO/4,-350);
            arrAshes.add(ashe);
        }

        if(estadoOlivia!= com.itesm.magmaescape.EstadoOlivia.MURIENDO){
            probarColisiones();
        }

        // Mover los Ashes
        for (int i=arrAshes.size-1; i>=0; i--){
            com.itesm.magmaescape.Ashe ashe = arrAshes.get(i);
        if (coliPotenLenti){
                if(tiempoLentitud>=8.0){
                    ashe.setPotenLentitud(false);
                    coliPotenLenti=false;
                    tiempoLentitud=0;

                } else {
                    ashe.setPotenLentitud(true);
                }
        }
            ashe.moverIzquierda(delta);
            if (ashe.sprite.getX() <-60) {
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
                break;

            }

        }

    }

    private void colisionCaja() {
        for (Caja caja : arrCajas) {
            if (olivia.sprite.getBoundingRectangle().overlaps(caja.sprite.getBoundingRectangle())){
                // Le pego
                olivia.setEstado(com.itesm.magmaescape.EstadoOlivia.MURIENDO);
                estadoOlivia = com.itesm.magmaescape.EstadoOlivia.MURIENDO;
                //olivia = null;
                break;
            }
        }
    }

    private void colisionAshe() {
        for (Ashe ashe: arrAshes) {
            if (olivia.sprite.getBoundingRectangle().overlaps(ashe.sprite.getBoundingRectangle())){
                olivia.setEstado(com.itesm.magmaescape.EstadoOlivia.MURIENDO);
                estadoOlivia = com.itesm.magmaescape.EstadoOlivia.MURIENDO;
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
                estadoOlivia = com.itesm.magmaescape.EstadoOlivia.PAUSA;
                // CAMBIAR el procesador de entrada
                Gdx.input.setInputProcessor(escenaPausa);   //Para detectar los clicks sobre los botones

            }else

            if (estadoOlivia != com.itesm.magmaescape.EstadoOlivia.PAUSA && estadoOlivia != com.itesm.magmaescape.EstadoOlivia.MURIENDO && (int)tiempo<60 ){

                olivia.saltar(); // Top-Down
            }

            if (estadoOlivia == com.itesm.magmaescape.EstadoOlivia.MURIENDO){
               //juego.reproducir(Juego.TipoMusica.MUERTE);
                if (v.x >= ANCHO/2){
                    juego.setScreen(new com.itesm.magmaescape.PantallaCargando(juego, com.itesm.magmaescape.Pantallas.NIVEL2));
                }
                else
                {
                    Preferences prefs = Gdx.app.getPreferences("MusicPreference");
                    boolean musicOn = prefs.getBoolean("musicOn", true);
                    if(musicOn){
                        juego.detener(Juego.TipoMusica.NIVELES);
                        juego.reproducir(Juego.TipoMusica.MENU);
                    }
                    juego.setScreen(new com.itesm.magmaescape.PantallaCargando(juego, com.itesm.magmaescape.Pantallas.MENU));

                }
            }

            if (estadoOlivia != com.itesm.magmaescape.EstadoOlivia.MURIENDO && (int)tiempo==60 ){
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
            Button btnJugar = crearBoton("menuPausa/button_volver-al-juego.png", "menuPausa/button_volver-al-juego-2.png");
            btnJugar.setPosition(ANCHO / 2, 0.7f * ALTO, Align.center);
            addActor(btnJugar);
            //Registrar el evento de click para el boton
            btnJugar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //QUITAR LA PAUSA
                    if (estadoOlivia == com.itesm.magmaescape.EstadoOlivia.PAUSA) {
                        estadoOlivia = EstadoOlivia.CAMINADO;
                        Gdx.input.setInputProcessor(procesadorEntrada);
                    }
                }
            });

            //BOTON VOLVER A INTENTAR
            Button btnVolverIntentar =  crearBoton("menuPausa/button_volver-a-intentar.png", "menuPausa/button_volver-a-intentar-2.png");
            addActor(btnVolverIntentar);
            btnVolverIntentar.setPosition(ANCHO/2,0.5f*ALTO, Align.center);

            btnVolverIntentar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    juego.setScreen(new PantallaNivel2(juego));
                    juego.setScreen(new com.itesm.magmaescape.PantallaCargando(juego, com.itesm.magmaescape.Pantallas.NIVEL2));
                }
            });

            //BOTON IR A MENU PRINCIPAL
            Button btnMenu = crearBoton("menuPausa/button_menu-principal.png", "menuPausa/button_menu-principal-2.png");
            btnMenu.setPosition(ANCHO / 2, 0.3f * ALTO, Align.center);
            addActor(btnMenu);
            //Registrar el evento de click para el boton
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Preferences prefs = Gdx.app.getPreferences("MusicPreference");
                    boolean musicOn = prefs.getBoolean("musicOn", true);
                    if(musicOn){
                        juego.detener(Juego.TipoMusica.NIVELES);
                        juego.reproducir(Juego.TipoMusica.MENU);
                    }
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MENU));
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
