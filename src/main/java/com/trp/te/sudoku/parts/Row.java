package com.trp.te.sudoku.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
public class Row {
    private Map<Integer, Cell> cells;

    private int[] listOfFoundValues;


    public Row() {
        // NOOP
    }

    public Row(Map<Integer, Cell> cells) {
        this.cells = cells;
    }

    public boolean checkAllInRowSolved() {
        return  !cells.isEmpty() && cells.values().stream().allMatch(Cell::isSolved);
    }

}
