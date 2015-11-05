package me.zhang.lab.gesture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import me.zhang.lab.R;

/**
 * Created by Zhang on 2015/11/5 上午 10:06 .
 */
public class GatherDataFragment extends Fragment {

    private static final String DEBUG_TAG = "GatherDataFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gather_data, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        View touchView = view.findViewById(R.id.ib_wechat);

        /**
         * Beware of creating a listener that returns false for the ACTION_DOWN event.
         * If you do this, the listener will not be called for the subsequent ACTION_MOVE and ACTION_UP string of events.
         * This is because ACTION_DOWN is the starting point for all touch events.
         */
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(DEBUG_TAG, "Touched");

                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "Action was DOWN");
                        break;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "Action was MOVE");
                        break;
                    case (MotionEvent.ACTION_UP):
                        Log.d(DEBUG_TAG, "Action was UP");
                        break;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "Action was CANCEL");
                        break;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                                "of current screen element");
                        break;
                    default:
                        break;
                }

                return false; // true: consumed, false: not
            }
        });
    }
}
