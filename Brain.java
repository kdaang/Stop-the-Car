import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Brain implements Runnable{
    static int x;
    static int backx; //move background
    static int level = 0; //counts the number of levels passed
    static int highestlevel = 0; //highest level achieved
    static int speed = 2600; //speed of the moving background
    static AtomicBoolean line = new AtomicBoolean(false);
                                    //know when to make a line
    static AtomicBoolean state = new AtomicBoolean(true); 
                                    //know when to stop making lines
    static int stoplinex = 367; //the range that the car is allowed
    static int stoplinex2 = 393;  // to stop (367 - 393)
    static AtomicInteger movelinex = new AtomicInteger(1500); //move the line
    static AtomicBoolean startgame = new AtomicBoolean(false);//know when to 
                                                         // start the game
    public void run(){
        try{
            while(true){
                if(startgame.get() == false){
                    for(x = 0; x >= -1011; x--){ //moves the background
                        if(startgame.get() == true){
                            break;
                        }
                        delay(2600);//delay by the speed set
                    }
                }
                if (startgame.get() == true){
                    background(); //run background
                }    
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void start(){
        Thread t = new Thread(this); //create thread
        t.start(); //start thread
    }
    public void stopline(){ //know when the player has failed to stop the car
        if(movelinex.get() < stoplinex){
            state.set(false);
        }
    }
    public void makeline(){ //makes the incoming lines
        if((line.get() == false)&&(state.get() == true)){
            movelinex.set(1500); 
            speed = speed - 50; //decrease time by 100 microseconds to move 
                                // quicker each level
            line.set(true);
        }
    }
    public void background(){
        for(backx = 0; backx >= -1011; backx--){ //moves the background
                                                //picture to the left
            makeline(); //check to see if a new line needs to be made
            if(state.get() == true){ //check to see if car has pass line
                    stopline();
                }
            if((line.get() == true)){
                movelinex.set(movelinex.get()-1);//move the line to the left
            }
            delay(speed);//delay by the speed set
            if(startgame.get() == false){
                break;
            }
        }
    }
    public void delay(int time){ //delay
        try {
            TimeUnit.MICROSECONDS.sleep(time);
        }catch(InterruptedException e){
            System.out.println(e);
        }
    }
}
