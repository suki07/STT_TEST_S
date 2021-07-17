package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView gifImage = findViewById(R.id.gif_image);
//        Glide.with(this)
//                .load("https://storage.googleapis.com/byte_mouth/gif_lip_annyeong.gif")
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
//                .into(gifImage);
//
        Glide.with(this)
                .load("https://storage.googleapis.com/byte_mouth/gif_lip_annyeong.gif")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (resource instanceof GifDrawable){
                            ((GifDrawable) resource).setLoopCount(1);
                        }
                        return false;
                    }
                })
                .into(gifImage);
    }
}