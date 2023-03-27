package android.support.v4.media;

import android.content.Context;
import android.net.Uri;
import android.support.v4.util.Preconditions;
import java.io.FileDescriptor;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataSourceDesc {
    public static final long FD_LENGTH_UNKNOWN = 576460752303423487L;
    private static final long LONG_MAX = 576460752303423487L;
    public static final long POSITION_UNKNOWN = 576460752303423487L;
    public static final int TYPE_CALLBACK = 1;
    public static final int TYPE_FD = 2;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_URI = 3;
    /* access modifiers changed from: private */
    public long mEndPositionMs;
    /* access modifiers changed from: private */
    public FileDescriptor mFD;
    /* access modifiers changed from: private */
    public long mFDLength;
    /* access modifiers changed from: private */
    public long mFDOffset;
    /* access modifiers changed from: private */
    public Media2DataSource mMedia2DataSource;
    /* access modifiers changed from: private */
    public String mMediaId;
    /* access modifiers changed from: private */
    public long mStartPositionMs;
    /* access modifiers changed from: private */
    public int mType;
    /* access modifiers changed from: private */
    public Uri mUri;
    /* access modifiers changed from: private */
    public Context mUriContext;
    /* access modifiers changed from: private */
    public List<HttpCookie> mUriCookies;
    /* access modifiers changed from: private */
    public Map<String, String> mUriHeader;

    private DataSourceDesc() {
        this.mType = 0;
        this.mFDOffset = 0;
        this.mFDLength = 576460752303423487L;
        this.mStartPositionMs = 0;
        this.mEndPositionMs = 576460752303423487L;
    }

    public String getMediaId() {
        return this.mMediaId;
    }

    public long getStartPosition() {
        return this.mStartPositionMs;
    }

    public long getEndPosition() {
        return this.mEndPositionMs;
    }

    public int getType() {
        return this.mType;
    }

    public Media2DataSource getMedia2DataSource() {
        return this.mMedia2DataSource;
    }

    public FileDescriptor getFileDescriptor() {
        return this.mFD;
    }

    public long getFileDescriptorOffset() {
        return this.mFDOffset;
    }

    public long getFileDescriptorLength() {
        return this.mFDLength;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public Map<String, String> getUriHeaders() {
        if (this.mUriHeader == null) {
            return null;
        }
        return new HashMap(this.mUriHeader);
    }

    public List<HttpCookie> getUriCookies() {
        if (this.mUriCookies == null) {
            return null;
        }
        return new ArrayList(this.mUriCookies);
    }

    public Context getUriContext() {
        return this.mUriContext;
    }

    public static class Builder {
        private long mEndPositionMs = 576460752303423487L;
        private FileDescriptor mFD;
        private long mFDLength = 576460752303423487L;
        private long mFDOffset = 0;
        private Media2DataSource mMedia2DataSource;
        private String mMediaId;
        private long mStartPositionMs = 0;
        private int mType = 0;
        private Uri mUri;
        private Context mUriContext;
        private List<HttpCookie> mUriCookies;
        private Map<String, String> mUriHeader;

        public Builder() {
        }

        public Builder(DataSourceDesc dsd) {
            this.mType = dsd.mType;
            this.mMedia2DataSource = dsd.mMedia2DataSource;
            this.mFD = dsd.mFD;
            this.mFDOffset = dsd.mFDOffset;
            this.mFDLength = dsd.mFDLength;
            this.mUri = dsd.mUri;
            this.mUriHeader = dsd.mUriHeader;
            this.mUriCookies = dsd.mUriCookies;
            this.mUriContext = dsd.mUriContext;
            this.mMediaId = dsd.mMediaId;
            this.mStartPositionMs = dsd.mStartPositionMs;
            this.mEndPositionMs = dsd.mEndPositionMs;
        }

        public DataSourceDesc build() {
            int i = this.mType;
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalStateException("Illegal type: " + this.mType);
            } else if (this.mStartPositionMs <= this.mEndPositionMs) {
                DataSourceDesc dsd = new DataSourceDesc();
                int unused = dsd.mType = this.mType;
                Media2DataSource unused2 = dsd.mMedia2DataSource = this.mMedia2DataSource;
                FileDescriptor unused3 = dsd.mFD = this.mFD;
                long unused4 = dsd.mFDOffset = this.mFDOffset;
                long unused5 = dsd.mFDLength = this.mFDLength;
                Uri unused6 = dsd.mUri = this.mUri;
                Map unused7 = dsd.mUriHeader = this.mUriHeader;
                List unused8 = dsd.mUriCookies = this.mUriCookies;
                Context unused9 = dsd.mUriContext = this.mUriContext;
                String unused10 = dsd.mMediaId = this.mMediaId;
                long unused11 = dsd.mStartPositionMs = this.mStartPositionMs;
                long unused12 = dsd.mEndPositionMs = this.mEndPositionMs;
                return dsd;
            } else {
                throw new IllegalStateException("Illegal start/end position: " + this.mStartPositionMs + " : " + this.mEndPositionMs);
            }
        }

        public Builder setMediaId(String mediaId) {
            this.mMediaId = mediaId;
            return this;
        }

        public Builder setStartPosition(long position) {
            if (position < 0) {
                position = 0;
            }
            this.mStartPositionMs = position;
            return this;
        }

        public Builder setEndPosition(long position) {
            if (position < 0) {
                position = 576460752303423487L;
            }
            this.mEndPositionMs = position;
            return this;
        }

        public Builder setDataSource(Media2DataSource m2ds) {
            Preconditions.checkNotNull(m2ds);
            resetDataSource();
            this.mType = 1;
            this.mMedia2DataSource = m2ds;
            return this;
        }

        public Builder setDataSource(FileDescriptor fd) {
            Preconditions.checkNotNull(fd);
            resetDataSource();
            this.mType = 2;
            this.mFD = fd;
            return this;
        }

        public Builder setDataSource(FileDescriptor fd, long offset, long length) {
            Preconditions.checkNotNull(fd);
            if (offset < 0) {
                offset = 0;
            }
            if (length < 0) {
                length = 576460752303423487L;
            }
            resetDataSource();
            this.mType = 2;
            this.mFD = fd;
            this.mFDOffset = offset;
            this.mFDLength = length;
            return this;
        }

        public Builder setDataSource(Context context, Uri uri) {
            Preconditions.checkNotNull(context, "context cannot be null");
            Preconditions.checkNotNull(uri, "uri cannot be null");
            resetDataSource();
            this.mType = 3;
            this.mUri = uri;
            this.mUriContext = context;
            return this;
        }

        public Builder setDataSource(Context context, Uri uri, Map<String, String> headers, List<HttpCookie> cookies) {
            CookieHandler cookieHandler;
            Preconditions.checkNotNull(context, "context cannot be null");
            Preconditions.checkNotNull(uri);
            if (cookies == null || (cookieHandler = CookieHandler.getDefault()) == null || (cookieHandler instanceof CookieManager)) {
                resetDataSource();
                this.mType = 3;
                this.mUri = uri;
                if (headers != null) {
                    this.mUriHeader = new HashMap(headers);
                }
                if (cookies != null) {
                    this.mUriCookies = new ArrayList(cookies);
                }
                this.mUriContext = context;
                return this;
            }
            throw new IllegalArgumentException("The cookie handler has to be of CookieManager type when cookies are provided.");
        }

        private void resetDataSource() {
            this.mType = 0;
            this.mMedia2DataSource = null;
            this.mFD = null;
            this.mFDOffset = 0;
            this.mFDLength = 576460752303423487L;
            this.mUri = null;
            this.mUriHeader = null;
            this.mUriCookies = null;
            this.mUriContext = null;
        }
    }
}
