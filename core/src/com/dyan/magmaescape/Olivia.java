/*
Esta clase representa la clase del personaje Olivia(Personaje Principal)
Autor: Daniel Castañeda
*/

package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Olivia extends Objeto {
    private Animation<TextureRegion> animacionCorrer;
    private Animation<TextureRegion> animacionMorir;
    private float timerAnimacion;  // Para saber el que corresponde mostrar

    private Sound saltar;
    private final AssetManager managerr;





    // Salto
    private final float yBase = 180; // Suelo, piso
    // Aire
    private float tAire;            // Tiempo que lleva en el aire
    private float tVuelo;           // tiempo de vuelo total
    private float v0y = 250;  // Componente en y de la velocida
    private float g = 150;   // La gravedad:  Pixeles/ s^2

    private EstadoOlivia estado;


    public Olivia(Texture textura, float x, float y, float inicialY, float gravity){

        managerr = new AssetManager();
        managerr.load("musica/salto.mp3", Sound.class);
        managerr.finishLoading();
        saltar= managerr.get("musica/salto.mp3");

       //SOBREESCRIBIR LOS VALORES ORIGINALES
       v0y=inicialY;
       g = gravity;
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(80,220);

        //Cuadros para caminar
        TextureRegion[] arrFramesCaminar={ texturas[0][0],texturas[0][1],texturas[0][2],texturas[0][3],texturas[1][0], texturas[1][1],texturas[1][2]};
        animacionCorrer= new Animation<TextureRegion>(0.17f,arrFramesCaminar );
        animacionCorrer.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] arrFramesMorir={ texturas[0][2]};
        animacionMorir= new Animation<TextureRegion>(0.2f,arrFramesMorir );
        animacionMorir.setPlayMode(Animation.PlayMode.LOOP);

        timerAnimacion = 0;

        //IDLE
        sprite= new Sprite(texturas[0][2]);
        sprite.setPosition(x,y);

        estado = EstadoOlivia.CAMINADO;

    }

    //Reescribimos el metodo render
    public void render(SpriteBatch batch){
        float delta = Gdx.graphics.getDeltaTime();
        switch (estado){
            case CAMINADO:
                timerAnimacion+=delta;
                TextureRegion frame= animacionCorrer.getKeyFrame(timerAnimacion);
                batch.draw(frame,sprite.getX(),sprite.getY());
                break;
            case SALTANDO:
                actualizar();
                super.render(batch);
                break;

            case MURIENDO:
                timerAnimacion+=delta;
                TextureRegion frameMorir= animacionMorir.getKeyFrame(timerAnimacion);
                batch.draw(frameMorir,sprite.getX(),sprite.getY());
                break;

        }

    }

    // Calcula el movimiento vertical;
    private void actualizar() {
        float delta = Gdx.graphics.getDeltaTime();
        tAire += 2*delta;
        float y = yBase + v0y*tAire - 0.5f * g * tAire*tAire; // formula
        sprite.setY(y);

        // Como sabria que ya termino la simulacion
        if (tAire >= tVuelo || y <=yBase){
            estado = EstadoOlivia.CAMINADO;
            sprite.setY(yBase);
        }


    }

    public Sprite getSprite() {
        return sprite;
    }

    public void reproducirSalto()
    {

        saltar.play();
    }

    public void saltar() {
        if ( estado != EstadoOlivia.SALTANDO){
            reproducirSalto();
            tAire = 0;
            tVuelo =  2*v0y/ g;
            estado = EstadoOlivia.SALTANDO;
        }

    }


    public EstadoOlivia getEstado(){
        return estado;
    }

    public void setEstado(EstadoOlivia nuevoEstado){
        this.estado = nuevoEstado;
    }

}
