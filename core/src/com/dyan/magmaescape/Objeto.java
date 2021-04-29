/*
Esta clase representa el objeto para que clases que hereden, pueden acceder a la variable
Autor: Daniel Castañeda
*/

package com.dyan.magmaescape;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Objeto {

    protected Sprite sprite;


    public Objeto(Texture textura, float x, float y){
        sprite=new Sprite(textura);
        sprite.setPosition(x,y);


    }

    public Objeto(){

    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }
}
