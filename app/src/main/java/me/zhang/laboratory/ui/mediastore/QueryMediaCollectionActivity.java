package me.zhang.laboratory.ui.mediastore;

import static me.zhang.laboratory.utils.UiUtilsKt.convertDpToPixel;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import me.zhang.laboratory.R;

public class QueryMediaCollectionActivity extends AppCompatActivity {

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                //noinspection StatementWithEmptyBody
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    doQuery();
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });
    private RecyclerView mediaCollections;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_media_collection);

        mediaCollections = findViewById(R.id.mediaCollections);

        HandlerThread handlerThread = new HandlerThread("queryMediaCollection");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper(), msg -> {
            if (msg.what == 1) {
                //noinspection unchecked
                ArrayList<Video> videoList = (ArrayList<Video>) msg.obj;

                runOnUiThread(() -> bindUi(videoList));
            }
            return true;
        });

        //region Permission check
        String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, readExternalStorage) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            doQuery();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //noinspection StatementWithEmptyBody
            if (shouldShowRequestPermissionRationale(readExternalStorage)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
            } else {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(readExternalStorage);
            }
        }
        //endregion
    }

    @UiThread
    private void bindUi(@NonNull List<Video> videoList) {
        DividerItemDecoration decor = new DividerItemDecoration(mediaCollections.getContext(), DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(mediaCollections.getContext(), R.drawable.divider_horizontal);
        if (drawable != null) {
            decor.setDrawable(drawable);
        }
        mediaCollections.addItemDecoration(decor);
        mediaCollections.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @NotNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false)) {
                    @NonNull
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
                View itemView = holder.itemView;
                Context context = itemView.getContext();

                TextView mediaItem = itemView.findViewById(R.id.mediaItem);
                Video video = videoList.get(position);

                ImageView mediaThumbnail = itemView.findViewById(R.id.mediaThumbnail);
                try {
                    int _48dp = (int) convertDpToPixel(48, context);
                    Size size = new Size(_48dp, _48dp);
                    Bitmap bitmap;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                        bitmap = getContentResolver().loadThumbnail(video.uri, size, new CancellationSignal());
                        mediaThumbnail.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaItem.setText(String.format(Locale.getDefault(),
                        "%s\n%d ms\n%d bytes\n%s",
                        video.name, video.duration, video.size, video.uri));
            }

            @Override
            public int getItemCount() {
                return videoList.size();
            }
        });
    }

    private void doQuery() {
        handler.post(this::queryVideoCollection);
    }

    private void queryVideoCollection() {
        // Need the READ_EXTERNAL_STORAGE permission if accessing video files that your
        // app didn't create.

        final List<Video> videoList = new ArrayList<>();

        Uri collection;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }

        String[] projection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE
        };
        String selection = MediaStore.Video.Media.DURATION +
                " >= ?";
        String[] selectionArgs = new String[]{
                String.valueOf(TimeUnit.MILLISECONDS.convert(0, TimeUnit.MINUTES))
        };
        String sortOrder = MediaStore.Video.Media.DISPLAY_NAME + " ASC";

        try (Cursor cursor = getApplicationContext().getContentResolver().query(
                collection,
                projection,
                selection,
                selectionArgs,
                sortOrder
        )) {
            // Cache column indices.
            int idColumn;
            if (cursor != null) {
                idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                int nameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                int durationColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

                while (cursor.moveToNext()) {
                    // Get values of columns for a given video.
                    long id = cursor.getLong(idColumn);
                    String name = cursor.getString(nameColumn);
                    int duration = cursor.getInt(durationColumn);
                    int size = cursor.getInt(sizeColumn);

                    Uri contentUri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

                    // Stores column values and the contentUri in a local object
                    // that represents the media file.
                    videoList.add(new Video(contentUri, name, duration, size));
                }
            }
        }

        Message message = Message.obtain();
        message.what = 1;
        message.obj = videoList;
        handler.sendMessage(message);
    }

    // Container for information about each video.
    public static class Video {
        private final Uri uri;
        private final String name;
        private final int duration;
        private final int size;

        public Video(Uri uri, String name, int duration, int size) {
            this.uri = uri;
            this.name = name;
            this.duration = duration;
            this.size = size;
        }
    }

}