package com.trp.te.sudoku.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Puzzle {

    public static final String END_PANE_ROW_DIVIDER = "\n|-------------------------------------|    |-----------------------------------||-----------------------------------||-----------------------------------|\n";
    public static final String ROW_DIVIDER = "\n|-----------||-----------||-----------|    |-----------------------------------||-----------------------------------||-----------------------------------|\n";
    public static final String ROW_CAP = "|-------------------------------------|\n";
    public static final String ROW_DIVISION = "|\n|-----------||-----------||-----------|\n";
    public static final String ROW_END = "|\n|-------------------------------------|";
    public static final String ROW_ENDER = "|-------------------------------------|    |-------------------------------------------------------------------------------------------------------------|\n";

    private Grid grid;

    public String printDebug() {
        return  ROW_ENDER +
                "|" + grid.printCellsForRow(0) + "|    |" + grid.printCandidatesForRow(0) + ROW_DIVIDER +
                "|" + grid.printCellsForRow(1) + "|    |" + grid.printCandidatesForRow(1) + ROW_DIVIDER + 
                "|" + grid.printCellsForRow(2) + "|    |" + grid.printCandidatesForRow(2) + END_PANE_ROW_DIVIDER +
                ROW_ENDER +
                "|" + grid.printCellsForRow(3) + "|    |" + grid.printCandidatesForRow(3) + ROW_DIVIDER + 
                "|" + grid.printCellsForRow(4) + "|    |" + grid.printCandidatesForRow(4) + ROW_DIVIDER + 
                "|" + grid.printCellsForRow(5) + "|    |" + grid.printCandidatesForRow(5) + END_PANE_ROW_DIVIDER +
                ROW_ENDER +
                "|" + grid.printCellsForRow(6) + "|    |" + grid.printCandidatesForRow(6) + ROW_DIVIDER + 
                "|" + grid.printCellsForRow(7) + "|    |" + grid.printCandidatesForRow(7) + ROW_DIVIDER + 
                "|" + grid.printCellsForRow(8) + "|    |" + grid.printCandidatesForRow(8) + END_PANE_ROW_DIVIDER;
        
        
    }
    
    
    @Override
    public String toString(){

        return ROW_CAP +
                "|" + grid.printCellsForRow(0) + ROW_DIVISION +
                "|" + grid.printCellsForRow(1) + ROW_DIVISION +
                "|" + grid.printCellsForRow(2) + ROW_END + "\n" +
                ROW_CAP +
                "|" + grid.printCellsForRow(3) + ROW_DIVISION +
                "|" + grid.printCellsForRow(4) + ROW_DIVISION +
                "|" + grid.printCellsForRow(5) + ROW_END + "\n" +
                ROW_CAP +
                "|" + grid.printCellsForRow(6) + ROW_DIVISION +
                "|" + grid.printCellsForRow(7) + ROW_DIVISION +
                "|" + grid.printCellsForRow(8) + ROW_END;
    }

    public String printOutColumns() {
        return ROW_CAP +
                "|" + grid.getPrintRowFromColumns(0) + ROW_DIVISION +
                "|" + grid.getPrintRowFromColumns(1) + ROW_DIVISION +
                "|" + grid.getPrintRowFromColumns(2) + "|\n|-------------------------------------|\n" +
                ROW_CAP +
                "|" + grid.getPrintRowFromColumns(3) + ROW_DIVISION +
                "|" + grid.getPrintRowFromColumns(4) + ROW_DIVISION +
                "|" + grid.getPrintRowFromColumns(5) + "|\n|-------------------------------------|\n" +
                ROW_CAP +
                "|" + grid.getPrintRowFromColumns(6) + ROW_DIVISION +
                "|" + grid.getPrintRowFromColumns(7) + ROW_DIVISION +
                "|" + grid.getPrintRowFromColumns(8) + ROW_END;
    }

    public String printOutPanes() {
        return  ROW_CAP +
                "|" + grid.getPrintRowFromPanes(0) + ROW_DIVISION +
                "|" + grid.getPrintRowFromPanes(1) + ROW_DIVISION +
                "|" + grid.getPrintRowFromPanes(2) + "|\n|-------------------------------------|\n" +
                ROW_CAP +
                "|" + grid.getPrintRowFromPanes(3) + ROW_DIVISION +
                "|" + grid.getPrintRowFromPanes(4) + ROW_DIVISION +
                "|" + grid.getPrintRowFromPanes(5) + "|\n|-------------------------------------|\n" +
                ROW_CAP +
                "|" + grid.getPrintRowFromPanes(6) + ROW_DIVISION +
                "|" + grid.getPrintRowFromPanes(7) + ROW_DIVISION +
                "|" + grid.getPrintRowFromPanes(8) + ROW_END;
    }
}
