import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    int b_height=400;
    int b_width=400;

    int max_dots=1600;
    int dot_size=10;
    int dots;
    int x[]=new int[max_dots];
    int y[]=new int[max_dots];
    int apple_x;
    int apple_y;
    Image body,head ,apple;
    Timer timer;
    int DELAY = 150;
    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;
    boolean inGame=true;

    Board(){
        TAdapter tAdapter=new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension( b_width,b_height));
        setBackground(Color.BLACK);
        initializeGame();
        loadImages();
    }
    public void initializeGame(){
        dots=3;
        x[0]=250;
        y[0]=250;
        for(int i=1;i<dots;i++)
        {
            x[i]=x[0]+dot_size*i;
            y[i]=y[0];
        }
        //intialize apple position
        locateapple();
        timer =new Timer(DELAY,this);
        timer.start();

    }
    //load images from resource folder to Image object
    public void loadImages(){
        ImageIcon bodyIcon=new ImageIcon("src/resources/dot.png");
        body=bodyIcon.getImage();
        ImageIcon headIcon=new ImageIcon("src/resources/head.png");
        head=headIcon.getImage();
        ImageIcon appleIcon=new ImageIcon("src/resources/apple.png");
        apple=appleIcon.getImage();
    }
    //draw images at snake and apple poistion
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    //draw the image
    public void doDrawing(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i=0;i<dots;i++)
            {
                if(i==0)
                {
                    g.drawImage(head,x[0],y[0],this);
                }
                else
                    g.drawImage(body,x[i],y[i],this);
            }
        }
        else {
            gameOver(g);
            timer.stop();
        }
    }
    //randomize apple poistion
    public void locateapple(){
        apple_x=((int)(Math.random()*39))*dot_size;
        apple_y=((int)(Math.random()*39))*dot_size;
    }
    //check colllision with body and border
    public void checkCollision(){
        for(int i=0;i<dots;i++)
        {
            //collision with body
            if(i>4 && x[i]==x[0]&& y[i]==y[0])
            {
                inGame=false;
            }
            //collision with border
        }
        if(x[0]<0)
        {
            inGame=false;
        }
        if(x[0]>=b_height)
        {
            inGame=false;
        }
        if(y[0]<0)
        {
            inGame=false;
        }
        if(x[0]>=b_width)
        {
            inGame=false;
        }
    }
    //gameover msg
    public void gameOver(Graphics g){
        String msg="Game Over";
        int score=(dots-3)*100;
        String Scoremsg="Score:"+Integer.toString(score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontmetrics=getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(b_width-fontmetrics.stringWidth(msg))/2,b_height/4);
        g.drawString(Scoremsg,(b_width-fontmetrics.stringWidth(Scoremsg))/2,3*(b_height/4));
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent){
       if(inGame)
       {
           checkApple();
           move();
           checkCollision();
       }
        repaint();
    }
    //make snake move
    public void move(){
        for(int i=dots-1;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection)
        {
            x[0]-=dot_size;
        }
        if(rightDirection)
        {
            x[0]+=dot_size;
        }
        if(upDirection)
        {
            y[0]-=dot_size;
        }
        if(downDirection)
        {
            y[0]+=dot_size;
        }
    }
    //make snake eat food
    private void checkApple(){
        if (apple_x == x[0] && apple_y == y[0]) {
           dots++;
           locateapple();
        }
    }

    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&(!rightDirection)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT&&(!leftDirection)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP&&(!downDirection)){
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN&&(!upDirection)){
                leftDirection=false;
                rightDirection=false;
                downDirection=true;
            }
        }

    }



}
