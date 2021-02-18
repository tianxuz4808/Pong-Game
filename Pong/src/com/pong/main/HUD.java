package com.pong.main;

import java.awt.*;

public class HUD {

    public void tick() {

    }
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(30f));
        g.drawString(Game.pOneScore + ":" + Game.pTwoScore, 15, 64);

    }
}
