package org.satwrapper;

import java.util.List;

public interface Solver {
    boolean solve();

    boolean solve(int... assumptions);

    boolean solve(List<Integer> assumptions);

    void close();

    void addClause(int... literals);

    void addClause(List<Integer> literals);

    int addLiteral();

    boolean[] getLiterals();

    int getNumberOfLiterals();
}
