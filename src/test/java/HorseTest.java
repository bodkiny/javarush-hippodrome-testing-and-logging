import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException with corresponding message if the first arg of constructor is null")
    void shouldThrowIllegalArgumentExceptionIfFirstArgOfConstructorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0.0, 0.0));

        try {
            new Horse(null, 0.0, 0.0);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\n", "\t"})
    @DisplayName("Should throw IllegalArgumentException with corresponding message if the first arg is blank string")
    void shouldThrowIllegalArgumentExceptionIfFirstArgOfConstructorIsBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(arg, 1.0, 2.0));

        try {
            new Horse(arg, 5.0, 2.0);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("randomNegativeDoubles")
    @DisplayName("Should throw IllegalArgumentException with corresponding message if the second arg is negative")
    void shouldThrowIllegalArgumentExceptionIfSecondArgumentIsNegative(double arg) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name", arg, 2.0));

        try {
            new Horse("Name", arg, 2.0);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("randomNegativeDoubles")
    @DisplayName("Should throw IllegalArgumentException with corresponding message if the third arg is negative")
    void shouldThrowIllegalArgumentExceptionIfThirdArgumentIsNegative(double arg) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name", 5.0, arg));

        try {
            new Horse("Name", 5.0, arg);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"FirstName", "SOmeName", "Second Name", "123", "Third name As Well"})
    @DisplayName("getName() should return the first argument passed to the constructor")
    void nameGetterShouldReturnTheSameValueThatWasPassedToConstructor(String name) {
        Horse horse = new Horse(name, 4.75, 3.45);

        assertEquals(name, horse.getName());
    }

    @ParameterizedTest
    @MethodSource("randomPositiveDoubles")
    @DisplayName("getSpeed() should return the second argument passed to the constructor")
    void speedGetterShouldReturnTheSameValueThatWasPassedToConstructor(double speed) {
        Horse horse = new Horse("Name", speed, 4.2);

        assertEquals(speed, horse.getSpeed());
    }

    @ParameterizedTest
    @MethodSource("randomPositiveDoubles")
    @DisplayName("getDistance() should return third argument passed to the constructor")
    void distanceGetterShouldReturnTheSameValueThatWasPassedToConstructor(double distance) {
        Horse horse = new Horse("Name", 22.4, distance);

        assertEquals(distance, horse.getDistance());
    }

    @Test
    @DisplayName("getDistance() should return 0 if Horse object was instantiated with two arg constructor")
    void distanceGetterShouldReturnZero() {
        Horse horse = new Horse("Name", 37.5);

        assertEquals(0, horse.getDistance());
    }

    @Test
    @DisplayName("move() should call getRandomDouble() with arguments 0.2 and 0.9")
    void moveMethodShouldCallRandomDouble() {
        Horse horse = new Horse("Name", 37.5);

        try (MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)) {
            horse.move();

            horseMock.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9, 0.75, 0.8, 0.3})
    void moveMethodShouldCalculateDistanceCorrectly(double randomValue) {
        double initialSpeed = 10.0;
        double initialDistance = 5.0;
        Horse horse = new Horse("Name", initialSpeed, initialDistance);

        try (MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)) {
            horseMock.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            horse.move();

            double expectedDistance = initialDistance + initialSpeed * randomValue;
            assertEquals(expectedDistance, horse.getDistance());
        }
    }

    static Stream<Double> randomPositiveDoubles() {
        return new Random()
                .doubles(30, 0.1, 50)
                .boxed();
    }

    static Stream<Double> randomNegativeDoubles() {
        return new Random()
                .doubles(30, -50, 0)
                .boxed();
    }
}
