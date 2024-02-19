import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HippodromeTest {

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException with corresponding message if passed argument is null")
    void constructorShouldThrowIllegalArgumentExceptionIfArgIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException with corresponding message if passed list is empty")
    void constructorShouldThrowIllegalArgumentExceptionIfListIsEmpty() {
        List<Horse> emptyHorsesList = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyHorsesList));

        try {
            new Hippodrome(emptyHorsesList);
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    @Test
    @DisplayName("getHorses() should return the same list that was passed to the constructor")
    void getHorsesShouldReturnSameList() {
        List<Horse> originalHorses = createHorseList();

        Hippodrome hippodrome = new Hippodrome(originalHorses);

        List<Horse> returnedHorses = hippodrome.getHorses();

        assertEquals(originalHorses, returnedHorses);
    }

    @Test
    @DisplayName("move() should call move() method of all containing horses")
    void shouldCallMoveMethodOfAllHorses() {
        List<Horse> mockedHorses = createMockedHorseList();

        Hippodrome hippodrome = new Hippodrome(mockedHorses);
        hippodrome.move();

        mockedHorses.forEach(horse -> Mockito.verify(horse, Mockito.only()).move());
    }

    @Test
    @DisplayName("getWinner() should return the horse with the largest distance value")
    void getWinnerShouldReturnHorseWithLargestDistance() {
        List<Horse> horses = createHorseListWithDifferentDistances();

        Hippodrome hippodrome = new Hippodrome(horses);

        Horse returnedHorse = hippodrome.getWinner();

        Horse expectedHorse = horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get();

        assertEquals(expectedHorse, returnedHorse);
    }

    private List<Horse> createHorseListWithDifferentDistances() {
        List<Horse> horses = new ArrayList<>();
        Random random = new Random();
        double min = 1.0;
        double max = 50.0;
        for (int i = 0; i < 30; i++) {
            double distance = min + random.nextDouble() * (max - min);
            horses.add(new Horse("Horse " + i, i, distance));
        }
        return horses;
    }

    private List<Horse> createMockedHorseList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        return horses;
    }

    private List<Horse> createHorseList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse " + i, i, i));
        }
        return horses;
    }
}
