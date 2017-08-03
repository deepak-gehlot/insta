package rudiment.jsoupexample.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import rudiment.jsoupexample.R;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailCallback;
import rudiment.jsoupexample.imageprocessinghelper.ThumbnailItem3;

public class ThumbnailsAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "THUMBNAILS_ADAPTER";
    private static int lastPosition = -1;
    private ThumbnailCallback thumbnailCallback;
    private List<ThumbnailItem3> dataSet;
    private Context context;

    public ThumbnailsAdapter3(Context context, List<ThumbnailItem3> dataSet, ThumbnailCallback thumbnailCallback) {
        Log.v(TAG, "Thumbnails Adapter has " + dataSet.size() + " items");
        this.dataSet = dataSet;
        this.thumbnailCallback = thumbnailCallback;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.v(TAG, "On Create View Holder Called");
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_thumbnail_item, viewGroup, false);
        return new ThumbnailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {
        final ThumbnailItem3 thumbnailItem = dataSet.get(i);
        Log.v(TAG, "On Bind View Called");
        ThumbnailsViewHolder thumbnailsViewHolder = (ThumbnailsViewHolder) holder;
        try {
            thumbnailsViewHolder.thumbnail.setImageBitmap(thumbnailItem.image);
            thumbnailsViewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastPosition != i) {
                        thumbnailCallback.onThumbnailClick(thumbnailItem.filter);
                        lastPosition = i;
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ThumbnailsViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;

        public ThumbnailsViewHolder(View v) {
            super(v);
            this.thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        }
    }

    private Bitmap getBitmapFromFile(File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
        return bitmap;
    }
}
