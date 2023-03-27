package android.hardware.radio.V1_5;

import android.hardware.radio.V1_4.NrSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.support.v4.media.subtitle.Cea708CCParser;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoNr {
    public CellIdentityNr cellIdentityNr = new CellIdentityNr();
    public NrSignalStrength signalStrengthNr = new NrSignalStrength();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != CellInfoNr.class) {
            return false;
        }
        CellInfoNr other = (CellInfoNr) otherObject;
        if (HidlSupport.deepEquals(this.cellIdentityNr, other.cellIdentityNr) && HidlSupport.deepEquals(this.signalStrengthNr, other.signalStrengthNr)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentityNr)), Integer.valueOf(HidlSupport.deepHashCode(this.signalStrengthNr))});
    }

    public final String toString() {
        return "{" + ".cellIdentityNr = " + this.cellIdentityNr + ", .signalStrengthNr = " + this.signalStrengthNr + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(144), 0);
    }

    public static final ArrayList<CellInfoNr> readVectorFromParcel(HwParcel parcel) {
        ArrayList<CellInfoNr> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * Cea708CCParser.Const.CODE_C1_SPA), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            CellInfoNr _hidl_vec_element = new CellInfoNr();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * Cea708CCParser.Const.CODE_C1_SPA));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        this.cellIdentityNr.readEmbeddedFromParcel(parcel, _hidl_blob, 0 + _hidl_offset);
        this.signalStrengthNr.readEmbeddedFromParcel(parcel, _hidl_blob, 120 + _hidl_offset);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(Cea708CCParser.Const.CODE_C1_SPA);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<CellInfoNr> _hidl_vec) {
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
        this.cellIdentityNr.writeEmbeddedToBlob(_hidl_blob, 0 + _hidl_offset);
        this.signalStrengthNr.writeEmbeddedToBlob(_hidl_blob, 120 + _hidl_offset);
    }
}
