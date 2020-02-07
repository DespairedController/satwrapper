package org.satwrapper;

import java.io.IOException;
import java.util.List;

public class CadicalSolver extends Solver {
    //pointer to CPP class
    private long pointer;
    private int vars;

    static {
        try {
            LibLoader.loadLibrary("cadicalsolver");
        } catch (IOException e) {
            throw new UnsatisfiedLinkError(e.getMessage());
        }
    }

    private int[] toIntArray(List<Integer> array) {
        int[] result = new int[array.size()];
        int i = 0;
        for (int element : array) {
            result[i++]=element;
        }
        return result;
    }

    public CadicalSolver() {
        pointer = 0;
        vars = 0;
        pointer = create();
        if (pointer == 0) {
            throw new OutOfMemoryError();
        }
    }

    @Override
    public void add(int lit) {
        vars++;
        cadical_add_val(pointer, lit);
    }

    @Override
    public void assume(int lit) {
        cadical_assume(pointer, lit);
    }

    @Override
    public boolean failed(int lit) {
        return cadical_failed(pointer, lit);
    }

    @Override
    public int solve() {
        return cadical_solve(pointer);
    }

    @Override
    public int val(int lit) {
        return cadical_val(pointer, lit);
    }

    @Override
    public void close() {
        if (pointer != 0) {
            delete(pointer);
            pointer = 0;
        }
    }

    @Override
    public void addClause(int[] literals) {
        for (int i : literals) {
            vars = Math.max(Math.abs(i), vars);
        }
        cadical_add_clause(pointer, literals);
    }

    @Override
    public void addClause(List<Integer> literals) {
        for (int i : literals) {
            vars = Math.max(Math.abs(i), vars);
        }
        cadical_add_clause(pointer, toIntArray(literals));
    }

    @Override
    public int addVariable() {
        return ++vars;
    }

    private native long create();

    private native void delete(long pointer);

    private native void cadical_add_val(long pointer, int val);

    private native int cadical_solve(long pointer);

    private native int cadical_val(long pointer, int lit);

    private native void cadical_assume(long pointer, int lit);

    private native boolean cadical_failed(long pointer, int lit);

    private native void cadical_add_clause(long pointer, int[] literals);

}