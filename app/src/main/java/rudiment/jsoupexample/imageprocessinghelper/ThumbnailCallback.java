package rudiment.jsoupexample.imageprocessinghelper;

import com.zomato.photofilters.imageprocessors.Filter;

import net.alhazmy13.imagefilter.ImageFilter;

/**
 * @author Varun on 01/07/15.
 */
public interface ThumbnailCallback {

    void onThumbnailClick(Filter filter);

    void onThumbnailClick(ImageFilter.Filter filter);

    void onThumbnailClick(GPUImageFilterTools.FilterType filter);
}
