package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import com.mediatek.engineermode.Elog;
import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: MDMComponentICD */
class ONasInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContentICD.MSG_TYPE_ICD_EVENT, MDMContentICD.MSG_ID_VGNAS_MM_STATE_VALUE, MDMContentICD.MSG_ID_VGNAS_MM_FAILURE_EVENT_CAUSE, MDMContentICD.MSG_ID_VGNAS_SM_CONTEXT_INFO, MDMContentICD.MSG_ID_FAILURE_EVENT_CAUSE, MDMContentICD.MSG_ID_ENAS_EMM_CONTEXT_INFO};
    int[][] eventAddr = {new int[]{8, 40, 16}, new int[]{8, 40, 16}};
    int[][] mCauseAddr = {new int[]{8, 8, 16}};
    HashMap<Integer, String> mCauseMapping = new HashMap<Integer, String>() {
        {
            put(3, "Illegal UE");
            put(5, "PEI not accepted");
            put(6, "Illegal ME");
            put(7, "5GS services not allowed");
            put(9, "UE identity cannot be derived by the network");
            put(10, "Implicitly de-registered");
            put(11, "PLMN not allowed");
            put(12, "Tracking area not allowed");
            put(13, "Roaming not allowed in this tracking area");
            put(15, "No suitable cells in tracking area");
            put(20, "MAC failure");
            put(21, "Synch failure");
            put(22, "Congestion");
            put(23, "UE security capabilities mismatch");
            put(24, "Security mode rejected, unspecified");
            put(26, "Non-5G authentication unacceptable");
            put(27, "N1 mode not allowed");
            put(28, "Restricted service area");
            put(31, "Redirection to EPC required");
            put(43, "LADN not available");
            put(62, "No network slices available");
            put(65, "Maximum number of PDU sessions reached");
            put(67, "Insufficient resources for specific slice and DNN");
            put(69, "Insufficient resources for specific slice");
            put(71, "ngKSI already in use");
            put(72, "Non-3GPP access to 5GCN not allowed");
            put(73, "Serving network not authorized");
            put(74, "Temporarily not authorized for this SNPN");
            put(75, "Permanently not authorized for this SNPN");
            put(76, "Not authorized for this CAG or authorized for CAG cells only");
            put(90, "Payload was not forwarded");
            put(91, "DNN not supported or not subscribed in the slice");
            put(92, "Insufficient user-plane resources for the PDU session");
            put(96, "Semantically incorrect message");
            put(96, "Invalid mandatory information");
            put(97, "Message type non-existent or not implemented");
            put(98, "Message type not compatible with the protocol state");
            put(99, "Information element non-existent or not implemented");
            put(100, "Conditional IE error");
            put(102, "Message not compatible with the protocol state");
            put(111, "Protocol error, unspecified");
        }
    };
    int[][] mPduStateAddr = {new int[]{8, 16, 8}, new int[]{8, 16, 8}, new int[]{8, 8, 8}};
    HashMap<Integer, String> mPduStateMapping = new HashMap<Integer, String>() {
        {
            put(0, "INACTIVE");
            put(1, "ACTIVE PENDING");
            put(2, "ACTIVE");
            put(3, "MODIFICATION PENDING");
            put(4, "INACTIVE PENDING");
        }
    };
    int[][] mStateTypeAddr = {new int[]{8, 0, 8}};
    int[][] mSubstateTypeAddr = {new int[]{8, 8, 8}};
    int[][] restrictDCNRAddr = {new int[]{8, 16, 8}};

    public ONasInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Other NAS Information";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "8. NR EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"5GMM State", "5GMM Cause", "5GSM State", "5GSM Cause", "RestrictDCNR"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        ByteBuffer icdPacket = (ByteBuffer) msg;
        if (name.equals(MDMContentICD.MSG_ID_VGNAS_MM_STATE_VALUE)) {
            int[][] iArr = this.mStateTypeAddr;
            int version = getFieldValueIcdVersion(icdPacket, iArr[iArr.length - 1][0]);
            int stateType = getFieldValueIcd(icdPacket, version, this.mStateTypeAddr);
            int substateType = getFieldValueIcd(icdPacket, version, this.mSubstateTypeAddr);
            String[] strArr = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr, getName() + " update,name id = " + name + ", version = " + version + ", values = " + stateType + ", " + substateType);
            switch (stateType) {
                case 1:
                    setData(0, "VGmmNull");
                    return;
                case 2:
                    switch (substateType) {
                        case 1:
                            setData(0, "VGmmDeregisteredNoSupi");
                            return;
                        case 2:
                            setData(0, "VGmmDeregisteredPlmnSearch");
                            return;
                        case 3:
                            setData(0, "VGmmDeregisteredNoCellAvailable");
                            return;
                        case 4:
                            setData(0, "VGmmDeregisteredAttemptingRegistrationUpdate");
                            return;
                        case 5:
                            setData(0, "VGmmDeregisteredLimitedService");
                            return;
                        case 6:
                            setData(0, "VGmmDeregisteredNormalService");
                            return;
                        case 7:
                            setData(0, "VGmmDeregisteredInitRegNeeded");
                            return;
                        default:
                            return;
                    }
                case 3:
                    setData(0, "VGmmRegisteredInitiated");
                    return;
                case 4:
                    switch (substateType) {
                        case 1:
                            setData(0, "VGmmRegisteredNormalService");
                            return;
                        case 2:
                            setData(0, "VGmmRegisteredNonAllowedService");
                            return;
                        case 3:
                            setData(0, "VGmmRegisteredAttemptingToUpdate");
                            return;
                        case 4:
                            setData(0, "VGmmRegisteredLimitedService");
                            return;
                        case 5:
                            setData(0, "VGmmRegisteredPlmnSearch");
                            return;
                        case 6:
                            setData(0, "VGmmRegisteredNoCellAvailable");
                            return;
                        case 7:
                            setData(0, "VGmmRegisteredUpdateNeeded");
                            return;
                        default:
                            return;
                    }
                case 5:
                    setData(0, "VGmmDeregisteredInitiated");
                    return;
                case 6:
                    setData(0, "VGmmServiceRequestInitiated");
                    return;
                default:
                    return;
            }
        } else if (name.equals(MDMContentICD.MSG_ID_VGNAS_MM_FAILURE_EVENT_CAUSE)) {
            int[][] iArr2 = this.mStateTypeAddr;
            int version2 = getFieldValueIcdVersion(icdPacket, iArr2[iArr2.length - 1][0]);
            int cause = getFieldValueIcd(icdPacket, version2, this.mCauseAddr, true);
            String[] strArr2 = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr2, getName() + " update,name id = " + name + ", version = " + version2 + ", values = " + cause);
            setData(1, getValueByMapping(this.mCauseMapping, cause));
        } else if (name.equals(MDMContentICD.MSG_ID_VGNAS_SM_CONTEXT_INFO)) {
            int[][] iArr3 = this.mStateTypeAddr;
            int version3 = getFieldValueIcdVersion(icdPacket, iArr3[iArr3.length - 1][0]);
            int pduState = getFieldValueIcd(icdPacket, version3, this.mPduStateAddr);
            String[] strArr3 = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr3, getName() + " update,name id = " + name + ", version = " + version3 + ", values = " + pduState);
            setData(2, getValueByMapping(this.mPduStateMapping, pduState));
        } else if (name.equals(MDMContentICD.MSG_ID_FAILURE_EVENT_CAUSE)) {
            int[][] iArr4 = this.eventAddr;
            int version4 = getFieldValueIcdVersion(icdPacket, iArr4[iArr4.length - 1][0]);
            int event = getFieldValueIcd(icdPacket, version4, this.eventAddr, true);
            String[] strArr4 = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr4, getName() + " update,name id = " + name + ", version = " + version4 + ", values = " + event);
            setData(3, Integer.valueOf(event));
        } else if (name.equals(MDMContentICD.MSG_ID_ENAS_EMM_CONTEXT_INFO)) {
            int[][] iArr5 = this.restrictDCNRAddr;
            int version5 = getFieldValueIcdVersion(icdPacket, iArr5[iArr5.length - 1][0]);
            int restrictDCNR = getFieldValueIcd(icdPacket, version5, this.restrictDCNRAddr);
            String[] strArr5 = icdLogInfo;
            Elog.d("EmInfo/MDMComponent", strArr5, getName() + " update,name id = " + name + ", version = " + version5 + ", values = " + restrictDCNR);
            setData(4, Integer.valueOf(restrictDCNR));
        }
    }
}
