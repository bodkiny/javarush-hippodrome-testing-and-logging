import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import static java.util.Objects.isNull;

@Log4j2
public class Horse {

    private final String name;
    private final double speed;
    private double distance;

    public Horse(String name, double speed, double distance) {
        if (isNull(name)) {
            LOGGER.error("Name is null");
            throw new IllegalArgumentException("Name cannot be null.");
        } else if (name.isBlank()) {
            LOGGER.error("Name is blank");
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (speed < 0) {
            LOGGER.error("Speed is negative");
            throw new IllegalArgumentException("Speed cannot be negative.");
        }
        if (distance < 0) {
            LOGGER.error("Distance is negative");
            throw new IllegalArgumentException("Distance cannot be negative.");
        }

        this.name = name;
        this.speed = speed;
        this.distance = distance;
        LOGGER.debug("Horse creation, name [{}], speed [{}]", name, speed);
    }

    public Horse(String name, double speed) {
        this(name, speed, 0);
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    public void move() {
        distance += speed * getRandomDouble(0.2, 0.9);
    }

    public static double getRandomDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
}
