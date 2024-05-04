package com.trp.te.sudoku.util;

import com.trp.te.sudoku.exceptions.SetupException;
import com.trp.te.sudoku.parts.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Util {

    public static List<Integer> getNewFullCandidateList() {
        List<Integer> list = new ArrayList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        return list;
    }

    public static Puzzle setupFromString(String init) {
        // Check string
        if (init == null || init.isEmpty()){
            log.error("String was {}", (init == null) ? "null." : "empty.");
            throw new SetupException("String was empty or null.");
        }
        if (init.length() != 81){
            log.error("String was not 81 chars. len {}", init.length());
            throw new SetupException("String was not the right length. Len: " + init.length());
        }

        // Get a list of strings representing each row.
        List<String> rowsList = getStrings(init);

        // Then get the rows from this
        Map<Integer, Row> rows = getIntegerRowMap(rowsList);

        // Using the rows, we can fill the columns.
        Map<Integer, Column> columnMap = getIntegerColumnMap(rows);

        Map<Integer, Pane> paneMap = getPaneMap(rows);


        Grid grid = new Grid();
        grid.setRowMap(rows);
        grid.setColumnMap(columnMap);
        grid.setPaneMap(paneMap);

        Puzzle puzzle = new Puzzle(grid);
        puzzle.setGrid(grid);
        return puzzle;
    }


    private static List<String> getStrings(String init) {
        List<String> rowsList = new ArrayList<>();
        // Parse input string
        for(int i = 0; i < 9; i++) {
            String row = init.substring(i*9, (i+1)*9);
            rowsList.add(row);
        }
        return rowsList;
    }

    private static Map<Integer, Row> getIntegerRowMap(List<String> rowsList) {
        Map<Integer, Row> rows = new HashMap<>();

        int rowNum = 0;
        for(String rowSlug : rowsList) {

            Map<Integer, Cell> cells = new HashMap<>();

            for (int i = 0; i < 9; i++) {
                // TODO: CARE? try catch?
                int value = Integer.parseInt(rowSlug.substring(i,i+1));

                boolean solved = value > 0;
                boolean original = value > 0;
                List<Integer> valsPossible = Collections.emptyList();
                if (!solved) {
                    valsPossible = getNewFullCandidateList();
                }
                Cell cell = new Cell(solved, original, value, valsPossible);
                cells.put(i, cell);
            }
            Row row = new Row(cells, computeFoundValuesForMap(cells));
            row.setCells(cells);
            rows.put(rowNum, row);
            rowNum++;
        }
        return rows;
    }

    private static Map<Integer, Column> getIntegerColumnMap(Map<Integer, Row> rows) {
        Map<Integer, Column> columnMap = new HashMap<>();
        // Now to do the columns
        for(int i = 0; i < 9; i++){

            Map<Integer, Cell> cellMapCols = new HashMap<>();

            final int finalI = i;

            rows.forEach((idx, row) -> cellMapCols.put(idx, row.getCells().get(finalI)));

            Column column = new Column(cellMapCols, computeFoundValuesForMap(cellMapCols));
            columnMap.put(i, column);

        }
        return columnMap;
    }

    private static Map<Integer, Pane> getPaneMap(Map<Integer, Row> rows) {

        Map<Integer, Cell> pane1Map = new HashMap<>();
        pane1Map.put(0, rows.get(0).getCells().get(0));
        pane1Map.put(1, rows.get(0).getCells().get(1));
        pane1Map.put(2, rows.get(0).getCells().get(2));
        pane1Map.put(3, rows.get(1).getCells().get(0));
        pane1Map.put(4, rows.get(1).getCells().get(1));
        pane1Map.put(5, rows.get(1).getCells().get(2));
        pane1Map.put(6, rows.get(2).getCells().get(0));
        pane1Map.put(7, rows.get(2).getCells().get(1));
        pane1Map.put(8, rows.get(2).getCells().get(2));
        Pane pane1 = new Pane(pane1Map, computeFoundValuesForMap(pane1Map));

        Map<Integer, Cell> pane2Map = new HashMap<>();
        pane2Map.put(0, rows.get(0).getCells().get(3));
        pane2Map.put(1, rows.get(0).getCells().get(4));
        pane2Map.put(2, rows.get(0).getCells().get(5));
        pane2Map.put(3, rows.get(1).getCells().get(3));
        pane2Map.put(4, rows.get(1).getCells().get(4));
        pane2Map.put(5, rows.get(1).getCells().get(5));
        pane2Map.put(6, rows.get(2).getCells().get(3));
        pane2Map.put(7, rows.get(2).getCells().get(4));
        pane2Map.put(8, rows.get(2).getCells().get(5));
        Pane pane2 = new Pane(pane2Map, computeFoundValuesForMap(pane2Map));

        Map<Integer, Cell> pane3Map = new HashMap<>();
        pane3Map.put(0, rows.get(0).getCells().get(6));
        pane3Map.put(1, rows.get(0).getCells().get(7));
        pane3Map.put(2, rows.get(0).getCells().get(8));
        pane3Map.put(3, rows.get(1).getCells().get(6));
        pane3Map.put(4, rows.get(1).getCells().get(7));
        pane3Map.put(5, rows.get(1).getCells().get(8));
        pane3Map.put(6, rows.get(2).getCells().get(6));
        pane3Map.put(7, rows.get(2).getCells().get(7));
        pane3Map.put(8, rows.get(2).getCells().get(8));
        Pane pane3 = new Pane(pane3Map, computeFoundValuesForMap(pane3Map));

        Map<Integer, Cell> pane4Map = new HashMap<>();
        pane4Map.put(0, rows.get(3).getCells().get(0));
        pane4Map.put(1, rows.get(3).getCells().get(1));
        pane4Map.put(2, rows.get(3).getCells().get(2));
        pane4Map.put(3, rows.get(4).getCells().get(0));
        pane4Map.put(4, rows.get(4).getCells().get(1));
        pane4Map.put(5, rows.get(4).getCells().get(2));
        pane4Map.put(6, rows.get(5).getCells().get(0));
        pane4Map.put(7, rows.get(5).getCells().get(1));
        pane4Map.put(8, rows.get(5).getCells().get(2));
        Pane pane4 = new Pane(pane4Map, computeFoundValuesForMap(pane4Map));

        Map<Integer, Cell> pane5Map = new HashMap<>();
        pane5Map.put(0, rows.get(3).getCells().get(3));
        pane5Map.put(1, rows.get(3).getCells().get(4));
        pane5Map.put(2, rows.get(3).getCells().get(5));
        pane5Map.put(3, rows.get(4).getCells().get(3));
        pane5Map.put(4, rows.get(4).getCells().get(4));
        pane5Map.put(5, rows.get(4).getCells().get(5));
        pane5Map.put(6, rows.get(5).getCells().get(3));
        pane5Map.put(7, rows.get(5).getCells().get(4));
        pane5Map.put(8, rows.get(5).getCells().get(5));
        Pane pane5 = new Pane(pane5Map, computeFoundValuesForMap(pane5Map));

        Map<Integer, Cell> pane6Map = new HashMap<>();
        pane6Map.put(0, rows.get(3).getCells().get(6));
        pane6Map.put(1, rows.get(3).getCells().get(7));
        pane6Map.put(2, rows.get(3).getCells().get(8));
        pane6Map.put(3, rows.get(4).getCells().get(6));
        pane6Map.put(4, rows.get(4).getCells().get(7));
        pane6Map.put(5, rows.get(4).getCells().get(8));
        pane6Map.put(6, rows.get(5).getCells().get(6));
        pane6Map.put(7, rows.get(5).getCells().get(7));
        pane6Map.put(8, rows.get(5).getCells().get(8));
        Pane pane6 = new Pane(pane6Map, computeFoundValuesForMap(pane6Map));

        Map<Integer, Cell> pane7Map = new HashMap<>();
        pane7Map.put(0, rows.get(6).getCells().get(0));
        pane7Map.put(1, rows.get(6).getCells().get(1));
        pane7Map.put(2, rows.get(6).getCells().get(2));
        pane7Map.put(3, rows.get(7).getCells().get(0));
        pane7Map.put(4, rows.get(7).getCells().get(1));
        pane7Map.put(5, rows.get(7).getCells().get(2));
        pane7Map.put(6, rows.get(8).getCells().get(0));
        pane7Map.put(7, rows.get(8).getCells().get(1));
        pane7Map.put(8, rows.get(8).getCells().get(2));
        Pane pane7 = new Pane(pane7Map, computeFoundValuesForMap(pane7Map));

        Map<Integer, Cell> pane8Map = new HashMap<>();
        pane8Map.put(0, rows.get(6).getCells().get(3));
        pane8Map.put(1, rows.get(6).getCells().get(4));
        pane8Map.put(2, rows.get(6).getCells().get(5));
        pane8Map.put(3, rows.get(7).getCells().get(3));
        pane8Map.put(4, rows.get(7).getCells().get(4));
        pane8Map.put(5, rows.get(7).getCells().get(5));
        pane8Map.put(6, rows.get(8).getCells().get(3));
        pane8Map.put(7, rows.get(8).getCells().get(4));
        pane8Map.put(8, rows.get(8).getCells().get(5));
        Pane pane8 = new Pane(pane8Map, computeFoundValuesForMap(pane8Map));

        Map<Integer, Cell> pane9Map = new HashMap<>();
        pane9Map.put(0, rows.get(6).getCells().get(6));
        pane9Map.put(1, rows.get(6).getCells().get(7));
        pane9Map.put(2, rows.get(6).getCells().get(8));
        pane9Map.put(3, rows.get(7).getCells().get(6));
        pane9Map.put(4, rows.get(7).getCells().get(7));
        pane9Map.put(5, rows.get(7).getCells().get(8));
        pane9Map.put(6, rows.get(8).getCells().get(6));
        pane9Map.put(7, rows.get(8).getCells().get(7));
        pane9Map.put(8, rows.get(8).getCells().get(8));
        Pane pane9 = new Pane(pane9Map, computeFoundValuesForMap(pane9Map));

        Map<Integer, Pane> paneMap = new HashMap<>();
        paneMap.put(0, pane1);
        paneMap.put(1, pane2);
        paneMap.put(2, pane3);
        paneMap.put(3, pane4);
        paneMap.put(4, pane5);
        paneMap.put(5, pane6);
        paneMap.put(6, pane7);
        paneMap.put(7, pane8);
        paneMap.put(8, pane9);
        return paneMap;
    }

    public static int[] computeFoundValuesForMap(Map<Integer, Cell> cellMap) {

        List<Integer> found = new ArrayList<>();
        cellMap.values().forEach((cell) -> {
            if (cell.isSolved() || cell.isOriginal()){
                found.add(cell.getValue());
            }
        });

        int[] foundArr = new int[found.size()];
        for (int i=0; i < found.size(); i++){
            foundArr[i] = found.get(i);
        }
        return foundArr;
    }
}
