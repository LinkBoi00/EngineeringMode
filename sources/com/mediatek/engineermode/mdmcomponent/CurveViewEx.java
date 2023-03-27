package com.mediatek.engineermode.mdmcomponent;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.media.subtitle.Cea708CCParser;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import com.mediatek.engineermode.Elog;
import com.mediatek.engineermode.R;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CurveViewEx extends View {
    private static final int BOTTOM_GAP = 20;
    private static final String COMPONENT_RSRPSINR_CONFIG_SHAREPRE = "telephony_rsrpsinr_config_settings";
    private static final int FONT_SIZE = 18;
    private static final int LEFT_GAP = 5;
    private static final int LINE_LENGTH = 5;
    private static final int MAX_COUNT = 20;
    private static final int MEDIUMWEAK_POINT1_X_DEFAULT = -140;
    private static final int MEDIUMWEAK_POINT1_Y_DEFAULT = 10;
    private static final int MEDIUMWEAK_POINT2_X_DEFAULT = -90;
    private static final int MEDIUMWEAK_POINT2_Y_DEFAULT = 10;
    private static final int MEDIUMWEAK_POINT3_X_DEFAULT = -90;
    private static final int MEDIUMWEAK_POINT3_Y_DEFAULT = -20;
    private static final int RIGHT_GAP = 10;
    private static final int STRONG_POINT1_X_DEFAULT = -80;
    private static final int STRONG_POINT1_Y_DEFAULT = 30;
    private static final int STRONG_POINT2_X_DEFAULT = -80;
    private static final int STRONG_POINT2_Y_DEFAULT = 20;
    private static final int STRONG_POINT3_X_DEFAULT = -30;
    private static final int STRONG_POINT3_Y_DEFAULT = 20;
    private static final String TAG = "EmInfo/CurveViewEx";
    private static final int TEXT_OFFSET_X = 16;
    private static final int TEXT_OFFSET_Y = 16;
    private static final int TOP_GAP = 70;
    private static final int WEAK_POINT1_X_DEFAULT = -140;
    private static final int WEAK_POINT1_Y_DEFAULT = 5;
    private static final int WEAK_POINT2_X_DEFAULT = -100;
    private static final int WEAK_POINT2_Y_DEFAULT = 5;
    private static final int WEAK_POINT3_X_DEFAULT = -100;
    private static final int WEAK_POINT3_Y_DEFAULT = -20;
    private static int mediumWeakPoint1X = -140;
    private static int mediumWeakPoint1Y = 10;
    private static int mediumWeakPoint2X = -90;
    private static int mediumWeakPoint2Y = 10;
    private static int mediumWeakPoint3X = -90;
    private static int mediumWeakPoint3Y = -20;
    private static int strongPoint1X = -80;
    private static int strongPoint1Y = 30;
    private static int strongPoint2X = -80;
    private static int strongPoint2Y = 20;
    private static int strongPoint3X = STRONG_POINT3_X_DEFAULT;
    private static int strongPoint3Y = 20;
    private static int weakPoint1X = -140;
    private static int weakPoint1Y = 5;
    private static int weakPoint2X = -100;
    private static int weakPoint2Y = 5;
    private static int weakPoint3X = -100;
    private static int weakPoint3Y = -20;
    private boolean mAutoScroll = true;
    private Paint mBoldPaint;
    private ArrayList<Config> mConfigs;
    Context mContext = null;
    private ArrayList<float[]> mData = null;
    private GestureDetector mGestureDetector;
    private int mHeight = 0;
    private Paint mLightPaint;
    /* access modifiers changed from: private */
    public Matrix mMatrix;
    private int mMediumWeakEndX = -90;
    private int mMediumWeakEndY = 10;
    private int mMediumWeakStartX = -140;
    private int mMediumWeakStartY = -20;
    private int mOffsetY = 100;
    private Paint mPaint;
    private int[] mRegionColor = {Color.rgb(43, 101, 171), Color.rgb(NfcCommand.CommandType.MTK_NFC_EM_MSG_END, Cea708CCParser.Const.CODE_C1_DF1, 0), Color.rgb(Cea708CCParser.Const.CODE_C1_DF0, Cea708CCParser.Const.CODE_C1_DF0, 186)};
    private Paint mRegionPaint;
    private ScaleGestureDetector mScaleDetector;
    /* access modifiers changed from: private */
    public boolean mScaling;
    private int mStrongX = -80;
    private int mStrongY = 20;
    private Paint mTextPaint;
    private Paint mTextPaint2;
    /* access modifiers changed from: private */
    public RectF mViewRect = new RectF();
    private int mWeakEndX = -100;
    private int mWeakEndY = 5;
    private int mWeakStartX = -140;
    private int mWeakStartY = -20;
    private int mWidth = 0;
    Axis mXAxis = new Axis();
    AxisConfig mXAxisConfig = null;
    AxisLabel mXAxisLabel = new AxisLabel();
    float mXMax = -30.0f;
    float mXMin = 0.0f;
    Axis mYAxis = new Axis();
    AxisConfig mYAxisConfig = null;
    AxisLabel mYAxisLabel = new AxisLabel();
    float mYMax = 0.0f;
    float mYMin = 0.0f;
    boolean test = false;

    public static class AxisConfig {
        static int TYPE_NORMAL = 0;
        static int TYPE_TIME = 1;
        long base;
        boolean configMax;
        boolean configMin;
        boolean configStep;
        HashMap<Integer, String> customLabel;
        boolean fixedMax;
        boolean fixedMin;
        boolean fixedStep;
        long max;
        long min;
        long step;
        int type;
    }

    public static class Config {
        static int LINE_DASH = 1;
        static int LINE_SOLID = 0;
        static int TYPE_CIRCLE = 0;
        static int TYPE_CROSS = 1;
        static int TYPE_NONE = 5;
        static int TYPE_RHOMBUS = 2;
        static int TYPE_SQUARE = 3;
        static int TYPE_TRIANGLE = 4;
        int color = -16776961;
        int lineType;
        int lineWidth;
        String name;
        int newLineThreadshold;
        int nodeType;
    }

    class GestureListenerEx implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {
        private static final int DIRECTION_HORIZONTAL = 1;
        private static final int DIRECTION_UNKNOWN = 0;
        private static final int DIRECTION_VERTICAL = 2;
        private int mScaleDirection;
        private int mScrollDirection;
        private float[] mValues = new float[9];

        GestureListenerEx() {
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            float scaleX = scale;
            float scaleY = scale;
            if (this.mScaleDirection == 0) {
                if (detector.getCurrentSpanX() > detector.getCurrentSpanY()) {
                    this.mScaleDirection = 1;
                } else {
                    this.mScaleDirection = 2;
                }
            }
            int i = this.mScaleDirection;
            if (i == 1) {
                scaleY = 1.0f;
            }
            if (i == 2) {
                scaleX = 1.0f;
            }
            doScale(scaleX, scaleY, detector.getFocusX(), detector.getFocusY());
            snapBack();
            CurveViewEx.this.invalidate();
            return true;
        }

        private void doScale(float scaleX, float scaleY, float x, float y) {
            if (CurveViewEx.this.mMatrix != null) {
                CurveViewEx.this.mMatrix.postScale(scaleX, scaleY, x, y);
            }
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            boolean unused = CurveViewEx.this.mScaling = true;
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            boolean unused = CurveViewEx.this.mScaling = false;
            this.mScaleDirection = 0;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (CurveViewEx.this.mScaling) {
                return false;
            }
            if (this.mScrollDirection == 0) {
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    this.mScrollDirection = 1;
                } else {
                    this.mScrollDirection = 2;
                }
            }
            int i = this.mScrollDirection;
            if (i == 1) {
                distanceY = 0.0f;
            }
            if (i == 2) {
                distanceX = 0.0f;
            }
            doTranslate(distanceX, distanceY);
            snapBack();
            CurveViewEx.this.invalidate();
            return true;
        }

        private void doTranslate(float distanceX, float distanceY) {
            if (CurveViewEx.this.mMatrix != null) {
                CurveViewEx.this.mMatrix.postTranslate(-distanceX, -distanceY);
            }
        }

        private void snapBack() {
            if (CurveViewEx.this.mMatrix != null) {
                float scaleX = 1.0f;
                float scaleY = 1.0f;
                float[] p1 = {(float) CurveViewEx.this.mXAxis.min, (float) CurveViewEx.this.mYAxis.min};
                float[] p2 = {(float) CurveViewEx.this.mXAxis.max, (float) CurveViewEx.this.mYAxis.max};
                CurveViewEx.this.mMatrix.mapPoints(p1);
                CurveViewEx.this.mMatrix.mapPoints(p2);
                if (Math.abs(p1[0] - p2[0]) < CurveViewEx.this.mViewRect.width()) {
                    scaleX = CurveViewEx.this.mViewRect.width() / Math.abs(p1[0] - p2[0]);
                }
                if (Math.abs(p1[1] - p2[1]) < CurveViewEx.this.mViewRect.height()) {
                    scaleY = CurveViewEx.this.mViewRect.height() / Math.abs(p1[1] - p2[1]);
                }
                CurveViewEx.this.mMatrix.postScale(scaleX, scaleY);
                float transX = 0.0f;
                float transY = 0.0f;
                float[] p12 = {(float) CurveViewEx.this.mXAxis.min, (float) CurveViewEx.this.mYAxis.min};
                float[] p22 = {(float) CurveViewEx.this.mXAxis.max, (float) CurveViewEx.this.mYAxis.max};
                CurveViewEx.this.mMatrix.mapPoints(p12);
                CurveViewEx.this.mMatrix.mapPoints(p22);
                if (p12[0] > CurveViewEx.this.mViewRect.left) {
                    transX = -(p12[0] - CurveViewEx.this.mViewRect.left);
                } else if (p22[0] < CurveViewEx.this.mViewRect.right) {
                    transX = -(p22[0] - CurveViewEx.this.mViewRect.right);
                }
                if (p12[1] < CurveViewEx.this.mViewRect.bottom) {
                    transY = -(p12[1] - CurveViewEx.this.mViewRect.bottom);
                } else if (p22[1] > CurveViewEx.this.mViewRect.top) {
                    transY = -(p22[1] - CurveViewEx.this.mViewRect.top);
                }
                CurveViewEx.this.mMatrix.postTranslate(transX, transY);
            }
        }

        public boolean onDown(MotionEvent e) {
            this.mScrollDirection = 0;
            return true;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return true;
        }
    }

    class Axis {
        long base;
        long count = 0;
        long max = 0;
        long min = 0;
        long step = 0;

        Axis() {
        }

        /* access modifiers changed from: package-private */
        public void set(float dataMin, float dataMax, AxisConfig config) {
            if (config.configMin) {
                if (config.fixedMin) {
                    dataMin = (float) config.min;
                } else {
                    dataMin = Math.min((float) config.min, dataMin);
                }
            }
            if (config.configMax) {
                if (config.fixedMax) {
                    dataMax = (float) config.max;
                } else {
                    dataMax = Math.max((float) config.max, dataMax);
                }
            }
            if (config.configStep) {
                long j = config.step;
                this.step = j;
                long j2 = this.step;
                this.min = ((long) Math.floor(((double) dataMin) / ((double) j))) * j2;
                long j3 = this.step;
                long ceil = ((long) Math.ceil(((double) dataMax) / ((double) j2))) * j3;
                this.max = ceil;
                this.count = (ceil - this.min) / j3;
            }
            this.base = config.base;
        }
    }

    class AxisLabel {
        long count = 0;
        long max = 0;
        long min = 0;
        long step = 0;
        int type;

        AxisLabel() {
        }

        /* access modifiers changed from: package-private */
        public void set(Axis axis, AxisConfig config) {
            this.step = axis.step;
            this.min = axis.min;
            this.max = axis.max;
            this.count = axis.count;
            this.type = config.type;
        }
    }

    public void setAxisConfig(AxisConfig xConfig, AxisConfig yConfig) {
        this.mXAxisConfig = xConfig;
        this.mYAxisConfig = yConfig;
        this.test = true;
        invalidate();
    }

    public void setConfig(Config[] configs) {
        this.mConfigs = new ArrayList<>();
        for (Config add : configs) {
            this.mConfigs.add(add);
        }
    }

    public CurveViewEx(Context context) {
        super(context);
        init(context);
        this.mContext = context;
    }

    public CurveViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        this.mContext = context;
    }

    private void init(Context context) {
        GestureListenerEx listener = new GestureListenerEx();
        this.mGestureDetector = new GestureDetector(context, listener, (Handler) null, true);
        this.mScaleDetector = new ScaleGestureDetector(context, listener);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mPaint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mBoldPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mBoldPaint.setAntiAlias(false);
        this.mBoldPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        Paint paint3 = new Paint();
        this.mLightPaint = paint3;
        paint3.setStyle(Paint.Style.STROKE);
        this.mLightPaint.setAntiAlias(false);
        this.mLightPaint.setColor(-3355444);
        Paint paint4 = new Paint();
        this.mTextPaint = paint4;
        paint4.setStyle(Paint.Style.STROKE);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mTextPaint.setTextSize(18.0f);
        Paint paint5 = new Paint();
        this.mTextPaint2 = paint5;
        paint5.setStyle(Paint.Style.STROKE);
        this.mTextPaint2.setAntiAlias(true);
        this.mTextPaint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mTextPaint2.setTextSize(18.0f);
        Paint paint6 = new Paint();
        this.mRegionPaint = paint6;
        paint6.setStyle(Paint.Style.STROKE);
        this.mRegionPaint.setAntiAlias(false);
        this.mRegionPaint.setTextSize(12.0f);
        this.mRegionPaint.setStrokeWidth(5.0f);
    }

    public void setData(int index, float[] data) {
        if (data != null && data.length > 1 && this.mWidth != 0 && this.mHeight != 0) {
            if (this.mData == null) {
                this.mData = new ArrayList<>();
                for (int i = 0; i < this.mConfigs.size(); i++) {
                    this.mData.add((Object) null);
                }
            }
            this.mData.set(index, data);
            checkData();
            updateAxis();
            if (this.mAutoScroll) {
                makeLastVisible();
            }
            if (!this.mScaling) {
                invalidate();
            }
        }
    }

    private void makeLastVisible() {
        float transX = 0.0f;
        float[] p = {this.mXMax, this.mYMax};
        this.mMatrix.mapPoints(p);
        if (p[0] > this.mViewRect.right) {
            transX = -(p[0] - this.mViewRect.right);
        }
        this.mMatrix.postTranslate(transX, 0.0f);
    }

    private void checkData() {
        Iterator<float[]> it = this.mData.iterator();
        while (it.hasNext()) {
            float[] data = it.next();
            if (data != null) {
                for (int i = 0; i < data.length; i += 4) {
                    float x = data[i];
                    float y = data[i + 1];
                    if (x < this.mXMin) {
                        this.mXMin = x;
                    }
                    if (y < this.mYMin) {
                        this.mYMin = y;
                    }
                    if (x > this.mXMax) {
                        this.mXMax = x;
                    }
                    if (y > this.mYMax) {
                        this.mYMax = y;
                    }
                }
            }
        }
    }

    private void updateAxis() {
        this.mXAxis.set(this.mXMin, this.mXMax, this.mXAxisConfig);
        this.mYAxis.set(this.mYMin, this.mYMax, this.mYAxisConfig);
        int width = 0;
        Rect rect = new Rect();
        if (this.mYAxisConfig.customLabel != null) {
            for (String str : this.mYAxisConfig.customLabel.values()) {
                this.mTextPaint.getTextBounds(str, 0, str.length(), rect);
                if (rect.width() > width) {
                    width = rect.width();
                }
            }
        } else {
            long y = this.mYAxis.min;
            while (y <= this.mYAxis.max) {
                String str2 = String.valueOf(y);
                this.mTextPaint.getTextBounds(str2, 0, str2.length(), rect);
                if (rect.width() > width) {
                    width = rect.width();
                }
                y += this.mYAxis.step;
            }
        }
        this.mViewRect.set((float) (width + 5), 70.0f, (float) (this.mWidth - 10), (float) (this.mHeight - 20));
        if (this.mMatrix == null) {
            Matrix matrix = new Matrix();
            this.mMatrix = matrix;
            matrix.postScale(this.mViewRect.width() / ((float) (this.mXAxis.max - this.mXAxis.min)), (-this.mViewRect.height()) / ((float) (this.mYAxis.max - this.mYAxis.min)), (float) this.mXAxis.min, (float) this.mYAxis.min);
            this.mMatrix.postTranslate(this.mViewRect.left - ((float) this.mXAxis.min), this.mViewRect.bottom - ((float) this.mYAxis.min));
        }
        updateAxisLabel();
    }

    private void updateAxisLabel() {
        this.mXAxisLabel.set(this.mXAxis, this.mXAxisConfig);
        this.mYAxisLabel.set(this.mYAxis, this.mYAxisConfig);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        this.mWidth = View.MeasureSpec.getSize(widthSpec);
        this.mHeight = View.MeasureSpec.getSize(heightSpec);
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.mGestureDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float[] weakPts;
        int i;
        float f;
        int i2;
        float[] data;
        int nodeType;
        float[] tmp;
        int j;
        float[] weakPts2;
        char c;
        float f2;
        int i3;
        float[] p;
        int width;
        float f3;
        long y;
        String text;
        long x;
        float[] p2;
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        if (this.mXAxisConfig != null && this.mYAxisConfig != null) {
            char c2 = 0;
            if (this.test) {
                updateAxis();
                this.test = false;
            }
            int width2 = getWidth();
            int height = getHeight();
            Matrix m = this.mMatrix;
            RectF r = this.mViewRect;
            int i4 = 2;
            char c3 = 1;
            float[] p3 = {this.mXMax, 0.0f};
            m.mapPoints(p3);
            if (p3[0] <= ((float) width2)) {
                this.mAutoScroll = true;
            } else {
                this.mAutoScroll = false;
            }
            canvas2.drawColor(-1);
            Canvas canvas3 = canvas;
            float[] fArr = p3;
            canvas3.drawLine(r.left, r.bottom, r.right, r.bottom, this.mBoldPaint);
            canvas3.drawLine(r.left, r.top, r.left, r.bottom, this.mBoldPaint);
            canvas.save();
            canvas2.clipRect(r.left, 0.0f, (float) this.mWidth, (float) this.mHeight);
            long x2 = this.mXAxisLabel.min;
            while (x2 <= this.mXAxisLabel.max) {
                float[] fArr2 = new float[2];
                fArr2[0] = (float) x2;
                fArr2[c3] = 0.0f;
                float[] p4 = fArr2;
                m.mapPoints(p4);
                if (Math.abs(p4[0] - r.left) > 1.0f) {
                    p2 = p4;
                    x = x2;
                    canvas.drawLine(p4[0], r.top, p4[0], r.bottom, this.mBoldPaint);
                } else {
                    p2 = p4;
                    x = x2;
                }
                x2 = x + this.mXAxisLabel.step;
                float[] fArr3 = p2;
                c3 = 1;
            }
            long j2 = x2;
            canvas.restore();
            long x3 = this.mXAxisLabel.min;
            while (x3 <= this.mXAxisLabel.max) {
                float[] p5 = {(float) x3, 0.0f};
                m.mapPoints(p5);
                canvas2.drawText(Long.toString(x3), p5[0] - 16.0f, r.bottom + 16.0f, this.mTextPaint);
                x3 += this.mXAxisLabel.step;
                float[] fArr4 = p5;
            }
            canvas.save();
            canvas2.clipRect(0.0f, r.top - ((float) this.mOffsetY), (float) this.mWidth, r.bottom);
            long y2 = this.mYAxisLabel.min;
            while (y2 <= this.mYAxisLabel.max) {
                float[] fArr5 = new float[i4];
                fArr5[c2] = 0.0f;
                fArr5[1] = (float) y2;
                float[] p6 = fArr5;
                m.mapPoints(p6);
                if (Math.abs(p6[1] - r.bottom) > 1.0f) {
                    p = p6;
                    f3 = 5.0f;
                    width = width2;
                    y = y2;
                    canvas.drawLine(r.left, p6[1], r.right, p6[1], this.mBoldPaint);
                } else {
                    p = p6;
                    width = width2;
                    f3 = 5.0f;
                    y = y2;
                }
                if (this.mYAxisConfig.customLabel != null) {
                    text = this.mYAxisConfig.customLabel.get(Integer.valueOf((int) y));
                    if (text == null) {
                        text = "";
                    }
                } else {
                    text = Long.toString(y);
                }
                if (y == this.mYAxisLabel.max) {
                    canvas2.drawText(text, f3, p[1] + 20.0f, this.mTextPaint);
                } else {
                    canvas2.drawText(text, f3, p[1] - 16.0f, this.mTextPaint);
                }
                y2 = y + this.mYAxisLabel.step;
                width2 = width;
                float[] fArr6 = p;
                c2 = 0;
                i4 = 2;
            }
            long j3 = y2;
            canvas.restore();
            canvas.save();
            canvas2.clipRect(r.left, 0.0f, (float) this.mWidth, (float) this.mHeight);
            int i5 = strongPoint2X;
            int i6 = strongPoint2Y;
            float[] strongPts = {(float) strongPoint1X, (float) strongPoint1Y, (float) i5, (float) i6, (float) i5, (float) i6, (float) strongPoint3X, (float) strongPoint3Y};
            m.mapPoints(strongPts);
            this.mRegionPaint.setColor(this.mRegionColor[0]);
            canvas2.drawLines(strongPts, this.mRegionPaint);
            int i7 = mediumWeakPoint2X;
            int i8 = mediumWeakPoint2Y;
            float[] mediumWeakPts = {(float) mediumWeakPoint1X, (float) mediumWeakPoint1Y, (float) i7, (float) i8, (float) i7, (float) i8, (float) mediumWeakPoint3X, (float) mediumWeakPoint3Y};
            m.mapPoints(mediumWeakPts);
            this.mRegionPaint.setColor(this.mRegionColor[1]);
            canvas2.drawLines(mediumWeakPts, this.mRegionPaint);
            int i9 = weakPoint2X;
            int i10 = weakPoint2Y;
            float[] weakPts3 = {(float) weakPoint1X, (float) weakPoint1Y, (float) i9, (float) i10, (float) i9, (float) i10, (float) weakPoint3X, (float) weakPoint3Y};
            m.mapPoints(weakPts3);
            this.mRegionPaint.setColor(this.mRegionColor[2]);
            canvas2.drawLines(weakPts3, this.mRegionPaint);
            canvas.restore();
            drawSeries(canvas2, new RectF(50.0f, 0.0f, (float) this.mWidth, 86.0f));
            ArrayList<float[]> arrayList = this.mData;
            if (arrayList == null) {
                float[] fArr7 = weakPts3;
            } else if (arrayList.size() < 1) {
                float[] fArr8 = weakPts3;
            } else {
                canvas.save();
                canvas2.clipRect(r.left, 0.0f, (float) this.mWidth, r.bottom);
                float[] pText = {r.left, 100.0f};
                float f4 = 10.0f;
                canvas2.drawText("PCI", pText[0] + 10.0f, pText[1] - 60.0f, this.mTextPaint2);
                int i11 = 1109393408;
                canvas2.drawText("EARFCN", pText[0] + 10.0f, pText[1] - 40.0f, this.mTextPaint2);
                int i12 = 0;
                while (i12 < this.mData.size()) {
                    float[] data2 = this.mData.get(i12);
                    if (data2 == null) {
                        f = f4;
                        weakPts = weakPts3;
                        i = i11;
                        i2 = i12;
                    } else {
                        this.mPaint.setColor(this.mConfigs.get(i12).color);
                        this.mPaint.setStyle(Paint.Style.FILL);
                        int nodeType2 = this.mConfigs.get(i12).nodeType;
                        float[] tmp2 = new float[data2.length];
                        m.mapPoints(tmp2, data2);
                        int j4 = 0;
                        while (j4 < tmp2.length) {
                            if (nodeType2 == Config.TYPE_CIRCLE) {
                                weakPts2 = weakPts3;
                                canvas2.drawCircle(tmp2[j4], tmp2[j4 + 1], f4, this.mPaint);
                                canvas2.drawText(Float.toString(data2[j4 + 2]), pText[0] + 100.0f, pText[1] - 60.0f, this.mTextPaint2);
                                canvas2.drawText(Float.toString(data2[j4 + 3]), pText[0] + 100.0f, pText[1] - 40.0f, this.mTextPaint2);
                                j = j4;
                                tmp = tmp2;
                                nodeType = nodeType2;
                                data = data2;
                                i3 = i12;
                                f2 = 10.0f;
                                c = 0;
                            } else {
                                int i13 = i12;
                                weakPts2 = weakPts3;
                                if (nodeType2 == Config.TYPE_TRIANGLE) {
                                    Path pathTriangleFigure = new Path();
                                    pathTriangleFigure.moveTo(tmp2[j4], tmp2[j4 + 1] - 14.0f);
                                    pathTriangleFigure.lineTo(tmp2[j4] - 14.0f, tmp2[j4 + 1] + 14.0f);
                                    pathTriangleFigure.lineTo(tmp2[j4] + 14.0f, tmp2[j4 + 1] + 14.0f);
                                    pathTriangleFigure.close();
                                    canvas2.drawPath(pathTriangleFigure, this.mPaint);
                                    Path path = pathTriangleFigure;
                                    canvas2.drawText(Float.toString(data2[j4 + 2]), pText[0] + 250.0f, pText[1] - 60.0f, this.mTextPaint2);
                                    c = 0;
                                    canvas2.drawText(Float.toString(data2[j4 + 3]), pText[0] + 250.0f, pText[1] - 40.0f, this.mTextPaint2);
                                    j = j4;
                                    tmp = tmp2;
                                    nodeType = nodeType2;
                                    data = data2;
                                    i3 = i13;
                                    f2 = 10.0f;
                                } else {
                                    c = 0;
                                    if (nodeType2 == Config.TYPE_CROSS) {
                                        j = j4;
                                        tmp = tmp2;
                                        nodeType = nodeType2;
                                        data = data2;
                                        i3 = i13;
                                        f2 = 10.0f;
                                        canvas.drawLine(tmp2[j4] - 5.0f, tmp2[j4 + 1] - 5.0f, tmp2[j4] + 5.0f, tmp2[j4 + 1] + 5.0f, this.mPaint);
                                        canvas.drawLine(tmp[j] + 5.0f, tmp[j + 1] - 5.0f, tmp[j] - 5.0f, tmp[j + 1] + 5.0f, this.mPaint);
                                    } else {
                                        j = j4;
                                        tmp = tmp2;
                                        nodeType = nodeType2;
                                        data = data2;
                                        i3 = i13;
                                        f2 = 10.0f;
                                    }
                                }
                            }
                            j4 = j + 4;
                            i12 = i3;
                            f4 = f2;
                            int i14 = c;
                            weakPts3 = weakPts2;
                            tmp2 = tmp;
                            nodeType2 = nodeType;
                            data2 = data;
                        }
                        int i15 = j4;
                        float[] fArr9 = tmp2;
                        int i16 = nodeType2;
                        float[] fArr10 = data2;
                        i2 = i12;
                        f = f4;
                        weakPts = weakPts3;
                        i = 1109393408;
                    }
                    i12 = i2 + 1;
                    f4 = f;
                    i11 = i;
                    weakPts3 = weakPts;
                }
                canvas.restore();
            }
        }
    }

    private void drawSeries(Canvas canvas, RectF rect) {
        canvas.save();
        canvas.clipRect(rect);
        float[] p = {rect.left, ((rect.top + rect.bottom) / 2.0f) - 28.0f};
        this.mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < this.mConfigs.size(); i++) {
            Elog.v(TAG, "[CurveViewEx] drawSeries i " + i);
            p[0] = p[0] + 10.0f;
            this.mPaint.setColor(this.mConfigs.get(i).color);
            this.mPaint.setStrokeWidth((float) this.mConfigs.get(i).lineWidth);
            this.mPaint.setPathEffect((PathEffect) null);
            int nodeType = this.mConfigs.get(i).nodeType;
            if (nodeType == Config.TYPE_NONE) {
                canvas.drawLine(p[0], p[1], p[0] + 100.0f, p[1], this.mPaint);
            }
            p[0] = p[0] + 50.0f;
            if (nodeType == Config.TYPE_CIRCLE) {
                canvas.drawCircle(p[0], p[1], 5.0f, this.mPaint);
            } else if (nodeType != Config.TYPE_NONE) {
                if (nodeType == Config.TYPE_TRIANGLE) {
                    Path pathTriangleFigure = new Path();
                    pathTriangleFigure.moveTo(p[0], p[1] - 5.0f);
                    pathTriangleFigure.lineTo(p[0] - 5.0f, p[1] + 5.0f);
                    pathTriangleFigure.lineTo(p[0] + 5.0f, p[1] + 5.0f);
                    pathTriangleFigure.close();
                    canvas.drawPath(pathTriangleFigure, this.mPaint);
                } else if (nodeType == Config.TYPE_CROSS) {
                    Canvas canvas2 = canvas;
                    canvas2.drawLine(p[0] - 5.0f, p[1] - 5.0f, p[0] + 5.0f, p[1] + 5.0f, this.mPaint);
                    canvas2.drawLine(p[0] + 5.0f, p[1] - 5.0f, p[0] - 5.0f, p[1] + 5.0f, this.mPaint);
                } else if (nodeType == Config.TYPE_RHOMBUS) {
                    Path pathRhombusFigure = new Path();
                    pathRhombusFigure.moveTo(p[0], p[1] + 5.0f);
                    pathRhombusFigure.lineTo(p[0] - 5.0f, p[1]);
                    pathRhombusFigure.lineTo(p[0], p[1] - 5.0f);
                    pathRhombusFigure.lineTo(p[0] + 5.0f, p[1]);
                    pathRhombusFigure.close();
                    canvas.drawPath(pathRhombusFigure, this.mPaint);
                } else if (nodeType == Config.TYPE_SQUARE) {
                    canvas.drawRect(p[0] - 5.0f, p[1] - 5.0f, p[0] + 5.0f, p[1] + 5.0f, this.mPaint);
                }
            }
            p[0] = p[0] + 60.0f;
            String str = this.mConfigs.get(i).name;
            if (nodeType == Config.TYPE_CIRCLE || nodeType == Config.TYPE_TRIANGLE) {
                canvas.drawText(str, p[0] - 40.0f, p[1], this.mTextPaint2);
            } else {
                canvas.drawText(str, p[0], p[1], this.mTextPaint2);
            }
            Rect r = new Rect();
            this.mTextPaint2.getTextBounds(str, 0, str.length(), r);
            p[0] = p[0] + ((float) r.width());
        }
        canvas.restore();
    }

    private long increase(long value) {
        if (value == 0) {
            return 1;
        }
        if (String.valueOf(value).startsWith("1") || String.valueOf(value).startsWith("5")) {
            return value * 2;
        }
        return (value / 2) * 5;
    }

    public void getRSRPSINRConfig() {
        SharedPreferences rsrpSinrConfigSh = this.mContext.getSharedPreferences(COMPONENT_RSRPSINR_CONFIG_SHAREPRE, 0);
        strongPoint1X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.strong_point_1x), -80);
        strongPoint1Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.strong_point_1y), 30);
        strongPoint2X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.strong_point_2x), -80);
        strongPoint2Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.strong_point_2y), 20);
        strongPoint3X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.strong_point_3x), STRONG_POINT3_X_DEFAULT);
        strongPoint3Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.strong_point_3y), 20);
        mediumWeakPoint1X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.mediumweak_point_1x), -140);
        mediumWeakPoint1Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.mediumweak_point_1y), 10);
        mediumWeakPoint2X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.mediumweak_point_2x), -90);
        mediumWeakPoint2Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.mediumweak_point_2y), 10);
        mediumWeakPoint3X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.mediumweak_point_3x), -90);
        mediumWeakPoint3Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.mediumweak_point_3y), -20);
        weakPoint1X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.weak_point_1x), -140);
        weakPoint1Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.weak_point_1y), 5);
        weakPoint2X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.weak_point_2x), -100);
        weakPoint2Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.weak_point_2y), 5);
        weakPoint3X = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.weak_point_3x), -100);
        weakPoint3Y = rsrpSinrConfigSh.getInt(this.mContext.getString(R.string.weak_point_3y), -20);
    }
}
