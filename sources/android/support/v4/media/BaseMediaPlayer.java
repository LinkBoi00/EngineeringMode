package android.support.v4.media;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class BaseMediaPlayer implements AutoCloseable {
    public static final int BUFFERING_STATE_BUFFERING_AND_PLAYABLE = 1;
    public static final int BUFFERING_STATE_BUFFERING_AND_STARVED = 2;
    public static final int BUFFERING_STATE_BUFFERING_COMPLETE = 3;
    public static final int BUFFERING_STATE_UNKNOWN = 0;
    public static final int PLAYER_STATE_ERROR = 3;
    public static final int PLAYER_STATE_IDLE = 0;
    public static final int PLAYER_STATE_PAUSED = 1;
    public static final int PLAYER_STATE_PLAYING = 2;
    public static final long UNKNOWN_TIME = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BuffState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayerState {
    }

    public abstract AudioAttributesCompat getAudioAttributes();

    public abstract int getBufferingState();

    public abstract DataSourceDesc getCurrentDataSource();

    public abstract int getPlayerState();

    public abstract float getPlayerVolume();

    public abstract void loopCurrent(boolean z);

    public abstract void pause();

    public abstract void play();

    public abstract void prepare();

    public abstract void registerPlayerEventCallback(Executor executor, PlayerEventCallback playerEventCallback);

    public abstract void reset();

    public abstract void seekTo(long j);

    public abstract void setAudioAttributes(AudioAttributesCompat audioAttributesCompat);

    public abstract void setDataSource(DataSourceDesc dataSourceDesc);

    public abstract void setNextDataSource(DataSourceDesc dataSourceDesc);

    public abstract void setNextDataSources(List<DataSourceDesc> list);

    public abstract void setPlaybackSpeed(float f);

    public abstract void setPlayerVolume(float f);

    public abstract void skipToNext();

    public abstract void unregisterPlayerEventCallback(PlayerEventCallback playerEventCallback);

    public long getCurrentPosition() {
        return -1;
    }

    public long getDuration() {
        return -1;
    }

    public long getBufferedPosition() {
        return -1;
    }

    public float getPlaybackSpeed() {
        return 1.0f;
    }

    public boolean isReversePlaybackSupported() {
        return false;
    }

    public float getMaxPlayerVolume() {
        return 1.0f;
    }

    public static abstract class PlayerEventCallback {
        public void onCurrentDataSourceChanged(BaseMediaPlayer mpb, DataSourceDesc dsd) {
        }

        public void onMediaPrepared(BaseMediaPlayer mpb, DataSourceDesc dsd) {
        }

        public void onPlayerStateChanged(BaseMediaPlayer mpb, int state) {
        }

        public void onBufferingStateChanged(BaseMediaPlayer mpb, DataSourceDesc dsd, int state) {
        }

        public void onPlaybackSpeedChanged(BaseMediaPlayer mpb, float speed) {
        }

        public void onSeekCompleted(BaseMediaPlayer mpb, long position) {
        }
    }
}
