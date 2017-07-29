package rudiment.jsoupexample.bindingadapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by RWS 6 on 7/20/2017.
 */

public class ImageBinding {

    @BindingAdapter({"bind:imageFile"})
    public static void loadImageFromFile(ImageView imageView, File file) {
        Glide.with(imageView.getContext())
                .load(file)
                .into(imageView);
    }

    @BindingAdapter({"bind:imageFileBlur"})
    public static void loadImageFromFileBlur(ImageView imageView, File file) {
        Glide.with(imageView.getContext())
                .load(file)
                .bitmapTransform(new BlurTransformation(imageView.getContext(), 30, 10))
                .into(imageView);
    }
}
