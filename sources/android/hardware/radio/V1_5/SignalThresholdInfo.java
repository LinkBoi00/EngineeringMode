package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SignalThresholdInfo {
    public int hysteresisDb = 0;
    public int hysteresisMs = 0;
    public boolean isEnabled = false;
    public int signalMeasurement = 0;
    public ArrayList<Integer> thresholds = new ArrayList<>();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SignalThresholdInfo.class) {
            return false;
        }
        SignalThresholdInfo other = (SignalThresholdInfo) otherObject;
        if (this.signalMeasurement == other.signalMeasurement && this.hysteresisMs == other.hysteresisMs && this.hysteresisDb == other.hysteresisDb && HidlSupport.deepEquals(this.thresholds, other.thresholds) && this.isEnabled == other.isEnabled) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.signalMeasurement))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.hysteresisMs))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.hysteresisDb))), Integer.valueOf(HidlSupport.deepHashCode(this.thresholds)), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isEnabled)))});
    }

    public final String toString() {
        return "{" + ".signalMeasurement = " + SignalMeasurementType.toString(this.signalMeasurement) + ", .hysteresisMs = " + this.hysteresisMs + ", .hysteresisDb = " + this.hysteresisDb + ", .thresholds = " + this.thresholds + ", .isEnabled = " + this.isEnabled + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(40), 0);
    }

    public static final ArrayList<SignalThresholdInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SignalThresholdInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 40), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SignalThresholdInfo _hidl_vec_element = new SignalThresholdInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 40));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.signalMeasurement = hwBlob.getInt32(_hidl_offset + 0);
        this.hysteresisMs = hwBlob.getInt32(_hidl_offset + 4);
        this.hysteresisDb = hwBlob.getInt32(_hidl_offset + 8);
        int _hidl_vec_size = hwBlob.getInt32(_hidl_offset + 16 + 8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 4), _hidl_blob.handle(), _hidl_offset + 16 + 0, true);
        this.thresholds.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            this.thresholds.add(Integer.valueOf(childBlob.getInt32((long) (_hidl_index_0 * 4))));
        }
        this.isEnabled = hwBlob.getBool(_hidl_offset + 32);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(40);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SignalThresholdInfo> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 40);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 40));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(_hidl_offset + 0, this.signalMeasurement);
        _hidl_blob.putInt32(4 + _hidl_offset, this.hysteresisMs);
        _hidl_blob.putInt32(_hidl_offset + 8, this.hysteresisDb);
        int _hidl_vec_size = this.thresholds.size();
        _hidl_blob.putInt32(_hidl_offset + 16 + 8, _hidl_vec_size);
        _hidl_blob.putBool(_hidl_offset + 16 + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 4);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            childBlob.putInt32((long) (_hidl_index_0 * 4), this.thresholds.get(_hidl_index_0).intValue());
        }
        _hidl_blob.putBlob(16 + _hidl_offset + 0, childBlob);
        _hidl_blob.putBool(32 + _hidl_offset, this.isEnabled);
    }
}
