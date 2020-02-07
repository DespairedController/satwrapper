package org.satwrapper.examples;

import org.satwrapper.CadicalSolver;
import org.satwrapper.Solver;

import java.util.ArrayList;
import java.util.List;

public final class EssentialVariables {
    private EssentialVariables() {
    }

    private static int pLit(int lit) {
        return 2 * lit;
    }

    private static int nLit(int lit) {
        return 2 * lit - 1;
    }

    private static Solver generate(final List<List<Integer>> formula, final int literalNumber) {
        Solver solver = new CadicalSolver();

        for (List<Integer> clause : formula) {
            for (int i : clause) {
                solver.add(i > 0 ? pLit(i) : nLit(-i));
            }
            solver.add(0);
        }

        for (int i = 1; i <= literalNumber; i++) {
            solver.addClause(-nLit(i), -pLit(i));
        }
        return solver;
    }

    public static List<Integer> getEssentialVariables(final List<List<Integer>> formula, final int literalNumber) {
        Solver solver = generate(formula, literalNumber);
        List<Integer> answer = new ArrayList<>();

        for (int i = 1; i <= literalNumber; i++) {
            solver.assume(-nLit(i));
            solver.assume(-pLit(i));
            int res = solver.solve();
            if (res == 20) {
                answer.add(i);
            }
        }
        solver.close();
        return answer;
    }
}
