package nl.tudelft.jpacman.npc.ghost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import com.google.common.collect.Lists;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClydeTest2 {
	
	
	private MapParser mapParser;

	@BeforeAll
	void setup() {
		//Arrange
		 PacManSprites sprites = mock(PacManSprites.class);//new PacManSprites();
		 GhostFactory ghostFactory = new GhostFactory(sprites);
		 
	     LevelFactory levelFactory = new LevelFactory(
	            sprites,
	            ghostFactory,
	            mock(PointCalculator.class));
	     
	     BoardFactory boardFactory = new BoardFactory(sprites);
	    
		mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
	}
	
	@Test
	@DisplayName("Clyde离Player距离小于8个方块")
	@Order(1)
	void departLessThanEight() {
		//Arrange
		List<String> text = Lists.newArrayList(
				"##############",
				"#.#....G.....P",
				"##############");
		Level level = mapParser.parseMap(text);
		
		Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
		assertThat(clyde).isNotNull();
		assertThat(clyde.getDirection()).isEqualTo(Direction.valueOf("EAST"));
		
		//创建Player
		Player player = new PlayerFactory(new PacManSprites()).createPacMan();
		player.setDirection(Direction.valueOf("WEST"));
		level.registerPlayer(player);
		
		Player p = Navigation.findUnitInBoard(Player.class, level.getBoard());
		assertThat(p).isNotNull();
		assertThat(p.getDirection()).isEqualTo(Direction.valueOf("WEST"));
		
		//act:
		Optional<Direction> opt = clyde.nextAiMove();
		assertThat(opt.get()).isEqualTo(Direction.valueOf("WEST"));
	}
	
	
	@Test
	@DisplayName("Clyde离Player距离大于8个方块")
	@Order(2)
	void departMoreThanEight() {
		
		//Arrange
		List<String> text = Lists.newArrayList(
				"##############",
				"#.C..........P",
				"##############");
		Level level = mapParser.parseMap(text);
		
		Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
		assertThat(clyde).isNotNull();
		assertThat(clyde.getDirection()).isEqualTo(Direction.valueOf("EAST"));
		
		Player player = new PlayerFactory(new PacManSprites()).createPacMan();
		player.setDirection(Direction.valueOf("WEST"));
		level.registerPlayer(player);
		
		Player p = Navigation.findUnitInBoard(Player.class, level.getBoard());
		assertThat(p).isNotNull();
		assertThat(p.getDirection()).isEqualTo(Direction.valueOf("WEST"));
		
		//act:
		Optional<Direction> opt = clyde.nextAiMove();
		
		//assert:
		assertThat(opt.get()).isEqualTo(Direction.valueOf("EAST"));
	}
	
	@Test
	@DisplayName("Clyde没有Player")
	@Order(3)
	void departWithoutPlayer() {
		
		//Arrange
		List<String> text = Lists.newArrayList(
				"##############",
				"#.C...........",
				"##############");
		Level level = mapParser.parseMap(text);
		
		Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
		assertThat(clyde).isNotNull();
		assertThat(clyde.getDirection()).isEqualTo(Direction.valueOf("EAST"));
		
		assertThat(level.isAnyPlayerAlive()).isFalse();

		//act:
		Optional<Direction> opt = clyde.nextAiMove();
		
		//assert:
		assertThat(opt.isPresent()).isFalse();
	}
	
	@Test
	@DisplayName("Clyde与Player之间没有路径")
	@Order(4)
	void withoutPathBetweenClydeAndPlayer() {
		
		//Arrange
		List<String> text = Lists.newArrayList(
				"#############P",
				"#.C..........#",
				"##############");
		Level level = mapParser.parseMap(text);
		
		Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
		assertThat(clyde).isNotNull();
		assertThat(clyde.getDirection()).isEqualTo(Direction.valueOf("EAST"));
		
		assertThat(level.isAnyPlayerAlive()).isFalse();
		
		//act:
		Optional<Direction> opt = clyde.nextAiMove();
		
		//assert:
		assertThat(opt.isPresent()).isFalse();
	}
}
