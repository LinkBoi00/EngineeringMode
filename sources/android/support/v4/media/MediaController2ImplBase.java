package android.support.v4.media;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.media.IMediaSession2;
import android.support.v4.media.MediaController2;
import android.support.v4.media.MediaSession2;
import android.util.Log;
import java.util.List;
import java.util.concurrent.Executor;

class MediaController2ImplBase implements MediaController2.SupportLibraryImpl {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MC2ImplBase";
    private SessionCommandGroup2 mAllowedCommands;
    private long mBufferedPositionMs;
    private int mBufferingState;
    /* access modifiers changed from: private */
    public final MediaController2.ControllerCallback mCallback;
    private final Executor mCallbackExecutor;
    private final Context mContext;
    final MediaController2Stub mControllerStub;
    private MediaItem2 mCurrentMediaItem;
    private final IBinder.DeathRecipient mDeathRecipient;
    private volatile IMediaSession2 mISession2;
    /* access modifiers changed from: private */
    public final MediaController2 mInstance;
    private boolean mIsReleased;
    private final Object mLock = new Object();
    private MediaController2.PlaybackInfo mPlaybackInfo;
    private float mPlaybackSpeed;
    private int mPlayerState;
    private List<MediaItem2> mPlaylist;
    private MediaMetadata2 mPlaylistMetadata;
    private long mPositionEventTimeMs;
    private long mPositionMs;
    private int mRepeatMode;
    private SessionServiceConnection mServiceConnection;
    private PendingIntent mSessionActivity;
    private int mShuffleMode;
    /* access modifiers changed from: private */
    public final SessionToken2 mToken;

    MediaController2ImplBase(Context context, MediaController2 instance, SessionToken2 token, Executor executor, MediaController2.ControllerCallback callback) {
        this.mInstance = instance;
        if (context == null) {
            throw new IllegalArgumentException("context shouldn't be null");
        } else if (token == null) {
            throw new IllegalArgumentException("token shouldn't be null");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback shouldn't be null");
        } else if (executor != null) {
            this.mContext = context;
            this.mControllerStub = new MediaController2Stub(this);
            this.mToken = token;
            this.mCallback = callback;
            this.mCallbackExecutor = executor;
            this.mDeathRecipient = new IBinder.DeathRecipient() {
                public void binderDied() {
                    MediaController2ImplBase.this.mInstance.close();
                }
            };
            IMediaSession2 iSession2 = IMediaSession2.Stub.asInterface((IBinder) token.getBinder());
            if (token.getType() == 0) {
                this.mServiceConnection = null;
                connectToSession(iSession2);
                return;
            }
            this.mServiceConnection = new SessionServiceConnection();
            connectToService();
        } else {
            throw new IllegalArgumentException("executor shouldn't be null");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003e, code lost:
        if (r2 == null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.asBinder().unlinkToDeath(r6.mDeathRecipient, 0);
        r2.release(r6.mControllerStub);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r6 = this;
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = "MC2ImplBase"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "release from "
            r1.append(r2)
            android.support.v4.media.SessionToken2 r2 = r6.mToken
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
        L_0x001c:
            java.lang.Object r0 = r6.mLock
            monitor-enter(r0)
            r1 = 0
            android.support.v4.media.IMediaSession2 r2 = r6.mISession2     // Catch:{ all -> 0x005e }
            boolean r3 = r6.mIsReleased     // Catch:{ all -> 0x005c }
            if (r3 == 0) goto L_0x0028
            monitor-exit(r0)     // Catch:{ all -> 0x005c }
            return
        L_0x0028:
            r3 = 1
            r6.mIsReleased = r3     // Catch:{ all -> 0x005c }
            android.support.v4.media.MediaController2ImplBase$SessionServiceConnection r3 = r6.mServiceConnection     // Catch:{ all -> 0x005c }
            if (r3 == 0) goto L_0x0036
            android.content.Context r4 = r6.mContext     // Catch:{ all -> 0x005c }
            r4.unbindService(r3)     // Catch:{ all -> 0x005c }
            r6.mServiceConnection = r1     // Catch:{ all -> 0x005c }
        L_0x0036:
            r6.mISession2 = r1     // Catch:{ all -> 0x005c }
            android.support.v4.media.MediaController2Stub r1 = r6.mControllerStub     // Catch:{ all -> 0x005c }
            r1.destroy()     // Catch:{ all -> 0x005c }
            monitor-exit(r0)     // Catch:{ all -> 0x005c }
            if (r2 == 0) goto L_0x0051
            android.os.IBinder r0 = r2.asBinder()     // Catch:{ RemoteException -> 0x0050 }
            android.os.IBinder$DeathRecipient r1 = r6.mDeathRecipient     // Catch:{ RemoteException -> 0x0050 }
            r3 = 0
            r0.unlinkToDeath(r1, r3)     // Catch:{ RemoteException -> 0x0050 }
            android.support.v4.media.MediaController2Stub r0 = r6.mControllerStub     // Catch:{ RemoteException -> 0x0050 }
            r2.release(r0)     // Catch:{ RemoteException -> 0x0050 }
            goto L_0x0051
        L_0x0050:
            r0 = move-exception
        L_0x0051:
            java.util.concurrent.Executor r0 = r6.mCallbackExecutor
            android.support.v4.media.MediaController2ImplBase$2 r1 = new android.support.v4.media.MediaController2ImplBase$2
            r1.<init>()
            r0.execute(r1)
            return
        L_0x005c:
            r1 = move-exception
            goto L_0x0062
        L_0x005e:
            r2 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
        L_0x0062:
            monitor-exit(r0)     // Catch:{ all -> 0x005c }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaController2ImplBase.close():void");
    }

    public SessionToken2 getSessionToken() {
        return this.mToken;
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mISession2 != null;
        }
        return z;
    }

    public void play() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(1);
        if (iSession2 != null) {
            try {
                iSession2.play(this.mControllerStub);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void pause() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(2);
        if (iSession2 != null) {
            try {
                iSession2.pause(this.mControllerStub);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void reset() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(3);
        if (iSession2 != null) {
            try {
                iSession2.reset(this.mControllerStub);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void prepare() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(6);
        if (iSession2 != null) {
            try {
                iSession2.prepare(this.mControllerStub);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void fastForward() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(7);
        if (iSession2 != null) {
            try {
                iSession2.fastForward(this.mControllerStub);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void rewind() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(8);
        if (iSession2 != null) {
            try {
                iSession2.rewind(this.mControllerStub);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void seekTo(long pos) {
        if (pos >= 0) {
            IMediaSession2 iSession2 = getSessionInterfaceIfAble(9);
            if (iSession2 != null) {
                try {
                    iSession2.seekTo(this.mControllerStub, pos);
                } catch (RemoteException e) {
                    Log.w(TAG, "Cannot connect to the service or the session is gone", e);
                }
            }
        } else {
            throw new IllegalArgumentException("position shouldn't be negative");
        }
    }

    public void skipForward() {
    }

    public void skipBackward() {
    }

    public void playFromMediaId(String mediaId, Bundle extras) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(22);
        if (iSession2 != null) {
            try {
                iSession2.playFromMediaId(this.mControllerStub, mediaId, extras);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void playFromSearch(String query, Bundle extras) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(24);
        if (iSession2 != null) {
            try {
                iSession2.playFromSearch(this.mControllerStub, query, extras);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void playFromUri(Uri uri, Bundle extras) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(23);
        if (iSession2 != null) {
            try {
                iSession2.playFromUri(this.mControllerStub, uri, extras);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void prepareFromMediaId(String mediaId, Bundle extras) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(25);
        if (iSession2 != null) {
            try {
                iSession2.prepareFromMediaId(this.mControllerStub, mediaId, extras);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void prepareFromSearch(String query, Bundle extras) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(27);
        if (iSession2 != null) {
            try {
                iSession2.prepareFromSearch(this.mControllerStub, query, extras);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void prepareFromUri(Uri uri, Bundle extras) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(26);
        if (iSession2 != null) {
            try {
                iSession2.prepareFromUri(this.mControllerStub, uri, extras);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void setVolumeTo(int value, int flags) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(10);
        if (iSession2 != null) {
            try {
                iSession2.setVolumeTo(this.mControllerStub, value, flags);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void adjustVolume(int direction, int flags) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(11);
        if (iSession2 != null) {
            try {
                iSession2.adjustVolume(this.mControllerStub, direction, flags);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public PendingIntent getSessionActivity() {
        PendingIntent pendingIntent;
        synchronized (this.mLock) {
            pendingIntent = this.mSessionActivity;
        }
        return pendingIntent;
    }

    public int getPlayerState() {
        int i;
        synchronized (this.mLock) {
            i = this.mPlayerState;
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getDuration() {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.MediaItem2 r1 = r4.mCurrentMediaItem     // Catch:{ all -> 0x001f }
            android.support.v4.media.MediaMetadata2 r1 = r1.getMetadata()     // Catch:{ all -> 0x001f }
            if (r1 == 0) goto L_0x001b
            java.lang.String r2 = "android.media.metadata.DURATION"
            boolean r2 = r1.containsKey(r2)     // Catch:{ all -> 0x001f }
            if (r2 == 0) goto L_0x001b
            java.lang.String r2 = "android.media.metadata.DURATION"
            long r2 = r1.getLong(r2)     // Catch:{ all -> 0x001f }
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            return r2
        L_0x001b:
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            r0 = -1
            return r0
        L_0x001f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaController2ImplBase.getDuration():long");
    }

    public long getCurrentPosition() {
        long timeDiff;
        synchronized (this.mLock) {
            if (this.mISession2 == null) {
                Log.w(TAG, "Session isn't active", new IllegalStateException());
                return -1;
            }
            if (this.mInstance.mTimeDiff != null) {
                timeDiff = this.mInstance.mTimeDiff.longValue();
            } else {
                timeDiff = SystemClock.elapsedRealtime() - this.mPositionEventTimeMs;
            }
            long max = Math.max(0, this.mPositionMs + ((long) (this.mPlaybackSpeed * ((float) timeDiff))));
            return max;
        }
    }

    public float getPlaybackSpeed() {
        synchronized (this.mLock) {
            if (this.mISession2 == null) {
                Log.w(TAG, "Session isn't active", new IllegalStateException());
                return 0.0f;
            }
            float f = this.mPlaybackSpeed;
            return f;
        }
    }

    public void setPlaybackSpeed(float speed) {
        synchronized (this.mLock) {
            IMediaSession2 iSession2 = getSessionInterfaceIfAble(39);
            if (iSession2 != null) {
                try {
                    iSession2.setPlaybackSpeed(this.mControllerStub, speed);
                } catch (RemoteException e) {
                    Log.w(TAG, "Cannot connect to the service or the session is gone", e);
                }
            }
        }
    }

    public int getBufferingState() {
        synchronized (this.mLock) {
            if (this.mISession2 == null) {
                Log.w(TAG, "Session isn't active", new IllegalStateException());
                return 0;
            }
            int i = this.mBufferingState;
            return i;
        }
    }

    public long getBufferedPosition() {
        synchronized (this.mLock) {
            if (this.mISession2 == null) {
                Log.w(TAG, "Session isn't active", new IllegalStateException());
                return -1;
            }
            long j = this.mBufferedPositionMs;
            return j;
        }
    }

    public MediaController2.PlaybackInfo getPlaybackInfo() {
        MediaController2.PlaybackInfo playbackInfo;
        synchronized (this.mLock) {
            playbackInfo = this.mPlaybackInfo;
        }
        return playbackInfo;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        android.util.Log.w(TAG, "Cannot connect to the service or the session is gone", r0);
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
        if (r1 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        r1.setRating(r4.mControllerStub, r5, r6.toBundle());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        r0 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setRating(java.lang.String r5, android.support.mediacompat.Rating2 r6) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            android.support.v4.media.IMediaSession2 r1 = r4.mISession2     // Catch:{ all -> 0x001d }
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            if (r1 == 0) goto L_0x001a
            android.support.v4.media.MediaController2Stub r0 = r4.mControllerStub     // Catch:{ RemoteException -> 0x0012 }
            android.os.Bundle r2 = r6.toBundle()     // Catch:{ RemoteException -> 0x0012 }
            r1.setRating(r0, r5, r2)     // Catch:{ RemoteException -> 0x0012 }
            goto L_0x001a
        L_0x0012:
            r0 = move-exception
            java.lang.String r2 = "MC2ImplBase"
            java.lang.String r3 = "Cannot connect to the service or the session is gone"
            android.util.Log.w(r2, r3, r0)
        L_0x001a:
            return
        L_0x001b:
            r2 = move-exception
            goto L_0x001f
        L_0x001d:
            r2 = move-exception
            r1 = 0
        L_0x001f:
            monitor-exit(r0)     // Catch:{ all -> 0x001b }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaController2ImplBase.setRating(java.lang.String, android.support.mediacompat.Rating2):void");
    }

    public void sendCustomCommand(SessionCommand2 command, Bundle args, ResultReceiver cb) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(command);
        if (iSession2 != null) {
            try {
                iSession2.sendCustomCommand(this.mControllerStub, command.toBundle(), args, cb);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public List<MediaItem2> getPlaylist() {
        List<MediaItem2> list;
        synchronized (this.mLock) {
            list = this.mPlaylist;
        }
        return list;
    }

    public void setPlaylist(List<MediaItem2> list, MediaMetadata2 metadata) {
        Bundle bundle;
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(19);
        if (iSession2 != null) {
            try {
                MediaController2Stub mediaController2Stub = this.mControllerStub;
                List<Bundle> convertMediaItem2ListToBundleList = MediaUtils2.convertMediaItem2ListToBundleList(list);
                if (metadata == null) {
                    bundle = null;
                } else {
                    bundle = metadata.toBundle();
                }
                iSession2.setPlaylist(mediaController2Stub, convertMediaItem2ListToBundleList, bundle);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void updatePlaylistMetadata(MediaMetadata2 metadata) {
        Bundle bundle;
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(21);
        if (iSession2 != null) {
            try {
                MediaController2Stub mediaController2Stub = this.mControllerStub;
                if (metadata == null) {
                    bundle = null;
                } else {
                    bundle = metadata.toBundle();
                }
                iSession2.updatePlaylistMetadata(mediaController2Stub, bundle);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public MediaMetadata2 getPlaylistMetadata() {
        MediaMetadata2 mediaMetadata2;
        synchronized (this.mLock) {
            mediaMetadata2 = this.mPlaylistMetadata;
        }
        return mediaMetadata2;
    }

    public void addPlaylistItem(int index, MediaItem2 item) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(15);
        if (iSession2 != null) {
            try {
                iSession2.addPlaylistItem(this.mControllerStub, index, item.toBundle());
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void removePlaylistItem(MediaItem2 item) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(16);
        if (iSession2 != null) {
            try {
                iSession2.removePlaylistItem(this.mControllerStub, item.toBundle());
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void replacePlaylistItem(int index, MediaItem2 item) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(17);
        if (iSession2 != null) {
            try {
                iSession2.replacePlaylistItem(this.mControllerStub, index, item.toBundle());
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public MediaItem2 getCurrentMediaItem() {
        MediaItem2 mediaItem2;
        synchronized (this.mLock) {
            mediaItem2 = this.mCurrentMediaItem;
        }
        return mediaItem2;
    }

    public void skipToPreviousItem() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(5);
        synchronized (this.mLock) {
            if (iSession2 != null) {
                try {
                    iSession2.skipToPreviousItem(this.mControllerStub);
                } catch (RemoteException e) {
                    Log.w(TAG, "Cannot connect to the service or the session is gone", e);
                }
            }
        }
    }

    public void skipToNextItem() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(4);
        synchronized (this.mLock) {
            if (iSession2 != null) {
                try {
                    this.mISession2.skipToNextItem(this.mControllerStub);
                } catch (RemoteException e) {
                    Log.w(TAG, "Cannot connect to the service or the session is gone", e);
                }
            }
        }
    }

    public void skipToPlaylistItem(MediaItem2 item) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(12);
        synchronized (this.mLock) {
            if (iSession2 != null) {
                try {
                    this.mISession2.skipToPlaylistItem(this.mControllerStub, item.toBundle());
                } catch (RemoteException e) {
                    Log.w(TAG, "Cannot connect to the service or the session is gone", e);
                }
            }
        }
    }

    public int getRepeatMode() {
        int i;
        synchronized (this.mLock) {
            i = this.mRepeatMode;
        }
        return i;
    }

    public void setRepeatMode(int repeatMode) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(14);
        if (iSession2 != null) {
            try {
                iSession2.setRepeatMode(this.mControllerStub, repeatMode);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public int getShuffleMode() {
        int i;
        synchronized (this.mLock) {
            i = this.mShuffleMode;
        }
        return i;
    }

    public void setShuffleMode(int shuffleMode) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(13);
        if (iSession2 != null) {
            try {
                iSession2.setShuffleMode(this.mControllerStub, shuffleMode);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void subscribeRoutesInfo() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(36);
        if (iSession2 != null) {
            try {
                iSession2.subscribeRoutesInfo(this.mControllerStub);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void unsubscribeRoutesInfo() {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(37);
        if (iSession2 != null) {
            try {
                iSession2.unsubscribeRoutesInfo(this.mControllerStub);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public void selectRoute(Bundle route) {
        IMediaSession2 iSession2 = getSessionInterfaceIfAble(38);
        if (iSession2 != null) {
            try {
                iSession2.selectRoute(this.mControllerStub, route);
            } catch (RemoteException e) {
                Log.w(TAG, "Cannot connect to the service or the session is gone", e);
            }
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public MediaController2.ControllerCallback getCallback() {
        return this.mCallback;
    }

    public Executor getCallbackExecutor() {
        return this.mCallbackExecutor;
    }

    public MediaBrowserCompat getBrowserCompat() {
        return null;
    }

    public MediaController2 getInstance() {
        return this.mInstance;
    }

    private void connectToService() {
        Intent intent = new Intent(MediaSessionService2.SERVICE_INTERFACE);
        intent.setClassName(this.mToken.getPackageName(), this.mToken.getServiceName());
        synchronized (this.mLock) {
            if (!this.mContext.bindService(intent, this.mServiceConnection, 1)) {
                Log.w(TAG, "bind to " + this.mToken + " failed");
            } else if (DEBUG) {
                Log.d(TAG, "bind to " + this.mToken + " success");
            }
        }
    }

    /* access modifiers changed from: private */
    public void connectToSession(IMediaSession2 sessionBinder) {
        try {
            sessionBinder.connect(this.mControllerStub, this.mContext.getPackageName());
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call connection request. Framework will retry automatically");
        }
    }

    /* access modifiers changed from: package-private */
    public IMediaSession2 getSessionInterfaceIfAble(int commandCode) {
        synchronized (this.mLock) {
            if (!this.mAllowedCommands.hasCommand(commandCode)) {
                Log.w(TAG, "Controller isn't allowed to call command, commandCode=" + commandCode);
                return null;
            }
            IMediaSession2 iMediaSession2 = this.mISession2;
            return iMediaSession2;
        }
    }

    /* access modifiers changed from: package-private */
    public IMediaSession2 getSessionInterfaceIfAble(SessionCommand2 command) {
        synchronized (this.mLock) {
            if (!this.mAllowedCommands.hasCommand(command)) {
                Log.w(TAG, "Controller isn't allowed to call command, command=" + command);
                return null;
            }
            IMediaSession2 iMediaSession2 = this.mISession2;
            return iMediaSession2;
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyCurrentMediaItemChanged(final MediaItem2 item) {
        synchronized (this.mLock) {
            this.mCurrentMediaItem = item;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onCurrentMediaItemChanged(MediaController2ImplBase.this.mInstance, item);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyPlayerStateChanges(long eventTimeMs, long positionMs, final int state) {
        synchronized (this.mLock) {
            this.mPositionEventTimeMs = eventTimeMs;
            this.mPositionMs = positionMs;
            this.mPlayerState = state;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onPlayerStateChanged(MediaController2ImplBase.this.mInstance, state);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyPlaybackSpeedChanges(long eventTimeMs, long positionMs, final float speed) {
        synchronized (this.mLock) {
            this.mPositionEventTimeMs = eventTimeMs;
            this.mPositionMs = positionMs;
            this.mPlaybackSpeed = speed;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onPlaybackSpeedChanged(MediaController2ImplBase.this.mInstance, speed);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyBufferingStateChanged(final MediaItem2 item, final int state, long bufferedPositionMs) {
        synchronized (this.mLock) {
            this.mBufferingState = state;
            this.mBufferedPositionMs = bufferedPositionMs;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onBufferingStateChanged(MediaController2ImplBase.this.mInstance, item, state);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyPlaylistChanges(final List<MediaItem2> playlist, final MediaMetadata2 metadata) {
        synchronized (this.mLock) {
            this.mPlaylist = playlist;
            this.mPlaylistMetadata = metadata;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onPlaylistChanged(MediaController2ImplBase.this.mInstance, playlist, metadata);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyPlaylistMetadataChanges(final MediaMetadata2 metadata) {
        synchronized (this.mLock) {
            this.mPlaylistMetadata = metadata;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onPlaylistMetadataChanged(MediaController2ImplBase.this.mInstance, metadata);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyPlaybackInfoChanges(final MediaController2.PlaybackInfo info) {
        synchronized (this.mLock) {
            this.mPlaybackInfo = info;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onPlaybackInfoChanged(MediaController2ImplBase.this.mInstance, info);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyRepeatModeChanges(final int repeatMode) {
        synchronized (this.mLock) {
            this.mRepeatMode = repeatMode;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onRepeatModeChanged(MediaController2ImplBase.this.mInstance, repeatMode);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyShuffleModeChanges(final int shuffleMode) {
        synchronized (this.mLock) {
            this.mShuffleMode = shuffleMode;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onShuffleModeChanged(MediaController2ImplBase.this.mInstance, shuffleMode);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifySeekCompleted(long eventTimeMs, long positionMs, final long seekPositionMs) {
        synchronized (this.mLock) {
            this.mPositionEventTimeMs = eventTimeMs;
            this.mPositionMs = positionMs;
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onSeekCompleted(MediaController2ImplBase.this.mInstance, seekPositionMs);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyError(final int errorCode, final Bundle extras) {
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onError(MediaController2ImplBase.this.mInstance, errorCode, extras);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void notifyRoutesInfoChanged(final List<Bundle> routes) {
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                if (MediaController2ImplBase.this.mInstance.isConnected()) {
                    MediaController2ImplBase.this.mCallback.onRoutesInfoChanged(MediaController2ImplBase.this.mInstance, routes);
                }
            }
        });
    }

    /* Debug info: failed to restart local var, previous not found, register: 16 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0043, code lost:
        if (0 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0045, code lost:
        r1.mInstance.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0058, code lost:
        if (1 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        r1.mInstance.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        r1.mCallbackExecutor.execute(new android.support.v4.media.MediaController2ImplBase.AnonymousClass15(r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00a8, code lost:
        if (0 == 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00aa, code lost:
        r1.mInstance.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00df, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onConnectedNotLocked(android.support.v4.media.IMediaSession2 r17, android.support.v4.media.SessionCommandGroup2 r18, int r19, android.support.v4.media.MediaItem2 r20, long r21, long r23, float r25, long r26, android.support.v4.media.MediaController2.PlaybackInfo r28, int r29, int r30, java.util.List<android.support.v4.media.MediaItem2> r31, android.app.PendingIntent r32) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0028
            java.lang.String r0 = "MC2ImplBase"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "onConnectedNotLocked sessionBinder="
            r4.append(r5)
            r4.append(r2)
            java.lang.String r5 = ", allowedCommands="
            r4.append(r5)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r0, r4)
        L_0x0028:
            r4 = 0
            if (r2 == 0) goto L_0x00f8
            if (r3 != 0) goto L_0x003b
            r7 = r20
            r8 = r21
            r10 = r23
            r12 = r25
            r13 = r26
            r15 = r28
            goto L_0x0104
        L_0x003b:
            java.lang.Object r5 = r1.mLock     // Catch:{ all -> 0x00e3 }
            monitor-enter(r5)     // Catch:{ all -> 0x00e3 }
            boolean r0 = r1.mIsReleased     // Catch:{ all -> 0x00d0 }
            if (r0 == 0) goto L_0x004b
            monitor-exit(r5)     // Catch:{ all -> 0x00d0 }
            if (r4 == 0) goto L_0x004a
            android.support.v4.media.MediaController2 r0 = r1.mInstance
            r0.close()
        L_0x004a:
            return
        L_0x004b:
            android.support.v4.media.IMediaSession2 r0 = r1.mISession2     // Catch:{ all -> 0x00d0 }
            if (r0 == 0) goto L_0x0060
            java.lang.String r0 = "MC2ImplBase"
            java.lang.String r6 = "Cannot be notified about the connection result many times. Probably a bug or malicious app."
            android.util.Log.e(r0, r6)     // Catch:{ all -> 0x00d0 }
            r4 = 1
            monitor-exit(r5)     // Catch:{ all -> 0x00d0 }
            if (r4 == 0) goto L_0x005f
            android.support.v4.media.MediaController2 r0 = r1.mInstance
            r0.close()
        L_0x005f:
            return
        L_0x0060:
            r1.mAllowedCommands = r3     // Catch:{ all -> 0x00d0 }
            r6 = r19
            r1.mPlayerState = r6     // Catch:{ all -> 0x00d0 }
            r7 = r20
            r1.mCurrentMediaItem = r7     // Catch:{ all -> 0x00ce }
            r8 = r21
            r1.mPositionEventTimeMs = r8     // Catch:{ all -> 0x00cc }
            r10 = r23
            r1.mPositionMs = r10     // Catch:{ all -> 0x00ca }
            r12 = r25
            r1.mPlaybackSpeed = r12     // Catch:{ all -> 0x00c8 }
            r13 = r26
            r1.mBufferedPositionMs = r13     // Catch:{ all -> 0x00c6 }
            r15 = r28
            r1.mPlaybackInfo = r15     // Catch:{ all -> 0x00e1 }
            r6 = r29
            r1.mRepeatMode = r6     // Catch:{ all -> 0x00e1 }
            r6 = r30
            r1.mShuffleMode = r6     // Catch:{ all -> 0x00e1 }
            r6 = r31
            r1.mPlaylist = r6     // Catch:{ all -> 0x00e1 }
            r6 = r32
            r1.mSessionActivity = r6     // Catch:{ all -> 0x00e1 }
            r1.mISession2 = r2     // Catch:{ all -> 0x00e1 }
            android.support.v4.media.IMediaSession2 r0 = r1.mISession2     // Catch:{ RemoteException -> 0x00b0 }
            android.os.IBinder r0 = r0.asBinder()     // Catch:{ RemoteException -> 0x00b0 }
            android.os.IBinder$DeathRecipient r2 = r1.mDeathRecipient     // Catch:{ RemoteException -> 0x00b0 }
            r6 = 0
            r0.linkToDeath(r2, r6)     // Catch:{ RemoteException -> 0x00b0 }
            monitor-exit(r5)     // Catch:{ all -> 0x00e1 }
            java.util.concurrent.Executor r0 = r1.mCallbackExecutor     // Catch:{ all -> 0x00df }
            android.support.v4.media.MediaController2ImplBase$15 r2 = new android.support.v4.media.MediaController2ImplBase$15     // Catch:{ all -> 0x00df }
            r2.<init>(r3)     // Catch:{ all -> 0x00df }
            r0.execute(r2)     // Catch:{ all -> 0x00df }
            if (r4 == 0) goto L_0x00af
            android.support.v4.media.MediaController2 r0 = r1.mInstance
            r0.close()
        L_0x00af:
            return
        L_0x00b0:
            r0 = move-exception
            boolean r2 = DEBUG     // Catch:{ all -> 0x00e1 }
            if (r2 == 0) goto L_0x00bc
            java.lang.String r2 = "MC2ImplBase"
            java.lang.String r6 = "Session died too early."
            android.util.Log.d(r2, r6, r0)     // Catch:{ all -> 0x00e1 }
        L_0x00bc:
            r4 = 1
            monitor-exit(r5)     // Catch:{ all -> 0x00e1 }
            if (r4 == 0) goto L_0x00c5
            android.support.v4.media.MediaController2 r2 = r1.mInstance
            r2.close()
        L_0x00c5:
            return
        L_0x00c6:
            r0 = move-exception
            goto L_0x00db
        L_0x00c8:
            r0 = move-exception
            goto L_0x00d9
        L_0x00ca:
            r0 = move-exception
            goto L_0x00d7
        L_0x00cc:
            r0 = move-exception
            goto L_0x00d5
        L_0x00ce:
            r0 = move-exception
            goto L_0x00d3
        L_0x00d0:
            r0 = move-exception
            r7 = r20
        L_0x00d3:
            r8 = r21
        L_0x00d5:
            r10 = r23
        L_0x00d7:
            r12 = r25
        L_0x00d9:
            r13 = r26
        L_0x00db:
            r15 = r28
        L_0x00dd:
            monitor-exit(r5)     // Catch:{ all -> 0x00e1 }
            throw r0     // Catch:{ all -> 0x00df }
        L_0x00df:
            r0 = move-exception
            goto L_0x00f0
        L_0x00e1:
            r0 = move-exception
            goto L_0x00dd
        L_0x00e3:
            r0 = move-exception
            r7 = r20
            r8 = r21
            r10 = r23
            r12 = r25
            r13 = r26
            r15 = r28
        L_0x00f0:
            if (r4 == 0) goto L_0x00f7
            android.support.v4.media.MediaController2 r2 = r1.mInstance
            r2.close()
        L_0x00f7:
            throw r0
        L_0x00f8:
            r7 = r20
            r8 = r21
            r10 = r23
            r12 = r25
            r13 = r26
            r15 = r28
        L_0x0104:
            r0 = 1
            if (r0 == 0) goto L_0x010c
            android.support.v4.media.MediaController2 r2 = r1.mInstance
            r2.close()
        L_0x010c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaController2ImplBase.onConnectedNotLocked(android.support.v4.media.IMediaSession2, android.support.v4.media.SessionCommandGroup2, int, android.support.v4.media.MediaItem2, long, long, float, long, android.support.v4.media.MediaController2$PlaybackInfo, int, int, java.util.List, android.app.PendingIntent):void");
    }

    /* access modifiers changed from: package-private */
    public void onCustomCommand(final SessionCommand2 command, final Bundle args, final ResultReceiver receiver) {
        if (DEBUG) {
            Log.d(TAG, "onCustomCommand cmd=" + command);
        }
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                MediaController2ImplBase.this.mCallback.onCustomCommand(MediaController2ImplBase.this.mInstance, command, args, receiver);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void onAllowedCommandsChanged(final SessionCommandGroup2 commands) {
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                MediaController2ImplBase.this.mCallback.onAllowedCommandsChanged(MediaController2ImplBase.this.mInstance, commands);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void onCustomLayoutChanged(final List<MediaSession2.CommandButton> layout) {
        this.mCallbackExecutor.execute(new Runnable() {
            public void run() {
                MediaController2ImplBase.this.mCallback.onCustomLayoutChanged(MediaController2ImplBase.this.mInstance, layout);
            }
        });
    }

    private class SessionServiceConnection implements ServiceConnection {
        private SessionServiceConnection() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            if (MediaController2ImplBase.DEBUG) {
                Log.d(MediaController2ImplBase.TAG, "onServiceConnected " + name + " " + this);
            }
            if (!MediaController2ImplBase.this.mToken.getPackageName().equals(name.getPackageName())) {
                Log.wtf(MediaController2ImplBase.TAG, name + " was connected, but expected pkg=" + MediaController2ImplBase.this.mToken.getPackageName() + " with id=" + MediaController2ImplBase.this.mToken.getId());
                return;
            }
            MediaController2ImplBase.this.connectToSession(IMediaSession2.Stub.asInterface(service));
        }

        public void onServiceDisconnected(ComponentName name) {
            if (MediaController2ImplBase.DEBUG) {
                Log.w(MediaController2ImplBase.TAG, "Session service " + name + " is disconnected.");
            }
        }

        public void onBindingDied(ComponentName name) {
            MediaController2ImplBase.this.close();
        }
    }
}
