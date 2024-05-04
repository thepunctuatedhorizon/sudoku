package com.trp.te.sudoku.solver;

import com.trp.te.sudoku.exceptions.SetupException;
import com.trp.te.sudoku.parts.*;
import com.trp.te.sudoku.util.Util;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class Solver {

    public static final int MAX_CYCLES = 10;

    public int solve(Puzzle puzzle){
        int numUnsolved = 81;
        int cycles = 0;
        while (numUnsolved > 0 && cycles < MAX_CYCLES) {
            numUnsolved = cycleGrid(puzzle);
            cycles++;

            log.info("WE HAVE FINISHED A CYCLE. AT CYCLE: {}", cycles);
        }
        return cycles;
    }

    /**
     * Does the cycle
     * @param puzzle the puzzle to solve
     * @return the number of unsolved still to find.
     */
    public int cycleGrid(Puzzle puzzle){
        int numSolved = puzzle.getGrid().getRowMap().values().stream().mapToInt(row -> (row.getListOfFoundValues() != null) ? row.getListOfFoundValues().length : 0).sum();
        int numUnSolved = 81 - numSolved;
        log.info("There are {} solved and {} unsolved values.", numSolved, numUnSolved);

        int i = 0;
        for(Row row : puzzle.getGrid().getRowMap().values()){
           cycleMap(row.getCells());
           log.info("Row {} Cycling Result: \n{}", i, puzzle.printDebug());
           i++;
        }
        updateRowsWithFoundSolutions(puzzle.getGrid().getRowMap());
        updateColumnsWithFoundSolutions(puzzle.getGrid().getColumnMap());
        updatePanesWithFoundSolutions(puzzle.getGrid().getPaneMap());
        log.info("Row Cycling: \n{}", puzzle);
        numSolved = puzzle.getGrid().getRowMap().values().stream().mapToInt(row-> (row.getListOfFoundValues() != null) ? row.getListOfFoundValues().length : 0).sum();
        numUnSolved = 81 - numSolved;
        log.info("There are {} solved and {} unsolved values.", numSolved, numUnSolved);

        int j = 0;
        for(Column column : puzzle.getGrid().getColumnMap().values()){
            cycleMap(column.getCells());
            log.info("Column {} Cycling Result: \n{}", j, puzzle.printDebug());
            j++;
        }

        updateRowsWithFoundSolutions(puzzle.getGrid().getRowMap());
        updateColumnsWithFoundSolutions(puzzle.getGrid().getColumnMap());
        updatePanesWithFoundSolutions(puzzle.getGrid().getPaneMap());
        log.info("Column Cycling \n{}", puzzle);
        numSolved = puzzle.getGrid().getRowMap().values().stream().mapToInt(row-> (row.getListOfFoundValues() != null) ? row.getListOfFoundValues().length : 0).sum();
        numUnSolved = 81 - numSolved;
        log.info("There are {} solved and {} unsolved values.", numSolved, numUnSolved);

        int k=0;
        for(Pane pane : puzzle.getGrid().getPaneMap().values()){
            cycleMap(pane.getCells());
            log.info("Pane {} Cycling Result: \n{}", k, puzzle.printDebug());
            k++;
        }

        updateRowsWithFoundSolutions(puzzle.getGrid().getRowMap());
        updateColumnsWithFoundSolutions(puzzle.getGrid().getColumnMap());
        updatePanesWithFoundSolutions(puzzle.getGrid().getPaneMap());
        log.info("Pane Cycling \n{}", puzzle);
        numSolved = puzzle.getGrid().getRowMap().values().stream().mapToInt(row-> (row.getListOfFoundValues() != null) ? row.getListOfFoundValues().length : 0).sum();
        numUnSolved = 81 - numSolved;
        log.info("There are {} solved and {} unsolved values.", numSolved, numUnSolved);

        return numUnSolved;
    }

    public void updateRowsWithFoundSolutions(Map<Integer, Row> rows) {
        rows.values().forEach(row -> {
            int[] values =  Util.computeFoundValuesForMap(row.getCells());
            log.info("values: {}", values);
            row.setListOfFoundValues(values);
        });
    }
    public void updateColumnsWithFoundSolutions(Map<Integer, Column> columns) {
        columns.values().forEach(column -> column.setListOfFoundValues(Util.computeFoundValuesForMap(column.getCells())));
    }
    public void updatePanesWithFoundSolutions(Map<Integer, Pane> panes) {
        panes.values().forEach(pane -> pane.setListOfFoundValues(Util.computeFoundValuesForMap(pane.getCells())));
    }

    public void cycleMap(Map<Integer, Cell> cellMap){
        processMapToRemoveAllFoundOrOriginalValues(cellMap);
        checkMapAndSolveAnySolutions(cellMap);
    }

    public void checkMapAndSolveAnySolutions(Map<Integer, Cell> cellMap){
        List<Integer> valuesSolved = new ArrayList<>();
        for(Cell cell:cellMap.values()){
            int possibleSolution = checkCellForSolution(cell);
            if (possibleSolution > 0){
                // We now solve the cell.
                cell.setSolved(true);
                cell.setValue(possibleSolution);
                valuesSolved.add(possibleSolution);
            }
        }
        valuesSolved.forEach(val -> updateRowToRemoveSinglePossibilityFromAllCells(cellMap, val));
    }

    public int checkCellForSolution(Cell cell){
        if (cell.isSolved() || cell.isOriginal() || cell.numberOfPossibilitiesLeft() != 1){
            return -1;
        }

        // Else, the only possibility left is the solution.
        return cell.getPossibilities().get(0);
    }

    public void processMapToRemoveAllFoundOrOriginalValues(Map<Integer, Cell> cellMap) {
        List<Integer> foundOrOriginal = getListOfFoundOrOriginalFromAllCells(cellMap);
        if (foundOrOriginal != null && !foundOrOriginal.isEmpty()) {
            foundOrOriginal.forEach( val -> updateRowToRemoveSinglePossibilityFromAllCells(cellMap, val));
        }
    }

    public List<Integer> getListOfFoundOrOriginalFromAllCells(Map<Integer, Cell> cellMap){
        if (cellMap == null || cellMap.isEmpty()){
            return null;  // We don't care, returning null list to indicate operation was not done.
        }
        final List<Integer> list = new ArrayList<>();
        cellMap.forEach((idx, cell) -> {
            if (cell.isOriginal() || cell.isSolved()){
                list.add(cell.getValue());
            }
        });
        return list;
    }

    public void updateRowToRemoveSinglePossibilityFromAllCells(Map<Integer, Cell> cellMap, int value) {
        if (value < 1 || value > 9) {
            throw new SetupException("Value to remove not within 1 to 9, inclusive.");
        }
        cellMap.forEach((idx, cell) -> {
            if (cell.isSolved() || cell.isOriginal()){
                // We do not wish to update when we know this cell is already solved.
                return;
            }

            // We know what value to remove from each cell.
            List<Integer> list = cell.getPossibilities();

            // Get index in list of value
            int indexOfValue = list.indexOf(value);

            if(indexOfValue > -1) {
                // Remove if index is above -1, -1 meaning not in list.
                list.remove(indexOfValue); // Zero based index list

                cell.setPossibilities(list);
            } // else, already removed.
        });
    }
}
