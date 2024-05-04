package com.trp.te.sudoku.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Column {
    private Map<Integer, Cell> cells;

    private int[] listOfFoundValues;

    public Column(Map<Integer, Cell> cells) {
        this.cells = cells;
    }

    public boolean checkAllInRowSolved() {
        return  !cells.isEmpty() && cells.values().stream().allMatch(Cell::isSolved);
    }

}
