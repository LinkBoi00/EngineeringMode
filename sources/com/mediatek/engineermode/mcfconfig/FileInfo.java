package com.mediatek.engineermode.mcfconfig;

import android.net.Uri;
import java.io.File;

public class FileInfo {
    public static final int FILENAME_MAX_LENGTH = 255;
    private static final String TAG = "McfConfig/FileInfo";
    private final String mAbsolutePath;
    private final File mFile;
    private String mFileSizeStr;
    private boolean mIsChecked;
    private final boolean mIsDir;
    private String mLastAccessPath;
    private long mLastModifiedTime;
    private final String mName;
    private String mParentPath;
    private long mSize;

    public FileInfo(File file, long size) throws IllegalArgumentException {
        this.mParentPath = null;
        this.mFileSizeStr = null;
        this.mLastModifiedTime = -1;
        this.mSize = -1;
        this.mIsChecked = false;
        if (file != null) {
            this.mFile = file;
            this.mName = file.getName();
            this.mAbsolutePath = file.getAbsolutePath();
            this.mLastModifiedTime = file.lastModified();
            boolean isDirectory = file.isDirectory();
            this.mIsDir = isDirectory;
            if (size > -1) {
                this.mSize = size;
            } else if (!isDirectory) {
                this.mSize = file.length();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public FileInfo(String absPath, long size) {
        this.mParentPath = null;
        this.mFileSizeStr = null;
        this.mLastModifiedTime = -1;
        this.mSize = -1;
        boolean z = false;
        this.mIsChecked = false;
        if (absPath != null) {
            this.mAbsolutePath = absPath;
            File file = new File(absPath);
            this.mFile = file;
            this.mName = FileUtils.getFileName(absPath);
            this.mLastModifiedTime = file.lastModified();
            this.mSize = size > 0 ? size : 0;
            this.mIsDir = absPath.indexOf(46) < 0 ? true : z;
            return;
        }
        throw new IllegalArgumentException();
    }

    public FileInfo(String absPath, boolean isChecked) {
        this.mParentPath = null;
        this.mFileSizeStr = null;
        this.mLastModifiedTime = -1;
        this.mSize = -1;
        this.mIsChecked = false;
        if (absPath == null || absPath == "") {
            this.mAbsolutePath = null;
            this.mFile = null;
            this.mName = absPath;
            this.mLastModifiedTime = -1;
        } else {
            this.mAbsolutePath = absPath;
            File file = new File(absPath);
            this.mFile = file;
            this.mName = FileUtils.getFileName(absPath);
            this.mLastModifiedTime = file.lastModified();
        }
        this.mIsChecked = isChecked;
        this.mIsDir = false;
    }

    public String getFileParentPath() {
        if (this.mParentPath == null) {
            this.mParentPath = FileUtils.getFilePath(this.mAbsolutePath);
        }
        return this.mParentPath;
    }

    public String getFileName() {
        return this.mName;
    }

    public long getFileSize() {
        return this.mSize;
    }

    public boolean isDir() {
        return this.mIsDir;
    }

    public boolean isChecked() {
        return this.mIsChecked;
    }

    public void setChecked(boolean isChecked) {
        this.mIsChecked = isChecked;
    }

    public String getFileSizeStr() {
        if (this.mFileSizeStr == null) {
            this.mFileSizeStr = FileUtils.sizeToString(this.mSize);
        }
        return this.mFileSizeStr;
    }

    public String getFileAbsolutePath() {
        return this.mAbsolutePath;
    }

    public File getFile() {
        return this.mFile;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0095, code lost:
        if (r10 != null) goto L_0x0097;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0097, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00b2, code lost:
        if (r10 == null) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00b5, code lost:
        return r12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.net.Uri getItemContentUri(android.content.Context r15) {
        /*
            r14 = this;
            java.lang.String r0 = "_id"
            java.lang.String[] r1 = new java.lang.String[]{r0}
            java.lang.String r8 = "_data = ?"
            java.lang.String r2 = "external"
            android.net.Uri r9 = android.provider.MediaStore.Files.getContentUri(r2)
            r10 = 0
            java.lang.String r11 = "com.android.providers.media.MediaProvider"
            r12 = 0
            r2 = 1
            r15.grantUriPermission(r11, r9, r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "getItemContentUri, filePath = "
            r3.append(r4)
            java.lang.String r4 = r14.mAbsolutePath
            r3.append(r4)
            java.lang.String r4 = ", projection = "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r4 = ", where = "
            r3.append(r4)
            java.lang.String r4 = "_data = ?"
            r3.append(r4)
            java.lang.String r4 = ", baseUri = "
            r3.append(r4)
            r3.append(r9)
            java.lang.String r3 = r3.toString()
            java.lang.String r13 = "McfConfig/FileInfo"
            com.mediatek.engineermode.Elog.d(r13, r3)
            android.content.ContentResolver r3 = r15.getContentResolver()     // Catch:{ Exception -> 0x009d }
            java.lang.String r5 = "_data = ?"
            java.lang.String[] r6 = new java.lang.String[r2]     // Catch:{ Exception -> 0x009d }
            r2 = 0
            java.lang.String r4 = r14.mAbsolutePath     // Catch:{ Exception -> 0x009d }
            r6[r2] = r4     // Catch:{ Exception -> 0x009d }
            r7 = 0
            r2 = r3
            r3 = r9
            r4 = r1
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x009d }
            r10 = r2
            if (r10 == 0) goto L_0x0095
            boolean r2 = r10.moveToNext()     // Catch:{ Exception -> 0x009d }
            if (r2 == 0) goto L_0x0095
            int r2 = r10.getColumnIndexOrThrow(r0)     // Catch:{ Exception -> 0x009d }
            int r2 = r10.getInt(r2)     // Catch:{ Exception -> 0x009d }
            if (r2 == 0) goto L_0x0095
            int r0 = r10.getColumnIndexOrThrow(r0)     // Catch:{ Exception -> 0x009d }
            long r3 = r10.getLong(r0)     // Catch:{ Exception -> 0x009d }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009d }
            r0.<init>()     // Catch:{ Exception -> 0x009d }
            java.lang.String r5 = "getItemContentUri, item id = "
            r0.append(r5)     // Catch:{ Exception -> 0x009d }
            r0.append(r3)     // Catch:{ Exception -> 0x009d }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x009d }
            com.mediatek.engineermode.Elog.d(r13, r0)     // Catch:{ Exception -> 0x009d }
            java.lang.String r0 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x009d }
            android.net.Uri r0 = android.net.Uri.withAppendedPath(r9, r0)     // Catch:{ Exception -> 0x009d }
            r12 = r0
        L_0x0095:
            if (r10 == 0) goto L_0x00b5
        L_0x0097:
            r10.close()
            goto L_0x00b5
        L_0x009b:
            r0 = move-exception
            goto L_0x00b6
        L_0x009d:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x009b }
            r2.<init>()     // Catch:{ all -> 0x009b }
            java.lang.String r3 = "getItemContentUri Exception "
            r2.append(r3)     // Catch:{ all -> 0x009b }
            r2.append(r0)     // Catch:{ all -> 0x009b }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x009b }
            com.mediatek.engineermode.Elog.e(r13, r2)     // Catch:{ all -> 0x009b }
            if (r10 == 0) goto L_0x00b5
            goto L_0x0097
        L_0x00b5:
            return r12
        L_0x00b6:
            if (r10 == 0) goto L_0x00bb
            r10.close()
        L_0x00bb:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mediatek.engineermode.mcfconfig.FileInfo.getItemContentUri(android.content.Context):android.net.Uri");
    }

    public Uri getUri() {
        return Uri.fromFile(this.mFile);
    }

    public int hashCode() {
        return getFileAbsolutePath().hashCode();
    }

    public boolean equals(Object o) {
        if (super.equals(o)) {
            return true;
        }
        if (!(o instanceof FileInfo) || !((FileInfo) o).getFileAbsolutePath().equals(getFileAbsolutePath())) {
            return false;
        }
        return true;
    }

    public boolean isHideFile() {
        if (getFileName().startsWith(".")) {
            return true;
        }
        return false;
    }
}
