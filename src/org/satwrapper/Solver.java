package org.satwrapper;

public abstract class Solver {
    public abstract void add(int lit);

    public abstract int solve();

    public abstract int val(int lit);

    public abstract void assume(int lit);

    public abstract boolean failed(int lit);

    public abstract void close();
}
