package android.support.v4.media;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.mediacompat.Rating2;
import android.support.v4.media.MediaController2;
import android.support.v4.media.MediaInterface2;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

public class MediaSession2 implements MediaInterface2.SessionPlayer, AutoCloseable {
    public static final int ERROR_CODE_ACTION_ABORTED = 10;
    public static final int ERROR_CODE_APP_ERROR = 1;
    public static final int ERROR_CODE_AUTHENTICATION_EXPIRED = 3;
    public static final int ERROR_CODE_CONCURRENT_STREAM_LIMIT = 5;
    public static final int ERROR_CODE_CONTENT_ALREADY_PLAYING = 8;
    public static final int ERROR_CODE_END_OF_QUEUE = 11;
    public static final int ERROR_CODE_NOT_AVAILABLE_IN_REGION = 7;
    public static final int ERROR_CODE_NOT_SUPPORTED = 2;
    public static final int ERROR_CODE_PARENTAL_CONTROL_RESTRICTED = 6;
    public static final int ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED = 4;
    public static final int ERROR_CODE_SETUP_REQUIRED = 12;
    public static final int ERROR_CODE_SKIP_LIMIT_REACHED = 9;
    public static final int ERROR_CODE_UNKNOWN_ERROR = 0;
    static final String TAG = "MediaSession2";
    private final SupportLibraryImpl mImpl;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    public interface OnDataSourceMissingHelper {
        DataSourceDesc onDataSourceMissing(MediaSession2 mediaSession2, MediaItem2 mediaItem2);
    }

    interface SupportLibraryImpl extends MediaInterface2.SessionPlayer, AutoCloseable {
        AudioFocusHandler getAudioFocusHandler();

        SessionCallback getCallback();

        Executor getCallbackExecutor();

        List<ControllerInfo> getConnectedControllers();

        Context getContext();

        MediaSession2 getInstance();

        MediaController2.PlaybackInfo getPlaybackInfo();

        PlaybackStateCompat getPlaybackStateCompat();

        BaseMediaPlayer getPlayer();

        MediaPlaylistAgent getPlaylistAgent();

        PendingIntent getSessionActivity();

        IBinder getSessionBinder();

        MediaSessionCompat getSessionCompat();

        SessionToken2 getToken();

        VolumeProviderCompat getVolumeProvider();

        boolean isClosed();

        void notifyRoutesInfoChanged(ControllerInfo controllerInfo, List<Bundle> list);

        void sendCustomCommand(ControllerInfo controllerInfo, SessionCommand2 sessionCommand2, Bundle bundle, ResultReceiver resultReceiver);

        void sendCustomCommand(SessionCommand2 sessionCommand2, Bundle bundle);

        void setAllowedCommands(ControllerInfo controllerInfo, SessionCommandGroup2 sessionCommandGroup2);

        void setCustomLayout(ControllerInfo controllerInfo, List<CommandButton> list);

        void updatePlayer(BaseMediaPlayer baseMediaPlayer, MediaPlaylistAgent mediaPlaylistAgent, VolumeProviderCompat volumeProviderCompat);
    }

    MediaSession2(Context context, String id, BaseMediaPlayer player, MediaPlaylistAgent playlistAgent, VolumeProviderCompat volumeProvider, PendingIntent sessionActivity, Executor callbackExecutor, SessionCallback callback) {
        this.mImpl = createImpl(context, id, player, playlistAgent, volumeProvider, sessionActivity, callbackExecutor, callback);
    }

    /* access modifiers changed from: package-private */
    public SupportLibraryImpl createImpl(Context context, String id, BaseMediaPlayer player, MediaPlaylistAgent playlistAgent, VolumeProviderCompat volumeProvider, PendingIntent sessionActivity, Executor callbackExecutor, SessionCallback callback) {
        return new MediaSession2ImplBase(this, context, id, player, playlistAgent, volumeProvider, sessionActivity, callbackExecutor, callback);
    }

    /* access modifiers changed from: package-private */
    public SupportLibraryImpl getImpl() {
        return this.mImpl;
    }

    public void updatePlayer(BaseMediaPlayer player, MediaPlaylistAgent playlistAgent, VolumeProviderCompat volumeProvider) {
        this.mImpl.updatePlayer(player, playlistAgent, volumeProvider);
    }

    public void close() {
        try {
            this.mImpl.close();
        } catch (Exception e) {
        }
    }

    public BaseMediaPlayer getPlayer() {
        return this.mImpl.getPlayer();
    }

    public MediaPlaylistAgent getPlaylistAgent() {
        return this.mImpl.getPlaylistAgent();
    }

    public VolumeProviderCompat getVolumeProvider() {
        return this.mImpl.getVolumeProvider();
    }

    public SessionToken2 getToken() {
        return this.mImpl.getToken();
    }

    /* access modifiers changed from: package-private */
    public Context getContext() {
        return this.mImpl.getContext();
    }

    /* access modifiers changed from: package-private */
    public Executor getCallbackExecutor() {
        return this.mImpl.getCallbackExecutor();
    }

    /* access modifiers changed from: package-private */
    public SessionCallback getCallback() {
        return this.mImpl.getCallback();
    }

    /* access modifiers changed from: package-private */
    public AudioFocusHandler getAudioFocusHandler() {
        return this.mImpl.getAudioFocusHandler();
    }

    public List<ControllerInfo> getConnectedControllers() {
        return this.mImpl.getConnectedControllers();
    }

    public void setCustomLayout(ControllerInfo controller, List<CommandButton> layout) {
        this.mImpl.setCustomLayout(controller, layout);
    }

    public void setAllowedCommands(ControllerInfo controller, SessionCommandGroup2 commands) {
        this.mImpl.setAllowedCommands(controller, commands);
    }

    public void sendCustomCommand(SessionCommand2 command, Bundle args) {
        this.mImpl.sendCustomCommand(command, args);
    }

    public void sendCustomCommand(ControllerInfo controller, SessionCommand2 command, Bundle args, ResultReceiver receiver) {
        this.mImpl.sendCustomCommand(controller, command, args, receiver);
    }

    public void play() {
        this.mImpl.play();
    }

    public void pause() {
        this.mImpl.pause();
    }

    public void reset() {
        this.mImpl.reset();
    }

    public void prepare() {
        this.mImpl.prepare();
    }

    public void seekTo(long pos) {
        this.mImpl.seekTo(pos);
    }

    public void skipForward() {
        this.mImpl.skipForward();
    }

    public void skipBackward() {
        this.mImpl.skipBackward();
    }

    public void notifyError(int errorCode, Bundle extras) {
        this.mImpl.notifyError(errorCode, extras);
    }

    public void notifyRoutesInfoChanged(ControllerInfo controller, List<Bundle> routes) {
        this.mImpl.notifyRoutesInfoChanged(controller, routes);
    }

    public int getPlayerState() {
        return this.mImpl.getPlayerState();
    }

    public long getCurrentPosition() {
        return this.mImpl.getCurrentPosition();
    }

    public long getDuration() {
        return this.mImpl.getDuration();
    }

    public long getBufferedPosition() {
        return this.mImpl.getBufferedPosition();
    }

    public int getBufferingState() {
        return this.mImpl.getBufferingState();
    }

    public float getPlaybackSpeed() {
        return this.mImpl.getPlaybackSpeed();
    }

    public void setPlaybackSpeed(float speed) {
        this.mImpl.setPlaybackSpeed(speed);
    }

    public void setOnDataSourceMissingHelper(OnDataSourceMissingHelper helper) {
        this.mImpl.setOnDataSourceMissingHelper(helper);
    }

    public void clearOnDataSourceMissingHelper() {
        this.mImpl.clearOnDataSourceMissingHelper();
    }

    public List<MediaItem2> getPlaylist() {
        return this.mImpl.getPlaylist();
    }

    public void setPlaylist(List<MediaItem2> list, MediaMetadata2 metadata) {
        this.mImpl.setPlaylist(list, metadata);
    }

    public void skipToPlaylistItem(MediaItem2 item) {
        this.mImpl.skipToPlaylistItem(item);
    }

    public void skipToPreviousItem() {
        this.mImpl.skipToPreviousItem();
    }

    public void skipToNextItem() {
        this.mImpl.skipToNextItem();
    }

    public MediaMetadata2 getPlaylistMetadata() {
        return this.mImpl.getPlaylistMetadata();
    }

    public void addPlaylistItem(int index, MediaItem2 item) {
        this.mImpl.addPlaylistItem(index, item);
    }

    public void removePlaylistItem(MediaItem2 item) {
        this.mImpl.removePlaylistItem(item);
    }

    public void replacePlaylistItem(int index, MediaItem2 item) {
        this.mImpl.replacePlaylistItem(index, item);
    }

    public MediaItem2 getCurrentMediaItem() {
        return this.mImpl.getCurrentMediaItem();
    }

    public void updatePlaylistMetadata(MediaMetadata2 metadata) {
        this.mImpl.updatePlaylistMetadata(metadata);
    }

    public int getRepeatMode() {
        return this.mImpl.getRepeatMode();
    }

    public void setRepeatMode(int repeatMode) {
        this.mImpl.setRepeatMode(repeatMode);
    }

    public int getShuffleMode() {
        return this.mImpl.getShuffleMode();
    }

    public void setShuffleMode(int shuffleMode) {
        this.mImpl.setShuffleMode(shuffleMode);
    }

    public MediaSessionCompat getSessionCompat() {
        return this.mImpl.getSessionCompat();
    }

    /* access modifiers changed from: package-private */
    public IBinder getSessionBinder() {
        return this.mImpl.getSessionBinder();
    }

    public static abstract class SessionCallback {
        public SessionCommandGroup2 onConnect(MediaSession2 session, ControllerInfo controller) {
            SessionCommandGroup2 commands = new SessionCommandGroup2();
            commands.addAllPredefinedCommands();
            return commands;
        }

        public void onDisconnected(MediaSession2 session, ControllerInfo controller) {
        }

        public boolean onCommandRequest(MediaSession2 session, ControllerInfo controller, SessionCommand2 command) {
            return true;
        }

        public void onSetRating(MediaSession2 session, ControllerInfo controller, String mediaId, Rating2 rating) {
        }

        public void onCustomCommand(MediaSession2 session, ControllerInfo controller, SessionCommand2 customCommand, Bundle args, ResultReceiver cb) {
        }

        public void onPlayFromMediaId(MediaSession2 session, ControllerInfo controller, String mediaId, Bundle extras) {
        }

        public void onPlayFromSearch(MediaSession2 session, ControllerInfo controller, String query, Bundle extras) {
        }

        public void onPlayFromUri(MediaSession2 session, ControllerInfo controller, Uri uri, Bundle extras) {
        }

        public void onPrepareFromMediaId(MediaSession2 session, ControllerInfo controller, String mediaId, Bundle extras) {
        }

        public void onPrepareFromSearch(MediaSession2 session, ControllerInfo controller, String query, Bundle extras) {
        }

        public void onPrepareFromUri(MediaSession2 session, ControllerInfo controller, Uri uri, Bundle extras) {
        }

        public void onFastForward(MediaSession2 session, ControllerInfo controller) {
        }

        public void onRewind(MediaSession2 session, ControllerInfo controller) {
        }

        public void onSubscribeRoutesInfo(MediaSession2 session, ControllerInfo controller) {
        }

        public void onUnsubscribeRoutesInfo(MediaSession2 session, ControllerInfo controller) {
        }

        public void onSelectRoute(MediaSession2 session, ControllerInfo controller, Bundle route) {
        }

        public void onCurrentMediaItemChanged(MediaSession2 session, BaseMediaPlayer player, MediaItem2 item) {
        }

        public void onMediaPrepared(MediaSession2 session, BaseMediaPlayer player, MediaItem2 item) {
        }

        public void onPlayerStateChanged(MediaSession2 session, BaseMediaPlayer player, int state) {
        }

        public void onBufferingStateChanged(MediaSession2 session, BaseMediaPlayer player, MediaItem2 item, int state) {
        }

        public void onPlaybackSpeedChanged(MediaSession2 session, BaseMediaPlayer player, float speed) {
        }

        public void onSeekCompleted(MediaSession2 session, BaseMediaPlayer player, long position) {
        }

        public void onPlaylistChanged(MediaSession2 session, MediaPlaylistAgent playlistAgent, List<MediaItem2> list, MediaMetadata2 metadata) {
        }

        public void onPlaylistMetadataChanged(MediaSession2 session, MediaPlaylistAgent playlistAgent, MediaMetadata2 metadata) {
        }

        public void onShuffleModeChanged(MediaSession2 session, MediaPlaylistAgent playlistAgent, int shuffleMode) {
        }

        public void onRepeatModeChanged(MediaSession2 session, MediaPlaylistAgent playlistAgent, int repeatMode) {
        }
    }

    public static final class Builder extends BuilderBase<MediaSession2, Builder, SessionCallback> {
        public Builder(Context context) {
            super(context);
        }

        public Builder setPlayer(BaseMediaPlayer player) {
            return (Builder) super.setPlayer(player);
        }

        public Builder setPlaylistAgent(MediaPlaylistAgent playlistAgent) {
            return (Builder) super.setPlaylistAgent(playlistAgent);
        }

        public Builder setVolumeProvider(VolumeProviderCompat volumeProvider) {
            return (Builder) super.setVolumeProvider(volumeProvider);
        }

        public Builder setSessionActivity(PendingIntent pi) {
            return (Builder) super.setSessionActivity(pi);
        }

        public Builder setId(String id) {
            return (Builder) super.setId(id);
        }

        public Builder setSessionCallback(Executor executor, SessionCallback callback) {
            return (Builder) super.setSessionCallback(executor, callback);
        }

        public MediaSession2 build() {
            if (this.mCallbackExecutor == null) {
                this.mCallbackExecutor = new MainHandlerExecutor(this.mContext);
            }
            if (this.mCallback == null) {
                this.mCallback = new SessionCallback() {
                };
            }
            return new MediaSession2(this.mContext, this.mId, this.mPlayer, this.mPlaylistAgent, this.mVolumeProvider, this.mSessionActivity, this.mCallbackExecutor, this.mCallback);
        }
    }

    public static final class ControllerInfo {
        private final ControllerCb mControllerCb;
        private final boolean mIsTrusted = false;
        private final String mPackageName;
        private final int mUid;

        ControllerInfo(String packageName, int pid, int uid, ControllerCb cb) {
            this.mUid = uid;
            this.mPackageName = packageName;
            this.mControllerCb = cb;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int getUid() {
            return this.mUid;
        }

        public boolean isTrusted() {
            return this.mIsTrusted;
        }

        public int hashCode() {
            return this.mControllerCb.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ControllerInfo)) {
                return false;
            }
            return this.mControllerCb.equals(((ControllerInfo) obj).mControllerCb);
        }

        public String toString() {
            return "ControllerInfo {pkg=" + this.mPackageName + ", uid=" + this.mUid + "})";
        }

        /* access modifiers changed from: package-private */
        public IBinder getId() {
            return this.mControllerCb.getId();
        }

        /* access modifiers changed from: package-private */
        public ControllerCb getControllerCb() {
            return this.mControllerCb;
        }
    }

    public static final class CommandButton {
        private static final String KEY_COMMAND = "android.media.media_session2.command_button.command";
        private static final String KEY_DISPLAY_NAME = "android.media.media_session2.command_button.display_name";
        private static final String KEY_ENABLED = "android.media.media_session2.command_button.enabled";
        private static final String KEY_EXTRAS = "android.media.media_session2.command_button.extras";
        private static final String KEY_ICON_RES_ID = "android.media.media_session2.command_button.icon_res_id";
        private SessionCommand2 mCommand;
        private String mDisplayName;
        private boolean mEnabled;
        private Bundle mExtras;
        private int mIconResId;

        private CommandButton(SessionCommand2 command, int iconResId, String displayName, Bundle extras, boolean enabled) {
            this.mCommand = command;
            this.mIconResId = iconResId;
            this.mDisplayName = displayName;
            this.mExtras = extras;
            this.mEnabled = enabled;
        }

        public SessionCommand2 getCommand() {
            return this.mCommand;
        }

        public int getIconResId() {
            return this.mIconResId;
        }

        public String getDisplayName() {
            return this.mDisplayName;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBundle(KEY_COMMAND, this.mCommand.toBundle());
            bundle.putInt(KEY_ICON_RES_ID, this.mIconResId);
            bundle.putString(KEY_DISPLAY_NAME, this.mDisplayName);
            bundle.putBundle(KEY_EXTRAS, this.mExtras);
            bundle.putBoolean(KEY_ENABLED, this.mEnabled);
            return bundle;
        }

        public static CommandButton fromBundle(Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            Builder builder = new Builder();
            builder.setCommand(SessionCommand2.fromBundle(bundle.getBundle(KEY_COMMAND)));
            builder.setIconResId(bundle.getInt(KEY_ICON_RES_ID, 0));
            builder.setDisplayName(bundle.getString(KEY_DISPLAY_NAME));
            builder.setExtras(bundle.getBundle(KEY_EXTRAS));
            builder.setEnabled(bundle.getBoolean(KEY_ENABLED));
            try {
                return builder.build();
            } catch (IllegalStateException e) {
                return null;
            }
        }

        public static final class Builder {
            private SessionCommand2 mCommand;
            private String mDisplayName;
            private boolean mEnabled;
            private Bundle mExtras;
            private int mIconResId;

            public Builder setCommand(SessionCommand2 command) {
                this.mCommand = command;
                return this;
            }

            public Builder setIconResId(int resId) {
                this.mIconResId = resId;
                return this;
            }

            public Builder setDisplayName(String displayName) {
                this.mDisplayName = displayName;
                return this;
            }

            public Builder setEnabled(boolean enabled) {
                this.mEnabled = enabled;
                return this;
            }

            public Builder setExtras(Bundle extras) {
                this.mExtras = extras;
                return this;
            }

            public CommandButton build() {
                return new CommandButton(this.mCommand, this.mIconResId, this.mDisplayName, this.mExtras, this.mEnabled);
            }
        }
    }

    static abstract class ControllerCb {
        /* access modifiers changed from: package-private */
        public abstract IBinder getId();

        /* access modifiers changed from: package-private */
        public abstract void onAllowedCommandsChanged(SessionCommandGroup2 sessionCommandGroup2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onBufferingStateChanged(MediaItem2 mediaItem2, int i, long j) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onChildrenChanged(String str, int i, Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onCurrentMediaItemChanged(MediaItem2 mediaItem2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onCustomCommand(SessionCommand2 sessionCommand2, Bundle bundle, ResultReceiver resultReceiver) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onCustomLayoutChanged(List<CommandButton> list) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onDisconnected() throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onError(int i, Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onGetChildrenDone(String str, int i, int i2, List<MediaItem2> list, Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onGetItemDone(String str, MediaItem2 mediaItem2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onGetLibraryRootDone(Bundle bundle, String str, Bundle bundle2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onGetSearchResultDone(String str, int i, int i2, List<MediaItem2> list, Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlaybackInfoChanged(MediaController2.PlaybackInfo playbackInfo) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlaybackSpeedChanged(long j, long j2, float f) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlayerStateChanged(long j, long j2, int i) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlaylistChanged(List<MediaItem2> list, MediaMetadata2 mediaMetadata2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onPlaylistMetadataChanged(MediaMetadata2 mediaMetadata2) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onRepeatModeChanged(int i) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onRoutesInfoChanged(List<Bundle> list) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onSearchResultChanged(String str, int i, Bundle bundle) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onSeekCompleted(long j, long j2, long j3) throws RemoteException;

        /* access modifiers changed from: package-private */
        public abstract void onShuffleModeChanged(int i) throws RemoteException;

        ControllerCb() {
        }

        public int hashCode() {
            return getId().hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ControllerCb)) {
                return false;
            }
            return getId().equals(((ControllerCb) obj).getId());
        }
    }

    static abstract class BuilderBase<T extends MediaSession2, U extends BuilderBase<T, U, C>, C extends SessionCallback> {
        C mCallback;
        Executor mCallbackExecutor;
        final Context mContext;
        String mId;
        BaseMediaPlayer mPlayer;
        MediaPlaylistAgent mPlaylistAgent;
        PendingIntent mSessionActivity;
        VolumeProviderCompat mVolumeProvider;

        /* access modifiers changed from: package-private */
        public abstract T build();

        BuilderBase(Context context) {
            if (context != null) {
                this.mContext = context;
                this.mId = MediaSession2.TAG;
                return;
            }
            throw new IllegalArgumentException("context shouldn't be null");
        }

        /* access modifiers changed from: package-private */
        public U setPlayer(BaseMediaPlayer player) {
            if (player != null) {
                this.mPlayer = player;
                return this;
            }
            throw new IllegalArgumentException("player shouldn't be null");
        }

        /* access modifiers changed from: package-private */
        public U setPlaylistAgent(MediaPlaylistAgent playlistAgent) {
            if (playlistAgent != null) {
                this.mPlaylistAgent = playlistAgent;
                return this;
            }
            throw new IllegalArgumentException("playlistAgent shouldn't be null");
        }

        /* access modifiers changed from: package-private */
        public U setVolumeProvider(VolumeProviderCompat volumeProvider) {
            this.mVolumeProvider = volumeProvider;
            return this;
        }

        /* access modifiers changed from: package-private */
        public U setSessionActivity(PendingIntent pi) {
            this.mSessionActivity = pi;
            return this;
        }

        /* access modifiers changed from: package-private */
        public U setId(String id) {
            if (id != null) {
                this.mId = id;
                return this;
            }
            throw new IllegalArgumentException("id shouldn't be null");
        }

        /* access modifiers changed from: package-private */
        public U setSessionCallback(Executor executor, C callback) {
            if (executor == null) {
                throw new IllegalArgumentException("executor shouldn't be null");
            } else if (callback != null) {
                this.mCallbackExecutor = executor;
                this.mCallback = callback;
                return this;
            } else {
                throw new IllegalArgumentException("callback shouldn't be null");
            }
        }
    }

    static class MainHandlerExecutor implements Executor {
        private final Handler mHandler;

        MainHandlerExecutor(Context context) {
            this.mHandler = new Handler(context.getMainLooper());
        }

        public void execute(Runnable command) {
            if (!this.mHandler.post(command)) {
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }
}
