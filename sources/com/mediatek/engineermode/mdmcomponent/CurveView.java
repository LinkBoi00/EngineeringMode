package com.mediatek.engineermode.mdmcomponent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import com.mediatek.engineermode.Elog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class CurveView extends View {
    private static final int BOTTOM_GAP = 20;
    private static final int FONT_SIZE = 10;
    private static final int LEFT_GAP = 5;
    private static final int LINE_LENGTH = 5;
    private static final int MAX_COUNT = 20;
    private static final int RIGHT_GAP = 10;
    private static final String TAG = "EmInfo/CurveView";
    private static final int TEXT_OFFSET_X = 5;
    private static final int TEXT_OFFSET_Y = 10;
    private static final int TOP_GAP = 70;
    int drawIndex = 0;
    private boolean mAutoScroll = true;
    private Paint mBoldPaint;
    private ArrayList<Config> mConfigs;
    private ArrayList<float[]> mData = null;
    private GestureDetector mGestureDetector;
    private int mHeight = 0;
    private Paint mLightPaint;
    /* access modifiers changed from: private */
    public Matrix mMatrix;
    private Paint mPaint;
    private ScaleGestureDetector mScaleDetector;
    /* access modifiers changed from: private */
    public boolean mScaling;
    /* access modifiers changed from: private */
    public Paint mTextPaint;
    private Paint mTextPaint2;
    /* access modifiers changed from: private */
    public RectF mViewRect = new RectF();
    private int mWidth = 0;
    Axis mXAxis = new Axis();
    AxisConfig mXAxisConfig = null;
    AxisLabel mXAxisLabel = new AxisLabel();
    float mXMax = Float.NEGATIVE_INFINITY;
    float mXMin = Float.POSITIVE_INFINITY;
    Axis mYAxis = new Axis();
    AxisConfig mYAxisConfig = null;
    AxisLabel mYAxisLabel = new AxisLabel();
    float mYMax = Float.NEGATIVE_INFINITY;
    float mYMin = Float.POSITIVE_INFINITY;
    boolean test = false;

    public static class AxisConfig {
        static int TYPE_AUTO_SCALE = 3;
        static int TYPE_NORMAL = 0;
        static int TYPE_TIME = 1;
        long base;
        boolean configMax;
        boolean configMaxCount = false;
        boolean configMin;
        boolean configStep;
        HashMap<Integer, String> customLabel;
        boolean fixedMax;
        boolean fixedMin;
        boolean fixedStep;
        long max;
        long maxCount;
        long min;
        long step;
        int type;
    }

    public static class Config {
        static int LINE_DASH = 1;
        static int LINE_SOLID = 0;
        static int TYPE_CIRCLE = 0;
        static int TYPE_CROSS = 1;
        int color = -16776961;
        int lineType;
        int lineWidth;
        String name;
        int newLineThreadshold;
        int nodeType;
    }

    class GestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {
        private static final int DIRECTION_HORIZONTAL = 1;
        private static final int DIRECTION_UNKNOWN = 0;
        private static final int DIRECTION_VERTICAL = 2;
        private int mScaleDirection;
        private int mScrollDirection;
        private float[] mValues = new float[9];

        GestureListener() {
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            float scaleX = scale;
            float scaleY = scale;
            float scaleX2 = scaleX < ((float) CurveView.this.getWidth()) / CurveView.this.mViewRect.width() ? scaleX : ((float) CurveView.this.getWidth()) / CurveView.this.mViewRect.width();
            float scaleY2 = scaleY < ((float) CurveView.this.getHeight()) / CurveView.this.mViewRect.height() ? scaleY : ((float) CurveView.this.getHeight()) / CurveView.this.mViewRect.height();
            if (this.mScaleDirection == 0) {
                if (detector.getCurrentSpanX() > detector.getCurrentSpanY()) {
                    this.mScaleDirection = 1;
                    scaleY2 = 1.0f;
                } else {
                    this.mScaleDirection = 2;
                    scaleX2 = 1.0f;
                }
            }
            doScale(scaleX2, scaleY2, detector.getFocusX(), detector.getFocusY());
            snapBack();
            CurveView.this.updateAxisLabel();
            CurveView.this.invalidate();
            return true;
        }

        private void doScale(float scaleX, float scaleY, float x, float y) {
            if (CurveView.this.mMatrix != null) {
                CurveView.this.mMatrix.postScale(scaleX, scaleY, x, y);
            }
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            boolean unused = CurveView.this.mScaling = true;
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            boolean unused = CurveView.this.mScaling = false;
            this.mScaleDirection = 0;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (CurveView.this.mScaling) {
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
            CurveView.this.invalidate();
            return true;
        }

        private void doTranslate(float distanceX, float distanceY) {
            if (CurveView.this.mMatrix != null) {
                CurveView.this.mMatrix.postTranslate(-distanceX, -distanceY);
            }
        }

        private void snapBack() {
            if (CurveView.this.mMatrix != null) {
                float scaleX = 1.0f;
                float scaleY = 1.0f;
                float[] p1 = {(float) CurveView.this.mXAxis.min, (float) CurveView.this.mYAxis.min};
                float[] p2 = {(float) CurveView.this.mXAxis.max, (float) CurveView.this.mYAxis.max};
                CurveView.this.mMatrix.mapPoints(p1);
                CurveView.this.mMatrix.mapPoints(p2);
                if (Math.abs(p1[0] - p2[0]) < CurveView.this.mViewRect.width()) {
                    scaleX = CurveView.this.mViewRect.width() / Math.abs(p1[0] - p2[0]);
                }
                if (Math.abs(p1[1] - p2[1]) < CurveView.this.mViewRect.height()) {
                    scaleY = CurveView.this.mViewRect.height() / Math.abs(p1[1] - p2[1]);
                }
                CurveView.this.mMatrix.postScale(scaleX, scaleY);
                float transX = 0.0f;
                float transY = 0.0f;
                float[] p12 = {(float) CurveView.this.mXAxis.min, (float) CurveView.this.mYAxis.min};
                float[] p22 = {(float) CurveView.this.mXAxis.max, (float) CurveView.this.mYAxis.max};
                CurveView.this.mMatrix.mapPoints(p12);
                CurveView.this.mMatrix.mapPoints(p22);
                if (p12[0] > CurveView.this.mViewRect.left) {
                    transX = -(p12[0] - CurveView.this.mViewRect.left);
                } else if (p22[0] < CurveView.this.mViewRect.right) {
                    transX = -(p22[0] - CurveView.this.mViewRect.right);
                }
                if (p12[1] < CurveView.this.mViewRect.bottom) {
                    transY = -(p12[1] - CurveView.this.mViewRect.bottom);
                } else if (p22[1] > CurveView.this.mViewRect.top) {
                    transY = -(p22[1] - CurveView.this.mViewRect.top);
                }
                CurveView.this.mMatrix.postTranslate(transX, transY);
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
        long lastMax = -1;
        long max = 0;
        long min = 0;
        long step = 0;

        Axis() {
        }

        /* access modifiers changed from: package-private */
        public void set(float dataMin, float dataMax, AxisConfig config) {
            long j;
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
                long j2 = config.step;
                this.step = j2;
                long j3 = this.step;
                this.min = ((long) Math.floor(((double) dataMin) / ((double) j2))) * j3;
                long j4 = this.step;
                long ceil = ((long) Math.ceil(((double) dataMax) / ((double) j3))) * j4;
                this.max = ceil;
                this.count = (ceil - this.min) / j4;
                if (config.configMaxCount && this.count > config.maxCount) {
                    while (this.count > config.maxCount) {
                        long access$400 = CurveView.this.increase(this.step);
                        this.step = access$400;
                        long j5 = this.step;
                        this.min = ((long) Math.floor(((double) dataMin) / ((double) access$400))) * j5;
                        long j6 = this.step;
                        long ceil2 = ((long) Math.ceil(((double) dataMax) / ((double) j5))) * j6;
                        this.max = ceil2;
                        this.count = (ceil2 - this.min) / j6;
                    }
                }
            } else {
                this.step = 0;
                this.count = 21;
                while (true) {
                    j = this.count;
                    if (j <= 20) {
                        break;
                    }
                    long access$4002 = CurveView.this.increase(this.step);
                    this.step = access$4002;
                    long j7 = this.step;
                    this.min = ((long) Math.floor(((double) dataMin) / ((double) access$4002))) * j7;
                    long j8 = this.step;
                    long ceil3 = ((long) Math.ceil(((double) dataMax) / ((double) j7))) * j8;
                    this.max = ceil3;
                    this.count = (ceil3 - this.min) / j8;
                }
                if (j == 0) {
                    this.count = 1;
                    this.min = this.max - this.step;
                }
            }
            this.base = config.base;
            Elog.d(CurveView.TAG, "[CurveView][Axis][set] min: " + this.min + " max: " + this.max + " step: " + this.step + " count: " + this.count);
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
            int i = config.type;
            this.type = i;
            if (i == AxisConfig.TYPE_TIME) {
                int maxWidth = 0;
                Rect rect = new Rect();
                CurveView.this.mTextPaint.getTextBounds("HH:mm:ss", 0, "HH:mm:ss".length(), rect);
                if (rect.width() * 2 > 0) {
                    maxWidth = rect.width() * 2;
                }
                while (CurveView.this.mViewRect.width() / ((float) this.count) < ((float) maxWidth)) {
                    long access$400 = CurveView.this.increase(this.step);
                    this.step = access$400;
                    long j = this.step;
                    this.min = ((long) Math.floor(((double) this.min) / ((double) access$400))) * j;
                    long j2 = this.step;
                    long ceil = ((long) Math.ceil(((double) this.max) / ((double) j))) * j2;
                    this.max = ceil;
                    this.count = (ceil - this.min) / j2;
                }
            }
            if (this.type == AxisConfig.TYPE_AUTO_SCALE) {
                int maxHeight = 0;
                Rect rect2 = new Rect();
                CurveView.this.mTextPaint.getTextBounds("100000", 0, "100000".length(), rect2);
                if (rect2.height() * 5 > 0) {
                    maxHeight = rect2.height() * 5;
                }
                while (CurveView.this.mViewRect.height() / ((float) this.count) < ((float) maxHeight)) {
                    long access$4002 = CurveView.this.increase(this.step);
                    this.step = access$4002;
                    long j3 = this.step;
                    this.min = ((long) Math.floor(((double) this.min) / ((double) access$4002))) * j3;
                    long j4 = this.step;
                    long ceil2 = ((long) Math.ceil(((double) this.max) / ((double) j3))) * j4;
                    this.max = ceil2;
                    this.count = (ceil2 - this.min) / j4;
                }
            }
            Elog.d(CurveView.TAG, "[CurveView][AxisLabel][set] minAxisLabel: " + this.min + " maxAxisLabel: " + this.max + " step: " + this.step + " count: " + this.count);
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

    public CurveView(Context context) {
        super(context);
        init(context);
    }

    public CurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        GestureListener listener = new GestureListener();
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
        this.mTextPaint.setTextSize(10.0f);
        Paint paint5 = new Paint();
        this.mTextPaint2 = paint5;
        paint5.setStyle(Paint.Style.STROKE);
        this.mTextPaint2.setAntiAlias(true);
        this.mTextPaint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mTextPaint2.setTextSize(12.0f);
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
        this.mXAxisLabel.set(this.mXAxis, this.mXAxisConfig);
    }

    private void checkData() {
        Iterator<float[]> it = this.mData.iterator();
        while (it.hasNext()) {
            float[] data = it.next();
            if (data != null) {
                for (int i = this.drawIndex; i < data.length; i += 2) {
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
                this.drawIndex = data.length;
            }
        }
    }

    private void updateAxis() {
        this.mXAxis.set(this.mXMin, this.mXMax, this.mXAxisConfig);
        this.mYAxis.set(this.mYMin, this.mYMax, this.mYAxisConfig);
        if (this.mYAxis.lastMax < this.mYAxis.max) {
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
                String str2 = String.valueOf(this.mYMax);
                this.mTextPaint.getTextBounds(str2, 0, str2.length(), rect);
                if (rect.width() > 0) {
                    width = rect.width();
                }
                Axis axis = this.mYAxis;
                axis.lastMax = axis.max;
            }
            this.mViewRect.set((float) (width + 5), 70.0f, (float) (this.mWidth - 10), (float) (this.mHeight - 20));
            if (this.mMatrix == null) {
                this.mMatrix = new Matrix();
                setMatrix();
            } else if (this.mYAxisConfig.type == AxisConfig.TYPE_AUTO_SCALE) {
                this.mMatrix = new Matrix();
                setMatrix();
            }
            updateAxisLabel();
        }
    }

    private void setMatrix() {
        this.mMatrix.postScale(this.mViewRect.width() / ((float) (this.mXAxis.max - this.mXAxis.min)), (-this.mViewRect.height()) / ((float) (this.mYAxis.max - this.mYAxis.min)), (float) this.mXAxis.min, (float) this.mYAxis.min);
        this.mMatrix.postTranslate(this.mViewRect.left - ((float) this.mXAxis.min), this.mViewRect.bottom - ((float) this.mYAxis.min));
    }

    /* access modifiers changed from: private */
    public void updateAxisLabel() {
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
        long j;
        int height;
        int width;
        int i;
        Matrix m;
        int j2;
        int i2;
        int nodeType;
        int height2;
        int j3;
        int width2;
        boolean z;
        Matrix m2;
        String name;
        float[] data;
        float[] tmp;
        float[] p;
        String text;
        long x;
        float[] p2;
        Canvas canvas2 = canvas;
        super.onDraw(canvas);
        if (this.mXAxisConfig != null && this.mYAxisConfig != null) {
            if (this.test) {
                updateAxis();
                this.test = false;
            }
            int width3 = getWidth();
            int height3 = getHeight();
            Matrix m3 = this.mMatrix;
            RectF r = this.mViewRect;
            int i3 = 2;
            char c = 1;
            float f = 0.0f;
            float[] p3 = {this.mXMax, 0.0f};
            m3.mapPoints(p3);
            if (p3[0] <= ((float) width3)) {
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
                fArr2[c] = 0.0f;
                float[] p4 = fArr2;
                m3.mapPoints(p4);
                if (Math.abs(p4[0] - r.left) > 1.0f) {
                    p2 = p4;
                    x = x2;
                    canvas.drawLine(p4[0], r.top, p4[0], r.bottom, this.mLightPaint);
                } else {
                    p2 = p4;
                    x = x2;
                }
                x2 = x + this.mXAxisLabel.step;
                float[] fArr3 = p2;
                c = 1;
            }
            long j4 = x2;
            long x3 = this.mXAxis.min;
            while (x3 <= this.mXAxis.max) {
                float[] p5 = {(float) x3, 0.0f};
                m3.mapPoints(p5);
                canvas.drawLine(p5[0], r.bottom, p5[0], r.bottom - 5.0f, this.mBoldPaint);
                x3 += this.mXAxis.step;
                float[] fArr4 = p5;
            }
            long j5 = x3;
            canvas.restore();
            long x4 = this.mXAxisLabel.min;
            while (x4 <= this.mXAxisLabel.max) {
                float[] p6 = {(float) x4, f};
                m3.mapPoints(p6);
                if (this.mXAxisLabel.type == AxisConfig.TYPE_TIME) {
                    String str = formatTime((this.mXAxis.base + x4) * 1000);
                    Rect rect = new Rect();
                    this.mTextPaint.getTextBounds(str, 0, str.length(), rect);
                    canvas2.drawText(str, p6[0] - ((float) (rect.width() / 2)), r.bottom + 10.0f, this.mTextPaint);
                } else {
                    canvas2.drawText(Long.toString(x4), p6[0], r.bottom + 10.0f, this.mTextPaint);
                }
                x4 += this.mXAxisLabel.step;
                float[] fArr5 = p6;
                f = 0.0f;
            }
            canvas.save();
            canvas2.clipRect(0.0f, r.top, (float) this.mWidth, r.bottom);
            long y = this.mYAxisLabel.min;
            while (y <= this.mYAxisLabel.max) {
                float[] p7 = {0.0f, (float) y};
                m3.mapPoints(p7);
                if (Math.abs(p7[1] - r.bottom) > 1.0f) {
                    float f2 = r.left;
                    float f3 = p7[1];
                    p = p7;
                    canvas.drawLine(f2, f3, r.right, p7[1], this.mLightPaint);
                } else {
                    p = p7;
                }
                if (this.mYAxisConfig.customLabel != null) {
                    text = this.mYAxisConfig.customLabel.get(Integer.valueOf((int) y));
                    if (text == null) {
                        text = "";
                    }
                } else {
                    text = Long.toString(y);
                }
                canvas2.drawText(text, 5.0f, p[1], this.mTextPaint);
                y += this.mYAxisLabel.step;
                float[] fArr6 = p;
            }
            if (this.mYAxisConfig.type == AxisConfig.TYPE_AUTO_SCALE) {
                j = this.mYAxisLabel.step / 5;
            } else {
                j = this.mYAxis.step;
            }
            long step = j;
            long y2 = this.mYAxis.min;
            while (y2 <= this.mYAxis.max) {
                float[] p8 = {0.0f, (float) y2};
                m3.mapPoints(p8);
                canvas.drawLine(r.left, p8[1], r.left + 5.0f, p8[1], this.mBoldPaint);
                y2 += step;
                float[] fArr7 = p8;
            }
            long j6 = y2;
            canvas.restore();
            drawSeries(canvas2, new RectF(10.0f, 10.0f, (float) this.mWidth, 80.0f));
            ArrayList<float[]> arrayList = this.mData;
            if (arrayList == null) {
                int i4 = width3;
                int i5 = height3;
                Matrix matrix = m3;
            } else if (arrayList.size() < 1) {
                int i6 = width3;
                int i7 = height3;
                Matrix matrix2 = m3;
            } else {
                canvas.save();
                canvas2.clipRect(r.left, r.top, (float) this.mWidth, r.bottom);
                int i8 = 0;
                while (i8 < this.mData.size()) {
                    float[] data2 = this.mData.get(i8);
                    if (data2 == null) {
                        width = width3;
                        height = height3;
                        m = m3;
                        i = i3;
                    } else {
                        this.mPaint.setColor(this.mConfigs.get(i8).color);
                        this.mPaint.setStrokeWidth((float) this.mConfigs.get(i8).lineWidth);
                        String name2 = this.mConfigs.get(i8).name;
                        this.mConfigs.get(i8).name = name2.replaceAll("\\([0-9]+\\.[0-9]+\\)", "(" + data2[data2.length - 1] + ")");
                        if (this.mConfigs.get(i8).lineType == Config.LINE_DASH) {
                            float[] fArr8 = new float[i3];
                            // fill-array-data instruction
                            fArr8[0] = 1082130432;
                            fArr8[1] = 1073741824;
                            this.mPaint.setPathEffect(new DashPathEffect(fArr8, 0.0f));
                        } else {
                            this.mPaint.setPathEffect((PathEffect) null);
                        }
                        int nodeType2 = this.mConfigs.get(i8).nodeType;
                        float[] tmp2 = new float[data2.length];
                        m3.mapPoints(tmp2, data2);
                        int j7 = 0;
                        while (j7 < tmp2.length) {
                            if (nodeType2 == Config.TYPE_CIRCLE) {
                                width2 = width3;
                                canvas2.drawCircle(tmp2[j7], tmp2[j7 + 1], 5.0f, this.mPaint);
                                j3 = j7;
                                nodeType = nodeType2;
                                height2 = height3;
                                name = name2;
                                z = false;
                                tmp = tmp2;
                                m2 = m3;
                                data = data2;
                            } else {
                                String name3 = name2;
                                width2 = width3;
                                if (nodeType2 == Config.TYPE_CROSS) {
                                    j3 = j7;
                                    height2 = height3;
                                    tmp = tmp2;
                                    nodeType = nodeType2;
                                    z = false;
                                    name = name3;
                                    m2 = m3;
                                    data = data2;
                                    canvas.drawLine(tmp2[j7] - 5.0f, tmp2[j7 + 1] - 5.0f, tmp2[j7] + 5.0f, tmp2[j7 + 1] + 5.0f, this.mPaint);
                                    canvas.drawLine(tmp[j3] + 5.0f, tmp[j3 + 1] - 5.0f, tmp[j3] - 5.0f, tmp[j3 + 1] + 5.0f, this.mPaint);
                                } else {
                                    j3 = j7;
                                    nodeType = nodeType2;
                                    height2 = height3;
                                    name = name3;
                                    z = false;
                                    tmp = tmp2;
                                    m2 = m3;
                                    data = data2;
                                }
                            }
                            j7 = j3 + 2;
                            tmp2 = tmp;
                            data2 = data;
                            name2 = name;
                            m3 = m2;
                            boolean z2 = z;
                            width3 = width2;
                            height3 = height2;
                            nodeType2 = nodeType;
                        }
                        int i9 = j7;
                        int i10 = nodeType2;
                        String str2 = name2;
                        width = width3;
                        height = height3;
                        m = m3;
                        float[] tmp3 = tmp2;
                        float[] data3 = data2;
                        if (data3.length <= 2) {
                            i = 2;
                        } else if (this.mConfigs.get(i8).newLineThreadshold == 0) {
                            canvas2.drawLines(tmp3, this.mPaint);
                            canvas2.drawLines(tmp3, 2, tmp3.length - 2, this.mPaint);
                            i = 2;
                        } else {
                            int i11 = 2;
                            int j8 = 0;
                            while (j8 < data3.length - i11) {
                                if (Math.abs(data3[j8 + 2] - data3[j8]) < 11.0f) {
                                    float f4 = tmp3[j8];
                                    float f5 = tmp3[j8 + 1];
                                    float f6 = tmp3[j8 + 2];
                                    j2 = j8;
                                    float f7 = tmp3[j8 + 3];
                                    i2 = i11;
                                    canvas.drawLine(f4, f5, f6, f7, this.mPaint);
                                } else {
                                    j2 = j8;
                                    i2 = i11;
                                }
                                j8 = j2 + 2;
                                i11 = i2;
                            }
                            int i12 = j8;
                            i = i11;
                        }
                    }
                    i8++;
                    m3 = m;
                    i3 = i;
                    width3 = width;
                    height3 = height;
                }
                canvas.restore();
            }
        }
    }

    private void drawSeries(Canvas canvas, RectF rect) {
        Canvas canvas2 = canvas;
        RectF rectF = rect;
        canvas.save();
        canvas.clipRect(rect);
        float[] p = {rectF.left, (rectF.top + rectF.bottom) / 2.0f};
        for (int i = 0; i < this.mConfigs.size(); i++) {
            p[0] = p[0] + 10.0f;
            this.mPaint.setColor(this.mConfigs.get(i).color);
            this.mPaint.setStrokeWidth((float) this.mConfigs.get(i).lineWidth);
            if (this.mConfigs.get(i).lineType == Config.LINE_DASH) {
                this.mPaint.setPathEffect(new DashPathEffect(new float[]{4.0f, 2.0f}, 0.0f));
            } else {
                this.mPaint.setPathEffect((PathEffect) null);
            }
            canvas.drawLine(p[0], p[1], 100.0f + p[0], p[1], this.mPaint);
            p[0] = p[0] + 50.0f;
            int nodeType = this.mConfigs.get(i).nodeType;
            if (nodeType == Config.TYPE_CIRCLE) {
                canvas2.drawCircle(p[0], p[1], 5.0f, this.mPaint);
            } else if (nodeType == Config.TYPE_CROSS) {
                canvas.drawLine(p[0] - 5.0f, p[1] - 5.0f, p[0] + 5.0f, p[1] + 5.0f, this.mPaint);
                canvas.drawLine(p[0] + 5.0f, p[1] - 5.0f, p[0] - 5.0f, p[1] + 5.0f, this.mPaint);
            }
            p[0] = p[0] + 60.0f;
            String str = this.mConfigs.get(i).name;
            canvas2.drawText(str, p[0], p[1], this.mTextPaint2);
            Rect r = new Rect();
            this.mTextPaint2.getTextBounds(str, 0, str.length(), r);
            p[0] = p[0] + ((float) r.width());
        }
        canvas.restore();
    }

    /* access modifiers changed from: private */
    public long increase(long value) {
        if (value == 0) {
            return 1;
        }
        if (String.valueOf(value).startsWith("1") || String.valueOf(value).startsWith("5")) {
            return value * 2;
        }
        return (value / 2) * 5;
    }

    private String formatTime(long miliSeconds) {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(miliSeconds));
    }
}
