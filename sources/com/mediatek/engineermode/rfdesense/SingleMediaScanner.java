package com.mediatek.engineermode.rfdesense;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.io.File;

public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private File mFile;
    private MediaScannerConnection mMs;

    public SingleMediaScanner(Context context, File f) {
        this.mFile = f;
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, this);
        this.mMs = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    public void onMediaScannerConnected() {
        this.mMs.scanFile(this.mFile.getAbsolutePath(), (String) null);
    }

    public void onScanCompleted(String path, Uri uri) {
        this.mMs.disconnect();
    }
}
