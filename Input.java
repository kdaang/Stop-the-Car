import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input extends KeyAdapter{
    Brain b = new Brain(); //create an object
    
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode(); // gets the key pressed
        if(keyCode == e.VK_M){ 
            b.startgame.set(false); // brings up menu screen
        }
        if(keyCode == e.VK_ENTER){ //brings up play screen
            reset();
            b.startgame.set(true);
        }
        if (keyCode == e.VK_SPACE){ 
            if((b.movelinex.get() >= b.stoplinex) && //checks to see if car is 
                (b.movelinex.get() <= b.stoplinex2)){//inbetween the boundary
                b.line.set(false);//reset line so 
                b.state.set(true);//it can be made again
                b.level++;        // increase level
                if(b.highestlevel < b.level){
                    b.highestlevel = b.level;
                }
            }
        }//resets if called
        if ((keyCode == e.VK_R) && (b.state.get() == false)){
            reset();
        }
    }
    public void reset(){ //resets game
        b.line.set(false);
        b.state.set(true);
        b.speed = 2600;
        b.level = 0;
        b.movelinex.set(1500);
    }
}
