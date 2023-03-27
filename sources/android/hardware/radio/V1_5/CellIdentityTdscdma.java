package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.support.v4.media.subtitle.Cea708CCParser;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityTdscdma {
    public ArrayList<String> additionalPlmns = new ArrayList<>();
    public android.hardware.radio.V1_2.CellIdentityTdscdma base = new android.hardware.radio.V1_2.CellIdentityTdscdma();
    public OptionalCsgInfo optionalCsgInfo = new OptionalCsgInfo();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != CellIdentityTdscdma.class) {
            return false;
        }
        CellIdentityTdscdma other = (CellIdentityTdscdma) otherObject;
        if (HidlSupport.deepEquals(this.base, other.base) && HidlSupport.deepEquals(this.additionalPlmns, other.additionalPlmns) && HidlSupport.deepEquals(this.optionalCsgInfo, other.optionalCsgInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.additionalPlmns)), Integer.valueOf(HidlSupport.deepHashCode(this.optionalCsgInfo))});
    }

    public final String toString() {
        return "{" + ".base = " + this.base + ", .additionalPlmns = " + this.additionalPlmns + ", .optionalCsgInfo = " + this.optionalCsgInfo + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(144), 0);
    }

    public static final ArrayList<CellIdentityTdscdma> readVectorFromParcel(HwParcel parcel) {
        ArrayList<CellIdentityTdscdma> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * Cea708CCParser.Const.CODE_C1_SPA), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            CellIdentityTdscdma _hidl_vec_element = new CellIdentityTdscdma();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * Cea708CCParser.Const.CODE_C1_SPA));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwParcel hwParcel = parcel;
        HwBlob hwBlob = _hidl_blob;
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, _hidl_offset + 0);
        int _hidl_vec_size = hwBlob.getInt32(_hidl_offset + 88 + 8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 16), _hidl_blob.handle(), _hidl_offset + 88 + 0, true);
        this.additionalPlmns.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            new String();
            String _hidl_vec_element = childBlob.getString((long) (_hidl_index_0 * 16));
            parcel.readEmbeddedBuffer((long) (_hidl_vec_element.getBytes().length + 1), childBlob.handle(), (long) ((_hidl_index_0 * 16) + 0), false);
            this.additionalPlmns.add(_hidl_vec_element);
        }
        this.optionalCsgInfo.readEmbeddedFromParcel(hwParcel, hwBlob, _hidl_offset + 104);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(Cea708CCParser.Const.CODE_C1_SPA);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<CellIdentityTdscdma> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * Cea708CCParser.Const.CODE_C1_SPA);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * Cea708CCParser.Const.CODE_C1_SPA));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        this.base.writeEmbeddedToBlob(_hidl_blob, _hidl_offset + 0);
        int _hidl_vec_size = this.additionalPlmns.size();
        _hidl_blob.putInt32(_hidl_offset + 88 + 8, _hidl_vec_size);
        _hidl_blob.putBool(_hidl_offset + 88 + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 16);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            childBlob.putString((long) (_hidl_index_0 * 16), this.additionalPlmns.get(_hidl_index_0));
        }
        _hidl_blob.putBlob(88 + _hidl_offset + 0, childBlob);
        this.optionalCsgInfo.writeEmbeddedToBlob(_hidl_blob, 104 + _hidl_offset);
    }
}
