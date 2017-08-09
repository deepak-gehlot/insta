package rudiment.jsoupexample;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

public class IntroActivity extends VerticalIntro {

    @Override
    protected void init() {
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorAccent)
                .image(R.drawable.web_hi_res_512)
                .title("Step 1")
                .text("Open DownloadInsta app." +
                        "\n" +
                        "\n" +
                        "\n")
                .textSize(20)
                .titleSize(30)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimaryDark)
                .image(R.drawable.android_home_button)
                .title("Step 2")
                .text("Minimize DownloadInsta App and Open Instagram." +
                        "\n" +
                        "\n" +
                        "\n")
                .textSize(20)
                .titleSize(30)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimary)
                .image(R.drawable.vish)
                .title("Step 3")
                .text("Click on Three Dot option icon." +
                        "\n" +
                        "\n" +
                        "\n")
                .textSize(20)
                .titleSize(30)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorAccent)
                .image(R.drawable.ic_2259)
                .title("Step 4")
                .text("Click on copy share Url, Download will start in background." +
                        "\n" +
                        "\n" +
                        "\n     ")
                .textSize(20)
                .titleSize(30)
                .build());

        setCustomTypeFace(Typeface.createFromAsset(getAssets(), "fonts/TypeType.otf"));
    }


    @Override
    protected Integer setLastItemBottomViewColor() {
        return null;
    }

    @Override
    protected void onSkipPressed(View view) {

    }

    @Override
    protected void onFragmentChanged(int position) {

    }

    @Override
    protected void onDonePressed() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }
}
