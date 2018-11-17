package graficacion_lights;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/*
 * @author Brandon Ceja
 */
public class Graficacion_lights extends Applet implements KeyListener{
    //Window config
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas3D = new Canvas3D(config);
    //Universe config
    SimpleUniverse universo = new SimpleUniverse(canvas3D);
    //Scene config
    BranchGroup scene = new BranchGroup();
    //Transform groups
    TransformGroup GT[] = new TransformGroup[4];
    //Transforms
    Transform3D TR[] = new Transform3D[4]; 
    
    //Lights
    AmbientLight aLight = new AmbientLight(false, new Color3f(Color.blue));
    DirectionalLight dLight = new DirectionalLight(false, new Color3f(Color.red), new Vector3f(-1.0f, 0.0f, 0.0f));
    PointLight pLight = new PointLight(false, new Color3f(Color.yellow), new Point3f(0.0f, 0.0f, 0.0f), new Point3f(1.0f, 0.0f, 0.0f));
    SpotLight sLight = new SpotLight(false, new Color3f(Color.white), new Point3f(0.0f, 0.0f, 0.0f),  new Point3f(1.0f, 0.0f, 0.0f),
                            new Vector3f(10.0f, 0.0f, 0.0f), (float)Math.toRadians(80), 0.0f);
    
    //lights array
    Light lights[] = {aLight, dLight, pLight, sLight};
    
    //Lights states
    boolean flags[] = new boolean[4];
    
    public Graficacion_lights(){
        //Canvas configuration
        canvas3D.addKeyListener(this);
        setLayout(new BorderLayout());
        add("Center", canvas3D);
        //Transform init
        setTransformConfig();
        //Transform per sphere       
        for (int i = 0; i < GT.length; i++) {
            GT[i].setTransform(TR[i]);
            GT[i].addChild(new Sphere(0.2f, Sphere.GENERATE_NORMALS, createAppearance()));
            scene.addChild(GT[i]);
            
        }
        //lights config
        for (int i = 0; i < lights.length; i++) {
            lights[i].setInfluencingBounds(new BoundingSphere());
            lights[i].setCapability(Light.ALLOW_STATE_WRITE);
            scene.addChild(lights[i]);
        }

        //Orbit view
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere());
        universo.getViewingPlatform().setViewPlatformBehavior(orbit);
        
        //Adds view to SimpleUniverse
        universo.getViewingPlatform().setNominalViewingTransform();
        //Adds BranchGroup to SimpleUniverse
        universo.addBranchGraph(scene);
    }
    
    public Appearance createAppearance() {
        Appearance appear = new Appearance();
        Material material = new Material();
        appear.setMaterial(material);

        return appear;
    }
    
    public void setTransformConfig(){
        for (int i = 0; i < GT.length; i++) {
            GT[i] = new TransformGroup();
        }
        
        for (int i = 0; i < TR.length; i++) {
            TR[i] = new Transform3D();
        }
        
        TR[0].setTranslation(new Vector3f(-.5f,0.0f,0.0f));
        TR[1].setTranslation(new Vector3f(.5f,0.0f,0.0f));
        TR[2].setTranslation(new Vector3f(0.0f,.5f,0.0f));
        TR[3].setTranslation(new Vector3f(0.0f,-.5f,0.0f));
    } 

    @Override
    public void keyPressed(KeyEvent e) {
        // 49-52 = 1-4
        int key = e.getKeyCode();
        
        if(key > 48 && key < 53){
            key-=49;
            flags[key] =  !flags[key];
            lights[key].setEnable(flags[key]);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
     
    public static void main(String[] args) {
        //Window start
        Frame frame = new MainFrame(new Graficacion_lights(), 1200, 900);
        frame.setLocationRelativeTo(null);
    } 
}
