package vendor.mediatek.hardware.mtkradioex.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.support.v4.media.subtitle.Cea708CCParser;
import java.util.ArrayList;
import java.util.Objects;

public final class PlmnMvnoInfo {
    public String cdmaImsi = new String();
    public String cdmaPlmn = new String();
    public String cdmaSpn = new String();
    public String gid1 = new String();
    public String gsmImsi = new String();
    public String gsmPlmn = new String();
    public String gsmSpn = new String();
    public String impi = new String();
    public String pnn = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != PlmnMvnoInfo.class) {
            return false;
        }
        PlmnMvnoInfo other = (PlmnMvnoInfo) otherObject;
        if (HidlSupport.deepEquals(this.gsmPlmn, other.gsmPlmn) && HidlSupport.deepEquals(this.cdmaPlmn, other.cdmaPlmn) && HidlSupport.deepEquals(this.gsmSpn, other.gsmSpn) && HidlSupport.deepEquals(this.cdmaSpn, other.cdmaSpn) && HidlSupport.deepEquals(this.gsmImsi, other.gsmImsi) && HidlSupport.deepEquals(this.cdmaImsi, other.cdmaImsi) && HidlSupport.deepEquals(this.gid1, other.gid1) && HidlSupport.deepEquals(this.pnn, other.pnn) && HidlSupport.deepEquals(this.impi, other.impi)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(this.gsmPlmn)), Integer.valueOf(HidlSupport.deepHashCode(this.cdmaPlmn)), Integer.valueOf(HidlSupport.deepHashCode(this.gsmSpn)), Integer.valueOf(HidlSupport.deepHashCode(this.cdmaSpn)), Integer.valueOf(HidlSupport.deepHashCode(this.gsmImsi)), Integer.valueOf(HidlSupport.deepHashCode(this.cdmaImsi)), Integer.valueOf(HidlSupport.deepHashCode(this.gid1)), Integer.valueOf(HidlSupport.deepHashCode(this.pnn)), Integer.valueOf(HidlSupport.deepHashCode(this.impi))});
    }

    public final String toString() {
        return "{" + ".gsmPlmn = " + this.gsmPlmn + ", .cdmaPlmn = " + this.cdmaPlmn + ", .gsmSpn = " + this.gsmSpn + ", .cdmaSpn = " + this.cdmaSpn + ", .gsmImsi = " + this.gsmImsi + ", .cdmaImsi = " + this.cdmaImsi + ", .gid1 = " + this.gid1 + ", .pnn = " + this.pnn + ", .impi = " + this.impi + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(144), 0);
    }

    public static final ArrayList<PlmnMvnoInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<PlmnMvnoInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * Cea708CCParser.Const.CODE_C1_SPA), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            PlmnMvnoInfo _hidl_vec_element = new PlmnMvnoInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * Cea708CCParser.Const.CODE_C1_SPA));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        String string = hwBlob.getString(_hidl_offset + 0);
        this.gsmPlmn = string;
        parcel.readEmbeddedBuffer((long) (string.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 0 + 0, false);
        String string2 = hwBlob.getString(_hidl_offset + 16);
        this.cdmaPlmn = string2;
        parcel.readEmbeddedBuffer((long) (string2.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 16 + 0, false);
        String string3 = hwBlob.getString(_hidl_offset + 32);
        this.gsmSpn = string3;
        parcel.readEmbeddedBuffer((long) (string3.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 32 + 0, false);
        String string4 = hwBlob.getString(_hidl_offset + 48);
        this.cdmaSpn = string4;
        parcel.readEmbeddedBuffer((long) (string4.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 48 + 0, false);
        String string5 = hwBlob.getString(_hidl_offset + 64);
        this.gsmImsi = string5;
        parcel.readEmbeddedBuffer((long) (string5.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 64 + 0, false);
        String string6 = hwBlob.getString(_hidl_offset + 80);
        this.cdmaImsi = string6;
        parcel.readEmbeddedBuffer((long) (string6.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 80 + 0, false);
        String string7 = hwBlob.getString(_hidl_offset + 96);
        this.gid1 = string7;
        parcel.readEmbeddedBuffer((long) (string7.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 96 + 0, false);
        String string8 = hwBlob.getString(_hidl_offset + 112);
        this.pnn = string8;
        parcel.readEmbeddedBuffer((long) (string8.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 112 + 0, false);
        String string9 = hwBlob.getString(_hidl_offset + 128);
        this.impi = string9;
        parcel.readEmbeddedBuffer((long) (string9.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 128 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(Cea708CCParser.Const.CODE_C1_SPA);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<PlmnMvnoInfo> _hidl_vec) {
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
        _hidl_blob.putString(0 + _hidl_offset, this.gsmPlmn);
        _hidl_blob.putString(16 + _hidl_offset, this.cdmaPlmn);
        _hidl_blob.putString(32 + _hidl_offset, this.gsmSpn);
        _hidl_blob.putString(48 + _hidl_offset, this.cdmaSpn);
        _hidl_blob.putString(64 + _hidl_offset, this.gsmImsi);
        _hidl_blob.putString(80 + _hidl_offset, this.cdmaImsi);
        _hidl_blob.putString(96 + _hidl_offset, this.gid1);
        _hidl_blob.putString(112 + _hidl_offset, this.pnn);
        _hidl_blob.putString(128 + _hidl_offset, this.impi);
    }
}
