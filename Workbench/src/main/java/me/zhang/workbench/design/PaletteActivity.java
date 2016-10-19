package me.zhang.workbench.design;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import me.zhang.workbench.R;

public class PaletteActivity extends AppCompatActivity {

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    SwatchAdapter swatchAdapter;
    @InjectView(R.id.grid_view)
    GridView gridView;
    @InjectView(R.id.tool_bar)
    Toolbar toolbar;
    @InjectView(R.id.imageView)
    ImageView imageView;
    int numPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_palette);

        ButterKnife.inject(this);

        toolbar.setTitle(getString(R.string.app_name));

    }

    @OnClick(R.id.fab)
    public void click(View view) {
        Snackbar.make(findViewById(R.id.fragment), "Clicked FAB.", Snackbar.LENGTH_LONG)
                //.setAction("Action", this)
                .show();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        PickerFragment pickerFragment = new PickerFragment();
        pickerFragment.show(getFragmentManager(), "dialog");
        ft.commit();
    }

    public void createPalette(Object object) {
        Bitmap bitmap;
        try {
            if (object instanceof Uri) {
                Uri imageUri = (Uri) object;
                Picasso.with(this).load(imageUri).into(imageView);
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
            } else {
                bitmap = (Bitmap) object;
                imageView.setImageBitmap(bitmap);
            }

            // Do this async on activity

            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    HashMap map = processPalette(palette);
                    swatchAdapter = new SwatchAdapter(getApplicationContext(), map.entrySet().toArray());
                    gridView.setAdapter(swatchAdapter);
                }
            });


        } catch (Exception ex) {
            Log.e("MainActivity", "error in creating palette");
        }
    }

    HashMap<String, Palette.Swatch> processPalette(Palette p) {
        HashMap<String, Palette.Swatch> map = new HashMap<>();

        if (p.getVibrantSwatch() != null)
            map.put("Vibrant", p.getVibrantSwatch());
        if (p.getDarkVibrantSwatch() != null)
            map.put("DarkVibrant", p.getDarkVibrantSwatch());
        if (p.getLightVibrantSwatch() != null)
            map.put("LightVibrant", p.getLightVibrantSwatch());

        if (p.getMutedSwatch() != null)
            map.put("Muted", p.getMutedSwatch());
        if (p.getDarkMutedSwatch() != null)
            map.put("DarkMuted", p.getDarkMutedSwatch());
        if (p.getLightMutedSwatch() != null)
            map.put("LightMuted", p.getLightMutedSwatch());

        return map;
    }

    @OnItemClick(R.id.grid_view)
    void onItemClick(int position) {
        Palette.Swatch swatch = ((Map.Entry<String, Palette.Swatch>) gridView.getItemAtPosition(position)).getValue();

        String b = "Title Text Color: " + "#" + Integer.toHexString(swatch.getBodyTextColor()).toUpperCase() + "\n" +
                "Population: " + swatch.getPopulation();

        Snackbar.make(gridView, b, Snackbar.LENGTH_LONG).show();
    }
}
