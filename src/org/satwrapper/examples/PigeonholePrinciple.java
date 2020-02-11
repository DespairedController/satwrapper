package org.satwrapper.examples;

import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

public final class PigeonholePrinciple {
    private static Solver solver;
    private static int pigeons;
    private static int[][] table;

    private static void generate() {
        for (int i = 0; i < pigeons; i++) {
            for (int j = 0; j < pigeons - 1; j++) {
                table[i][j] = solver.addLiteral();
            }
        }
        int[] clause = new int[pigeons - 1];
        for (int i = 0; i < pigeons; i++) {
            System.arraycopy(table[i], 0, clause, 0, pigeons - 1);
            solver.addClause(clause);
        }

        for (int k = 0; k < pigeons - 1; k++) {
            for (int i = 0; i < pigeons; i++) {
                for (int j = 0; j < pigeons; j++) {
                    if (i == j) continue;
                    solver.addClause(-table[i][k], -table[j][k]);
                }
            }
        }
    }

    public static boolean isPigeonholePrincipleTrue(int n) {
        pigeons = n;
        solver = new CadicalSolver();
        table = new int[pigeons][pigeons - 1];
        generate();
        return solver.solve() == 20;
    }
}