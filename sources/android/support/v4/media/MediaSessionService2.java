package android.support.v4.media;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public abstract class MediaSessionService2 extends Service {
    public static final String SERVICE_INTERFACE = "android.media.MediaSessionService2";
    public static final String SERVICE_META_DATA = "android.media.session";
    private final SupportLibraryImpl mImpl = createImpl();

    interface SupportLibraryImpl {
        MediaSession2 getSession();

        int getSessionType();

        IBinder onBind(Intent intent);

        void onCreate(MediaSessionService2 mediaSessionService2);

        MediaNotification onUpdateNotification();
    }

    public abstract MediaSession2 onCreateSession(String str);

    /* access modifiers changed from: package-private */
    public SupportLibraryImpl createImpl() {
        return new MediaSessionService2ImplBase();
    }

    public void onCreate() {
        super.onCreate();
        this.mImpl.onCreate(this);
    }

    public MediaNotification onUpdateNotification() {
        return this.mImpl.onUpdateNotification();
    }

    public final MediaSession2 getSession() {
        return this.mImpl.getSession();
    }

    public IBinder onBind(Intent intent) {
        return this.mImpl.onBind(intent);
    }

    public static class MediaNotification {
        private final Notification mNotification;
        private final int mNotificationId;

        public MediaNotification(int notificationId, Notification notification) {
            if (notification != null) {
                this.mNotificationId = notificationId;
                this.mNotification = notification;
                return;
            }
            throw new IllegalArgumentException("notification shouldn't be null");
        }

        public int getNotificationId() {
            return this.mNotificationId;
        }

        public Notification getNotification() {
            return this.mNotification;
        }
    }
}
