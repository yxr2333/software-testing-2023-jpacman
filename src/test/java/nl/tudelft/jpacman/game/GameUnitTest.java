package nl.tudelft.jpacman.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

class GameUnitTest {

	private SinglePlayerGame game;
	private Level level;
	
	@BeforeEach
	void setup() {
		level = mock(Level.class);
	}
	
	
	@Test
	@DisplayName("有豆子没有玩家存活,则游戏结束")
	void testNoPlayerAlive() {
		
		//arrange
		when(level.isAnyPlayerAlive()).thenReturn(false);
		when(level.remainingPellets()).thenReturn(23);
		
		game = new SinglePlayerGame(mock(Player.class), level, mock(PointCalculator.class));
		
		//act
		game.start();
		
		//assert
		assertThat(game.isInProgress()).isFalse();
	}

	@Test
	@DisplayName("没有豆子没有玩家存活，则游戏结束")
	void testNoPlayerAliveNoPellet(){
		//arrange
		when(level.isAnyPlayerAlive()).thenReturn(false);
		when(level.remainingPellets()).thenReturn(0);
		
		game = new SinglePlayerGame(mock(Player.class), level, mock(PointCalculator.class));
		
		// act
		game.start();
		
		//assert
		assertThat(game.isInProgress()).isFalse();
	}
	
	@Test
	@DisplayName("有豆子有玩家存活，则游戏进行中")
	void testPlayerAliveAndHasPellet(){
		//arrange
		when(level.isAnyPlayerAlive()).thenReturn(true);
		when(level.remainingPellets()).thenReturn(23);
		
		game = new SinglePlayerGame(mock(Player.class), level, mock(PointCalculator.class));
		
		// act
		game.start();
		
		//assert
		assertThat(game.isInProgress()).isTrue();
	}
	
	@Test
	@DisplayName("没有豆子有玩家存活，则游戏结束")
	void testPlayerAliveAndNoPellet(){
		//arrange
		when(level.isAnyPlayerAlive()).thenReturn(true);
		when(level.remainingPellets()).thenReturn(0);
		
		game = new SinglePlayerGame(mock(Player.class), level, mock(PointCalculator.class));
		
		// act
		game.start();
		
		//assert
		assertThat(game.isInProgress()).isFalse();
	}
	
	@Test
	@DisplayName("游戏进行中再次调用start，则状态不变")
	void testReStart() {
		when(level.isAnyPlayerAlive()).thenReturn(true);
		when(level.remainingPellets()).thenReturn(23);
		
		game = new SinglePlayerGame(mock(Player.class), level, mock(PointCalculator.class));
		assertThat(game.isInProgress()).isFalse();
		
		//act
		game.start();
		assertThat(game.isInProgress()).isTrue();
		
		//再次点击start按钮
		game.start();
		assertThat(game.isInProgress()).isTrue();
		
		//没有做其他的事情
		//assert
		verify(level,times(1)).isAnyPlayerAlive();
		verify(level,times(1)).remainingPellets();
	}
	
}
