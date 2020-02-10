package org.satwrapper.examples;

import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

public final class PigeonholePrinciple {
    private static Solver solver;
    private static int n;
    private static int[] table;

    private static void generate() {
        for (int i = 0; i < table.length; i++)
            table[i] = solver.addLiteral();
        int[] clause = new int[n - 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(table, i * n, clause, 0, n - 1);
            solver.addClause(clause);
        }

        for (int k = 0; k < n - 1; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) continue;
                    solver.addClause(-table[i * n + k], -table[j * n + k]);
                }
            }
        }
    }

    public static boolean isPigeonholePrincipleTrue(int num) {
        n = num;
        solver = new CadicalSolver();
        table = new int[n * n];
        generate();
        return !solver.solve();
    }
}