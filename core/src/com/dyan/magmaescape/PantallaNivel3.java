package com.dyan.magmaescape;
/*
// Esta clase desarrolla el nivel 3 del juego
Autor: Norma P Iturbide
*/
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
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
    private Texture texturaNeblina;
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

    //Libelulas
    private Array<Libelula> arrLibelulas;
    private Texture texturaLibelula;
    private float timerCreaLibelula;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_LIBELULAS = 5;

    //Bolas de fuego
    private Array<BolaFuego> arrBolasFuego;
    private Texture texturaBolaFuego;
    private float timerCreaBola;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_BOLA = 95;

    // Boton PAUSE
    private Texture texturaPause;

    //contador
    private float tiempo=0;
    private TextoBlanco texto; //escribe texto en la pantalla negro

    TextoBlanco txt= new TextoBlanco("font/arcade2.fnt");

    TextoBlanco txtLEN=new TextoBlanco("font/arcade2.fnt");



    //Caja
    private Array<Caja> arrCajas;
    private Texture texturaCaja;
    private float timerCrearCaja;
    private final float TIEMPO_CREAR_CAJA = 92;


    //Potenciadores
    //Azul
    private Potenciador potenciadorLentitud;
    private Texture texturaPotenciadores;
    private float timerCrearPotenciador;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_POTENCIADOR = 5;

    //Rojo
    private  PotenciadorInvencibilidad potenciadorInvencibilidad;
    private Texture texturaPotenciadorInvencibilidad;
    private float timerCrearPotenciadorInvencibilidad;   //Acumulador de tiempo
    private final float TIEMPO_CREAR_POTENCIADOR_INVENCIBILIDAD = 25;


    //Escena pausa
    private EscenaPausa escenaPausa;
    private ProcesadorEntrada procesadorEntrada;


    // Estado de Olivia
    private EstadoOlivia estadoOlivia = EstadoOlivia.CAMINADO;

    //Estado del juego
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    // Estados potenciadores
    private EstadoInvencibilidad estadoInvencibilidad = EstadoInvencibilidad.INVENCIBILIDAD_DESACTIVADA;
    private EstadoLentitud estadoLentitud = EstadoLentitud.LENTITUD_DESACTIVADA;
    //contador
    private float tiempoLentitud = 0;
    private float tiempoInv = 0;


    public PantallaNivel3(Juego juego) {
        this.juego = juego;
        juego.reproducir(Juego.TipoMusica.NIVELES);

    }

    @Override
    public void show() {

        crearFondo();
        crearFondoNeblina();
        crearPause();
        crearOlivia();
        crearAshes();
        crearLibelulas();
        crearBolasFuego();
        crearTexto();
        crearCaja();
        crearPotenciador();
        crearPotenciadorInvencibilidad();
        //recuperarMarcador();



        procesadorEntrada = new ProcesadorEntrada();
        Gdx.input.setInputProcessor(procesadorEntrada);




    }



    private void crearFondoNeblina() {
        texturaNeblina= new Texture("nivel3/Nube35.png");

    }

    private void crearPotenciadorInvencibilidad() {
        texturaPotenciadorInvencibilidad= new Texture("nivel3/Diamante2rojo.png");
        float xPotenciadorINV= MathUtils.random(ANCHO,ANCHO*1.5F);
        potenciadorInvencibilidad= new PotenciadorInvencibilidad(texturaPotenciadorInvencibilidad,xPotenciadorINV,ALTO/1.35F);

    }

    private void crearPotenciador() {
        texturaPotenciadores = new Texture("nivel3/Diamante2resize.png");
        float xPotenciador = MathUtils.random(ANCHO, ANCHO*1.5f);
        potenciadorLentitud = new Potenciador(texturaPotenciadores, xPotenciador, ALTO/1.35f);
    }

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtnMario = new TextureRegionDrawable(texturaBoton);
        Texture texturaInverso = new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso = new TextureRegionDrawable(texturaInverso);
        return new Button(trdBtnMario, trdBtnInverso);
    }

    private void crearCaja() {
        texturaCaja= new Texture("nivel2/fuegos.png");
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

    private void crearLibelulas() {
        texturaLibelula = new Texture("nivel3/libelula.png");
        arrLibelulas = new Array<>();
    }

    private void crearBolasFuego() {
        texturaBolaFuego = new Texture("nivel3/bolaFuego.png");
        arrBolasFuego = new Array<>();
        //Gdx.app.log("FUNION:", "creaBolasFuego");
    }

    private void crearOlivia() {
        Texture texturaOlivia = new Texture("nivel1/oliviaSprites.png");
        olivia = new Olivia(texturaOlivia,ANCHO/4-(texturaOlivia.getWidth()/4f),ALTO/4f,300,190);
    }
    private void crearPause() {

        texturaPause = new Texture("nivel1/button_pausa.png");
    }


    @Override
    public void render(float delta) {

        // actualizar
        if (estadoInvencibilidad == EstadoInvencibilidad.INVENCIBILIDAD_DESACTIVADA && estadoLentitud == EstadoLentitud.LENTITUD_DESACTIVADA){
            actualizar(delta);
        }


        if (estadoInvencibilidad == EstadoInvencibilidad.INVENCIBILIDAD_ACTIVADA){
            actualizarInven(delta);
        }

        if (estadoLentitud == EstadoLentitud.LENTITUD_ACTIVADA){
            actualizarLentitud(delta);
        }

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
        //Dibujar Libelulas
        for (Libelula libelula : arrLibelulas) {
            libelula.render(batch);
        }


        //Dibujar Bolas de fuego
        for (BolaFuego bolaFuego : arrBolasFuego) {
            bolaFuego.render(batch);
        }

        //Dibujar Cajas de Fuego
        for (Caja caja : arrCajas) {
            caja.render(batch);
        }




        if(potenciadorLentitud!=null)
        {
            timerCrearPotenciador+= delta;
            if (timerCrearPotenciador>=TIEMPO_CREAR_POTENCIADOR) {
                //Crear Potenciador
                potenciadorLentitud.render(batch);
                if(estadoJuego==EstadoJuego.JUGANDO && estadoOlivia!=EstadoOlivia.MURIENDO) potenciadorLentitud.moverIzquierda(delta);
            }
        }


        //Dibujar Potenciador Rojo
        if(potenciadorInvencibilidad!=null)
        {
            timerCrearPotenciadorInvencibilidad+=delta;

            if(timerCrearPotenciadorInvencibilidad>=TIEMPO_CREAR_POTENCIADOR_INVENCIBILIDAD)
            {
                //crear potenciador
                potenciadorInvencibilidad.render(batch);
                if(estadoJuego==EstadoJuego.JUGANDO && estadoOlivia!=EstadoOlivia.MURIENDO) potenciadorInvencibilidad.moverIzquierda(delta);

            }
        }

        batch.draw(texturaNeblina,ANCHO-texturaNeblina.getWidth(),ALTO*.15F);


        if (estadoOlivia == EstadoOlivia.MURIENDO){
            texto.mostrarMensaje(batch, "Sorry,  perdiste :(", ANCHO/2, ALTO-(ALTO*.20F));
            texto.mostrarMensaje(batch, "Tap para VOLVER A INTENTAR", 3*ANCHO/4, ALTO/4);
            texto.mostrarMensaje(batch, "Tap para ir a MENU", ANCHO/4, ALTO/4);
        }

        //dibujar contador de tiempo
        texto.mostrarMensaje(batch,"Meta  90s",ANCHO*.45F,.9F*ALTO);
        texto.mostrarMensaje(batch,"Tiempo  "+Integer.toString((int) tiempo),ANCHO*.85F,.9F*ALTO);

        if(estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==90){
            texto.mostrarMensaje(batch, "¡Has ganado! Has pasado el tercer nivel", ANCHO/2, ALTO/2);
            texto.mostrarMensaje(batch, "Tap para continuar...", ANCHO/2, ALTO/4);
        }

        if(estadoInvencibilidad==EstadoInvencibilidad.INVENCIBILIDAD_ACTIVADA && txt!=null)
        {
            txt.mostrarMensaje(batch,"Invencibilidad",ANCHO*.13F,.65F*ALTO);
            txt.mostrarMensaje(batch,"Activada "+(10-(int)(tiempoInv)),ANCHO*.13F,.60F*ALTO);
        }

        if(estadoLentitud==EstadoLentitud.LENTITUD_ACTIVADA&& txtLEN!=null)
        {
            txtLEN.mostrarMensaje(batch,"Lentitud",ANCHO*.13F,.65F*ALTO);
            txtLEN.mostrarMensaje(batch,"Activada "+(11-(int)(tiempoLentitud)),ANCHO*.13F,.60F*ALTO);
        }

        //if(estadoOlivia==EstadoOlivia.MURIENDO) juego.reproducir(Juego.TipoMusica.MUERTE);

        //Dibujar la pausa
        if(estadoJuego == EstadoJuego.PAUSADO && escenaPausa!= null)
        {
            escenaPausa.draw();
        }

        batch.end();



    }

    private void actualizarLentitud(float delta) {
        if(estadoJuego==EstadoJuego.JUGANDO && estadoOlivia!=EstadoOlivia.MURIENDO && (int)tiempo<90) {
            actualizarFondo();
            actualizarAshesLentitud(delta);
            actualizarLibelulas(delta);
            actualizarBolasFuego(delta);
            actualizarCajas(delta);
            actualizarPotenciadores();
            actualizarPotenciadorInvencibilidad(delta);
            checandoTiempoPotenciador();
            tiempo= tiempo+(60*Gdx.graphics.getDeltaTime())/60;
        }
    }

    private void actualizarInven(float delta) {
        if(estadoJuego==EstadoJuego.JUGANDO && estadoOlivia!=EstadoOlivia.MURIENDO && (int)tiempo<90) {
            actualizarFondo();
            actualizarAshesINVENCIBILIDAD(delta);
            actualizarLibelulas(delta);
            actualizarBolasFuegoINVENCIBILIDAD(delta);
            actualizarCajasINVENCIBILIDAD(delta);
            actualizarPotenciadores();
            actualizarPotenciadorInvencibilidad(delta);
            checandoTiempoPotenciador();
            tiempo= tiempo+(60*Gdx.graphics.getDeltaTime())/60;
        }
    }


    private void actualizar(float delta) {
        if(estadoJuego==EstadoJuego.JUGANDO && estadoOlivia!=EstadoOlivia.MURIENDO && (int)tiempo<90) {
            actualizarFondo();
            actualizarAshes(delta);
            actualizarLibelulas(delta);
            actualizarBolasFuego(delta);
            actualizarCajas(delta);
            actualizarPotenciadores();
            actualizarPotenciadorInvencibilidad(delta);
            checandoTiempoPotenciador();
            tiempo= tiempo+(60*Gdx.graphics.getDeltaTime())/60;
        }




    }

    private void checandoTiempoPotenciador() {
        if (estadoLentitud == EstadoLentitud.LENTITUD_ACTIVADA){
            tiempoLentitud = tiempoLentitud +(60*Gdx.graphics.getDeltaTime())/60;
        }
        if ((int)tiempoLentitud ==11){
            estadoLentitud = EstadoLentitud.LENTITUD_DESACTIVADA;
            txtLEN=null;
        }
        if (estadoInvencibilidad == EstadoInvencibilidad.INVENCIBILIDAD_ACTIVADA){
            tiempoInv = tiempoInv+(60*Gdx.graphics.getDeltaTime())/60;
        }
        if ((int)tiempoInv == 10){
            estadoInvencibilidad = EstadoInvencibilidad.INVENCIBILIDAD_DESACTIVADA;
            txt=null;
        }
    }


    private void actualizarPotenciadorInvencibilidad(float delta) {
        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            colisionPotenInvencibilidad();
        }


    }

    private void colisionPotenInvencibilidad() {
        if(potenciadorInvencibilidad!=null){
            if (olivia.sprite.getBoundingRectangle().overlaps(potenciadorInvencibilidad.sprite.getBoundingRectangle())) {
                // Le pego
                //Gdx.app.log("Probando colision con potenciador rojo", "invencibilidad papu");
                estadoInvencibilidad = EstadoInvencibilidad.INVENCIBILIDAD_ACTIVADA;
                Gdx.app.log("ESTADO", "ya tenemo EDO INVENC");
                potenciadorInvencibilidad=null;

            }
        }

    }

    private void actualizarPotenciadores() {
        // Crear Potenciadores
        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            colisionPotenRapidez();
        }

    }

    private void colisionPotenRapidez() {
        if(potenciadorLentitud!=null)
        {
            if (olivia.sprite.getBoundingRectangle().overlaps(potenciadorLentitud.sprite.getBoundingRectangle())){
                Gdx.app.log("Probando colision con el potenciador", "TOCO DIAMANTE,CORRIENDO");
                estadoLentitud = EstadoLentitud.LENTITUD_ACTIVADA;
                    Gdx.app.log("ESTADO", "ya tenemo EDO LENTITUD");
                potenciadorLentitud=null;
            }
        }

    }

    private void actualizarCajasINVENCIBILIDAD(float delta) {

        timerCrearCaja += delta;
        if (timerCrearCaja >= TIEMPO_CREAR_CAJA) {
            timerCrearCaja = 0;
            //Crear obstaculo
            float xCaja = MathUtils.random(ANCHO, ANCHO*1.5F);
            Caja caja = new Caja(texturaCaja, xCaja, ALTO/4);
            arrCajas.add(caja);
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            //colisionCaja();
        }

        //Mover los obstaculos
        for (int i=arrCajas.size-1; i>=0; i--){
            Caja caja =arrCajas.get(i);
            caja.moverIzquierda(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (caja.sprite.getX() < -60) {
                arrCajas.removeIndex(i);
            }
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
        for (int i=arrCajas.size-1; i>=0; i--){
            Caja caja =arrCajas.get(i);
            caja.moverIzquierda(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (caja.sprite.getX() < -60) {
                arrCajas.removeIndex(i);
            }
        }
    }

    private void actualizarBolasFuegoINVENCIBILIDAD(float delta) {

        // Crear Bolas de fuego
        timerCreaBola += delta;
        if (timerCreaBola>= TIEMPO_CREAR_BOLA) {
            timerCreaBola = 0;
            float xBolaFuego = MathUtils.random(ANCHO, ANCHO*1.5f);
            BolaFuego bolaFuego = new BolaFuego(texturaBolaFuego, xBolaFuego, ALTO-texturaBolaFuego.getHeight());
            arrBolasFuego.add(bolaFuego);
        }
        // Mover Bolas de fuego
        for (int i=arrBolasFuego.size-1; i>=0; i--){
            BolaFuego bolaFuego =arrBolasFuego.get(i);
            bolaFuego.moverCaida(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (bolaFuego.sprite.getX() < -60) {
                arrBolasFuego.removeIndex(i);
            }
        }

    }


    private void actualizarBolasFuego(float delta){

        // Crear Bolas de fuego
        timerCreaBola += delta;
        if (timerCreaBola>= TIEMPO_CREAR_BOLA) {
            timerCreaBola = 0;
            float xBolaFuego = MathUtils.random(ANCHO, ANCHO*1.5f);
            BolaFuego bolaFuego = new BolaFuego(texturaBolaFuego, xBolaFuego, ALTO-texturaBolaFuego.getHeight());
            arrBolasFuego.add(bolaFuego);
        }
        // Mover Bolas de fuego
        for (int i=arrBolasFuego.size-1; i>=0; i--){
            BolaFuego bolaFuego =arrBolasFuego.get(i);
            bolaFuego.moverCaida(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (bolaFuego.sprite.getX() < -60) {
                arrBolasFuego.removeIndex(i);
            }
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            probarColisionBolaFuego();
        }

    }

    private void actualizarAshesINVENCIBILIDAD(float delta) {
        // Crear Ashes
        timerCreaAshe += delta;
        if (timerCreaAshe>=TIEMPO_CREAR_ASHE) {
            timerCreaAshe = 0;
            //Crear Enemigo
            float xAshe = MathUtils.random(ANCHO, ANCHO*1.5f);
            Ashe ashe = new Ashe(texturaAshe, xAshe, ALTO/4f, -700);
            arrAshes.add(ashe);
        }

        /*if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            probarColisionAshe();
        }*/

        // Mover los Ashes
        for (int i=arrAshes.size-1; i>=0; i--){
            Ashe ashe=arrAshes.get(i);
            ashe.moverIzquierda(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (ashe.sprite.getX() < -60) {
                arrAshes.removeIndex(i);
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
            Ashe ashe = new Ashe(texturaAshe, xAshe, ALTO/4f, -800);
            arrAshes.add(ashe);
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            probarColisionAshe();
        }

        // Mover los Ashes
        for (int i=arrAshes.size-1; i>=0; i--){
            Ashe ashe=arrAshes.get(i);
            ashe.moverIzquierda(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (ashe.sprite.getX() < -60) {
                arrAshes.removeIndex(i);
            }
        }
    }

    private void actualizarLibelulas(float delta) {
        // Crear Ashes
        timerCreaLibelula += delta;
        if (timerCreaLibelula >= TIEMPO_CREAR_LIBELULAS) {
            timerCreaLibelula = 0;
            //Crear Enemigo
            float xLibelula = MathUtils.random(ANCHO, ANCHO*1.5f);
            float yLibelula = MathUtils.random(ALTO*0.75f, ALTO);
            Libelula libelula = new Libelula(texturaLibelula, xLibelula, yLibelula);
            arrLibelulas.add(libelula);
        }
        // Mover los Ashes
        for (int i = arrLibelulas.size-1; i>=0; i--){
            Libelula libelula = arrLibelulas.get(i);
            libelula.mover(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (libelula.sprite.getX() < -60) {
                arrLibelulas.removeIndex(i);
            }
        }
    }

    private void actualizarAshesLentitud(float delta) {
        // Crear Ashes
        timerCreaAshe += delta;
        if (timerCreaAshe>=TIEMPO_CREAR_ASHE) {
            timerCreaAshe = 0;
            //Crear Enemigo
            float xAshe = MathUtils.random(ANCHO, ANCHO*1.5f);
            Ashe ashe = new Ashe(texturaAshe, xAshe, ALTO/4f, -400);
            arrAshes.add(ashe);
        }

        if(estadoOlivia!=EstadoOlivia.MURIENDO && estadoJuego==EstadoJuego.JUGANDO){
            probarColisionAshe();
        }

        // Mover los Ashes
        for (int i=arrAshes.size-1; i>=0; i--){
            Ashe ashe=arrAshes.get(i);
            ashe.moverIzquierda(delta);
            //Prueba si la caja debe desaparecer, porque salió de la pantalla
            if (ashe.sprite.getX() < -60) {
                arrAshes.removeIndex(i);
            }
        }
    }


    private void probarColisionBolaFuego() {
        for (BolaFuego bolaFuego : arrBolasFuego) {
            if (olivia.sprite.getBoundingRectangle().overlaps(bolaFuego.sprite.getBoundingRectangle())) {
                // Le pego
                olivia.setEstado(EstadoOlivia.MURIENDO);
                estadoOlivia = EstadoOlivia.MURIENDO;
                //olivia = null;
                Gdx.app.log("Probando colision con bola de fuego", "YA LE PEGAMOS");
                break;
            }
        }
    }

    private void probarColisionAshe() {
        for (Ashe ashe : arrAshes) {
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



    private void actualizarFondo() {
        xFondo-=6;
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
            float xPause = .03F*ANCHO;
            float yPause = .85F*ALTO;
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

                    juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL3));
                }
                else
                {
                    juego.reproducir(Juego.TipoMusica.MENU);
                    juego.setScreen(new PantallaCargando(juego,Pantallas.MENU));
                }

            }

            if (estadoOlivia != EstadoOlivia.MURIENDO && (int)tiempo==90 ){
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
            Button btnVolverJuego = crearBoton("menuPausa/button_volver-al-juego.png", "menuPausa/button_volver-al-juego-2.png");
            //agregar boton a la escena
            addActor(btnVolverJuego);
            btnVolverJuego.setPosition(ANCHO/2,.7F*ALTO,Align.center);

            btnVolverJuego.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event,x,y);
                    estadoJuego= PantallaNivel3.EstadoJuego.JUGANDO;
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
                    juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL3));
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
