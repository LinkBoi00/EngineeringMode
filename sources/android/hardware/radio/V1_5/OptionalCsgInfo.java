package android.hardware.radio.V1_5;

import android.hidl.safe_union.V1_0.Monostate;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class OptionalCsgInfo {
    private byte hidl_d;
    private Object hidl_o;

    public OptionalCsgInfo() {
        this.hidl_d = 0;
        this.hidl_o = null;
        this.hidl_o = new Monostate();
    }

    public static final class hidl_discriminator {
        public static final byte csgInfo = 1;
        public static final byte noinit = 0;

        public static final String getName(byte value) {
            switch (value) {
                case 0:
                    return "noinit";
                case 1:
                    return "csgInfo";
                default:
                    return "Unknown";
            }
        }

        private hidl_discriminator() {
        }
    }

    public void noinit(Monostate noinit) {
        this.hidl_d = 0;
        this.hidl_o = noinit;
    }

    public Monostate noinit() {
        if (this.hidl_d != 0) {
            Object obj = this.hidl_o;
            String className = obj != null ? obj.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + this.hidl_d + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + ".");
        }
        Object obj2 = this.hidl_o;
        if (obj2 == null || Monostate.class.isInstance(obj2)) {
            return (Monostate) this.hidl_o;
        }
        throw new Error("Union is in a corrupted state.");
    }

    public void csgInfo(ClosedSubscriberGroupInfo csgInfo) {
        this.hidl_d = 1;
        this.hidl_o = csgInfo;
    }

    public ClosedSubscriberGroupInfo csgInfo() {
        if (this.hidl_d != 1) {
            Object obj = this.hidl_o;
            String className = obj != null ? obj.getClass().getName() : "null";
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + this.hidl_d + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + className + ".");
        }
        Object obj2 = this.hidl_o;
        if (obj2 == null || ClosedSubscriberGroupInfo.class.isInstance(obj2)) {
            return (ClosedSubscriberGroupInfo) this.hidl_o;
        }
        throw new Error("Union is in a corrupted state.");
    }

    public byte getDiscriminator() {
        return this.hidl_d;
    }

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != OptionalCsgInfo.class) {
            return false;
        }
        OptionalCsgInfo other = (OptionalCsgInfo) otherObject;
        if (this.hidl_d == other.hidl_d && HidlSupport.deepEquals(this.hidl_o, other.hidl_o)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d)))});
    }

    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        switch (this.hidl_d) {
            case 0:
                builder.append(".noinit = ");
                builder.append(noinit());
                break;
            case 1:
                builder.append(".csgInfo = ");
                builder.append(csgInfo());
                break;
            default:
                throw new Error("Unknown union discriminator (value: " + this.hidl_d + ").");
        }
        builder.append("}");
        return builder.toString();
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(40), 0);
    }

    public static final ArrayList<OptionalCsgInfo> readVectorFromParcel(HwParcel parcel) {
        ArrayList<OptionalCsgInfo> _hidl_vec = new ArrayList<>();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 40), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            OptionalCsgInfo _hidl_vec_element = new OptionalCsgInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 40));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        byte int8 = _hidl_blob.getInt8(0 + _hidl_offset);
        this.hidl_d = int8;
        switch (int8) {
            case 0:
                Monostate monostate = new Monostate();
                this.hidl_o = monostate;
                monostate.readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            case 1:
                ClosedSubscriberGroupInfo closedSubscriberGroupInfo = new ClosedSubscriberGroupInfo();
                this.hidl_o = closedSubscriberGroupInfo;
                closedSubscriberGroupInfo.readEmbeddedFromParcel(parcel, _hidl_blob, 8 + _hidl_offset);
                return;
            default:
                throw new IllegalStateException("Unknown union discriminator (value: " + this.hidl_d + ").");
        }
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(40);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<OptionalCsgInfo> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 40);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            _hidl_vec.get(_hidl_index_0).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 40));
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt8(0 + _hidl_offset, this.hidl_d);
        switch (this.hidl_d) {
            case 0:
                noinit().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            case 1:
                csgInfo().writeEmbeddedToBlob(_hidl_blob, 8 + _hidl_offset);
                return;
            default:
                throw new Error("Unknown union discriminator (value: " + this.hidl_d + ").");
        }
    }
}
