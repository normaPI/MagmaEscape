/*
Esta clase representa la pantalla para la seleccion de nivel
Autor: Amauri Elian Perez
*/
package com.itesm.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaSeleccionarNivel extends Pantalla {
    private Juego juego;

    //Fondo
    private Texture texturaFondo;

    //Escena
    private Stage escenaMenuSeleccionNivel;

    //Preferencias Niveles
    Integer nivel2;
    Integer nivel3;
    private com.itesm.magmaescape.EstadoNiveles estadoNiveles2 = com.itesm.magmaescape.EstadoNiveles.NIVEL2_LOCK;
    private com.itesm.magmaescape.EstadoNiveles estadoNiveles3 = com.itesm.magmaescape.EstadoNiveles.NIVEL3_LOCK;




    public PantallaSeleccionarNivel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        recuperarDisposicionNiveles();
        if (estadoNiveles2 == com.itesm.magmaescape.EstadoNiveles.NIVEL2_LOCK && estadoNiveles3 == com.itesm.magmaescape.EstadoNiveles.NIVEL3_LOCK)
            crearMenuNiveles1();
        else if (estadoNiveles3 == com.itesm.magmaescape.EstadoNiveles.NIVEL3_LOCK)
            crearMenuNiveles1y2();
        else
            crearMenuNiveles1y2y3();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }
    private void recuperarDisposicionNiveles() {
        Preferences prefsNivel2 = Gdx.app.getPreferences("NIVEL2");
        Preferences prefsNivel3 = Gdx.app.getPreferences("NIVEL3");
        // 0: lock 1:unlock
        nivel2 = prefsNivel2.getInteger("edoNivel2",0);
        nivel3 = prefsNivel3.getInteger("edoNivel3",0);

        if (nivel2 == 1){
            estadoNiveles2 = com.itesm.magmaescape.EstadoNiveles.NIVEL2_UNLOCK;
        }
        if (nivel3 == 1){
            estadoNiveles3 = EstadoNiveles.NIVEL3_UNLOCK;
        }


    }

    private void crearMenuNiveles1() {
        texturaFondo= new Texture("menuNiveles/fondoSeleccionNivel.png");

        escenaMenuSeleccionNivel= new Stage(vista);

        //actores botones...
        Button btnNivel1 = crearBoton("menuNiveles/button_nivel1.png", "menuNiveles/button_nivel1-2.png");
        btnNivel1.setPosition(ANCHO*.20F,3*ALTO/6, Align.center);
        //agregar boton a la escena
        escenaMenuSeleccionNivel.addActor(btnNivel1);
        btnNivel1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL1));
            }
        });

        Button btnNivel2 = crearBoton("menuNiveles/button_nivel2BLOCK.png", "menuNiveles/button_nivel2-2.png");
        btnNivel2.setPosition(ANCHO*.5F,3*ALTO/6,Align.center);
        escenaMenuSeleccionNivel.addActor(btnNivel2);
        btnNivel2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL2));
            }
        });

        Button btnNivel3 = crearBoton("menuNiveles/button_nivel3BLOCK.png", "menuNiveles/button_nivel3-2.png");
        btnNivel3.setPosition(ANCHO*.80F,3*ALTO/6,Align.center);
        escenaMenuSeleccionNivel.addActor(btnNivel3);
        btnNivel3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL3));
            }
        } );

        Button btnRegresar = crearBoton("configuracion/button_regresar.png", "configuracion/button_regresar.png");
        btnRegresar.setPosition(ANCHO/6,ALTO/6, Align.center);
        escenaMenuSeleccionNivel.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaMenuSeleccionNivel);

    }

    private void crearMenuNiveles1y2() {
        texturaFondo= new Texture("menuNiveles/fondoSeleccionNivel.png");

        escenaMenuSeleccionNivel= new Stage(vista);

        //actores botones...
        Button btnNivel1 = crearBoton("menuNiveles/button_nivel1.png", "menuNiveles/button_nivel1-2.png");
        btnNivel1.setPosition(ANCHO*.20F,3*ALTO/6, Align.center);
        //agregar boton a la escena
        escenaMenuSeleccionNivel.addActor(btnNivel1);
        btnNivel1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL1));
            }
        });

        Button btnNivel2 = crearBoton("menuNiveles/button_nivel2.png", "menuNiveles/button_nivel2-2.png");
        btnNivel2.setPosition(ANCHO*.5F,3*ALTO/6,Align.center);
        escenaMenuSeleccionNivel.addActor(btnNivel2);
        btnNivel2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL2));
            }
        });

        Button btnNivel3 = crearBoton("menuNiveles/button_nivel3BLOCK.png", "menuNiveles/button_nivel3-2.png");
        btnNivel3.setPosition(ANCHO*.80F,3*ALTO/6,Align.center);
        escenaMenuSeleccionNivel.addActor(btnNivel3);
        btnNivel3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL3));
            }
        } );

        Button btnRegresar = crearBoton("configuracion/button_regresar.png", "configuracion/button_regresar.png");
        btnRegresar.setPosition(ANCHO/6,ALTO/6, Align.center);
        escenaMenuSeleccionNivel.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaMenuSeleccionNivel);

    }

    private void crearMenuNiveles1y2y3() {
        texturaFondo= new Texture("menuNiveles/fondoSeleccionNivel.png");

        escenaMenuSeleccionNivel= new Stage(vista);

        //actores botones...
        Button btnNivel1 = crearBoton("menuNiveles/button_nivel1.png", "menuNiveles/button_nivel1-2.png");
        btnNivel1.setPosition(ANCHO*.20F,3*ALTO/6, Align.center);
        //agregar boton a la escena
        escenaMenuSeleccionNivel.addActor(btnNivel1);
        btnNivel1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL1));
            }
        });

        Button btnNivel2 = crearBoton("menuNiveles/button_nivel2.png", "menuNiveles/button_nivel2-2.png");
        btnNivel2.setPosition(ANCHO*.5F,3*ALTO/6,Align.center);
        escenaMenuSeleccionNivel.addActor(btnNivel2);
        btnNivel2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL2));
            }
        });

        Button btnNivel3 = crearBoton("menuNiveles/button_nivel3.png", "menuNiveles/button_nivel3-2.png");
        btnNivel3.setPosition(ANCHO*.80F,3*ALTO/6,Align.center);
        escenaMenuSeleccionNivel.addActor(btnNivel3);
        btnNivel3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaCargando(juego,Pantallas.NIVEL3));
            }
        } );

        Button btnRegresar = crearBoton("configuracion/button_regresar.png", "configuracion/button_regresar.png");
        btnRegresar.setPosition(ANCHO/6,ALTO/6, Align.center);
        escenaMenuSeleccionNivel.addActor(btnRegresar);
        btnRegresar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaMenuSeleccionNivel);

    }

    private Button crearBoton(String archivo, String archivoInverso) {
        Texture texturaBoton= new Texture(archivo);
        TextureRegionDrawable trdBtnMario =new TextureRegionDrawable(texturaBoton);

        //Inverso
        Texture texturaInverso= new Texture(archivoInverso);
        TextureRegionDrawable trdBtnInverso= new TextureRegionDrawable(texturaInverso);
        return new Button(trdBtnMario,trdBtnInverso);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(1,1,1);
        batch.setProjectionMatrix(camara.combined);

        //inicia dibujo
        batch.begin();

        batch.draw(texturaFondo,0,0);

        batch.end();

        escenaMenuSeleccionNivel.draw();
    }


    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }

    //este es un comentario
    // confirmacion del comentario
}
