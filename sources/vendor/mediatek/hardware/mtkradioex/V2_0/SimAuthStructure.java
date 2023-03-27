package vendor.mediatek.hardware.mtkradioex.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SimAuthStructure {
    public int mode = 0;
    public String param1 = new String();
    public String param2 = new String();
    public int sessionId = 0;
    public int tag = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != SimAuthStructure.class) {
            return false;
        }
        SimAuthStructure other = (SimAuthStructure) otherObject;
        if (this.sessionId == other.sessionId && this.mode == other.mode && HidlSupport.deepEquals(this.param1, other.param1) && HidlSupport.deepEquals(this.param2, other.param2) && this.tag == other.tag) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sessionId))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mode))), Integer.valueOf(HidlSupport.deepHashCode(this.param1)), Integer.valueOf(HidlSupport.deepHashCode(this.param2)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.tag)))});
    }

    public final String toString() {
        return "{" + ".sessionId = " + this.sessionId + ", .mode = " + this.mode + ", .param1 = " + this.param1 + ", .param2 = " + this.param2 + ", .tag = " + this.tag + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(48), 0);
    }

    public static final ArrayList<SimAuthStructure> readVectorFromParcel(HwParcel parcel) {
        ArrayList<SimAuthStructure> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 48), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            SimAuthStructure _hidl_vec_element = new SimAuthStructure();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 48));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.sessionId = hwBlob.getInt32(_hidl_offset + 0);
        this.mode = hwBlob.getInt32(_hidl_offset + 4);
        String string = hwBlob.getString(_hidl_offset + 8);
        this.param1 = string;
        parcel.readEmbeddedBuffer((long) (string.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
        String string2 = hwBlob.getString(_hidl_offset + 24);
        this.param2 = string2;
        parcel.readEmbeddedBuffer((long) (string2.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 24 + 0, false);
        this.tag = hwBlob.getInt32(_hidl_offset + 40);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(48);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<SimAuthStructure> _hidl_vec) {
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
        _hidl_blob.putInt32(0 + _hidl_offset, this.sessionId);
        _hidl_blob.putInt32(4 + _hidl_offset, this.mode);
        _hidl_blob.putString(8 + _hidl_offset, this.param1);
        _hidl_blob.putString(24 + _hidl_offset, this.param2);
        _hidl_blob.putInt32(40 + _hidl_offset, this.tag);
    }
}
