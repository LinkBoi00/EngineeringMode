package android.hardware.radio.V1_5;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityLte {
    public ArrayList<String> additionalPlmns = new ArrayList<>();
    public ArrayList<Integer> bands = new ArrayList<>();
    public android.hardware.radio.V1_2.CellIdentityLte base = new android.hardware.radio.V1_2.CellIdentityLte();
    public OptionalCsgInfo optionalCsgInfo = new OptionalCsgInfo();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != CellIdentityLte.class) {
            return false;
        }
        CellIdentityLte other = (CellIdentityLte) otherObject;
        if (HidlSupport.deepEquals(this.base, other.base) && HidlSupport.deepEquals(this.additionalPlmns, other.additionalPlmns) && HidlSupport.deepEquals(this.optionalCsgInfo, other.optionalCsgInfo) && HidlSupport.deepEquals(this.bands, other.bands)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.additionalPlmns)), Integer.valueOf(HidlSupport.deepHashCode(this.optionalCsgInfo)), Integer.valueOf(HidlSupport.deepHashCode(this.bands))});
    }

    public final String toString() {
        return "{" + ".base = " + this.base + ", .additionalPlmns = " + this.additionalPlmns + ", .optionalCsgInfo = " + this.optionalCsgInfo + ", .bands = " + this.bands + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(160), 0);
    }

    public static final ArrayList<CellIdentityLte> readVectorFromParcel(HwParcel parcel) {
        ArrayList<CellIdentityLte> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 160), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            CellIdentityLte _hidl_vec_element = new CellIdentityLte();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 160));
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
        int _hidl_index_0 = 0;
        while (_hidl_index_0 < _hidl_vec_size) {
            new String();
            String _hidl_vec_element = childBlob.getString((long) (_hidl_index_0 * 16));
            parcel.readEmbeddedBuffer((long) (_hidl_vec_element.getBytes().length + 1), childBlob.handle(), (long) ((_hidl_index_0 * 16) + 0), false);
            this.additionalPlmns.add(_hidl_vec_element);
            _hidl_index_0++;
            childBlob = childBlob;
        }
        int i = _hidl_index_0;
        HwBlob hwBlob2 = childBlob;
        this.optionalCsgInfo.readEmbeddedFromParcel(hwParcel, hwBlob, _hidl_offset + 104);
        int _hidl_vec_size2 = hwBlob.getInt32(_hidl_offset + 144 + 8);
        HwBlob childBlob2 = parcel.readEmbeddedBuffer((long) (_hidl_vec_size2 * 4), _hidl_blob.handle(), 0 + _hidl_offset + 144, true);
        this.bands.clear();
        for (int _hidl_index_02 = 0; _hidl_index_02 < _hidl_vec_size2; _hidl_index_02++) {
            this.bands.add(Integer.valueOf(childBlob2.getInt32((long) (_hidl_index_02 * 4))));
        }
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(160);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<CellIdentityLte> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 160);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 160));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.base.writeEmbeddedToBlob(hwBlob, _hidl_offset + 0);
        int _hidl_vec_size = this.additionalPlmns.size();
        hwBlob.putInt32(_hidl_offset + 88 + 8, _hidl_vec_size);
        hwBlob.putBool(_hidl_offset + 88 + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 16);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            childBlob.putString((long) (_hidl_index_0 * 16), this.additionalPlmns.get(_hidl_index_0));
        }
        hwBlob.putBlob(_hidl_offset + 88 + 0, childBlob);
        this.optionalCsgInfo.writeEmbeddedToBlob(hwBlob, _hidl_offset + 104);
        int _hidl_vec_size2 = this.bands.size();
        hwBlob.putInt32(_hidl_offset + 144 + 8, _hidl_vec_size2);
        hwBlob.putBool(_hidl_offset + 144 + 12, false);
        HwBlob childBlob2 = new HwBlob(_hidl_vec_size2 * 4);
        for (int _hidl_index_02 = 0; _hidl_index_02 < _hidl_vec_size2; _hidl_index_02++) {
            childBlob2.putInt32((long) (_hidl_index_02 * 4), this.bands.get(_hidl_index_02).intValue());
        }
        hwBlob.putBlob(_hidl_offset + 144 + 0, childBlob2);
    }
}
