package com.dyan.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ave extends Objeto {

    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

    private float velocidadX = -330;  //pixeles/segundos

    public Ave(Texture textura, float x, float y) {
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(186,120);

        //Crear la animacion
        TextureRegion[] arrFrames = { texturas[0][0], texturas[1][0],texturas[2][0]};
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
    public void moverIzquierda(float delta) {
        float dx = velocidadX * delta;
        sprite.setX(sprite.getX() + dx);
    }
}
