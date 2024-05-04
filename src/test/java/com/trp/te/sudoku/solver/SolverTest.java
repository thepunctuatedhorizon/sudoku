package com.trp.te.sudoku.solver;

import com.trp.te.sudoku.exceptions.SetupException;
import com.trp.te.sudoku.parts.Cell;
import com.trp.te.sudoku.parts.Row;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {


    @ParameterizedTest
    @CsvSource({
            "1|3|9,1,3|9,1,true",
            "1|3|9,2,1|3|9,2,true",
            "1|3|9,1|2,1|3|9,0,false"
    })
    void testCheckMapAndSolveAnySolutions_whenValidCases_thenExpectedUpdatesOccur(String possibles1, String possibles2, String afterMethodLeftIn1, int expected, boolean solved) {

        // Arrange

        Solver solver = new Solver();
        String[] partsBeforeFirst = possibles1.split("\\|");
        List<Integer> listFor1 = new ArrayList<>();
        for (String val : partsBeforeFirst) {
            listFor1.add(Integer.parseInt(val));
        }
        String[] partsBefore2 = possibles2.split("\\|");
        List<Integer> listFor2 = new ArrayList<>();
        for (String val : partsBefore2) {
            listFor2.add(Integer.parseInt(val));
        }
        String[] partsA = afterMethodLeftIn1.split("\\|");
        List<Integer> listAfter1 = new ArrayList<>();
        for (String val : partsA) {
            listAfter1.add(Integer.parseInt(val));
        }


        Cell cell1 = new Cell(false, false, 0, listFor1);
        Cell cell2 = new Cell(false, false, 0, listFor2);

        Map<Integer, Cell> cells = new HashMap<>();
        cells.put(0, cell1);
        cells.put(1, cell2);

        Row row = new Row(cells);

        // Act

        solver.checkMapAndSolveAnySolutions(row.getCells());
        int actual = row.getCells().get(1).getValue();
        boolean gotSolved = row.getCells().get(1).isSolved();


        // Assert

        assertEquals(expected, actual);
        assertEquals(solved, gotSolved);
        assertEquals(listAfter1, row.getCells().get(0).getPossibilities());

    }

    @ParameterizedTest
    @CsvSource({
            "1|3|4|9,1,3|9,1,true",
            "1|3|4|9,2,1|3|9,2,true",
            "1|3|4|9,1|2,1|3|9,0,false"
    })
    void testCycle_whenValidCases_thenExpectedUpdatesOccur(String possibles1, String possibles2, String afterMethodLeftIn1, int expected, boolean solved){

        // Arrange

        Solver solver = new Solver();
        String[] partsBeforeFirst = possibles1.split("\\|");
        List<Integer> listFor1 = new ArrayList<>();
        for(String val : partsBeforeFirst){
            listFor1.add(Integer.parseInt(val));
        }
        String[] partsBefore2 = possibles2.split("\\|");
        List<Integer> listFor2 = new ArrayList<>();
        for(String val : partsBefore2){
            listFor2.add(Integer.parseInt(val));
        }
        String[] partsA = afterMethodLeftIn1.split("\\|");
        List<Integer> listAfter1 = new ArrayList<>();
        for(String val : partsA){
            listAfter1.add(Integer.parseInt(val));
        }


        Cell cell1 = new Cell(false, false, 0, listFor1);
        Cell cell2 = new Cell(false, false, 0, listFor2);
        Cell cell3 = new Cell(true, false, 4, new ArrayList<>());

        Map<Integer, Cell> cells = new HashMap<>();
        cells.put(0, cell1);
        cells.put(1, cell2);
        cells.put(2, cell3);

        Row row = new Row(cells);

        // Act

        solver.cycleMap(row.getCells());
        int actual = row.getCells().get(1).getValue();
        boolean gotSolved = row.getCells().get(1).isSolved();

        // Assert

        assertEquals(expected, actual);
        assertEquals(solved, gotSolved);
        assertEquals(listAfter1, row.getCells().get(0).getPossibilities());

    }




    @ParameterizedTest
    @CsvSource({
            "1|2|3|4,1|2|7,2,1|3|4,1|7",
            "1|2|3|4,1|2|7,3,1|2|4,1|2|7",
            "2|3|4|5,1|2|7,1,2|3|4|5,2|7",
            "1|2|3|4,1|2|9,9,1|2|3|4,1|2"
    })
    void testUpdateRowToRemoveSinglePossibilityFromAllCells_withValidInputs_validResults(String beforeFirst, String beforeSecond, int knockOut, String after1, String after2) {

        // Arrange

        Solver solver = new Solver();
        String[] partsBeforeFirst = beforeFirst.split("\\|");
        List<Integer> listBeforeFirst = new ArrayList<>();
        for(String val : partsBeforeFirst){
            listBeforeFirst.add(Integer.parseInt(val));
        }
        String[] partsBefore2 = beforeSecond.split("\\|");
        List<Integer> listBefore2 = new ArrayList<>();
        for(String val : partsBefore2){
            listBefore2.add(Integer.parseInt(val));
        }
        String[] partsA = after1.split("\\|");
        List<Integer> listA1 = new ArrayList<>();
        for(String val : partsA){
            listA1.add(Integer.parseInt(val));
        }
        String[] parts2 = after2.split("\\|");
        List<Integer> listA2 = new ArrayList<>();
        for(String val : parts2){
            listA2.add(Integer.parseInt(val));
        }

        Cell cell1 = new Cell(false, false, 0, listBeforeFirst);
        Cell cell2 = new Cell(false, false, 0, listBefore2);

        Map<Integer, Cell> cells = new HashMap<>();
        cells.put(0, cell1);
        cells.put(1, cell2);

        Row row = new Row();
        row.setCells(cells);

        // Act

        solver.updateRowToRemoveSinglePossibilityFromAllCells(row.getCells(), knockOut);

        List<Integer> listA1Result = row.getCells().get(0).getPossibilities();
        List<Integer> listA2Result = row.getCells().get(1).getPossibilities();

        // Assert

        assertEquals(listA1, listA1Result);
        assertEquals(listA2, listA2Result);

    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "0",
            "10"
    })
    void testUpdateRowToRemoveSinglePossibilityFromAllCells_whenInvalidRemovalRequested_thenThrows(int val) {

        // Arrange

        Solver solver = new Solver();

        List<Integer> list = new ArrayList<>();
        list.add(1);

        Cell cell = new Cell(false, false, 0, list);
        Map<Integer, Cell> map = new HashMap<>();
        map.put(1, cell);

        // Act

        // Assert

        assertThrows(SetupException.class, () -> solver.updateRowToRemoveSinglePossibilityFromAllCells(map, val));

    }

    @Test
    void testUpdateRowToRemoveSinglePossibilityFromAllCells_whenSolvedOriginal_thenNOOP() {

        // Arrange

        Solver solver = new Solver();

        List<Integer> list = new ArrayList<>();


        Cell cell = new Cell(true, false, 1, list);

        Cell cell2 = new Cell(false, true, 1, list);
        Map<Integer, Cell> map = new HashMap<>();
        map.put(1, cell);
        map.put(2, cell2);

        // Act

        solver.updateRowToRemoveSinglePossibilityFromAllCells(map, 1);

        // Assert

        assertEquals(1, map.get(1).getValue());
        assertEquals(1, map.get(2).getValue());
    }

    @ParameterizedTest
    @CsvSource({
            "0,0,0",
            "0,0,1",
            "0,1,1",
            "0,1,2",
            "1,0,1",
            "1,1,2",
            "1,2,3",
            "2,0,4",
            "2,1,4",
            "2,1,3",
            "7,0,7",
            "7,1,8",
            "7,0,9",
            "7,2,9",
            "8,0,9",
            "8,1,9",
            "9,0,9"
    })
    void testGetListOfFoundOrOriginalFromAllCells_whenNumberOfCellsNumSolved_thenReturnValidSolvedValues(int numSolved, int numOriginal, int numCells){

        // Arrange

        int expectedSize = numSolved + numOriginal;
        Solver solver = new Solver();
        Map<Integer, Cell> map = new HashMap<>();

        int expectedSum = 0;
        for(int i=0; i < numCells; i++){
            boolean solved = i < numSolved;
            boolean original = i >= numSolved && i < numSolved + numOriginal;
            if (solved || original){
                expectedSum += i+1;
            }
            List<Integer> list = new ArrayList<>();
            if (!solved && ! original){
                list.add(1);
            }
            Cell cell = new Cell(solved, original, i+1, list);
            map.put(i, cell);
        }

        // Act

        List<Integer> values = solver.getListOfFoundOrOriginalFromAllCells(map);

        int sum = 0;
        int solvedOrOriginal = 0;
        if (values != null) {
            sum = values.stream().mapToInt(i -> i).sum();
            solvedOrOriginal = values.size();
        }

        // Assert

        assertEquals(expectedSum, sum);
        assertEquals(expectedSize, solvedOrOriginal);
    }



    @ParameterizedTest
    @CsvSource({
            "1|2|3|4,1|2|7,2,1|3|4,1|7",
            "1|2|3|4,1|2|7,4,1|2|3,1|2|7",
            "1|2|3|4,1|2|7,7,1|2|3|4,1|2",
            "1|2|3|4,1|2|7,9,1|2|3|4,1|2|7",
            "1|2|3|4,1|2|7,1,2|3|4,2|7",
    })
    void testProcessMapToKnockOutFoundOrOriginalValues_whenValidInput_thenKnockoutIfExisted(String beforeFirst, String beforeSecond, int valOrg, String after1, String after2) {

        // Arrange

        Solver solver = new Solver();
        String[] partsBeforeFirst = beforeFirst.split("\\|");
        List<Integer> listBeforeFirst = new ArrayList<>();
        for(String val : partsBeforeFirst){
            listBeforeFirst.add(Integer.parseInt(val));
        }
        String[] partsBefore2 = beforeSecond.split("\\|");
        List<Integer> listBefore2 = new ArrayList<>();
        for(String val : partsBefore2){
            listBefore2.add(Integer.parseInt(val));
        }
        String[] partsA = after1.split("\\|");
        List<Integer> listA1 = new ArrayList<>();
        for(String val : partsA){
            listA1.add(Integer.parseInt(val));
        }
        String[] parts2 = after2.split("\\|");
        List<Integer> listA2 = new ArrayList<>();
        for(String val : parts2){
            listA2.add(Integer.parseInt(val));
        }

        Cell cell1 = new Cell(false, false, 0, listBeforeFirst);
        Cell cell2 = new Cell(false, false, 0, listBefore2);
        Cell cell3 = new Cell(true, false, valOrg, new ArrayList<>());

        Map<Integer, Cell> cells = new HashMap<>();
        cells.put(0, cell1);
        cells.put(1, cell2);
        cells.put(2, cell3);

        Row row = new Row();
        row.setCells(cells);

        // Act

        solver.processMapToRemoveAllFoundOrOriginalValues(cells);

        List<Integer> listA1Result = row.getCells().get(0).getPossibilities();
        List<Integer> listA2Result = row.getCells().get(1).getPossibilities();

        // Assert

        assertEquals(listA1, listA1Result);
        assertEquals(listA2, listA2Result);
    }

    @ParameterizedTest
    @CsvSource({
            "false,false,1,0,1",
            "false,false,1|2|3,0,-1",
            "true,false,,2,-1",
            "false,true,,3,-1",
            "true,true,,5,-1",
            "false,false,2,0,2",
            "false,false,5,0,5"
    })
    void testCheckCellForSolution(boolean solved, boolean original, String list, int val, int expectedResult) {

        // Arrange

        Solver solver = new Solver();

        List<Integer> intList = new ArrayList<>();
        if (list != null) {
            String[] listArr = list.split("\\|", -1);
            for (String s : listArr) {
                intList.add(Integer.parseInt(s));
            }
        }
        Cell cell = new Cell(solved, original, val, intList);

        // Act

        int result = solver.checkCellForSolution(cell);

        // Assert

        assertEquals(expectedResult, result);
    }
}