package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.IMediaSession2;
import android.support.v4.media.SessionToken2;
import android.text.TextUtils;
import java.util.List;

final class SessionToken2ImplBase implements SessionToken2.SupportLibraryImpl {
    private final ComponentName mComponentName;
    private final IMediaSession2 mISession2;
    private final String mPackageName;
    private final String mServiceName;
    private final String mSessionId;
    private final int mType;
    private final int mUid;

    SessionToken2ImplBase(Context context, ComponentName serviceComponent) {
        this(context, serviceComponent, -1);
    }

    SessionToken2ImplBase(Context context, ComponentName serviceComponent, int uid) {
        if (serviceComponent != null) {
            this.mComponentName = serviceComponent;
            String packageName = serviceComponent.getPackageName();
            this.mPackageName = packageName;
            String className = serviceComponent.getClassName();
            this.mServiceName = className;
            PackageManager manager = context.getPackageManager();
            if (uid == -1) {
                try {
                    uid = manager.getApplicationInfo(packageName, 0).uid;
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalArgumentException("Cannot find package " + this.mPackageName);
                }
            }
            this.mUid = uid;
            String sessionId = getSessionIdFromService(manager, MediaLibraryService2.SERVICE_INTERFACE, serviceComponent);
            if (sessionId != null) {
                this.mSessionId = sessionId;
                this.mType = 2;
            } else {
                this.mSessionId = getSessionIdFromService(manager, MediaSessionService2.SERVICE_INTERFACE, serviceComponent);
                this.mType = 1;
            }
            if (this.mSessionId != null) {
                this.mISession2 = null;
                return;
            }
            throw new IllegalArgumentException("service " + className + " doesn't implement" + " session service nor library service. Use service's full name.");
        }
        throw new IllegalArgumentException("serviceComponent shouldn't be null");
    }

    SessionToken2ImplBase(int uid, int type, String packageName, String serviceName, String sessionId, IMediaSession2 iSession2) {
        this.mUid = uid;
        this.mType = type;
        this.mPackageName = packageName;
        this.mServiceName = serviceName;
        this.mComponentName = type == 0 ? null : new ComponentName(packageName, serviceName);
        this.mSessionId = sessionId;
        this.mISession2 = iSession2;
    }

    public int hashCode() {
        int i = this.mType;
        int i2 = this.mUid;
        int hashCode = this.mPackageName.hashCode();
        int hashCode2 = this.mSessionId.hashCode();
        String str = this.mServiceName;
        return i + ((i2 + ((hashCode + ((hashCode2 + ((str != null ? str.hashCode() : 0) * 31)) * 31)) * 31)) * 31);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SessionToken2ImplBase)) {
            return false;
        }
        SessionToken2ImplBase other = (SessionToken2ImplBase) obj;
        if (this.mUid != other.mUid || !TextUtils.equals(this.mPackageName, other.mPackageName) || !TextUtils.equals(this.mServiceName, other.mServiceName) || !TextUtils.equals(this.mSessionId, other.mSessionId) || this.mType != other.mType || !sessionBinderEquals(this.mISession2, other.mISession2)) {
            return false;
        }
        return true;
    }

    private boolean sessionBinderEquals(IMediaSession2 a, IMediaSession2 b) {
        if (a == null || b == null) {
            return a == b;
        }
        return a.asBinder().equals(b.asBinder());
    }

    public String toString() {
        return "SessionToken {pkg=" + this.mPackageName + " id=" + this.mSessionId + " type=" + this.mType + " service=" + this.mServiceName + " IMediaSession2=" + this.mISession2 + "}";
    }

    public int getUid() {
        return this.mUid;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getServiceName() {
        return this.mServiceName;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public String getSessionId() {
        return this.mSessionId;
    }

    public int getType() {
        return this.mType;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("android.media.token.uid", this.mUid);
        bundle.putString("android.media.token.package_name", this.mPackageName);
        bundle.putString("android.media.token.service_name", this.mServiceName);
        bundle.putString("android.media.token.session_id", this.mSessionId);
        bundle.putInt("android.media.token.type", this.mType);
        IMediaSession2 iMediaSession2 = this.mISession2;
        if (iMediaSession2 != null) {
            BundleCompat.putBinder(bundle, "android.media.token.session_binder", iMediaSession2.asBinder());
        }
        return bundle;
    }

    public Object getBinder() {
        IMediaSession2 iMediaSession2 = this.mISession2;
        if (iMediaSession2 == null) {
            return null;
        }
        return iMediaSession2.asBinder();
    }

    public static SessionToken2ImplBase fromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        int uid = bundle.getInt("android.media.token.uid");
        int type = bundle.getInt("android.media.token.type", -1);
        String packageName = bundle.getString("android.media.token.package_name");
        String serviceName = bundle.getString("android.media.token.service_name");
        String sessionId = bundle.getString("android.media.token.session_id");
        IMediaSession2 iSession2 = IMediaSession2.Stub.asInterface(BundleCompat.getBinder(bundle, "android.media.token.session_binder"));
        switch (type) {
            case 0:
                if (iSession2 == null) {
                    throw new IllegalArgumentException("Unexpected token for session, binder=" + iSession2);
                }
                break;
            case 1:
            case 2:
                if (TextUtils.isEmpty(serviceName)) {
                    throw new IllegalArgumentException("Session service needs service name");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid type");
        }
        if (!TextUtils.isEmpty(packageName) && sessionId != null) {
            return new SessionToken2ImplBase(uid, type, packageName, serviceName, sessionId, iSession2);
        }
        throw new IllegalArgumentException("Package name nor ID cannot be null.");
    }

    public static String getSessionId(ResolveInfo resolveInfo) {
        if (resolveInfo == null || resolveInfo.serviceInfo == null) {
            return null;
        }
        if (resolveInfo.serviceInfo.metaData == null) {
            return "";
        }
        return resolveInfo.serviceInfo.metaData.getString(MediaSessionService2.SERVICE_META_DATA, "");
    }

    private static String getSessionIdFromService(PackageManager manager, String serviceInterface, ComponentName serviceComponent) {
        Intent serviceIntent = new Intent(serviceInterface);
        serviceIntent.setPackage(serviceComponent.getPackageName());
        List<ResolveInfo> list = manager.queryIntentServices(serviceIntent, 128);
        if (list == null) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo resolveInfo = list.get(i);
            if (resolveInfo != null && resolveInfo.serviceInfo != null && TextUtils.equals(resolveInfo.serviceInfo.name, serviceComponent.getClassName())) {
                return getSessionId(resolveInfo);
            }
        }
        return null;
    }
}
