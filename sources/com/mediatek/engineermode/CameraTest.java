package com.mediatek.engineermode;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CameraProfile;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CameraTest extends Activity implements SurfaceHolder.Callback {
    public static final String FACING_TAG = "facingfront";
    private static final int FULL_DEGREE = 360;
    private static final int IDLE = 1;
    private static final int PREVIEW_STOPPED = 0;
    static final String TAG = "CameraTest";
    private static final long TIME_ONE_SEC = 1000;
    private Camera mCameraDevice;
    private int mCameraId;
    private int mCameraState = 0;
    private boolean mPausing;
    private SurfaceHolder mSurfaceHolder = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Elog.i(TAG, "[onCreate]");
        this.mCameraId = getCameraID(getIntent().getBooleanExtra(FACING_TAG, false));
        SurfaceView surfaceView = new SurfaceView(this);
        setContentView(surfaceView);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(3);
        Elog.i(TAG, "[onCreate] Finished");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Elog.i(TAG, "onResume()");
        getWindow().addFlags(128);
        this.mPausing = false;
        Camera openCamera = openCamera(this.mCameraId);
        this.mCameraDevice = openCamera;
        if (openCamera != null) {
            startPreview();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        Elog.i(TAG, "onPause()");
        this.mPausing = true;
        stopPreview();
        closeCamera();
        getWindow().clearFlags(128);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Elog.v(TAG, "surfaceCreated.");
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder == null || holder.getSurface() == null || this.mCameraDevice == null || this.mPausing || isFinishing()) {
            Elog.d(TAG, "holder.getSurface() == null");
            return;
        }
        this.mSurfaceHolder = holder;
        if (this.mCameraState == 0) {
            startPreview();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Elog.v(TAG, "surfaceDestroyed()");
        stopPreview();
        this.mSurfaceHolder = null;
    }

    private Camera openCamera(int cameraId) {
        int i = 0;
        while (i < 10) {
            Camera camera = Camera.open(cameraId);
            if (camera != null) {
                return camera;
            }
            try {
                Thread.sleep(TIME_ONE_SEC);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private static int getCameraID(boolean front) {
        int i;
        Elog.d(TAG, "facing:" + front);
        int availCameraNumber = Camera.getNumberOfCameras();
        Elog.d(TAG, "availCameraNumber:" + availCameraNumber);
        for (int i2 = 0; i2 < availCameraNumber; i2++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i2, info);
            int i3 = info.facing;
            if (front) {
                i = 1;
            } else {
                i = 0;
            }
            if (i3 == i) {
                Elog.d(TAG, "cameraID:" + i2);
                return i2;
            }
        }
        return -1;
    }

    public static boolean isCameraSupport(boolean front) {
        return getCameraID(front) != -1;
    }

    private void closeCamera() {
        Camera camera = this.mCameraDevice;
        if (camera != null) {
            camera.release();
            this.mCameraDevice = null;
            setCameraState(0);
        }
    }

    private void startPreview() {
        Elog.d(TAG, "set Preview");
        if (this.mSurfaceHolder == null || this.mPausing || isFinishing()) {
            Elog.d(TAG, "startPreview() return");
            return;
        }
        if (this.mCameraState != 0) {
            stopPreview();
        }
        setPreviewDisplay(this.mSurfaceHolder);
        setOrientation();
        setCameraParameters();
        Elog.v(TAG, "==== startPreview ====");
        this.mCameraDevice.startPreview();
        setCameraState(1);
    }

    private void stopPreview() {
        if (!(this.mCameraDevice == null || this.mCameraState == 0)) {
            Elog.v(TAG, "stopPreview");
            this.mCameraDevice.stopPreview();
        }
        setCameraState(0);
    }

    private void setPreviewDisplay(SurfaceHolder holder) {
        try {
            this.mCameraDevice.setPreviewDisplay(holder);
        } catch (IOException e) {
            closeCamera();
        }
    }

    private void setOrientation() {
        int degrees = 0;
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = 180;
                break;
            case 3:
                degrees = 270;
                break;
        }
        this.mCameraDevice.setDisplayOrientation(getOrientation(degrees));
    }

    private int getOrientation(int degrees) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(this.mCameraId, cameraInfo);
        if (cameraInfo.facing == 1) {
            return (360 - ((cameraInfo.orientation + degrees) % FULL_DEGREE)) % FULL_DEGREE;
        }
        return ((cameraInfo.orientation - degrees) + FULL_DEGREE) % FULL_DEGREE;
    }

    private void setCameraState(int state) {
        Elog.d(TAG, "setCameraState() state " + state);
        this.mCameraState = state;
    }

    private void setCameraParameters() {
        Camera.Parameters parameters = this.mCameraDevice.getParameters();
        List<Integer> frameRates = parameters.getSupportedPreviewFrameRates();
        if (frameRates != null) {
            Integer max = (Integer) Collections.max(frameRates);
            Elog.v(TAG, "Max frame rate is " + max);
            parameters.setPreviewFrameRate(max.intValue());
        }
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        if (pictureSizes != null) {
            Camera.Size maxSize = pictureSizes.get(0);
            for (Camera.Size size : pictureSizes) {
                if (size.height > maxSize.height || size.width > maxSize.width) {
                    maxSize = size;
                }
            }
            Elog.v(TAG, "Max picture size is " + maxSize.width + "x" + maxSize.height);
            parameters.setPictureSize(maxSize.width, maxSize.height);
        }
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        if (previewSizes != null) {
            Camera.Size maxSize2 = previewSizes.get(0);
            for (Camera.Size size2 : previewSizes) {
                if (size2.height > maxSize2.height || size2.width > maxSize2.width) {
                    maxSize2 = size2;
                }
            }
            Elog.v(TAG, "Max preview size is " + maxSize2.width + "x" + maxSize2.height);
            parameters.setPreviewSize(maxSize2.width, maxSize2.height);
        }
        parameters.setJpegQuality(CameraProfile.getJpegEncodingQualityParameter(this.mCameraId, 2));
        this.mCameraDevice.setParameters(parameters);
    }
}
