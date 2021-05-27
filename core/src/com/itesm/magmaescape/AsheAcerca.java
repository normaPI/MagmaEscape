package com.itesm.magmaescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
Norma Perez
Representa a los Ashes en la pantalla de acerca de
 */
public class AsheAcerca extends Objeto
{
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;


    public AsheAcerca(Texture textura, float x, float y,int cw,int ch) {
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(cw,ch);

        //Crear la animacion
        TextureRegion[] arrFrames = { texturas[0][0], texturas[0][1] };
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

