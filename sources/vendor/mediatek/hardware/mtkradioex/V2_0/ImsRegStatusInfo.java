package vendor.mediatek.hardware.mtkradioex.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class ImsRegStatusInfo {
    public int account_id = 0;
    public int error_code = 0;
    public String error_msg = new String();
    public int expire_time = 0;
    public int report_type = 0;
    public String uri = new String();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != ImsRegStatusInfo.class) {
            return false;
        }
        ImsRegStatusInfo other = (ImsRegStatusInfo) otherObject;
        if (this.report_type == other.report_type && this.account_id == other.account_id && this.expire_time == other.expire_time && this.error_code == other.error_code && HidlSupport.deepEquals(this.uri, other.uri) && HidlSupport.deepEquals(this.error_msg, other.error_msg)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.report_type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.account_id))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.expire_time))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.error_code))), Integer.valueOf(HidlSupport.deepHashCode(this.uri)), Integer.valueOf(HidlSupport.deepHashCode(this.error_msg))});
    }

    public final String toString() {
        return "{" + ".report_type = " + this.report_type + ", .account_id = " + this.account_id + ", .expire_time = " + this.expire_time + ", .error_code = " + this.error_code + ", .uri = " + this.uri + ", .error_msg = " + this.error_msg + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(48), 0);
    }

    public static final ArrayList<ImsRegStatusInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<ImsRegStatusInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 48), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            ImsRegStatusInfo _hidl_vec_element = new ImsRegStatusInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 48));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.report_type = hwBlob.getInt32(_hidl_offset + 0);
        this.account_id = hwBlob.getInt32(_hidl_offset + 4);
        this.expire_time = hwBlob.getInt32(_hidl_offset + 8);
        this.error_code = hwBlob.getInt32(_hidl_offset + 12);
        String string = hwBlob.getString(_hidl_offset + 16);
        this.uri = string;
        parcel.readEmbeddedBuffer((long) (string.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 16 + 0, false);
        String string2 = hwBlob.getString(_hidl_offset + 32);
        this.error_msg = string2;
        parcel.readEmbeddedBuffer((long) (string2.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 32 + 0, false);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(48);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<ImsRegStatusInfo> _hidl_vec) {
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
        _hidl_blob.putInt32(0 + _hidl_offset, this.report_type);
        _hidl_blob.putInt32(4 + _hidl_offset, this.account_id);
        _hidl_blob.putInt32(8 + _hidl_offset, this.expire_time);
        _hidl_blob.putInt32(12 + _hidl_offset, this.error_code);
        _hidl_blob.putString(16 + _hidl_offset, this.uri);
        _hidl_blob.putString(32 + _hidl_offset, this.error_msg);
    }
}
