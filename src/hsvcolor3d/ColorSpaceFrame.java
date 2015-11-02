package hsvcolor3d;
import java.awt.*;

import javax.swing.*;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

public class ColorSpaceFrame implements ActionListener{
	
	Button hsvBtn,hslBtn;
	Frame frame;
	Canvas3D canvas;
	JTextField jtf1;
	
	ColorSpaceFrame(){

	    
	   //Construct interface
	    frame = new Frame("CS5185 Multimedia Project");
	    frame.setBounds (100, 100, 600, 600);
	    frame.setLayout(new BorderLayout());
	    frame.add("North",new JLabel("<html>Color Space Model:<br/> Left Click To Rotate,Wheels To Zoom, Hold Right Click To Move. <br/>Click on HSV/HSL buttons to intiate model</html>"));
	    //Label modelLabel=new Label("Color Model");
	    hsvBtn = new Button("HSV");
	    hslBtn = new Button("HSL");
            
            
	    //The button which later on generate the Models
	    hsvBtn.addActionListener(this);
	    hslBtn.addActionListener(this);
	    JPanel bottomPanel= new JPanel();
	    JPanel innerBottomPanel= new JPanel();
            
	    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            
	    //This is just the dummy canvas
	    canvas = new Canvas3D(config);  
	    frame.add("Center",canvas);
	    bottomPanel.setLayout(new BorderLayout());
	    innerBottomPanel.setLayout(new BorderLayout());
	    Label sizeLabel = new Label("Sphere Size:");
	    jtf1 = new JTextField("12",15);
	    bottomPanel.add (hsvBtn, BorderLayout.EAST);
	    bottomPanel.add (hslBtn, BorderLayout.WEST);
	    
	    innerBottomPanel.add (sizeLabel, BorderLayout.CENTER);
	    innerBottomPanel.add (jtf1, BorderLayout.EAST);
	    bottomPanel.add(innerBottomPanel,BorderLayout.CENTER);
	    frame.add("South",bottomPanel);   
	    frame.addWindowListener
	    (new WindowAdapter()
	        {
	            public void windowClosing (WindowEvent e)
	            {
	                System.exit (0);
	            }
	        }
	    );
	    
	    
	    frame.setVisible(true);
	}

		
public void actionPerformed(ActionEvent event){
	Object source = event.getSource();
	float spheresize = 0.04f;
        
try{  
          
                        //Initial Canvas
			//First we remove the canvase,then draw on a new canvas
                        //Then load it at the end	
			frame.remove(canvas);
			BranchGroup c1 = new BranchGroup();
			GraphicsConfiguration config = 
			SimpleUniverse.getPreferredConfiguration();
			canvas = new Canvas3D(config);  
          
                        
                        int maxk=16;
                        
                        if (source == hsvBtn)
                        { maxk=32;}

		//Generating the points using RGB			    
		for (float i = 0; i <= 255; i += maxk){
			for (float j = 0; j <= 255; j+=maxk){
				for (float k = 0; k <= 255; k +=maxk){
					//Construct the points, using the RGB color 
				
                                    
                                        Appearance ap = new Appearance();
					spheresize=new Float(jtf1.getText())/300;
					Sphere sphere = new Sphere(spheresize);
					Color3f temColor = new Color3f(i/280,j/280,k/280);
                                        ColoringAttributes ca = new ColoringAttributes (temColor, ColoringAttributes.NICEST); 
					ap.setColoringAttributes(ca);
					sphere.setAppearance(ap);
                                        TransformGroup tg = new TransformGroup();
					Transform3D transform = new Transform3D();   
					ColorSpace coord= new ColorSpace (i,j, k);
                                        
                                    //HSV color model	
                                    if (source == hsvBtn)
                                    {
					//Move the point to the HSV location
					Vector3f vector = new Vector3f(coord.x(),coord.y(),coord.z()/160);
                                        transform.setTranslation(vector);
				
                                    }
                                    
                                    if(source == hslBtn)
                                    {		
                                    //Move the point to the HSL location
                                    Vector3f vector = new Vector3f(coord.x1(),coord.y1(),coord.z1()/160);
                                    transform.setTranslation(vector);
                                    }
                                    
                                    	
					tg.setTransform(transform);
					tg.addChild(sphere);
					c1.addChild(tg);
                                    
                                }
                        }
                } //end for
		
		  
		
                    
                    //Create the J3d Universe	
		    SimpleUniverse universe = new SimpleUniverse(canvas);
		    ViewingPlatform viewingPlatform = universe.getViewingPlatform();
		    OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
		    //Rotation center point
		    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 50.0);
		    orbit.setSchedulingBounds(bounds);
		    viewingPlatform.setViewPlatformBehavior(orbit);
		    universe.getViewingPlatform().setNominalViewingTransform();
		    universe.addBranchGraph(c1);
                    
                     //REload the drawn canvas
		    frame.add("Center",canvas);
		    frame.setVisible(false);
		    frame.setVisible(true);
        
                }
		catch(Exception e){
			System.out.println(e.getMessage());
                        System.out.println(e.getStackTrace());
		}

}




	
		


}//end class

