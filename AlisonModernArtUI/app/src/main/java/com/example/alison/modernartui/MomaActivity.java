package com.example.alison.modernartui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Dialog;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout;

public class MomaActivity extends ActionBarActivity {


    private SeekBar seekBar;
    private int[] startingColors = new int[5];
    private TextView[] textViews = new TextView[5];
    private int viewCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moma);

        LinearLayout momaLayout = (LinearLayout) findViewById(R.id.fullLayout);
        viewCount = getViewInformation(textViews, startingColors, viewCount, momaLayout);


        seekBar = (SeekBar) findViewById(R.id.colorChanger);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;

                // loop through the 5 textViews and update their background, adding progress% to the
                // starting color

                for (int i = 0; i < viewCount; i++) {
                    TextView tv = textViews[i];
                    int color = startingColors[i];
                    int red = Color.red(color)  + progress;
                    int blue = Color.blue(color)  + progress;
                    int green = Color.green(color) + progress;
                    int alpha = Color.alpha(color);
                    int newColor = Color.argb(alpha, red, green, blue);
                    ColorDrawable colorDrawable = new ColorDrawable();
                    colorDrawable.setColor(newColor);
                    tv.setBackground(colorDrawable);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // Get the textViews from the main screen as well as their starting color value
    private int getViewInformation(TextView[] textViews, int[] startingColors, int viewCount, LinearLayout momaLayout) {
        for (int i = 0; i < momaLayout.getChildCount(); i++) {
            if (momaLayout.getChildAt(i).getClass() == LinearLayout.class) {
                LinearLayout subLayout = (LinearLayout)momaLayout.getChildAt(i);
                for (int j = 0; j < subLayout.getChildCount(); j++) {
                    if (subLayout.getChildAt(j).getClass() == AppCompatTextView.class || subLayout.getChildAt(j).getClass() == TextView.class) {
                        textViews[viewCount] = (TextView)subLayout.getChildAt(j);
                        ColorDrawable colorDrawable = (ColorDrawable) textViews[viewCount].getBackground();
                        startingColors[viewCount] = colorDrawable.getColor();
                        viewCount++;
                    }
                }
            }
        }
        return viewCount;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moma, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.more_info) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.help_dialog);

            dialog.setCancelable(true);
            dialog.show();

            Button notNowButton = (Button) dialog.findViewById(R.id.dialogButtonNotNow);
            Button momaButton = (Button) dialog.findViewById(R.id.dialogButtonMOMA);

            notNowButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.dismiss();
                }
            });

            momaButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.dismiss();
                    startMomaBrowser(v);
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }


    private void startMomaBrowser(View v) {
        Intent baseIntent = null;
        baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moma.org"));
        startActivity(baseIntent);
    }
}
