package android.support.v4.media;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.media.BaseMediaPlayer;
import android.support.v4.media.MediaController2;
import android.support.v4.media.MediaMetadata2;
import android.support.v4.media.MediaPlaylistAgent;
import android.support.v4.media.MediaSession2;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.util.ObjectsCompat;
import android.text.TextUtils;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;

class MediaSession2ImplBase implements MediaSession2.SupportLibraryImpl {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MS2ImplBase";
    /* access modifiers changed from: private */
    public final AudioFocusHandler mAudioFocusHandler;
    private final AudioManager mAudioManager;
    /* access modifiers changed from: private */
    public final MediaSession2.SessionCallback mCallback;
    private final Executor mCallbackExecutor;
    private final Context mContext;
    private MediaSession2.OnDataSourceMissingHelper mDsmHelper;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private final MediaSession2 mInstance;
    final Object mLock = new Object();
    private MediaController2.PlaybackInfo mPlaybackInfo;
    private BaseMediaPlayer mPlayer;
    private final BaseMediaPlayer.PlayerEventCallback mPlayerEventCallback;
    private MediaPlaylistAgent mPlaylistAgent;
    private final MediaPlaylistAgent.PlaylistEventCallback mPlaylistEventCallback;
    private final MediaSession2Stub mSession2Stub;
    private final PendingIntent mSessionActivity;
    private final MediaSessionCompat mSessionCompat;
    private final MediaSessionLegacyStub mSessionLegacyStub;
    private SessionPlaylistAgentImplBase mSessionPlaylistAgent;
    private final SessionToken2 mSessionToken;
    private VolumeProviderCompat mVolumeProvider;

    @FunctionalInterface
    interface NotifyRunnable {
        void run(MediaSession2.ControllerCb controllerCb) throws RemoteException;
    }

    MediaSession2ImplBase(MediaSession2 instance, Context context, String id, BaseMediaPlayer player, MediaPlaylistAgent playlistAgent, VolumeProviderCompat volumeProvider, PendingIntent sessionActivity, Executor callbackExecutor, MediaSession2.SessionCallback callback) {
        Context context2 = context;
        String str = id;
        PendingIntent pendingIntent = sessionActivity;
        this.mContext = context2;
        this.mInstance = instance;
        HandlerThread handlerThread = new HandlerThread("MediaController2_Thread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        this.mHandler = handler;
        MediaSession2Stub mediaSession2Stub = new MediaSession2Stub(this);
        this.mSession2Stub = mediaSession2Stub;
        MediaSessionLegacyStub mediaSessionLegacyStub = new MediaSessionLegacyStub(this);
        this.mSessionLegacyStub = mediaSessionLegacyStub;
        this.mSessionActivity = pendingIntent;
        this.mCallback = callback;
        this.mCallbackExecutor = callbackExecutor;
        this.mAudioManager = (AudioManager) context2.getSystemService("audio");
        this.mPlayerEventCallback = new MyPlayerEventCallback();
        this.mPlaylistEventCallback = new MyPlaylistEventCallback();
        this.mAudioFocusHandler = new AudioFocusHandler(context2, getInstance());
        String libraryService = getServiceName(context2, MediaLibraryService2.SERVICE_INTERFACE, str);
        String sessionService = getServiceName(context2, MediaSessionService2.SERVICE_INTERFACE, str);
        if (sessionService == null || libraryService == null) {
            if (libraryService != null) {
                SessionToken2ImplBase sessionToken2ImplBase = r2;
                SessionToken2ImplBase sessionToken2ImplBase2 = new SessionToken2ImplBase(Process.myUid(), 2, context.getPackageName(), libraryService, id, mediaSession2Stub);
                this.mSessionToken = new SessionToken2(sessionToken2ImplBase);
            } else if (sessionService != null) {
                SessionToken2ImplBase sessionToken2ImplBase3 = r2;
                SessionToken2ImplBase sessionToken2ImplBase4 = new SessionToken2ImplBase(Process.myUid(), 1, context.getPackageName(), sessionService, id, mediaSession2Stub);
                this.mSessionToken = new SessionToken2(sessionToken2ImplBase3);
            } else {
                SessionToken2ImplBase sessionToken2ImplBase5 = r2;
                SessionToken2ImplBase sessionToken2ImplBase6 = new SessionToken2ImplBase(Process.myUid(), 0, context.getPackageName(), (String) null, id, mediaSession2Stub);
                this.mSessionToken = new SessionToken2(sessionToken2ImplBase5);
            }
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context2, str, this.mSessionToken);
            this.mSessionCompat = mediaSessionCompat;
            mediaSessionCompat.setCallback(mediaSessionLegacyStub, handler);
            mediaSessionCompat.setSessionActivity(pendingIntent);
            updatePlayer(player, playlistAgent, volumeProvider);
            return;
        }
        throw new IllegalArgumentException("Ambiguous session type. Multiple session services define the same id=" + str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0042, code lost:
        if (r13 != null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0044, code lost:
        r10.mSessionCompat.setPlaybackToLocal(getLegacyStreamType(r11.getAudioAttributes()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0051, code lost:
        if (r11 == r3) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0053, code lost:
        r11.registerPlayerEventCallback(r10.mCallbackExecutor, r10.mPlayerEventCallback);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005a, code lost:
        if (r3 == null) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x005c, code lost:
        r3.unregisterPlayerEventCallback(r10.mPlayerEventCallback);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0061, code lost:
        if (r12 == r4) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0063, code lost:
        r12.registerPlaylistEventCallback(r10.mCallbackExecutor, r10.mPlaylistEventCallback);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x006a, code lost:
        if (r4 == null) goto L_0x0071;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006c, code lost:
        r4.unregisterPlaylistEventCallback(r10.mPlaylistEventCallback);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0071, code lost:
        if (r3 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0073, code lost:
        if (r8 == false) goto L_0x0078;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0075, code lost:
        notifyAgentUpdatedNotLocked(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0078, code lost:
        if (r6 == false) goto L_0x007d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x007a, code lost:
        notifyPlayerUpdatedNotLocked(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x007d, code lost:
        if (r2 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x007f, code lost:
        notifyToAllControllers(new android.support.v4.media.MediaSession2ImplBase.AnonymousClass1(r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updatePlayer(android.support.v4.media.BaseMediaPlayer r11, android.support.v4.media.MediaPlaylistAgent r12, android.support.v4.media.VolumeProviderCompat r13) {
        /*
            r10 = this;
            if (r11 == 0) goto L_0x009d
            android.support.v4.media.AudioAttributesCompat r0 = r11.getAudioAttributes()
            android.support.v4.media.MediaController2$PlaybackInfo r0 = r10.createPlaybackInfo(r13, r0)
            java.lang.Object r1 = r10.mLock
            monitor-enter(r1)
            r2 = 0
            r3 = 0
            android.support.v4.media.BaseMediaPlayer r4 = r10.mPlayer     // Catch:{ all -> 0x0091 }
            r5 = 1
            if (r4 == r11) goto L_0x0016
            r6 = r5
            goto L_0x0017
        L_0x0016:
            r6 = r3
        L_0x0017:
            android.support.v4.media.MediaPlaylistAgent r7 = r10.mPlaylistAgent     // Catch:{ all -> 0x008e }
            if (r7 == r12) goto L_0x001d
            r8 = r5
            goto L_0x001e
        L_0x001d:
            r8 = r3
        L_0x001e:
            android.support.v4.media.MediaController2$PlaybackInfo r2 = r10.mPlaybackInfo     // Catch:{ all -> 0x0088 }
            if (r2 == r0) goto L_0x0023
            r3 = r5
        L_0x0023:
            r2 = r3
            r3 = r4
            r4 = r7
            r10.mPlayer = r11     // Catch:{ all -> 0x009b }
            if (r12 != 0) goto L_0x003b
            android.support.v4.media.SessionPlaylistAgentImplBase r5 = new android.support.v4.media.SessionPlaylistAgentImplBase     // Catch:{ all -> 0x009b }
            r5.<init>(r10, r11)     // Catch:{ all -> 0x009b }
            r10.mSessionPlaylistAgent = r5     // Catch:{ all -> 0x009b }
            android.support.v4.media.MediaSession2$OnDataSourceMissingHelper r7 = r10.mDsmHelper     // Catch:{ all -> 0x009b }
            if (r7 == 0) goto L_0x0038
            r5.setOnDataSourceMissingHelper(r7)     // Catch:{ all -> 0x009b }
        L_0x0038:
            android.support.v4.media.SessionPlaylistAgentImplBase r5 = r10.mSessionPlaylistAgent     // Catch:{ all -> 0x009b }
            r12 = r5
        L_0x003b:
            r10.mPlaylistAgent = r12     // Catch:{ all -> 0x009b }
            r10.mVolumeProvider = r13     // Catch:{ all -> 0x009b }
            r10.mPlaybackInfo = r0     // Catch:{ all -> 0x009b }
            monitor-exit(r1)     // Catch:{ all -> 0x009b }
            if (r13 != 0) goto L_0x0051
            android.support.v4.media.AudioAttributesCompat r1 = r11.getAudioAttributes()
            int r1 = r10.getLegacyStreamType(r1)
            android.support.v4.media.session.MediaSessionCompat r5 = r10.mSessionCompat
            r5.setPlaybackToLocal(r1)
        L_0x0051:
            if (r11 == r3) goto L_0x0061
            java.util.concurrent.Executor r1 = r10.mCallbackExecutor
            android.support.v4.media.BaseMediaPlayer$PlayerEventCallback r5 = r10.mPlayerEventCallback
            r11.registerPlayerEventCallback(r1, r5)
            if (r3 == 0) goto L_0x0061
            android.support.v4.media.BaseMediaPlayer$PlayerEventCallback r1 = r10.mPlayerEventCallback
            r3.unregisterPlayerEventCallback(r1)
        L_0x0061:
            if (r12 == r4) goto L_0x0071
            java.util.concurrent.Executor r1 = r10.mCallbackExecutor
            android.support.v4.media.MediaPlaylistAgent$PlaylistEventCallback r5 = r10.mPlaylistEventCallback
            r12.registerPlaylistEventCallback(r1, r5)
            if (r4 == 0) goto L_0x0071
            android.support.v4.media.MediaPlaylistAgent$PlaylistEventCallback r1 = r10.mPlaylistEventCallback
            r4.unregisterPlaylistEventCallback(r1)
        L_0x0071:
            if (r3 == 0) goto L_0x0087
            if (r8 == 0) goto L_0x0078
            r10.notifyAgentUpdatedNotLocked(r4)
        L_0x0078:
            if (r6 == 0) goto L_0x007d
            r10.notifyPlayerUpdatedNotLocked(r3)
        L_0x007d:
            if (r2 == 0) goto L_0x0087
            android.support.v4.media.MediaSession2ImplBase$1 r1 = new android.support.v4.media.MediaSession2ImplBase$1
            r1.<init>(r0)
            r10.notifyToAllControllers(r1)
        L_0x0087:
            return
        L_0x0088:
            r5 = move-exception
            r4 = r2
            r9 = r3
            r3 = r2
            r2 = r9
            goto L_0x0099
        L_0x008e:
            r5 = move-exception
            r4 = r3
            goto L_0x0094
        L_0x0091:
            r5 = move-exception
            r4 = r3
            r6 = r3
        L_0x0094:
            r7 = r2
            r8 = r3
            r3 = r2
            r2 = r4
            r4 = r7
        L_0x0099:
            monitor-exit(r1)     // Catch:{ all -> 0x009b }
            throw r5
        L_0x009b:
            r5 = move-exception
            goto L_0x0099
        L_0x009d:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "player shouldn't be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.updatePlayer(android.support.v4.media.BaseMediaPlayer, android.support.v4.media.MediaPlaylistAgent, android.support.v4.media.VolumeProviderCompat):void");
    }

    private MediaController2.PlaybackInfo createPlaybackInfo(VolumeProviderCompat volumeProvider, AudioAttributesCompat attrs) {
        if (volumeProvider != null) {
            return MediaController2.PlaybackInfo.createPlaybackInfo(2, attrs, volumeProvider.getVolumeControl(), volumeProvider.getMaxVolume(), volumeProvider.getCurrentVolume());
        }
        int stream = getLegacyStreamType(attrs);
        int controlType = 2;
        if (Build.VERSION.SDK_INT >= 21 && this.mAudioManager.isVolumeFixed()) {
            controlType = 0;
        }
        return MediaController2.PlaybackInfo.createPlaybackInfo(1, attrs, controlType, this.mAudioManager.getStreamMaxVolume(stream), this.mAudioManager.getStreamVolume(stream));
    }

    private int getLegacyStreamType(AudioAttributesCompat attrs) {
        int stream;
        if (attrs == null || (stream = attrs.getLegacyStreamType()) == Integer.MIN_VALUE) {
            return 3;
        }
        return stream;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r3.mPlayer     // Catch:{ all -> 0x0045 }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x0045 }
            return
        L_0x0009:
            android.support.v4.media.AudioFocusHandler r1 = r3.mAudioFocusHandler     // Catch:{ all -> 0x0045 }
            r1.close()     // Catch:{ all -> 0x0045 }
            android.support.v4.media.BaseMediaPlayer r1 = r3.mPlayer     // Catch:{ all -> 0x0045 }
            android.support.v4.media.BaseMediaPlayer$PlayerEventCallback r2 = r3.mPlayerEventCallback     // Catch:{ all -> 0x0045 }
            r1.unregisterPlayerEventCallback(r2)     // Catch:{ all -> 0x0045 }
            r1 = 0
            r3.mPlayer = r1     // Catch:{ all -> 0x0045 }
            android.support.v4.media.session.MediaSessionCompat r2 = r3.mSessionCompat     // Catch:{ all -> 0x0045 }
            r2.release()     // Catch:{ all -> 0x0045 }
            android.support.v4.media.MediaSession2ImplBase$2 r2 = new android.support.v4.media.MediaSession2ImplBase$2     // Catch:{ all -> 0x0045 }
            r2.<init>()     // Catch:{ all -> 0x0045 }
            r3.notifyToAllControllers(r2)     // Catch:{ all -> 0x0045 }
            android.os.Handler r2 = r3.mHandler     // Catch:{ all -> 0x0045 }
            r2.removeCallbacksAndMessages(r1)     // Catch:{ all -> 0x0045 }
            android.os.HandlerThread r1 = r3.mHandlerThread     // Catch:{ all -> 0x0045 }
            boolean r1 = r1.isAlive()     // Catch:{ all -> 0x0045 }
            if (r1 == 0) goto L_0x0043
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0045 }
            r2 = 18
            if (r1 < r2) goto L_0x003e
            android.os.HandlerThread r1 = r3.mHandlerThread     // Catch:{ all -> 0x0045 }
            r1.quitSafely()     // Catch:{ all -> 0x0045 }
            goto L_0x0043
        L_0x003e:
            android.os.HandlerThread r1 = r3.mHandlerThread     // Catch:{ all -> 0x0045 }
            r1.quit()     // Catch:{ all -> 0x0045 }
        L_0x0043:
            monitor-exit(r0)     // Catch:{ all -> 0x0045 }
            return
        L_0x0045:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0045 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.close():void");
    }

    public BaseMediaPlayer getPlayer() {
        BaseMediaPlayer baseMediaPlayer;
        synchronized (this.mLock) {
            baseMediaPlayer = this.mPlayer;
        }
        return baseMediaPlayer;
    }

    public MediaPlaylistAgent getPlaylistAgent() {
        MediaPlaylistAgent mediaPlaylistAgent;
        synchronized (this.mLock) {
            mediaPlaylistAgent = this.mPlaylistAgent;
        }
        return mediaPlaylistAgent;
    }

    public VolumeProviderCompat getVolumeProvider() {
        VolumeProviderCompat volumeProviderCompat;
        synchronized (this.mLock) {
            volumeProviderCompat = this.mVolumeProvider;
        }
        return volumeProviderCompat;
    }

    public SessionToken2 getToken() {
        return this.mSessionToken;
    }

    public List<MediaSession2.ControllerInfo> getConnectedControllers() {
        return this.mSession2Stub.getConnectedControllers();
    }

    public void setCustomLayout(MediaSession2.ControllerInfo controller, final List<MediaSession2.CommandButton> layout) {
        if (controller == null) {
            throw new IllegalArgumentException("controller shouldn't be null");
        } else if (layout != null) {
            notifyToController(controller, new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onCustomLayoutChanged(layout);
                }
            });
        } else {
            throw new IllegalArgumentException("layout shouldn't be null");
        }
    }

    public void setAllowedCommands(MediaSession2.ControllerInfo controller, final SessionCommandGroup2 commands) {
        if (controller == null) {
            throw new IllegalArgumentException("controller shouldn't be null");
        } else if (commands != null) {
            this.mSession2Stub.setAllowedCommands(controller, commands);
            notifyToController(controller, new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onAllowedCommandsChanged(commands);
                }
            });
        } else {
            throw new IllegalArgumentException("commands shouldn't be null");
        }
    }

    public void sendCustomCommand(final SessionCommand2 command, final Bundle args) {
        if (command != null) {
            notifyToAllControllers(new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onCustomCommand(command, args, (ResultReceiver) null);
                }
            });
            return;
        }
        throw new IllegalArgumentException("command shouldn't be null");
    }

    public void sendCustomCommand(MediaSession2.ControllerInfo controller, final SessionCommand2 command, final Bundle args, final ResultReceiver receiver) {
        if (controller == null) {
            throw new IllegalArgumentException("controller shouldn't be null");
        } else if (command != null) {
            notifyToController(controller, new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onCustomCommand(command, args, receiver);
                }
            });
        } else {
            throw new IllegalArgumentException("command shouldn't be null");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        android.util.Log.w(TAG, "play() wouldn't be called because of the failure in audio focus");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        if (r4.mAudioFocusHandler.onPlayRequested() == false) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        r1.play();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void play() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x002f }
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            if (r1 == 0) goto L_0x001c
            android.support.v4.media.AudioFocusHandler r0 = r4.mAudioFocusHandler
            boolean r0 = r0.onPlayRequested()
            if (r0 == 0) goto L_0x0014
            r1.play()
            goto L_0x002c
        L_0x0014:
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "play() wouldn't be called because of the failure in audio focus"
            android.util.Log.w(r0, r2)
            goto L_0x002c
        L_0x001c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x002c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x002c:
            return
        L_0x002d:
            r2 = move-exception
            goto L_0x0031
        L_0x002f:
            r2 = move-exception
            r1 = 0
        L_0x0031:
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.play():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        android.util.Log.w(TAG, "pause() wouldn't be called of the failure in audio focus");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000e, code lost:
        if (r4.mAudioFocusHandler.onPauseRequested() == false) goto L_0x0014;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0010, code lost:
        r1.pause();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pause() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x002f }
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            if (r1 == 0) goto L_0x001c
            android.support.v4.media.AudioFocusHandler r0 = r4.mAudioFocusHandler
            boolean r0 = r0.onPauseRequested()
            if (r0 == 0) goto L_0x0014
            r1.pause()
            goto L_0x002c
        L_0x0014:
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "pause() wouldn't be called of the failure in audio focus"
            android.util.Log.w(r0, r2)
            goto L_0x002c
        L_0x001c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x002c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x002c:
            return
        L_0x002d:
            r2 = move-exception
            goto L_0x0031
        L_0x002f:
            r2 = move-exception
            r1 = 0
        L_0x0031:
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.pause():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.reset();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void reset() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.reset()
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.reset():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.prepare();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void prepare() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.prepare()
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.prepare():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.seekTo(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void seekTo(long r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.seekTo(r5)
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.seekTo(long):void");
    }

    public void skipForward() {
    }

    public void skipBackward() {
    }

    public void notifyError(final int errorCode, final Bundle extras) {
        notifyToAllControllers(new NotifyRunnable() {
            public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                callback.onError(errorCode, extras);
            }
        });
    }

    public void notifyRoutesInfoChanged(MediaSession2.ControllerInfo controller, final List<Bundle> routes) {
        notifyToController(controller, new NotifyRunnable() {
            public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                callback.onRoutesInfoChanged(routes);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return r1.getPlayerState();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getPlayerState() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x000d
            int r0 = r1.getPlayerState()
            return r0
        L_0x000d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001d:
            r0 = 3
            return r0
        L_0x001f:
            r2 = move-exception
            goto L_0x0023
        L_0x0021:
            r2 = move-exception
            r1 = 0
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getPlayerState():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return r1.getCurrentPosition();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getCurrentPosition() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x0022 }
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x000d
            long r2 = r1.getCurrentPosition()
            return r2
        L_0x000d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001d:
            r2 = -1
            return r2
        L_0x0020:
            r2 = move-exception
            goto L_0x0024
        L_0x0022:
            r2 = move-exception
            r1 = 0
        L_0x0024:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getCurrentPosition():long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return r1.getDuration();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getDuration() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x0022 }
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x000d
            long r2 = r1.getDuration()
            return r2
        L_0x000d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001d:
            r2 = -1
            return r2
        L_0x0020:
            r2 = move-exception
            goto L_0x0024
        L_0x0022:
            r2 = move-exception
            r1 = 0
        L_0x0024:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getDuration():long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return r1.getBufferedPosition();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getBufferedPosition() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x0022 }
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x000d
            long r2 = r1.getBufferedPosition()
            return r2
        L_0x000d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001d:
            r2 = -1
            return r2
        L_0x0020:
            r2 = move-exception
            goto L_0x0024
        L_0x0022:
            r2 = move-exception
            r1 = 0
        L_0x0024:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getBufferedPosition():long");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return r1.getBufferingState();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getBufferingState() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x000d
            int r0 = r1.getBufferingState()
            return r0
        L_0x000d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001d:
            r0 = 0
            return r0
        L_0x001f:
            r2 = move-exception
            goto L_0x0023
        L_0x0021:
            r2 = move-exception
            r1 = 0
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getBufferingState():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return 1.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return 1.0f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return r1.getPlaybackSpeed();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float getPlaybackSpeed() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x0022 }
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x000d
            float r0 = r1.getPlaybackSpeed()
            return r0
        L_0x000d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001d:
            r0 = 1065353216(0x3f800000, float:1.0)
            return r0
        L_0x0020:
            r2 = move-exception
            goto L_0x0024
        L_0x0022:
            r2 = move-exception
            r1 = 0
        L_0x0024:
            monitor-exit(r0)     // Catch:{ all -> 0x0020 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getPlaybackSpeed():float");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.setPlaybackSpeed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setPlaybackSpeed(float r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.setPlaybackSpeed(r5)
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.setPlaybackSpeed(float):void");
    }

    public void setOnDataSourceMissingHelper(MediaSession2.OnDataSourceMissingHelper helper) {
        if (helper != null) {
            synchronized (this.mLock) {
                this.mDsmHelper = helper;
                SessionPlaylistAgentImplBase sessionPlaylistAgentImplBase = this.mSessionPlaylistAgent;
                if (sessionPlaylistAgentImplBase != null) {
                    sessionPlaylistAgentImplBase.setOnDataSourceMissingHelper(helper);
                }
            }
            return;
        }
        throw new IllegalArgumentException("helper shouldn't be null");
    }

    public void clearOnDataSourceMissingHelper() {
        synchronized (this.mLock) {
            this.mDsmHelper = null;
            SessionPlaylistAgentImplBase sessionPlaylistAgentImplBase = this.mSessionPlaylistAgent;
            if (sessionPlaylistAgentImplBase != null) {
                sessionPlaylistAgentImplBase.clearOnDataSourceMissingHelper();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0010, code lost:
        if (DEBUG == false) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0012, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0007, code lost:
        if (r2 == null) goto L_0x000e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000d, code lost:
        return r2.getPlaylist();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<android.support.v4.media.MediaItem2> getPlaylist() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            r1 = 0
            android.support.v4.media.MediaPlaylistAgent r2 = r6.mPlaylistAgent     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r2 == 0) goto L_0x000e
            java.util.List r0 = r2.getPlaylist()
            return r0
        L_0x000e:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r3 = "API calls after the close()"
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            r4.<init>()
            android.util.Log.d(r0, r3, r4)
        L_0x001e:
            return r1
        L_0x001f:
            r1 = move-exception
            goto L_0x0025
        L_0x0021:
            r2 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
        L_0x0025:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getPlaylist():java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        if (r1 == null) goto L_0x000e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        r1.setPlaylist(r5, r6);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setPlaylist(java.util.List<android.support.v4.media.MediaItem2> r5, android.support.v4.media.MediaMetadata2 r6) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0025
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x000e
            r1.setPlaylist(r5, r6)
            goto L_0x001e
        L_0x000e:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001e:
            return
        L_0x001f:
            r2 = move-exception
            goto L_0x0023
        L_0x0021:
            r2 = move-exception
            r1 = 0
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r2
        L_0x0025:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "list shouldn't be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.setPlaylist(java.util.List, android.support.v4.media.MediaMetadata2):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        if (r1 == null) goto L_0x000e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        r1.skipToPlaylistItem(r5);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void skipToPlaylistItem(android.support.v4.media.MediaItem2 r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0025
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x000e
            r1.skipToPlaylistItem(r5)
            goto L_0x001e
        L_0x000e:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001e:
            return
        L_0x001f:
            r2 = move-exception
            goto L_0x0023
        L_0x0021:
            r2 = move-exception
            r1 = 0
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r2
        L_0x0025:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "item shouldn't be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.skipToPlaylistItem(android.support.v4.media.MediaItem2):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.skipToPreviousItem();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void skipToPreviousItem() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.skipToPreviousItem()
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.skipToPreviousItem():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.skipToNextItem();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void skipToNextItem() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.skipToNextItem()
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.skipToNextItem():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0010, code lost:
        if (DEBUG == false) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0012, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0007, code lost:
        if (r2 == null) goto L_0x000e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000d, code lost:
        return r2.getPlaylistMetadata();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.support.v4.media.MediaMetadata2 getPlaylistMetadata() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            r1 = 0
            android.support.v4.media.MediaPlaylistAgent r2 = r6.mPlaylistAgent     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r2 == 0) goto L_0x000e
            android.support.v4.media.MediaMetadata2 r0 = r2.getPlaylistMetadata()
            return r0
        L_0x000e:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r3 = "API calls after the close()"
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            r4.<init>()
            android.util.Log.d(r0, r3, r4)
        L_0x001e:
            return r1
        L_0x001f:
            r1 = move-exception
            goto L_0x0025
        L_0x0021:
            r2 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
        L_0x0025:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getPlaylistMetadata():android.support.v4.media.MediaMetadata2");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0014, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        if (r1 == null) goto L_0x0010;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000c, code lost:
        r1.addPlaylistItem(r5, r6);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addPlaylistItem(int r5, android.support.v4.media.MediaItem2 r6) {
        /*
            r4 = this;
            if (r5 < 0) goto L_0x002f
            if (r6 == 0) goto L_0x0027
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x0023 }
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x0010
            r1.addPlaylistItem(r5, r6)
            goto L_0x0020
        L_0x0010:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0020
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x0020:
            return
        L_0x0021:
            r2 = move-exception
            goto L_0x0025
        L_0x0023:
            r2 = move-exception
            r1 = 0
        L_0x0025:
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            throw r2
        L_0x0027:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "item shouldn't be null"
            r0.<init>(r1)
            throw r0
        L_0x002f:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "index shouldn't be negative"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.addPlaylistItem(int, android.support.v4.media.MediaItem2):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        if (r1 == null) goto L_0x000e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        r1.removePlaylistItem(r5);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removePlaylistItem(android.support.v4.media.MediaItem2 r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0025
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x000e
            r1.removePlaylistItem(r5)
            goto L_0x001e
        L_0x000e:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001e:
            return
        L_0x001f:
            r2 = move-exception
            goto L_0x0023
        L_0x0021:
            r2 = move-exception
            r1 = 0
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r2
        L_0x0025:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "item shouldn't be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.removePlaylistItem(android.support.v4.media.MediaItem2):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0012, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0014, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000a, code lost:
        if (r1 == null) goto L_0x0010;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000c, code lost:
        r1.replacePlaylistItem(r5, r6);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void replacePlaylistItem(int r5, android.support.v4.media.MediaItem2 r6) {
        /*
            r4 = this;
            if (r5 < 0) goto L_0x002f
            if (r6 == 0) goto L_0x0027
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x0023 }
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x0010
            r1.replacePlaylistItem(r5, r6)
            goto L_0x0020
        L_0x0010:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0020
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x0020:
            return
        L_0x0021:
            r2 = move-exception
            goto L_0x0025
        L_0x0023:
            r2 = move-exception
            r1 = 0
        L_0x0025:
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            throw r2
        L_0x0027:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "item shouldn't be null"
            r0.<init>(r1)
            throw r0
        L_0x002f:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "index shouldn't be negative"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.replacePlaylistItem(int, android.support.v4.media.MediaItem2):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0010, code lost:
        if (DEBUG == false) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0012, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0007, code lost:
        if (r2 == null) goto L_0x000e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000d, code lost:
        return r2.getCurrentMediaItem();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.support.v4.media.MediaItem2 getCurrentMediaItem() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            r1 = 0
            android.support.v4.media.MediaPlaylistAgent r2 = r6.mPlaylistAgent     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r2 == 0) goto L_0x000e
            android.support.v4.media.MediaItem2 r0 = r2.getCurrentMediaItem()
            return r0
        L_0x000e:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001e
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r3 = "API calls after the close()"
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            r4.<init>()
            android.util.Log.d(r0, r3, r4)
        L_0x001e:
            return r1
        L_0x001f:
            r1 = move-exception
            goto L_0x0025
        L_0x0021:
            r2 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
        L_0x0025:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getCurrentMediaItem():android.support.v4.media.MediaItem2");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.updatePlaylistMetadata(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updatePlaylistMetadata(android.support.v4.media.MediaMetadata2 r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.updatePlaylistMetadata(r5)
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.updatePlaylistMetadata(android.support.v4.media.MediaMetadata2):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return r1.getRepeatMode();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getRepeatMode() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x000d
            int r0 = r1.getRepeatMode()
            return r0
        L_0x000d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001d:
            r0 = 0
            return r0
        L_0x001f:
            r2 = move-exception
            goto L_0x0023
        L_0x0021:
            r2 = move-exception
            r1 = 0
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getRepeatMode():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.setRepeatMode(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setRepeatMode(int r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.setRepeatMode(r5)
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.setRepeatMode(int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0011, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000c, code lost:
        return r1.getShuffleMode();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getShuffleMode() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x0021 }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x000d
            int r0 = r1.getShuffleMode()
            return r0
        L_0x000d:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001d:
            r0 = 0
            return r0
        L_0x001f:
            r2 = move-exception
            goto L_0x0023
        L_0x0021:
            r2 = move-exception
            r1 = 0
        L_0x0023:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.getShuffleMode():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0010, code lost:
        android.util.Log.d(TAG, "API calls after the close()", new java.lang.IllegalStateException());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0006, code lost:
        if (r1 == null) goto L_0x000c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0008, code lost:
        r1.setShuffleMode(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x000e, code lost:
        if (DEBUG == false) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setShuffleMode(int r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaPlaylistAgent r1 = r4.mPlaylistAgent     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            if (r1 == 0) goto L_0x000c
            r1.setShuffleMode(r5)
            goto L_0x001c
        L_0x000c:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MS2ImplBase"
            java.lang.String r2 = "API calls after the close()"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            r3.<init>()
            android.util.Log.d(r0, r2, r3)
        L_0x001c:
            return
        L_0x001d:
            r2 = move-exception
            goto L_0x0021
        L_0x001f:
            r2 = move-exception
            r1 = 0
        L_0x0021:
            monitor-exit(r0)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaSession2ImplBase.setShuffleMode(int):void");
    }

    public MediaSession2 getInstance() {
        return this.mInstance;
    }

    public IBinder getSessionBinder() {
        return this.mSession2Stub.asBinder();
    }

    public Context getContext() {
        return this.mContext;
    }

    public Executor getCallbackExecutor() {
        return this.mCallbackExecutor;
    }

    public MediaSession2.SessionCallback getCallback() {
        return this.mCallback;
    }

    public MediaSessionCompat getSessionCompat() {
        return this.mSessionCompat;
    }

    public AudioFocusHandler getAudioFocusHandler() {
        return this.mAudioFocusHandler;
    }

    public boolean isClosed() {
        return !this.mHandlerThread.isAlive();
    }

    public PlaybackStateCompat getPlaybackStateCompat() {
        PlaybackStateCompat build;
        synchronized (this.mLock) {
            build = new PlaybackStateCompat.Builder().setState(MediaUtils2.convertToPlaybackStateCompatState(getPlayerState(), getBufferingState()), getCurrentPosition(), getPlaybackSpeed()).setActions(3670015).setBufferedPosition(getBufferedPosition()).build();
        }
        return build;
    }

    public MediaController2.PlaybackInfo getPlaybackInfo() {
        MediaController2.PlaybackInfo playbackInfo;
        synchronized (this.mLock) {
            playbackInfo = this.mPlaybackInfo;
        }
        return playbackInfo;
    }

    public PendingIntent getSessionActivity() {
        return this.mSessionActivity;
    }

    private static String getServiceName(Context context, String serviceAction, String id) {
        PackageManager manager = context.getPackageManager();
        Intent serviceIntent = new Intent(serviceAction);
        serviceIntent.setPackage(context.getPackageName());
        List<ResolveInfo> services = manager.queryIntentServices(serviceIntent, 128);
        String serviceName = null;
        if (services != null) {
            for (int i = 0; i < services.size(); i++) {
                String serviceId = SessionToken2.getSessionId(services.get(i));
                if (!(serviceId == null || !TextUtils.equals(id, serviceId) || services.get(i).serviceInfo == null)) {
                    if (serviceName == null) {
                        serviceName = services.get(i).serviceInfo.name;
                    } else {
                        throw new IllegalArgumentException("Ambiguous session type. Multiple session services define the same id=" + id);
                    }
                }
            }
        }
        return serviceName;
    }

    private void notifyAgentUpdatedNotLocked(MediaPlaylistAgent oldAgent) {
        List<MediaItem2> oldPlaylist = oldAgent.getPlaylist();
        final List<MediaItem2> newPlaylist = getPlaylist();
        if (!ObjectsCompat.equals(oldPlaylist, newPlaylist)) {
            notifyToAllControllers(new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onPlaylistChanged(newPlaylist, MediaSession2ImplBase.this.getPlaylistMetadata());
                }
            });
        } else {
            MediaMetadata2 oldMetadata = oldAgent.getPlaylistMetadata();
            final MediaMetadata2 newMetadata = getPlaylistMetadata();
            if (!ObjectsCompat.equals(oldMetadata, newMetadata)) {
                notifyToAllControllers(new NotifyRunnable() {
                    public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                        callback.onPlaylistMetadataChanged(newMetadata);
                    }
                });
            }
        }
        MediaItem2 oldCurrentItem = oldAgent.getCurrentMediaItem();
        final MediaItem2 newCurrentItem = getCurrentMediaItem();
        if (!ObjectsCompat.equals(oldCurrentItem, newCurrentItem)) {
            notifyToAllControllers(new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onCurrentMediaItemChanged(newCurrentItem);
                }
            });
        }
        final int repeatMode = getRepeatMode();
        if (oldAgent.getRepeatMode() != repeatMode) {
            notifyToAllControllers(new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onRepeatModeChanged(repeatMode);
                }
            });
        }
        final int shuffleMode = getShuffleMode();
        if (oldAgent.getShuffleMode() != shuffleMode) {
            notifyToAllControllers(new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onShuffleModeChanged(shuffleMode);
                }
            });
        }
    }

    private void notifyPlayerUpdatedNotLocked(BaseMediaPlayer oldPlayer) {
        long currentTimeMs = SystemClock.elapsedRealtime();
        long positionMs = getCurrentPosition();
        final long j = currentTimeMs;
        final long j2 = positionMs;
        final int playerState = getPlayerState();
        notifyToAllControllers(new NotifyRunnable() {
            public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                callback.onPlayerStateChanged(j, j2, playerState);
            }
        });
        MediaItem2 item = getCurrentMediaItem();
        if (item != null) {
            int bufferingState = getBufferingState();
            final MediaItem2 mediaItem2 = item;
            final int i = bufferingState;
            int i2 = bufferingState;
            AnonymousClass15 r6 = r0;
            final long bufferedPosition = getBufferedPosition();
            AnonymousClass15 r0 = new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onBufferingStateChanged(mediaItem2, i, bufferedPosition);
                }
            };
            notifyToAllControllers(r6);
        }
        float speed = getPlaybackSpeed();
        if (speed != oldPlayer.getPlaybackSpeed()) {
            final long j3 = currentTimeMs;
            final long j4 = positionMs;
            final float f = speed;
            notifyToAllControllers(new NotifyRunnable() {
                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                    callback.onPlaybackSpeedChanged(j3, j4, f);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void notifyPlaylistChangedOnExecutor(MediaPlaylistAgent playlistAgent, final List<MediaItem2> list, final MediaMetadata2 metadata) {
        synchronized (this.mLock) {
            if (playlistAgent == this.mPlaylistAgent) {
                this.mCallback.onPlaylistChanged(this.mInstance, playlistAgent, list, metadata);
                notifyToAllControllers(new NotifyRunnable() {
                    public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                        callback.onPlaylistChanged(list, metadata);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyPlaylistMetadataChangedOnExecutor(MediaPlaylistAgent playlistAgent, final MediaMetadata2 metadata) {
        synchronized (this.mLock) {
            if (playlistAgent == this.mPlaylistAgent) {
                this.mCallback.onPlaylistMetadataChanged(this.mInstance, playlistAgent, metadata);
                notifyToAllControllers(new NotifyRunnable() {
                    public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                        callback.onPlaylistMetadataChanged(metadata);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyRepeatModeChangedOnExecutor(MediaPlaylistAgent playlistAgent, final int repeatMode) {
        synchronized (this.mLock) {
            if (playlistAgent == this.mPlaylistAgent) {
                this.mCallback.onRepeatModeChanged(this.mInstance, playlistAgent, repeatMode);
                notifyToAllControllers(new NotifyRunnable() {
                    public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                        callback.onRepeatModeChanged(repeatMode);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void notifyShuffleModeChangedOnExecutor(MediaPlaylistAgent playlistAgent, final int shuffleMode) {
        synchronized (this.mLock) {
            if (playlistAgent == this.mPlaylistAgent) {
                this.mCallback.onShuffleModeChanged(this.mInstance, playlistAgent, shuffleMode);
                notifyToAllControllers(new NotifyRunnable() {
                    public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                        callback.onShuffleModeChanged(shuffleMode);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyToController(final MediaSession2.ControllerInfo controller, NotifyRunnable runnable) {
        if (controller != null) {
            try {
                runnable.run(controller.getControllerCb());
            } catch (DeadObjectException e) {
                if (DEBUG) {
                    Log.d(TAG, controller.toString() + " is gone", e);
                }
                this.mSession2Stub.removeControllerInfo(controller);
                this.mCallbackExecutor.execute(new Runnable() {
                    public void run() {
                        MediaSession2ImplBase.this.mCallback.onDisconnected(MediaSession2ImplBase.this.getInstance(), controller);
                    }
                });
            } catch (RemoteException e2) {
                Log.w(TAG, "Exception in " + controller.toString(), e2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyToAllControllers(NotifyRunnable runnable) {
        List<MediaSession2.ControllerInfo> controllers = getConnectedControllers();
        for (int i = 0; i < controllers.size(); i++) {
            notifyToController(controllers.get(i), runnable);
        }
    }

    private static class MyPlayerEventCallback extends BaseMediaPlayer.PlayerEventCallback {
        private final WeakReference<MediaSession2ImplBase> mSession;

        private MyPlayerEventCallback(MediaSession2ImplBase session) {
            this.mSession = new WeakReference<>(session);
        }

        public void onCurrentDataSourceChanged(final BaseMediaPlayer player, final DataSourceDesc dsd) {
            final MediaSession2ImplBase session = getSession();
            if (session != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    public void run() {
                        final MediaItem2 item;
                        DataSourceDesc dataSourceDesc = dsd;
                        if (dataSourceDesc == null) {
                            item = null;
                        } else {
                            item = MyPlayerEventCallback.this.getMediaItem(session, dataSourceDesc);
                            if (item == null) {
                                Log.w(MediaSession2ImplBase.TAG, "Cannot obtain media item from the dsd=" + dsd);
                                return;
                            }
                        }
                        session.getCallback().onCurrentMediaItemChanged(session.getInstance(), player, item);
                        session.notifyToAllControllers(new NotifyRunnable() {
                            public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                                callback.onCurrentMediaItemChanged(item);
                            }
                        });
                    }
                });
            }
        }

        public void onMediaPrepared(final BaseMediaPlayer mpb, final DataSourceDesc dsd) {
            final MediaSession2ImplBase session = getSession();
            if (session != null && dsd != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    public void run() {
                        MediaMetadata2 metadata;
                        MediaItem2 item = MyPlayerEventCallback.this.getMediaItem(session, dsd);
                        if (item != null) {
                            if (item.equals(session.getCurrentMediaItem())) {
                                long duration = session.getDuration();
                                if (duration >= 0) {
                                    MediaMetadata2 metadata2 = item.getMetadata();
                                    if (metadata2 == null) {
                                        metadata = new MediaMetadata2.Builder().putLong("android.media.metadata.DURATION", duration).putString("android.media.metadata.MEDIA_ID", item.getMediaId()).build();
                                    } else if (!metadata2.containsKey("android.media.metadata.DURATION")) {
                                        metadata = new MediaMetadata2.Builder(metadata2).putLong("android.media.metadata.DURATION", duration).build();
                                    } else {
                                        long durationFromMetadata = metadata2.getLong("android.media.metadata.DURATION");
                                        if (duration != durationFromMetadata) {
                                            Log.w(MediaSession2ImplBase.TAG, "duration mismatch for an item. duration from player=" + duration + " duration from metadata=" + durationFromMetadata + ". May be a timing issue?");
                                        }
                                        metadata = null;
                                    }
                                    if (metadata != null) {
                                        item.setMetadata(metadata);
                                        session.notifyToAllControllers(new NotifyRunnable() {
                                            public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                                                callback.onPlaylistChanged(session.getPlaylist(), session.getPlaylistMetadata());
                                            }
                                        });
                                    }
                                } else {
                                    return;
                                }
                            }
                            session.getCallback().onMediaPrepared(session.getInstance(), mpb, item);
                        }
                    }
                });
            }
        }

        public void onPlayerStateChanged(final BaseMediaPlayer player, final int state) {
            final MediaSession2ImplBase session = getSession();
            if (session != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    public void run() {
                        session.mAudioFocusHandler.onPlayerStateChanged(state);
                        session.getCallback().onPlayerStateChanged(session.getInstance(), player, state);
                        session.notifyToAllControllers(new NotifyRunnable() {
                            public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                                callback.onPlayerStateChanged(SystemClock.elapsedRealtime(), player.getCurrentPosition(), state);
                            }
                        });
                    }
                });
            }
        }

        public void onBufferingStateChanged(BaseMediaPlayer mpb, DataSourceDesc dsd, int state) {
            MediaSession2ImplBase session = getSession();
            if (session != null && dsd != null) {
                final MediaSession2ImplBase mediaSession2ImplBase = session;
                final DataSourceDesc dataSourceDesc = dsd;
                final BaseMediaPlayer baseMediaPlayer = mpb;
                final int i = state;
                session.getCallbackExecutor().execute(new Runnable() {
                    public void run() {
                        final MediaItem2 item = MyPlayerEventCallback.this.getMediaItem(mediaSession2ImplBase, dataSourceDesc);
                        if (item != null) {
                            mediaSession2ImplBase.getCallback().onBufferingStateChanged(mediaSession2ImplBase.getInstance(), baseMediaPlayer, item, i);
                            mediaSession2ImplBase.notifyToAllControllers(new NotifyRunnable() {
                                public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                                    callback.onBufferingStateChanged(item, i, baseMediaPlayer.getBufferedPosition());
                                }
                            });
                        }
                    }
                });
            }
        }

        public void onPlaybackSpeedChanged(final BaseMediaPlayer mpb, final float speed) {
            final MediaSession2ImplBase session = getSession();
            if (session != null) {
                session.getCallbackExecutor().execute(new Runnable() {
                    public void run() {
                        session.getCallback().onPlaybackSpeedChanged(session.getInstance(), mpb, speed);
                        session.notifyToAllControllers(new NotifyRunnable() {
                            public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                                callback.onPlaybackSpeedChanged(SystemClock.elapsedRealtime(), session.getCurrentPosition(), speed);
                            }
                        });
                    }
                });
            }
        }

        public void onSeekCompleted(BaseMediaPlayer mpb, long position) {
            MediaSession2ImplBase session = getSession();
            if (session != null) {
                final MediaSession2ImplBase mediaSession2ImplBase = session;
                final BaseMediaPlayer baseMediaPlayer = mpb;
                final long j = position;
                session.getCallbackExecutor().execute(new Runnable() {
                    public void run() {
                        mediaSession2ImplBase.getCallback().onSeekCompleted(mediaSession2ImplBase.getInstance(), baseMediaPlayer, j);
                        mediaSession2ImplBase.notifyToAllControllers(new NotifyRunnable() {
                            public void run(MediaSession2.ControllerCb callback) throws RemoteException {
                                callback.onSeekCompleted(SystemClock.elapsedRealtime(), mediaSession2ImplBase.getCurrentPosition(), j);
                            }
                        });
                    }
                });
            }
        }

        private MediaSession2ImplBase getSession() {
            MediaSession2ImplBase session = (MediaSession2ImplBase) this.mSession.get();
            if (session == null && MediaSession2ImplBase.DEBUG) {
                Log.d(MediaSession2ImplBase.TAG, "Session is closed", new IllegalStateException());
            }
            return session;
        }

        /* access modifiers changed from: private */
        public MediaItem2 getMediaItem(MediaSession2ImplBase session, DataSourceDesc dsd) {
            MediaPlaylistAgent agent = session.getPlaylistAgent();
            if (agent != null) {
                MediaItem2 item = agent.getMediaItem(dsd);
                if (item == null && MediaSession2ImplBase.DEBUG) {
                    Log.d(MediaSession2ImplBase.TAG, "Could not find matching item for dsd=" + dsd, new NoSuchElementException());
                }
                return item;
            } else if (!MediaSession2ImplBase.DEBUG) {
                return null;
            } else {
                Log.d(MediaSession2ImplBase.TAG, "Session is closed", new IllegalStateException());
                return null;
            }
        }
    }

    private static class MyPlaylistEventCallback extends MediaPlaylistAgent.PlaylistEventCallback {
        private final WeakReference<MediaSession2ImplBase> mSession;

        private MyPlaylistEventCallback(MediaSession2ImplBase session) {
            this.mSession = new WeakReference<>(session);
        }

        public void onPlaylistChanged(MediaPlaylistAgent playlistAgent, List<MediaItem2> list, MediaMetadata2 metadata) {
            MediaSession2ImplBase session = (MediaSession2ImplBase) this.mSession.get();
            if (session != null) {
                session.notifyPlaylistChangedOnExecutor(playlistAgent, list, metadata);
            }
        }

        public void onPlaylistMetadataChanged(MediaPlaylistAgent playlistAgent, MediaMetadata2 metadata) {
            MediaSession2ImplBase session = (MediaSession2ImplBase) this.mSession.get();
            if (session != null) {
                session.notifyPlaylistMetadataChangedOnExecutor(playlistAgent, metadata);
            }
        }

        public void onRepeatModeChanged(MediaPlaylistAgent playlistAgent, int repeatMode) {
            MediaSession2ImplBase session = (MediaSession2ImplBase) this.mSession.get();
            if (session != null) {
                session.notifyRepeatModeChangedOnExecutor(playlistAgent, repeatMode);
            }
        }

        public void onShuffleModeChanged(MediaPlaylistAgent playlistAgent, int shuffleMode) {
            MediaSession2ImplBase session = (MediaSession2ImplBase) this.mSession.get();
            if (session != null) {
                session.notifyShuffleModeChangedOnExecutor(playlistAgent, shuffleMode);
            }
        }
    }
}
