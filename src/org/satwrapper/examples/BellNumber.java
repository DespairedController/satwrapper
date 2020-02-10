package org.satwrapper.examples;

import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

public final class BellNumber {
    private BellNumber() {
    }

    private static int[] table;
    private static Solver solver;
    private static int n;

    private static void generate() {
        for (int i = 0; i < table.length; i++) {
            table[i] = solver.addLiteral();
        }

        for (int i = 0; i < n; i++) {
            solver.addClause(table[i * (n + 1)]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                solver.addClause(table[i * n + j], -table[j * n + i]);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    solver.addClause(-table[i * n + j], -table[j * n + k], table[i * n + k]);
                }
            }
        }
    }

    public static int getNthBellNumber(final int num) {
        n = num;
        table = new int[n * n];
        solver = new CadicalSolver();
        generate();

        int[] clause = new int[table.length];
        int answer = 0;
        while (solver.solve()) {
            answer++;
            boolean[] result = solver.getLiterals();
            for (int i = 0; i < table.length; i++) {
                clause[i] = result[table[i]] ? -table[i] : table[i];
            }
            solver.addClause(clause);
        }
        solver.close();
        return answer;
    }
}
