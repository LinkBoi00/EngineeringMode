package android.support.v4.media;

import android.support.v4.media.BaseMediaPlayer;
import android.support.v4.media.MediaSession2;
import android.support.v4.util.ArrayMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class SessionPlaylistAgentImplBase extends MediaPlaylistAgent {
    static final int END_OF_PLAYLIST = -1;
    static final int NO_VALID_ITEMS = -2;
    /* access modifiers changed from: private */
    public PlayItem mCurrent;
    private MediaSession2.OnDataSourceMissingHelper mDsmHelper;
    /* access modifiers changed from: private */
    public final PlayItem mEopPlayItem = new PlayItem(-1, (DataSourceDesc) null);
    private Map<MediaItem2, DataSourceDesc> mItemDsdMap = new ArrayMap();
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private MediaMetadata2 mMetadata;
    /* access modifiers changed from: private */
    public BaseMediaPlayer mPlayer;
    private final MyPlayerEventCallback mPlayerCallback;
    private ArrayList<MediaItem2> mPlaylist = new ArrayList<>();
    private int mRepeatMode;
    private final MediaSession2ImplBase mSession;
    private int mShuffleMode;
    /* access modifiers changed from: private */
    public ArrayList<MediaItem2> mShuffledList = new ArrayList<>();

    private class MyPlayerEventCallback extends BaseMediaPlayer.PlayerEventCallback {
        private MyPlayerEventCallback() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onCurrentDataSourceChanged(android.support.v4.media.BaseMediaPlayer r5, android.support.v4.media.DataSourceDesc r6) {
            /*
                r4 = this;
                android.support.v4.media.SessionPlaylistAgentImplBase r0 = android.support.v4.media.SessionPlaylistAgentImplBase.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                android.support.v4.media.SessionPlaylistAgentImplBase r1 = android.support.v4.media.SessionPlaylistAgentImplBase.this     // Catch:{ all -> 0x0032 }
                android.support.v4.media.BaseMediaPlayer r1 = r1.mPlayer     // Catch:{ all -> 0x0032 }
                if (r1 == r5) goto L_0x0011
                monitor-exit(r0)     // Catch:{ all -> 0x0032 }
                return
            L_0x0011:
                if (r6 != 0) goto L_0x0030
                android.support.v4.media.SessionPlaylistAgentImplBase r1 = android.support.v4.media.SessionPlaylistAgentImplBase.this     // Catch:{ all -> 0x0032 }
                android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r1 = r1.mCurrent     // Catch:{ all -> 0x0032 }
                if (r1 == 0) goto L_0x0030
                android.support.v4.media.SessionPlaylistAgentImplBase r1 = android.support.v4.media.SessionPlaylistAgentImplBase.this     // Catch:{ all -> 0x0032 }
                android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r2 = r1.mCurrent     // Catch:{ all -> 0x0032 }
                int r2 = r2.shuffledIdx     // Catch:{ all -> 0x0032 }
                r3 = 1
                android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r2 = r1.getNextValidPlayItemLocked(r2, r3)     // Catch:{ all -> 0x0032 }
                android.support.v4.media.SessionPlaylistAgentImplBase.PlayItem unused = r1.mCurrent = r2     // Catch:{ all -> 0x0032 }
                android.support.v4.media.SessionPlaylistAgentImplBase r1 = android.support.v4.media.SessionPlaylistAgentImplBase.this     // Catch:{ all -> 0x0032 }
                r1.updateCurrentIfNeededLocked()     // Catch:{ all -> 0x0032 }
            L_0x0030:
                monitor-exit(r0)     // Catch:{ all -> 0x0032 }
                return
            L_0x0032:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0032 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.SessionPlaylistAgentImplBase.MyPlayerEventCallback.onCurrentDataSourceChanged(android.support.v4.media.BaseMediaPlayer, android.support.v4.media.DataSourceDesc):void");
        }
    }

    private class PlayItem {
        public DataSourceDesc dsd;
        public MediaItem2 mediaItem;
        public int shuffledIdx;

        PlayItem(SessionPlaylistAgentImplBase sessionPlaylistAgentImplBase, int shuffledIdx2) {
            this(shuffledIdx2, (DataSourceDesc) null);
        }

        PlayItem(int shuffledIdx2, DataSourceDesc dsd2) {
            this.shuffledIdx = shuffledIdx2;
            if (shuffledIdx2 >= 0) {
                this.mediaItem = (MediaItem2) SessionPlaylistAgentImplBase.this.mShuffledList.get(shuffledIdx2);
                if (dsd2 == null) {
                    synchronized (SessionPlaylistAgentImplBase.this.mLock) {
                        this.dsd = SessionPlaylistAgentImplBase.this.retrieveDataSourceDescLocked(this.mediaItem);
                    }
                    return;
                }
                this.dsd = dsd2;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean isValid() {
            if (this == SessionPlaylistAgentImplBase.this.mEopPlayItem) {
                return true;
            }
            MediaItem2 mediaItem2 = this.mediaItem;
            if (mediaItem2 == null || this.dsd == null) {
                return false;
            }
            if (mediaItem2.getDataSourceDesc() != null && !this.mediaItem.getDataSourceDesc().equals(this.dsd)) {
                return false;
            }
            synchronized (SessionPlaylistAgentImplBase.this.mLock) {
                if (this.shuffledIdx >= SessionPlaylistAgentImplBase.this.mShuffledList.size()) {
                    return false;
                }
                if (this.mediaItem != SessionPlaylistAgentImplBase.this.mShuffledList.get(this.shuffledIdx)) {
                    return false;
                }
                return true;
            }
        }
    }

    SessionPlaylistAgentImplBase(MediaSession2ImplBase session, BaseMediaPlayer player) {
        if (session == null) {
            throw new IllegalArgumentException("sessionImpl shouldn't be null");
        } else if (player != null) {
            this.mSession = session;
            this.mPlayer = player;
            MyPlayerEventCallback myPlayerEventCallback = new MyPlayerEventCallback();
            this.mPlayerCallback = myPlayerEventCallback;
            this.mPlayer.registerPlayerEventCallback(session.getCallbackExecutor(), myPlayerEventCallback);
        } else {
            throw new IllegalArgumentException("player shouldn't be null");
        }
    }

    public void setPlayer(BaseMediaPlayer player) {
        if (player != null) {
            synchronized (this.mLock) {
                BaseMediaPlayer baseMediaPlayer = this.mPlayer;
                if (player != baseMediaPlayer) {
                    baseMediaPlayer.unregisterPlayerEventCallback(this.mPlayerCallback);
                    this.mPlayer = player;
                    player.registerPlayerEventCallback(this.mSession.getCallbackExecutor(), this.mPlayerCallback);
                    updatePlayerDataSourceLocked();
                    return;
                }
                return;
            }
        }
        throw new IllegalArgumentException("player shouldn't be null");
    }

    public void setOnDataSourceMissingHelper(MediaSession2.OnDataSourceMissingHelper helper) {
        synchronized (this.mLock) {
            this.mDsmHelper = helper;
        }
    }

    public void clearOnDataSourceMissingHelper() {
        synchronized (this.mLock) {
            this.mDsmHelper = null;
        }
    }

    public List<MediaItem2> getPlaylist() {
        List<MediaItem2> unmodifiableList;
        synchronized (this.mLock) {
            unmodifiableList = Collections.unmodifiableList(this.mPlaylist);
        }
        return unmodifiableList;
    }

    public void setPlaylist(List<MediaItem2> list, MediaMetadata2 metadata) {
        if (list != null) {
            synchronized (this.mLock) {
                this.mItemDsdMap.clear();
                this.mPlaylist.clear();
                this.mPlaylist.addAll(list);
                applyShuffleModeLocked();
                this.mMetadata = metadata;
                this.mCurrent = getNextValidPlayItemLocked(-1, 1);
                updatePlayerDataSourceLocked();
            }
            notifyPlaylistChanged();
            return;
        }
        throw new IllegalArgumentException("list shouldn't be null");
    }

    public MediaMetadata2 getPlaylistMetadata() {
        MediaMetadata2 mediaMetadata2;
        synchronized (this.mLock) {
            mediaMetadata2 = this.mMetadata;
        }
        return mediaMetadata2;
    }

    public void updatePlaylistMetadata(MediaMetadata2 metadata) {
        synchronized (this.mLock) {
            if (metadata != this.mMetadata) {
                this.mMetadata = metadata;
                notifyPlaylistMetadataChanged();
            }
        }
    }

    public MediaItem2 getCurrentMediaItem() {
        MediaItem2 mediaItem2;
        synchronized (this.mLock) {
            PlayItem playItem = this.mCurrent;
            mediaItem2 = playItem == null ? null : playItem.mediaItem;
        }
        return mediaItem2;
    }

    public void addPlaylistItem(int index, MediaItem2 item) {
        if (item != null) {
            synchronized (this.mLock) {
                int index2 = clamp(index, this.mPlaylist.size());
                this.mPlaylist.add(index2, item);
                if (this.mShuffleMode == 0) {
                    this.mShuffledList.add(index2, item);
                } else {
                    this.mShuffledList.add((int) (Math.random() * ((double) (this.mShuffledList.size() + 1))), item);
                }
                if (!hasValidItem()) {
                    this.mCurrent = getNextValidPlayItemLocked(-1, 1);
                    updatePlayerDataSourceLocked();
                } else {
                    updateCurrentIfNeededLocked();
                }
            }
            notifyPlaylistChanged();
            return;
        }
        throw new IllegalArgumentException("item shouldn't be null");
    }

    public void removePlaylistItem(MediaItem2 item) {
        if (item != null) {
            synchronized (this.mLock) {
                if (this.mPlaylist.remove(item)) {
                    this.mShuffledList.remove(item);
                    this.mItemDsdMap.remove(item);
                    updateCurrentIfNeededLocked();
                    notifyPlaylistChanged();
                    return;
                }
                return;
            }
        }
        throw new IllegalArgumentException("item shouldn't be null");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0052, code lost:
        notifyPlaylistChanged();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0055, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void replacePlaylistItem(int r6, android.support.v4.media.MediaItem2 r7) {
        /*
            r5 = this;
            if (r7 == 0) goto L_0x0059
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            java.util.ArrayList<android.support.v4.media.MediaItem2> r1 = r5.mPlaylist     // Catch:{ all -> 0x0056 }
            int r1 = r1.size()     // Catch:{ all -> 0x0056 }
            if (r1 > 0) goto L_0x000f
            monitor-exit(r0)     // Catch:{ all -> 0x0056 }
            return
        L_0x000f:
            java.util.ArrayList<android.support.v4.media.MediaItem2> r1 = r5.mPlaylist     // Catch:{ all -> 0x0056 }
            int r1 = r1.size()     // Catch:{ all -> 0x0056 }
            r2 = 1
            int r1 = r1 - r2
            int r1 = clamp(r6, r1)     // Catch:{ all -> 0x0056 }
            r6 = r1
            java.util.ArrayList<android.support.v4.media.MediaItem2> r1 = r5.mShuffledList     // Catch:{ all -> 0x0056 }
            java.util.ArrayList<android.support.v4.media.MediaItem2> r3 = r5.mPlaylist     // Catch:{ all -> 0x0056 }
            java.lang.Object r3 = r3.get(r6)     // Catch:{ all -> 0x0056 }
            int r1 = r1.indexOf(r3)     // Catch:{ all -> 0x0056 }
            java.util.Map<android.support.v4.media.MediaItem2, android.support.v4.media.DataSourceDesc> r3 = r5.mItemDsdMap     // Catch:{ all -> 0x0056 }
            java.util.ArrayList<android.support.v4.media.MediaItem2> r4 = r5.mShuffledList     // Catch:{ all -> 0x0056 }
            java.lang.Object r4 = r4.get(r1)     // Catch:{ all -> 0x0056 }
            r3.remove(r4)     // Catch:{ all -> 0x0056 }
            java.util.ArrayList<android.support.v4.media.MediaItem2> r3 = r5.mShuffledList     // Catch:{ all -> 0x0056 }
            r3.set(r1, r7)     // Catch:{ all -> 0x0056 }
            java.util.ArrayList<android.support.v4.media.MediaItem2> r3 = r5.mPlaylist     // Catch:{ all -> 0x0056 }
            r3.set(r6, r7)     // Catch:{ all -> 0x0056 }
            boolean r3 = r5.hasValidItem()     // Catch:{ all -> 0x0056 }
            if (r3 != 0) goto L_0x004e
            r3 = -1
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r2 = r5.getNextValidPlayItemLocked(r3, r2)     // Catch:{ all -> 0x0056 }
            r5.mCurrent = r2     // Catch:{ all -> 0x0056 }
            r5.updatePlayerDataSourceLocked()     // Catch:{ all -> 0x0056 }
            goto L_0x0051
        L_0x004e:
            r5.updateCurrentIfNeededLocked()     // Catch:{ all -> 0x0056 }
        L_0x0051:
            monitor-exit(r0)     // Catch:{ all -> 0x0056 }
            r5.notifyPlaylistChanged()
            return
        L_0x0056:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0056 }
            throw r1
        L_0x0059:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "item shouldn't be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.SessionPlaylistAgentImplBase.replacePlaylistItem(int, android.support.v4.media.MediaItem2):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void skipToPlaylistItem(android.support.v4.media.MediaItem2 r4) {
        /*
            r3 = this;
            if (r4 == 0) goto L_0x0031
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = r3.hasValidItem()     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x002c
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r1 = r3.mCurrent     // Catch:{ all -> 0x002e }
            android.support.v4.media.MediaItem2 r1 = r1.mediaItem     // Catch:{ all -> 0x002e }
            boolean r1 = r4.equals(r1)     // Catch:{ all -> 0x002e }
            if (r1 == 0) goto L_0x0016
            goto L_0x002c
        L_0x0016:
            java.util.ArrayList<android.support.v4.media.MediaItem2> r1 = r3.mShuffledList     // Catch:{ all -> 0x002e }
            int r1 = r1.indexOf(r4)     // Catch:{ all -> 0x002e }
            if (r1 >= 0) goto L_0x0020
            monitor-exit(r0)     // Catch:{ all -> 0x002e }
            return
        L_0x0020:
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r2 = new android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem     // Catch:{ all -> 0x002e }
            r2.<init>(r3, r1)     // Catch:{ all -> 0x002e }
            r3.mCurrent = r2     // Catch:{ all -> 0x002e }
            r3.updateCurrentIfNeededLocked()     // Catch:{ all -> 0x002e }
            monitor-exit(r0)     // Catch:{ all -> 0x002e }
            return
        L_0x002c:
            monitor-exit(r0)     // Catch:{ all -> 0x002e }
            return
        L_0x002e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002e }
            throw r1
        L_0x0031:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "item shouldn't be null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.SessionPlaylistAgentImplBase.skipToPlaylistItem(android.support.v4.media.MediaItem2):void");
    }

    public void skipToPreviousItem() {
        synchronized (this.mLock) {
            if (hasValidItem()) {
                PlayItem prev = getNextValidPlayItemLocked(this.mCurrent.shuffledIdx, -1);
                if (prev != this.mEopPlayItem) {
                    this.mCurrent = prev;
                }
                updateCurrentIfNeededLocked();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void skipToNextItem() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mLock
            monitor-enter(r0)
            boolean r1 = r3.hasValidItem()     // Catch:{ all -> 0x0024 }
            if (r1 == 0) goto L_0x0022
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r1 = r3.mCurrent     // Catch:{ all -> 0x0024 }
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r2 = r3.mEopPlayItem     // Catch:{ all -> 0x0024 }
            if (r1 != r2) goto L_0x0010
            goto L_0x0022
        L_0x0010:
            int r1 = r1.shuffledIdx     // Catch:{ all -> 0x0024 }
            r2 = 1
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r1 = r3.getNextValidPlayItemLocked(r1, r2)     // Catch:{ all -> 0x0024 }
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r2 = r3.mEopPlayItem     // Catch:{ all -> 0x0024 }
            if (r1 == r2) goto L_0x001d
            r3.mCurrent = r1     // Catch:{ all -> 0x0024 }
        L_0x001d:
            r3.updateCurrentIfNeededLocked()     // Catch:{ all -> 0x0024 }
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            return
        L_0x0022:
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            return
        L_0x0024:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.SessionPlaylistAgentImplBase.skipToNextItem():void");
    }

    public int getRepeatMode() {
        int i;
        synchronized (this.mLock) {
            i = this.mRepeatMode;
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        notifyRepeatModeChanged();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setRepeatMode(int r5) {
        /*
            r4 = this;
            if (r5 < 0) goto L_0x0043
            r0 = 3
            if (r5 <= r0) goto L_0x0006
            goto L_0x0043
        L_0x0006:
            java.lang.Object r0 = r4.mLock
            monitor-enter(r0)
            int r1 = r4.mRepeatMode     // Catch:{ all -> 0x0040 }
            if (r1 != r5) goto L_0x000f
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            return
        L_0x000f:
            r4.mRepeatMode = r5     // Catch:{ all -> 0x0040 }
            r1 = 1
            switch(r5) {
                case 0: goto L_0x0035;
                case 1: goto L_0x0027;
                case 2: goto L_0x0016;
                case 3: goto L_0x0016;
                default: goto L_0x0015;
            }     // Catch:{ all -> 0x0040 }
        L_0x0015:
            goto L_0x003b
        L_0x0016:
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r2 = r4.mCurrent     // Catch:{ all -> 0x0040 }
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r3 = r4.mEopPlayItem     // Catch:{ all -> 0x0040 }
            if (r2 != r3) goto L_0x0035
            r2 = -1
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r1 = r4.getNextValidPlayItemLocked(r2, r1)     // Catch:{ all -> 0x0040 }
            r4.mCurrent = r1     // Catch:{ all -> 0x0040 }
            r4.updatePlayerDataSourceLocked()     // Catch:{ all -> 0x0040 }
            goto L_0x0035
        L_0x0027:
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r2 = r4.mCurrent     // Catch:{ all -> 0x0040 }
            if (r2 == 0) goto L_0x003b
            android.support.v4.media.SessionPlaylistAgentImplBase$PlayItem r3 = r4.mEopPlayItem     // Catch:{ all -> 0x0040 }
            if (r2 == r3) goto L_0x003b
            android.support.v4.media.BaseMediaPlayer r2 = r4.mPlayer     // Catch:{ all -> 0x0040 }
            r2.loopCurrent(r1)     // Catch:{ all -> 0x0040 }
            goto L_0x003b
        L_0x0035:
            android.support.v4.media.BaseMediaPlayer r1 = r4.mPlayer     // Catch:{ all -> 0x0040 }
            r2 = 0
            r1.loopCurrent(r2)     // Catch:{ all -> 0x0040 }
        L_0x003b:
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            r4.notifyRepeatModeChanged()
            return
        L_0x0040:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0040 }
            throw r1
        L_0x0043:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.SessionPlaylistAgentImplBase.setRepeatMode(int):void");
    }

    public int getShuffleMode() {
        int i;
        synchronized (this.mLock) {
            i = this.mShuffleMode;
        }
        return i;
    }

    public void setShuffleMode(int shuffleMode) {
        if (shuffleMode >= 0 && shuffleMode <= 2) {
            synchronized (this.mLock) {
                if (this.mShuffleMode != shuffleMode) {
                    this.mShuffleMode = shuffleMode;
                    applyShuffleModeLocked();
                    updateCurrentIfNeededLocked();
                    notifyShuffleModeChanged();
                }
            }
        }
    }

    public MediaItem2 getMediaItem(DataSourceDesc dsd) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getCurShuffledIndex() {
        int i;
        synchronized (this.mLock) {
            i = hasValidItem() ? this.mCurrent.shuffledIdx : -2;
        }
        return i;
    }

    private boolean hasValidItem() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mCurrent != null;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public DataSourceDesc retrieveDataSourceDescLocked(MediaItem2 item) {
        DataSourceDesc dsd = item.getDataSourceDesc();
        if (dsd != null) {
            this.mItemDsdMap.put(item, dsd);
            return dsd;
        }
        DataSourceDesc dsd2 = this.mItemDsdMap.get(item);
        if (dsd2 != null) {
            return dsd2;
        }
        MediaSession2.OnDataSourceMissingHelper helper = this.mDsmHelper;
        if (!(helper == null || (dsd2 = helper.onDataSourceMissing(this.mSession.getInstance(), item)) == null)) {
            this.mItemDsdMap.put(item, dsd2);
        }
        return dsd2;
    }

    /* access modifiers changed from: private */
    public PlayItem getNextValidPlayItemLocked(int curShuffledIdx, int direction) {
        int size = this.mPlaylist.size();
        int i = -1;
        if (curShuffledIdx == -1) {
            if (direction <= 0) {
                i = size;
            }
            curShuffledIdx = i;
        }
        for (int i2 = 0; i2 < size; i2++) {
            curShuffledIdx += direction;
            if (curShuffledIdx < 0 || curShuffledIdx >= this.mPlaylist.size()) {
                if (this.mRepeatMode != 0) {
                    curShuffledIdx = curShuffledIdx < 0 ? this.mPlaylist.size() - 1 : 0;
                } else if (i2 == size - 1) {
                    return null;
                } else {
                    return this.mEopPlayItem;
                }
            }
            DataSourceDesc dsd = retrieveDataSourceDescLocked(this.mShuffledList.get(curShuffledIdx));
            if (dsd != null) {
                return new PlayItem(curShuffledIdx, dsd);
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void updateCurrentIfNeededLocked() {
        if (hasValidItem() && !this.mCurrent.isValid()) {
            int shuffledIdx = this.mShuffledList.indexOf(this.mCurrent.mediaItem);
            if (shuffledIdx >= 0) {
                this.mCurrent.shuffledIdx = shuffledIdx;
                return;
            }
            if (this.mCurrent.shuffledIdx >= this.mShuffledList.size()) {
                this.mCurrent = getNextValidPlayItemLocked(this.mShuffledList.size() - 1, 1);
            } else {
                PlayItem playItem = this.mCurrent;
                playItem.mediaItem = this.mShuffledList.get(playItem.shuffledIdx);
                if (retrieveDataSourceDescLocked(this.mCurrent.mediaItem) == null) {
                    this.mCurrent = getNextValidPlayItemLocked(this.mCurrent.shuffledIdx, 1);
                }
            }
            updatePlayerDataSourceLocked();
        }
    }

    private void updatePlayerDataSourceLocked() {
        PlayItem playItem = this.mCurrent;
        if (playItem != null && playItem != this.mEopPlayItem && this.mPlayer.getCurrentDataSource() != this.mCurrent.dsd) {
            this.mPlayer.setDataSource(this.mCurrent.dsd);
            BaseMediaPlayer baseMediaPlayer = this.mPlayer;
            boolean z = true;
            if (this.mRepeatMode != 1) {
                z = false;
            }
            baseMediaPlayer.loopCurrent(z);
        }
    }

    private void applyShuffleModeLocked() {
        this.mShuffledList.clear();
        this.mShuffledList.addAll(this.mPlaylist);
        int i = this.mShuffleMode;
        if (i == 1 || i == 2) {
            Collections.shuffle(this.mShuffledList);
        }
    }

    private static int clamp(int value, int size) {
        if (value < 0) {
            return 0;
        }
        return value > size ? size : value;
    }
}
