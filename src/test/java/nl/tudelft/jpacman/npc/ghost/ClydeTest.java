package nl.tudelft.jpacman.npc.ghost;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClydeTest {

    private static MapParser mapParser;

    @Test
    @DisplayName("Clyde离Player距离小于8个方块")
    @Order(1)
    void departMoreThanEight() {
        List<String> text = Lists.newArrayList(
            "##############",
            "#.#....C.....P",
            "##############");
        Level level = mapParser.parseMap(text);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        //创建Player
        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.valueOf("WEST"));
        level.registerPlayer(player);

        //act:
        Optional<Direction> opt = clyde.nextAiMove();

        //assert:
        assertThat(opt.get()).isEqualTo(Direction.valueOf("WEST"));
    }

    @BeforeAll
    public static void setup() {
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        BoardFactory boardFactory = new BoardFactory(sprites);
        GhostFactory ghostFactory = new GhostFactory(sprites);

        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    @Test
    @DisplayName("Clyde离Player距离大于8个方块")
    @Order(2)
    void departLessThanEight2() {

        List<String> text = Lists.newArrayList(
            "##############",
            "#.C..........P",
            "##############");
        Level level = mapParser.parseMap(text);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.valueOf("WEST"));
        level.registerPlayer(player);

        //act:
        Optional<Direction> opt = clyde.nextAiMove();

        //assert:
        assertThat(opt.get()).isEqualTo(Direction.valueOf("EAST"));
    }

    @Test
    @DisplayName("Clyde没有Player")
    @Order(3)
    void departWithoutPlayer() {

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
    @DisplayName("Clyde与Player直接没有路径")
    @Order(4)
    void withoutPathBetweenClydeAndPlayer() {

        List<String> text = Lists.newArrayList(
            "#############P",
            "#.C..........#",
            "##############");
        Level level = mapParser.parseMap(text);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());

        assertThat(level.isAnyPlayerAlive()).isFalse();

        //act:
        Optional<Direction> opt = clyde.nextAiMove();

        //assert:
        assertThat(opt.isPresent()).isFalse();
    }
}
