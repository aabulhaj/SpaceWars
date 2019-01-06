package resources;

public abstract class Physics {
    protected double x;
    protected double y;
    protected double angle;

    public Physics(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public double distanceFrom(Physics other) {
        double dx = getDx(other);
        double dy = getDy(other);
        return Math.sqrt(dx * dx + dy * dy);
    }

    protected void loopCoord() {
        while (x > 1) {
            x -= 1;
        }
        while (x < 0) {
            x += 1;
        }
        while (y > 1) {
            y -= 1;
        }
        while (y < 0) {
            y += 1;
        }
    }


    protected static double correctDif(double num) {
        if (num > 0.5) {
            return num - 1;
        } else if (num < -0.5) {
            return 1 + num;
        } else {
            return num;
        }
    }

    protected double getDx(Physics other) {
        return (correctDif(x - other.x));
    }

    protected double getDy(Physics other) {
        return (correctDif(y - other.y));
    }
}
