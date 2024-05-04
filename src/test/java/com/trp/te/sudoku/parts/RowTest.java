package com.trp.te.sudoku.parts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RowTest {

    @ParameterizedTest
    @CsvSource({
            "0,false",
            "1,false",
            "5,false",
            "8,false",
            "9,true"
    })
    void testCheckAllInRowSolved(int numberTrue, boolean expected) {

        // Arrange

        Row row = new Row();

        Map<Integer, Cell> cells = new HashMap<>();

        if (numberTrue > 0) {
            for (int i = 0; i < numberTrue; i++) {
                Cell newCell = new Cell(true, true, 1, new ArrayList<>());
                cells.put(i, newCell);
            }
            List<Integer> list = new ArrayList<>();
            list.add(1);
            list.add(2);
            for (int i = numberTrue; i < 9; i++) {
                Cell newCell = new Cell(false, false, 0, list);
                cells.put(i, newCell);
            }
        }

        row.setCells(cells);

        // Act

        boolean actual = row.checkAllInRowSolved();

        // Assert

        assertEquals(expected, actual);

    }

    @Test
    void testCheckAllInRowSolved_whenNoCells_thenFalse() {

        // Arrange

        Map<Integer, Cell> map = new HashMap<>();
        Row row = new Row(map);
        row.setListOfFoundValues(new int[]{1,2});

        // Act

        boolean actual = row.checkAllInRowSolved();

        // Assert

        assertFalse(actual);
        assertEquals(2, row.getListOfFoundValues().length);

    }

    @Test
    void testCheckAllInRowSolved_whenSolvedCells_thenTrue() {

        // Arrange

        Map<Integer, Cell> map = new HashMap<>();
        map.put(1, new Cell(true, true, 1, new ArrayList<>()));
        Row row = new Row(map);

        // Act

        boolean actual = row.checkAllInRowSolved();

        // Assert

        assertTrue(actual);
        assertTrue(row.getCells().get(1).isSolved());

    }

}