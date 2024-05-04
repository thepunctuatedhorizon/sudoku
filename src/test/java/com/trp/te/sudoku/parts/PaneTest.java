package com.trp.te.sudoku.parts;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaneTest {

    @Test
    void testToString() {

        // Arrange

        String expected = "| 1 2 3 |\n| 4 5 6 |\n| 7 8 9 |\n";

        Map<Integer, Cell> cellMap = new HashMap<>();
        cellMap.put(0, new Cell(true, false, 1, new ArrayList<>()));
        cellMap.put(1, new Cell(true, false, 2, new ArrayList<>()));
        cellMap.put(2, new Cell(true, false, 3, new ArrayList<>()));
        cellMap.put(3, new Cell(true, false, 4, new ArrayList<>()));
        cellMap.put(4, new Cell(true, false, 5, new ArrayList<>()));
        cellMap.put(5, new Cell(true, false, 6, new ArrayList<>()));
        cellMap.put(6, new Cell(true, false, 7, new ArrayList<>()));
        cellMap.put(7, new Cell(true, false, 8, new ArrayList<>()));
        cellMap.put(8, new Cell(true, false, 9, new ArrayList<>()));

        int[] listFound = {1,2,3,4,5,6,7,8,9};

        Pane pane = new Pane(cellMap);
        pane.setListOfFoundValues(listFound);

        Pane pane1 = new Pane();
        pane1.setCells(cellMap);
        pane1.setListOfFoundValues(listFound);

        Pane pane2 = new Pane(cellMap, listFound);

        // Act

        String actual = pane.toString();

        // Assert

        assertEquals(expected, actual);
        assertEquals(9, pane.getCells().size());
        assertEquals(9, pane.getListOfFoundValues().length);

        assertEquals(9, pane1.getListOfFoundValues().length);
        assertEquals(9, pane2.getListOfFoundValues().length);

    }
}