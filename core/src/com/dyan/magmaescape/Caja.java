package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
Representa un obstáculo
 */
//Prueba de errores
public class Caja extends Objeto
{
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

    private float velocidadX = -300;  //pixeles/segundos

    public Caja(Texture textura, float x, float y) {
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(80,80);

        //Crear la animacion
        TextureRegion[] arrFrames = { texturas[0][0], texturas[0][1],texturas[0][2]};
        animacion = new Animation<TextureRegion>(0.4f, arrFrames);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;

        sprite = new Sprite(texturas[0][0]);
        sprite.setPosition(x, y);
    }

    @Override  //Sobreescribir el metodo
    public void render(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, sprite.getX(), sprite.getY());
    }

    //Mover al enemigo
    public void moverIzquierda(float delta) {
        float dx = velocidadX * delta;
        sprite.setX(sprite.getX() + dx);
    }
}
