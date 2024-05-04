package com.trp.te.sudoku.parts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Grid {
    private Map<Integer, Row> rowMap;
    private Map<Integer, Column> columnMap;

    /**
     * Note that the panes are in the following layout:
     * 123
     * 456
     * 789
     * <br>
     * E.g., row 1 would be included in pane 1, 2, and 3
     * Also, column 7 would be included in pane 3, 6, and 9
     */
    private Map<Integer, Pane> paneMap;


    public String printCellsForRow(int row){
        Map<Integer, Cell> cells = rowMap.get(row).getCells();
        StringBuilder builder = new StringBuilder();
        cells.forEach((i, cell) -> {
            String val = " ";
            if (cell.getValue() > 0){
                val = cell.getValue() + "";
            }
            if (i < 8 && i !=2 && i != 5) {
                builder.append(" ").append(val).append(" |");
            } else if (i == 2 || i == 5) {
                builder.append(" ").append(val).append(" ||");
            } else {
                builder.append(" ").append(val).append(" ");
            }
        });
        return builder.toString();
    }

    public String getPrintRowFromColumns(int row) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            String val = " ";
            Cell cell = columnMap.get(i).getCells().get(row);
            if (cell.getValue() > 0) {
                val = cell.getValue() + "";
            }
            if (i < 8 && i != 2 && i != 5) {
                builder.append(" ").append(val).append(" |");
            } else if (i == 2 || i == 5) {
                builder.append(" ").append(val).append(" ||");
            } else {
                builder.append(" ").append(val).append(" ");
            }
        }
        return builder.toString();
    }

    public String getPrintRowFromPanes(int idx) {
        StringBuilder builder = new StringBuilder();

        // For 2nd row, we need panes 0, 1, and 2.  For row 5, we need panes 4, 5, 6.
        // Additionally, for idx=7, the 8th row, we would need panes 6, 7, and 8  (The 7th, 8th, and 9th)
        int currLeftPane = 0;
        int currCenterPane = 1;
        int currRightPane = 2;

        switch (idx) {
            case 3, 4, 5 -> {
                currLeftPane = 3;
                currCenterPane = 4;
                currRightPane = 5;
            }
            case 6, 7, 8 -> {
                currLeftPane = 6;
                currCenterPane = 7;
                currRightPane = 8;
            }
            default -> {}   // Already set correctly.
        }
        Pane pane1 = paneMap.get( currLeftPane );
        Pane pane2 = paneMap.get( currCenterPane );
        Pane pane3 = paneMap.get( currRightPane );


        List<Pane> panes = new ArrayList<>();
        panes.add(pane1);
        panes.add(pane2);
        panes.add(pane3);

        AtomicInteger step = new AtomicInteger();
        panes.forEach(pane -> {

            // Now, we calculate similarly, what cells are needed from the panes

            int currLeftCell = 0;
            int currCenterCell = 1;
            int currRightCell = 2;

            int paneLevel = idx % 3;
            switch(paneLevel){
                case 1-> {
                    currLeftCell = 3;
                    currCenterCell = 4;
                    currRightCell = 5;
                }
                case 2 -> {
                    currLeftCell = 6;
                    currCenterCell = 7;
                    currRightCell = 8;
                }
                default -> {}   // Already set correctly.
            }

            // Build the row
            builder.append(" ").append( pane.getCells().get(currLeftCell).toString()   ).append( " |");
            builder.append(" ").append( pane.getCells().get(currCenterCell).toString() ).append( " |");
            builder.append(" ").append( pane.getCells().get(currRightCell).toString()  );

            if (step.get() > 1) {  // step zero = left, step 1 = middle step 2 = right
                builder.append( " ");
            } else {
                builder.append(" ||");
            }
            step.getAndIncrement();
        });

        return builder.toString();
    }

    public String printCandidatesForRow(int row) {
        StringBuilder val = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            val.append(" ").append(get9CharCandidateString(rowMap.get(row).getCells().get(i))).append(" |");
            if (i>0 && (i < 8) && (i+1)%3==0){
                val.append("|");
            }
        }
        return val.toString();
    }

    protected String get9CharCandidateString(Cell cell) {
        StringBuilder val = new StringBuilder();
        for( int i = 0; i < 9; i++){
            if (!cell.isOriginal() && !cell.isSolved() && i < cell.getPossibilities().size()) {
                val.append(cell.getPossibilities().get(i));
            } else {
                val.append(" ");
            }
        }
        return val.toString();
    }
}
