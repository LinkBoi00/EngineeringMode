package android.support.v4.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;

public class AudioFocusHandler {
    private static final boolean DEBUG = false;
    private static final String TAG = "AudioFocusHandler";
    private final AudioFocusHandlerImpl mImpl;

    interface AudioFocusHandlerImpl {
        void close();

        boolean onPauseRequested();

        boolean onPlayRequested();

        void onPlayerStateChanged(int i);

        void sendIntent(Intent intent);
    }

    AudioFocusHandler(Context context, MediaSession2 session) {
        this.mImpl = new AudioFocusHandlerImplBase(context, session);
    }

    public boolean onPlayRequested() {
        return this.mImpl.onPlayRequested();
    }

    public boolean onPauseRequested() {
        return this.mImpl.onPauseRequested();
    }

    public void onPlayerStateChanged(int playerState) {
        this.mImpl.onPlayerStateChanged(playerState);
    }

    public void close() {
        this.mImpl.close();
    }

    public void sendIntent(Intent intent) {
        this.mImpl.sendIntent(intent);
    }

    private static class AudioFocusHandlerImplBase implements AudioFocusHandlerImpl {
        private static final float VOLUME_DUCK_FACTOR = 0.2f;
        /* access modifiers changed from: private */
        public AudioAttributesCompat mAudioAttributes;
        private final AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioFocusListener();
        private final AudioManager mAudioManager;
        private final BroadcastReceiver mBecomingNoisyIntentReceiver = new NoisyIntentReceiver();
        private boolean mHasAudioFocus;
        /* access modifiers changed from: private */
        public boolean mHasRegisteredReceiver;
        private final IntentFilter mIntentFilter = new IntentFilter("android.media.AUDIO_BECOMING_NOISY");
        /* access modifiers changed from: private */
        public final Object mLock = new Object();
        /* access modifiers changed from: private */
        public boolean mResumeWhenAudioFocusGain;
        /* access modifiers changed from: private */
        public final MediaSession2 mSession;

        AudioFocusHandlerImplBase(Context context, MediaSession2 session) {
            this.mSession = session;
            this.mAudioManager = (AudioManager) context.getSystemService("audio");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:21:0x003c, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void updateAudioAttributesIfNeeded() {
            /*
                r4 = this;
                android.support.v4.media.MediaSession2 r0 = r4.mSession
                android.support.v4.media.VolumeProviderCompat r0 = r0.getVolumeProvider()
                if (r0 == 0) goto L_0x000a
                r0 = 0
                goto L_0x0019
            L_0x000a:
                android.support.v4.media.MediaSession2 r0 = r4.mSession
                android.support.v4.media.BaseMediaPlayer r0 = r0.getPlayer()
                if (r0 != 0) goto L_0x0014
                r1 = 0
                goto L_0x0018
            L_0x0014:
                android.support.v4.media.AudioAttributesCompat r1 = r0.getAudioAttributes()
            L_0x0018:
                r0 = r1
            L_0x0019:
                java.lang.Object r1 = r4.mLock
                monitor-enter(r1)
                android.support.v4.media.AudioAttributesCompat r2 = r4.mAudioAttributes     // Catch:{ all -> 0x003d }
                boolean r2 = android.support.v4.util.ObjectsCompat.equals(r0, r2)     // Catch:{ all -> 0x003d }
                if (r2 == 0) goto L_0x0026
                monitor-exit(r1)     // Catch:{ all -> 0x003d }
                return
            L_0x0026:
                r4.mAudioAttributes = r0     // Catch:{ all -> 0x003d }
                boolean r2 = r4.mHasAudioFocus     // Catch:{ all -> 0x003d }
                if (r2 == 0) goto L_0x003b
                boolean r2 = r4.requestAudioFocusLocked()     // Catch:{ all -> 0x003d }
                r4.mHasAudioFocus = r2     // Catch:{ all -> 0x003d }
                if (r2 != 0) goto L_0x003b
                java.lang.String r2 = "AudioFocusHandler"
                java.lang.String r3 = "Failed to regain audio focus."
                android.util.Log.w(r2, r3)     // Catch:{ all -> 0x003d }
            L_0x003b:
                monitor-exit(r1)     // Catch:{ all -> 0x003d }
                return
            L_0x003d:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x003d }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.updateAudioAttributesIfNeeded():void");
        }

        public boolean onPlayRequested() {
            updateAudioAttributesIfNeeded();
            synchronized (this.mLock) {
                if (!requestAudioFocusLocked()) {
                    return false;
                }
                return true;
            }
        }

        public boolean onPauseRequested() {
            synchronized (this.mLock) {
                this.mResumeWhenAudioFocusGain = false;
            }
            return true;
        }

        public void onPlayerStateChanged(int playerState) {
            synchronized (this.mLock) {
                switch (playerState) {
                    case 0:
                        abandonAudioFocusLocked();
                        break;
                    case 1:
                        updateAudioAttributesIfNeeded();
                        unregisterReceiverLocked();
                        break;
                    case 2:
                        updateAudioAttributesIfNeeded();
                        registerReceiverLocked();
                        break;
                    case 3:
                        abandonAudioFocusLocked();
                        unregisterReceiverLocked();
                        break;
                }
            }
        }

        public void close() {
            synchronized (this.mLock) {
                unregisterReceiverLocked();
                abandonAudioFocusLocked();
            }
        }

        public void sendIntent(Intent intent) {
            this.mBecomingNoisyIntentReceiver.onReceive(this.mSession.getContext(), intent);
        }

        private boolean requestAudioFocusLocked() {
            int focusGain = convertAudioAttributesToFocusGainLocked();
            if (focusGain == 0) {
                return true;
            }
            int audioFocusRequestResult = this.mAudioManager.requestAudioFocus(this.mAudioFocusListener, this.mAudioAttributes.getVolumeControlStream(), focusGain);
            if (audioFocusRequestResult == 1) {
                this.mHasAudioFocus = true;
            } else {
                Log.w(AudioFocusHandler.TAG, "requestAudioFocus(" + focusGain + ") failed (return=" + audioFocusRequestResult + ") playback wouldn't start.");
                this.mHasAudioFocus = false;
            }
            this.mResumeWhenAudioFocusGain = false;
            return this.mHasAudioFocus;
        }

        private void abandonAudioFocusLocked() {
            if (this.mHasAudioFocus) {
                this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
                this.mHasAudioFocus = false;
                this.mResumeWhenAudioFocusGain = false;
            }
        }

        private void registerReceiverLocked() {
            if (!this.mHasRegisteredReceiver) {
                this.mSession.getContext().registerReceiver(this.mBecomingNoisyIntentReceiver, this.mIntentFilter);
                this.mHasRegisteredReceiver = true;
            }
        }

        private void unregisterReceiverLocked() {
            if (this.mHasRegisteredReceiver) {
                this.mSession.getContext().unregisterReceiver(this.mBecomingNoisyIntentReceiver);
                this.mHasRegisteredReceiver = false;
            }
        }

        private int convertAudioAttributesToFocusGainLocked() {
            AudioAttributesCompat audioAttributesCompat = this.mAudioAttributes;
            if (audioAttributesCompat == null) {
                return 0;
            }
            switch (audioAttributesCompat.getUsage()) {
                case 0:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                    return 3;
                case 1:
                case 14:
                    return 1;
                case 2:
                case 3:
                case 4:
                    return 2;
                default:
                    return 0;
            }
        }

        private class NoisyIntentReceiver extends BroadcastReceiver {
            private NoisyIntentReceiver() {
            }

            /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
                r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.access$200(r1.this$0);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x0024, code lost:
                monitor-enter(r2);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x002b, code lost:
                if (android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.access$400(r1.this$0) != null) goto L_0x002f;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
                monitor-exit(r2);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:17:0x002f, code lost:
                r3 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.access$400(r1.this$0).getUsage();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:18:0x0039, code lost:
                monitor-exit(r2);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:19:0x003a, code lost:
                switch(r3) {
                    case 1: goto L_0x0056;
                    case 14: goto L_0x003e;
                    default: goto L_0x003d;
                };
             */
            /* JADX WARNING: Code restructure failed: missing block: B:20:0x003e, code lost:
                r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.access$500(r1.this$0).getPlayer();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x0048, code lost:
                if (r2 == null) goto L_?;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:22:0x004a, code lost:
                r2.setPlayerVolume(r2.getPlayerVolume() * android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.VOLUME_DUCK_FACTOR);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:23:0x0056, code lost:
                android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.access$500(r1.this$0).pause();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x001c, code lost:
                if ("android.media.AUDIO_BECOMING_NOISY".equals(r3.getAction()) == false) goto L_?;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onReceive(android.content.Context r2, android.content.Intent r3) {
                /*
                    r1 = this;
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    java.lang.Object r2 = r2.mLock
                    monitor-enter(r2)
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x0064 }
                    boolean r0 = r0.mHasRegisteredReceiver     // Catch:{ all -> 0x0064 }
                    if (r0 != 0) goto L_0x0011
                    monitor-exit(r2)     // Catch:{ all -> 0x0064 }
                    return
                L_0x0011:
                    monitor-exit(r2)     // Catch:{ all -> 0x0064 }
                    java.lang.String r2 = "android.media.AUDIO_BECOMING_NOISY"
                    java.lang.String r3 = r3.getAction()
                    boolean r2 = r2.equals(r3)
                    if (r2 == 0) goto L_0x0063
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    java.lang.Object r2 = r2.mLock
                    monitor-enter(r2)
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r3 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x0060 }
                    android.support.v4.media.AudioAttributesCompat r3 = r3.mAudioAttributes     // Catch:{ all -> 0x0060 }
                    if (r3 != 0) goto L_0x002f
                    monitor-exit(r2)     // Catch:{ all -> 0x0060 }
                    return
                L_0x002f:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r3 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x0060 }
                    android.support.v4.media.AudioAttributesCompat r3 = r3.mAudioAttributes     // Catch:{ all -> 0x0060 }
                    int r3 = r3.getUsage()     // Catch:{ all -> 0x0060 }
                    monitor-exit(r2)     // Catch:{ all -> 0x0060 }
                    switch(r3) {
                        case 1: goto L_0x0056;
                        case 14: goto L_0x003e;
                        default: goto L_0x003d;
                    }
                L_0x003d:
                    goto L_0x0063
                L_0x003e:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    android.support.v4.media.MediaSession2 r2 = r2.mSession
                    android.support.v4.media.BaseMediaPlayer r2 = r2.getPlayer()
                    if (r2 == 0) goto L_0x0063
                    float r3 = r2.getPlayerVolume()
                    r0 = 1045220557(0x3e4ccccd, float:0.2)
                    float r3 = r3 * r0
                    r2.setPlayerVolume(r3)
                    goto L_0x0063
                L_0x0056:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    android.support.v4.media.MediaSession2 r2 = r2.mSession
                    r2.pause()
                    goto L_0x0063
                L_0x0060:
                    r3 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x0060 }
                    throw r3
                L_0x0063:
                    return
                L_0x0064:
                    r3 = move-exception
                    monitor-exit(r2)     // Catch:{ all -> 0x0064 }
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.NoisyIntentReceiver.onReceive(android.content.Context, android.content.Intent):void");
            }
        }

        private class AudioFocusListener implements AudioManager.OnAudioFocusChangeListener {
            private float mPlayerDuckingVolume;
            private float mPlayerVolumeBeforeDucking;

            private AudioFocusListener() {
            }

            /* Debug info: failed to restart local var, previous not found, register: 6 */
            /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
                return;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onAudioFocusChange(int r7) {
                /*
                    r6 = this;
                    r0 = 1
                    switch(r7) {
                        case -3: goto L_0x0098;
                        case -2: goto L_0x007e;
                        case -1: goto L_0x0062;
                        case 0: goto L_0x0004;
                        case 1: goto L_0x0006;
                        default: goto L_0x0004;
                    }
                L_0x0004:
                    goto L_0x00eb
                L_0x0006:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r1 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    android.support.v4.media.MediaSession2 r1 = r1.mSession
                    int r1 = r1.getPlayerState()
                    if (r1 != r0) goto L_0x0033
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    java.lang.Object r0 = r0.mLock
                    monitor-enter(r0)
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r1 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x0030 }
                    boolean r1 = r1.mResumeWhenAudioFocusGain     // Catch:{ all -> 0x0030 }
                    if (r1 != 0) goto L_0x0024
                    monitor-exit(r0)     // Catch:{ all -> 0x0030 }
                    goto L_0x00eb
                L_0x0024:
                    monitor-exit(r0)     // Catch:{ all -> 0x0030 }
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    android.support.v4.media.MediaSession2 r0 = r0.mSession
                    r0.play()
                    goto L_0x00eb
                L_0x0030:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x0030 }
                    throw r1
                L_0x0033:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    android.support.v4.media.MediaSession2 r0 = r0.mSession
                    android.support.v4.media.BaseMediaPlayer r0 = r0.getPlayer()
                    if (r0 == 0) goto L_0x0060
                    float r1 = r0.getPlayerVolume()
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    java.lang.Object r2 = r2.mLock
                    monitor-enter(r2)
                    r3 = 0
                    float r4 = r6.mPlayerDuckingVolume     // Catch:{ all -> 0x005b }
                    int r4 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
                    if (r4 == 0) goto L_0x0054
                    monitor-exit(r2)     // Catch:{ all -> 0x005b }
                    goto L_0x00eb
                L_0x0054:
                    float r3 = r6.mPlayerVolumeBeforeDucking     // Catch:{ all -> 0x005b }
                    monitor-exit(r2)     // Catch:{ all -> 0x005e }
                    r0.setPlayerVolume(r3)
                    goto L_0x0060
                L_0x005b:
                    r4 = move-exception
                L_0x005c:
                    monitor-exit(r2)     // Catch:{ all -> 0x005e }
                    throw r4
                L_0x005e:
                    r4 = move-exception
                    goto L_0x005c
                L_0x0060:
                    goto L_0x00eb
                L_0x0062:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    android.support.v4.media.MediaSession2 r0 = r0.mSession
                    r0.pause()
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    java.lang.Object r1 = r0.mLock
                    monitor-enter(r1)
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x007b }
                    r2 = 0
                    boolean unused = r0.mResumeWhenAudioFocusGain = r2     // Catch:{ all -> 0x007b }
                    monitor-exit(r1)     // Catch:{ all -> 0x007b }
                    goto L_0x00eb
                L_0x007b:
                    r0 = move-exception
                    monitor-exit(r1)     // Catch:{ all -> 0x007b }
                    throw r0
                L_0x007e:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r1 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    android.support.v4.media.MediaSession2 r1 = r1.mSession
                    r1.pause()
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r1 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    java.lang.Object r1 = r1.mLock
                    monitor-enter(r1)
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x0095 }
                    boolean unused = r2.mResumeWhenAudioFocusGain = r0     // Catch:{ all -> 0x0095 }
                    monitor-exit(r1)     // Catch:{ all -> 0x0095 }
                    goto L_0x00eb
                L_0x0095:
                    r0 = move-exception
                    monitor-exit(r1)     // Catch:{ all -> 0x0095 }
                    throw r0
                L_0x0098:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r1 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this
                    java.lang.Object r1 = r1.mLock
                    monitor-enter(r1)
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x00e8 }
                    android.support.v4.media.AudioAttributesCompat r2 = r2.mAudioAttributes     // Catch:{ all -> 0x00e8 }
                    if (r2 != 0) goto L_0x00a9
                    monitor-exit(r1)     // Catch:{ all -> 0x00e8 }
                    goto L_0x00eb
                L_0x00a9:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r2 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x00e8 }
                    android.support.v4.media.AudioAttributesCompat r2 = r2.mAudioAttributes     // Catch:{ all -> 0x00e8 }
                    int r2 = r2.getContentType()     // Catch:{ all -> 0x00e8 }
                    if (r2 != r0) goto L_0x00bf
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x00e8 }
                    android.support.v4.media.MediaSession2 r0 = r0.mSession     // Catch:{ all -> 0x00e8 }
                    r0.pause()     // Catch:{ all -> 0x00e8 }
                    goto L_0x00e6
                L_0x00bf:
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r0 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x00e8 }
                    android.support.v4.media.MediaSession2 r0 = r0.mSession     // Catch:{ all -> 0x00e8 }
                    android.support.v4.media.BaseMediaPlayer r0 = r0.getPlayer()     // Catch:{ all -> 0x00e8 }
                    if (r0 == 0) goto L_0x00e6
                    float r2 = r0.getPlayerVolume()     // Catch:{ all -> 0x00e8 }
                    r3 = 1045220557(0x3e4ccccd, float:0.2)
                    float r3 = r3 * r2
                    android.support.v4.media.AudioFocusHandler$AudioFocusHandlerImplBase r4 = android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.this     // Catch:{ all -> 0x00e8 }
                    java.lang.Object r4 = r4.mLock     // Catch:{ all -> 0x00e8 }
                    monitor-enter(r4)     // Catch:{ all -> 0x00e8 }
                    r6.mPlayerVolumeBeforeDucking = r2     // Catch:{ all -> 0x00e3 }
                    r6.mPlayerDuckingVolume = r3     // Catch:{ all -> 0x00e3 }
                    monitor-exit(r4)     // Catch:{ all -> 0x00e3 }
                    r0.setPlayerVolume(r3)     // Catch:{ all -> 0x00e8 }
                    goto L_0x00e6
                L_0x00e3:
                    r5 = move-exception
                    monitor-exit(r4)     // Catch:{ all -> 0x00e3 }
                    throw r5     // Catch:{ all -> 0x00e8 }
                L_0x00e6:
                    monitor-exit(r1)     // Catch:{ all -> 0x00e8 }
                    goto L_0x00eb
                L_0x00e8:
                    r0 = move-exception
                    monitor-exit(r1)     // Catch:{ all -> 0x00e8 }
                    throw r0
                L_0x00eb:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.AudioFocusHandler.AudioFocusHandlerImplBase.AudioFocusListener.onAudioFocusChange(int):void");
            }
        }
    }
}
