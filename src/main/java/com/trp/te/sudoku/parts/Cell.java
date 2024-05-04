package com.trp.te.sudoku.parts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Cell {
    private boolean solved;
    private final boolean original;
    private int value;
    private List<Integer> possibilities;

    public int numberOfPossibilitiesLeft(){
        return possibilities.size();
    }

    @Override
    public String toString(){
        return (solved || original) ? "" + value : " ";
    }
}
