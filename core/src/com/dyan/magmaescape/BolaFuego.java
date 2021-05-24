package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
Representa un enemigo
 */
public class BolaFuego extends Objeto
{
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

    private float velocidadX = -500;  // pixeles/segundos
    private float velocidadY = -30;  // pixeles/segundos

    public BolaFuego(Texture textura, float x, float y) {
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(80,95);

        //Crear la animacion
        TextureRegion[] arrFrames = { texturas[0][0], texturas[0][1], texturas[0][2] };
        animacion = new Animation<TextureRegion>(0.3f, arrFrames);
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
    public void moverCaida(float delta) {
        float dx = velocidadX * delta;
        float dy = velocidadY * delta;
        sprite.setX(sprite.getX() + dx);
        sprite.setY(sprite.getY() + dy);

    }
}
