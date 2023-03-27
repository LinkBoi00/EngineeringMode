package android.support.v4.media;

import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class MediaPlaylistAgent {
    public static final int REPEAT_MODE_ALL = 2;
    public static final int REPEAT_MODE_GROUP = 3;
    public static final int REPEAT_MODE_NONE = 0;
    public static final int REPEAT_MODE_ONE = 1;
    public static final int SHUFFLE_MODE_ALL = 1;
    public static final int SHUFFLE_MODE_GROUP = 2;
    public static final int SHUFFLE_MODE_NONE = 0;
    private static final String TAG = "MediaPlaylistAgent";
    private final SimpleArrayMap<PlaylistEventCallback, Executor> mCallbacks = new SimpleArrayMap<>();
    private final Object mLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShuffleMode {
    }

    public abstract void addPlaylistItem(int i, MediaItem2 mediaItem2);

    public abstract MediaItem2 getCurrentMediaItem();

    public abstract List<MediaItem2> getPlaylist();

    public abstract MediaMetadata2 getPlaylistMetadata();

    public abstract int getRepeatMode();

    public abstract int getShuffleMode();

    public abstract void removePlaylistItem(MediaItem2 mediaItem2);

    public abstract void replacePlaylistItem(int i, MediaItem2 mediaItem2);

    public abstract void setPlaylist(List<MediaItem2> list, MediaMetadata2 mediaMetadata2);

    public abstract void setRepeatMode(int i);

    public abstract void setShuffleMode(int i);

    public abstract void skipToNextItem();

    public abstract void skipToPlaylistItem(MediaItem2 mediaItem2);

    public abstract void skipToPreviousItem();

    public abstract void updatePlaylistMetadata(MediaMetadata2 mediaMetadata2);

    public final void registerPlaylistEventCallback(Executor executor, PlaylistEventCallback callback) {
        if (executor == null) {
            throw new IllegalArgumentException("executor shouldn't be null");
        } else if (callback != null) {
            synchronized (this.mLock) {
                if (this.mCallbacks.get(callback) != null) {
                    Log.w(TAG, "callback is already added. Ignoring.");
                } else {
                    this.mCallbacks.put(callback, executor);
                }
            }
        } else {
            throw new IllegalArgumentException("callback shouldn't be null");
        }
    }

    public final void unregisterPlaylistEventCallback(PlaylistEventCallback callback) {
        if (callback != null) {
            synchronized (this.mLock) {
                this.mCallbacks.remove(callback);
            }
            return;
        }
        throw new IllegalArgumentException("callback shouldn't be null");
    }

    public final void notifyPlaylistChanged() {
        SimpleArrayMap<PlaylistEventCallback, Executor> callbacks = getCallbacks();
        final List<MediaItem2> playlist = getPlaylist();
        final MediaMetadata2 metadata = getPlaylistMetadata();
        for (int i = 0; i < callbacks.size(); i++) {
            final PlaylistEventCallback callback = callbacks.keyAt(i);
            callbacks.valueAt(i).execute(new Runnable() {
                public void run() {
                    callback.onPlaylistChanged(MediaPlaylistAgent.this, playlist, metadata);
                }
            });
        }
    }

    public final void notifyPlaylistMetadataChanged() {
        SimpleArrayMap<PlaylistEventCallback, Executor> callbacks = getCallbacks();
        for (int i = 0; i < callbacks.size(); i++) {
            final PlaylistEventCallback callback = callbacks.keyAt(i);
            callbacks.valueAt(i).execute(new Runnable() {
                public void run() {
                    PlaylistEventCallback playlistEventCallback = callback;
                    MediaPlaylistAgent mediaPlaylistAgent = MediaPlaylistAgent.this;
                    playlistEventCallback.onPlaylistMetadataChanged(mediaPlaylistAgent, mediaPlaylistAgent.getPlaylistMetadata());
                }
            });
        }
    }

    public final void notifyShuffleModeChanged() {
        SimpleArrayMap<PlaylistEventCallback, Executor> callbacks = getCallbacks();
        for (int i = 0; i < callbacks.size(); i++) {
            final PlaylistEventCallback callback = callbacks.keyAt(i);
            callbacks.valueAt(i).execute(new Runnable() {
                public void run() {
                    PlaylistEventCallback playlistEventCallback = callback;
                    MediaPlaylistAgent mediaPlaylistAgent = MediaPlaylistAgent.this;
                    playlistEventCallback.onShuffleModeChanged(mediaPlaylistAgent, mediaPlaylistAgent.getShuffleMode());
                }
            });
        }
    }

    public final void notifyRepeatModeChanged() {
        SimpleArrayMap<PlaylistEventCallback, Executor> callbacks = getCallbacks();
        for (int i = 0; i < callbacks.size(); i++) {
            final PlaylistEventCallback callback = callbacks.keyAt(i);
            callbacks.valueAt(i).execute(new Runnable() {
                public void run() {
                    PlaylistEventCallback playlistEventCallback = callback;
                    MediaPlaylistAgent mediaPlaylistAgent = MediaPlaylistAgent.this;
                    playlistEventCallback.onRepeatModeChanged(mediaPlaylistAgent, mediaPlaylistAgent.getRepeatMode());
                }
            });
        }
    }

    public MediaItem2 getMediaItem(DataSourceDesc dsd) {
        if (dsd != null) {
            List<MediaItem2> itemList = getPlaylist();
            if (itemList == null) {
                return null;
            }
            for (int i = 0; i < itemList.size(); i++) {
                MediaItem2 item = itemList.get(i);
                if (item != null && item.getDataSourceDesc().equals(dsd)) {
                    return item;
                }
            }
            return null;
        }
        throw new IllegalArgumentException("dsd shouldn't be null");
    }

    private SimpleArrayMap<PlaylistEventCallback, Executor> getCallbacks() {
        SimpleArrayMap<PlaylistEventCallback, Executor> callbacks = new SimpleArrayMap<>();
        synchronized (this.mLock) {
            callbacks.putAll(this.mCallbacks);
        }
        return callbacks;
    }

    public static abstract class PlaylistEventCallback {
        public void onPlaylistChanged(MediaPlaylistAgent playlistAgent, List<MediaItem2> list, MediaMetadata2 metadata) {
        }

        public void onPlaylistMetadataChanged(MediaPlaylistAgent playlistAgent, MediaMetadata2 metadata) {
        }

        public void onShuffleModeChanged(MediaPlaylistAgent playlistAgent, int shuffleMode) {
        }

        public void onRepeatModeChanged(MediaPlaylistAgent playlistAgent, int repeatMode) {
        }
    }
}
