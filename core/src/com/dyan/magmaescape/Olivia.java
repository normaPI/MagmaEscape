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


    public Olivia(Texture textura, float x, float y){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(407,400);

        //Cuadros para caminar
        TextureRegion[] arrFramesCaminar={ texturas[0][0], texturas[0][1],texturas[1][0],texturas[1][1], texturas[2][0],texturas[2][1]};
        animacionCorrer= new Animation<TextureRegion>(0.2f,arrFramesCaminar );
        animacionCorrer.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;

        //IDLE
        sprite= new Sprite(texturas[0][0]);
        sprite.setPosition(x,y);



    }
    //Reescribimos el metodo render
    public void render(SpriteBatch batch){
        float delta= Gdx.graphics.getDeltaTime();
        timerAnimacion+=delta;
        TextureRegion frame= animacionCorrer.getKeyFrame(timerAnimacion);
        batch.draw(frame,sprite.getX(),sprite.getY());

    }

}
