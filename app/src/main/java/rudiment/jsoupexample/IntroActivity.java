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
                .image(R.drawable.ic_image)
                .title("Lorem Ipsum Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .textSize(14)
                .titleSize(17)
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimaryDark)
                .image(R.drawable.ic_image)
                .title("Lorem Ipsum Lorem Ipsum ")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .build());

        addIntroItem(new VerticalIntroItem.Builder()
                .backgroundColor(R.color.colorPrimary)
                .image(R.drawable.ic_image)
                .title("Lorem Ipsum")
                .text("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
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
