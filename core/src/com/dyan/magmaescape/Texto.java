package com.dyan.magmaescape;

// Created by Norma P Iturbide 11/04/2021

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Texto {
    private BitmapFont font;

    public Texto(String archivoo){
        font = new BitmapFont(Gdx.files.internal(archivoo));
        font.setColor(1,1,1,1);

    }

    public void mostrarMensaje(SpriteBatch batch, String mensaje, float x, float y){
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, mensaje);
        float anchoTexto = glyph.width;
        font.draw(batch, glyph, x-anchoTexto/2, y);


    }
}