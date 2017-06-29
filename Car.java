import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;

public class Car extends JFrame{
    Brain b = new Brain(); //create an object
    Input b2 = new Input(); //create an object  
    static Font alpha;
    static Image title, desert, rcar, fline, wheels; 
    private Image dbImage;
    private Graphics dbGraphics;
    
    public Car(){ //create game window
        load();
        addKeyListener(new Input());
        setTitle("STOP THE CAR!");
        setSize(1011,500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } 
    public void paint(Graphics g){ //paint graphics
        dbImage = createImage(getWidth(), getHeight());
        dbGraphics = dbImage.getGraphics();
        paintComponent(dbGraphics);
        g.drawImage(dbImage, 0, 0, this);
    }
    public void paintComponent(Graphics g){
        if(b.startgame.get() == false){
            menu(g);
        }
        else{
            game(g);
        }
        repaint();
    }
    public void menu(Graphics g){ //menu screen
        //draw background
        g.drawImage(desert, b.x, 0, getWidth(), getHeight(), this);
        g.drawImage(desert, b.x+1011, 0, getWidth(), getHeight(), this);
        //draw title
        g.drawImage(title, 250, 60, this);
        //draw finish line
        g.drawImage(fline, 367, 385, this);
        g.drawImage(fline, 713, 385, this);
        //draw car
        g.drawImage(rcar, 400, 230, this);
        //instructions
        g.setFont(alpha.deriveFont(Font.PLAIN, 25));
        g.setColor(Color.BLUE);
        g.drawString("Click space when the car is between", 290, 185);
        g.drawString(" the lines to get to the next level!", 290, 215);
        g.drawString("Click \"Enter\" to start!", 380, 245);
        g.setColor(Color.BLACK);
        g.drawString("By: Kevin Dang", 570, 275);
    }
    public void game(Graphics g){ // game screen
        //draw background
        g.drawImage(desert, b.backx, 0, getWidth(), getHeight(), this);
        g.drawImage(desert, b.backx+1011, 0, getWidth(), getHeight(), this);
        //draw finish line
        g.drawImage(fline, b.movelinex.get(),385, this);
        g.drawImage(fline, b.movelinex.get() + 320,385, this);
        //draw car
        g.drawImage(rcar, 400, 230, this);
        //draw wheels and rotate it
        Graphics2D g2 = (Graphics2D)g.create();
        AffineTransform oldTransform = g2.getTransform();
        g2.rotate(Math.toRadians(-b.backx),(wheels.getWidth(this)/2)+466,
                (wheels.getHeight(this)/2)+395);
        g2.drawImage(wheels, 466, 395, this);
        g2.setTransform(oldTransform);
        g2.rotate(Math.toRadians(-b.backx),(wheels.getWidth(this)/2)+608,
                (wheels.getHeight(this)/2)+399);
        g2.drawImage(wheels, 608, 399, this);
        //display the level    
        g.setColor(Color.BLACK);
        g.setFont(alpha.deriveFont(Font.PLAIN, 30));
        g.drawString("Level: " + b.level, 5, 50);
        g.drawString("Highest Level: " + b.highestlevel, 5, 80);
        //displays when player loses
        if(b.state.get() == false){
            g.setColor(Color.RED);
            g.setFont(alpha.deriveFont(Font.PLAIN, 30));
            g.drawString("YOU LOST :(", 175, 200);
            g.setFont(alpha.deriveFont(Font.PLAIN, 25));
            g.drawString("click \"r\" to play again ", 175, 230);
            g.drawString("click \"m\" for instructions ", 175, 260);
        }
    }
    public void load (){
        //get font
        try{
            InputStream LOAD_FONT = getClass().getClassLoader()
                    .getResourceAsStream("car/alpha_echo.ttf");
            alpha = Font.createFont(Font.TRUETYPE_FONT, LOAD_FONT);
        }catch(FontFormatException|IOException e){
            System.out.println(e);
        } 
        //get images
        title = new ImageIcon(getClass().getClassLoader()
                .getResource("car/title.gif")).getImage();
        desert = new ImageIcon(getClass().getClassLoader()
                .getResource("car/desert.gif")).getImage();
        rcar = new ImageIcon(getClass().getClassLoader()
                .getResource("car/car.gif")).getImage(); 
        fline = new ImageIcon(getClass().getClassLoader()
                .getResource("car/fline.gif")).getImage();
        wheels = new ImageIcon(getClass().getClassLoader()
                .getResource("car/wheels.gif")).getImage();
    }
    public static void main(String[] args) { 
        Car c = new Car(); //create an object
        Brain b = new Brain();// create an object
        b.start(); //start thread in class Brain
    }
}
