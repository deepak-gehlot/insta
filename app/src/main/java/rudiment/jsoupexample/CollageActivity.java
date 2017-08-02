package rudiment.jsoupexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.jcmore2.collage.CollageView;

import java.io.File;
import java.util.ArrayList;

public class CollageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);

        ArrayList<Bitmap> result = new ArrayList<>(); //ArrayList cause you don't know how many files there is
        try {
            File folder = new File(Environment.getExternalStorageDirectory() + "/dJsonoup"); //This is just to cast to a File type since you pass it as a String
            File[] filesInFolder = folder.listFiles(); // This returns all the folders and files in your path
            for (File file : filesInFolder) { //For each of the entries do:
                if (!file.isDirectory()) { //check that it's not a dir
                    result.add(getBitmapFromFile(file)); //push the filename as a string
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CollageView collage = (CollageView) findViewById(R.id.collage);
        collage.createCollageBitmaps(result);
    }

    private Bitmap getBitmapFromFile(File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
        return bitmap;
    }
}
