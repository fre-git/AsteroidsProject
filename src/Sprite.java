import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    public Vector position;
    public Vector velocity;
    public double rotation;
    public Rectangle boundary;
    public Image image;
    public double elapsedTime; // seconds

    public Sprite(){
        this.position = new Vector();
        this.velocity = new Vector();
        this.rotation = 0;
        this.boundary = new Rectangle();
        this.elapsedTime = 0;
    }

    public Sprite(String imageFileName){
        this();
        setImage(imageFileName);
    }

    public Sprite(String imageFilename, double scale){
        this();
        setImage(imageFilename, scale);
    }

    public Sprite(Image image){
        this();
        setImage(image);
    }


    public void setImage(String imageFileName){
        this.image = new Image(imageFileName);
        this.boundary.setSize(this.image.getWidth(), this.image.getHeight());
    }

    public void setImage(String imageFileName, double rescale){
        this.image = new Image(imageFileName, rescale, rescale, false, false);
        this.boundary.setSize(this.image.getWidth(), this.image.getHeight());
    }

    public void setImage(Image image){
        this.image = image;
        this.boundary.setSize(this.image.getWidth(), this.image.getHeight());
    }
    


    public Rectangle getBoundary(){
        //first update boundary just in case then returns it
        this.boundary.setPosition(this.position.x, this.position.y);
        return this.boundary;
    }

    public boolean overlaps(Sprite other){
        return this.getBoundary().overlaps(other.getBoundary());
    }

    public void wrap(double screenWidth, double screenHeight){

        double halfWidth = this.image.getWidth()/2;
        double halfheight = this.image.getHeight()/2;

        if(this.position.x + halfWidth < 0)
            this.position.x = screenWidth + halfWidth;
        if(this.position.x > screenWidth + halfWidth)
            this.position.x = -halfWidth;
        if(this.position.y + halfheight < 0)
            this.position.y = screenHeight +halfheight;
        /* SPECIAL EFFECTS
        if(this.position.y / this.image.getHeight() < 0)
            this.position.y = screenHeight;
         */
        if(this.position.y > screenHeight +halfheight)
            this.position.y = -halfheight;
    }

    public void update(double deltaTime){
        // increase elapsed time for this sprite
        this.elapsedTime += deltaTime;
        //update position acoording to velocity
        this.position.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        // wrap around screen
        this.wrap(800, 600);
    }



    public void render(GraphicsContext context){
        context.save();

        context.translate(this.position.x, this.position.y);
        context.rotate(this.rotation);
        //get the center of the image
        context.translate(-this.image.getWidth()/2, - this.image.getHeight()/2);
        context.drawImage(this.image, 0,0);

        context.restore();

    }

}
