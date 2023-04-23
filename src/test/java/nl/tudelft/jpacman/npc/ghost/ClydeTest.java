package nl.tudelft.jpacman.npc.ghost;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.SinglePlayerGame;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created By IntelliJ IDEA
 *
 * @author IceCreamQAQ
 * @datetime 2023/4/18 星期二
 * Happy Every Coding Time~
 */
public class ClydeTest {

    private final List<String> MAP = Lists.newArrayList(
        "##############",
        "#.C.........P#",
        "##############");

    private PacManSprites sprites;
    private PlayerFactory playerFactory;
    private GhostFactory ghostFactory;
    private DefaultPointCalculator pointCalculator;
    private LevelFactory levelFactory;
    private GhostMapParser mapParser;
    private BoardFactory boardFactory;

    @BeforeEach
    public void setUp() {
        sprites = new PacManSprites();
        ghostFactory = new GhostFactory(sprites);
        pointCalculator = new DefaultPointCalculator();
        levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        playerFactory = new PlayerFactory(sprites);
        boardFactory = new BoardFactory(sprites);
        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    @Test
    public void testClydeBehaviorWhenFarFromPacMan() {
        // Arrange
        Game game = createGame(MAP);
        Level level = game.getLevel();
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Square clydeSquare = clyde.getSquare();

        // Move Pac-Man far from Clyde
        movePlayer(game, clydeSquare, Direction.NORTH, 20);

        // Act
        Optional<Direction> nextMove = clyde.nextAiMove();

        // Assert
        assertThat(nextMove).isPresent();
        assertThat(nextMove.get()).isEqualTo(Direction.EAST);
    }

    @Test
    public void testClydeBehaviorWhenNearToPacMan() {
        // Arrange
        Game game = createGame(MAP);
        Level level = game.getLevel();
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Square clydeSquare = clyde.getSquare();

        // Move Pac-Man close to Clyde
        movePlayer(game, clydeSquare, Direction.NORTH, 5);

        // Act
        Optional<Direction> nextMove = clyde.nextAiMove();

        // Assert
        assertThat(nextMove).isPresent();
        assertThat(nextMove.get()).isEqualTo(Direction.EAST);
    }

    @Test
    public void testClydeStaysAwayFromPacMan() {
        // Arrange
        Game game = createGame(MAP);
        Level level = game.getLevel();
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Square clydeSquare = clyde.getSquare();

        // Move Pac-Man close to Clyde
        movePlayer(game, clydeSquare, Direction.NORTH, 5);

        // Act
        List<Direction> path = Navigation.shortestPath(clydeSquare, game.getPlayers().get(0).getSquare(), clyde);

        // Assert
        assertThat(path).isNotEmpty();
        assertThat(path).hasSizeGreaterThan(8);
    }

    private Game createGame(List<String> map) {
        Level level = mapParser.parseMap(map);
        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);
        Game game = new SinglePlayerGame(player, level, pointCalculator);
        game.start();
        return game;
    }

    private void movePlayer(Game game, Square square, Direction direction, int steps) {
        Player player = game.getPlayers().get(0); // 获取当前唯一的玩家
        game.move(player, direction);
    }

    private Square getSquareAt(Square square, Direction direction, int steps) {
        Square result = square;
        for (int i = 0; i < steps; i++) {
            result = result.getSquareAt(direction);
        }
        return result;
    }
}
