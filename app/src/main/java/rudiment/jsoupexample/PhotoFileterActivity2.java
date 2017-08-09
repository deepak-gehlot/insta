package rudiment.jsoupexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.zomato.photofilters.imageprocessors.Filter;

import net.alhazmy13.imagefilter.ImageFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import rudiment.jsoupexample.adapter.ThumbnailsAdapter3;
import rudiment.jsoupexample.imageprocessinghelper.GPUImageFilterTools;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailCallback;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailItem3;
import rudiment.jsoupexample.util.Constant;

public class PhotoFileterActivity2 extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        View.OnClickListener, GPUImageView.OnPictureSavedListener, ThumbnailCallback {

    private GPUImageFilter mFilter;
    private GPUImageFilterTools.FilterAdjuster mFilterAdjuster;
    private GPUImageView mGPUImageView;
    private Uri imageUri = null;
    private File imageFile = null;
    private RecyclerView filterListView;
    private List<ThumbnailItem3> thumbs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_fileter2);

        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(this);
        findViewById(R.id.button_save).setOnClickListener(this);

        mGPUImageView = (GPUImageView) findViewById(R.id.gpuimage);
        mGPUImageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        filterListView = (RecyclerView) findViewById(R.id.filterListView);
        filterListView.setLayoutManager(new LinearLayoutManager(PhotoFileterActivity2.this, LinearLayoutManager.HORIZONTAL, false));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageFile = (File) bundle.get("file");
            //  bitmap_Source = getBitmapFromFile(file);
            handleImage(Uri.fromFile(imageFile));
        } else {
            finish();
        }

        bindDataToAdapter();

        AdRequest adRequest = new AdRequest.Builder().build();
        ((AdView) findViewById(R.id.adView)).loadAd(adRequest);

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
           /* case R.id.button_choose_filter:
                GPUImageFilterTools.showDialog(this, new GPUImageFilterTools.OnGpuImageFilterChosenListener() {

                    @Override
                    public void onGpuImageFilterChosenListener(final GPUImageFilter filter) {
                        switchFilterTo(filter);
                        mGPUImageView.requestRender();
                    }
                });
                break;*/
            case R.id.button_save:
                saveImage();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPictureSaved(final Uri uri) {
        Toast.makeText(this, "Saved: " + uri.toString(), Toast.LENGTH_SHORT).show();
    }

    private void bindDataToAdapter() {
        final Context context = this.getApplication();
        final ProgressDialog progressDialog = ProgressDialog.show(PhotoFileterActivity2.this, "", "Loading...", false, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThumbnailItem3 t1 = new ThumbnailItem3();
                ThumbnailItem3 t2 = new ThumbnailItem3();
                ThumbnailItem3 t3 = new ThumbnailItem3();
                ThumbnailItem3 t4 = new ThumbnailItem3();
                ThumbnailItem3 t5 = new ThumbnailItem3();
                ThumbnailItem3 t6 = new ThumbnailItem3();
                ThumbnailItem3 t7 = new ThumbnailItem3();
                ThumbnailItem3 t8 = new ThumbnailItem3();
                ThumbnailItem3 t9 = new ThumbnailItem3();
                ThumbnailItem3 t10 = new ThumbnailItem3();
                ThumbnailItem3 t11 = new ThumbnailItem3();
                ThumbnailItem3 t12 = new ThumbnailItem3();
                ThumbnailItem3 t13 = new ThumbnailItem3();
                ThumbnailItem3 t14 = new ThumbnailItem3();
                ThumbnailItem3 t15 = new ThumbnailItem3();
                ThumbnailItem3 t16 = new ThumbnailItem3();
                ThumbnailItem3 t17 = new ThumbnailItem3();
                ThumbnailItem3 t18 = new ThumbnailItem3();
                ThumbnailItem3 t19 = new ThumbnailItem3();
                ThumbnailItem3 t20 = new ThumbnailItem3();
                ThumbnailItem3 t21 = new ThumbnailItem3();
                ThumbnailItem3 t22 = new ThumbnailItem3();
                ThumbnailItem3 t23 = new ThumbnailItem3();
                ThumbnailItem3 t24 = new ThumbnailItem3();
                ThumbnailItem3 t25 = new ThumbnailItem3();
                ThumbnailItem3 t26 = new ThumbnailItem3();
                ThumbnailItem3 t27 = new ThumbnailItem3();
                ThumbnailItem3 t28 = new ThumbnailItem3();
                ThumbnailItem3 t29 = new ThumbnailItem3();
                ThumbnailItem3 t30 = new ThumbnailItem3();
                ThumbnailItem3 t31 = new ThumbnailItem3();

                t1.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.CONTRAST);
                t1.filter = GPUImageFilterTools.FilterType.CONTRAST;
                t1.title = "CONTRAST";

                t2.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.GRAYSCALE);
                t2.filter = GPUImageFilterTools.FilterType.GRAYSCALE;
                t2.title = "GRAYSCALE";

                t3.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.SHARPEN);
                t3.filter = GPUImageFilterTools.FilterType.SHARPEN;
                t3.title = "SHARPEN";

                t4.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.SEPIA);
                t4.filter = GPUImageFilterTools.FilterType.SEPIA;
                t4.title = "SEPIA";

                t5.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.SOBEL_EDGE_DETECTION);
                t5.filter = GPUImageFilterTools.FilterType.SOBEL_EDGE_DETECTION;
                t5.title = "EDGE DETECTION";

                t6.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.THREE_X_THREE_CONVOLUTION);
                t6.filter = GPUImageFilterTools.FilterType.THREE_X_THREE_CONVOLUTION;
                t6.title = "CONVOLUTION";

                t7.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.FILTER_GROUP);
                t7.filter = GPUImageFilterTools.FilterType.FILTER_GROUP;
                t7.title = "FILTER GROUP";

                t8.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.EMBOSS);
                t8.filter = GPUImageFilterTools.FilterType.EMBOSS;
                t8.title = "EMBOSS";

                t9.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.POSTERIZE);
                t9.filter = GPUImageFilterTools.FilterType.POSTERIZE;
                t9.title = "POSTERIZE";

                t10.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.GAMMA);
                t10.filter = GPUImageFilterTools.FilterType.GAMMA;
                t10.title = "GAMMA";

                t11.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.BRIGHTNESS);
                t11.filter = GPUImageFilterTools.FilterType.BRIGHTNESS;
                t11.title = "BRIGHTNESS";

                t12.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.INVERT);
                t12.filter = GPUImageFilterTools.FilterType.INVERT;
                t12.title = "INVERT";

                t13.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.HUE);
                t13.filter = GPUImageFilterTools.FilterType.HUE;
                t13.title = "HUE";

                t14.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.PIXELATION);
                t14.filter = GPUImageFilterTools.FilterType.PIXELATION;
                t14.title = "PIXELATION";

                t15.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.SATURATION);
                t15.filter = GPUImageFilterTools.FilterType.SATURATION;
                t15.title = "SATURATION";

                t16.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.EXPOSURE);
                t16.filter = GPUImageFilterTools.FilterType.EXPOSURE;
                t16.title = "EXPOSURE";

                t17.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.HIGHLIGHT_SHADOW);
                t17.filter = GPUImageFilterTools.FilterType.HIGHLIGHT_SHADOW;
                t17.title = "HIGHLIGHT SHADOW";

                t18.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.MONOCHROME);
                t18.filter = GPUImageFilterTools.FilterType.MONOCHROME;
                t18.title = "MONOCHROME";

                t19.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.OPACITY);
                t19.filter = GPUImageFilterTools.FilterType.OPACITY;
                t19.title = "OPACITY";

                t20.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.RGB);
                t20.filter = GPUImageFilterTools.FilterType.RGB;
                t20.title = "RGB";

                t21.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.WHITE_BALANCE);
                t21.filter = GPUImageFilterTools.FilterType.WHITE_BALANCE;
                t21.title = "WHITE BALANCE";

                t22.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.VIGNETTE);
                t22.filter = GPUImageFilterTools.FilterType.VIGNETTE;
                t22.title = "";

                t23.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.TONE_CURVE);
                t23.filter = GPUImageFilterTools.FilterType.TONE_CURVE;
                t23.title = "VIGNETTE";

                t24.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.LOOKUP_AMATORKA);
                t24.filter = GPUImageFilterTools.FilterType.LOOKUP_AMATORKA;
                t24.title = "LOOKUP AMATORKA";

                t25.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.CROSSHATCH);
                t25.filter = GPUImageFilterTools.FilterType.CROSSHATCH;
                t25.title = "CROSSHATCH";

                t26.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.BOX_BLUR);
                t26.filter = GPUImageFilterTools.FilterType.BOX_BLUR;
                t26.title = "BOX BLUR";

                t27.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.CGA_COLORSPACE);
                t27.filter = GPUImageFilterTools.FilterType.CGA_COLORSPACE;
                t27.title = "COLORSPACE";

                t28.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.DILATION);
                t28.filter = GPUImageFilterTools.FilterType.DILATION;
                t28.title = "DILATION";

                t29.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.KUWAHARA);
                t29.filter = GPUImageFilterTools.FilterType.KUWAHARA;
                t29.title = "KUWAHARA";

                t30.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.RGB_DILATION);
                t30.filter = GPUImageFilterTools.FilterType.RGB_DILATION;
                t30.title = "DILATION";

                t31.image = getFilterThumbBitmap(PhotoFileterActivity2.this, GPUImageFilterTools.FilterType.SKETCH);
                t31.filter = GPUImageFilterTools.FilterType.SKETCH;
                t31.title = "SKETCH";

                thumbs = new ArrayList<>();
                thumbs.add(t1);
                thumbs.add(t2);
                thumbs.add(t3);
                thumbs.add(t4);
                thumbs.add(t5);
                thumbs.add(t6);
                thumbs.add(t7);
                thumbs.add(t8);
                thumbs.add(t9);
                thumbs.add(t10);
                thumbs.add(t11);
                thumbs.add(t12);
                thumbs.add(t13);
                thumbs.add(t14);
                thumbs.add(t15);
                thumbs.add(t16);
                thumbs.add(t17);
                thumbs.add(t18);
                thumbs.add(t19);
                thumbs.add(t20);
                thumbs.add(t21);
                thumbs.add(t22);
                thumbs.add(t23);
                thumbs.add(t24);
                thumbs.add(t25);
                thumbs.add(t26);
                thumbs.add(t27);
                thumbs.add(t28);
                thumbs.add(t29);
                thumbs.add(t30);
                thumbs.add(t31);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ThumbnailsAdapter3 adapter = new ThumbnailsAdapter3(PhotoFileterActivity2.this, thumbs, (ThumbnailCallback) PhotoFileterActivity2.this);
                        filterListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }

    private void saveImage() {
        Bitmap bitmap = mGPUImageView.getGPUImage().getBitmapWithFilterApplied();
        saveBitmap(bitmap);
       /* String fileName = System.currentTimeMillis() + ".jpg";
        mGPUImageView.saveToPictures(Constant.FOLDER_NAME, fileName, this);*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 300);
//        mGPUImageView.saveToPictures("GPUImage", fileName, 1600, 1600, this);
    }

    private void switchFilterTo(final GPUImageFilter filter) {
        if (mFilter == null
                || (filter != null && !mFilter.getClass().equals(filter.getClass()))) {
            mFilter = filter;
            mGPUImageView.setFilter(mFilter);
            mFilterAdjuster = new GPUImageFilterTools.FilterAdjuster(mFilter);

            findViewById(R.id.seekBar).setVisibility(
                    mFilterAdjuster.canAdjust() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
        if (mFilterAdjuster != null) {
            mFilterAdjuster.adjust(progress);
        }
        mGPUImageView.requestRender();
    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {
    }


    @Override
    public void onThumbnailClick(Filter filter) {

    }

    @Override
    public void onThumbnailClick(ImageFilter.Filter filter) {

    }

    @Override
    public void onThumbnailClick(GPUImageFilterTools.FilterType filter) {
        switchFilterTo(GPUImageFilterTools.createFilterForType(PhotoFileterActivity2.this, filter));
    }

    private void handleImage(final Uri selectedImage) {
        mGPUImageView.setImage(selectedImage);
    }

    private Bitmap getBitmapFromFile(File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
        return bitmap;
    }

    private Bitmap getFilterThumbBitmap(Context context, GPUImageFilterTools.FilterType filterType) {
        GPUImage gpuImage = new GPUImage(context);
        gpuImage.setImage(getBitmapFromFile(imageFile));
        gpuImage.setFilter(GPUImageFilterTools.createFilterForType(context, filterType));
        gpuImage.requestRender();
        return gpuImage.getBitmapWithFilterApplied();
    }

    private void saveBitmap(Bitmap bitmap) {
        File path = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String mImageName = "INSTA_" + timeStamp + ".jpg";
        File dest = new File(path, Constant.FOLDER_NAME + "/" + mImageName);
        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
