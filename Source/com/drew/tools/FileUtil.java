package com.drew.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A series of utility methods for working with the file system. The methods herein are used in unit testing.
 * Use them in production code at your own risk!
 *
 * @author Drew Noakes http://drewnoakes.com
 */
public class FileUtil
{
    /**
     * Saves the contents of a <code>byte[]</code> to the specified {@link File}.
     */
    public static void saveBytes(File file, byte[] bytes) throws IOException
    {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(bytes);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * Reads the contents of a {@link File} into a <code>byte[]</code>. This relies upon <code>File.length()</code>
     * returning the correct value, which may not be the case when using a network file system. However this method is
     * intended for unit test support, in which case the files should be on the local volume.
     */
    public static byte[] readBytes(File file) throws IOException
    {
        int length = (int)file.length();
        // should only be zero if loading from a network or similar
        assert(length != 0);
        byte[] bytes = new byte[length];

        int totalBytesRead = 0;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            while (totalBytesRead != length) {
                int bytesRead = inputStream.read(bytes, totalBytesRead, length - totalBytesRead);
                if (bytesRead == -1) {
                    break;
                }
                totalBytesRead += bytesRead;
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return bytes;
    }

    /**
     * Reads the contents of a {@link File} into a <code>byte[]</code>. This relies upon <code>File.length()</code>
     * returning the correct value, which may not be the case when using a network file system. However this method is
     * intended for unit test support, in which case the files should be on the local volume.
     */
    public static byte[] readBytes(String filePath) throws IOException
    {
        return readBytes(new File(filePath));
    }
}
