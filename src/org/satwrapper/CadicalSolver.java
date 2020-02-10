package org.satwrapper;

import java.io.IOException;
import java.util.List;

public class CadicalSolver implements Solver {
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
    public boolean solve() {
        return cadical_solve(pointer) == 10;
    }

    @Override
    public boolean solve(int[] assumptions) {
        return cadical_solve(pointer, assumptions) == 10;
    }

    @Override
    public boolean solve(List<Integer> assumptions) {
        return cadical_solve(pointer, toIntArray(assumptions)) == 10;
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
    public int addLiteral() {
        return ++vars;
    }

    @Override
    public boolean[] getLiterals() {
        boolean[] result = cadical_get_literals(pointer);
        if (result == null) {
            throw new OutOfMemoryError();
        }
        return result;
    }

    @Override
    public int getNumberOfLiterals() {
        return vars;
    }

    private static native long create();

    private static native void delete(long pointer);

    private static native int cadical_solve(long pointer);

    private static native int cadical_solve(long pointer, int[] assumptions);

    private static native void cadical_add_clause(long pointer, int[] literals);

    private static native boolean[] cadical_get_literals(long pointer);
}
