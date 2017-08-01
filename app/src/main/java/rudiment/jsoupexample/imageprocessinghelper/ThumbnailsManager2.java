package rudiment.jsoupexample.imageprocessinghelper;

import android.content.Context;
import android.graphics.Bitmap;

import net.alhazmy13.imagefilter.ImageFilter;

import java.util.ArrayList;
import java.util.List;

import rudiment.jsoupexample.R;
import rudiment.jsoupexample.util.GeneralUtils;

/**
 * @author Varun on 30/06/15.
 *         <p/>
 *         Singleton Class Used to Manage filters and process them all at once
 */
public final class ThumbnailsManager2 {
    private static List<ThumbnailItem2> filterThumbs = new ArrayList<ThumbnailItem2>(20);
    private static List<ThumbnailItem2> processedThumbs = new ArrayList<ThumbnailItem2>(20);

    private ThumbnailsManager2() {
    }

    public static void addThumb(ThumbnailItem2 thumbnailItem) {
        filterThumbs.add(thumbnailItem);
    }

    public static List<ThumbnailItem2> processThumbs(Context context) {
        for (ThumbnailItem2 thumb : filterThumbs) {
            // scaling down the image
            float size = context.getResources().getDimension(R.dimen.thumbnail_size);
            thumb.image = Bitmap.createScaledBitmap(thumb.image, (int) size, (int) size, false);
            thumb.image = ImageFilter.applyFilter(thumb.image, thumb.filter);
            //cropping circle
            thumb.image = GeneralUtils.generateCircularBitmap(thumb.image);
            processedThumbs.add(thumb);
        }
        return processedThumbs;
    }

    public static void clearThumbs() {
        filterThumbs = new ArrayList<>();
        processedThumbs = new ArrayList<>();
    }
}
