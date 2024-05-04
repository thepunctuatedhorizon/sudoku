package com.trp.te.sudoku.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pane {

    /**
     * given the difficulty of mapping, a few helper functions will assist here with keeping track of where we are in
     * this map.  For now, it is important to know we are in the
     * 123
     * 456
     * 789
     * layout.  So, cell 7 is in the column with cell 1 and cell 4, for example.  Also, the row cell 5 is in is shared
     * by cell 4 and cell 6. Whereas the row cell 6 is in is above the row which holds cell 7.
     */
    private Map<Integer, Cell> cells;

    private int[] listOfFoundValues;


    public Pane(Map<Integer, Cell> cells) {
        this.cells = cells;
    }

    /**
     * The output is not the canonical row based format.  Given the fact that this is a pane
     * and an object in and of itself with non-row properties, it is expected that this won't be
     * used in a row fashion.
     * @return String representing the pane.
     */
    @Override
    public String toString(){
        return "| " + cells.get(0).toString() + " " + cells.get(1) + " " + cells.get(2) + " |\n" +
               "| " + cells.get(3) + " " + cells.get(4) + " " + cells.get(5) + " |\n" +
               "| " + cells.get(6) + " " + cells.get(7) + " " + cells.get(8) + " |\n";
    }
}
