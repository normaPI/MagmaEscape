package com.itesm.magmaescape;
/*
Esta clase es para la animacion de Olivia y Ezra al final del nivel3
Autor: Norma P Iturbide
*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OliviaEzra extends Objeto {

    private Animation<TextureRegion> animacion;
    private float timerAnimacion;


    public OliviaEzra(Texture textura, float x, float y) {
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(270,420);

        //Crear la animacion
        TextureRegion[] arrFrames = { texturas[0][0], texturas[0][1]};
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

}
