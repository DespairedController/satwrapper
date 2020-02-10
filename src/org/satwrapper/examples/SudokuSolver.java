package org.satwrapper.examples;


import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

import java.util.ArrayList;
import java.util.List;

public final class SudokuSolver {
    private final static int size = 9;
    private static int[][][] table;
    private static Solver solver;

    private SudokuSolver() {
    }

    private static Solver generate(final int[][] field) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    table[i][j][k] = solver.addLiteral();
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] != 0) {
                    solver.addClause(table[i][j][field[i][j] - 1]);
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //each cell contains at least one copy of any number
                List<Integer> clause = new ArrayList<>();
                for (int k = 0; k < size; k++) {
                    clause.add(table[i][j][k]);
                }
                solver.addClause(clause);
                clause.clear();
                //each cell contains at most one copy of any number
                for (int k = 0; k < size; k++) {
                    for (int l = k + 1; l < size; l++) {
                        solver.addClause(-table[i][j][k], -table[i][j][l]);
                    }
                }
            }
        }

        //each column and row contains every number at least once

        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                List<Integer> row = new ArrayList<>();
                List<Integer> column = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    column.add(table[i][j][k]);
                    row.add(table[j][i][k]);
                }
                solver.addClause(row);
                solver.addClause(column);
                row.clear();
                column.clear();
            }
        }

        for (int l = 0; l <= 2; l++) {
            for (int f = 0; f <= 2; f++) {
                for (int k = 0; k < size; k++) {
                    List<Integer> clause = new ArrayList<>();
                    for (int i = l * 3; i <= l * 3 + 2; i++) {
                        for (int j = f * 3; j <= f * 3 + 2; j++) {
                            clause.add(table[i][j][k]);
                        }
                    }
                    solver.addClause(clause);
                }
            }
        }
        return solver;
    }

    public static int[][] solve(int[][] field) {
        assert field.length == size;
        assert field[0].length == size;
        table = new int[size][size][size];
        solver = new CadicalSolver();
        generate(field);
        int res = solver.solve();
        int[][] answer = new int[size][size];
        if (res == 10) {
            boolean[] literals = solver.getLiterals();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        if (literals[table[i][j][k]]) {
                            answer[i][j] = k + 1;
                            break;
                        }
                    }
                }
            }
        }
        solver.close();
        return answer;
    }
}