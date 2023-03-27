package android.support.v4.media.subtitle;

import android.content.Context;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.subtitle.SubtitleTrack;
import android.view.accessibility.CaptioningManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class SubtitleController {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int WHAT_HIDE = 2;
    private static final int WHAT_SELECT_DEFAULT_TRACK = 4;
    private static final int WHAT_SELECT_TRACK = 3;
    private static final int WHAT_SHOW = 1;
    private Anchor mAnchor;
    private final Handler.Callback mCallback;
    private CaptioningManager.CaptioningChangeListener mCaptioningChangeListener;
    private CaptioningManager mCaptioningManager;
    private Handler mHandler;
    private Listener mListener;
    private ArrayList<Renderer> mRenderers;
    private final Object mRenderersLock;
    private SubtitleTrack mSelectedTrack;
    private boolean mShowing;
    private MediaTimeProvider mTimeProvider;
    private boolean mTrackIsExplicit;
    private ArrayList<SubtitleTrack> mTracks;
    private final Object mTracksLock;
    private boolean mVisibilityIsExplicit;

    public interface Anchor {
        Looper getSubtitleLooper();

        void setSubtitleWidget(SubtitleTrack.RenderingWidget renderingWidget);
    }

    interface Listener {
        void onSubtitleTrackSelected(SubtitleTrack subtitleTrack);
    }

    public static abstract class Renderer {
        public abstract SubtitleTrack createTrack(MediaFormat mediaFormat);

        public abstract boolean supports(MediaFormat mediaFormat);
    }

    public SubtitleController(Context context) {
        this(context, (MediaTimeProvider) null, (Listener) null);
    }

    public SubtitleController(Context context, MediaTimeProvider timeProvider, Listener listener) {
        this.mRenderersLock = new Object();
        this.mTracksLock = new Object();
        this.mCallback = new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        SubtitleController.this.doShow();
                        return true;
                    case 2:
                        SubtitleController.this.doHide();
                        return true;
                    case 3:
                        SubtitleController.this.doSelectTrack((SubtitleTrack) msg.obj);
                        return true;
                    case 4:
                        SubtitleController.this.doSelectDefaultTrack();
                        return true;
                    default:
                        return false;
                }
            }
        };
        this.mCaptioningChangeListener = new CaptioningManager.CaptioningChangeListener() {
            public void onEnabledChanged(boolean enabled) {
                SubtitleController.this.selectDefaultTrack();
            }

            public void onLocaleChanged(Locale locale) {
                SubtitleController.this.selectDefaultTrack();
            }
        };
        this.mTrackIsExplicit = false;
        this.mVisibilityIsExplicit = false;
        this.mTimeProvider = timeProvider;
        this.mListener = listener;
        this.mRenderers = new ArrayList<>();
        this.mShowing = false;
        this.mTracks = new ArrayList<>();
        this.mCaptioningManager = (CaptioningManager) context.getSystemService("captioning");
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        this.mCaptioningManager.removeCaptioningChangeListener(this.mCaptioningChangeListener);
        super.finalize();
    }

    public SubtitleTrack[] getTracks() {
        SubtitleTrack[] tracks;
        synchronized (this.mTracksLock) {
            tracks = new SubtitleTrack[this.mTracks.size()];
            this.mTracks.toArray(tracks);
        }
        return tracks;
    }

    public SubtitleTrack getSelectedTrack() {
        return this.mSelectedTrack;
    }

    private SubtitleTrack.RenderingWidget getRenderingWidget() {
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack == null) {
            return null;
        }
        return subtitleTrack.getRenderingWidget();
    }

    public boolean selectTrack(SubtitleTrack track) {
        if (track != null && !this.mTracks.contains(track)) {
            return false;
        }
        processOnAnchor(this.mHandler.obtainMessage(3, track));
        return true;
    }

    /* access modifiers changed from: private */
    public void doSelectTrack(SubtitleTrack track) {
        this.mTrackIsExplicit = true;
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack != track) {
            if (subtitleTrack != null) {
                subtitleTrack.hide();
                this.mSelectedTrack.setTimeProvider((MediaTimeProvider) null);
            }
            this.mSelectedTrack = track;
            Anchor anchor = this.mAnchor;
            if (anchor != null) {
                anchor.setSubtitleWidget(getRenderingWidget());
            }
            SubtitleTrack subtitleTrack2 = this.mSelectedTrack;
            if (subtitleTrack2 != null) {
                subtitleTrack2.setTimeProvider(this.mTimeProvider);
                this.mSelectedTrack.show();
            }
            Listener listener = this.mListener;
            if (listener != null) {
                listener.onSubtitleTrackSelected(track);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.support.v4.media.subtitle.SubtitleTrack getDefaultTrack() {
        /*
            r18 = this;
            r1 = r18
            r2 = 0
            r3 = -1
            android.view.accessibility.CaptioningManager r0 = r1.mCaptioningManager
            java.util.Locale r4 = r0.getLocale()
            r0 = r4
            if (r0 != 0) goto L_0x0013
            java.util.Locale r0 = java.util.Locale.getDefault()
            r5 = r0
            goto L_0x0014
        L_0x0013:
            r5 = r0
        L_0x0014:
            android.view.accessibility.CaptioningManager r0 = r1.mCaptioningManager
            boolean r0 = r0.isEnabled()
            r6 = 1
            r0 = r0 ^ r6
            r7 = r0
            java.lang.Object r8 = r1.mTracksLock
            monitor-enter(r8)
            java.util.ArrayList<android.support.v4.media.subtitle.SubtitleTrack> r0 = r1.mTracks     // Catch:{ all -> 0x00c3 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x00c3 }
        L_0x0026:
            boolean r9 = r0.hasNext()     // Catch:{ all -> 0x00c3 }
            if (r9 == 0) goto L_0x00c1
            java.lang.Object r9 = r0.next()     // Catch:{ all -> 0x00c3 }
            android.support.v4.media.subtitle.SubtitleTrack r9 = (android.support.v4.media.subtitle.SubtitleTrack) r9     // Catch:{ all -> 0x00c3 }
            android.media.MediaFormat r10 = r9.getFormat()     // Catch:{ all -> 0x00c3 }
            java.lang.String r11 = "language"
            java.lang.String r11 = r10.getString(r11)     // Catch:{ all -> 0x00c3 }
            java.lang.String r12 = "is-forced-subtitle"
            r13 = 0
            int r12 = android.support.v4.media.subtitle.SubtitleController.MediaFormatUtil.getInteger(r10, r12, r13)     // Catch:{ all -> 0x00c3 }
            if (r12 == 0) goto L_0x0047
            r12 = r6
            goto L_0x0048
        L_0x0047:
            r12 = r13
        L_0x0048:
            java.lang.String r14 = "is-autoselect"
            int r14 = android.support.v4.media.subtitle.SubtitleController.MediaFormatUtil.getInteger(r10, r14, r6)     // Catch:{ all -> 0x00c3 }
            if (r14 == 0) goto L_0x0052
            r14 = r6
            goto L_0x0053
        L_0x0052:
            r14 = r13
        L_0x0053:
            java.lang.String r15 = "is-default"
            int r15 = android.support.v4.media.subtitle.SubtitleController.MediaFormatUtil.getInteger(r10, r15, r13)     // Catch:{ all -> 0x00c3 }
            if (r15 == 0) goto L_0x005d
            r15 = r6
            goto L_0x005e
        L_0x005d:
            r15 = r13
        L_0x005e:
            if (r5 == 0) goto L_0x0083
            java.lang.String r6 = r5.getLanguage()     // Catch:{ all -> 0x00c3 }
            java.lang.String r13 = ""
            boolean r6 = r6.equals(r13)     // Catch:{ all -> 0x00c3 }
            if (r6 != 0) goto L_0x0083
            java.lang.String r6 = r5.getISO3Language()     // Catch:{ all -> 0x00c3 }
            boolean r6 = r6.equals(r11)     // Catch:{ all -> 0x00c3 }
            if (r6 != 0) goto L_0x0083
            java.lang.String r6 = r5.getLanguage()     // Catch:{ all -> 0x00c3 }
            boolean r6 = r6.equals(r11)     // Catch:{ all -> 0x00c3 }
            if (r6 == 0) goto L_0x0081
            goto L_0x0083
        L_0x0081:
            r6 = 0
            goto L_0x0084
        L_0x0083:
            r6 = 1
        L_0x0084:
            if (r12 == 0) goto L_0x0088
            r13 = 0
            goto L_0x008a
        L_0x0088:
            r13 = 8
        L_0x008a:
            if (r4 != 0) goto L_0x0091
            if (r15 == 0) goto L_0x0091
            r17 = 4
            goto L_0x0093
        L_0x0091:
            r17 = 0
        L_0x0093:
            int r13 = r13 + r17
            if (r14 == 0) goto L_0x009a
            r17 = 0
            goto L_0x009c
        L_0x009a:
            r17 = 2
        L_0x009c:
            int r13 = r13 + r17
            if (r6 == 0) goto L_0x00a3
            r16 = 1
            goto L_0x00a5
        L_0x00a3:
            r16 = 0
        L_0x00a5:
            int r13 = r13 + r16
            if (r7 == 0) goto L_0x00ae
            if (r12 != 0) goto L_0x00ae
            r6 = 1
            goto L_0x0026
        L_0x00ae:
            if (r4 != 0) goto L_0x00b2
            if (r15 != 0) goto L_0x00ba
        L_0x00b2:
            if (r6 == 0) goto L_0x00be
            if (r14 != 0) goto L_0x00ba
            if (r12 != 0) goto L_0x00ba
            if (r4 == 0) goto L_0x00be
        L_0x00ba:
            if (r13 <= r3) goto L_0x00be
            r3 = r13
            r2 = r9
        L_0x00be:
            r6 = 1
            goto L_0x0026
        L_0x00c1:
            monitor-exit(r8)     // Catch:{ all -> 0x00c3 }
            return r2
        L_0x00c3:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00c3 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.subtitle.SubtitleController.getDefaultTrack():android.support.v4.media.subtitle.SubtitleTrack");
    }

    static class MediaFormatUtil {
        MediaFormatUtil() {
        }

        static int getInteger(MediaFormat format, String name, int defaultValue) {
            try {
                return format.getInteger(name);
            } catch (ClassCastException | NullPointerException e) {
                return defaultValue;
            }
        }
    }

    public void selectDefaultTrack() {
        processOnAnchor(this.mHandler.obtainMessage(4));
    }

    /* access modifiers changed from: private */
    public void doSelectDefaultTrack() {
        SubtitleTrack subtitleTrack;
        if (this.mTrackIsExplicit) {
            if (!this.mVisibilityIsExplicit) {
                if (this.mCaptioningManager.isEnabled() || !((subtitleTrack = this.mSelectedTrack) == null || MediaFormatUtil.getInteger(subtitleTrack.getFormat(), "is-forced-subtitle", 0) == 0)) {
                    show();
                } else {
                    SubtitleTrack subtitleTrack2 = this.mSelectedTrack;
                    if (subtitleTrack2 != null && subtitleTrack2.getTrackType() == 4) {
                        hide();
                    }
                }
                this.mVisibilityIsExplicit = false;
            } else {
                return;
            }
        }
        SubtitleTrack track = getDefaultTrack();
        if (track != null) {
            selectTrack(track);
            this.mTrackIsExplicit = false;
            if (!this.mVisibilityIsExplicit) {
                show();
                this.mVisibilityIsExplicit = false;
            }
        }
    }

    public void reset() {
        checkAnchorLooper();
        hide();
        selectTrack((SubtitleTrack) null);
        this.mTracks.clear();
        this.mTrackIsExplicit = false;
        this.mVisibilityIsExplicit = false;
        this.mCaptioningManager.removeCaptioningChangeListener(this.mCaptioningChangeListener);
    }

    /* Debug info: failed to restart local var, previous not found, register: 6 */
    public SubtitleTrack addTrack(MediaFormat format) {
        SubtitleTrack track;
        synchronized (this.mRenderersLock) {
            Iterator<Renderer> it = this.mRenderers.iterator();
            while (it.hasNext()) {
                Renderer renderer = it.next();
                if (renderer.supports(format) && (track = renderer.createTrack(format)) != null) {
                    synchronized (this.mTracksLock) {
                        if (this.mTracks.size() == 0) {
                            this.mCaptioningManager.addCaptioningChangeListener(this.mCaptioningChangeListener);
                        }
                        this.mTracks.add(track);
                    }
                    return track;
                }
            }
            return null;
        }
    }

    public void show() {
        processOnAnchor(this.mHandler.obtainMessage(1));
    }

    /* access modifiers changed from: private */
    public void doShow() {
        this.mShowing = true;
        this.mVisibilityIsExplicit = true;
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack != null) {
            subtitleTrack.show();
        }
    }

    public void hide() {
        processOnAnchor(this.mHandler.obtainMessage(2));
    }

    /* access modifiers changed from: private */
    public void doHide() {
        this.mVisibilityIsExplicit = true;
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack != null) {
            subtitleTrack.hide();
        }
        this.mShowing = false;
    }

    public void registerRenderer(Renderer renderer) {
        synchronized (this.mRenderersLock) {
            if (!this.mRenderers.contains(renderer)) {
                this.mRenderers.add(renderer);
            }
        }
    }

    public boolean hasRendererFor(MediaFormat format) {
        synchronized (this.mRenderersLock) {
            Iterator<Renderer> it = this.mRenderers.iterator();
            while (it.hasNext()) {
                if (it.next().supports(format)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void setAnchor(Anchor anchor) {
        Anchor anchor2 = this.mAnchor;
        if (anchor2 != anchor) {
            if (anchor2 != null) {
                checkAnchorLooper();
                this.mAnchor.setSubtitleWidget((SubtitleTrack.RenderingWidget) null);
            }
            this.mAnchor = anchor;
            this.mHandler = null;
            if (anchor != null) {
                this.mHandler = new Handler(this.mAnchor.getSubtitleLooper(), this.mCallback);
                checkAnchorLooper();
                this.mAnchor.setSubtitleWidget(getRenderingWidget());
            }
        }
    }

    private void checkAnchorLooper() {
    }

    private void processOnAnchor(Message m) {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            this.mHandler.dispatchMessage(m);
        } else {
            this.mHandler.sendMessage(m);
        }
    }
}
