package rudiment.jsoupexample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.zomato.photofilters.imageprocessors.Filter;

import net.alhazmy13.imagefilter.ImageFilter;

import java.io.File;
import java.util.List;

import rudiment.jsoupexample.adapter.ThumbnailsAdapter2;
import rudiment.jsoupexample.databinding.ActivityPhotoFilter2Binding;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailCallback;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailItem2;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailsManager2;

public class PhotoFilter2Activity extends AppCompatActivity implements ThumbnailCallback {

    private ActivityPhotoFilter2Binding binding;
    private Bitmap bitmap;
    private Bitmap bitmap1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_filter2);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            File file = (File) bundle.get("file");
            bitmap = getBitmapFromFile(file);
            bitmap1 = bitmap;
        } else {
            finish();
        }
        binding.placeHolderImageview.setImageBitmap(bitmap1);
        initHorizontalList();
    }

    private void initHorizontalList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        binding.thumbnails.setLayoutManager(layoutManager);
        binding.thumbnails.setHasFixedSize(true);
        bindDataToAdapter();
    }

    private void bindDataToAdapter() {
        final Context context = this.getApplication();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                ThumbnailItem2 t1 = new ThumbnailItem2();
                ThumbnailItem2 t2 = new ThumbnailItem2();
                ThumbnailItem2 t3 = new ThumbnailItem2();
                ThumbnailItem2 t4 = new ThumbnailItem2();
                ThumbnailItem2 t5 = new ThumbnailItem2();
                ThumbnailItem2 t6 = new ThumbnailItem2();
                ThumbnailItem2 t7 = new ThumbnailItem2();
                ThumbnailItem2 t8 = new ThumbnailItem2();
                ThumbnailItem2 t9 = new ThumbnailItem2();
                ThumbnailItem2 t10 = new ThumbnailItem2();
                ThumbnailItem2 t11 = new ThumbnailItem2();
                ThumbnailItem2 t12 = new ThumbnailItem2();
                ThumbnailItem2 t13 = new ThumbnailItem2();
                ThumbnailItem2 t14 = new ThumbnailItem2();
                ThumbnailItem2 t15 = new ThumbnailItem2();
                ThumbnailItem2 t16 = new ThumbnailItem2();
                ThumbnailItem2 t17 = new ThumbnailItem2();
                ThumbnailItem2 t18 = new ThumbnailItem2();
                ThumbnailItem2 t19 = new ThumbnailItem2();
                ThumbnailItem2 t20 = new ThumbnailItem2();

                t1.image = bitmap;
                t2.image = bitmap;
                t3.image = bitmap;
                t4.image = bitmap;
                t5.image = bitmap;
                t6.image = bitmap;
                t7.image = bitmap;
                t8.image = bitmap;
                t9.image = bitmap;
                t10.image = bitmap;
                t11.image = bitmap;
                t12.image = bitmap;
                t13.image = bitmap;
                t14.image = bitmap;
                t15.image = bitmap;
                t16.image = bitmap;
                t17.image = bitmap;
                t18.image = bitmap;
                t19.image = bitmap;
                t20.image = bitmap;

                ThumbnailsManager2.clearThumbs();
                ThumbnailsManager2.addThumb(t1); // Original Image

                t2.filter = ImageFilter.Filter.GRAY;
                ThumbnailsManager2.addThumb(t2);

                t3.filter = ImageFilter.Filter.RELIEF;
                ThumbnailsManager2.addThumb(t3);

                t4.filter = ImageFilter.Filter.AVERAGE_BLUR;
                ThumbnailsManager2.addThumb(t4);

                t5.filter = ImageFilter.Filter.OIL;
                ThumbnailsManager2.addThumb(t5);

                t6.filter = ImageFilter.Filter.NEON;
                ThumbnailsManager2.addThumb(t6);

                t7.filter = ImageFilter.Filter.PIXELATE;
                ThumbnailsManager2.addThumb(t6);

                t8.filter = ImageFilter.Filter.TV;
                ThumbnailsManager2.addThumb(t8);

                t9.filter = ImageFilter.Filter.INVERT;
                ThumbnailsManager2.addThumb(t9);

                t10.filter = ImageFilter.Filter.BLOCK;
                ThumbnailsManager2.addThumb(t10);

                t11.filter = ImageFilter.Filter.OLD;
                ThumbnailsManager2.addThumb(t11);

                t12.filter = ImageFilter.Filter.SHARPEN;
                ThumbnailsManager2.addThumb(t12);

                t13.filter = ImageFilter.Filter.LIGHT;
                ThumbnailsManager2.addThumb(t13);

                t14.filter = ImageFilter.Filter.LOMO;
                ThumbnailsManager2.addThumb(t14);

                t15.filter = ImageFilter.Filter.HDR;
                ThumbnailsManager2.addThumb(t15);

                t16.filter = ImageFilter.Filter.GAUSSIAN_BLUR;
                ThumbnailsManager2.addThumb(t16);

                t17.filter = ImageFilter.Filter.SOFT_GLOW;
                ThumbnailsManager2.addThumb(t17);

                t18.filter = ImageFilter.Filter.SKETCH;
                ThumbnailsManager2.addThumb(t18);

                t19.filter = ImageFilter.Filter.MOTION_BLUR;
                ThumbnailsManager2.addThumb(t19);

                t20.filter = ImageFilter.Filter.GOTHAM;
                ThumbnailsManager2.addThumb(t20);

                List<ThumbnailItem2> thumbs = ThumbnailsManager2.processThumbs(context);

                ThumbnailsAdapter2 adapter = new ThumbnailsAdapter2(thumbs, (ThumbnailCallback) PhotoFilter2Activity.this);
                binding.thumbnails.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    @Override
    public void onThumbnailClick(Filter filter) {

    }

    @Override
    public void onThumbnailClick(ImageFilter.Filter filter) {
        binding.placeHolderImageview.setImageBitmap(ImageFilter.applyFilter(bitmap1, filter));
    }

    private Bitmap getBitmapFromFile(File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
        return bitmap;
    }
}
