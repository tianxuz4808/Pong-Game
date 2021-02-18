package com.pong.main;


import java.awt.*;

public class BasicBall extends GameObject{

    Handler handler;

    public BasicBall(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = -5;
        velY = 5;
    }

    @Override
    public void tick() {

        x += velX;
        y += velY;

        if (y <=0 || y > Game.HEIGHT - 64) velY *= -1;

        didScore();
        collision();

    }

    public boolean didScore() {
        if (x <= 0) {
            Game.pTwoScore++;
            x = Game.WIDTH/2-32;
            y = Game.HEIGHT/2-32;
            velX = -5;
            return true;
        } else if (x > Game.WIDTH - 32) {
            Game.pOneScore++;
            x = Game.WIDTH/2-32;
            y = Game.HEIGHT/2-32;
            velX = 5;
            return true;
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 16, 16);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }

    public void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player || tempObject.getId() == ID.Player2) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    velX *= -1.025;
                }
            }
        }
    }
}
