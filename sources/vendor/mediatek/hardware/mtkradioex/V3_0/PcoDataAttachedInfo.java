package vendor.mediatek.hardware.mtkradioex.V3_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class PcoDataAttachedInfo {
    public String apnName = new String();
    public String bearerProto = new String();
    public int cid = 0;
    public ArrayList<Byte> contents = new ArrayList<>();
    public int pcoId = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != PcoDataAttachedInfo.class) {
            return false;
        }
        PcoDataAttachedInfo other = (PcoDataAttachedInfo) otherObject;
        if (this.cid == other.cid && HidlSupport.deepEquals(this.apnName, other.apnName) && HidlSupport.deepEquals(this.bearerProto, other.bearerProto) && this.pcoId == other.pcoId && HidlSupport.deepEquals(this.contents, other.contents)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cid))), Integer.valueOf(HidlSupport.deepHashCode(this.apnName)), Integer.valueOf(HidlSupport.deepHashCode(this.bearerProto)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pcoId))), Integer.valueOf(HidlSupport.deepHashCode(this.contents))});
    }

    public final String toString() {
        return "{" + ".cid = " + this.cid + ", .apnName = " + this.apnName + ", .bearerProto = " + this.bearerProto + ", .pcoId = " + this.pcoId + ", .contents = " + this.contents + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(64), 0);
    }

    public static final ArrayList<PcoDataAttachedInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<PcoDataAttachedInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 64), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            PcoDataAttachedInfo _hidl_vec_element = new PcoDataAttachedInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 64));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.cid = hwBlob.getInt32(_hidl_offset + 0);
        String string = hwBlob.getString(_hidl_offset + 8);
        this.apnName = string;
        parcel.readEmbeddedBuffer((long) (string.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
        String string2 = hwBlob.getString(_hidl_offset + 24);
        this.bearerProto = string2;
        parcel.readEmbeddedBuffer((long) (string2.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 24 + 0, false);
        this.pcoId = hwBlob.getInt32(_hidl_offset + 40);
        int _hidl_vec_size = hwBlob.getInt32(_hidl_offset + 48 + 8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 1), _hidl_blob.handle(), _hidl_offset + 48 + 0, true);
        this.contents.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            this.contents.add(Byte.valueOf(childBlob.getInt8((long) (_hidl_index_0 * 1))));
        }
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(64);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<PcoDataAttachedInfo> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 64);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 64));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(_hidl_offset + 0, this.cid);
        _hidl_blob.putString(_hidl_offset + 8, this.apnName);
        _hidl_blob.putString(24 + _hidl_offset, this.bearerProto);
        _hidl_blob.putInt32(40 + _hidl_offset, this.pcoId);
        int _hidl_vec_size = this.contents.size();
        _hidl_blob.putInt32(_hidl_offset + 48 + 8, _hidl_vec_size);
        _hidl_blob.putBool(_hidl_offset + 48 + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 1);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            childBlob.putInt8((long) (_hidl_index_0 * 1), this.contents.get(_hidl_index_0).byteValue());
        }
        _hidl_blob.putBlob(48 + _hidl_offset + 0, childBlob);
    }
}
