package rudiment.jsoupexample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import net.alhazmy13.imagefilter.ImageFilter;

import java.io.File;
import java.util.List;

import rudiment.jsoupexample.adapter.ThumbnailsAdapter;
import rudiment.jsoupexample.databinding.ActivityPhotoFileterBinding;
import rudiment.jsoupexample.imageprocessinghelper.GPUImageFilterTools;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailCallback;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailItem;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailsManager;

public class PhotoFileterActivity extends AppCompatActivity implements ThumbnailCallback {

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private ActivityPhotoFileterBinding binding;
    private Bitmap bitmap;
    private Bitmap bitmap1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_fileter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            File file = (File) bundle.get("file");
            bitmap = getBitmapFromFile(file);
            bitmap1 = bitmap;
        } else {
            finish();
        }
        binding.placeHolderImageview.setImageBitmap(bitmap);
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
                ThumbnailItem t1 = new ThumbnailItem();
                ThumbnailItem t2 = new ThumbnailItem();
                ThumbnailItem t3 = new ThumbnailItem();
                ThumbnailItem t4 = new ThumbnailItem();
                ThumbnailItem t5 = new ThumbnailItem();
                ThumbnailItem t6 = new ThumbnailItem();

                t1.image = bitmap;
                t2.image = bitmap;
                t3.image = bitmap;
                t4.image = bitmap;
                t5.image = bitmap;
                t6.image = bitmap;
                ThumbnailsManager.clearThumbs();
                ThumbnailsManager.addThumb(t1); // Original Image

                t2.filter = SampleFilters.getStarLitFilter();
                ThumbnailsManager.addThumb(t2);

                t3.filter = SampleFilters.getBlueMessFilter();
                ThumbnailsManager.addThumb(t3);

                t4.filter = SampleFilters.getAweStruckVibeFilter();
                ThumbnailsManager.addThumb(t4);

                t5.filter = SampleFilters.getLimeStutterFilter();
                ThumbnailsManager.addThumb(t5);

                t6.filter = SampleFilters.getNightWhisperFilter();
                ThumbnailsManager.addThumb(t6);

                List<ThumbnailItem> thumbs = ThumbnailsManager.processThumbs(context);

                ThumbnailsAdapter adapter = new ThumbnailsAdapter(thumbs, (ThumbnailCallback) PhotoFileterActivity.this);
                binding.thumbnails.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    @Override
    public void onThumbnailClick(Filter filter) {
        binding.placeHolderImageview.setImageBitmap(filter.processFilter(bitmap1));
    }

    @Override
    public void onThumbnailClick(ImageFilter.Filter filter) {

    }

    @Override
    public void onThumbnailClick(GPUImageFilterTools.FilterType filter) {

    }

    private Bitmap getBitmapFromFile(File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
        return bitmap;
    }
}
