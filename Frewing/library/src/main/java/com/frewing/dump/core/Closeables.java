package com.frewing.dump.core;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

/**
 * Created by fujianguo on 17-7-30.
 */
public class Closeables {

    public static void closeQuietly(ZipOutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
