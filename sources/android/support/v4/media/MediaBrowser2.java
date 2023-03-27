package android.support.v4.media;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.MediaController2;
import android.util.Log;
import java.util.List;
import java.util.concurrent.Executor;

public class MediaBrowser2 extends MediaController2 {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MediaBrowser2";

    interface SupportLibraryImpl extends MediaController2.SupportLibraryImpl {
        void getChildren(String str, int i, int i2, Bundle bundle);

        void getItem(String str);

        void getLibraryRoot(Bundle bundle);

        void getSearchResult(String str, int i, int i2, Bundle bundle);

        void search(String str, Bundle bundle);

        void subscribe(String str, Bundle bundle);

        void unsubscribe(String str);
    }

    public static class BrowserCallback extends MediaController2.ControllerCallback {
        public void onGetLibraryRootDone(MediaBrowser2 browser, Bundle rootHints, String rootMediaId, Bundle rootExtra) {
        }

        public void onChildrenChanged(MediaBrowser2 browser, String parentId, int itemCount, Bundle extras) {
        }

        public void onGetChildrenDone(MediaBrowser2 browser, String parentId, int page, int pageSize, List<MediaItem2> list, Bundle extras) {
        }

        public void onGetItemDone(MediaBrowser2 browser, String mediaId, MediaItem2 result) {
        }

        public void onSearchResultChanged(MediaBrowser2 browser, String query, int itemCount, Bundle extras) {
        }

        public void onGetSearchResultDone(MediaBrowser2 browser, String query, int page, int pageSize, List<MediaItem2> list, Bundle extras) {
        }
    }

    public MediaBrowser2(Context context, SessionToken2 token, Executor executor, BrowserCallback callback) {
        super(context, token, executor, callback);
    }

    /* access modifiers changed from: package-private */
    public SupportLibraryImpl createImpl(Context context, SessionToken2 token, Executor executor, MediaController2.ControllerCallback callback) {
        if (token.isLegacySession()) {
            return new MediaBrowser2ImplLegacy(context, this, token, executor, (BrowserCallback) callback);
        }
        return new MediaBrowser2ImplBase(context, this, token, executor, (BrowserCallback) callback);
    }

    /* access modifiers changed from: package-private */
    public SupportLibraryImpl getImpl() {
        return (SupportLibraryImpl) super.getImpl();
    }

    /* access modifiers changed from: package-private */
    public BrowserCallback getCallback() {
        return (BrowserCallback) super.getCallback();
    }

    public void getLibraryRoot(Bundle extras) {
        getImpl().getLibraryRoot(extras);
    }

    public void subscribe(String parentId, Bundle extras) {
        getImpl().subscribe(parentId, extras);
    }

    public void unsubscribe(String parentId) {
        getImpl().unsubscribe(parentId);
    }

    public void getChildren(String parentId, int page, int pageSize, Bundle extras) {
        getImpl().getChildren(parentId, page, pageSize, extras);
    }

    public void getItem(String mediaId) {
        getImpl().getItem(mediaId);
    }

    public void search(String query, Bundle extras) {
        getImpl().search(query, extras);
    }

    public void getSearchResult(String query, int page, int pageSize, Bundle extras) {
        getImpl().getSearchResult(query, page, pageSize, extras);
    }
}
