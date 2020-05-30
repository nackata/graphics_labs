import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.media.j3d.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.*;

public class AnimationBoat implements ActionListener {
    private Button go;
    private TransformGroup wholePlane;
    private Transform3D translateTransform;
    private Transform3D rotateTransformX;
    private Transform3D rotateTransformY;
    private Transform3D rotateTransformZ;
    
    private JFrame mainFrame;
    
    private float sign=1.0f;
    private float zoom=1.0f;
    private float xloc=0.0f;
    private float yloc=-1.3f;
    private float zloc=0.0f;
    private int moveType=1;
    private Timer timer;
    
    public AnimationBoat(TransformGroup wholeBoat, Transform3D trans, JFrame frame){
        go = new Button("Go");
        this.wholePlane=wholeBoat;
        this.translateTransform=trans;
        this.mainFrame=frame;
        
        rotateTransformX= new Transform3D();
        rotateTransformY= new Transform3D();
        rotateTransformZ= new Transform3D();

        timer = new Timer(100, this);
        
        Panel p =new Panel();
        p.add(go);
        mainFrame.add("North",p);
        go.addActionListener(this);
    }

    private void initialBoteState(){
        xloc=0.0f;
        yloc=0.0f;
        zloc=0.0f;
        zoom=1.0f;
        moveType=1;
        sign=1.0f;
        if(timer.isRunning()){timer.stop();}
        go.setLabel("Go");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // start timer when button is pressed
       if (e.getSource()==go){
          if (!timer.isRunning()) {
             timer.start();
             go.setLabel("Stop");
          }
          else {
              timer.stop();
              go.setLabel("Go");
          }
       }
       else {
           Move(moveType);
           translateTransform.setScale(new Vector3d(zoom, zoom, zoom));
           translateTransform.setTranslation(new Vector3f(xloc,yloc,zloc));
           wholePlane.setTransform(translateTransform);
       }
    }

    private void Move(int mType){
        if(mType==1){
           xloc += 0.1 * sign;
           if (Math.abs(xloc *2) >= 2 ) {
               sign = -1.0f * sign;
               rotateTransformZ.rotZ(Math.PI);
               translateTransform.mul(rotateTransformZ);
           }
        }
        if(mType == 2){
          if(zoom < 0.03){
              initialBoteState();
          }
          else{
           xloc += 0.04;
           yloc += 0.003;
           zloc -= 0.005;
           zoom -= 0.005;
          }
        }
    }
}
