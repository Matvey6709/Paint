package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.Timer;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    MyPaint myPaint;
    ImageButton imageButtonD;
    ImageButton imageButtonD2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(seekBar.getProgress() + "");
                myPaint.radius = seekBar.getProgress();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        myPaint = findViewById(R.id.myPaint);
        imageButtonD = findViewById(R.id.delete);
        imageButtonD2 = findViewById(R.id.delete2);

    }

    public void Panel(View view) {
        switch (view.getId()) {
            case R.id.size:
                textView.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                break;
            case R.id.textView:
                textView.setVisibility(View.GONE);
                seekBar.setVisibility(View.GONE);
                break;
            case R.id.delete:
                myPaint.Delete();
                imageButtonD.setVisibility(View.GONE);
                imageButtonD2.setVisibility(View.VISIBLE);
                break;
            case R.id.delete2:
                myPaint.stDelete();
                imageButtonD.setVisibility(View.VISIBLE);
                imageButtonD2.setVisibility(View.GONE);
                break;
            case R.id.color:
                final BottomSheetDialog bottomSheetDialogColor = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_color, findViewById(R.id.activity_color));
                bottomSheetView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialogColor.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.circle_g).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myPaint.colorMode(0);
                    }
                });
                bottomSheetView.findViewById(R.id.circle_y).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myPaint.colorMode(1);
                    }
                });
                bottomSheetView.findViewById(R.id.circle_b).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myPaint.colorMode(2);
                    }
                });
                bottomSheetView.findViewById(R.id.circle_r).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myPaint.colorMode(3);
                    }
                });
                bottomSheetView.findViewById(R.id.circle_bl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myPaint.colorMode(4);
                    }
                });
                bottomSheetView.findViewById(R.id.circle_pur).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myPaint.colorMode(5);
                    }
                });
                bottomSheetDialogColor.setContentView(bottomSheetView);
                bottomSheetDialogColor.show();
                break;
            case R.id.save:
                myPaint.setDrawingCacheEnabled(true);
                String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), myPaint.getDrawingCache(), "photo", "photo");
                if (imgSaved != null) {
                    Toast savedToast = Toast.makeText(getApplicationContext(), "Ваша картинка сохранена ", Toast.LENGTH_SHORT);
                    savedToast.show();
                } else {
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Ошибка, возможно вы тестируете на эмуляторе, нужно на телефоне", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                myPaint.destroyDrawingCache();
                break;
        }
    }


}