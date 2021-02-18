/*
 Source: https://www.youtube.com/watch?v=1gir2R7G9ws&list=PLWms45O3n--6TvZmtFHaCWRZwEqnz2MHa&ab_channel=RealTutsGML

 I used this playlist as an inspiration that helped me figure out the steps I needed to
 implement in order to create a Pong game.
 */


package com.pong.main;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public  static final int WIDTH = 640, HEIGHT = 480;
    public static int pOneScore = 0;
    public static int pTwoScore = 0;

    private Window window;
    private boolean running = false;

    private Thread thread;

    private Handler handler;
    private HUD hud;




    public Game() {

        handler = new Handler();

        this.addKeyListener(new KeyInput(handler));

        this.hud = new HUD();

        window = new Window(WIDTH, HEIGHT, "Pong", this);
        this.requestFocus();



        handler.addObject(new Player(0, HEIGHT/2-32, ID.Player, handler));
        handler.addObject(new Player(WIDTH - 32, HEIGHT/2-32, ID.Player2, handler));

        handler.addObject(new BasicBall(WIDTH/2-32, HEIGHT/2-32, ID.Ball, handler));


    }


    /*
        I had to look up the code that will update each frame
        of the Pong game. Everything else except this portion was
        based off of my learning from previous Java projects.
     */

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta =  0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();

    }
    public void tick() {
        handler.tick();
        hud.tick();

    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    public void render() {

        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }


        Graphics g = bs.getDrawGraphics(); //This creates a link btw graphics and the buffer strategy
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);
        hud.render(g);


        g.dispose(); //this gets rid of all of the current graphics and releases the memory
        bs.show();

    }

    public synchronized void stop() {

        try{
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        }
        else {
            return var;
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
