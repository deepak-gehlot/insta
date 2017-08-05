package rudiment.jsoupexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        this.dataSet = dataSet;
        this.thumbnailCallback = thumbnailCallback;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ThumbnailsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_thumbnail_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {
        final ThumbnailItem3 thumbnailItem = dataSet.get(i);
        ThumbnailsViewHolder thumbnailsViewHolder = (ThumbnailsViewHolder) holder;
        thumbnailsViewHolder.titleTxt.setText(thumbnailItem.title);
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
        private TextView titleTxt;

        public ThumbnailsViewHolder(View v) {
            super(v);
            this.thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            this.titleTxt = (TextView) v.findViewById(R.id.title);
        }
    }
}
