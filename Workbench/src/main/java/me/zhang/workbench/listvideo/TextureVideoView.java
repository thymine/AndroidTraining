package me.zhang.workbench.listvideo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

public class TextureVideoView extends TextureView implements
        SurfaceTextureListener {

    private static final int STOP = 0;
    boolean mPlayFinished = false;
    private MediaPlayer mMediaPlayer;
    private MediaState mMediaState;
    private OnStateChangeListener mOnStateChangeListener;

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public MediaState getState() {
        return mMediaState;
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.mOnStateChangeListener = onStateChangeListener;
    }

    private OnInfoListener mOnInfoListener = new OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (mOnStateChangeListener != null && mMediaState != MediaState.PAUSE) {
                mOnStateChangeListener.onPlaying();
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    mOnStateChangeListener.onBuffering();
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    mOnStateChangeListener.onPlaying();
                }
            }
            return false;
        }
    };

    private OnBufferingUpdateListener mOnBufferingUpdateListener = new OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            if (mOnStateChangeListener != null) {
                if (percent == 100 && mMediaState != MediaState.PAUSE) {
                    mMediaState = MediaState.PLAYING;
                    mOnStateChangeListener.onPlaying();
                }
                if (mMediaState == MediaState.PLAYING) {
                    if (mPlayFinished)
                        return;
                    mOnStateChangeListener.onSeek(mMediaPlayer.getDuration(),
                            mMediaPlayer.getCurrentPosition());
                }
            }
        }
    };

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case STOP:
                    if (mOnStateChangeListener != null) {
                        mOnStateChangeListener.onStop();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new Handler(mCallback);

    public TextureVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,
                                          int width, int height) {
        Surface surface = new Surface(surfaceTexture);
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mPlayFinished = false;
                    mediaPlayer.start();
                    mMediaState = MediaState.PLAYING;
                }
            });
            mMediaPlayer.setOnInfoListener(mOnInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mOnStateChangeListener != null) {
                        if (mMediaState != MediaState.PLAYING)
                            return;
                        mOnStateChangeListener.playFinish();
                        mPlayFinished = true;
                    }
                }
            });
            mMediaPlayer.setOnErrorListener(new OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mMediaPlayer.reset();
                    mMediaState = MediaState.INIT;
                    mOnStateChangeListener.onStop();
                    return false;
                }
            });
        }
        mMediaPlayer.setSurface(surface);
        mMediaState = MediaState.INIT;
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onTextureViewAvaliable();
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onSurfaceTextureDestroyed(surface);
        }
        return true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
                                            int height) {
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public void play(String videoUrl) {
        play(videoUrl, true);
    }

    public void play(String videoUrl, boolean looping) {
        if (mMediaState == MediaState.PREPARING) {
            stop();
            return;
        }
        mMediaPlayer.reset();
        mMediaPlayer.setLooping(looping);
        try {
            mMediaPlayer.setDataSource(videoUrl);
            mMediaPlayer.prepareAsync();
            if (mOnStateChangeListener != null) {
                mOnStateChangeListener.onPrepare();
            }
            mMediaState = MediaState.PREPARING;
        } catch (Exception e) {
            mMediaPlayer.reset();
            mMediaState = MediaState.INIT;
        }
    }

    public void pause() {
        mMediaPlayer.pause();
        mMediaState = MediaState.PAUSE;
        if (mOnStateChangeListener != null) {
            mOnStateChangeListener.onPause();
        }
    }

    public void start() {
        mPlayFinished = false;
        mMediaPlayer.start();
        mMediaState = MediaState.PLAYING;
    }

    public void stop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMediaState == MediaState.INIT) {
                        return;
                    }
                    if (mMediaState == MediaState.PREPARING) {
                        mMediaPlayer.reset();
                        mMediaState = MediaState.INIT;
                        return;
                    }
                    if (mMediaState == MediaState.PAUSE) {
                        mMediaPlayer.stop();
                        mMediaPlayer.reset();
                        mMediaState = MediaState.INIT;
                        return;
                    }
                    if (mMediaState == MediaState.PLAYING) {
                        mMediaPlayer.pause();
                        mMediaPlayer.stop();
                        mMediaPlayer.reset();
                        mMediaState = MediaState.INIT;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (null != mMediaPlayer)
                        mMediaPlayer.reset();
                    mMediaState = MediaState.INIT;
                } finally {
                    Message.obtain(mHandler, STOP).sendToTarget();
                }
            }
        }).start();
    }

    public enum MediaState {
        INIT, PREPARING, PLAYING, PAUSE, RELEASE
    }

    public interface OnStateChangeListener {
        void onSurfaceTextureDestroyed(SurfaceTexture surface);

        void onBuffering();

        void onPlaying();

        void onSeek(int max, int progress);

        void onStop();

        void onPause();

        void onTextureViewAvaliable();

        void playFinish();

        void onPrepare();
    }

}
