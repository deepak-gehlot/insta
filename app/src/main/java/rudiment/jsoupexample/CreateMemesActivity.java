package rudiment.jsoupexample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import java.io.File;

import rudiment.jsoupexample.databinding.ActivityCreateMemesBinding;

public class CreateMemesActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private ActivityCreateMemesBinding binding;
    private int selectedTextColor = -769226;
    private int selectedTextBorderColor = -769226;
    private int REQUEST_FOR = 0; // 1 = text color, 2 = border color
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_memes);
        binding.setActivity(this);
        handleIntent();
        binding.setFile(file);

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int size = progress + 22;
                binding.headerText.setTextSize(size);
                binding.footerText.setTextSize(size);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void handleIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            file = (File) bundle.get("file");
        }
    }

    public void onSelectTextColor() {
        REQUEST_FOR = 1;
        ColorPickerDialog.newBuilder().setColor(selectedTextColor).show(CreateMemesActivity.this);
    }

    public void onSelectTextBorderColor() {
        REQUEST_FOR = 2;
        ColorPickerDialog.newBuilder().setColor(selectedTextBorderColor).show(CreateMemesActivity.this);
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        switch (REQUEST_FOR) {
            case 1:
                selectedTextColor = color;
                binding.headerText.setTextColor(color);
                binding.footerText.setTextColor(color);
                binding.textColorView.setBackgroundColor(color);
                break;
            case 2:
                selectedTextBorderColor = color;
                binding.headerText.setShadowLayer(1.8f, 1.8f, 1.5f, color);
                binding.footerText.setShadowLayer(1.8f, 1.5f, 1.3f, color);
                binding.backgroundColorView.setBackgroundColor(color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
