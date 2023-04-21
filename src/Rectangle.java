public class Rectangle {

    // This is for collision detection

    // (x,y) represents top-left corner of Rectangle
    double x;
    double y;
    double width;
    double heigth;

    public Rectangle(){
        this.setPosition(0,0);
        this.setSize(1,1);
    }

    public Rectangle(double x, double y, double w, double h){
        this.setPosition(x,y);
        this.setSize(w,h);
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setSize(double w, double h){
        this.width = w;
        this.heigth = h;
    }

    public boolean overlaps(Rectangle other){
        //Four cases where there is no overlap:
        //1: this to the left of other
        //2: this to the right of other
        //3: this above other
        //4: other is above this
        boolean noOverlap = this.x + this.width < other.x ||
                other.x + other.width < this.x ||
                this.y + this.heigth < other.y ||
                other.y + other.heigth < this.y;
        return !noOverlap;

    }
}
