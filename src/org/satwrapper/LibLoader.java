package org.satwrapper;

import java.io.*;

public class LibLoader {
    private static final String LIBPREFIX = "/libs/lib";
    public static void loadLibrary(String name) throws IOException {
        String fullName = LIBPREFIX + name + ".so";
        InputStream is = LibLoader.class.getResourceAsStream(fullName);
        if (is == null) {
            throw new FileNotFoundException("File not found: " + fullName);
        }
        File temp;
        OutputStream os;
        temp = File.createTempFile("temp", name);
        os = new FileOutputStream(temp);
        try {
            byte[] buffer = new byte[4096];
            int count;
            while ((count = is.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
        } finally {
            os.close();
            is.close();
        }
        System.load(temp.getAbsolutePath());
    }
}
