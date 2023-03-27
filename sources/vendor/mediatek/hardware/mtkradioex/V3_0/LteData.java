package vendor.mediatek.hardware.mtkradioex.V3_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class LteData {
    public int band = 0;
    public int bandwidth = 0;
    public int cellId = 0;
    public int dlChannel = 0;
    public int mcc = 0;
    public int mnc = 0;
    public int rsrp = 0;
    public int rsrq = 0;
    public int rssi = 0;
    public int state = 0;
    public int txPower = 0;
    public int ulChannel = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != LteData.class) {
            return false;
        }
        LteData other = (LteData) otherObject;
        if (this.state == other.state && this.mcc == other.mcc && this.mnc == other.mnc && this.cellId == other.cellId && this.band == other.band && this.bandwidth == other.bandwidth && this.ulChannel == other.ulChannel && this.dlChannel == other.dlChannel && this.rssi == other.rssi && this.rsrq == other.rsrq && this.rsrp == other.rsrp && this.txPower == other.txPower) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.state))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mcc))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mnc))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cellId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.band))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.bandwidth))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.ulChannel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.dlChannel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rssi))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rsrq))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rsrp))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.txPower)))});
    }

    public final String toString() {
        return "{" + ".state = " + this.state + ", .mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .cellId = " + this.cellId + ", .band = " + this.band + ", .bandwidth = " + this.bandwidth + ", .ulChannel = " + this.ulChannel + ", .dlChannel = " + this.dlChannel + ", .rssi = " + this.rssi + ", .rsrq = " + this.rsrq + ", .rsrp = " + this.rsrp + ", .txPower = " + this.txPower + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(48), 0);
    }

    public static final ArrayList<LteData> readVectorFromParcel(HwParcel parcel) {
        ArrayList<LteData> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 48), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            LteData _hidl_vec_element = new LteData();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 48));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.state = _hidl_blob.getInt32(0 + _hidl_offset);
        this.mcc = _hidl_blob.getInt32(4 + _hidl_offset);
        this.mnc = _hidl_blob.getInt32(8 + _hidl_offset);
        this.cellId = _hidl_blob.getInt32(12 + _hidl_offset);
        this.band = _hidl_blob.getInt32(16 + _hidl_offset);
        this.bandwidth = _hidl_blob.getInt32(20 + _hidl_offset);
        this.ulChannel = _hidl_blob.getInt32(24 + _hidl_offset);
        this.dlChannel = _hidl_blob.getInt32(28 + _hidl_offset);
        this.rssi = _hidl_blob.getInt32(32 + _hidl_offset);
        this.rsrq = _hidl_blob.getInt32(36 + _hidl_offset);
        this.rsrp = _hidl_blob.getInt32(40 + _hidl_offset);
        this.txPower = _hidl_blob.getInt32(44 + _hidl_offset);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(48);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<LteData> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 48);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 48));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.state);
        _hidl_blob.putInt32(4 + _hidl_offset, this.mcc);
        _hidl_blob.putInt32(8 + _hidl_offset, this.mnc);
        _hidl_blob.putInt32(12 + _hidl_offset, this.cellId);
        _hidl_blob.putInt32(16 + _hidl_offset, this.band);
        _hidl_blob.putInt32(20 + _hidl_offset, this.bandwidth);
        _hidl_blob.putInt32(24 + _hidl_offset, this.ulChannel);
        _hidl_blob.putInt32(28 + _hidl_offset, this.dlChannel);
        _hidl_blob.putInt32(32 + _hidl_offset, this.rssi);
        _hidl_blob.putInt32(36 + _hidl_offset, this.rsrq);
        _hidl_blob.putInt32(40 + _hidl_offset, this.rsrp);
        _hidl_blob.putInt32(44 + _hidl_offset, this.txPower);
    }
}
