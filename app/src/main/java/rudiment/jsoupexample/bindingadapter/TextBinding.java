package rudiment.jsoupexample.bindingadapter;

import android.databinding.BindingAdapter;
import android.widget.TextView;

/**
 * Created by RWS 6 on 7/25/2017.
 */

public class TextBinding {

    @BindingAdapter({"bind:textColor"})
    public static void setTextColor(TextView textView, int color) {
        textView.setTextColor(color);
    }

    /*android:shadowColor="#000000"
    android:shadowDx="1.5"
    android:shadowDy="1.3"
    android:shadowRadius="1.6"*/
}
