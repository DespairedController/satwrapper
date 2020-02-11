package org.satwrapper.examples;

import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

import java.util.LinkedList;
import java.util.List;

public final class IncrementalPigeonholePrinciple {
    private static Solver solver;
    private static int[][] table;

    private static void generateTable(final int pigeons) {
        table = new int[pigeons][pigeons - 1];
        for (int i = 0; i < pigeons; i++) {
            for (int j = 0; j < pigeons - 1; j++) {
                table[i][j] = solver.addLiteral();
            }
        }
    }

    private static int generate(final int pigeons) {
        final int holes = pigeons - 1;
        int[] clause = new int[holes + 1];
        int activationLiteral = solver.addLiteral();
        for (int i = 0; i < pigeons; i++) {
            if (holes >= 0) System.arraycopy(table[i], 0, clause, 0, holes);
            clause[holes] = activationLiteral;
            solver.addClause(clause);
        }

        for (int i = 0; i < pigeons; i++) {
            for (int j = i + 1; j < pigeons; j++) {
                solver.addClause(-table[i][holes - 1], -table[j][holes - 1]);
            }
        }

        for (int k = 0; k < holes - 1; k++) {
            for (int i = 0; i < pigeons - 1; i++) {
                solver.addClause(-table[i][k], -table[pigeons - 1][k]);
            }
        }

        return -activationLiteral;
    }

    public static boolean isPigeonholePrincipleTrue(int n) {
        solver = new CadicalSolver();
        generateTable(n);

        for (int i = 2; i <= n; i++) {
            if (solver.solve(generate(i)) == 10) {
                return false;
            }
        }
        return true;
    }
}