package com.trp.te.sudoku.parts;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @ParameterizedTest
    @CsvSource({
            "1,true,false,1,false",
            ",false,fasle,0,true"
    })
    void testToString(String expected, boolean solved, boolean original, int value, boolean addToList) {

        // Arrange

        if (expected == null || expected.isEmpty()){
            expected = " ";
        }

        List<Integer> list = new ArrayList<>();
        if(addToList){
            list.add(1);
            list.add(2);
        }
        Cell cell = new Cell(solved, original, value, list);

        // Act

        String actual = cell.toString();

        // Assert

        assertEquals(expected, actual);
    }
}