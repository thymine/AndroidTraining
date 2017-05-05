package me.zhang.workbench.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhang.workbench.R;

import static android.view.View.FOCUS_DOWN;

public class SaveMyLocationActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<String>> {

    private static final int LOADER_ID = 0x01;
    @BindView(R.id.locations_view)
    TextView mLocationsView;

    @BindView(R.id.locations_scroll_view)
    ScrollView mLocationsScrollView;

    @BindView(R.id.location_capture_view)
    EditText mLocationCaptureView;

    private static final int DELAY_MILLIS = 1000;
    private LocationLoader mLocationLoader;
    Handler mHandler = new Handler();
    Runnable mReloadRunnable = new Runnable() {
        @Override
        public void run() {
            mLocationLoader.forceLoad();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_my_location);
        ButterKnife.bind(this);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    public void onSaveLocationButtonClick(View view) {
        Intent intent = new Intent(this, SaveMyLocationService.class);
        intent.putExtra(SaveMyLocationService.LOCATION_KEY, getCurrentLocation());
        startService(intent);

        mHandler.postDelayed(mReloadRunnable, DELAY_MILLIS);
    }

    private String getCurrentLocation() {
        String location = mLocationCaptureView.getText().toString();
        mLocationCaptureView.getText().clear();
        return location;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            stopLocationSavingService();
        }
    }

    private void stopLocationSavingService() {
        Intent intent = new Intent(this, SaveMyLocationService.class);
        stopService(intent);
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        return mLocationLoader = new LocationLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        StringBuilder locationBuilder = new StringBuilder();
        for (String s : data) {
            locationBuilder.append(s);
            locationBuilder.append("\n");
        }
        mLocationsView.setText(locationBuilder.toString());
        mLocationsScrollView.fullScroll(FOCUS_DOWN);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }

    private static class LocationLoader extends AsyncTaskLoader<List<String>> {

        List<String> mLocations;

        public LocationLoader(Context context) {
            super(context);
        }

        @Override
        public List<String> loadInBackground() {
            SharedPreferences sharedPref = getContext()
                    .getSharedPreferences(getContext().getString(R.string.preference_file_key),
                            Context.MODE_PRIVATE);
            String locationStr = sharedPref.getString(SaveMyLocationService.LOCATION_KEY, "");
            return Arrays.asList(locationStr.split(","));
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (mLocations != null) {
                deliverResult(mLocations);
            } else {
                forceLoad();
            }
        }

        @Override
        public void deliverResult(List<String> data) {
            super.deliverResult(data);
            if (isReset()) {
                return;
            }

            mLocations = data;

            if (isStarted()) {
                super.deliverResult(data);
            }
        }

        @Override
        protected void onReset() {
            super.onReset();
            mLocations = null;
        }

    }

}
