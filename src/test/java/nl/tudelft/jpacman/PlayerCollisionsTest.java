package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.BasicUnit;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerCollisions;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Created By IntelliJ IDEA
 *
 * @author IceCreamQAQ
 * @datetime 2023/4/25 星期二
 * Happy Every Coding Time~
 */
public class PlayerCollisionsTest {

    private Player player;
    private Ghost ghost;
    private Pellet pellet;
    private Unit unit;
    private PointCalculator pointCalculator;
    private PlayerCollisions playerCollisions;

    @BeforeEach
    public void setUp() {
        player = mock(Player.class);
        ghost = mock(Ghost.class);
        pellet = mock(Pellet.class);
        pointCalculator = mock(PointCalculator.class);
        playerCollisions = new PlayerCollisions(pointCalculator);
        unit = mock(BasicUnit.class);
    }

    @Test
    @Order(1)
    @DisplayName("Player与Ghost碰撞")
    public void testPlayerCollidingWithGhost() {
        playerCollisions.collide(player, ghost);

        verify(pointCalculator).collidedWithAGhost(player, ghost);
        verify(player).setAlive(false);
        verify(player).setKiller(ghost);
    }

    @Test
    @Order(2)
    @DisplayName("Player与Pellet碰撞")
    public void testPlayerCollidingWithPellet() {
        playerCollisions.collide(player, pellet);

        verify(pointCalculator).consumedAPellet(player, pellet);
        verify(pellet).leaveSquare();
    }

    @Test
    @Order(3)
    @DisplayName("Ghost与Player碰撞")
    public void testGhostCollidingWithPlayer() {
        playerCollisions.collide(ghost, player);

        verify(pointCalculator).collidedWithAGhost(player, ghost);
        verify(player).setAlive(false);
        verify(player).setKiller(ghost);
    }

    @Test
    @Order(4)
    @DisplayName("Pellet与Player碰撞")
    public void testPelletCollidingWithPlayer() {
        playerCollisions.collide(pellet, player);

        verify(pointCalculator).consumedAPellet(player, pellet);
        verify(pellet).leaveSquare();
    }

    @Test
    @Order(5)
    @DisplayName("Ghost与Pellet碰撞")
    public void testGhostCollidingWithPellet() {
        playerCollisions.collide(ghost, pellet);
        verifyNoInteractions(pointCalculator, ghost, pellet);
    }

    @Test
    @Order(6)
    @DisplayName("Pellet与Ghost碰撞")
    public void testPelletCollidingWithGhost() {
        playerCollisions.collide(pellet, ghost);

        verifyNoInteractions(pointCalculator, pellet, ghost);
    }

    @Test
    @Order(7)
    @DisplayName("空碰撞")
    public void testNoCollision() {

        playerCollisions.collide(unit, unit);

        verifyNoInteractions(pointCalculator);
    }

}
