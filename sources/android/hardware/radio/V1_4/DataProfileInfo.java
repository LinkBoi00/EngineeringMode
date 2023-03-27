package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.ApnAuthType;
import android.hardware.radio.V1_0.DataProfileId;
import android.hardware.radio.V1_0.DataProfileInfoType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class DataProfileInfo {
    public String apn = new String();
    public int authType = 0;
    public int bearerBitmap;
    public boolean enabled = false;
    public int maxConns = 0;
    public int maxConnsTime = 0;
    public int mtu = 0;
    public String password = new String();
    public boolean persistent = false;
    public boolean preferred = false;
    public int profileId = 0;
    public int protocol = 0;
    public int roamingProtocol = 0;
    public int supportedApnTypesBitmap;
    public int type = 0;
    public String user = new String();
    public int waitTime = 0;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != DataProfileInfo.class) {
            return false;
        }
        DataProfileInfo other = (DataProfileInfo) otherObject;
        if (this.profileId == other.profileId && HidlSupport.deepEquals(this.apn, other.apn) && this.protocol == other.protocol && this.roamingProtocol == other.roamingProtocol && this.authType == other.authType && HidlSupport.deepEquals(this.user, other.user) && HidlSupport.deepEquals(this.password, other.password) && this.type == other.type && this.maxConnsTime == other.maxConnsTime && this.maxConns == other.maxConns && this.waitTime == other.waitTime && this.enabled == other.enabled && HidlSupport.deepEquals(Integer.valueOf(this.supportedApnTypesBitmap), Integer.valueOf(other.supportedApnTypesBitmap)) && HidlSupport.deepEquals(Integer.valueOf(this.bearerBitmap), Integer.valueOf(other.bearerBitmap)) && this.mtu == other.mtu && this.preferred == other.preferred && this.persistent == other.persistent) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.profileId))), Integer.valueOf(HidlSupport.deepHashCode(this.apn)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.protocol))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.roamingProtocol))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.authType))), Integer.valueOf(HidlSupport.deepHashCode(this.user)), Integer.valueOf(HidlSupport.deepHashCode(this.password)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxConnsTime))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxConns))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.waitTime))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.enabled))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.supportedApnTypesBitmap))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.bearerBitmap))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mtu))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.preferred))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.persistent)))});
    }

    public final String toString() {
        return "{" + ".profileId = " + DataProfileId.toString(this.profileId) + ", .apn = " + this.apn + ", .protocol = " + PdpProtocolType.toString(this.protocol) + ", .roamingProtocol = " + PdpProtocolType.toString(this.roamingProtocol) + ", .authType = " + ApnAuthType.toString(this.authType) + ", .user = " + this.user + ", .password = " + this.password + ", .type = " + DataProfileInfoType.toString(this.type) + ", .maxConnsTime = " + this.maxConnsTime + ", .maxConns = " + this.maxConns + ", .waitTime = " + this.waitTime + ", .enabled = " + this.enabled + ", .supportedApnTypesBitmap = " + ApnTypes.dumpBitfield(this.supportedApnTypesBitmap) + ", .bearerBitmap = " + RadioAccessFamily.dumpBitfield(this.bearerBitmap) + ", .mtu = " + this.mtu + ", .preferred = " + this.preferred + ", .persistent = " + this.persistent + "}";
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(112), 0);
    }

    public static final ArrayList<DataProfileInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<DataProfileInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 112), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            DataProfileInfo _hidl_vec_element = new DataProfileInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 112));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.profileId = hwBlob.getInt32(_hidl_offset + 0);
        String string = hwBlob.getString(_hidl_offset + 8);
        this.apn = string;
        parcel.readEmbeddedBuffer((long) (string.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 8 + 0, false);
        this.protocol = hwBlob.getInt32(_hidl_offset + 24);
        this.roamingProtocol = hwBlob.getInt32(_hidl_offset + 28);
        this.authType = hwBlob.getInt32(_hidl_offset + 32);
        String string2 = hwBlob.getString(_hidl_offset + 40);
        this.user = string2;
        parcel.readEmbeddedBuffer((long) (string2.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 40 + 0, false);
        String string3 = hwBlob.getString(_hidl_offset + 56);
        this.password = string3;
        parcel.readEmbeddedBuffer((long) (string3.getBytes().length + 1), _hidl_blob.handle(), _hidl_offset + 56 + 0, false);
        this.type = hwBlob.getInt32(_hidl_offset + 72);
        this.maxConnsTime = hwBlob.getInt32(_hidl_offset + 76);
        this.maxConns = hwBlob.getInt32(_hidl_offset + 80);
        this.waitTime = hwBlob.getInt32(_hidl_offset + 84);
        this.enabled = hwBlob.getBool(_hidl_offset + 88);
        this.supportedApnTypesBitmap = hwBlob.getInt32(_hidl_offset + 92);
        this.bearerBitmap = hwBlob.getInt32(_hidl_offset + 96);
        this.mtu = hwBlob.getInt32(_hidl_offset + 100);
        this.preferred = hwBlob.getBool(_hidl_offset + 104);
        this.persistent = hwBlob.getBool(_hidl_offset + 105);
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(112);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<DataProfileInfo> _hidl_vec) {
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
        _hidl_blob.putInt32(0 + _hidl_offset, this.profileId);
        _hidl_blob.putString(8 + _hidl_offset, this.apn);
        _hidl_blob.putInt32(24 + _hidl_offset, this.protocol);
        _hidl_blob.putInt32(28 + _hidl_offset, this.roamingProtocol);
        _hidl_blob.putInt32(32 + _hidl_offset, this.authType);
        _hidl_blob.putString(40 + _hidl_offset, this.user);
        _hidl_blob.putString(56 + _hidl_offset, this.password);
        _hidl_blob.putInt32(72 + _hidl_offset, this.type);
        _hidl_blob.putInt32(76 + _hidl_offset, this.maxConnsTime);
        _hidl_blob.putInt32(80 + _hidl_offset, this.maxConns);
        _hidl_blob.putInt32(84 + _hidl_offset, this.waitTime);
        _hidl_blob.putBool(88 + _hidl_offset, this.enabled);
        _hidl_blob.putInt32(92 + _hidl_offset, this.supportedApnTypesBitmap);
        _hidl_blob.putInt32(96 + _hidl_offset, this.bearerBitmap);
        _hidl_blob.putInt32(100 + _hidl_offset, this.mtu);
        _hidl_blob.putBool(104 + _hidl_offset, this.preferred);
        _hidl_blob.putBool(105 + _hidl_offset, this.persistent);
    }
}
