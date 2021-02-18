package com.pong.main;

import java.awt.*;

public class Player extends GameObject{

    Handler handler;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

    }

    @Override
    public void tick() {

        y += velY;


        y = Game.clamp(y, 0, Game.HEIGHT - 96);
        //check for collision

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, 16, 64);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 64);
    }
}
