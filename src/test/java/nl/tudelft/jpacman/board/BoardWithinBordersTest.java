package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BoardWithinBordersTest {
	private static final int MAX_WIDTH = 3;
	private static final int MAX_HEIGHT = 4;

	private static Square[][] grid = {
			{ mock(Square.class), mock(Square.class), mock(Square.class), mock(Square.class) },
			{ mock(Square.class), mock(Square.class), mock(Square.class), mock(Square.class) },
			{ mock(Square.class), mock(Square.class), mock(Square.class), mock(Square.class) } };
	
	private final Board board = new Board(grid);
	
	@Test
	void verifyWidth() {
		assertThat(board.getWidth()).isEqualTo(MAX_WIDTH);
	}
	
	@Test
	void verifyHeight() {
		assertThat(board.getHeight()).isEqualTo(MAX_HEIGHT);
	}
	
	@DisplayName("边界值方法测试游戏盘边界")
	@ParameterizedTest
	@CsvSource({
		"0,2,true",
		"1,2,true",
		"2,2,true",
		"3,2,false",
		"1,0,true",
		"1,1,true",
		"1,4,false",
		"1,3,true",
		"1,0,true",
	})
	void testWithinBorder(int x,int y,boolean result) {
		assertThat(board.withinBorders(x, y)).isEqualTo(result);
	}
}
