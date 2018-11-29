package com.byteshaft.thevideoplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private ImageButton mImageButton;
    private String uriString;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.ad_unit_id));

        //banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Interstitial Ad
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_ad_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        uriString = "android.resource://" + getPackageName() + "/" + R.raw.arnb;
        mVideoView = findViewById(R.id.video_view);
        mImageButton = findViewById(R.id.toggle_button);
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(mVideoView);
//        mVideoView.setMediaController(mediaController);
        mVideoView.setZOrderOnTop(true);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mImageButton.setBackground(getDrawable(R.drawable.play));
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
        mVideoView.setVideoURI(Uri.parse(uriString));
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mImageButton.setBackground(getDrawable(R.drawable.play));
                } else {
                    mVideoView.start();
                    mImageButton.setBackground(getDrawable(R.drawable.stop));
                }
            }
        });
    }
}
