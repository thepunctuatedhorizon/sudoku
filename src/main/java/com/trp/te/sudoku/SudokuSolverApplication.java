package com.trp.te.sudoku;

import com.trp.te.sudoku.parts.Puzzle;
import com.trp.te.sudoku.solver.Solver;
import com.trp.te.sudoku.util.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SudokuSolverApplication {

    public static void main(String[] args) {
//        Starting point
//        String originalBoard = "002060010" +
//                "013405006" +
//                "004900003" +
//                "060843501" +
//                "800000007" +
//                "501679020" +
//                "100004700" +
//                "400102350" +
//                "090080100";
//
//        String solution = "982367415" +
//                "713425986" +
//                "654918273" +
//                "267843591" +
//                "849251637" +
//                "531679824" +
//                "128534769" +
//                "476192358" +
//                "395786142";
//
//        // Start
//        Puzzle puzzle = Util.setupFromString(originalBoard);
//
//        log.info("Puzzle: \n{}", puzzle);
//        log.info("Puzzle from columns: \n{}", puzzle.printOutColumns());
//        log.info("Puzzle from panes\n{}", puzzle.printOutPanes());
//
//        // Now we start solving.
//
//        Solver solver = new Solver();
//
//        solver.solve(puzzle);
//
//        log.info("Puzzle: \n{}", puzzle);


        // LET"S DO IT FOR THE NEW BOARD
        String originalBoard =
                "002060010" +
                "013405006" +
                "004900003" +
                "060843501" +
                "800000007" +
                "501679020" +
                "100004700" +
                "400102350" +
                "090080100";

        // Start
        Puzzle puzzle = Util.setupFromString(originalBoard);

        log.info("Puzzle: \n{}", puzzle);

        // Now we start solving.

        Solver solver = new Solver();

        int cyclesToSolve = solver.solve(puzzle);
        if (cyclesToSolve > 10) {
            log.error("PUZZLE DIDN'T SOLVE WITHIN CYCLE LIMIT.");
        }

        log.info("Puzzle Solved in {} cycles. Solution: \n{}", cyclesToSolve, puzzle);

    }
}
