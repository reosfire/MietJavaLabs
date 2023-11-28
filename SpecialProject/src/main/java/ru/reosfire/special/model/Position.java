package ru.reosfire.special.model;

public class Position {
    public final double x;
    public final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position updateZoom(Position pivot, Double factor) {
        Position coordinatesInPivot = this.minus(pivot);
        Position shift = coordinatesInPivot.times(factor).minus(coordinatesInPivot);
        return this.plus(shift);
    }

    public Position minus(Position other) {
        return new Position(this.x - other.x, this.y - other.y);
    }

    public Position plus(Position other) {
        return new Position(this.x + other.x, this.y + other.y);
    }

    public Position times(Double factor) {
        return new Position(this.x * factor, this.y * factor);
    }

    public Position times(int factor) {
        return new Position(this.x * factor, this.y * factor);
    }

    public double magnitude() {
        return x*x + y*y;
    }
    public double magnitude(Position other) {
        return this.minus(other).magnitude();
    }

    public double distance() {
        return Math.sqrt(magnitude());
    }
    public double distance(Position other) {
        return Math.sqrt(magnitude(other));
    }

    public Position normalize() {
        return this.times(1 / distance());
    }

    public Position perpendicular() {
        return new Position(y, -x);
    }

    public int getIntX() {
        return (int)Math.round(x);
    }
    public int getIntY() {
        return (int)Math.round(y);
    }
}
