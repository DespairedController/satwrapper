package org.satwrapper.examples;

import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

import java.util.LinkedList;
import java.util.List;

public final class BellNumber {
    private BellNumber() {
    }
    private static Solver generate(Solver solver, final int n) {

        for (int i = 0; i < n; i++) {
            solver.addClause(i * (n + 1) + 1);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                solver.addClause(i * n + j + 1, -(j * n + i + 1));
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    solver.addClause(-(i * n + j + 1), -(j * n + k + 1), i * n + k + 1);
                }
            }
        }
        return solver;
    }

    public static int getNthBellNumber(final int n) {
        Solver solver = generate(new CadicalSolver(), n);

        int answer = 0;
        while (solver.solve() == 10) {
            answer++;
            List<Integer> list = new LinkedList<>();
            for (int i = 0; i < n * n; i++) {
                if (solver.val(i + 1) > 0) {
                    list.add(-(i + 1));
                } else {
                    list.add(i + 1);
                }
            }
            for (int i : list) {
                solver.add(i);
            }
            solver.add(0);
        }
        solver.close();
        return answer;
    }
}
