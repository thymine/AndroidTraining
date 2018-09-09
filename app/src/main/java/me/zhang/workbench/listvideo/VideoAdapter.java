package me.zhang.workbench.listvideo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import me.zhang.workbench.R;
import me.zhang.workbench.listvideo.TextureVideoView.MediaState;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<String> mVideos;
    private Context mContext;
    private VideoViewHolder mLastPlayVideoViewHolder;

    public VideoAdapter(Context context, List<String> videos) {
        mContext = context;
        mVideos = videos;
    }

    class VideoViewHolder extends ViewHolder {
        public VideoViewHolder(View itemView) {
            super(itemView);

            mVideoView = (TextureVideoView) itemView.findViewById(R.id.textureview);
            mPreviewImage = (ImageView) itemView.findViewById(R.id.imv_preview);
            mPlayButton = (ImageView) itemView.findViewById(R.id.imv_video_play);
            mLoadingProgress = (ProgressBar) itemView.findViewById(R.id.pb_waiting);
            mProgressRate = (ProgressBar) itemView.findViewById(R.id.progress_progressbar);

            mVideoView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLastPlayVideoViewHolder == null) {
                        mLastPlayVideoViewHolder = VideoViewHolder.this;
                    } else {
                        if (!VideoViewHolder.this.equals(mLastPlayVideoViewHolder)) {
                            mLastPlayVideoViewHolder.mVideoView.stop();
                            mLastPlayVideoViewHolder.mLoadingProgress.setVisibility(View.GONE);
                            mLastPlayVideoViewHolder.mPlayButton.setVisibility(View.VISIBLE);
                            mLastPlayVideoViewHolder = VideoViewHolder.this;
                        }
                    }
                    TextureVideoView textureView = (TextureVideoView) v;
                    if (textureView.getState() == MediaState.INIT
                            || textureView.getState() == MediaState.RELEASE) {
                        textureView.play(mVideos.get(getAdapterPosition()));
                        mLoadingProgress.setVisibility(View.VISIBLE);
                        mPlayButton.setVisibility(View.GONE);
                    } else if (textureView.getState() == MediaState.PAUSE) {
                        textureView.start();
                        mLoadingProgress.setVisibility(View.GONE);
                        mPlayButton.setVisibility(View.GONE);
                    } else if (textureView.getState() == MediaState.PLAYING) {
                        textureView.pause();
                        mLoadingProgress.setVisibility(View.GONE);
                        mPlayButton.setVisibility(View.VISIBLE);
                    } else if (textureView.getState() == MediaState.PREPARING) {
                        textureView.stop();
                        mLoadingProgress.setVisibility(View.GONE);
                        mPlayButton.setVisibility(View.VISIBLE);
                    }
                }
            });

            mVideoView.setOnStateChangeListener(new TextureVideoView.OnStateChangeListener() {
                @Override
                public void onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    mVideoView.stop();
                    mLoadingProgress.setVisibility(View.GONE);
                    mPlayButton.setVisibility(View.VISIBLE);
                    mProgressRate.setMax(1);
                    mProgressRate.setProgress(0);
                    mPreviewImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPlaying() {
                    mLoadingProgress.setVisibility(View.GONE);
                    mPlayButton.setVisibility(View.GONE);
                }

                @Override
                public void onBuffering() {
                    mLoadingProgress.setVisibility(View.VISIBLE);
                    mPlayButton.setVisibility(View.GONE);
                }

                @Override
                public void onSeek(int max, int progress) {
                    mPreviewImage.setVisibility(View.GONE);
                    mProgressRate.setMax(max);
                    mProgressRate.setProgress(progress);
                }

                @Override
                public void onStop() {
                    mProgressRate.setMax(1);
                    mProgressRate.setProgress(0);
                    mLoadingProgress.setVisibility(View.GONE);
                    mPlayButton.setVisibility(View.VISIBLE);
                    mPreviewImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPause() {
                    mLoadingProgress.setVisibility(View.GONE);
                    mPlayButton.setVisibility(View.VISIBLE);
                }

                @Override
                public void onTextureViewAvaliable() {

                }

                @Override
                public void playFinish() {
                    mProgressRate.setMax(1);
                    mProgressRate.setProgress(0);
                    mPlayButton.setVisibility(View.GONE);
                    mPreviewImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPrepare() {

                }
            });
        }

        TextureVideoView mVideoView;
        ImageView mPreviewImage;
        ImageView mPlayButton;
        ProgressBar mLoadingProgress;
        ProgressBar mProgressRate;
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder viewHolder, final int position) {

    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup root, int position) {
        View containerView = LayoutInflater.from(mContext)
                .inflate(R.layout.videoitem, root, false);
        return new VideoViewHolder(containerView);
    }
}
