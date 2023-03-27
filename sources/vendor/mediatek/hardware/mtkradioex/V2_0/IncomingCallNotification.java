package vendor.mediatek.hardware.mtkradioex.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class IncomingCallNotification {
    public String callId = new String();
    public String callMode = new String();
    public String number = new String();
    public String redirectNumber = new String();
    public String seqNo = new String();
    public String toNumber = new String();
    public String type = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != IncomingCallNotification.class) {
            return false;
        }
        IncomingCallNotification other = (IncomingCallNotification) otherObject;
        if (HidlSupport.deepEquals(this.callId, other.callId) && HidlSupport.deepEquals(this.number, other.number) && HidlSupport.deepEquals(this.type, other.type) && HidlSupport.deepEquals(this.callMode, other.callMode) && HidlSupport.deepEquals(this.seqNo, other.seqNo) && HidlSupport.deepEquals(this.redirectNumber, other.redirectNumber) && HidlSupport.deepEquals(this.toNumber, other.toNumber)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(this.callId)), Integer.valueOf(HidlSupport.deepHashCode(this.number)), Integer.valueOf(HidlSupport.deepHashCode(this.type)), Integer.valueOf(HidlSupport.deepHashCode(this.callMode)), Integer.valueOf(HidlSupport.deepHashCode(this.seqNo)), Integer.valueOf(HidlSupport.deepHashCode(this.redirectNumber)), Integer.valueOf(HidlSupport.deepHashCode(this.toNumber))});
    }

    public final String toString() {
        return "{" + ".callId = " + this.callId + ", .number = " + this.number + ", .type = " + this.type + ", .callMode = " + this.callMode + ", .seqNo = " + this.seqNo + ", .redirectNumber = " + this.redirectNumber + ", .toNumber = " + this.toNumber + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(112), 0);
    }

    public static final ArrayList<IncomingCallNotification> readVectorFromParcel(HwParcel parcel) {
        ArrayList<IncomingCallNotification> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 112), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            IncomingCallNotification _hidl_vec_element = new IncomingCallNotification();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 112));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        String string = hwBlob.getString(_hidl_offset + 0);
        this.callId = string;
        parcel.readEmbeddedBuffer((long) (string.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 0 + 0, false);
        String string2 = hwBlob.getString(_hidl_offset + 16);
        this.number = string2;
        parcel.readEmbeddedBuffer((long) (string2.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 16 + 0, false);
        String string3 = hwBlob.getString(_hidl_offset + 32);
        this.type = string3;
        parcel.readEmbeddedBuffer((long) (string3.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 32 + 0, false);
        String string4 = hwBlob.getString(_hidl_offset + 48);
        this.callMode = string4;
        parcel.readEmbeddedBuffer((long) (string4.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 48 + 0, false);
        String string5 = hwBlob.getString(_hidl_offset + 64);
        this.seqNo = string5;
        parcel.readEmbeddedBuffer((long) (string5.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 64 + 0, false);
        String string6 = hwBlob.getString(_hidl_offset + 80);
        this.redirectNumber = string6;
        parcel.readEmbeddedBuffer((long) (string6.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 80 + 0, false);
        String string7 = hwBlob.getString(_hidl_offset + 96);
        this.toNumber = string7;
        parcel.readEmbeddedBuffer((long) (string7.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 96 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(112);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<IncomingCallNotification> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 112);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 112));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putString(0 + _hidl_offset, this.callId);
        _hidl_blob.putString(16 + _hidl_offset, this.number);
        _hidl_blob.putString(32 + _hidl_offset, this.type);
        _hidl_blob.putString(48 + _hidl_offset, this.callMode);
        _hidl_blob.putString(64 + _hidl_offset, this.seqNo);
        _hidl_blob.putString(80 + _hidl_offset, this.redirectNumber);
        _hidl_blob.putString(96 + _hidl_offset, this.toNumber);
    }
}
