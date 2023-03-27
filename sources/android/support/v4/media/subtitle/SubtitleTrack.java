package android.support.v4.media.subtitle;

import android.graphics.Canvas;
import android.media.MediaFormat;
import android.os.Handler;
import android.support.v4.media.SubtitleData2;
import android.support.v4.media.subtitle.MediaTimeProvider;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class SubtitleTrack implements MediaTimeProvider.OnMediaTimeListener {
    private static final String TAG = "SubtitleTrack";
    public boolean DEBUG = false;
    /* access modifiers changed from: private */
    public final ArrayList<Cue> mActiveCues = new ArrayList<>();
    private CueList mCues;
    private MediaFormat mFormat;
    protected Handler mHandler = new Handler();
    private long mLastTimeMs;
    private long mLastUpdateTimeMs;
    private long mNextScheduledTimeMs = -1;
    /* access modifiers changed from: private */
    public Runnable mRunnable;
    private final LongSparseArray<Run> mRunsByEndTime = new LongSparseArray<>();
    private final LongSparseArray<Run> mRunsByID = new LongSparseArray<>();
    protected MediaTimeProvider mTimeProvider;
    protected boolean mVisible;

    public interface RenderingWidget {

        public interface OnChangedListener {
            void onChanged(RenderingWidget renderingWidget);
        }

        void draw(Canvas canvas);

        void onAttachedToWindow();

        void onDetachedFromWindow();

        void setOnChangedListener(OnChangedListener onChangedListener);

        void setSize(int i, int i2);

        void setVisible(boolean z);
    }

    public abstract RenderingWidget getRenderingWidget();

    /* access modifiers changed from: protected */
    public abstract void onData(byte[] bArr, boolean z, long j);

    public abstract void updateView(ArrayList<Cue> arrayList);

    public SubtitleTrack(MediaFormat format) {
        this.mFormat = format;
        this.mCues = new CueList();
        clearActiveCues();
        this.mLastTimeMs = -1;
    }

    public final MediaFormat getFormat() {
        return this.mFormat;
    }

    public void onData(SubtitleData2 data) {
        long runID = data.getStartTimeUs() + 1;
        onData(data.getData(), true, runID);
        setRunDiscardTimeMs(runID, (data.getStartTimeUs() + data.getDurationUs()) / 1000);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0007, code lost:
        if (r7.mLastUpdateTimeMs > r9) goto L_0x0009;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updateActiveCues(boolean r8, long r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            if (r8 != 0) goto L_0x0009
            long r0 = r7.mLastUpdateTimeMs     // Catch:{ all -> 0x00ba }
            int r0 = (r0 > r9 ? 1 : (r0 == r9 ? 0 : -1))
            if (r0 <= 0) goto L_0x000c
        L_0x0009:
            r7.clearActiveCues()     // Catch:{ all -> 0x00ba }
        L_0x000c:
            android.support.v4.media.subtitle.SubtitleTrack$CueList r0 = r7.mCues     // Catch:{ all -> 0x00ba }
            long r1 = r7.mLastUpdateTimeMs     // Catch:{ all -> 0x00ba }
            java.lang.Iterable r0 = r0.entriesBetween(r1, r9)     // Catch:{ all -> 0x00ba }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x00ba }
        L_0x0018:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x00ba }
            if (r1 == 0) goto L_0x009f
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x00ba }
            android.util.Pair r1 = (android.util.Pair) r1     // Catch:{ all -> 0x00ba }
            java.lang.Object r2 = r1.second     // Catch:{ all -> 0x00ba }
            android.support.v4.media.subtitle.SubtitleTrack$Cue r2 = (android.support.v4.media.subtitle.SubtitleTrack.Cue) r2     // Catch:{ all -> 0x00ba }
            long r3 = r2.mEndTimeMs     // Catch:{ all -> 0x00ba }
            java.lang.Object r5 = r1.first     // Catch:{ all -> 0x00ba }
            java.lang.Long r5 = (java.lang.Long) r5     // Catch:{ all -> 0x00ba }
            long r5 = r5.longValue()     // Catch:{ all -> 0x00ba }
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x0061
            boolean r3 = r7.DEBUG     // Catch:{ all -> 0x00ba }
            if (r3 == 0) goto L_0x0050
            java.lang.String r3 = "SubtitleTrack"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ba }
            r4.<init>()     // Catch:{ all -> 0x00ba }
            java.lang.String r5 = "Removing "
            r4.append(r5)     // Catch:{ all -> 0x00ba }
            r4.append(r2)     // Catch:{ all -> 0x00ba }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00ba }
            android.util.Log.v(r3, r4)     // Catch:{ all -> 0x00ba }
        L_0x0050:
            java.util.ArrayList<android.support.v4.media.subtitle.SubtitleTrack$Cue> r3 = r7.mActiveCues     // Catch:{ all -> 0x00ba }
            r3.remove(r2)     // Catch:{ all -> 0x00ba }
            long r3 = r2.mRunID     // Catch:{ all -> 0x00ba }
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x009d
            r0.remove()     // Catch:{ all -> 0x00ba }
            goto L_0x009d
        L_0x0061:
            long r3 = r2.mStartTimeMs     // Catch:{ all -> 0x00ba }
            java.lang.Object r5 = r1.first     // Catch:{ all -> 0x00ba }
            java.lang.Long r5 = (java.lang.Long) r5     // Catch:{ all -> 0x00ba }
            long r5 = r5.longValue()     // Catch:{ all -> 0x00ba }
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x0096
            boolean r3 = r7.DEBUG     // Catch:{ all -> 0x00ba }
            if (r3 == 0) goto L_0x0089
            java.lang.String r3 = "SubtitleTrack"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ba }
            r4.<init>()     // Catch:{ all -> 0x00ba }
            java.lang.String r5 = "Adding "
            r4.append(r5)     // Catch:{ all -> 0x00ba }
            r4.append(r2)     // Catch:{ all -> 0x00ba }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00ba }
            android.util.Log.v(r3, r4)     // Catch:{ all -> 0x00ba }
        L_0x0089:
            long[] r3 = r2.mInnerTimesMs     // Catch:{ all -> 0x00ba }
            if (r3 == 0) goto L_0x0090
            r2.onTime(r9)     // Catch:{ all -> 0x00ba }
        L_0x0090:
            java.util.ArrayList<android.support.v4.media.subtitle.SubtitleTrack$Cue> r3 = r7.mActiveCues     // Catch:{ all -> 0x00ba }
            r3.add(r2)     // Catch:{ all -> 0x00ba }
            goto L_0x009d
        L_0x0096:
            long[] r3 = r2.mInnerTimesMs     // Catch:{ all -> 0x00ba }
            if (r3 == 0) goto L_0x009d
            r2.onTime(r9)     // Catch:{ all -> 0x00ba }
        L_0x009d:
            goto L_0x0018
        L_0x009f:
            android.util.LongSparseArray<android.support.v4.media.subtitle.SubtitleTrack$Run> r0 = r7.mRunsByEndTime     // Catch:{ all -> 0x00ba }
            int r0 = r0.size()     // Catch:{ all -> 0x00ba }
            if (r0 <= 0) goto L_0x00b6
            android.util.LongSparseArray<android.support.v4.media.subtitle.SubtitleTrack$Run> r0 = r7.mRunsByEndTime     // Catch:{ all -> 0x00ba }
            r1 = 0
            long r2 = r0.keyAt(r1)     // Catch:{ all -> 0x00ba }
            int r0 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r0 > 0) goto L_0x00b6
            r7.removeRunsByEndTimeIndex(r1)     // Catch:{ all -> 0x00ba }
            goto L_0x009f
        L_0x00b6:
            r7.mLastUpdateTimeMs = r9     // Catch:{ all -> 0x00ba }
            monitor-exit(r7)
            return
        L_0x00ba:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.subtitle.SubtitleTrack.updateActiveCues(boolean, long):void");
    }

    private void removeRunsByEndTimeIndex(int ix) {
        Run run = this.mRunsByEndTime.valueAt(ix);
        while (run != null) {
            Cue cue = run.mFirstCue;
            while (cue != null) {
                this.mCues.remove(cue);
                Cue nextCue = cue.mNextInRun;
                cue.mNextInRun = null;
                cue = nextCue;
            }
            this.mRunsByID.remove(run.mRunID);
            Run nextRun = run.mNextRunAtEndTimeMs;
            run.mPrevRunAtEndTimeMs = null;
            run.mNextRunAtEndTimeMs = null;
            run = nextRun;
        }
        this.mRunsByEndTime.removeAt(ix);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        for (int ix = this.mRunsByEndTime.size() - 1; ix >= 0; ix--) {
            removeRunsByEndTimeIndex(ix);
        }
        super.finalize();
    }

    private synchronized void takeTime(long timeMs) {
        this.mLastTimeMs = timeMs;
    }

    /* access modifiers changed from: protected */
    public synchronized void clearActiveCues() {
        if (this.DEBUG) {
            Log.v(TAG, "Clearing " + this.mActiveCues.size() + " active cues");
        }
        this.mActiveCues.clear();
        this.mLastUpdateTimeMs = -1;
    }

    /* access modifiers changed from: protected */
    public void scheduleTimedEvents() {
        if (this.mTimeProvider != null) {
            this.mNextScheduledTimeMs = this.mCues.nextTimeAfter(this.mLastTimeMs);
            if (this.DEBUG) {
                Log.d(TAG, "sched @" + this.mNextScheduledTimeMs + " after " + this.mLastTimeMs);
            }
            MediaTimeProvider mediaTimeProvider = this.mTimeProvider;
            long j = this.mNextScheduledTimeMs;
            mediaTimeProvider.notifyAt(j >= 0 ? j * 1000 : -1, this);
        }
    }

    public void onTimedEvent(long timeUs) {
        if (this.DEBUG) {
            Log.d(TAG, "onTimedEvent " + timeUs);
        }
        synchronized (this) {
            long timeMs = timeUs / 1000;
            updateActiveCues(false, timeMs);
            takeTime(timeMs);
        }
        updateView(this.mActiveCues);
        scheduleTimedEvents();
    }

    public void onSeek(long timeUs) {
        if (this.DEBUG) {
            Log.d(TAG, "onSeek " + timeUs);
        }
        synchronized (this) {
            long timeMs = timeUs / 1000;
            updateActiveCues(true, timeMs);
            takeTime(timeMs);
        }
        updateView(this.mActiveCues);
        scheduleTimedEvents();
    }

    public void onStop() {
        synchronized (this) {
            if (this.DEBUG) {
                Log.d(TAG, "onStop");
            }
            clearActiveCues();
            this.mLastTimeMs = -1;
        }
        updateView(this.mActiveCues);
        this.mNextScheduledTimeMs = -1;
        this.mTimeProvider.notifyAt(-1, this);
    }

    public void show() {
        if (!this.mVisible) {
            this.mVisible = true;
            RenderingWidget renderingWidget = getRenderingWidget();
            if (renderingWidget != null) {
                renderingWidget.setVisible(true);
            }
            MediaTimeProvider mediaTimeProvider = this.mTimeProvider;
            if (mediaTimeProvider != null) {
                mediaTimeProvider.scheduleUpdate(this);
            }
        }
    }

    public void hide() {
        if (this.mVisible) {
            MediaTimeProvider mediaTimeProvider = this.mTimeProvider;
            if (mediaTimeProvider != null) {
                mediaTimeProvider.cancelNotifications(this);
            }
            RenderingWidget renderingWidget = getRenderingWidget();
            if (renderingWidget != null) {
                renderingWidget.setVisible(false);
            }
            this.mVisible = false;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d9, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f6, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean addCue(android.support.v4.media.subtitle.SubtitleTrack.Cue r12) {
        /*
            r11 = this;
            monitor-enter(r11)
            android.support.v4.media.subtitle.SubtitleTrack$CueList r0 = r11.mCues     // Catch:{ all -> 0x00f7 }
            r0.add(r12)     // Catch:{ all -> 0x00f7 }
            long r0 = r12.mRunID     // Catch:{ all -> 0x00f7 }
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 == 0) goto L_0x003f
            android.util.LongSparseArray<android.support.v4.media.subtitle.SubtitleTrack$Run> r0 = r11.mRunsByID     // Catch:{ all -> 0x00f7 }
            long r4 = r12.mRunID     // Catch:{ all -> 0x00f7 }
            java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x00f7 }
            android.support.v4.media.subtitle.SubtitleTrack$Run r0 = (android.support.v4.media.subtitle.SubtitleTrack.Run) r0     // Catch:{ all -> 0x00f7 }
            if (r0 != 0) goto L_0x002d
            android.support.v4.media.subtitle.SubtitleTrack$Run r1 = new android.support.v4.media.subtitle.SubtitleTrack$Run     // Catch:{ all -> 0x00f7 }
            r4 = 0
            r1.<init>()     // Catch:{ all -> 0x00f7 }
            r0 = r1
            android.util.LongSparseArray<android.support.v4.media.subtitle.SubtitleTrack$Run> r1 = r11.mRunsByID     // Catch:{ all -> 0x00f7 }
            long r4 = r12.mRunID     // Catch:{ all -> 0x00f7 }
            r1.put(r4, r0)     // Catch:{ all -> 0x00f7 }
            long r4 = r12.mEndTimeMs     // Catch:{ all -> 0x00f7 }
            r0.mEndTimeMs = r4     // Catch:{ all -> 0x00f7 }
            goto L_0x0039
        L_0x002d:
            long r4 = r0.mEndTimeMs     // Catch:{ all -> 0x00f7 }
            long r6 = r12.mEndTimeMs     // Catch:{ all -> 0x00f7 }
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 >= 0) goto L_0x0039
            long r4 = r12.mEndTimeMs     // Catch:{ all -> 0x00f7 }
            r0.mEndTimeMs = r4     // Catch:{ all -> 0x00f7 }
        L_0x0039:
            android.support.v4.media.subtitle.SubtitleTrack$Cue r1 = r0.mFirstCue     // Catch:{ all -> 0x00f7 }
            r12.mNextInRun = r1     // Catch:{ all -> 0x00f7 }
            r0.mFirstCue = r12     // Catch:{ all -> 0x00f7 }
        L_0x003f:
            r0 = -1
            android.support.v4.media.subtitle.MediaTimeProvider r4 = r11.mTimeProvider     // Catch:{ all -> 0x00f7 }
            r5 = 1
            r6 = 0
            if (r4 == 0) goto L_0x0051
            long r7 = r4.getCurrentTimeUs(r6, r5)     // Catch:{ IllegalStateException -> 0x0050 }
            r9 = 1000(0x3e8, double:4.94E-321)
            long r7 = r7 / r9
            r0 = r7
            goto L_0x0051
        L_0x0050:
            r4 = move-exception
        L_0x0051:
            boolean r4 = r11.DEBUG     // Catch:{ all -> 0x00f7 }
            if (r4 == 0) goto L_0x0093
            java.lang.String r4 = "SubtitleTrack"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f7 }
            r7.<init>()     // Catch:{ all -> 0x00f7 }
            java.lang.String r8 = "mVisible="
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            boolean r8 = r11.mVisible     // Catch:{ all -> 0x00f7 }
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            java.lang.String r8 = ", "
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            long r8 = r12.mStartTimeMs     // Catch:{ all -> 0x00f7 }
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            java.lang.String r8 = " <= "
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            r7.append(r0)     // Catch:{ all -> 0x00f7 }
            java.lang.String r8 = ", "
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            long r8 = r12.mEndTimeMs     // Catch:{ all -> 0x00f7 }
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            java.lang.String r8 = " >= "
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            long r8 = r11.mLastTimeMs     // Catch:{ all -> 0x00f7 }
            r7.append(r8)     // Catch:{ all -> 0x00f7 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00f7 }
            android.util.Log.v(r4, r7)     // Catch:{ all -> 0x00f7 }
        L_0x0093:
            boolean r4 = r11.mVisible     // Catch:{ all -> 0x00f7 }
            if (r4 == 0) goto L_0x00da
            long r7 = r12.mStartTimeMs     // Catch:{ all -> 0x00f7 }
            int r4 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r4 > 0) goto L_0x00da
            long r7 = r12.mEndTimeMs     // Catch:{ all -> 0x00f7 }
            long r9 = r11.mLastTimeMs     // Catch:{ all -> 0x00f7 }
            int r4 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r4 < 0) goto L_0x00da
            java.lang.Runnable r2 = r11.mRunnable     // Catch:{ all -> 0x00f7 }
            if (r2 == 0) goto L_0x00ae
            android.os.Handler r3 = r11.mHandler     // Catch:{ all -> 0x00f7 }
            r3.removeCallbacks(r2)     // Catch:{ all -> 0x00f7 }
        L_0x00ae:
            r2 = r11
            r3 = r0
            android.support.v4.media.subtitle.SubtitleTrack$1 r6 = new android.support.v4.media.subtitle.SubtitleTrack$1     // Catch:{ all -> 0x00f7 }
            r6.<init>(r2, r3)     // Catch:{ all -> 0x00f7 }
            r11.mRunnable = r6     // Catch:{ all -> 0x00f7 }
            android.os.Handler r7 = r11.mHandler     // Catch:{ all -> 0x00f7 }
            r8 = 10
            boolean r6 = r7.postDelayed(r6, r8)     // Catch:{ all -> 0x00f7 }
            if (r6 == 0) goto L_0x00cd
            boolean r6 = r11.DEBUG     // Catch:{ all -> 0x00f7 }
            if (r6 == 0) goto L_0x00d8
            java.lang.String r6 = "SubtitleTrack"
            java.lang.String r7 = "scheduling update"
            android.util.Log.v(r6, r7)     // Catch:{ all -> 0x00f7 }
            goto L_0x00d8
        L_0x00cd:
            boolean r6 = r11.DEBUG     // Catch:{ all -> 0x00f7 }
            if (r6 == 0) goto L_0x00d8
            java.lang.String r6 = "SubtitleTrack"
            java.lang.String r7 = "failed to schedule subtitle view update"
            android.util.Log.w(r6, r7)     // Catch:{ all -> 0x00f7 }
        L_0x00d8:
            monitor-exit(r11)
            return r5
        L_0x00da:
            boolean r4 = r11.mVisible     // Catch:{ all -> 0x00f7 }
            if (r4 == 0) goto L_0x00f5
            long r4 = r12.mEndTimeMs     // Catch:{ all -> 0x00f7 }
            long r7 = r11.mLastTimeMs     // Catch:{ all -> 0x00f7 }
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 < 0) goto L_0x00f5
            long r4 = r12.mStartTimeMs     // Catch:{ all -> 0x00f7 }
            long r7 = r11.mNextScheduledTimeMs     // Catch:{ all -> 0x00f7 }
            int r4 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r4 < 0) goto L_0x00f2
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x00f5
        L_0x00f2:
            r11.scheduleTimedEvents()     // Catch:{ all -> 0x00f7 }
        L_0x00f5:
            monitor-exit(r11)
            return r6
        L_0x00f7:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.subtitle.SubtitleTrack.addCue(android.support.v4.media.subtitle.SubtitleTrack$Cue):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0014, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setTimeProvider(android.support.v4.media.subtitle.MediaTimeProvider r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            android.support.v4.media.subtitle.MediaTimeProvider r0 = r1.mTimeProvider     // Catch:{ all -> 0x0015 }
            if (r0 != r2) goto L_0x0007
            monitor-exit(r1)
            return
        L_0x0007:
            if (r0 == 0) goto L_0x000c
            r0.cancelNotifications(r1)     // Catch:{ all -> 0x0015 }
        L_0x000c:
            r1.mTimeProvider = r2     // Catch:{ all -> 0x0015 }
            if (r2 == 0) goto L_0x0013
            r2.scheduleUpdate(r1)     // Catch:{ all -> 0x0015 }
        L_0x0013:
            monitor-exit(r1)
            return
        L_0x0015:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.subtitle.SubtitleTrack.setTimeProvider(android.support.v4.media.subtitle.MediaTimeProvider):void");
    }

    static class CueList {
        private static final String TAG = "CueList";
        public boolean DEBUG = false;
        /* access modifiers changed from: private */
        public SortedMap<Long, ArrayList<Cue>> mCues = new TreeMap();

        private boolean addEvent(Cue cue, long timeMs) {
            ArrayList<Cue> cues = (ArrayList) this.mCues.get(Long.valueOf(timeMs));
            if (cues == null) {
                cues = new ArrayList<>(2);
                this.mCues.put(Long.valueOf(timeMs), cues);
            } else if (cues.contains(cue)) {
                return false;
            }
            cues.add(cue);
            return true;
        }

        /* access modifiers changed from: private */
        public void removeEvent(Cue cue, long timeMs) {
            ArrayList<Cue> cues = (ArrayList) this.mCues.get(Long.valueOf(timeMs));
            if (cues != null) {
                cues.remove(cue);
                if (cues.size() == 0) {
                    this.mCues.remove(Long.valueOf(timeMs));
                }
            }
        }

        public void add(Cue cue) {
            if (cue.mStartTimeMs < cue.mEndTimeMs && addEvent(cue, cue.mStartTimeMs)) {
                long lastTimeMs = cue.mStartTimeMs;
                if (cue.mInnerTimesMs != null) {
                    for (long timeMs : cue.mInnerTimesMs) {
                        if (timeMs > lastTimeMs && timeMs < cue.mEndTimeMs) {
                            addEvent(cue, timeMs);
                            lastTimeMs = timeMs;
                        }
                    }
                }
                addEvent(cue, cue.mEndTimeMs);
            }
        }

        public void remove(Cue cue) {
            removeEvent(cue, cue.mStartTimeMs);
            if (cue.mInnerTimesMs != null) {
                for (long timeMs : cue.mInnerTimesMs) {
                    removeEvent(cue, timeMs);
                }
            }
            removeEvent(cue, cue.mEndTimeMs);
        }

        public Iterable<Pair<Long, Cue>> entriesBetween(long lastTimeMs, long timeMs) {
            final long j = lastTimeMs;
            final long j2 = timeMs;
            return new Iterable<Pair<Long, Cue>>() {
                public Iterator<Pair<Long, Cue>> iterator() {
                    if (CueList.this.DEBUG) {
                        Log.d(CueList.TAG, "slice (" + j + ", " + j2 + "]=");
                    }
                    try {
                        CueList cueList = CueList.this;
                        return new EntryIterator(cueList.mCues.subMap(Long.valueOf(j + 1), Long.valueOf(j2 + 1)));
                    } catch (IllegalArgumentException e) {
                        return new EntryIterator((SortedMap<Long, ArrayList<Cue>>) null);
                    }
                }
            };
        }

        public long nextTimeAfter(long timeMs) {
            try {
                SortedMap<Long, ArrayList<Cue>> tail = this.mCues.tailMap(Long.valueOf(1 + timeMs));
                if (tail != null) {
                    return tail.firstKey().longValue();
                }
                return -1;
            } catch (IllegalArgumentException e) {
                return -1;
            } catch (NoSuchElementException e2) {
                return -1;
            }
        }

        class EntryIterator implements Iterator<Pair<Long, Cue>> {
            private long mCurrentTimeMs;
            private boolean mDone;
            private Pair<Long, Cue> mLastEntry;
            private Iterator<Cue> mLastListIterator;
            private Iterator<Cue> mListIterator;
            private SortedMap<Long, ArrayList<Cue>> mRemainingCues;

            public boolean hasNext() {
                return !this.mDone;
            }

            public Pair<Long, Cue> next() {
                if (!this.mDone) {
                    this.mLastEntry = new Pair<>(Long.valueOf(this.mCurrentTimeMs), this.mListIterator.next());
                    Iterator<Cue> it = this.mListIterator;
                    this.mLastListIterator = it;
                    if (!it.hasNext()) {
                        nextKey();
                    }
                    return this.mLastEntry;
                }
                throw new NoSuchElementException("");
            }

            public void remove() {
                if (this.mLastListIterator == null || ((Cue) this.mLastEntry.second).mEndTimeMs != ((Long) this.mLastEntry.first).longValue()) {
                    throw new IllegalStateException("");
                }
                this.mLastListIterator.remove();
                this.mLastListIterator = null;
                if (((ArrayList) CueList.this.mCues.get(this.mLastEntry.first)).size() == 0) {
                    CueList.this.mCues.remove(this.mLastEntry.first);
                }
                Cue cue = (Cue) this.mLastEntry.second;
                CueList.this.removeEvent(cue, cue.mStartTimeMs);
                if (cue.mInnerTimesMs != null) {
                    for (long timeMs : cue.mInnerTimesMs) {
                        CueList.this.removeEvent(cue, timeMs);
                    }
                }
            }

            EntryIterator(SortedMap<Long, ArrayList<Cue>> cues) {
                if (CueList.this.DEBUG) {
                    Log.v(CueList.TAG, cues + "");
                }
                this.mRemainingCues = cues;
                this.mLastListIterator = null;
                nextKey();
            }

            /* Debug info: failed to restart local var, previous not found, register: 6 */
            private void nextKey() {
                do {
                    try {
                        SortedMap<Long, ArrayList<Cue>> sortedMap = this.mRemainingCues;
                        if (sortedMap != null) {
                            long longValue = sortedMap.firstKey().longValue();
                            this.mCurrentTimeMs = longValue;
                            this.mListIterator = ((ArrayList) this.mRemainingCues.get(Long.valueOf(longValue))).iterator();
                            try {
                                this.mRemainingCues = this.mRemainingCues.tailMap(Long.valueOf(this.mCurrentTimeMs + 1));
                            } catch (IllegalArgumentException e) {
                                this.mRemainingCues = null;
                            }
                            this.mDone = false;
                        } else {
                            throw new NoSuchElementException("");
                        }
                    } catch (NoSuchElementException e2) {
                        this.mDone = true;
                        this.mRemainingCues = null;
                        this.mListIterator = null;
                        return;
                    }
                } while (!this.mListIterator.hasNext());
            }
        }

        CueList() {
        }
    }

    static class Cue {
        public long mEndTimeMs;
        public long[] mInnerTimesMs;
        public Cue mNextInRun;
        public long mRunID;
        public long mStartTimeMs;

        Cue() {
        }

        public void onTime(long timeMs) {
        }
    }

    /* access modifiers changed from: protected */
    public void finishedRun(long runID) {
        Run run;
        if (runID != 0 && runID != -1 && (run = this.mRunsByID.get(runID)) != null) {
            run.storeByEndTimeMs(this.mRunsByEndTime);
        }
    }

    public void setRunDiscardTimeMs(long runID, long timeMs) {
        Run run;
        if (runID != 0 && runID != -1 && (run = this.mRunsByID.get(runID)) != null) {
            run.mEndTimeMs = timeMs;
            run.storeByEndTimeMs(this.mRunsByEndTime);
        }
    }

    public int getTrackType() {
        return getRenderingWidget() == null ? 3 : 4;
    }

    private static class Run {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public long mEndTimeMs;
        public Cue mFirstCue;
        public Run mNextRunAtEndTimeMs;
        public Run mPrevRunAtEndTimeMs;
        public long mRunID;
        private long mStoredEndTimeMs;

        static {
            Class<SubtitleTrack> cls = SubtitleTrack.class;
        }

        private Run() {
            this.mEndTimeMs = -1;
            this.mRunID = 0;
            this.mStoredEndTimeMs = -1;
        }

        public void storeByEndTimeMs(LongSparseArray<Run> runsByEndTime) {
            int ix = runsByEndTime.indexOfKey(this.mStoredEndTimeMs);
            if (ix >= 0) {
                if (this.mPrevRunAtEndTimeMs == null) {
                    Run run = this.mNextRunAtEndTimeMs;
                    if (run == null) {
                        runsByEndTime.removeAt(ix);
                    } else {
                        runsByEndTime.setValueAt(ix, run);
                    }
                }
                removeAtEndTimeMs();
            }
            long j = this.mEndTimeMs;
            if (j >= 0) {
                this.mPrevRunAtEndTimeMs = null;
                Run run2 = runsByEndTime.get(j);
                this.mNextRunAtEndTimeMs = run2;
                if (run2 != null) {
                    run2.mPrevRunAtEndTimeMs = this;
                }
                runsByEndTime.put(this.mEndTimeMs, this);
                this.mStoredEndTimeMs = this.mEndTimeMs;
            }
        }

        public void removeAtEndTimeMs() {
            Run prev = this.mPrevRunAtEndTimeMs;
            Run run = this.mPrevRunAtEndTimeMs;
            if (run != null) {
                run.mNextRunAtEndTimeMs = this.mNextRunAtEndTimeMs;
                this.mPrevRunAtEndTimeMs = null;
            }
            Run run2 = this.mNextRunAtEndTimeMs;
            if (run2 != null) {
                run2.mPrevRunAtEndTimeMs = prev;
                this.mNextRunAtEndTimeMs = null;
            }
        }
    }
}
