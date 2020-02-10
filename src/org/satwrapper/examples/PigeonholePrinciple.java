package org.satwrapper.examples;

import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

import java.util.ArrayList;
import java.util.List;

public final class PigeonholePrinciple {
    private PigeonholePrinciple() {}

    private static Solver generate(int n) {
        Solver solver = new CadicalSolver();
        List<Integer> clause = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                clause.add(i * n + j);
            }
            solver.addClause(clause);
            clause.clear();
        }

        for (int k = 1; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) continue;
                    solver.addClause(-(i * n + k), -(j * n + k));
                }
            }
        }
        return solver;
    }

    public static boolean isPigeonholePrincipleTrue(final int n) {
        Solver solver = generate(n);
        return solver.solve() == 20;
    }
}
