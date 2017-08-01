package rudiment.jsoupexample.imageprocessinghelper;

import android.graphics.Bitmap;

import net.alhazmy13.imagefilter.ImageFilter;

/**
 * @author Varun on 01/07/15.
 */
public class ThumbnailItem2 {
    public Bitmap image;
    public ImageFilter.Filter filter;

    public ThumbnailItem2() {
        image = null;
        filter = ImageFilter.Filter.GRAY;
    }
}
