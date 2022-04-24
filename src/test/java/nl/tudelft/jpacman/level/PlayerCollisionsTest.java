package nl.tudelft.jpacman.level;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;

import static org.mockito.Mockito.*;

class PlayerCollisionsTest {

	private PlayerCollisions playerCollisions;
	private PointCalculator pointCalculator;
	@BeforeEach
	void setup() {
		pointCalculator = mock(PointCalculator.class);
		playerCollisions = new PlayerCollisions(pointCalculator);
	}
	
	@Test
	@DisplayName("Player移到到Ghost位置")
	void playerCollideOnGhost() {
		//arrange
		Player player = mock(Player.class);
		Ghost ghost = mock(Ghost.class);
		
		//act
		playerCollisions.collide(player, ghost);
		
		//assert
		verify(pointCalculator).collidedWithAGhost(player, ghost);
		verify(player).setAlive(false);
		verify(player).setKiller(ghost);
	}

	@Test
	@DisplayName("Player移到到Pellet位置")
	void playerCollideOnPellet() {
		Player player = mock(Player.class);
		Pellet pellet = mock(Pellet.class);
		
		playerCollisions.collide(player, pellet);
		
		verify(pellet).leaveSquare();
		verify(pointCalculator).consumedAPellet(player, pellet);
	}
	
	@Test
	@DisplayName("Player移到到Player位置")
	void playerCollideOnPlayer() {
		Player player1 = mock(Player.class);
		Player player2 = mock(Player.class);
		
		playerCollisions.collide(player1, player2);
		
		verifyZeroInteractions(player1,player2);
	}
	
	@Test
	@DisplayName("Ghost移动到Ghost位置")
	void ghostCollideOnGhost() {
		// arrange
		Ghost ghost1 = mock(Ghost.class);
		Ghost ghost2 = mock(Ghost.class);
		
		//act
		playerCollisions.collide(ghost1, ghost2);
		
		//
		verifyZeroInteractions(ghost1,ghost2);
		
		
	}
}
