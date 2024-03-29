package android.hardware.radio.V1_5;

import android.hardware.radio.V1_2.WcdmaSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.support.v4.media.subtitle.Cea708CCParser;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoWcdma {
    public CellIdentityWcdma cellIdentityWcdma = new CellIdentityWcdma();
    public WcdmaSignalStrength signalStrengthWcdma = new WcdmaSignalStrength();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != CellInfoWcdma.class) {
            return false;
        }
        CellInfoWcdma other = (CellInfoWcdma) otherObject;
        if (HidlSupport.deepEquals(this.cellIdentityWcdma, other.cellIdentityWcdma) && HidlSupport.deepEquals(this.signalStrengthWcdma, other.signalStrengthWcdma)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityWcdma)), Integer.valueOf(HidlSupport.deepHashCode(this.signalStrengthWcdma))});
    }

    public final String toString() {
        return "{" + ".cellIdentityWcdma = " + this.cellIdentityWcdma + ", .signalStrengthWcdma = " + this.signalStrengthWcdma + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(152), 0);
    }

    public static final ArrayList<CellInfoWcdma> readVectorFromParcel(HwParcel parcel) {
        ArrayList<CellInfoWcdma> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * Cea708CCParser.Const.CODE_C1_DF0), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            CellInfoWcdma _hidl_vec_element = new CellInfoWcdma();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * Cea708CCParser.Const.CODE_C1_DF0));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.cellIdentityWcdma.readEmbeddedFromParcel(parcel, _hidl_blob, 0 + _hidl_offset);
        this.signalStrengthWcdma.readEmbeddedFromParcel(parcel, _hidl_blob, 136 + _hidl_offset);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(Cea708CCParser.Const.CODE_C1_DF0);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<CellInfoWcdma> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * Cea708CCParser.Const.CODE_C1_DF0);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * Cea708CCParser.Const.CODE_C1_DF0));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        this.cellIdentityWcdma.writeEmbeddedToBlob(_hidl_blob, 0 + _hidl_offset);
        this.signalStrengthWcdma.writeEmbeddedToBlob(_hidl_blob, 136 + _hidl_offset);
    }
}
