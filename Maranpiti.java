import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
// Class that sets up the window for the game
public class Maranpiti
{
// make some constants for this class
private static final int WIDTH = 800;
private static final int HIEGHT =600;
public Maranpiti() {
// make the window, add components
JFrame mainFrame = new JFrame("Maranpiti");
GamePanel panel = new GamePanel(WIDTH, HIEGHT);
mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
mainFrame.setSize(WIDTH, HIEGHT);
mainFrame.add(panel);
mainFrame.addKeyListener(panel);
mainFrame.setResizable(false);
mainFrame.setVisible(true);
}
}
class StickMan
{
private int x, y;
private int w, h;
private int velocity = 5;
public StickMan(int w, int h)
{
this.w = w;
this.h = h;
x = 100;
y = 825;
}
// Methods for this class
// draw the StickMan
public void draw(Graphics g)
{
g.setColor(Color.white);
// the head
g.drawOval(x-10,y-340,20,20);
// the syntax of drawLine is (x1,y1,x2,y2);
// to draw a line from point (x1,y1) to (x2,y2)
// the body
g.drawLine(x,y-320,x,y-280);
// the hands
g.drawLine(x,y-300,x-20,y-300);
g.drawLine(x,y-300,x+20,y-325);
// the legs
g.drawLine(x,y-280,x-15,y-265);
g.drawLine(x,y-280,x+15,y-265);
}
// update data
public void update(boolean[] key)
{
if (key[0])
x -= velocity;
if (key[1])
x += velocity;
}
// getter method
public int getX()
{
return x;
}
// getter method
public int getY()
{
return y-280;
}
// setter method
public void setCoords(int x, int y)
{
this.x = x;
this.y = y-280;
}
// getter method
public int getW()
{
return w;
}
// getter method
public int getH()
{
return h;
}
}
// Class for ball of doom
class DoomBall
{
private int x, y, speed;
private int w, h;
public DoomBall(int w, int h, int x, int y, int speed)
{
this.w = w;
this.h = h;
this.x = x;
this.y = y;
this.speed = speed;
}
// Methods for this class
// draw the ball
public void draw(Graphics g)
{
g.setColor(Color.RED);
g.fillOval(x, y, w, h);
}
// update data
public void update()
{
y += speed;
}
//getters and setters
public int getX()
{
return x;
}
public int getY()
{
return y;
}
public int getW()
{
return w;
}
public int getH()
{
return h;
}
public void setCoords(int x, int y)
{
this.x = x;
this.y = y;
}
}
// Class for making the box that gives points
class GiftBox
{
private int x, y;
private int w, h;
public GiftBox(int w, int h, int x, int y)
{
this.w = w;
this.h = h;
this.x = x;
this.y = y;
}
//Methods for this class
// draw this
public void draw(Graphics g)
{
g.setColor(Color.BLUE);
g.fillRect(x, y, w, h);
}
// update this
public void update()
{
y += 1;
}
// Getters and setters
public int getX()
{
return x;
}
public int getY()
{
return y;
}
public int getW()
{
return w;
}
public int getH()
{
return h;
}
}
// Class that holds the game stuff, inherits JPanel and KeyListener
class GamePanel extends JPanel implements KeyListener, Runnable
{
/**
*
*/
private static final long serialVersionUID = 1L;
private int w, h;
private GiftBox gift;
private DoomBall[] ball;
private StickMan user;
private boolean[] keyPressed = new boolean[2];
private Random r;
private int score;
private static final int NUMBALLS = 3;
// Construct the panel
public GamePanel(int w, int h)
{
// intitialize variables
r = new Random();
score = 0;
this.w = w;
this.h = h;
// make the objects
gift = new GiftBox(50, 50, 100, 0);
ball = new DoomBall[NUMBALLS];
user = new StickMan(50, 50);
for (int i = 0; i < NUMBALLS; i++)
{
ball[i] = new DoomBall(50, 50, i * 100, 0, i + 2);
}
// set key input
keyPressed[0] = false;
keyPressed[1] = false;
// make new thread and begin
Thread t = new Thread(this);
t.start();
}
// method that runs the game
public void run()
{
// loop forever
for (;;)
{
// update the data and repaint the screen
this.update();
repaint();
// makes the game about 60 fps
try
{
Thread.sleep(16);
}
catch (InterruptedException e)
{
e.printStackTrace();
}
// check boundary for gift
if (this.isOutBounds(gift.getX(), gift.getY()))
{
gift = new GiftBox(50, 50, r.nextInt(400)+1, 0);
score--;
}
// check collisions for user and balls
for (int i = 0; i < NUMBALLS; i++)
{
if (this.isOutBounds(ball[i].getX(), ball[i].getY())) {
ball[i].setCoords(r.nextInt(400), 0);}
if (this.isTouchingC(ball[i].getX(), ball[i].getY(), user.getX(), user.getY(), ball[i].getW() / 2, user.getW() / 2))
{
ball[i].setCoords(r.nextInt(400), 0);
score -= 1;
}
}
// check collision for user and box
if (this.isTouchingCirSq(gift.getX(), gift.getY(),gift.getW(), user.getX(), user.getY(), user.getW()/2))
{
gift = new GiftBox(50, 50, r.nextInt(400) + 1, 0);
score++;
}
}
}
// Method that paint the stuff
public void paintComponent(Graphics g)
{
g.setColor(Color.black);
g.fillRect(0, 0, w, h);
g.setColor(Color.white);
g.drawString("Score: " + score, 0, 20);
for (int i = 0; i < NUMBALLS; i++)
ball[i].draw(g);
user.draw(g);
gift.draw(g);
}
// update all data
public void update()
{
user.update(keyPressed);
gift.update();
for (int i = 0; i < NUMBALLS; i++)
ball[i].update();
}
// checks if out of window
public boolean isOutBounds(int x, int y)
{
if (this.w < x || x < 0)
return true;
if (this.h < y || y < 0)
return true;
return false;
}
// collision check between two rectangles
public boolean isTouching(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2)
{
boolean intersectX = false;
boolean intersectY = false;
if ((x1 + w1) >= x2 && x2 >= x1)
intersectX = true;
if ((x2 + w2) >= x1 && x1 >= x2)
intersectX = true;
if ((y1 + h1) >= y2 && y2 >= y1)
intersectY = true;
if ((y2 + h2) >= y1 && y1 >= y2)
intersectY = true;
return intersectX && intersectY;
}
// Collision for circles
public boolean isTouchingC(int x1, int y1, int x2, int y2, int r1, int r2)
{
int sumR = r1 + r2;
int centX1 = r1 + x1;
int centY1 = r1 + y1;
int centX2 = r2 + x2;
int centY2 = r2 + y2;
int a = centX2 - centX1;
int b = centY2 - centY1;
double s = (a*a) + (b*b);
double d = Math.sqrt(s);
if (sumR >= d)
return true;
return false;
}
// Collision between circle and rectengles
public boolean isTouchingCirSq(int sX, int sY, int sW, int cX, int cY, int cR)
{
int sMaxX = sX + sW;
int sMaxY = sY + sW;
int cCentX = cX + cR;
int cCentY = cY + cR;
if (cCentX >= sX && sMaxX >= cCentX)
{
return (cCentY >= (sY - cR)) && (cCentY <= (sMaxY + cR));
}
if (cCentY >= sY && sMaxY >= cCentY)
{
return (cCentX >= (sX - cR)) && (cCentX <= (sMaxX + cR));
}
return distanceCalc(cCentX, cCentY, sX, sY) <= cR ||
distanceCalc(cCentX, cCentY, sX, sMaxY) <= cR ||
distanceCalc(cCentX, cCentY, sMaxX, sY) <= cR ||
distanceCalc(cCentX, cCentY, sMaxX, sMaxY) <= cR;
}
// get distance between points
public double distanceCalc(int x1, int y1, int x2, int y2)
{
double a = x2 - x1;
double b = y2 - y1;
return Math.sqrt(a*a + b*b);
}
// Key Listener method set pressed to true
public void keyPressed(KeyEvent e)
{
if (e.getKeyCode() == KeyEvent.VK_LEFT)
keyPressed[0] = true;
if (e.getKeyCode() == KeyEvent.VK_RIGHT)
keyPressed[1] = true;
}
// Key Listener method set pressed to false
public void keyReleased(KeyEvent e)
{
if (e.getKeyCode() == KeyEvent.VK_LEFT)
keyPressed[0] = false;
if (e.getKeyCode() == KeyEvent.VK_RIGHT)
keyPressed[1] = false;
}
// Key Listener method
public void keyTyped(KeyEvent e)
{
}
}
