package android.support.v4.media;

import android.support.v4.media.MediaLibraryService2;

class MediaLibraryService2ImplBase extends MediaSessionService2ImplBase {
    MediaLibraryService2ImplBase() {
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.IBinder onBind(android.content.Intent r3) {
        /*
            r2 = this;
            java.lang.String r0 = r3.getAction()
            int r1 = r0.hashCode()
            switch(r1) {
                case 901933117: goto L_0x0016;
                case 1665850838: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0020
        L_0x000c:
            java.lang.String r1 = "android.media.browse.MediaBrowserService"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 1
            goto L_0x0021
        L_0x0016:
            java.lang.String r1 = "android.media.MediaLibraryService2"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000b
            r0 = 0
            goto L_0x0021
        L_0x0020:
            r0 = -1
        L_0x0021:
            switch(r0) {
                case 0: goto L_0x0036;
                case 1: goto L_0x0029;
                default: goto L_0x0024;
            }
        L_0x0024:
            android.os.IBinder r0 = super.onBind(r3)
            return r0
        L_0x0029:
            android.support.v4.media.MediaLibraryService2$MediaLibrarySession r0 = r2.getSession()
            android.support.v4.media.MediaLibraryService2$MediaLibrarySession$SupportLibraryImpl r0 = r0.getImpl()
            android.os.IBinder r0 = r0.getLegacySessionBinder()
            return r0
        L_0x0036:
            android.support.v4.media.MediaLibraryService2$MediaLibrarySession r0 = r2.getSession()
            android.os.IBinder r0 = r0.getSessionBinder()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaLibraryService2ImplBase.onBind(android.content.Intent):android.os.IBinder");
    }

    public MediaLibraryService2.MediaLibrarySession getSession() {
        return (MediaLibraryService2.MediaLibrarySession) super.getSession();
    }

    public int getSessionType() {
        return 2;
    }
}
