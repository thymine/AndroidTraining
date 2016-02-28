package me.zhang.lock;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import me.zhang.lock.util.DelayUtils;

/**
 * Created by Li on 2/11/2016 10:09 PM.
 */
public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();
    private SeekBar delaySeek;
    private TextView delayValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        delaySeek = (SeekBar) view.findViewById(R.id.seek_delay);

        int delay = DelayUtils.getDelayValue(getActivity());
        delaySeek.setProgress(delay);

        delayValue = (TextView) view.findViewById(R.id.delay_value);
        delayValue.setText(getStyledDelayText(delay));
    }

    @Override
    public void onStart() {
        super.onStart();
        delaySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "progress: " + progress);
                delayValue.setText(getStyledDelayText(progress));
                DelayUtils.saveDelayValue(getActivity(), progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Spanned getStyledDelayText(int progress) {
        return Html.fromHtml(
                String.format(
                        getResources().getString(R.string.title_screen_off_delay),
                        progress
                )
        );
    }
}
