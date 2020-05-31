import javax.vecmath.*;

import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Map;


public class Boat extends JFrame{
    public Canvas3D myCanvas3D;
    static SimpleUniverse universe;
    static Scene scene;
    static Map<String, Shape3D> nameMap;
    static BranchGroup root;

    static TransformGroup wholeBoat;
    static Transform3D transform3D;

    public Boat(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);
        simpUniv.getViewingPlatform().setNominalViewingTransform();

        // set the geometry and transformations
        createSceneGraph(simpUniv);
        addLight(simpUniv);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("Fish");
        setSize(700,700);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }

    public void createSceneGraph(SimpleUniverse su){
        // loading object
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        BoundingSphere bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        String name;
        BranchGroup fishBranchGroup = new BranchGroup();
        TextureLoader t = new TextureLoader("source_folder//ocean.jpg", myCanvas3D);
        Background fishBackground =  new Background(t.getImage());

        Scene fishScene = null;
        try{
            fishScene = f.load("source_folder//cat_boat.obj");
        }
        catch (Exception e){
            System.out.println("File loading failed:" + e);
        }
//        Hashtable roachNamedObjects = fishScene.getNamedObjects();
        nameMap = fishScene.getNamedObjects();


        wholeBoat = new TransformGroup();

        transform3D = new Transform3D();
        transform3D.rotX(-Math.PI / 2);
        wholeBoat.setTransform(transform3D);
        transform3D.setTranslation(new Vector3f(0, -1.3f, 0));
        wholeBoat.setTransform(transform3D);

        transform3D.rotX(-Math.PI / 2);
        wholeBoat.setTransform(transform3D);

//        wholeBoat.addChild(nameMap.get("19292_cat_boat_v1"));
//        wholeBoat.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);


        // start animation
        Transform3D startTransformation = new Transform3D();
        startTransformation.rotX(-Math.PI / 2);
//        startTransformation.rotX(-Math.PI / 2);
        startTransformation.setScale(1.0/6);
        Transform3D combinedStartTransformation = new Transform3D();
//        combinedStartTransformation.rotY(-3*Math.PI/2);
//        combinedStartTransformation.rotY(-3*Math.PI/2);
        combinedStartTransformation.mul(startTransformation);

        TransformGroup fishStartTransformGroup = new TransformGroup(combinedStartTransformation);


        Appearance bodyApp = addAppearance();

        int movesCount = 100; // moves count
        int movesDuration = 500; // moves for 0,3 seconds
        int startTime = 0; // launch animation after timeStart seconds


        TransformGroup sceneGroup = new TransformGroup();


        TransformGroup tgBody = new TransformGroup();


        Shape3D fishBodyShape = (Shape3D) nameMap.get("19292_cat_boat_v1");
        fishBodyShape.setAppearance(bodyApp);
        tgBody.addChild(fishBodyShape.cloneTree());



        sceneGroup.addChild(tgBody.cloneTree());


        TransformGroup whiteTransXformGroup = translate(
                fishStartTransformGroup,
                new Vector3f(0.0f,0.0f,0.5f));

        TransformGroup whiteRotXformGroup = rotate(whiteTransXformGroup, new Alpha(10,5000));
        fishBranchGroup.addChild(whiteRotXformGroup);
        fishStartTransformGroup.addChild(sceneGroup);

        // adding the car background to branch group
        BoundingSphere bounds = new BoundingSphere(new Point3d(120.0,250.0,100.0),Double.MAX_VALUE);
        fishBackground.setApplicationBounds(bounds);
        fishBranchGroup.addChild(fishBackground);

        fishBranchGroup.compile();
        su.addBranchGraph(fishBranchGroup);
    }

    public static void setToMyDefaultAppearance(Appearance app, Color3f col) {
        app.setMaterial(new Material(col, col, col, col, 150.0f));
    }

    public void addLight(SimpleUniverse su){
        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Color3f lightColour1 = new Color3f(65/255f, 30/255f, 25/255f);
        Vector3f lightDir1 = new Vector3f(-1.0f,0.0f,-0.5f);
        DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        bgLight.addChild(light1);
        su.addBranchGraph(bgLight);
    }

    TransformGroup translate(Node node,Vector3f vector){

        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(vector);
        TransformGroup transformGroup =
                new TransformGroup();
        transformGroup.setTransform(transform3D);

        transformGroup.addChild(node);
        return transformGroup;
    }//end translate

    TransformGroup rotate(Node node,Alpha alpha){

        TransformGroup xformGroup = new TransformGroup();
        xformGroup.setCapability(
                TransformGroup.ALLOW_TRANSFORM_WRITE);

        //Create an interpolator for rotating the node.
        RotationInterpolator interpolator =
                new RotationInterpolator(alpha,xformGroup);

        //Establish the animation region for this
        // interpolator.
        interpolator.setSchedulingBounds(new BoundingSphere(
                new Point3d(0.0,0.0,0.0),1.0));

        //Populate the xform group.
        xformGroup.addChild(interpolator);
        xformGroup.addChild(node);

        return xformGroup;

    }//end rotate

    private Appearance addAppearance(){
        Appearance boatAppearance = new Appearance();
        boatAppearance.setTexture(getTexture("source_folder//paper.jpg"));
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.COMBINE);
        boatAppearance.setTextureAttributes(texAttr);
        boatAppearance.setMaterial(getMaterial());
        Shape3D plane = nameMap.get("19292_cat_boat_v1");
        plane.setAppearance(boatAppearance);
        return boatAppearance;
    }

    Texture getTexture(String path) {
        TextureLoader textureLoader = new TextureLoader(path,"LUMINANCE",myCanvas3D);
        Texture texture = textureLoader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
        return texture;
    }

    Material getMaterial() {
        Material material = new Material();
        material.setAmbientColor ( new Color3f( 0.9f, 0.9f, 0.9f) );
        material.setDiffuseColor ( new Color3f( 1f, 1f, 1f ) );
        material.setSpecularColor( new Color3f( 1f, 1f, 1f ) );
        material.setShininess( 0.3f );
        material.setLightingEnable(true);
        return material;
    }

    public static void main(String[] args) {
        Boat start = new Boat();
    }

}

