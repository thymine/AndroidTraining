/*
 * Copyright 2015 Udacity, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zhang.workbench.design;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhang.workbench.R;

public class PickerFragment extends DialogFragment {
    private static final String TAG = "PICKER_FRAGMENT";
    private static final int PICK_PHOTO = 100;
    private static final int TAKE_PHOTO = 101;

    @BindView(R.id.pickImage)
    Button pickImage;
    @BindView(R.id.takeImage)
    Button takeImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker, container, false);
        ButterKnife.bind(this, view);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @OnClick(R.id.pickImage)
    void pickImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_PHOTO);
    }

    @OnClick(R.id.takeImage)
    void takeImage(View view) {
//        Snackbar.make(getView(), "I want to take an image.", Snackbar.LENGTH_SHORT)
//                .show();

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, TAKE_PHOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Picked a photo.");
                    Uri selectedImage = data.getData();
                    ((PaletteActivity) getActivity()).createPalette(selectedImage);
                    getDialog().dismiss();
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Took a photo.");
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ((PaletteActivity) getActivity()).createPalette(imageBitmap);
                    getDialog().dismiss();
                }
        }
    }
}
