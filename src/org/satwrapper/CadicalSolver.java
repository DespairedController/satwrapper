package org.satwrapper;

import java.io.File;
import java.io.IOException;

public class CadicalSolver extends Solver{
    //pointer to CPP class
    private long pointer;

    static {
        File file = new File("./libs/libcadicalsolver.so");
        try {
            System.load(file.getCanonicalPath());
        } catch (IOException e) {
            System.out.println("lol");
        }
    }

    public CadicalSolver() {
        pointer = 0;
        pointer = create();
        if (pointer == 0) {
            throw new OutOfMemoryError();
        }
    }

    public void close() {
        if (pointer != 0) {
            delete(pointer);
            pointer = 0;
        }
    }

    public void add(int lit) {
        cadical_add_val(pointer, lit);
    }

    public void assume(int lit) {
        cadical_assume(pointer, lit);
    }

    public boolean failed(int lit) {
        return cadical_failed(pointer, lit);
    }

    public int solve() {
        return cadical_solve(pointer);
    }

    public int val(int lit) {
        return cadical_val(pointer, lit);
    };

    private native long create();

    private native void delete(long pointer);

    private native void cadical_add_val(long pointer, int val);

    private native int cadical_solve(long pointer);

    private native int cadical_val(long pointer, int lit);

    private native void cadical_assume(long pointer, int lit);

    private native boolean cadical_failed(long pointer, int lit);
}
