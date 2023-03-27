package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class IccIo {
    public String aid = new String();
    public int command = 0;
    public String data = new String();
    public int fileId = 0;
    public int p1 = 0;
    public int p2 = 0;
    public int p3 = 0;
    public String path = new String();
    public String pin2 = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != IccIo.class) {
            return false;
        }
        IccIo other = (IccIo) otherObject;
        if (this.command == other.command && this.fileId == other.fileId && HidlSupport.deepEquals(this.path, other.path) && this.p1 == other.p1 && this.p2 == other.p2 && this.p3 == other.p3 && HidlSupport.deepEquals(this.data, other.data) && HidlSupport.deepEquals(this.pin2, other.pin2) && HidlSupport.deepEquals(this.aid, other.aid)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.command))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.fileId))), Integer.valueOf(HidlSupport.deepHashCode(this.path)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p1))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p2))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.p3))), Integer.valueOf(HidlSupport.deepHashCode(this.data)), Integer.valueOf(HidlSupport.deepHashCode(this.pin2)), Integer.valueOf(HidlSupport.deepHashCode(this.aid))});
    }

    public final String toString() {
        return "{" + ".command = " + this.command + ", .fileId = " + this.fileId + ", .path = " + this.path + ", .p1 = " + this.p1 + ", .p2 = " + this.p2 + ", .p3 = " + this.p3 + ", .data = " + this.data + ", .pin2 = " + this.pin2 + ", .aid = " + this.aid + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(88), 0);
    }

    public static final ArrayList<IccIo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<IccIo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 88), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            IccIo _hidl_vec_element = new IccIo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 88));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.command = hwBlob.getInt32(_hidl_offset + 0);
        this.fileId = hwBlob.getInt32(_hidl_offset + 4);
        String string = hwBlob.getString(_hidl_offset + 8);
        this.path = string;
        parcel.readEmbeddedBuffer((long) (string.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
        this.p1 = hwBlob.getInt32(_hidl_offset + 24);
        this.p2 = hwBlob.getInt32(_hidl_offset + 28);
        this.p3 = hwBlob.getInt32(_hidl_offset + 32);
        String string2 = hwBlob.getString(_hidl_offset + 40);
        this.data = string2;
        parcel.readEmbeddedBuffer((long) (string2.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 40 + 0, false);
        String string3 = hwBlob.getString(_hidl_offset + 56);
        this.pin2 = string3;
        parcel.readEmbeddedBuffer((long) (string3.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 56 + 0, false);
        String string4 = hwBlob.getString(_hidl_offset + 72);
        this.aid = string4;
        parcel.readEmbeddedBuffer((long) (string4.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 72 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(88);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<IccIo> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 88);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 88));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(0 + _hidl_offset, this.command);
        _hidl_blob.putInt32(4 + _hidl_offset, this.fileId);
        _hidl_blob.putString(8 + _hidl_offset, this.path);
        _hidl_blob.putInt32(24 + _hidl_offset, this.p1);
        _hidl_blob.putInt32(28 + _hidl_offset, this.p2);
        _hidl_blob.putInt32(32 + _hidl_offset, this.p3);
        _hidl_blob.putString(40 + _hidl_offset, this.data);
        _hidl_blob.putString(56 + _hidl_offset, this.pin2);
        _hidl_blob.putString(72 + _hidl_offset, this.aid);
    }
}
