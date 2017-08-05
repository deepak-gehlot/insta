package rudiment.jsoupexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appunite.appunitevideoplayer.PlayerActivity;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;

import rudiment.jsoupexample.FullImageActivity;
import rudiment.jsoupexample.PhotoFileterActivity2;
import rudiment.jsoupexample.R;
import rudiment.jsoupexample.databinding.DownloadListRowBinding;

/**
 * Created by RWS 6 on 7/20/2017.
 */
public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<File> files;
    private int TYPE_FREE_SPACE = 0;
    private int TYPE_VIEW = 1;

    public DownloadListAdapter(Context context, ArrayList<File> files) {
        this.files = files;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FREE_SPACE) {
            return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.download_list_row_blank, parent, false).getRoot());
        } else {
            DownloadListRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.download_list_row, parent, false);
            return new ViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FREE_SPACE) {

        } else if (getItemViewType(position) == TYPE_VIEW) {
            holder.binding.setFile(files.get(position));
            holder.binding.setAdapter(this);
            if (isVideoFile(Uri.fromFile(files.get(position)).toString())) {
                holder.binding.playButtonIcon.setVisibility(View.VISIBLE);
            } else {
                holder.binding.playButtonIcon.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == files.size() - 1) {
            return TYPE_FREE_SPACE;
        } else {
            return TYPE_VIEW;
        }
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    public void onFilterClick(File file) {
        if (isVideoFile(Uri.fromFile(file).toString())) {
            Toast.makeText(context, "You can not apply filter on video.", Toast.LENGTH_SHORT).show();
            return;
        }
        context.startActivity(new Intent(context, PhotoFileterActivity2.class).putExtra("file", file));
    }

    /**
     * on Item click listener method
     *
     * @param file
     */
    public void onShareInstaClick(File file) {
        if (isVideoFile(Uri.fromFile(file).toString())) {
            createInstagramVideoIntent(file);
        } else {
            createInstagramIntent(file);
        }
        //  context.startActivity(new Intent(context, CreateMemesActivity.class).putExtra("file", file));
        // context.startActivity(new Intent(context, PhotoFileterActivity.class).putExtra("file", file));
        //context.startActivity(new Intent(context, PhotoFileterActivity2.class).putExtra("file", file));
        //  context.startActivity(new Intent(context, PhotoFilter2Activity.class).putExtra("file", file));
        //context.startActivity(new Intent(context, CollageActivity.class));
    }

    public void onItemClick(File file) {
        if (isVideoFile(Uri.fromFile(file).toString())) {
            context.startActivity(PlayerActivity.getVideoPlayerIntent(context,
                    Uri.fromFile(file).toString(),
                    file.getName(), R.drawable.ic_video_play));
        } else {
            context.startActivity(new Intent(context, FullImageActivity.class).putExtra("item", file));
        }
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        DownloadListRowBinding binding;

        public ViewHolder(DownloadListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewHolder(View view) {
            super(view);
        }
    }

    private void createInstagramIntent(File file) {
        try {
            String type = "image/*";
            Intent shareIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
            shareIntent.setType(type);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Check this out, what do you think?"
                            + System.getProperty("line.separator")
                            + "desc");
            shareIntent.setPackage("com.instagram.android");
            context.startActivity(shareIntent);

        } catch (Exception e) {
            e.printStackTrace();
            // bring user to the market to download the app.
            // or let them choose an app?
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id="
                    + "com.instagram.android"));
            context.startActivity(intent);
        }
    }

    private void createInstagramVideoIntent(File file) {
        try {
            String type = "video/*";
            Intent shareIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
            shareIntent.setType(type);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Check this out, what do you think?"
                            + System.getProperty("line.separator")
                            + "desc");
            shareIntent.setPackage("com.instagram.android");
            context.startActivity(shareIntent);

        } catch (Exception e) {
            e.printStackTrace();
            // bring user to the market to download the app.
            // or let them choose an app?
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id="
                    + "com.instagram.android"));
            context.startActivity(intent);
        }
    }

    public void createOtherAppShareIntent(File photoFile) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(photoFile));
        context.startActivity(Intent.createChooser(shareIntent, "Share image using"));
    }
}