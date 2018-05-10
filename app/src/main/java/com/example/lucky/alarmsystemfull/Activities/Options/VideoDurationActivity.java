package com.example.lucky.alarmsystemfull.Activities.Options;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Presenters.VideoDurationPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.VideoDurationView;

public class VideoDurationActivity extends MvpAppCompatActivity implements VideoDurationView {

    @InjectPresenter
    VideoDurationPresenter videoDurationPresenter;

    private EditText eTxtDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_duration);
        initViews();
    }

    private void initViews() {
        eTxtDuration = findViewById(R.id.new_phone);
        videoDurationPresenter.loadVideoDuration();
        setListeners();
    }

    private void setListeners() {
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!eTxtDuration.getText().toString().isEmpty()) {
                    videoDurationPresenter.safeVideoDuration(eTxtDuration.getText().toString());
                } else {
                    AppHelper.showMessage(AppHelper.getStringForResources(R.string.set_video_duration));
                }
            }
        });
    }

    @Override
    public void setVideoDuration(String duration) {
        eTxtDuration.setText(duration);
    }
}
