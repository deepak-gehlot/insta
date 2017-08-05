package rudiment.jsoupexample;

import android.content.Intent;
import android.view.View;

import com.luseen.verticalintrolibrary.VerticalIntro;
import com.luseen.verticalintrolibrary.VerticalIntroItem;

public class IntroActivity extends VerticalIntro {

    @Override
    protected void init() {
        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorAccent)
                .image(R.drawable.ic_5932)
                .title("Step 1")
                .text("Open Download Insta app.")
                .textSize(14)
                .titleSize(17)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimaryDark)
                .image(R.drawable.ic_2259)
                .title("Step 2")
                .text("Minimize App and Open Instagram.")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimary)
                .image(R.drawable.ic_8930)
                .title("Step 3")
                .text("Click on Three Dot option icon.")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorAccent)
                .image(R.drawable.ic_8930)
                .title("Step 2")
                .text("Click on copy share Url, Download will start in background.")
                .build());

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
