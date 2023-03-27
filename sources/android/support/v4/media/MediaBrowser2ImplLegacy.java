package android.support.v4.media;

import android.content.Context;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.support.v4.media.MediaBrowser2;
import android.support.v4.media.MediaBrowserCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

class MediaBrowser2ImplLegacy extends MediaController2ImplLegacy implements MediaBrowser2.SupportLibraryImpl {
    public static final String EXTRA_ITEM_COUNT = "android.media.browse.extra.ITEM_COUNT";
    /* access modifiers changed from: private */
    public final HashMap<Bundle, MediaBrowserCompat> mBrowserCompats = new HashMap<>();
    private final HashMap<String, List<SubscribeCallback>> mSubscribeCallbacks = new HashMap<>();

    MediaBrowser2ImplLegacy(Context context, MediaBrowser2 instance, SessionToken2 token, Executor executor, MediaBrowser2.BrowserCallback callback) {
        super(context, instance, token, executor, callback);
    }

    public MediaBrowser2 getInstance() {
        return (MediaBrowser2) super.getInstance();
    }

    public void close() {
        synchronized (this.mLock) {
            for (MediaBrowserCompat browser : this.mBrowserCompats.values()) {
                browser.disconnect();
            }
            this.mBrowserCompats.clear();
            super.close();
        }
    }

    public void getLibraryRoot(final Bundle extras) {
        final MediaBrowserCompat browser = getBrowserCompat(extras);
        if (browser != null) {
            getCallbackExecutor().execute(new Runnable() {
                public void run() {
                    MediaBrowser2ImplLegacy.this.getCallback().onGetLibraryRootDone(MediaBrowser2ImplLegacy.this.getInstance(), extras, browser.getRoot(), browser.getExtras());
                }
            });
        } else {
            getCallbackExecutor().execute(new Runnable() {
                public void run() {
                    MediaBrowserCompat newBrowser = new MediaBrowserCompat(MediaBrowser2ImplLegacy.this.getContext(), MediaBrowser2ImplLegacy.this.getSessionToken().getComponentName(), new GetLibraryRootCallback(extras), extras);
                    synchronized (MediaBrowser2ImplLegacy.this.mLock) {
                        MediaBrowser2ImplLegacy.this.mBrowserCompats.put(extras, newBrowser);
                    }
                    newBrowser.connect();
                }
            });
        }
    }

    public void subscribe(String parentId, Bundle extras) {
        if (parentId != null) {
            MediaBrowserCompat browser = getBrowserCompat();
            if (browser != null) {
                SubscribeCallback callback = new SubscribeCallback();
                synchronized (this.mLock) {
                    List<SubscribeCallback> list = this.mSubscribeCallbacks.get(parentId);
                    if (list == null) {
                        list = new ArrayList<>();
                        this.mSubscribeCallbacks.put(parentId, list);
                    }
                    list.add(callback);
                }
                Bundle options = new Bundle();
                options.putBundle("android.support.v4.media.argument.EXTRAS", extras);
                browser.subscribe(parentId, options, callback);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("parentId shouldn't be null");
    }

    public void unsubscribe(String parentId) {
        if (parentId != null) {
            MediaBrowserCompat browser = getBrowserCompat();
            if (browser != null) {
                synchronized (this.mLock) {
                    List<SubscribeCallback> list = this.mSubscribeCallbacks.get(parentId);
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            browser.unsubscribe(parentId, list.get(i));
                        }
                        return;
                    }
                    return;
                }
            }
            return;
        }
        throw new IllegalArgumentException("parentId shouldn't be null");
    }

    public void getChildren(String parentId, int page, int pageSize, Bundle extras) {
        if (parentId == null) {
            throw new IllegalArgumentException("parentId shouldn't be null");
        } else if (page < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Neither page nor pageSize should be less than 1");
        } else {
            MediaBrowserCompat browser = getBrowserCompat();
            if (browser != null) {
                Bundle options = MediaUtils2.createBundle(extras);
                options.putInt(MediaBrowserCompat.EXTRA_PAGE, page);
                options.putInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, pageSize);
                browser.subscribe(parentId, options, new GetChildrenCallback(parentId, page, pageSize));
            }
        }
    }

    public void getItem(final String mediaId) {
        MediaBrowserCompat browser = getBrowserCompat();
        if (browser != null) {
            browser.getItem(mediaId, new MediaBrowserCompat.ItemCallback() {
                public void onItemLoaded(final MediaBrowserCompat.MediaItem item) {
                    MediaBrowser2ImplLegacy.this.getCallbackExecutor().execute(new Runnable() {
                        public void run() {
                            MediaBrowser2ImplLegacy.this.getCallback().onGetItemDone(MediaBrowser2ImplLegacy.this.getInstance(), mediaId, MediaUtils2.convertToMediaItem2(item));
                        }
                    });
                }

                public void onError(String itemId) {
                    MediaBrowser2ImplLegacy.this.getCallbackExecutor().execute(new Runnable() {
                        public void run() {
                            MediaBrowser2ImplLegacy.this.getCallback().onGetItemDone(MediaBrowser2ImplLegacy.this.getInstance(), mediaId, (MediaItem2) null);
                        }
                    });
                }
            });
        }
    }

    public void search(String query, Bundle extras) {
        MediaBrowserCompat browser = getBrowserCompat();
        if (browser != null) {
            browser.search(query, extras, new MediaBrowserCompat.SearchCallback() {
                public void onSearchResult(final String query, final Bundle extras, final List<MediaBrowserCompat.MediaItem> items) {
                    MediaBrowser2ImplLegacy.this.getCallbackExecutor().execute(new Runnable() {
                        public void run() {
                            MediaBrowser2ImplLegacy.this.getCallback().onSearchResultChanged(MediaBrowser2ImplLegacy.this.getInstance(), query, items.size(), extras);
                        }
                    });
                }

                public void onError(String query, Bundle extras) {
                }
            });
        }
    }

    public void getSearchResult(String query, final int page, final int pageSize, final Bundle extras) {
        MediaBrowserCompat browser = getBrowserCompat();
        if (browser != null) {
            Bundle options = MediaUtils2.createBundle(extras);
            options.putInt(MediaBrowserCompat.EXTRA_PAGE, page);
            options.putInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, pageSize);
            browser.search(query, options, new MediaBrowserCompat.SearchCallback() {
                public void onSearchResult(final String query, Bundle extrasSent, final List<MediaBrowserCompat.MediaItem> items) {
                    MediaBrowser2ImplLegacy.this.getCallbackExecutor().execute(new Runnable() {
                        public void run() {
                            List<MediaItem2> item2List = MediaUtils2.convertMediaItemListToMediaItem2List(items);
                            MediaBrowser2ImplLegacy.this.getCallback().onGetSearchResultDone(MediaBrowser2ImplLegacy.this.getInstance(), query, page, pageSize, item2List, extras);
                        }
                    });
                }

                public void onError(final String query, Bundle extrasSent) {
                    MediaBrowser2ImplLegacy.this.getCallbackExecutor().execute(new Runnable() {
                        public void run() {
                            MediaBrowser2ImplLegacy.this.getCallback().onGetSearchResultDone(MediaBrowser2ImplLegacy.this.getInstance(), query, page, pageSize, (List<MediaItem2>) null, extras);
                        }
                    });
                }
            });
        }
    }

    public MediaBrowser2.BrowserCallback getCallback() {
        return (MediaBrowser2.BrowserCallback) super.getCallback();
    }

    private MediaBrowserCompat getBrowserCompat(Bundle extras) {
        MediaBrowserCompat mediaBrowserCompat;
        synchronized (this.mLock) {
            mediaBrowserCompat = this.mBrowserCompats.get(extras);
        }
        return mediaBrowserCompat;
    }

    /* access modifiers changed from: private */
    public Bundle getExtrasWithoutPagination(Bundle extras) {
        if (extras == null) {
            return null;
        }
        extras.setClassLoader(getContext().getClassLoader());
        try {
            extras.remove(MediaBrowserCompat.EXTRA_PAGE);
            extras.remove(MediaBrowserCompat.EXTRA_PAGE_SIZE);
        } catch (BadParcelableException e) {
        }
        return extras;
    }

    private class GetLibraryRootCallback extends MediaBrowserCompat.ConnectionCallback {
        /* access modifiers changed from: private */
        public final Bundle mExtras;

        GetLibraryRootCallback(Bundle extras) {
            this.mExtras = extras;
        }

        public void onConnected() {
            MediaBrowser2ImplLegacy.this.getCallbackExecutor().execute(new Runnable() {
                /* JADX WARNING: Code restructure failed: missing block: B:10:0x0021, code lost:
                    r6.this$1.this$0.getCallback().onGetLibraryRootDone(r6.this$1.this$0.getInstance(), android.support.v4.media.MediaBrowser2ImplLegacy.GetLibraryRootCallback.access$200(r6.this$1), r1.getRoot(), r1.getExtras());
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:11:0x0042, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
                    if (r1 != null) goto L_0x0021;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:9:0x0020, code lost:
                    return;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r6 = this;
                        android.support.v4.media.MediaBrowser2ImplLegacy$GetLibraryRootCallback r0 = android.support.v4.media.MediaBrowser2ImplLegacy.GetLibraryRootCallback.this
                        android.support.v4.media.MediaBrowser2ImplLegacy r0 = android.support.v4.media.MediaBrowser2ImplLegacy.this
                        java.lang.Object r0 = r0.mLock
                        monitor-enter(r0)
                        r1 = 0
                        android.support.v4.media.MediaBrowser2ImplLegacy$GetLibraryRootCallback r2 = android.support.v4.media.MediaBrowser2ImplLegacy.GetLibraryRootCallback.this     // Catch:{ all -> 0x0043 }
                        android.support.v4.media.MediaBrowser2ImplLegacy r2 = android.support.v4.media.MediaBrowser2ImplLegacy.this     // Catch:{ all -> 0x0043 }
                        java.util.HashMap r2 = r2.mBrowserCompats     // Catch:{ all -> 0x0043 }
                        android.support.v4.media.MediaBrowser2ImplLegacy$GetLibraryRootCallback r3 = android.support.v4.media.MediaBrowser2ImplLegacy.GetLibraryRootCallback.this     // Catch:{ all -> 0x0043 }
                        android.os.Bundle r3 = r3.mExtras     // Catch:{ all -> 0x0043 }
                        java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0043 }
                        android.support.v4.media.MediaBrowserCompat r2 = (android.support.v4.media.MediaBrowserCompat) r2     // Catch:{ all -> 0x0043 }
                        r1 = r2
                        monitor-exit(r0)     // Catch:{ all -> 0x0046 }
                        if (r1 != 0) goto L_0x0021
                        return
                    L_0x0021:
                        android.support.v4.media.MediaBrowser2ImplLegacy$GetLibraryRootCallback r0 = android.support.v4.media.MediaBrowser2ImplLegacy.GetLibraryRootCallback.this
                        android.support.v4.media.MediaBrowser2ImplLegacy r0 = android.support.v4.media.MediaBrowser2ImplLegacy.this
                        android.support.v4.media.MediaBrowser2$BrowserCallback r0 = r0.getCallback()
                        android.support.v4.media.MediaBrowser2ImplLegacy$GetLibraryRootCallback r2 = android.support.v4.media.MediaBrowser2ImplLegacy.GetLibraryRootCallback.this
                        android.support.v4.media.MediaBrowser2ImplLegacy r2 = android.support.v4.media.MediaBrowser2ImplLegacy.this
                        android.support.v4.media.MediaBrowser2 r2 = r2.getInstance()
                        android.support.v4.media.MediaBrowser2ImplLegacy$GetLibraryRootCallback r3 = android.support.v4.media.MediaBrowser2ImplLegacy.GetLibraryRootCallback.this
                        android.os.Bundle r3 = r3.mExtras
                        java.lang.String r4 = r1.getRoot()
                        android.os.Bundle r5 = r1.getExtras()
                        r0.onGetLibraryRootDone(r2, r3, r4, r5)
                        return
                    L_0x0043:
                        r2 = move-exception
                    L_0x0044:
                        monitor-exit(r0)     // Catch:{ all -> 0x0046 }
                        throw r2
                    L_0x0046:
                        r2 = move-exception
                        goto L_0x0044
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaBrowser2ImplLegacy.GetLibraryRootCallback.AnonymousClass1.run():void");
                }
            });
        }

        public void onConnectionSuspended() {
            MediaBrowser2ImplLegacy.this.close();
        }

        public void onConnectionFailed() {
            MediaBrowser2ImplLegacy.this.close();
        }
    }

    private class SubscribeCallback extends MediaBrowserCompat.SubscriptionCallback {
        private SubscribeCallback() {
        }

        public void onError(String parentId) {
            onChildrenLoaded(parentId, (List<MediaBrowserCompat.MediaItem>) null, (Bundle) null);
        }

        public void onError(String parentId, Bundle options) {
            onChildrenLoaded(parentId, (List<MediaBrowserCompat.MediaItem>) null, options);
        }

        public void onChildrenLoaded(String parentId, List<MediaBrowserCompat.MediaItem> children) {
            onChildrenLoaded(parentId, children, (Bundle) null);
        }

        public void onChildrenLoaded(final String parentId, List<MediaBrowserCompat.MediaItem> children, Bundle options) {
            final int itemCount;
            if (options != null && options.containsKey(MediaBrowser2ImplLegacy.EXTRA_ITEM_COUNT)) {
                itemCount = options.getInt(MediaBrowser2ImplLegacy.EXTRA_ITEM_COUNT);
            } else if (children != null) {
                itemCount = children.size();
            } else {
                return;
            }
            final Bundle notifyChildrenChangedOptions = MediaBrowser2ImplLegacy.this.getBrowserCompat().getNotifyChildrenChangedOptions();
            MediaBrowser2ImplLegacy.this.getCallbackExecutor().execute(new Runnable() {
                public void run() {
                    MediaBrowser2ImplLegacy.this.getCallback().onChildrenChanged(MediaBrowser2ImplLegacy.this.getInstance(), parentId, itemCount, notifyChildrenChangedOptions);
                }
            });
        }
    }

    private class GetChildrenCallback extends MediaBrowserCompat.SubscriptionCallback {
        /* access modifiers changed from: private */
        public final int mPage;
        /* access modifiers changed from: private */
        public final int mPageSize;
        /* access modifiers changed from: private */
        public final String mParentId;

        GetChildrenCallback(String parentId, int page, int pageSize) {
            this.mParentId = parentId;
            this.mPage = page;
            this.mPageSize = pageSize;
        }

        public void onError(String parentId) {
            onChildrenLoaded(parentId, (List<MediaBrowserCompat.MediaItem>) null, (Bundle) null);
        }

        public void onError(String parentId, Bundle options) {
            onChildrenLoaded(parentId, (List<MediaBrowserCompat.MediaItem>) null, options);
        }

        public void onChildrenLoaded(String parentId, List<MediaBrowserCompat.MediaItem> children) {
            onChildrenLoaded(parentId, children, (Bundle) null);
        }

        public void onChildrenLoaded(final String parentId, List<MediaBrowserCompat.MediaItem> children, Bundle options) {
            final List<MediaItem2> items;
            if (children == null) {
                items = null;
            } else {
                items = new ArrayList<>();
                for (int i = 0; i < children.size(); i++) {
                    items.add(MediaUtils2.convertToMediaItem2(children.get(i)));
                }
            }
            final Bundle extras = MediaBrowser2ImplLegacy.this.getExtrasWithoutPagination(options);
            MediaBrowser2ImplLegacy.this.getCallbackExecutor().execute(new Runnable() {
                public void run() {
                    MediaBrowserCompat browser = MediaBrowser2ImplLegacy.this.getBrowserCompat();
                    if (browser != null) {
                        MediaBrowser2ImplLegacy.this.getCallback().onGetChildrenDone(MediaBrowser2ImplLegacy.this.getInstance(), parentId, GetChildrenCallback.this.mPage, GetChildrenCallback.this.mPageSize, items, extras);
                        browser.unsubscribe(GetChildrenCallback.this.mParentId, GetChildrenCallback.this);
                    }
                }
            });
        }
    }
}
