package org.satwrapper.examples;


import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

public final class SudokuSolver {
    final static int size = 9;

    private SudokuSolver() {
    }

    private static int getLiteral(int i, int j, int k) {
        return size * size * i + size * j + k;
    }

    private static Solver generate(int[][] table) {
        Solver solver = new CadicalSolver();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (table[i][j] != 0) {
                    solver.addClause(getLiteral(i + 1, j + 1, table[i][j]));
                }
            }
        }

        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                //each cell contains at least one copy of any number
                for (int k = 1; k <= size; k++) {
                    solver.add(getLiteral(i, j, k));
                }
                solver.add(0);
                //each cell contains at most one copy of any number
                for (int k = 1; k <= size; k++) {
                    for (int l = k + 1; l <= size; l++) {
                        solver.addClause(-getLiteral(i, j, k), -getLiteral(i, j, l));
                    }
                }
            }
        }

        //each column contains every number at least once
        for (int i = 1; i <= size; i++) {
            for (int k = 1; k <= size; k++) {
                for (int j = 1; j <= size; j++) {
                    solver.add(getLiteral(i, j, k));
                }
                solver.add(0);
            }
        }

        //each row contains every number at least once
        for (int j = 1; j <= size; j++) {
            for (int k = 1; k <= size; k++) {
                for (int i = 1; i <= size; i++) {
                    solver.add(getLiteral(i, j, k));
                }
                solver.add(0);
            }
        }

        for (int l = 0; l <= 2; l++) {
            for (int f = 0; f <= 2; f++) {
                for (int k = 1; k <= size; k++) {
                    for (int i = l * 3 + 1; i <= l * 3 + 3; i++) {
                        for (int j = f * 3 + 1; j <= f * 3 + 3; j++) {
                            solver.add(getLiteral(i, j, k));
                        }
                    }
                    solver.add(0);
                }
            }
        }
        return solver;
    }

    public static int[][] solve(int[][] table) {
        assert table.length == 9;
        assert table[0].length == 9;
        Solver solver = generate(table);
        int res = solver.solve();
        int[][] answer = new int[size][size];
        if (res == 10) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int k = 1; k <= size; k++) {
                        int r = solver.val(getLiteral(i + 1, j + 1, k));
                        if (r > 0) {
                            answer[i][j] = k;
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
