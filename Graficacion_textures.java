package graficacion_textures;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

/*
 * @author Brandon Ceja
 */
public class Graficacion_textures extends Applet implements Runnable{
    
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas3D = new Canvas3D(config);

    //Universe config
    SimpleUniverse universo = new SimpleUniverse(canvas3D);
    //Scene config
    BranchGroup scene = new BranchGroup();
    //Transform groups
    TransformGroup GT = new TransformGroup();
    TransformGroup mGT = new TransformGroup();
    //Transforms
    Transform3D TR = new Transform3D(); 
    Transform3D mTR = new Transform3D();
    Transform3D mRot = new Transform3D();
    
    //Rotations
    double Y = 0;
    double X = 0;
    Thread t1 = new Thread(this);
    
    //Traslation
    double tetha = 0;
    Thread t2 = new Thread(this);
    
    boolean trans;
    
    public Graficacion_textures(){
         //Canvas configuration
        setLayout(new BorderLayout());
        add("Center", canvas3D);
        
        GT.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        mGT.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        
      
        
        t1.start();
        t2.start();
        
        TextureLoader loader = new TextureLoader("C:\\Users\\Brandon Ceja\\Pictures\\texture.jpg", this);
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        
        TextureAttributes textAttr = new TextureAttributes();
        textAttr.setTextureMode(TextureAttributes.MODULATE);
        
        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(textAttr);
        ap.setMaterial(new Material());
        
         TextureLoader mloader = new TextureLoader("C:\\Users\\Brandon Ceja\\Pictures\\moon.png", this);
        Texture mtexture = mloader.getTexture();
        mtexture.setBoundaryModeS(Texture.WRAP);
        mtexture.setBoundaryModeT(Texture.WRAP);
        
        TextureAttributes mtextAttr = new TextureAttributes();
        mtextAttr.setTextureMode(TextureAttributes.MODULATE);
        
        Appearance map = new Appearance();
        map.setTexture(mtexture);
        map.setTextureAttributes(mtextAttr);
        map.setMaterial(new Material());
      
        
        AmbientLight aLight = new AmbientLight();
        DirectionalLight dLight = new DirectionalLight(new Color3f(Color.white), new Vector3f(0.0f, 0.0f, -1.0f));
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        Sphere sphere = new Sphere(0.4f, primflags, 130, ap);
        GT.addChild(sphere);
        scene.addChild(GT);
        
        
        int mprimflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        Sphere msphere = new Sphere(0.1f, mprimflags, 130, map);
        mGT.addChild(msphere);
        scene.addChild(mGT);
        
        
        aLight.setInfluencingBounds(new BoundingSphere());
        dLight.setInfluencingBounds(new BoundingSphere());
        
        
        
        
        scene.addChild(aLight);
        scene.addChild(dLight);
   
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere());
        universo.getViewingPlatform().setViewPlatformBehavior(orbit);
        
        //Adds view to SimpleUniverse
        universo.getViewingPlatform().setNominalViewingTransform();
        //Adds BranchGroup to SimpleUniverse
        universo.addBranchGraph(scene);
    }
    
    public static void main(String[] args) {
        Frame frame = new MainFrame(new Graficacion_textures(), 1200, 900);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void run() {
        while(Thread.currentThread() == t1){
           try {
               Y = Y + 0.05;
               TR.rotY(Y);
               GT.setTransform(TR);
               Thread.sleep(50);
            } catch (InterruptedException ex) {
               Logger.getLogger(Graficacion_textures.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
        while(Thread.currentThread() == t2){
         try {
               tetha = tetha - 0.05;
               X = (!trans) ? X + 0.01 : X - 0.01;  
               
               if(X > 0.50) trans = true;
               else if(X < -0.50) trans = false;
             
               mTR.setTranslation(new Vector3f(0.6f, (float)X, 0.0f));
               mRot.rotY(tetha);

               mRot.mul(mTR);
               mGT.setTransform(mRot);
               Thread.sleep(50);
            } catch (InterruptedException ex) {
               Logger.getLogger(Graficacion_textures.class.getName()).log(Level.SEVERE, null, ex);
           }  
       }
    }
}
