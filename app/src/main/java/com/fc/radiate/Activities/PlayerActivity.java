package com.fc.radiate.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fc.radiate.DataManagement.Station;
import com.fc.radiate.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import co.mobiwise.library.radio.RadioListener;
import co.mobiwise.library.radio.RadioManager;

import static com.fc.radiate.DataManagement.FetchData.getCurrentStation;
import static com.fc.radiate.DataManagement.FetchData.setCurrentStation;

public class PlayerActivity extends Activity implements RadioListener {

    static RadioManager mRadioManager;
    boolean isNewStation;
    private ImageView icon = null;
    private ConstraintLayout mainConstrain = null;
    private Bitmap bm_icon = null;
    private FloatingActionButton playPauseButton;
    private FloatingActionButton nextButton;
    private FloatingActionButton prevButton;
    private FloatingActionButton addToFavButton;
    private TextView RadioName;
    private TextView countryOrSongName;
    private TextView RadiateOrStreamName;
    private Station newStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Log.v("RadioFc", "OnCreate");

        mRadioManager = RadioManager.with(getApplicationContext());
        mRadioManager.registerListener(this);
        mRadioManager.setLogging(true);
        mRadioManager.enableNotification(true);

        if (getCurrentStation() == null)
            setCurrentStation((Station) getIntent().getSerializableExtra("st"));

        newStation = (Station) getIntent().getSerializableExtra("st");
        getIntent().removeExtra("st");

        if (getCurrentStation() != null)
            isNewStation = !newStation.getId().equals(getCurrentStation().getId());
        else isNewStation = true;

        mRadioManager.updateNotification(newStation.getName(), newStation.getCountry(), R.drawable.radio_tower, R.mipmap.ic_launcher);
        initializeUI();

    }

    public void initializeUI() {
        mainConstrain = findViewById(R.id.playerLayout);
        playPauseButton = findViewById(R.id.playPauseButton);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        addToFavButton = findViewById(R.id.AddFavButton);
        RadioName = findViewById(R.id.RadioName);
        countryOrSongName = findViewById(R.id.countryOrSongName);
        RadiateOrStreamName = findViewById(R.id.RadiateOrArtist);
        icon = findViewById(R.id.stationIcon);
        /*
        if (getCurrentStation().isFav()) {
            addToFavButton.setImageResource(R.drawable.starred);
        } else addToFavButton.setImageResource(R.drawable.unstarred);

        addToFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean tempBool = getCurrentStation().isFav();
                if (tempBool == true) {
                    getCurrentStation().setFav(false);
                    addToFavButton.setImageResource(R.drawable.unstarred);
                } else {
                    getCurrentStation().setFav(true);
                    addToFavButton.setImageResource(R.drawable.starred);
                }
            }
        });
*/
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRadioManager.isPlaying())
                    mRadioManager.startRadio(getCurrentStation().getUrl());
                else
                    mRadioManager.stopRadio();
            }
        });

        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        boolean ShouldGetImgOverInternet = pref.getBoolean("DownloadImages", true);

        if (!newStation.getFavicon().equals("") && ShouldGetImgOverInternet) {
            Picasso.get()
                    .load(newStation.getFavicon())
                    .placeholder(R.drawable.launcher_icon_r)
                    .error(R.drawable.launcher_icon_r)
                    .into(icon, new Callback() {
                        @Override
                        public void onSuccess() {
                            updateBackground();
                        }

                        @Override
                        public void onError(Exception e) {
                            icon.setImageResource(R.drawable.launcher_icon_r);
                            updateBackground();
                        }
                    });
        } else {
            icon.setImageResource(R.drawable.launcher_icon_r);
            updateBackground();
        }
    }

    @Override
    protected void onResume() {
        Log.v("RadioFc", "OnResume");
        super.onResume();
        if (isNewStation) {
            setCurrentStation(newStation);
            if (mRadioManager.isPlaying()) {
                mRadioManager.stopRadio();
            }
            if (mRadioManager.isConnected()) {
                mRadioManager.disconnect();
            }
        }
        RadioName.setText(getCurrentStation().getName());
        countryOrSongName.setText(getCurrentStation().getCountry());
        mRadioManager.connect();
    }

    @Override
    public void onRadioLoading() {
        Log.v("RadioFC", "onRadioLoading");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.v("RadioFC", "onRadioLoadingIn");
                //GifLoading();
            }
        });
    }

    public int darkerColor(int color, float ratio) {
        int a = (color >> 24) & 0xFF;
        int r = (int) (((color >> 16) & 0xFF) * ratio);
        int g = (int) (((color >> 8) & 0xFF) * ratio);
        int b = (int) ((color & 0xFF) * ratio);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public void updateBackground() {
        bm_icon = ((BitmapDrawable) icon.getDrawable()).getBitmap();
        Palette.from(bm_icon).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                final int newColor;
                if (vibrantSwatch != null) {
                    newColor = vibrantSwatch.getRgb();
                } else {
                    newColor = Palette.from(bm_icon).generate().
                            getDominantColor(0x000000);
                }
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{newColor, darkerColor(newColor, 0.2f)});
                gd.setCornerRadius(0f);
                mainConstrain.setBackground(gd);
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(darkerColor(newColor, 0.8f));
                }
            }
        });
    }

    @Override
    public void onRadioConnected() {
        Log.v("RadioFC", "onRadioConnected for " + getCurrentStation().getUrl());
        RadioName.setText(getCurrentStation().getName());
        countryOrSongName.setText(getCurrentStation().getCountry());
        mRadioManager.startRadio(getCurrentStation().getUrl());

    }

    @Override
    public void onRadioStarted() {
        Log.v("RadioFC", "onRadioStarted");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.v("RadioFC", "onRadioStartedIn");
                playPauseButton.setImageResource(R.drawable.pause);
            }
        });
    }

    @Override
    public void onRadioStopped() {
        Log.v("RadioFC", "onRadioStopped");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.v("RadioFC", "onRadioStoppedIn");
                playPauseButton.setImageResource(R.drawable.play);
                updateBackground();
            }
        });
    }

    @Override
    public void onMetaDataReceived(String s, final String s1) {
        if (s != null && s1 != null && s.equals("StreamTitle") && s1.length() > 3) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("ABCDEF", "UPDATE TEXT WITH 1"+s1);
                    Log.v("ABCDEF", RadiateOrStreamName.getText().toString());
                    RadiateOrStreamName.setText(s1);
                    Log.v("ABCDEF", "UPDATE TEXT WITH 2"+s1);
                    mRadioManager.updateNotification(getCurrentStation().getName(), s1, R.drawable.radio_tower, R.mipmap.ic_launcher);
                }
            });
        }
    }

    @Override
    public void onAudioSessionId(int i) {

    }

    @Override
    public void onError() {
        Log.v("RadioFC", "OnError");
        mRadioManager.startRadio(getCurrentStation().getUrl());
    }

}
