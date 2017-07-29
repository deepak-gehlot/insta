package rudiment.jsoupexample.util;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by RWS 6 on 7/29/2017.
 */

public class VideoFileFilter implements FileFilter {
    File file;
    private final String[] okFileExtensions = new String[]{"mp4"};

    /**
     *
     */
    public VideoFileFilter(File newfile) {
        this.file = newfile;
    }

    public boolean accept(File file) {
        for (String extension : okFileExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}