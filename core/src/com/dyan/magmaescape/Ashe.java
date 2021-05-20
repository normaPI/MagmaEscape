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
public class Ashe extends Objeto
{
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

    private float velocidadX = -350;  //pixeles/segundos
    private boolean potenLentitud=false;

    public Ashe(Texture textura, float x, float y, float vX) {
        velocidadX = vX;
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturas = region.split(73,118);

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

    //Mover al enemigo
    public void moverIzquierda(float delta) {
        if (potenLentitud){
            float dx = (velocidadX+80) * delta;
            sprite.setX(sprite.getX() + dx);


        }else {
            float dx = (velocidadX * delta);
            sprite.setX(sprite.getX() + dx);
        }

    }

    public boolean getpotenLentitud(){
        return potenLentitud;
    }
    public void setPotenLentitud(boolean pl){
        this.potenLentitud=pl;
    }

    public float getX() {
        return sprite.getX();
    }
}
