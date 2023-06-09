public class Vector {
    public double x;
    public double y;

    public Vector() {
        this.set(0, 0);
    }

    public Vector(double x, double y) {
        this.set(x, y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //
    public void add(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    //scaling vector (shrink or stretching)
    public void multiply(double m) {
        this.x *= m;
        this.y *= m;
    }

    public double getLenght() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void setLength(double L) {
        double currentLength = this.getLenght();
        //if current length is , then current angle is undefined
        // asume current angle is 0 (pointing to the right)

        if (currentLength == 0) {
            this.set(L, 0);
            return;
        } else { // able to preserve current angle
            //scale vector to have Length 1
            this.multiply(1 / currentLength);
            //scale vector to have length L
            this.multiply(L);
        }
    }

    public double getAngle() {
        return Math.toDegrees(Math.atan2(this.y, this.x));
    }

    public void setAngle(double angleDegrees) {
        double length = this.getLenght();
        double angleRadians = Math.toRadians(angleDegrees);
        this.x = length * Math.cos(angleRadians);
        this.y = length * Math.sin(angleRadians);
    }
}
