package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        // Create a new instance of the BasicSquare class
        Square square = new BasicSquare();

        // Occupy the square with the unit
        unit.occupy(square);

        // Assert that the unit's square is equal to the occupied square
        assertThat(unit.getSquare()).isEqualTo(square);

        // Assert that the square contains the unit as an occupant
        assertThat(square.getOccupants()).contains(unit);
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
    	Square square = new BasicSquare();
    	unit.occupy(square);
    	
    	assertThat(unit.getSquare()).isEqualTo(square);
    	assertThat(square.getOccupants()).contains(unit);
    }

    /**
     * Tests that a unit can reoccupy a square that it already occupies.
     */
    @Test
    void testReoccupy() {
        Square square = new BasicSquare();
        unit.occupy(square);
        unit.occupy(square);

        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).contains(unit);
        assertThat(square.getOccupants()).hasSize(1);
    }

}
