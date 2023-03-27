package android.support.v4.media;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaSession2;
import android.support.v4.media.MediaSessionService2;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class MediaLibraryService2 extends MediaSessionService2 {
    public static final String SERVICE_INTERFACE = "android.media.MediaLibraryService2";

    public abstract MediaLibrarySession onCreateSession(String str);

    public static final class MediaLibrarySession extends MediaSession2 {

        interface SupportLibraryImpl extends MediaSession2.SupportLibraryImpl {
            MediaLibrarySessionCallback getCallback();

            MediaLibrarySession getInstance();

            IBinder getLegacySessionBinder();

            void notifyChildrenChanged(MediaSession2.ControllerInfo controllerInfo, String str, int i, Bundle bundle);

            void notifyChildrenChanged(String str, int i, Bundle bundle);

            void notifySearchResultChanged(MediaSession2.ControllerInfo controllerInfo, String str, int i, Bundle bundle);

            void onGetChildrenOnExecutor(MediaSession2.ControllerInfo controllerInfo, String str, int i, int i2, Bundle bundle);

            void onGetItemOnExecutor(MediaSession2.ControllerInfo controllerInfo, String str);

            void onGetLibraryRootOnExecutor(MediaSession2.ControllerInfo controllerInfo, Bundle bundle);

            void onGetSearchResultOnExecutor(MediaSession2.ControllerInfo controllerInfo, String str, int i, int i2, Bundle bundle);

            void onSearchOnExecutor(MediaSession2.ControllerInfo controllerInfo, String str, Bundle bundle);

            void onSubscribeOnExecutor(MediaSession2.ControllerInfo controllerInfo, String str, Bundle bundle);

            void onUnsubscribeOnExecutor(MediaSession2.ControllerInfo controllerInfo, String str);
        }

        public static class MediaLibrarySessionCallback extends MediaSession2.SessionCallback {
            public LibraryRoot onGetLibraryRoot(MediaLibrarySession session, MediaSession2.ControllerInfo controller, Bundle rootHints) {
                return null;
            }

            public MediaItem2 onGetItem(MediaLibrarySession session, MediaSession2.ControllerInfo controller, String mediaId) {
                return null;
            }

            public List<MediaItem2> onGetChildren(MediaLibrarySession session, MediaSession2.ControllerInfo controller, String parentId, int page, int pageSize, Bundle extras) {
                return null;
            }

            public void onSubscribe(MediaLibrarySession session, MediaSession2.ControllerInfo controller, String parentId, Bundle extras) {
            }

            public void onUnsubscribe(MediaLibrarySession session, MediaSession2.ControllerInfo controller, String parentId) {
            }

            public void onSearch(MediaLibrarySession session, MediaSession2.ControllerInfo controller, String query, Bundle extras) {
            }

            public List<MediaItem2> onGetSearchResult(MediaLibrarySession session, MediaSession2.ControllerInfo controller, String query, int page, int pageSize, Bundle extras) {
                return null;
            }
        }

        public static final class Builder extends MediaSession2.BuilderBase<MediaLibrarySession, Builder, MediaLibrarySessionCallback> {
            public Builder(MediaLibraryService2 service, Executor callbackExecutor, MediaLibrarySessionCallback callback) {
                super(service);
                setSessionCallback(callbackExecutor, callback);
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

            public MediaLibrarySession build() {
                if (this.mCallbackExecutor == null) {
                    this.mCallbackExecutor = new MediaSession2.MainHandlerExecutor(this.mContext);
                }
                if (this.mCallback == null) {
                    this.mCallback = new MediaLibrarySessionCallback() {
                    };
                }
                return new MediaLibrarySession(this.mContext, this.mId, this.mPlayer, this.mPlaylistAgent, this.mVolumeProvider, this.mSessionActivity, this.mCallbackExecutor, this.mCallback);
            }
        }

        MediaLibrarySession(Context context, String id, BaseMediaPlayer player, MediaPlaylistAgent playlistAgent, VolumeProviderCompat volumeProvider, PendingIntent sessionActivity, Executor callbackExecutor, MediaSession2.SessionCallback callback) {
            super(context, id, player, playlistAgent, volumeProvider, sessionActivity, callbackExecutor, callback);
        }

        /* access modifiers changed from: package-private */
        public SupportLibraryImpl createImpl(Context context, String id, BaseMediaPlayer player, MediaPlaylistAgent playlistAgent, VolumeProviderCompat volumeProvider, PendingIntent sessionActivity, Executor callbackExecutor, MediaSession2.SessionCallback callback) {
            return new MediaLibrarySessionImplBase(this, context, id, player, playlistAgent, volumeProvider, sessionActivity, callbackExecutor, callback);
        }

        /* access modifiers changed from: package-private */
        public SupportLibraryImpl getImpl() {
            return (SupportLibraryImpl) super.getImpl();
        }

        public void notifyChildrenChanged(MediaSession2.ControllerInfo controller, String parentId, int itemCount, Bundle extras) {
            getImpl().notifyChildrenChanged(controller, parentId, itemCount, extras);
        }

        public void notifyChildrenChanged(String parentId, int itemCount, Bundle extras) {
            getImpl().notifyChildrenChanged(parentId, itemCount, extras);
        }

        public void notifySearchResultChanged(MediaSession2.ControllerInfo controller, String query, int itemCount, Bundle extras) {
            getImpl().notifySearchResultChanged(controller, query, itemCount, extras);
        }

        /* access modifiers changed from: package-private */
        public MediaLibrarySessionCallback getCallback() {
            return (MediaLibrarySessionCallback) super.getCallback();
        }
    }

    /* access modifiers changed from: package-private */
    public MediaSessionService2.SupportLibraryImpl createImpl() {
        return new MediaLibraryService2ImplBase();
    }

    public void onCreate() {
        super.onCreate();
        if (!(getSession() instanceof MediaLibrarySession)) {
            throw new RuntimeException("Expected MediaLibrarySession, but returned MediaSession2");
        }
    }

    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    public static final class LibraryRoot {
        public static final String EXTRA_OFFLINE = "android.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.media.extra.SUGGESTED";
        private final Bundle mExtras;
        private final String mRootId;

        public LibraryRoot(String rootId, Bundle extras) {
            if (rootId != null) {
                this.mRootId = rootId;
                this.mExtras = extras;
                return;
            }
            throw new IllegalArgumentException("rootId shouldn't be null");
        }

        public String getRootId() {
            return this.mRootId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }
    }
}
