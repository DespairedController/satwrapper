package org.satwrapper;

import java.util.List;

public interface Solver extends AutoCloseable {

    //  === IPASIR ===
    void add(int lit);

    int solve();

    int val(int lit);

    void assume(int lit);

    boolean failed(int lit);

    //  === END IPASIR ===

    boolean frozen(int lit);

    void freeze(int lit);

    void melt(int lit);

    int fixed(int lit);

    // solve under assumption
    int solve(int lit);

    int solve(int lit1, int lit2);

    int solve(int lit1, int lit2, int lit3);

    int solve(int[] literals);

    int solve(List<Integer> literals);

    @Override
    void close();

    void addClause(int lit);

    void addClause(int lit1, int lit2);

    void addClause(int lit1, int lit2, int lit3);

    void addClause(int... literals);

    void addClause(List<Integer> literals);

    int addLiteral();

    boolean[] getLiterals();

    int getNumberOfLiterals();

    int getNumberOfClauses();
}
