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
            List<Integer> satClause = new ArrayList<>();
            for (int i : clause) {
                satClause.add(i > 0 ? pLit(i) : nLit(-i));
            }
            solver.addClause(satClause);
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
            if (solver.solve(-nLit(i), -pLit(i)) == 20) {
                answer.add(i);
            }
        }
        solver.close();
        return answer;
    }
}