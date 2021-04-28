/*
Esta clase representa la clase del personaje Olivia(Personaje Principal)
Autor: Daniel Castañeda
*/

package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Olivia extends Objeto {
    private Animation<TextureRegion> animacionCorrer;
    private float timerAnimacion;  //Para saber el que corresponder mostar

    // Salto
    private final float yBase = 180; // Suelo, piso
    // Aire
    private float tAire;            // Tiempo que lleva en el aire
    private float tVuelo;           // tiempo de vuelo total
    private final float v0y = 250;  // Componente en y de la velocida
    private final float g = 150;   // La gravedad:  Pixeles/ s^2

    private EstadoOlivia estado;


    public Olivia(Texture textura, float x, float y){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(127,275);

        //Cuadros para caminar
        TextureRegion[] arrFramesCaminar={ texturas[0][0], texturas[0][1],texturas[0][2],texturas[1][0], texturas[1][1],texturas[1][2]};
        animacionCorrer= new Animation<TextureRegion>(0.2f,arrFramesCaminar );
        animacionCorrer.setPlayMode(Animation.PlayMode.LOOP);
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

    public void saltar() {
        if ( estado != EstadoOlivia.SALTANDO){
            tAire = 0;
            tVuelo = 2 * v0y/ g;
            estado = EstadoOlivia.SALTANDO;

        }

    }

}
