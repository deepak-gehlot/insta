package rudiment.jsoupexample.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import rudiment.jsoupexample.R;
import rudiment.jsoupexample.Util;
import rudiment.jsoupexample.databinding.FragmentDownloadBinding;
import rudiment.jsoupexample.util.Clipboard_Utils;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadFragment extends Fragment {

    private String url = "";
    FragmentDownloadBinding binding;
    private int SOURCE_TYPE = 1; // 1 = image, 2 = video

    public DownloadFragment() {
        // Required empty public constructor
    }

    public static DownloadFragment newInstance() {
        DownloadFragment fragment = new DownloadFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_download, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setFragment(this);

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
        binding.adView2.loadAd(adRequest);

        binding.selectionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.imageUrlRB:
                        SOURCE_TYPE = 1;
                        break;
                    case R.id.videoUrlRB:
                        SOURCE_TYPE = 2;
                        break;
                }
            }
        });
    }

    public void onDownloadBtnClick() {
        new Permissive.Request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .whenPermissionsGranted(new PermissionsGrantedListener() {
                    @Override
                    public void onPermissionsGranted(String[] permissions) throws SecurityException {
                        // given permissions are granted
                        if (!url.isEmpty() && URLUtil.isValidUrl(url)) {
                            DownloadManager dm = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
                            DownloadManager.Request request = new DownloadManager.Request(
                                    Uri.parse(url));
                            String fileName = URLUtil.guessFileName(url, null, null);
                            request.setDestinationInExternalPublicDir("/dJsonoup", fileName);
                            dm.enqueue(request);
                            Toast.makeText(getActivity(), "Downloading start.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Paste Url.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .whenPermissionsRefused(new PermissionsRefusedListener() {
                    @Override
                    public void onPermissionsRefused(String[] permissions) {
                        // given permissions are refused
                        Toast.makeText(getActivity(), "App required permission to store file.", Toast.LENGTH_SHORT).show();
                    }
                })
                .execute(getActivity());
    }

    public void loadUrl() {
        final String url = binding.urlEdt.getText().toString().trim();
        if (!url.isEmpty() && URLUtil.isValidUrl(url)) {
            String msg = "";
            if (SOURCE_TYPE == 1) {
                msg = "Do you want to download image?";
            } else {
                msg = "Do you want to download video?";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("");
            builder.setMessage(msg);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    new Task().execute(url);
                }
            });
            builder.create().show();
        } else {
            Toast.makeText(getActivity(), "Paste share url", Toast.LENGTH_SHORT).show();
        }
    }

    public void pasteUrl() {
        try {
            String url = Clipboard_Utils.getDataFromClipboard(getActivity());
            binding.urlEdt.setText(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createInstagramIntent() {
        if (!url.isEmpty() && URLUtil.isValidUrl(url)) {
            Bitmap imagebitmap = null;
            try {
                imagebitmap = ((BitmapDrawable) binding.imageView.getDrawable()).getBitmap();
                String type = "image/*";
                Intent shareIntent = new Intent(
                        android.content.Intent.ACTION_SEND);
                shareIntent.setType(type);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Util.getImageUri(getActivity(), imagebitmap));
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        "Check this out, what do you think?"
                                + System.getProperty("line.separator")
                                + "desc");
                shareIntent.setPackage("com.instagram.android");
                startActivity(shareIntent);

            } catch (Exception e) {
                e.printStackTrace();
                // bring user to the market to download the app.
                // or let them choose an app?
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("market://details?id="
                        + "com.instagram.android"));
                startActivity(intent);
            }
        } else {
            Toast.makeText(getActivity(), "Paste share url", Toast.LENGTH_SHORT).show();
        }
    }


    private class Task extends AsyncTask<String, String, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... voids) {
            return parse(voids[0]);
        }

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(ArrayList<String> aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (aVoid != null && aVoid.size() != 0) {
                binding.adView2.setVisibility(View.GONE);
                binding.imageView.setVisibility(View.VISIBLE);
                AQuery aQuery = new AQuery(getActivity());
                switch (SOURCE_TYPE) {
                    case 1:
                        url = aVoid.get(0);
                        aQuery.id(binding.imageView).progress(binding.progress).image(aVoid.get(0), true, true, 0, R.drawable.ic_placeholder_600x400);
                        break;
                    case 2:
                        url = aVoid.get(1);
                        aQuery.id(binding.imageView).progress(binding.progress).image(aVoid.get(0), true, true, 0, R.drawable.ic_placeholder_600x400);
                        break;
                }
            } else {
                Toast.makeText(getActivity(), "Invalid Url.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ArrayList<String> parse(String url) {
        try {
            ArrayList<String> images = new ArrayList<>();
            Document doc = Jsoup.connect(url).get();
            String imageUrl = "";
            Elements meta = null;
            if (SOURCE_TYPE == 1) {
                meta = doc.select("meta[property=og:image]");
                imageUrl = meta.attr("content");
                images.add(imageUrl);
            } else if (SOURCE_TYPE == 2) {
                Elements meta1 = doc.select("meta[property=og:image]");
                meta = doc.select("meta[property=og:video]");
                String imageUrl1 = meta1.attr("content");
                imageUrl = meta.attr("content");
                images.add(imageUrl1);
                images.add(imageUrl);
            }
            return images;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
