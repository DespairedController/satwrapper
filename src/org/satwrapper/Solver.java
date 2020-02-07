package org.satwrapper;

import java.util.List;

public abstract class Solver {

//  === IPASIR ===
    public abstract void add(int lit);

    public abstract int solve();

    public abstract int val(int lit);

    public abstract void assume(int lit);

    public abstract boolean failed(int lit);
//  === END IPASIR ===

    public abstract void close();

    public abstract void addClause(int... literals);

    public abstract void addClause(List<Integer> literals);

    public abstract int addVariable();
}
