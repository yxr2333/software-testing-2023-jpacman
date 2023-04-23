package nl.tudelft.jpacman;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Created By IntelliJ IDEA
 *
 * @author IceCreamQAQ
 * @datetime 2023/4/23 星期日
 * Happy Every Coding Time~
 */

public class MapParserTest {

    private MapParser mapParser;

    @Mock
    private final LevelFactory levelFactory = mock(LevelFactory.class);

    @Mock
    private final BoardFactory boardFactory = mock(BoardFactory.class);

    @BeforeEach
    void setUp() {
        mapParser =new MapParser(levelFactory,boardFactory);
        when(boardFactory.createGround()).thenReturn(mock(Square.class));
        when(boardFactory.createWall()).thenReturn(mock(Square.class));
        when(levelFactory.createGhost()).thenReturn(mock(Ghost.class));
        when(levelFactory.createPellet()).thenReturn(mock(Pellet.class));
    }

    @Test
    @Order(0)
    @DisplayName("文件名称为null")
    void testNullFileName() {
        assertThatThrownBy(() -> mapParser.parseMap((String) null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @Order(1)
    @DisplayName("读取不存在的文件")
    void testFileNotFound() {
        String file = "/testMapFile.txt";
        assertThatThrownBy(() -> mapParser.parseMap(file))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessage("Could not get resource for: " + file);
    }

    @Test
    @Order(2)
    @DisplayName("读取存在的文件")
    void testFileFound() throws IOException {
        String file = "/simplemap.txt";
        mapParser.parseMap(file);
        verify(boardFactory, times(4)).createGround();
        verify(boardFactory, times(2)).createWall();
        verify(levelFactory, times(1)).createGhost();
    }

    @Test
    @Order(3)
    @DisplayName("读取无法解析的文件")
    void testFileUnreadable() throws IOException {
        String file = "/unrecognizedcharmap.txt";
        assertThatThrownBy(() -> mapParser.parseMap(file))
                .isInstanceOf(PacmanConfigurationException.class)
                .hasMessage("Invalid character at 0,0: A");
    }
}
