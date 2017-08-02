package rudiment.jsoupexample.imageprocessinghelper;

import android.net.Uri;

import java.io.File;

/**
 * @author Varun on 01/07/15.
 */
public class ThumbnailItem3 {
    public Uri image;
    public File imageFile;
    public GPUImageFilterTools.FilterType filter;

    public ThumbnailItem3() {
        image = null;
        filter = null;
        imageFile = null;
    }
}
