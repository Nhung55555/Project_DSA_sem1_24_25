package GameObj;

public class ModelGoBoom {

    double size;
    float angle;

    public ModelGoBoom(double size, float angle) {
        this.size = size;
        this.angle = angle;
    }
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }


}