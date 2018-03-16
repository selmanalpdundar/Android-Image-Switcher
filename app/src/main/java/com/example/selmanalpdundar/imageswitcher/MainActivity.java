package com.example.selmanalpdundar.imageswitcher;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    InputStream inputStream;
    AssetManager assetManager;
    int counter = 0;
    Drawable[] drawables;
    String[] images;
    ImageSwitcher imageSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            assetManager = getAssets();
            images = assetManager.list("dogs");
            drawables = new Drawable[images.length];

            for (int i = 0; i < images.length; i++) {
                inputStream = getAssets().open("dogs/" + images[i]);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                drawables[i] = drawable;
            }
        } catch (IOException e) {

        }

        imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView switcherImageView = new ImageView(getApplicationContext());
                switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT
                ));
                switcherImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                switcherImageView.setImageDrawable(drawables[counter]);
                return switcherImageView;
            }
        });

        Animation animationOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        Animation animationIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

        imageSwitcher.setOutAnimation(animationOut);
        imageSwitcher.setInAnimation(animationIn);





    }



    public void onCLickNext(View view){
        counter++;
        if (counter == images.length) {
            counter = 0;
        }
        imageSwitcher.setImageDrawable(drawables[counter]);

    }

    public void onCLickBack(View view){

        counter--;
        if (counter < 0) {
            counter = images.length - 1;
        }
        imageSwitcher.setImageDrawable(drawables[counter]);


    }
}
