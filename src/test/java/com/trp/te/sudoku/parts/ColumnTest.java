package com.trp.te.sudoku.parts;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {

    @Test
    void testCheckAllInRowSolved_whenNoCells_thenFalse() {

        // Arrange

        Map<Integer, Cell> map = new HashMap<>();
        Column column = new Column(map);
        column.setListOfFoundValues(new int[]{1,2});

        // Act

        boolean actual = column.checkAllInRowSolved();

        // Assert

        assertFalse(actual);
        assertEquals(2, column.getListOfFoundValues().length);

    }

    @Test
    void testCheckAllInRowSolved_whenSolvedCells_thenTrue() {

        // Arrange

        Map<Integer, Cell> map = new HashMap<>();
        map.put(1, new Cell(true, true, 1, new ArrayList<>()));
        Column column = new Column(map);

        // Act

        boolean actual = column.checkAllInRowSolved();

        // Assert

        assertTrue(actual);
        assertTrue(column.getCells().get(1).isSolved());

    }

}