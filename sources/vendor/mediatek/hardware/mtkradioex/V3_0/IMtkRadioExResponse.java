package vendor.mediatek.hardware.mtkradioex.V3_0;

import android.hardware.radio.V1_0.LastCallFailCause;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.V1_4.DataCallFailCause;
import android.hardware.radio.V1_5.EutranBands;
import android.hardware.radio.V1_5.NgranBands;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface IMtkRadioExResponse extends IBase {
    public static final String kInterfaceName = "vendor.mediatek.hardware.mtkradioex@3.0::IMtkRadioExResponse";

    void abortFemtocellListResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void activateUiccCardRsp(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    IHwBinder asBinder();

    void cancelAvailableNetworksResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void cfgA2offsetResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void cfgB1offsetResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void clearLteAvailableFileResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void dataConnectionAttachResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void dataConnectionDetachResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void deactivateNrScgCommunicationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void deactivateUiccCardRsp(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void deleteUPBEntryResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void disableAllCALinksResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void editUPBEntryResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void enableCAPlusBandWidthFilterResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void enableDsdaIndicationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void enableSCGfailureResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void get4x4MimoEnabledResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getATRResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void getAllBandModeResponse(RadioResponseInfo radioResponseInfo, BandModeInfo bandModeInfo) throws RemoteException;

    void getApcInfoResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getAvailableNetworksWithActResponse(RadioResponseInfo radioResponseInfo, ArrayList<OperatorInfoWithAct> arrayList) throws RemoteException;

    void getBandModeResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getBandPriorityListResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getCALinkCapabilityListResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void getCALinkEnableStatusResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void getCaBandModeResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getCallSubAddressResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getCampedFemtoCellInfoResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void getColpResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException;

    void getColrResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getCurrentPOLListResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void getCurrentUiccCardProvisioningStatusRsp(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getDeactivateNrScgCommunicationResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    void getDisable2GResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getDsdaStatusResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getEccNumResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getEngineeringModeInfoResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void getFemtocellListResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void getGsmBroadcastActivationRsp(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getGsmBroadcastLangsResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIWlanRegistrationStateResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getIccidResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void getLte1xRttCellListResponse(RadioResponseInfo radioResponseInfo, ArrayList<Lte1xRttCellInfo> arrayList) throws RemoteException;

    void getLteBsrTimerResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getLteDataResponse(RadioResponseInfo radioResponseInfo, LteData lteData) throws RemoteException;

    void getLteRRCStateResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getLteReleaseVersionResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getLteScanDurationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getPOLCapabilityResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getPhoneBookMemStorageResponse(RadioResponseInfo radioResponseInfo, PhbMemStorageResponse phbMemStorageResponse) throws RemoteException;

    void getPhoneBookStringsLengthResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getPlmnNameFromSE13TableResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void getQamEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z, boolean z2) throws RemoteException;

    void getRoamingEnableResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getRxTestResultResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getSignalStrengthWithWcdmaEcioResponse(RadioResponseInfo radioResponseInfo, SignalStrengthWithWcdmaEcio signalStrengthWithWcdmaEcio) throws RemoteException;

    void getSmsMemStatusResponse(RadioResponseInfo radioResponseInfo, SmsMemStatus smsMemStatus) throws RemoteException;

    void getSmsParametersResponse(RadioResponseInfo radioResponseInfo, SmsParams smsParams) throws RemoteException;

    void getSmsRuimMemoryStatusResponse(RadioResponseInfo radioResponseInfo, SmsMemStatus smsMemStatus) throws RemoteException;

    void getSuggestedPlmnListResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void getTOEInfoResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3) throws RemoteException;

    void getTm9EnabledResponse(RadioResponseInfo radioResponseInfo, boolean z, boolean z2) throws RemoteException;

    void handleStkCallSetupRequestFromSimWithResCodeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void hangupAllResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void hangupWithReasonResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void modifyModemTypeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void ping() throws RemoteException;

    void queryCallForwardInTimeSlotStatusResponse(RadioResponseInfo radioResponseInfo, ArrayList<CallForwardInfoEx> arrayList) throws RemoteException;

    void queryFemtoCellSystemSelectionModeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void queryNetworkLockResponse(RadioResponseInfo radioResponseInfo, int i, int i2, int i3, int i4, int i5, int i6, int i7) throws RemoteException;

    void queryPhbStorageInfoResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void queryUPBAvailableResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void queryUPBCapabilityResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void readPhbEntryResponse(RadioResponseInfo radioResponseInfo, ArrayList<PhbEntryStructure> arrayList) throws RemoteException;

    void readPhoneBookEntryExtResponse(RadioResponseInfo radioResponseInfo, ArrayList<PhbEntryExt> arrayList) throws RemoteException;

    void readUPBAasListResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void readUPBAnrEntryResponse(RadioResponseInfo radioResponseInfo, ArrayList<PhbEntryStructure> arrayList) throws RemoteException;

    void readUPBEmailEntryResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void readUPBGasListResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void readUPBGrpEntryResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void readUPBSneEntryResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void registerCellQltyReportResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void removeCbMsgResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void resetAllConnectionsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void resetMdDataRetryCountResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void restartRILDResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void runGbaAuthenticationResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void selectFemtocellResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendCnapResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException;

    void sendEmbmsAtCommandResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void sendRequestRawResponse(RadioResponseInfo radioResponseInfo, ArrayList<Byte> arrayList) throws RemoteException;

    void sendRequestStringsResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException;

    void sendRsuRequestResponse(RadioResponseInfo radioResponseInfo, RsuResponseInfo rsuResponseInfo) throws RemoteException;

    void sendSarIndicatorResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendWifiAssociatedResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendWifiEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendWifiIpAddressResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void set4x4MimoEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setApcModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setBandPriorityListResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setBgsrchDeltaSleepTimerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCALinkEnableStatusResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCallForwardInTimeSlotResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCallIndicationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCallSubAddressResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCallValidTimerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setClipResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setColpResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setColrResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setDisable2GResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setEccModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setEccNumResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setEtwsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setFdModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setFemtoCellSystemSelectionModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setGsmBroadcastLangsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setGwsdModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    void setIgnoreSameNumberIntervalResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setKeepAliveByIpDataResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setKeepAliveByPDCPCtrlPDUResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setLteBandEnableStatusResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setLteBsrTimerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setLteReleaseVersionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setLteScanDurationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setMaxUlSpeedResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setModemPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNROptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNetworkLockResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNetworkSelectionModeManualWithActResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNrBandModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setPOLEntryResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setPhoneBookMemStorageResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setPhonebookReadyResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setQamEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setRemoveRestrictEutranModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setResumeRegistrationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setRoamingEnableResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setRxTestConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void setSearchRatResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSearchStoredFreqInfoResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setServiceStateToModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSimPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSmsParametersResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSuppServPropertyResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setTm9EnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setTrmResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setTxPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setTxPowerStatusResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setVendorSettingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void supplyDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplyDeviceNetworkDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void syncDataSettingsToMdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void triggerModeSwitchByEccResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void vsimNotificationResponse(RadioResponseInfo radioResponseInfo, VsimEvent vsimEvent) throws RemoteException;

    void vsimOperationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void writePhbEntryResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void writePhoneBookEntryExtResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void writeUPBGrpEntryResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    static IMtkRadioExResponse asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IMtkRadioExResponse)) {
            return (IMtkRadioExResponse) iface;
        }
        IMtkRadioExResponse proxy = new Proxy(binder);
        try {
            Iterator<String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException e) {
        }
        return null;
    }

    static IMtkRadioExResponse castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IMtkRadioExResponse getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IMtkRadioExResponse getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static IMtkRadioExResponse getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static IMtkRadioExResponse getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IMtkRadioExResponse {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            Objects.requireNonNull(remote);
            this.mRemote = remote;
        }

        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of vendor.mediatek.hardware.mtkradioex@3.0::IMtkRadioExResponse]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public void sendEmbmsAtCommandResponse(RadioResponseInfo responseInfo, String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRoamingEnableResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getRoamingEnableResponse(RadioResponseInfo responseInfo, ArrayList<Integer> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryPhbStorageInfoResponse(RadioResponseInfo info, ArrayList<Integer> storageInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(storageInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writePhbEntryResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readPhbEntryResponse(RadioResponseInfo info, ArrayList<PhbEntryStructure> phbEntries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            PhbEntryStructure.writeVectorToParcel(_hidl_request, phbEntries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryUPBCapabilityResponse(RadioResponseInfo info, ArrayList<Integer> upbCapability) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(upbCapability);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void editUPBEntryResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteUPBEntryResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBGasListResponse(RadioResponseInfo info, ArrayList<String> gasList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(gasList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBGrpEntryResponse(RadioResponseInfo info, ArrayList<Integer> grpEntries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(grpEntries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeUPBGrpEntryResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPhoneBookStringsLengthResponse(RadioResponseInfo info, ArrayList<Integer> stringLengthInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(stringLengthInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPhoneBookMemStorageResponse(RadioResponseInfo info, PhbMemStorageResponse phbMemStorage) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            phbMemStorage.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPhoneBookMemStorageResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readPhoneBookEntryExtResponse(RadioResponseInfo info, ArrayList<PhbEntryExt> phbEntryExts) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            PhbEntryExt.writeVectorToParcel(_hidl_request, phbEntryExts);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writePhoneBookEntryExtResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryUPBAvailableResponse(RadioResponseInfo info, ArrayList<Integer> upbAvailable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(upbAvailable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBEmailEntryResponse(RadioResponseInfo info, String email) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(email);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBSneEntryResponse(RadioResponseInfo info, String sne) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(sne);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBAnrEntryResponse(RadioResponseInfo info, ArrayList<PhbEntryStructure> anrs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            PhbEntryStructure.writeVectorToParcel(_hidl_request, anrs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBAasListResponse(RadioResponseInfo info, ArrayList<String> aasList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(aasList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPhonebookReadyResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setClipResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getColpResponse(RadioResponseInfo info, int n, int m) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(n);
            _hidl_request.writeInt32(m);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getColrResponse(RadioResponseInfo info, int n) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(n);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCnapResponse(RadioResponseInfo info, int n, int m) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(n);
            _hidl_request.writeInt32(m);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setColpResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setColrResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryCallForwardInTimeSlotStatusResponse(RadioResponseInfo info, ArrayList<CallForwardInfoEx> callForwardInfoExs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            CallForwardInfoEx.writeVectorToParcel(_hidl_request, callForwardInfoExs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallForwardInTimeSlotResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void runGbaAuthenticationResponse(RadioResponseInfo info, ArrayList<String> resList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(resList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupAllResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallIndicationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setEccModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setEccNumResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getEccNumResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallSubAddressResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallSubAddressResponse(RadioResponseInfo info, int enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setModemPowerResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void triggerModeSwitchByEccResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getATRResponse(RadioResponseInfo info, String response) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(response);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIccidResponse(RadioResponseInfo info, String response) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(response);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSimPowerResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void activateUiccCardRsp(RadioResponseInfo info, int simPowerOnOffResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(simPowerOnOffResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateUiccCardRsp(RadioResponseInfo info, int simPowerOnOffResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(simPowerOnOffResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentUiccCardProvisioningStatusRsp(RadioResponseInfo info, int simPowerOnOffStatus) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(simPowerOnOffStatus);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryNetworkLockResponse(RadioResponseInfo info, int catagory, int state, int retry_cnt, int autolock_cnt, int num_set, int total_set, int key_state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(catagory);
            _hidl_request.writeInt32(state);
            _hidl_request.writeInt32(retry_cnt);
            _hidl_request.writeInt32(autolock_cnt);
            _hidl_request.writeInt32(num_set);
            _hidl_request.writeInt32(total_set);
            _hidl_request.writeInt32(key_state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(48, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkLockResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(49, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyDepersonalizationResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(50, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void vsimNotificationResponse(RadioResponseInfo info, VsimEvent event) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            event.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(51, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void vsimOperationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(52, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmsParametersResponse(RadioResponseInfo info, SmsParams param) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            param.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(53, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSmsParametersResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(54, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmsMemStatusResponse(RadioResponseInfo info, SmsMemStatus status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            status.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(55, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setEtwsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(56, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void removeCbMsgResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(57, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastLangsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(58, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getGsmBroadcastLangsResponse(RadioResponseInfo info, String langs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(langs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(59, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getGsmBroadcastActivationRsp(RadioResponseInfo info, int active) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(active);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(60, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendRequestRawResponse(RadioResponseInfo info, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(61, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendRequestStringsResponse(RadioResponseInfo info, ArrayList<String> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(62, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResumeRegistrationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(63, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void modifyModemTypeResponse(RadioResponseInfo info, int applyType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(applyType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(64, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmsRuimMemoryStatusResponse(RadioResponseInfo info, SmsMemStatus memStatus) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            memStatus.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(65, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeManualWithActResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(66, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableNetworksWithActResponse(RadioResponseInfo info, ArrayList<OperatorInfoWithAct> networkInfosWithAct) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            OperatorInfoWithAct.writeVectorToParcel(_hidl_request, networkInfosWithAct);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(67, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrengthWithWcdmaEcioResponse(RadioResponseInfo info, SignalStrengthWithWcdmaEcio signalStrength) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            signalStrength.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(68, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cancelAvailableNetworksResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(69, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getFemtocellListResponse(RadioResponseInfo responseInfo, ArrayList<String> femtoList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(femtoList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(70, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void abortFemtocellListResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(71, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void selectFemtocellResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(72, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryFemtoCellSystemSelectionModeResponse(RadioResponseInfo responseInfo, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(73, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setFemtoCellSystemSelectionModeResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(74, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setServiceStateToModemResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(75, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cfgA2offsetResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(76, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cfgB1offsetResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(77, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableSCGfailureResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(78, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTxPowerResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(79, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSearchStoredFreqInfoResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(80, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSearchRatResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(81, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBgsrchDeltaSleepTimerResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(82, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRxTestConfigResponse(RadioResponseInfo responseInfo, ArrayList<Integer> respAntConf) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(respAntConf);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(83, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getRxTestResultResponse(RadioResponseInfo responseInfo, ArrayList<Integer> respAntInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(respAntInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(84, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPOLCapabilityResponse(RadioResponseInfo responseInfo, ArrayList<Integer> polCapability) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(polCapability);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(85, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentPOLListResponse(RadioResponseInfo responseInfo, ArrayList<String> polList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(polList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(86, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPOLEntryResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(87, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setFdModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(88, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTrmResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(89, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void handleStkCallSetupRequestFromSimWithResCodeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(90, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void restartRILDResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(91, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void syncDataSettingsToMdResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(92, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void resetMdDataRetryCountResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(93, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRemoveRestrictEutranModeResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(94, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setApcModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(95, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getApcInfoResponse(RadioResponseInfo info, ArrayList<Integer> cellInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(cellInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(96, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dataConnectionAttachResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(97, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dataConnectionDetachResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(98, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void resetAllConnectionsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(99, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLteReleaseVersionResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(100, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLteReleaseVersionResponse(RadioResponseInfo info, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(101, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTxPowerStatusResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(102, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSuppServPropertyResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(103, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyDeviceNetworkDepersonalizationResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(104, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupWithReasonResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(105, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setVendorSettingResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(106, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPlmnNameFromSE13TableResponse(RadioResponseInfo info, String name) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(name);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableCAPlusBandWidthFilterResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGwsdModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallValidTimerResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(110, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIgnoreSameNumberIntervalResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(111, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setKeepAliveByPDCPCtrlPDUResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(112, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setKeepAliveByIpDataResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(113, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableDsdaIndicationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(114, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDsdaStatusResponse(RadioResponseInfo info, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(115, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void registerCellQltyReportResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(116, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSuggestedPlmnListResponse(RadioResponseInfo responseInfo, ArrayList<String> plmnList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(plmnList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(117, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateNrScgCommunicationResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(118, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDeactivateNrScgCommunicationResponse(RadioResponseInfo responseInfo, int deactivate, int allowSCGAdd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(deactivate);
            _hidl_request.writeInt32(allowSCGAdd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(119, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setMaxUlSpeedResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(120, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSarIndicatorResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(121, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendRsuRequestResponse(RadioResponseInfo info, RsuResponseInfo rri) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            rri.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(122, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendWifiEnabledResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_DNS_ADDR, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendWifiAssociatedResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendWifiIpAddressResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(125, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNROptionResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(126, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getTOEInfoResponse(RadioResponseInfo info, String longName, String shortName, String numeric) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(longName);
            _hidl_request.writeString(shortName);
            _hidl_request.writeString(numeric);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(127, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void disableAllCALinksResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(128, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCALinkEnableStatusResponse(RadioResponseInfo info, boolean status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(129, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCALinkEnableStatusResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(130, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCALinkCapabilityListResponse(RadioResponseInfo info, ArrayList<String> linkCapabilityList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(linkCapabilityList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(131, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLteDataResponse(RadioResponseInfo info, LteData data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            data.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(132, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLteRRCStateResponse(RadioResponseInfo info, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_CW5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLteBandEnableStatusResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(134, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getBandPriorityListResponse(RadioResponseInfo info, ArrayList<Integer> bandPriList) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(bandPriList);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(135, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBandPriorityListResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(136, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void set4x4MimoEnabledResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DSW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void get4x4MimoEnabledResponse(RadioResponseInfo info, int enabled_bitmask) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(enabled_bitmask);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_HDW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLteBsrTimerResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_TGW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLteBsrTimerResponse(RadioResponseInfo info, int timer) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(timer);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLte1xRttCellListResponse(RadioResponseInfo info, ArrayList<Lte1xRttCellInfo> list) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            Lte1xRttCellInfo.writeVectorToParcel(_hidl_request, list);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLY, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void clearLteAvailableFileResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLC, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getBandModeResponse(RadioResponseInfo info, ArrayList<Integer> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(143, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCaBandModeResponse(RadioResponseInfo info, ArrayList<Integer> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPA, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCampedFemtoCellInfoResponse(RadioResponseInfo info, ArrayList<String> femto) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(femto);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPC, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setQamEnabledResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPL, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getQamEnabledResponse(RadioResponseInfo info, boolean ulOrDl, boolean enabled) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(ulOrDl);
            _hidl_request.writeBool(enabled);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(147, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTm9EnabledResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(148, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getTm9EnabledResponse(RadioResponseInfo info, boolean fddOrTdd, boolean enabled) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(fddOrTdd);
            _hidl_request.writeBool(enabled);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(149, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLteScanDurationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(150, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLteScanDurationResponse(RadioResponseInfo info, int duration) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(duration);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SWA, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDisable2GResponse(RadioResponseInfo responseInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF0, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDisable2GResponse(RadioResponseInfo responseInfo, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getEngineeringModeInfoResponse(RadioResponseInfo responseInfo, ArrayList<String> result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeStringVector(result);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIWlanRegistrationStateResponse(RadioResponseInfo responseInfo, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            responseInfo.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAllBandModeResponse(RadioResponseInfo info, BandModeInfo data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            data.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNrBandModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioExResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256067662, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readStringVector();
            } finally {
                _hidl_reply.release();
            }
        }

        public void debug(NativeHandle fd, ArrayList<String> options) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            _hidl_request.writeNativeHandle(fd);
            _hidl_request.writeStringVector(options);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256131655, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public String interfaceDescriptor() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256136003, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readString();
            } finally {
                _hidl_reply.release();
            }
        }

        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256398152, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<byte[]> _hidl_out_hashchain = new ArrayList<>();
                HwBlob _hidl_blob = _hidl_reply.readBuffer(16);
                int _hidl_vec_size = _hidl_blob.getInt32(8);
                HwBlob childBlob = _hidl_reply.readEmbeddedBuffer((long) (_hidl_vec_size * 32), _hidl_blob.handle(), 0, true);
                _hidl_out_hashchain.clear();
                for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                    byte[] _hidl_vec_element = new byte[32];
                    childBlob.copyToInt8Array((long) (_hidl_index_0 * 32), _hidl_vec_element, 32);
                    _hidl_out_hashchain.add(_hidl_vec_element);
                }
                return _hidl_out_hashchain;
            } finally {
                _hidl_reply.release();
            }
        }

        public void setHALInstrumentation() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256462420, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        public void ping() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256921159, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257049926, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                DebugInfo _hidl_out_info = new DebugInfo();
                _hidl_out_info.readFromParcel(_hidl_reply);
                return _hidl_out_info;
            } finally {
                _hidl_reply.release();
            }
        }

        public void notifySyspropsChanged() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257120595, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IMtkRadioExResponse {
        public IHwBinder asBinder() {
            return this;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{IMtkRadioExResponse.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IMtkRadioExResponse.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{31, -24, 122, -13, 92, 112, -23, 65, 41, 77, -58, -38, 81, -81, 125, 22, 70, -83, 85, -111, -111, 27, 79, 88, 81, -3, -50, 21, -102, 33, -113, 82}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
        }

        public final void setHALInstrumentation() {
        }

        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        public final void ping() {
        }

        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0;
            info.arch = 0;
            return info;
        }

        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        public IHwInterface queryLocalInterface(String descriptor) {
            if (IMtkRadioExResponse.kInterfaceName.equals(descriptor)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String serviceName) throws RemoteException {
            registerService(serviceName);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        public void onTransact(int _hidl_code, HwParcel _hidl_request, HwParcel _hidl_reply, int _hidl_flags) throws RemoteException {
            HwParcel hwParcel = _hidl_request;
            HwParcel hwParcel2 = _hidl_reply;
            switch (_hidl_code) {
                case 1:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo = new RadioResponseInfo();
                    responseInfo.readFromParcel(hwParcel);
                    sendEmbmsAtCommandResponse(responseInfo, _hidl_request.readString());
                    return;
                case 2:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo2 = new RadioResponseInfo();
                    responseInfo2.readFromParcel(hwParcel);
                    setRoamingEnableResponse(responseInfo2);
                    return;
                case 3:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo3 = new RadioResponseInfo();
                    responseInfo3.readFromParcel(hwParcel);
                    getRoamingEnableResponse(responseInfo3, _hidl_request.readInt32Vector());
                    return;
                case 4:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info = new RadioResponseInfo();
                    info.readFromParcel(hwParcel);
                    queryPhbStorageInfoResponse(info, _hidl_request.readInt32Vector());
                    return;
                case 5:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info2 = new RadioResponseInfo();
                    info2.readFromParcel(hwParcel);
                    writePhbEntryResponse(info2);
                    return;
                case 6:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info3 = new RadioResponseInfo();
                    info3.readFromParcel(hwParcel);
                    readPhbEntryResponse(info3, PhbEntryStructure.readVectorFromParcel(_hidl_request));
                    return;
                case 7:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info4 = new RadioResponseInfo();
                    info4.readFromParcel(hwParcel);
                    queryUPBCapabilityResponse(info4, _hidl_request.readInt32Vector());
                    return;
                case 8:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info5 = new RadioResponseInfo();
                    info5.readFromParcel(hwParcel);
                    editUPBEntryResponse(info5);
                    return;
                case 9:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info6 = new RadioResponseInfo();
                    info6.readFromParcel(hwParcel);
                    deleteUPBEntryResponse(info6);
                    return;
                case 10:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info7 = new RadioResponseInfo();
                    info7.readFromParcel(hwParcel);
                    readUPBGasListResponse(info7, _hidl_request.readStringVector());
                    return;
                case 11:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info8 = new RadioResponseInfo();
                    info8.readFromParcel(hwParcel);
                    readUPBGrpEntryResponse(info8, _hidl_request.readInt32Vector());
                    return;
                case 12:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info9 = new RadioResponseInfo();
                    info9.readFromParcel(hwParcel);
                    writeUPBGrpEntryResponse(info9);
                    return;
                case 13:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info10 = new RadioResponseInfo();
                    info10.readFromParcel(hwParcel);
                    getPhoneBookStringsLengthResponse(info10, _hidl_request.readInt32Vector());
                    return;
                case 14:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info11 = new RadioResponseInfo();
                    info11.readFromParcel(hwParcel);
                    PhbMemStorageResponse phbMemStorage = new PhbMemStorageResponse();
                    phbMemStorage.readFromParcel(hwParcel);
                    getPhoneBookMemStorageResponse(info11, phbMemStorage);
                    return;
                case 15:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info12 = new RadioResponseInfo();
                    info12.readFromParcel(hwParcel);
                    setPhoneBookMemStorageResponse(info12);
                    return;
                case 16:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info13 = new RadioResponseInfo();
                    info13.readFromParcel(hwParcel);
                    readPhoneBookEntryExtResponse(info13, PhbEntryExt.readVectorFromParcel(_hidl_request));
                    return;
                case 17:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info14 = new RadioResponseInfo();
                    info14.readFromParcel(hwParcel);
                    writePhoneBookEntryExtResponse(info14);
                    return;
                case 18:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info15 = new RadioResponseInfo();
                    info15.readFromParcel(hwParcel);
                    queryUPBAvailableResponse(info15, _hidl_request.readInt32Vector());
                    return;
                case 19:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info16 = new RadioResponseInfo();
                    info16.readFromParcel(hwParcel);
                    readUPBEmailEntryResponse(info16, _hidl_request.readString());
                    return;
                case 20:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info17 = new RadioResponseInfo();
                    info17.readFromParcel(hwParcel);
                    readUPBSneEntryResponse(info17, _hidl_request.readString());
                    return;
                case 21:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info18 = new RadioResponseInfo();
                    info18.readFromParcel(hwParcel);
                    readUPBAnrEntryResponse(info18, PhbEntryStructure.readVectorFromParcel(_hidl_request));
                    return;
                case 22:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info19 = new RadioResponseInfo();
                    info19.readFromParcel(hwParcel);
                    readUPBAasListResponse(info19, _hidl_request.readStringVector());
                    return;
                case 23:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info20 = new RadioResponseInfo();
                    info20.readFromParcel(hwParcel);
                    setPhonebookReadyResponse(info20);
                    return;
                case 24:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info21 = new RadioResponseInfo();
                    info21.readFromParcel(hwParcel);
                    setClipResponse(info21);
                    return;
                case 25:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info22 = new RadioResponseInfo();
                    info22.readFromParcel(hwParcel);
                    getColpResponse(info22, _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 26:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info23 = new RadioResponseInfo();
                    info23.readFromParcel(hwParcel);
                    getColrResponse(info23, _hidl_request.readInt32());
                    return;
                case 27:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info24 = new RadioResponseInfo();
                    info24.readFromParcel(hwParcel);
                    sendCnapResponse(info24, _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 28:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info25 = new RadioResponseInfo();
                    info25.readFromParcel(hwParcel);
                    setColpResponse(info25);
                    return;
                case 29:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info26 = new RadioResponseInfo();
                    info26.readFromParcel(hwParcel);
                    setColrResponse(info26);
                    return;
                case 30:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info27 = new RadioResponseInfo();
                    info27.readFromParcel(hwParcel);
                    queryCallForwardInTimeSlotStatusResponse(info27, CallForwardInfoEx.readVectorFromParcel(_hidl_request));
                    return;
                case 31:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info28 = new RadioResponseInfo();
                    info28.readFromParcel(hwParcel);
                    setCallForwardInTimeSlotResponse(info28);
                    return;
                case 32:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info29 = new RadioResponseInfo();
                    info29.readFromParcel(hwParcel);
                    runGbaAuthenticationResponse(info29, _hidl_request.readStringVector());
                    return;
                case 33:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info30 = new RadioResponseInfo();
                    info30.readFromParcel(hwParcel);
                    hangupAllResponse(info30);
                    return;
                case 34:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info31 = new RadioResponseInfo();
                    info31.readFromParcel(hwParcel);
                    setCallIndicationResponse(info31);
                    return;
                case 35:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info32 = new RadioResponseInfo();
                    info32.readFromParcel(hwParcel);
                    setEccModeResponse(info32);
                    return;
                case 36:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info33 = new RadioResponseInfo();
                    info33.readFromParcel(hwParcel);
                    setEccNumResponse(info33);
                    return;
                case 37:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info34 = new RadioResponseInfo();
                    info34.readFromParcel(hwParcel);
                    getEccNumResponse(info34);
                    return;
                case 38:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info35 = new RadioResponseInfo();
                    info35.readFromParcel(hwParcel);
                    setCallSubAddressResponse(info35);
                    return;
                case 39:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info36 = new RadioResponseInfo();
                    info36.readFromParcel(hwParcel);
                    getCallSubAddressResponse(info36, _hidl_request.readInt32());
                    return;
                case 40:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo4 = new RadioResponseInfo();
                    responseInfo4.readFromParcel(hwParcel);
                    setModemPowerResponse(responseInfo4);
                    return;
                case 41:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info37 = new RadioResponseInfo();
                    info37.readFromParcel(hwParcel);
                    triggerModeSwitchByEccResponse(info37);
                    return;
                case 42:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info38 = new RadioResponseInfo();
                    info38.readFromParcel(hwParcel);
                    getATRResponse(info38, _hidl_request.readString());
                    return;
                case 43:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info39 = new RadioResponseInfo();
                    info39.readFromParcel(hwParcel);
                    getIccidResponse(info39, _hidl_request.readString());
                    return;
                case 44:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info40 = new RadioResponseInfo();
                    info40.readFromParcel(hwParcel);
                    setSimPowerResponse(info40);
                    return;
                case 45:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info41 = new RadioResponseInfo();
                    info41.readFromParcel(hwParcel);
                    activateUiccCardRsp(info41, _hidl_request.readInt32());
                    return;
                case 46:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info42 = new RadioResponseInfo();
                    info42.readFromParcel(hwParcel);
                    deactivateUiccCardRsp(info42, _hidl_request.readInt32());
                    return;
                case 47:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info43 = new RadioResponseInfo();
                    info43.readFromParcel(hwParcel);
                    getCurrentUiccCardProvisioningStatusRsp(info43, _hidl_request.readInt32());
                    return;
                case 48:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info44 = new RadioResponseInfo();
                    info44.readFromParcel(hwParcel);
                    queryNetworkLockResponse(info44, _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 49:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info45 = new RadioResponseInfo();
                    info45.readFromParcel(hwParcel);
                    setNetworkLockResponse(info45);
                    return;
                case 50:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info46 = new RadioResponseInfo();
                    info46.readFromParcel(hwParcel);
                    supplyDepersonalizationResponse(info46, _hidl_request.readInt32());
                    return;
                case 51:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info47 = new RadioResponseInfo();
                    info47.readFromParcel(hwParcel);
                    VsimEvent event = new VsimEvent();
                    event.readFromParcel(hwParcel);
                    vsimNotificationResponse(info47, event);
                    return;
                case 52:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info48 = new RadioResponseInfo();
                    info48.readFromParcel(hwParcel);
                    vsimOperationResponse(info48);
                    return;
                case 53:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info49 = new RadioResponseInfo();
                    info49.readFromParcel(hwParcel);
                    SmsParams param = new SmsParams();
                    param.readFromParcel(hwParcel);
                    getSmsParametersResponse(info49, param);
                    return;
                case 54:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info50 = new RadioResponseInfo();
                    info50.readFromParcel(hwParcel);
                    setSmsParametersResponse(info50);
                    return;
                case 55:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info51 = new RadioResponseInfo();
                    info51.readFromParcel(hwParcel);
                    SmsMemStatus status = new SmsMemStatus();
                    status.readFromParcel(hwParcel);
                    getSmsMemStatusResponse(info51, status);
                    return;
                case 56:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info52 = new RadioResponseInfo();
                    info52.readFromParcel(hwParcel);
                    setEtwsResponse(info52);
                    return;
                case 57:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info53 = new RadioResponseInfo();
                    info53.readFromParcel(hwParcel);
                    removeCbMsgResponse(info53);
                    return;
                case 58:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info54 = new RadioResponseInfo();
                    info54.readFromParcel(hwParcel);
                    setGsmBroadcastLangsResponse(info54);
                    return;
                case 59:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info55 = new RadioResponseInfo();
                    info55.readFromParcel(hwParcel);
                    getGsmBroadcastLangsResponse(info55, _hidl_request.readString());
                    return;
                case 60:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info56 = new RadioResponseInfo();
                    info56.readFromParcel(hwParcel);
                    getGsmBroadcastActivationRsp(info56, _hidl_request.readInt32());
                    return;
                case 61:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info57 = new RadioResponseInfo();
                    info57.readFromParcel(hwParcel);
                    sendRequestRawResponse(info57, _hidl_request.readInt8Vector());
                    return;
                case 62:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info58 = new RadioResponseInfo();
                    info58.readFromParcel(hwParcel);
                    sendRequestStringsResponse(info58, _hidl_request.readStringVector());
                    return;
                case 63:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info59 = new RadioResponseInfo();
                    info59.readFromParcel(hwParcel);
                    setResumeRegistrationResponse(info59);
                    return;
                case 64:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info60 = new RadioResponseInfo();
                    info60.readFromParcel(hwParcel);
                    modifyModemTypeResponse(info60, _hidl_request.readInt32());
                    return;
                case 65:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info61 = new RadioResponseInfo();
                    info61.readFromParcel(hwParcel);
                    SmsMemStatus memStatus = new SmsMemStatus();
                    memStatus.readFromParcel(hwParcel);
                    getSmsRuimMemoryStatusResponse(info61, memStatus);
                    return;
                case 66:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info62 = new RadioResponseInfo();
                    info62.readFromParcel(hwParcel);
                    setNetworkSelectionModeManualWithActResponse(info62);
                    return;
                case 67:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info63 = new RadioResponseInfo();
                    info63.readFromParcel(hwParcel);
                    getAvailableNetworksWithActResponse(info63, OperatorInfoWithAct.readVectorFromParcel(_hidl_request));
                    return;
                case 68:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info64 = new RadioResponseInfo();
                    info64.readFromParcel(hwParcel);
                    SignalStrengthWithWcdmaEcio signalStrength = new SignalStrengthWithWcdmaEcio();
                    signalStrength.readFromParcel(hwParcel);
                    getSignalStrengthWithWcdmaEcioResponse(info64, signalStrength);
                    return;
                case LastCallFailCause.REQUESTED_FACILITY_NOT_IMPLEMENTED:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info65 = new RadioResponseInfo();
                    info65.readFromParcel(hwParcel);
                    cancelAvailableNetworksResponse(info65);
                    return;
                case 70:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo5 = new RadioResponseInfo();
                    responseInfo5.readFromParcel(hwParcel);
                    getFemtocellListResponse(responseInfo5, _hidl_request.readStringVector());
                    return;
                case 71:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo6 = new RadioResponseInfo();
                    responseInfo6.readFromParcel(hwParcel);
                    abortFemtocellListResponse(responseInfo6);
                    return;
                case 72:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo7 = new RadioResponseInfo();
                    responseInfo7.readFromParcel(hwParcel);
                    selectFemtocellResponse(responseInfo7);
                    return;
                case 73:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo8 = new RadioResponseInfo();
                    responseInfo8.readFromParcel(hwParcel);
                    queryFemtoCellSystemSelectionModeResponse(responseInfo8, _hidl_request.readInt32());
                    return;
                case 74:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo9 = new RadioResponseInfo();
                    responseInfo9.readFromParcel(hwParcel);
                    setFemtoCellSystemSelectionModeResponse(responseInfo9);
                    return;
                case 75:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo10 = new RadioResponseInfo();
                    responseInfo10.readFromParcel(hwParcel);
                    setServiceStateToModemResponse(responseInfo10);
                    return;
                case 76:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo11 = new RadioResponseInfo();
                    responseInfo11.readFromParcel(hwParcel);
                    cfgA2offsetResponse(responseInfo11);
                    return;
                case 77:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo12 = new RadioResponseInfo();
                    responseInfo12.readFromParcel(hwParcel);
                    cfgB1offsetResponse(responseInfo12);
                    return;
                case 78:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo13 = new RadioResponseInfo();
                    responseInfo13.readFromParcel(hwParcel);
                    enableSCGfailureResponse(responseInfo13);
                    return;
                case 79:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo14 = new RadioResponseInfo();
                    responseInfo14.readFromParcel(hwParcel);
                    setTxPowerResponse(responseInfo14);
                    return;
                case NgranBands.BAND_80:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo15 = new RadioResponseInfo();
                    responseInfo15.readFromParcel(hwParcel);
                    setSearchStoredFreqInfoResponse(responseInfo15);
                    return;
                case 81:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo16 = new RadioResponseInfo();
                    responseInfo16.readFromParcel(hwParcel);
                    setSearchRatResponse(responseInfo16);
                    return;
                case NgranBands.BAND_82:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo17 = new RadioResponseInfo();
                    responseInfo17.readFromParcel(hwParcel);
                    setBgsrchDeltaSleepTimerResponse(responseInfo17);
                    return;
                case NgranBands.BAND_83:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo18 = new RadioResponseInfo();
                    responseInfo18.readFromParcel(hwParcel);
                    setRxTestConfigResponse(responseInfo18, _hidl_request.readInt32Vector());
                    return;
                case NgranBands.BAND_84:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo19 = new RadioResponseInfo();
                    responseInfo19.readFromParcel(hwParcel);
                    getRxTestResultResponse(responseInfo19, _hidl_request.readInt32Vector());
                    return;
                case EutranBands.BAND_85:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo20 = new RadioResponseInfo();
                    responseInfo20.readFromParcel(hwParcel);
                    getPOLCapabilityResponse(responseInfo20, _hidl_request.readInt32Vector());
                    return;
                case NgranBands.BAND_86:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo21 = new RadioResponseInfo();
                    responseInfo21.readFromParcel(hwParcel);
                    getCurrentPOLListResponse(responseInfo21, _hidl_request.readStringVector());
                    return;
                case 87:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo22 = new RadioResponseInfo();
                    responseInfo22.readFromParcel(hwParcel);
                    setPOLEntryResponse(responseInfo22);
                    return;
                case 88:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info66 = new RadioResponseInfo();
                    info66.readFromParcel(hwParcel);
                    setFdModeResponse(info66);
                    return;
                case NgranBands.BAND_89:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info67 = new RadioResponseInfo();
                    info67.readFromParcel(hwParcel);
                    setTrmResponse(info67);
                    return;
                case NgranBands.BAND_90:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info68 = new RadioResponseInfo();
                    info68.readFromParcel(hwParcel);
                    handleStkCallSetupRequestFromSimWithResCodeResponse(info68);
                    return;
                case 91:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info69 = new RadioResponseInfo();
                    info69.readFromParcel(hwParcel);
                    restartRILDResponse(info69);
                    return;
                case 92:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info70 = new RadioResponseInfo();
                    info70.readFromParcel(hwParcel);
                    syncDataSettingsToMdResponse(info70);
                    return;
                case NgranBands.BAND_93:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info71 = new RadioResponseInfo();
                    info71.readFromParcel(hwParcel);
                    resetMdDataRetryCountResponse(info71);
                    return;
                case NgranBands.BAND_94:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo23 = new RadioResponseInfo();
                    responseInfo23.readFromParcel(hwParcel);
                    setRemoveRestrictEutranModeResponse(responseInfo23);
                    return;
                case 95:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info72 = new RadioResponseInfo();
                    info72.readFromParcel(hwParcel);
                    setApcModeResponse(info72);
                    return;
                case 96:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info73 = new RadioResponseInfo();
                    info73.readFromParcel(hwParcel);
                    getApcInfoResponse(info73, _hidl_request.readInt32Vector());
                    return;
                case 97:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info74 = new RadioResponseInfo();
                    info74.readFromParcel(hwParcel);
                    dataConnectionAttachResponse(info74);
                    return;
                case 98:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info75 = new RadioResponseInfo();
                    info75.readFromParcel(hwParcel);
                    dataConnectionDetachResponse(info75);
                    return;
                case 99:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info76 = new RadioResponseInfo();
                    info76.readFromParcel(hwParcel);
                    resetAllConnectionsResponse(info76);
                    return;
                case 100:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info77 = new RadioResponseInfo();
                    info77.readFromParcel(hwParcel);
                    setLteReleaseVersionResponse(info77);
                    return;
                case 101:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info78 = new RadioResponseInfo();
                    info78.readFromParcel(hwParcel);
                    getLteReleaseVersionResponse(info78, _hidl_request.readInt32());
                    return;
                case 102:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info79 = new RadioResponseInfo();
                    info79.readFromParcel(hwParcel);
                    setTxPowerStatusResponse(info79);
                    return;
                case 103:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info80 = new RadioResponseInfo();
                    info80.readFromParcel(hwParcel);
                    setSuppServPropertyResponse(info80);
                    return;
                case 104:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info81 = new RadioResponseInfo();
                    info81.readFromParcel(hwParcel);
                    supplyDeviceNetworkDepersonalizationResponse(info81, _hidl_request.readInt32());
                    return;
                case 105:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info82 = new RadioResponseInfo();
                    info82.readFromParcel(hwParcel);
                    hangupWithReasonResponse(info82);
                    return;
                case 106:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info83 = new RadioResponseInfo();
                    info83.readFromParcel(hwParcel);
                    setVendorSettingResponse(info83);
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info84 = new RadioResponseInfo();
                    info84.readFromParcel(hwParcel);
                    getPlmnNameFromSE13TableResponse(info84, _hidl_request.readString());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info85 = new RadioResponseInfo();
                    info85.readFromParcel(hwParcel);
                    enableCAPlusBandWidthFilterResponse(info85);
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info86 = new RadioResponseInfo();
                    info86.readFromParcel(hwParcel);
                    setGwsdModeResponse(info86);
                    return;
                case 110:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info87 = new RadioResponseInfo();
                    info87.readFromParcel(hwParcel);
                    setCallValidTimerResponse(info87);
                    return;
                case 111:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info88 = new RadioResponseInfo();
                    info88.readFromParcel(hwParcel);
                    setIgnoreSameNumberIntervalResponse(info88);
                    return;
                case 112:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info89 = new RadioResponseInfo();
                    info89.readFromParcel(hwParcel);
                    setKeepAliveByPDCPCtrlPDUResponse(info89);
                    return;
                case 113:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info90 = new RadioResponseInfo();
                    info90.readFromParcel(hwParcel);
                    setKeepAliveByIpDataResponse(info90);
                    return;
                case 114:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info91 = new RadioResponseInfo();
                    info91.readFromParcel(hwParcel);
                    enableDsdaIndicationResponse(info91);
                    return;
                case 115:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info92 = new RadioResponseInfo();
                    info92.readFromParcel(hwParcel);
                    getDsdaStatusResponse(info92, _hidl_request.readInt32());
                    return;
                case 116:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info93 = new RadioResponseInfo();
                    info93.readFromParcel(hwParcel);
                    registerCellQltyReportResponse(info93);
                    return;
                case 117:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo24 = new RadioResponseInfo();
                    responseInfo24.readFromParcel(hwParcel);
                    getSuggestedPlmnListResponse(responseInfo24, _hidl_request.readStringVector());
                    return;
                case 118:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo25 = new RadioResponseInfo();
                    responseInfo25.readFromParcel(hwParcel);
                    deactivateNrScgCommunicationResponse(responseInfo25);
                    return;
                case 119:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo26 = new RadioResponseInfo();
                    responseInfo26.readFromParcel(hwParcel);
                    getDeactivateNrScgCommunicationResponse(responseInfo26, _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 120:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo27 = new RadioResponseInfo();
                    responseInfo27.readFromParcel(hwParcel);
                    setMaxUlSpeedResponse(responseInfo27);
                    return;
                case 121:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info94 = new RadioResponseInfo();
                    info94.readFromParcel(hwParcel);
                    sendSarIndicatorResponse(info94);
                    return;
                case 122:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info95 = new RadioResponseInfo();
                    info95.readFromParcel(hwParcel);
                    RsuResponseInfo rri = new RsuResponseInfo();
                    rri.readFromParcel(hwParcel);
                    sendRsuRequestResponse(info95, rri);
                    return;
                case DataCallFailCause.INVALID_DNS_ADDR:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo28 = new RadioResponseInfo();
                    responseInfo28.readFromParcel(hwParcel);
                    sendWifiEnabledResponse(responseInfo28);
                    return;
                case DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo29 = new RadioResponseInfo();
                    responseInfo29.readFromParcel(hwParcel);
                    sendWifiAssociatedResponse(responseInfo29);
                    return;
                case 125:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo30 = new RadioResponseInfo();
                    responseInfo30.readFromParcel(hwParcel);
                    sendWifiIpAddressResponse(responseInfo30);
                    return;
                case 126:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo31 = new RadioResponseInfo();
                    responseInfo31.readFromParcel(hwParcel);
                    setNROptionResponse(responseInfo31);
                    return;
                case 127:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info96 = new RadioResponseInfo();
                    info96.readFromParcel(hwParcel);
                    getTOEInfoResponse(info96, _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 128:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info97 = new RadioResponseInfo();
                    info97.readFromParcel(hwParcel);
                    disableAllCALinksResponse(info97);
                    return;
                case 129:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info98 = new RadioResponseInfo();
                    info98.readFromParcel(hwParcel);
                    getCALinkEnableStatusResponse(info98, _hidl_request.readBool());
                    return;
                case 130:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info99 = new RadioResponseInfo();
                    info99.readFromParcel(hwParcel);
                    setCALinkEnableStatusResponse(info99);
                    return;
                case 131:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info100 = new RadioResponseInfo();
                    info100.readFromParcel(hwParcel);
                    getCALinkCapabilityListResponse(info100, _hidl_request.readStringVector());
                    return;
                case 132:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info101 = new RadioResponseInfo();
                    info101.readFromParcel(hwParcel);
                    LteData data = new LteData();
                    data.readFromParcel(hwParcel);
                    getLteDataResponse(info101, data);
                    return;
                case Cea708CCParser.Const.CODE_C1_CW5:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info102 = new RadioResponseInfo();
                    info102.readFromParcel(hwParcel);
                    getLteRRCStateResponse(info102, _hidl_request.readInt32());
                    return;
                case 134:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info103 = new RadioResponseInfo();
                    info103.readFromParcel(hwParcel);
                    setLteBandEnableStatusResponse(info103);
                    return;
                case 135:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info104 = new RadioResponseInfo();
                    info104.readFromParcel(hwParcel);
                    getBandPriorityListResponse(info104, _hidl_request.readInt32Vector());
                    return;
                case 136:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info105 = new RadioResponseInfo();
                    info105.readFromParcel(hwParcel);
                    setBandPriorityListResponse(info105);
                    return;
                case Cea708CCParser.Const.CODE_C1_DSW:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info106 = new RadioResponseInfo();
                    info106.readFromParcel(hwParcel);
                    set4x4MimoEnabledResponse(info106);
                    return;
                case Cea708CCParser.Const.CODE_C1_HDW:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info107 = new RadioResponseInfo();
                    info107.readFromParcel(hwParcel);
                    get4x4MimoEnabledResponse(info107, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_TGW:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info108 = new RadioResponseInfo();
                    info108.readFromParcel(hwParcel);
                    setLteBsrTimerResponse(info108);
                    return;
                case Cea708CCParser.Const.CODE_C1_DLW:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info109 = new RadioResponseInfo();
                    info109.readFromParcel(hwParcel);
                    getLteBsrTimerResponse(info109, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DLY:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info110 = new RadioResponseInfo();
                    info110.readFromParcel(hwParcel);
                    getLte1xRttCellListResponse(info110, Lte1xRttCellInfo.readVectorFromParcel(_hidl_request));
                    return;
                case Cea708CCParser.Const.CODE_C1_DLC:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info111 = new RadioResponseInfo();
                    info111.readFromParcel(hwParcel);
                    clearLteAvailableFileResponse(info111);
                    return;
                case 143:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info112 = new RadioResponseInfo();
                    info112.readFromParcel(hwParcel);
                    getBandModeResponse(info112, _hidl_request.readInt32Vector());
                    return;
                case Cea708CCParser.Const.CODE_C1_SPA:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info113 = new RadioResponseInfo();
                    info113.readFromParcel(hwParcel);
                    getCaBandModeResponse(info113, _hidl_request.readInt32Vector());
                    return;
                case Cea708CCParser.Const.CODE_C1_SPC:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info114 = new RadioResponseInfo();
                    info114.readFromParcel(hwParcel);
                    getCampedFemtoCellInfoResponse(info114, _hidl_request.readStringVector());
                    return;
                case Cea708CCParser.Const.CODE_C1_SPL:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info115 = new RadioResponseInfo();
                    info115.readFromParcel(hwParcel);
                    setQamEnabledResponse(info115);
                    return;
                case 147:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info116 = new RadioResponseInfo();
                    info116.readFromParcel(hwParcel);
                    getQamEnabledResponse(info116, _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 148:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info117 = new RadioResponseInfo();
                    info117.readFromParcel(hwParcel);
                    setTm9EnabledResponse(info117);
                    return;
                case 149:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info118 = new RadioResponseInfo();
                    info118.readFromParcel(hwParcel);
                    getTm9EnabledResponse(info118, _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 150:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info119 = new RadioResponseInfo();
                    info119.readFromParcel(hwParcel);
                    setLteScanDurationResponse(info119);
                    return;
                case Cea708CCParser.Const.CODE_C1_SWA:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info120 = new RadioResponseInfo();
                    info120.readFromParcel(hwParcel);
                    getLteScanDurationResponse(info120, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF0:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo32 = new RadioResponseInfo();
                    responseInfo32.readFromParcel(hwParcel);
                    setDisable2GResponse(responseInfo32);
                    return;
                case Cea708CCParser.Const.CODE_C1_DF1:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo33 = new RadioResponseInfo();
                    responseInfo33.readFromParcel(hwParcel);
                    getDisable2GResponse(responseInfo33, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF2:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo34 = new RadioResponseInfo();
                    responseInfo34.readFromParcel(hwParcel);
                    getEngineeringModeInfoResponse(responseInfo34, _hidl_request.readStringVector());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF3:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo responseInfo35 = new RadioResponseInfo();
                    responseInfo35.readFromParcel(hwParcel);
                    getIWlanRegistrationStateResponse(responseInfo35, _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF4:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info121 = new RadioResponseInfo();
                    info121.readFromParcel(hwParcel);
                    BandModeInfo data2 = new BandModeInfo();
                    data2.readFromParcel(hwParcel);
                    getAllBandModeResponse(info121, data2);
                    return;
                case Cea708CCParser.Const.CODE_C1_DF5:
                    hwParcel.enforceInterface(IMtkRadioExResponse.kInterfaceName);
                    RadioResponseInfo info122 = new RadioResponseInfo();
                    info122.readFromParcel(hwParcel);
                    setNrBandModeResponse(info122);
                    return;
                case 256067662:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    ArrayList<String> _hidl_out_descriptors = interfaceChain();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(_hidl_out_descriptors);
                    _hidl_reply.send();
                    return;
                case 256131655:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    debug(_hidl_request.readNativeHandle(), _hidl_request.readStringVector());
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 256136003:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    String _hidl_out_descriptor = interfaceDescriptor();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(_hidl_out_descriptor);
                    _hidl_reply.send();
                    return;
                case 256398152:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    ArrayList<byte[]> _hidl_out_hashchain = getHashChain();
                    hwParcel2.writeStatus(0);
                    HwBlob _hidl_blob = new HwBlob(16);
                    int _hidl_vec_size = _hidl_out_hashchain.size();
                    _hidl_blob.putInt32(8, _hidl_vec_size);
                    _hidl_blob.putBool(12, false);
                    HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
                    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                        long _hidl_array_offset_1 = (long) (_hidl_index_0 * 32);
                        byte[] _hidl_array_item_1 = _hidl_out_hashchain.get(_hidl_index_0);
                        if (_hidl_array_item_1 == null || _hidl_array_item_1.length != 32) {
                            throw new IllegalArgumentException("Array element is not of the expected length");
                        }
                        childBlob.putInt8Array(_hidl_array_offset_1, _hidl_array_item_1);
                    }
                    _hidl_blob.putBlob(0, childBlob);
                    hwParcel2.writeBuffer(_hidl_blob);
                    _hidl_reply.send();
                    return;
                case 256462420:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256921159:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    ping();
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 257049926:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    DebugInfo _hidl_out_info = getDebugInfo();
                    hwParcel2.writeStatus(0);
                    _hidl_out_info.writeToParcel(hwParcel2);
                    _hidl_reply.send();
                    return;
                case 257120595:
                    hwParcel.enforceInterface(IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
                default:
                    return;
            }
        }
    }
}
