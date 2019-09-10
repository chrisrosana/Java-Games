package TankGameMaster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class TankWorld extends JApplet implements Runnable{

    private Thread thread;
    static Image background, tank1, tank2, lifesLeft;
    static Image unBreakWall, breakWall, smallBullet, bigBullet; //refresh images
    static Image health, power; //Power Ups
    static Image win;   //end
    static Image minimap; //smallmap

    //image arrays
    static Image[] life = new Image[4];
    static Image[] explosion = new Image[7];
    static Image[] wallExplosion = new Image[6];
    private BufferedImage bimg, windowS, p1Windows, p2Window;
    Graphics2D g2;

    int GameSize = 1535, GameHeight = 1408;  //boundary of GameSize
    int p1X, p1Y;   //player 1 window
    int p2X, p2Y;   //player 2 window
    int time = 1;
    static Tank player1, player2;

    public InputStream LoadMap ;
    private Dimension miniImage;
    int w = 1300, h = 800; // Fixed Size window
    GameEvents gameEvent;
    static final TankWorld TANK_WORLD = new TankWorld();

    //Array
    public ArrayList<Wall> wall = new ArrayList<Wall>(), wallBreak = new ArrayList<Wall>();
    public ArrayList<bulletShot> bullet = new ArrayList<bulletShot>();
    public ArrayList<Explode> explosions = new ArrayList<Explode>();
    public ArrayList<powerUp> powerUp = new ArrayList<powerUp>();


    public void init()
    {
        setFocusable(true);

        //load map
        LoadMap = this.getClass().getClassLoader().getResourceAsStream("TankGameMaster/Resources/map.txt");
        try{

            //set the background Image of game
            background = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\Background.png"));

            //tank image
            tank1 = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\tank1.png"));
            tank2 = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\tank1.png"));

            //walls
            unBreakWall = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\redWall1.gif"));
            breakWall = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\redWall2.gif"));

            //bullet image
            smallBullet = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\bulletTest.png"));
            bigBullet = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\bigWeapon.png"));

            //Power Up image
            health = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\icon.png"));
            lifesLeft = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\lifesLeft.png"));
            power = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\Bouncing.png"));

            //get Explosion image array
            explosion[0] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode1.png"));
            explosion[1] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode2.png"));
            explosion[2] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode3.png"));
            explosion[3] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode4.png"));
            explosion[4] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode5.png"));
            explosion[5] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode6.png"));
            explosion[6] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode6.png"));

            //get Health image array
            life[3] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\life1.png"));
            life[2] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\life2.png"));
            life[1] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\life3.png"));
            life[0] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\life4.png"));

            //get wallExplosion image array
            wallExplosion[0] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode1.png"));
            wallExplosion[1] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode2.png"));
            wallExplosion[2] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode3.png"));
            wallExplosion[3] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode4.png"));
            wallExplosion[4] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode5.png"));
            wallExplosion[5] = ImageIO.read(new File("C:\\Users\\TEMP\\Desktop\\csc413-tankgame-angelomadarang\\TankGameMaster\\Resources\\explode6.png"));

            if(player1 == null)
                System.out.println("Player 1 is Ready!");
            if(player2 == null)
                System.out.println("Player 2 is Ready!");

        //keyboard controls(left,Right,Forward,Backward,Shoot) and speed of tank
        player1 = new Tank(tank1, 95, 64, 5, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W,KeyEvent.VK_S, KeyEvent.VK_SPACE);
        player2 = new Tank(tank2, 1250, 1300, 5, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP,KeyEvent.VK_DOWN, KeyEvent.VK_ENTER);
        gameEvent = new GameEvents();

        gameEvent.addObserver(player1);
        gameEvent.addObserver(player2);

        KeyControl key = new KeyControl();
        SoundPlayer.SoundPlayer( true, "TankGameMaster/Resources/recess intro 2.wav.wav");

        addKeyListener(key);
        MapLayout(); //load

        }
        catch(Exception e)
        {
            System.out.print(e.getStackTrace() +"No resources are found");
        }
    }

    //game instance for call/getter
    public static TankWorld getTankWorld()
    {
        return TANK_WORLD;
    }

    //return image of Small starting bullet
    public Image getSmallBullet()
    {
        return smallBullet;
    }
    //return image of upgraded bullet
    public Image getBigBullet()
    {
        return bigBullet;
    }
    //return image explosions array
    public Image[] getExplosions()
    {
        return explosion;
    }
    public Image[] getWallImageEx()
    {
        return wallExplosion;
    }

    //wall Array
    public ArrayList<Wall> getWall()
    {
        return wall;
    }
    //bullet Array
    public ArrayList<bulletShot> getBullet()
    {
        return bullet;
    }
    //explosion Array
    public ArrayList<Explode> getExplosion()
    {
        return explosions;
    }

    public void TileSize()
    {
        int TileW = background.getWidth(this);
        int TileH = background.getHeight(this);

        for (int i = -1; i <= 9; i++)
        {
            for (int j = 0; j <= 9; j++)
            {
                g2.drawImage(background, j * TileW,
                        i * TileH + (0 % TileH), TileW,
                        TileH, this);
            }
        }
    }

    public void drawDemo()
    {
        //Tile first
        TileSize();

        player1.draw(this, g2);
        player2.draw(this, g2);

        //update movement
        player1.MoveTank();
        player2.MoveTank();

        //draw the walls
        if (!wall.isEmpty())
        {
            //loop over size to draw
            for (int i = 0; i <= wall.size() - 1; i++)
            {
                wall.get(i).draw(this, g2);
            }
        }

        //draw explosions
        if (!explosions.isEmpty())
        {
            for (int i = 0; i <= explosions.size() - 1; i++)
            {
                if (explosions.get(i).boom())
                {
                    explosions.remove(i--);
                }
                else
                    {
                        explosions.get(i).draw(this, g2);
                    }
            }
        }

        //draw bullet
        if (!bullet.isEmpty())
        {
            for (int i = 0; i <= bullet.size() - 1; i++)
            {
                bullet.get(i).draw(this, g2);
                if (!bullet.get(i).flashing())
                {
                    bullet.remove(i);
                    i--;
                }
            }
        }

        //draw powerUp
        if (!powerUp.isEmpty())
        {
            for (int i = 0; i <= powerUp.size() - 1; i++)
            {
                //print only 1 at a time
                if (!powerUp.get(i).isShow())
                {
                    powerUp.remove(i--);
                }
                else
                {
                    powerUp.get(i).update();
                    powerUp.get(i).draw(this, g2);
                }
            }
        }

        //power up respond time
        if (time % 600 == 0)
        {
            //location of power up
            powerUp.add(new HealthUp(health, 355, 125, 0));
            powerUp.add(new bulletUp(power, 1152, 140, 0));
            powerUp.add(new bulletUp(power, 355, 1250, 0));
            powerUp.add(new HealthUp(health, 1152, 1250, 0));
        }

        //scale the minimap priority
        minimap = bimg.getScaledInstance(300, 300, Image.SCALE_FAST);

        //window
        windowS = (BufferedImage) createImage(miniImage.width, miniImage.height);
        g2 = windowS.createGraphics();

        PLayerWindow(); //print player window
        p1Windows = bimg.getSubimage(p1X, p1Y, miniImage.width / 2,
                miniImage.height);
        p2Window = bimg.getSubimage(p2X, p2Y, miniImage.width / 2,
                miniImage.height);

        g2.drawImage(p1Windows, 0, 0, this);
        g2.drawImage(p2Window, miniImage.width / 2, 0, this);


        //print out lifes left player 1
        if (player1.currentLife() != 0) {
            for (int i = 0; i < player1.currentLife(); i++)
            {
                g2.drawImage(lifesLeft, i * 32, miniImage.height - 20, this);
            }
            if (player1.getHealth() != 0)
            {
                g2.drawImage(life[player1.getHealth() - 1], 0,
                        miniImage.height - 10, this);
            }
        }


        //print out lifes left player 2
        if (player2.currentLife() != 0) {
            for (int i = 0; i < player2.currentLife(); i++)
            {
                g2.drawImage(lifesLeft, miniImage.width - (7 * lifesLeft.getWidth(null))
                                + ((i * health.getWidth(null))), miniImage.height - 20,
                        this);
            }
            if (player2.getHealth() != 0) {
                g2.drawImage(life[player2.getHealth() - 1],
                        miniImage.width - 120, miniImage.height - 10, this);
            }
        }

        //draw minimap
        g2.drawImage(minimap, miniImage.width / 2 - 100, 0, 200, 200, this);

        time++; //update increasing
    }


    public void paint(Graphics g)
    {
        bimg = (BufferedImage) createImage(GameSize,
                GameHeight);

        g2 = bimg.createGraphics();

        drawDemo();
        g.drawImage(windowS, 0, 0, this);
    }

    public void start()
    {
        System.out.println();
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }


    public void run()
    {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
        }
    }



    //create the mapLayout
    public void MapLayout()
    {
        BufferedReader temp = new BufferedReader(new InputStreamReader(LoadMap));
        String location;
        int count = 0;
        try{
            while((location = temp.readLine()) != null)
            {
                //print walls 1 = unBreakWall, 2 = breakWall
                for(int i = 0; i < location.length(); i++)
                {
                    if(location.charAt(i) == '1')
                    {
                        wall.add(new Wall(unBreakWall, (count % 48) * 32, count/ 48 * 32, false));
                    }
                    else if(location.charAt(i) == '2')
                    {
                        wall.add(new Wall(breakWall, (count % 48) * 32, count/ 48 * 32, true));
                    }
                    count++;
                }
            }
        }catch(Exception e){
            System.out.print(e.getStackTrace() +"Map Error");
        }
    }


    //window screen for players
    public void PLayerWindow()
    {
        //player1X
        if((p1X= (player1.CenterX()-(miniImage.width/4))) < 0)
        {
            p1X = 0;
        }
        else if(p1X>=GameSize-(miniImage.width/2))
        {
            p1X = GameSize-(miniImage.width/2);
        }
        //PLayer1Y
        if((p1Y = (player1.CenterY()-(miniImage.height/2))) < 0)
        {
            p1Y = 0;
        }
        else if(p1Y>=(GameHeight-(miniImage.height)))
        {
            p1Y = GameHeight-(miniImage.height);
        }
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        //player2X
        if((p2X= (player2.CenterX()-(miniImage.width/4))) < 0)
        {
            p2X = 0;
        }else if(p2X>=GameSize-(miniImage.width/2))
        {
            p2X = GameSize-(miniImage.width/2);
        }
        //player2Y
        if((p2Y = (player2.CenterY()-(miniImage.height/2))) < 0)
        {
            p2Y = 0;
        }
        else if(p2Y>=(GameHeight-(miniImage.height)))
        {
            p2Y = GameHeight-(miniImage.height);
        }
    }

    // keyboard layout
    public class KeyControl extends KeyAdapter
    {
        public void keyReleased(KeyEvent e)
        {
            gameEvent.setValue(e);
        }
        public void keyPressed(KeyEvent e)
        {
            gameEvent.setValue(e);
        }
    }

    public static void main(String argv[])
    {
        TANK_WORLD.init(); //run

        JFrame f = new JFrame("TankGame");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", TANK_WORLD);
        f.pack();
        f.setSize(new Dimension(TANK_WORLD.w, TANK_WORLD.h));
        f.setVisible(true);
        f.setResizable(false);
        TANK_WORLD.miniImage = f.getContentPane().getSize();
        TANK_WORLD.start();
    }

}