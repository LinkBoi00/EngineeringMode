package vendor.mediatek.hardware.mtkradioex.V2_0;

import android.hardware.radio.V1_0.CdmaSmsAck;
import android.hardware.radio.V1_0.Dial;
import android.hardware.radio.V1_0.ImsSmsMessage;
import android.hardware.radio.V1_0.LastCallFailCause;
import android.hardware.radio.V1_0.SmsAcknowledgeFailCause;
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
import android.support.v4.media.MediaPlayer2;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.hqanfc.NfcCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface IMtkRadioEx extends IBase {
    public static final String kInterfaceName = "vendor.mediatek.hardware.mtkradioex@2.0::IMtkRadioEx";

    void abortCertificate(int i, int i2) throws RemoteException;

    void abortFemtocellList(int i) throws RemoteException;

    void acknowledgeLastIncomingCdmaSmsEx(int i, CdmaSmsAck cdmaSmsAck) throws RemoteException;

    void acknowledgeLastIncomingGsmSmsEx(int i, boolean z, int i2) throws RemoteException;

    void activateUiccCard(int i) throws RemoteException;

    IHwBinder asBinder();

    void cancelAvailableNetworks(int i) throws RemoteException;

    void cancelUssi(int i) throws RemoteException;

    void cfgA2offset(int i, int i2, int i3) throws RemoteException;

    void cfgB1offset(int i, int i2, int i3) throws RemoteException;

    void conferenceDial(int i, ConferenceDial conferenceDial) throws RemoteException;

    void controlCall(int i, int i2, int i3) throws RemoteException;

    void controlImsConferenceCallMember(int i, int i2, int i3, String str, int i4) throws RemoteException;

    void dataConnectionAttach(int i, int i2) throws RemoteException;

    void dataConnectionDetach(int i, int i2) throws RemoteException;

    void deactivateNrScgCommunication(int i, boolean z, boolean z2) throws RemoteException;

    void deactivateUiccCard(int i) throws RemoteException;

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void deleteUPBEntry(int i, int i2, int i3, int i4) throws RemoteException;

    void dialWithSipUri(int i, String str) throws RemoteException;

    void disableNR(int i, boolean z) throws RemoteException;

    void doGeneralSimAuthentication(int i, SimAuthStructure simAuthStructure) throws RemoteException;

    void eccPreferredRat(int i, int i2) throws RemoteException;

    void eccRedialApprove(int i, int i2, int i3) throws RemoteException;

    void editUPBEntry(int i, ArrayList<String> arrayList) throws RemoteException;

    void enableCAPlusBandWidthFilter(int i, boolean z) throws RemoteException;

    void enableCapabaility(int i, String str, int i2, int i3) throws RemoteException;

    void enableDsdaIndication(int i, boolean z) throws RemoteException;

    void enableSCGfailure(int i, boolean z, int i2, int i3, int i4) throws RemoteException;

    void forceReleaseCall(int i, int i2) throws RemoteException;

    void getATR(int i) throws RemoteException;

    void getApcInfo(int i) throws RemoteException;

    void getAvailableNetworksWithAct(int i) throws RemoteException;

    void getCallSubAddress(int i) throws RemoteException;

    void getColp(int i) throws RemoteException;

    void getColr(int i) throws RemoteException;

    void getCurrentPOLList(int i) throws RemoteException;

    void getCurrentUiccCardProvisioningStatus(int i) throws RemoteException;

    void getDeactivateNrScgCommunication(int i) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    void getDsdaStatus(int i) throws RemoteException;

    void getEccNum(int i) throws RemoteException;

    void getFemtocellList(int i) throws RemoteException;

    void getGsmBroadcastActivation(int i) throws RemoteException;

    void getGsmBroadcastLangs(int i) throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIccid(int i) throws RemoteException;

    void getImsCfgFeatureValue(int i, int i2, int i3) throws RemoteException;

    void getImsCfgProvisionValue(int i, int i2) throws RemoteException;

    void getImsCfgResourceCapValue(int i, int i2) throws RemoteException;

    void getLteReleaseVersion(int i) throws RemoteException;

    void getPOLCapability(int i) throws RemoteException;

    void getPhoneBookMemStorage(int i) throws RemoteException;

    void getPhoneBookStringsLength(int i) throws RemoteException;

    void getPlmnNameFromSE13Table(int i, int i2, int i3) throws RemoteException;

    void getProvisionValue(int i, String str) throws RemoteException;

    void getRoamingEnable(int i, int i2) throws RemoteException;

    void getRxTestResult(int i, int i2) throws RemoteException;

    void getSignalStrengthWithWcdmaEcio(int i) throws RemoteException;

    void getSmartRatSwitch(int i, int i2) throws RemoteException;

    void getSmsMemStatus(int i) throws RemoteException;

    void getSmsParameters(int i) throws RemoteException;

    void getSmsRuimMemoryStatus(int i) throws RemoteException;

    void getSuggestedPlmnList(int i, int i2, int i3, int i4) throws RemoteException;

    void getVoiceDomainPreference(int i) throws RemoteException;

    void getXcapStatus(int i) throws RemoteException;

    void handleStkCallSetupRequestFromSimWithResCode(int i, int i2) throws RemoteException;

    void hangupAll(int i) throws RemoteException;

    void hangupWithReason(int i, int i2, int i3) throws RemoteException;

    void imsBearerStateConfirm(int i, int i2, int i3, int i4) throws RemoteException;

    void imsDeregNotification(int i, int i2) throws RemoteException;

    void imsEctCommand(int i, String str, int i2) throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void modifyModemType(int i, int i2, int i3) throws RemoteException;

    void notifyEPDGScreenState(int i, int i2) throws RemoteException;

    void notifyImsServiceReady() throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void ping() throws RemoteException;

    void pullCall(int i, String str, boolean z) throws RemoteException;

    void queryCallForwardInTimeSlotStatus(int i, CallForwardInfoEx callForwardInfoEx) throws RemoteException;

    void queryFemtoCellSystemSelectionMode(int i) throws RemoteException;

    void queryNetworkLock(int i, int i2) throws RemoteException;

    void queryPhbStorageInfo(int i, int i2) throws RemoteException;

    void querySsacStatus(int i) throws RemoteException;

    void queryUPBAvailable(int i, int i2, int i3) throws RemoteException;

    void queryUPBCapability(int i) throws RemoteException;

    void queryVopsStatus(int i) throws RemoteException;

    void readPhbEntry(int i, int i2, int i3, int i4) throws RemoteException;

    void readPhoneBookEntryExt(int i, int i2, int i3) throws RemoteException;

    void readUPBAasList(int i, int i2, int i3) throws RemoteException;

    void readUPBAnrEntry(int i, int i2, int i3) throws RemoteException;

    void readUPBEmailEntry(int i, int i2, int i3) throws RemoteException;

    void readUPBGasList(int i, int i2, int i3) throws RemoteException;

    void readUPBGrpEntry(int i, int i2) throws RemoteException;

    void readUPBSneEntry(int i, int i2, int i3) throws RemoteException;

    void registerCellQltyReport(int i, String str, String str2, String str3, String str4) throws RemoteException;

    void removeCbMsg(int i, int i2, int i3) throws RemoteException;

    void resetAllConnections(int i) throws RemoteException;

    void resetMdDataRetryCount(int i, String str) throws RemoteException;

    void resetSuppServ(int i) throws RemoteException;

    void responseAcknowledgementMtk() throws RemoteException;

    void restartRILD(int i) throws RemoteException;

    void routeAuthMessage(int i, int i2, ArrayList<Byte> arrayList) throws RemoteException;

    void routeCertificate(int i, int i2, ArrayList<Byte> arrayList, ArrayList<Byte> arrayList2) throws RemoteException;

    void rttModifyRequestResponse(int i, int i2, int i3) throws RemoteException;

    void runGbaAuthentication(int i, String str, String str2, boolean z, int i2) throws RemoteException;

    void selectFemtocell(int i, String str, String str2, String str3) throws RemoteException;

    void sendAtciRequest(int i, ArrayList<Byte> arrayList) throws RemoteException;

    void sendCnap(int i, String str) throws RemoteException;

    void sendEmbmsAtCommand(int i, String str) throws RemoteException;

    void sendImsSmsEx(int i, ImsSmsMessage imsSmsMessage) throws RemoteException;

    void sendRequestRaw(int i, ArrayList<Byte> arrayList) throws RemoteException;

    void sendRequestStrings(int i, ArrayList<String> arrayList) throws RemoteException;

    void sendRsuRequest(int i, RsuRequestInfo rsuRequestInfo) throws RemoteException;

    void sendRttModifyRequest(int i, int i2, int i3) throws RemoteException;

    void sendRttText(int i, int i2, int i3, String str) throws RemoteException;

    void sendSarIndicator(int i, int i2, String str) throws RemoteException;

    void sendSubsidyLockRequest(int i, int i2, ArrayList<Byte> arrayList) throws RemoteException;

    void sendUssi(int i, String str) throws RemoteException;

    void sendVsimNotification(int i, int i2, int i3, int i4) throws RemoteException;

    void sendVsimOperation(int i, int i2, int i3, int i4, int i5, ArrayList<Byte> arrayList) throws RemoteException;

    void sendWifiAssociated(int i, String str, int i2, String str2, String str3, int i3) throws RemoteException;

    void sendWifiEnabled(int i, String str, int i2) throws RemoteException;

    void sendWifiIpAddress(int i, String str, String str2, String str3, int i2, int i3, String str4, String str5, int i4, String str6) throws RemoteException;

    void setApcMode(int i, int i2, int i3, int i4) throws RemoteException;

    void setBarringPasswordCheckedByNW(int i, String str, String str2, String str3, String str4) throws RemoteException;

    void setBgsrchDeltaSleepTimer(int i, int i2) throws RemoteException;

    void setCallAdditionalInfo(int i, ArrayList<String> arrayList) throws RemoteException;

    void setCallForwardInTimeSlot(int i, CallForwardInfoEx callForwardInfoEx) throws RemoteException;

    void setCallIndication(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void setCallSubAddress(int i, boolean z) throws RemoteException;

    void setCallValidTimer(int i, int i2) throws RemoteException;

    void setClip(int i, int i2) throws RemoteException;

    void setColp(int i, int i2) throws RemoteException;

    void setColr(int i, int i2) throws RemoteException;

    void setEccMode(int i, String str, int i2, int i3, int i4) throws RemoteException;

    void setEccNum(int i, String str, String str2) throws RemoteException;

    void setEmergencyAddressId(int i, String str) throws RemoteException;

    void setEtws(int i, int i2) throws RemoteException;

    void setFdMode(int i, int i2, int i3, int i4) throws RemoteException;

    void setFemtoCellSystemSelectionMode(int i, int i2) throws RemoteException;

    void setGsmBroadcastLangs(int i, String str) throws RemoteException;

    void setGwsdMode(int i, ArrayList<String> arrayList) throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    void setIgnoreSameNumberInterval(int i, int i2) throws RemoteException;

    void setImsBearerNotification(int i, int i2) throws RemoteException;

    void setImsCallMode(int i, int i2) throws RemoteException;

    void setImsCfgFeatureValue(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void setImsCfgProvisionValue(int i, int i2, String str) throws RemoteException;

    void setImsEnable(int i, boolean z) throws RemoteException;

    void setImsRegistrationReport(int i) throws RemoteException;

    void setImsRtpReport(int i, int i2, int i3, int i4) throws RemoteException;

    void setImsVideoEnable(int i, boolean z) throws RemoteException;

    void setImscfg(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) throws RemoteException;

    void setKeepAliveByIpData(int i, String str) throws RemoteException;

    void setKeepAliveByPDCPCtrlPDU(int i, String str) throws RemoteException;

    void setLocationInfo(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11) throws RemoteException;

    void setLteReleaseVersion(int i, int i2) throws RemoteException;

    void setMaxUlSpeed(int i, int i2) throws RemoteException;

    void setModemImsCfg(int i, String str, String str2, int i2) throws RemoteException;

    void setModemPower(int i, boolean z) throws RemoteException;

    void setNattKeepAliveStatus(int i, String str, boolean z, String str2, int i2, String str3, int i3) throws RemoteException;

    void setNetworkLock(int i, int i2, int i3, String str, String str2, String str3, String str4) throws RemoteException;

    void setNetworkSelectionModeManualWithAct(int i, String str, String str2, String str3) throws RemoteException;

    void setPOLEntry(int i, int i2, String str, int i3) throws RemoteException;

    void setPhoneBookMemStorage(int i, String str, String str2) throws RemoteException;

    void setPhonebookReady(int i, int i2) throws RemoteException;

    void setProvisionValue(int i, String str, String str2) throws RemoteException;

    void setRemoveRestrictEutranMode(int i, int i2) throws RemoteException;

    void setResponseFunctionsAssist(IAssistRadioResponse iAssistRadioResponse) throws RemoteException;

    void setResponseFunctionsCap(ICapRadioResponse iCapRadioResponse) throws RemoteException;

    void setResponseFunctionsEm(IEmRadioResponse iEmRadioResponse, IEmRadioIndication iEmRadioIndication) throws RemoteException;

    void setResponseFunctionsForAtci(IAtciResponse iAtciResponse, IAtciIndication iAtciIndication) throws RemoteException;

    void setResponseFunctionsIms(IImsRadioResponse iImsRadioResponse, IImsRadioIndication iImsRadioIndication) throws RemoteException;

    void setResponseFunctionsMtk(IMtkRadioExResponse iMtkRadioExResponse, IMtkRadioExIndication iMtkRadioExIndication) throws RemoteException;

    void setResponseFunctionsMwi(IMwiRadioResponse iMwiRadioResponse, IMwiRadioIndication iMwiRadioIndication) throws RemoteException;

    void setResponseFunctionsRcs(IRcsRadioResponse iRcsRadioResponse, IRcsRadioIndication iRcsRadioIndication) throws RemoteException;

    void setResponseFunctionsRsu(IRsuRadioResponse iRsuRadioResponse, IRsuRadioIndication iRsuRadioIndication) throws RemoteException;

    void setResponseFunctionsSE(ISERadioResponse iSERadioResponse, ISERadioIndication iSERadioIndication) throws RemoteException;

    void setResponseFunctionsSmartRatSwitch(ISmartRatSwitchRadioResponse iSmartRatSwitchRadioResponse, ISmartRatSwitchRadioIndication iSmartRatSwitchRadioIndication) throws RemoteException;

    void setResponseFunctionsSubsidyLock(ISubsidyLockResponse iSubsidyLockResponse, ISubsidyLockIndication iSubsidyLockIndication) throws RemoteException;

    void setResumeRegistration(int i, int i2) throws RemoteException;

    void setRoamingEnable(int i, ArrayList<Integer> arrayList) throws RemoteException;

    void setRttMode(int i, int i2) throws RemoteException;

    void setRxTestConfig(int i, int i2) throws RemoteException;

    void setSearchRat(int i, ArrayList<Integer> arrayList) throws RemoteException;

    void setSearchStoredFreqInfo(int i, int i2, int i3, int i4, ArrayList<Integer> arrayList) throws RemoteException;

    void setServiceStateToModem(int i, int i2, int i3, int i4, int i5, int i6, int i7) throws RemoteException;

    void setSimPower(int i, int i2) throws RemoteException;

    void setSipHeader(int i, ArrayList<String> arrayList) throws RemoteException;

    void setSipHeaderReport(int i, ArrayList<String> arrayList) throws RemoteException;

    void setSmartSceneSwitch(int i, int i2, int i3, int i4) throws RemoteException;

    void setSmsParameters(int i, SmsParams smsParams) throws RemoteException;

    void setSuppServProperty(int i, String str, String str2) throws RemoteException;

    void setTrm(int i, int i2) throws RemoteException;

    void setTxPower(int i, int i2) throws RemoteException;

    void setTxPowerStatus(int i, int i2) throws RemoteException;

    void setVendorSetting(int i, int i2, String str) throws RemoteException;

    void setVoiceDomainPreference(int i, int i2) throws RemoteException;

    void setVoicePreferStatus(int i, int i2) throws RemoteException;

    void setWfcConfig(int i, int i2, String str, String str2) throws RemoteException;

    void setWfcProfile(int i, int i2) throws RemoteException;

    void setWifiAssociated(int i, String str, int i2, String str2, String str3, int i3, String str4) throws RemoteException;

    void setWifiEnabled(int i, String str, int i2, int i3) throws RemoteException;

    void setWifiIpAddress(int i, String str, String str2, String str3, int i2, int i3, String str4, String str5, int i4, String str6) throws RemoteException;

    void setWifiPingResult(int i, int i2, int i3, int i4) throws RemoteException;

    void setWifiSignalLevel(int i, int i2, int i3) throws RemoteException;

    void setupXcapUserAgentString(int i, String str) throws RemoteException;

    void smartRatSwitch(int i, int i2, int i3) throws RemoteException;

    void supplyDepersonalization(int i, String str, int i2) throws RemoteException;

    void supplyDeviceNetworkDepersonalization(int i, String str) throws RemoteException;

    void syncDataSettingsToMd(int i, ArrayList<Integer> arrayList) throws RemoteException;

    void toggleRttAudioIndication(int i, int i2, int i3) throws RemoteException;

    void triggerModeSwitchByEcc(int i, int i2) throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void videoCallAccept(int i, int i2, int i3) throws RemoteException;

    void vtDial(int i, Dial dial) throws RemoteException;

    void vtDialWithSipUri(int i, String str) throws RemoteException;

    void writePhbEntry(int i, PhbEntryStructure phbEntryStructure) throws RemoteException;

    void writePhoneBookEntryExt(int i, PhbEntryExt phbEntryExt) throws RemoteException;

    void writeUPBGrpEntry(int i, int i2, ArrayList<Integer> arrayList) throws RemoteException;

    static IMtkRadioEx asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IMtkRadioEx)) {
            return (IMtkRadioEx) iface;
        }
        IMtkRadioEx proxy = new Proxy(binder);
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

    static IMtkRadioEx castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IMtkRadioEx getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IMtkRadioEx getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static IMtkRadioEx getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static IMtkRadioEx getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IMtkRadioEx {
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
                return "[class or subclass of vendor.mediatek.hardware.mtkradioex@2.0::IMtkRadioEx]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public void responseAcknowledgementMtk() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsMtk(IMtkRadioExResponse radioResponse, IMtkRadioExIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsIms(IImsRadioResponse radioResponse, IImsRadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsMwi(IMwiRadioResponse radioResponse, IMwiRadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsSE(ISERadioResponse radioResponse, ISERadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsEm(IEmRadioResponse radioResponse, IEmRadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsAssist(IAssistRadioResponse radioResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsCap(ICapRadioResponse radioResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsRsu(IRsuRadioResponse radioResponse, IRsuRadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void videoCallAccept(int serial, int videoMode, int callId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(videoMode);
            _hidl_request.writeInt32(callId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void imsEctCommand(int serial, String number, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(number);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void controlCall(int serial, int controlType, int callId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(controlType);
            _hidl_request.writeInt32(callId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void imsDeregNotification(int serial, int cause) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(cause);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImsEnable(int serial, boolean isOn) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(isOn);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImsVideoEnable(int serial, boolean isOn) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(isOn);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImscfg(int serial, boolean volteEnable, boolean vilteEnable, boolean vowifiEnable, boolean viwifiEnable, boolean smsEnable, boolean eimsEnable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(volteEnable);
            _hidl_request.writeBool(vilteEnable);
            _hidl_request.writeBool(vowifiEnable);
            _hidl_request.writeBool(viwifiEnable);
            _hidl_request.writeBool(smsEnable);
            _hidl_request.writeBool(eimsEnable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getProvisionValue(int serial, String provisionstring) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(provisionstring);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setProvisionValue(int serial, String provisionstring, String provisionValue) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(provisionstring);
            _hidl_request.writeString(provisionValue);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void controlImsConferenceCallMember(int serial, int controlType, int confCallId, String address, int callId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(controlType);
            _hidl_request.writeInt32(confCallId);
            _hidl_request.writeString(address);
            _hidl_request.writeInt32(callId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setWfcProfile(int serial, int wfcPreference) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(wfcPreference);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void conferenceDial(int serial, ConferenceDial dailInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dailInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setModemImsCfg(int serial, String keys, String values, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(keys);
            _hidl_request.writeString(values);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dialWithSipUri(int serial, String address) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(address);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void vtDialWithSipUri(int serial, String address) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(address);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void vtDial(int serial, Dial dialInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dialInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void forceReleaseCall(int serial, int callId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(callId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void imsBearerStateConfirm(int serial, int aid, int action, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(aid);
            _hidl_request.writeInt32(action);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImsRtpReport(int serial, int pdnId, int networkId, int timer) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(pdnId);
            _hidl_request.writeInt32(networkId);
            _hidl_request.writeInt32(timer);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void pullCall(int serial, String target, boolean isVideoCall) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(target);
            _hidl_request.writeBool(isVideoCall);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImsRegistrationReport(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEmbmsAtCommand(int serial, String data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRoamingEnable(int serial, ArrayList<Integer> config) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32Vector(config);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getRoamingEnable(int serial, int phoneId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(phoneId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBarringPasswordCheckedByNW(int serial, String facility, String oldPassword, String newPassword, String cfmPassword) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeString(oldPassword);
            _hidl_request.writeString(newPassword);
            _hidl_request.writeString(cfmPassword);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setClip(int serial, int clipEnable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(clipEnable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getColp(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getColr(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCnap(int serial, String cnapssMessage) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(cnapssMessage);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setColp(int serial, int colpEnable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(colpEnable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setColr(int serial, int colrEnable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(colrEnable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryCallForwardInTimeSlotStatus(int serial, CallForwardInfoEx callInfoEx) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            callInfoEx.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallForwardInTimeSlot(int serial, CallForwardInfoEx callInfoEx) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            callInfoEx.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void runGbaAuthentication(int serial, String nafFqdn, String nafSecureProtocolId, boolean forceRun, int netId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(nafFqdn);
            _hidl_request.writeString(nafSecureProtocolId);
            _hidl_request.writeBool(forceRun);
            _hidl_request.writeInt32(netId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendUssi(int serial, String ussiString) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ussiString);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cancelUssi(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getXcapStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void resetSuppServ(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupXcapUserAgentString(int serial, String userAgent) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(userAgent);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(48, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupAll(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(49, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallIndication(int serial, int mode, int callId, int seqNumber, int cause) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            _hidl_request.writeInt32(callId);
            _hidl_request.writeInt32(seqNumber);
            _hidl_request.writeInt32(cause);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(50, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setEccMode(int serial, String number, int enable, int airplaneMode, int imsReg) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(number);
            _hidl_request.writeInt32(enable);
            _hidl_request.writeInt32(airplaneMode);
            _hidl_request.writeInt32(imsReg);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(51, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void eccPreferredRat(int serial, int phoneType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(phoneType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(52, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setVoicePreferStatus(int serial, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(53, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setEccNum(int serial, String ecc_list_with_card, String ecc_list_no_card) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ecc_list_with_card);
            _hidl_request.writeString(ecc_list_no_card);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(54, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getEccNum(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(55, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallSubAddress(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(56, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallSubAddress(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(57, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryPhbStorageInfo(int serial, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(58, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writePhbEntry(int serial, PhbEntryStructure phbEntry) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            phbEntry.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(59, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readPhbEntry(int serial, int type, int bIndex, int eIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(bIndex);
            _hidl_request.writeInt32(eIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(60, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryUPBCapability(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(61, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void editUPBEntry(int serial, ArrayList<String> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeStringVector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(62, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteUPBEntry(int serial, int entryType, int adnIndex, int entryIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(entryType);
            _hidl_request.writeInt32(adnIndex);
            _hidl_request.writeInt32(entryIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(63, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBGasList(int serial, int startIndex, int endIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(startIndex);
            _hidl_request.writeInt32(endIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(64, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBGrpEntry(int serial, int adnIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(adnIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(65, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeUPBGrpEntry(int serial, int adnIndex, ArrayList<Integer> grpIds) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(adnIndex);
            _hidl_request.writeInt32Vector(grpIds);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(66, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPhoneBookStringsLength(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(67, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPhoneBookMemStorage(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(68, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPhoneBookMemStorage(int serial, String storage, String password) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(storage);
            _hidl_request.writeString(password);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(69, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readPhoneBookEntryExt(int serial, int index1, int index2) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index1);
            _hidl_request.writeInt32(index2);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(70, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writePhoneBookEntryExt(int serial, PhbEntryExt phbEntryExt) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            phbEntryExt.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(71, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryUPBAvailable(int serial, int eftype, int fileIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(eftype);
            _hidl_request.writeInt32(fileIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(72, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBEmailEntry(int serial, int adnIndex, int fileIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(adnIndex);
            _hidl_request.writeInt32(fileIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(73, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBSneEntry(int serial, int adnIndex, int fileIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(adnIndex);
            _hidl_request.writeInt32(fileIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(74, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBAnrEntry(int serial, int adnIndex, int fileIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(adnIndex);
            _hidl_request.writeInt32(fileIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(75, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void readUPBAasList(int serial, int startIndex, int endIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(startIndex);
            _hidl_request.writeInt32(endIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(76, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPhonebookReady(int serial, int ready) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(ready);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(77, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setModemPower(int serial, boolean isOn) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(isOn);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(78, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void triggerModeSwitchByEcc(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(79, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getATR(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(80, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIccid(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(81, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSimPower(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(82, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void activateUiccCard(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(83, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateUiccCard(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(84, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentUiccCardProvisioningStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(85, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void doGeneralSimAuthentication(int serial, SimAuthStructure simAuth) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            simAuth.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(86, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryNetworkLock(int serial, int category) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(category);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(87, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkLock(int serial, int category, int lockop, String password, String data_imsi, String gid1, String gid2) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(category);
            _hidl_request.writeInt32(lockop);
            _hidl_request.writeString(password);
            _hidl_request.writeString(data_imsi);
            _hidl_request.writeString(gid1);
            _hidl_request.writeString(gid2);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(88, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyDepersonalization(int serial, String netPin, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(netPin);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(89, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendVsimNotification(int serial, int transactionId, int eventId, int simType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(transactionId);
            _hidl_request.writeInt32(eventId);
            _hidl_request.writeInt32(simType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(90, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendVsimOperation(int serial, int transactionId, int eventId, int result, int dataLength, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(transactionId);
            _hidl_request.writeInt32(eventId);
            _hidl_request.writeInt32(result);
            _hidl_request.writeInt32(dataLength);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(91, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmsParameters(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(92, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSmsParameters(int serial, SmsParams message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(93, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmsMemStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(94, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setEtws(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(95, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void removeCbMsg(int serial, int channelId, int serialId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(channelId);
            _hidl_request.writeInt32(serialId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(96, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastLangs(int serial, String langs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(langs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(97, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getGsmBroadcastLangs(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(98, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getGsmBroadcastActivation(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(99, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendImsSmsEx(int serial, ImsSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(100, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingGsmSmsEx(int serial, boolean success, int cause) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(success);
            _hidl_request.writeInt32(cause);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(101, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingCdmaSmsEx(int serial, CdmaSmsAck smsAck) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            smsAck.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(102, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendRequestRaw(int serial, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(103, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendRequestStrings(int serial, ArrayList<String> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeStringVector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(104, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResumeRegistration(int serial, int sessionId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(sessionId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(105, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void modifyModemType(int serial, int applyType, int modemType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(applyType);
            _hidl_request.writeInt32(modemType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(106, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmsRuimMemoryStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeManualWithAct(int serial, String operatorNumeric, String act, String mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(operatorNumeric);
            _hidl_request.writeString(act);
            _hidl_request.writeString(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableNetworksWithAct(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrengthWithWcdmaEcio(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(110, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cancelAvailableNetworks(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(111, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getFemtocellList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(112, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void abortFemtocellList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(113, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void selectFemtocell(int serial, String operatorNumeric, String act, String csgId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(operatorNumeric);
            _hidl_request.writeString(act);
            _hidl_request.writeString(csgId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(114, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryFemtoCellSystemSelectionMode(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(115, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setFemtoCellSystemSelectionMode(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(116, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setServiceStateToModem(int serial, int voiceRegState, int dataRegState, int voiceRoamingType, int dataRoamingType, int rilVoiceRegState, int rilDataRegState) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(voiceRegState);
            _hidl_request.writeInt32(dataRegState);
            _hidl_request.writeInt32(voiceRoamingType);
            _hidl_request.writeInt32(dataRoamingType);
            _hidl_request.writeInt32(rilVoiceRegState);
            _hidl_request.writeInt32(rilDataRegState);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(117, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cfgA2offset(int serial, int offset, int threshBound) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(offset);
            _hidl_request.writeInt32(threshBound);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(118, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cfgB1offset(int serial, int offset, int threshBound) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(offset);
            _hidl_request.writeInt32(threshBound);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(119, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableSCGfailure(int serial, boolean enable, int T1, int P1, int T2) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            _hidl_request.writeInt32(T1);
            _hidl_request.writeInt32(P1);
            _hidl_request.writeInt32(T2);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(120, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void disableNR(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(121, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTxPower(int serial, int limitpower) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(limitpower);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(122, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSearchStoredFreqInfo(int serial, int operation, int plmn_id, int rat, ArrayList<Integer> freq) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(operation);
            _hidl_request.writeInt32(plmn_id);
            _hidl_request.writeInt32(rat);
            _hidl_request.writeInt32Vector(freq);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_DNS_ADDR, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSearchRat(int serial, ArrayList<Integer> rat) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32Vector(rat);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBgsrchDeltaSleepTimer(int serial, int sleepDuration) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(sleepDuration);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(125, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRxTestConfig(int serial, int antType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(antType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(126, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getRxTestResult(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(127, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPOLCapability(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(128, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentPOLList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(129, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPOLEntry(int serial, int index, String numeric, int nAct) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index);
            _hidl_request.writeString(numeric);
            _hidl_request.writeInt32(nAct);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(130, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setFdMode(int serial, int mode, int param1, int param2) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            _hidl_request.writeInt32(param1);
            _hidl_request.writeInt32(param2);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(131, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTrm(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(132, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void handleStkCallSetupRequestFromSimWithResCode(int serial, int resultCode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(resultCode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_CW5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsForAtci(IAtciResponse atciResponse, IAtciIndication atciIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(atciResponse == null ? null : atciResponse.asBinder());
            if (atciIndication != null) {
                iHwBinder = atciIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(134, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendAtciRequest(int serial, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(135, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void restartRILD(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(136, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void syncDataSettingsToMd(int serial, ArrayList<Integer> settings) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32Vector(settings);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DSW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void resetMdDataRetryCount(int serial, String apn) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(apn);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_HDW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRemoveRestrictEutranMode(int serial, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_TGW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setVoiceDomainPreference(int serial, int vdp) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(vdp);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLW, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setWifiEnabled(int serial, String ifName, int isWifiEnabled, int isFlightModeOn) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ifName);
            _hidl_request.writeInt32(isWifiEnabled);
            _hidl_request.writeInt32(isFlightModeOn);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLY, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setWifiAssociated(int serial, String ifName, int associated, String ssid, String apMac, int mtuSize, String ueMac) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ifName);
            _hidl_request.writeInt32(associated);
            _hidl_request.writeString(ssid);
            _hidl_request.writeString(apMac);
            _hidl_request.writeInt32(mtuSize);
            _hidl_request.writeString(ueMac);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DLC, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setWifiSignalLevel(int serial, int rssi, int snr) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(rssi);
            _hidl_request.writeInt32(snr);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(143, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setWifiIpAddress(int serial, String ifName, String ipv4Addr, String ipv6Addr, int ipv4PrefixLen, int ipv6PrefixLen, String ipv4Gateway, String ipv6Gateway, int dnsCount, String dnsServers) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ifName);
            _hidl_request.writeString(ipv4Addr);
            _hidl_request.writeString(ipv6Addr);
            _hidl_request.writeInt32(ipv4PrefixLen);
            _hidl_request.writeInt32(ipv6PrefixLen);
            _hidl_request.writeString(ipv4Gateway);
            _hidl_request.writeString(ipv6Gateway);
            _hidl_request.writeInt32(dnsCount);
            _hidl_request.writeString(dnsServers);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPA, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setWfcConfig(int serial, int setting, String ifName, String value) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(setting);
            _hidl_request.writeString(ifName);
            _hidl_request.writeString(value);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPC, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void querySsacStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SPL, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLocationInfo(int serial, String accountId, String broadcastFlag, String latitude, String longitude, String accuracy, String method, String city, String state, String zip, String countryCode, String ueWlanMac) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(accountId);
            _hidl_request.writeString(broadcastFlag);
            _hidl_request.writeString(latitude);
            _hidl_request.writeString(longitude);
            _hidl_request.writeString(accuracy);
            _hidl_request.writeString(method);
            _hidl_request.writeString(city);
            _hidl_request.writeString(state);
            _hidl_request.writeString(zip);
            _hidl_request.writeString(countryCode);
            _hidl_request.writeString(ueWlanMac);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(147, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setEmergencyAddressId(int serial, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(148, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNattKeepAliveStatus(int serial, String ifName, boolean enable, String srcIp, int srcPort, String dstIp, int dstPort) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ifName);
            _hidl_request.writeBool(enable);
            _hidl_request.writeString(srcIp);
            _hidl_request.writeInt32(srcPort);
            _hidl_request.writeString(dstIp);
            _hidl_request.writeInt32(dstPort);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(149, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setWifiPingResult(int serial, int rat, int latency, int pktloss) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(rat);
            _hidl_request.writeInt32(latency);
            _hidl_request.writeInt32(pktloss);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(150, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setApcMode(int serial, int mode, int reportMode, int interval) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            _hidl_request.writeInt32(reportMode);
            _hidl_request.writeInt32(interval);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_SWA, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getApcInfo(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF0, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImsBearerNotification(int serial, int enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImsCfgFeatureValue(int serial, int featureId, int network, int value, int isLast) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(featureId);
            _hidl_request.writeInt32(network);
            _hidl_request.writeInt32(value);
            _hidl_request.writeInt32(isLast);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsCfgFeatureValue(int serial, int featureId, int network) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(featureId);
            _hidl_request.writeInt32(network);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImsCfgProvisionValue(int serial, int configId, String value) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(configId);
            _hidl_request.writeString(value);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsCfgProvisionValue(int serial, int configId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(configId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsCfgResourceCapValue(int serial, int featureId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(featureId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(Cea708CCParser.Const.CODE_C1_DF6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dataConnectionAttach(int serial, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(159, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dataConnectionDetach(int serial, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(160, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void resetAllConnections(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(161, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLteReleaseVersion(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(162, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLteReleaseVersion(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(163, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTxPowerStatus(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(164, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSuppServProperty(int serial, String name, String value) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(name);
            _hidl_request.writeString(value);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(165, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyDeviceNetworkDepersonalization(int serial, String pwd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(pwd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(166, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void notifyEPDGScreenState(int serial, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(167, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupWithReason(int serial, int callId, int reason) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(callId);
            _hidl_request.writeInt32(reason);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(168, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsSubsidyLock(ISubsidyLockResponse sublockResp, ISubsidyLockIndication sublockInd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(sublockResp == null ? null : sublockResp.asBinder());
            if (sublockInd != null) {
                iHwBinder = sublockInd.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(169, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsRcs(IRcsRadioResponse radioResponse, IRcsRadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(170, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSubsidyLockRequest(int serial, int reqType, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(reqType);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(171, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setVendorSetting(int serial, int setting, String value) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(setting);
            _hidl_request.writeString(value);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(172, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRttMode(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(173, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendRttModifyRequest(int serial, int callId, int newMode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(callId);
            _hidl_request.writeInt32(newMode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(174, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendRttText(int serial, int callId, int lenOfString, String text) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(callId);
            _hidl_request.writeInt32(lenOfString);
            _hidl_request.writeString(text);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(175, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void rttModifyRequestResponse(int serial, int callId, int result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(callId);
            _hidl_request.writeInt32(result);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(176, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void toggleRttAudioIndication(int serial, int callId, int audio) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(callId);
            _hidl_request.writeInt32(audio);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(177, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void queryVopsStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(178, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void notifyImsServiceReady() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(179, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPlmnNameFromSE13Table(int serial, int mcc, int mnc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mcc);
            _hidl_request.writeInt32(mnc);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(180, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableCAPlusBandWidthFilter(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(181, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceDomainPreference(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(182, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSipHeader(int serial, ArrayList<String> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeStringVector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(183, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSipHeaderReport(int serial, ArrayList<String> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeStringVector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(184, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setImsCallMode(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(185, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGwsdMode(int serial, ArrayList<String> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeStringVector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(186, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallValidTimer(int serial, int timer) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(timer);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(187, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIgnoreSameNumberInterval(int serial, int interval) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(interval);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(188, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setKeepAliveByPDCPCtrlPDU(int serial, String config) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(config);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(189, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setKeepAliveByIpData(int serial, String config) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(config);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(190, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableDsdaIndication(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(191, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDsdaStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(192, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void registerCellQltyReport(int serial, String registerQuality, String type, String thresholdValues, String triggerTime) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(registerQuality);
            _hidl_request.writeString(type);
            _hidl_request.writeString(thresholdValues);
            _hidl_request.writeString(triggerTime);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(193, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSuggestedPlmnList(int serial, int rat, int num, int timer) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(rat);
            _hidl_request.writeInt32(num);
            _hidl_request.writeInt32(timer);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(194, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void routeCertificate(int serial, int uid, ArrayList<Byte> cert, ArrayList<Byte> msg) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(uid);
            _hidl_request.writeInt8Vector(cert);
            _hidl_request.writeInt8Vector(msg);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(195, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void routeAuthMessage(int serial, int uid, ArrayList<Byte> msg) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(uid);
            _hidl_request.writeInt8Vector(msg);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(196, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void enableCapabaility(int serial, String id, int uid, int toActive) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(id);
            _hidl_request.writeInt32(uid);
            _hidl_request.writeInt32(toActive);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(197, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void abortCertificate(int serial, int uid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(uid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(198, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void eccRedialApprove(int serial, int approve, int callId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(approve);
            _hidl_request.writeInt32(callId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(199, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateNrScgCommunication(int serial, boolean deactivate, boolean allowSCGAdd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(deactivate);
            _hidl_request.writeBool(allowSCGAdd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(MediaPlayer2.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDeactivateNrScgCommunication(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_REQ, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setMaxUlSpeed(int serial, int ulSpeed) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(ulSpeed);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_NTF, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setResponseFunctionsSmartRatSwitch(ISmartRatSwitchRadioResponse radioResponse, ISmartRatSwitchRadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_RSP, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void smartRatSwitch(int serial, int mode, int rat) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            _hidl_request.writeInt32(rat);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(NfcCommand.CommandType.MTK_NFC_EM_MSG_END, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmartRatSwitch(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(205, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSmartSceneSwitch(int serial, int mode, int tGear, int lGear) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            _hidl_request.writeInt32(tGear);
            _hidl_request.writeInt32(lGear);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(206, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSarIndicator(int serial, int sar_cmd_type, String sar_parameter) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(sar_cmd_type);
            _hidl_request.writeString(sar_parameter);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(207, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallAdditionalInfo(int serial, ArrayList<String> info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeStringVector(info);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(208, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendRsuRequest(int serial, RsuRequestInfo rri) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            rri.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(209, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendWifiEnabled(int serial, String ifName, int isWifiEnabled) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ifName);
            _hidl_request.writeInt32(isWifiEnabled);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(210, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendWifiAssociated(int serial, String ifName, int associated, String ssid, String apMac, int mtuSize) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ifName);
            _hidl_request.writeInt32(associated);
            _hidl_request.writeString(ssid);
            _hidl_request.writeString(apMac);
            _hidl_request.writeInt32(mtuSize);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(SmsAcknowledgeFailCause.MEMORY_CAPACITY_EXCEEDED, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendWifiIpAddress(int serial, String ifName, String ipv4Addr, String ipv6Addr, int ipv4PrefixLen, int ipv6PrefixLen, String ipv4Gateway, String ipv6Gateway, int dnsCount, String dnsServers) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IMtkRadioEx.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ifName);
            _hidl_request.writeString(ipv4Addr);
            _hidl_request.writeString(ipv6Addr);
            _hidl_request.writeInt32(ipv4PrefixLen);
            _hidl_request.writeInt32(ipv6PrefixLen);
            _hidl_request.writeString(ipv4Gateway);
            _hidl_request.writeString(ipv6Gateway);
            _hidl_request.writeInt32(dnsCount);
            _hidl_request.writeString(dnsServers);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(212, _hidl_request, _hidl_reply, 1);
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

    public static abstract class Stub extends HwBinder implements IMtkRadioEx {
        public IHwBinder asBinder() {
            return this;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{IMtkRadioEx.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IMtkRadioEx.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{76, -66, -61, -49, -120, 33, 35, -48, 109, -32, -71, -85, -40, -103, -9, 64, -104, -16, -25, 104, -80, -86, -75, 44, -25, -104, 126, -71, -106, -15, 4, -96}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
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
            if (IMtkRadioEx.kInterfaceName.equals(descriptor)) {
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
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    responseAcknowledgementMtk();
                    return;
                case 2:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsMtk(IMtkRadioExResponse.asInterface(_hidl_request.readStrongBinder()), IMtkRadioExIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsIms(IImsRadioResponse.asInterface(_hidl_request.readStrongBinder()), IImsRadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsMwi(IMwiRadioResponse.asInterface(_hidl_request.readStrongBinder()), IMwiRadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsSE(ISERadioResponse.asInterface(_hidl_request.readStrongBinder()), ISERadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsEm(IEmRadioResponse.asInterface(_hidl_request.readStrongBinder()), IEmRadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsAssist(IAssistRadioResponse.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsCap(ICapRadioResponse.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsRsu(IRsuRadioResponse.asInterface(_hidl_request.readStrongBinder()), IRsuRadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    videoCallAccept(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 11:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    imsEctCommand(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case 12:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    controlCall(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 13:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    imsDeregNotification(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 14:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImsEnable(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 15:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImsVideoEnable(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 16:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImscfg(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case 17:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getProvisionValue(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 18:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setProvisionValue(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 19:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    controlImsConferenceCallMember(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case 20:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setWfcProfile(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 21:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial = _hidl_request.readInt32();
                    ConferenceDial dailInfo = new ConferenceDial();
                    dailInfo.readFromParcel(hwParcel);
                    conferenceDial(serial, dailInfo);
                    return;
                case 22:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setModemImsCfg(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case 23:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    dialWithSipUri(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 24:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    vtDialWithSipUri(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 25:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial2 = _hidl_request.readInt32();
                    Dial dialInfo = new Dial();
                    dialInfo.readFromParcel(hwParcel);
                    vtDial(serial2, dialInfo);
                    return;
                case 26:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    forceReleaseCall(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 27:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    imsBearerStateConfirm(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 28:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImsRtpReport(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 29:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    pullCall(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readBool());
                    return;
                case 30:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImsRegistrationReport(_hidl_request.readInt32());
                    return;
                case 31:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendEmbmsAtCommand(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 32:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setRoamingEnable(_hidl_request.readInt32(), _hidl_request.readInt32Vector());
                    return;
                case 33:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getRoamingEnable(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 34:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setBarringPasswordCheckedByNW(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 35:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setClip(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 36:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getColp(_hidl_request.readInt32());
                    return;
                case 37:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getColr(_hidl_request.readInt32());
                    return;
                case 38:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendCnap(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 39:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setColp(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 40:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setColr(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 41:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial3 = _hidl_request.readInt32();
                    CallForwardInfoEx callInfoEx = new CallForwardInfoEx();
                    callInfoEx.readFromParcel(hwParcel);
                    queryCallForwardInTimeSlotStatus(serial3, callInfoEx);
                    return;
                case 42:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial4 = _hidl_request.readInt32();
                    CallForwardInfoEx callInfoEx2 = new CallForwardInfoEx();
                    callInfoEx2.readFromParcel(hwParcel);
                    setCallForwardInTimeSlot(serial4, callInfoEx2);
                    return;
                case 43:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    runGbaAuthentication(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readBool(), _hidl_request.readInt32());
                    return;
                case 44:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendUssi(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 45:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    cancelUssi(_hidl_request.readInt32());
                    return;
                case 46:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getXcapStatus(_hidl_request.readInt32());
                    return;
                case 47:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    resetSuppServ(_hidl_request.readInt32());
                    return;
                case 48:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setupXcapUserAgentString(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 49:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    hangupAll(_hidl_request.readInt32());
                    return;
                case 50:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setCallIndication(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 51:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setEccMode(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 52:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    eccPreferredRat(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 53:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setVoicePreferStatus(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setEccNum(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 55:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getEccNum(_hidl_request.readInt32());
                    return;
                case 56:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setCallSubAddress(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 57:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getCallSubAddress(_hidl_request.readInt32());
                    return;
                case 58:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    queryPhbStorageInfo(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 59:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial5 = _hidl_request.readInt32();
                    PhbEntryStructure phbEntry = new PhbEntryStructure();
                    phbEntry.readFromParcel(hwParcel);
                    writePhbEntry(serial5, phbEntry);
                    return;
                case 60:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    readPhbEntry(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 61:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    queryUPBCapability(_hidl_request.readInt32());
                    return;
                case 62:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    editUPBEntry(_hidl_request.readInt32(), _hidl_request.readStringVector());
                    return;
                case 63:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    deleteUPBEntry(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 64:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    readUPBGasList(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 65:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    readUPBGrpEntry(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    writeUPBGrpEntry(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32Vector());
                    return;
                case 67:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getPhoneBookStringsLength(_hidl_request.readInt32());
                    return;
                case 68:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getPhoneBookMemStorage(_hidl_request.readInt32());
                    return;
                case LastCallFailCause.REQUESTED_FACILITY_NOT_IMPLEMENTED:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setPhoneBookMemStorage(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 70:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    readPhoneBookEntryExt(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial6 = _hidl_request.readInt32();
                    PhbEntryExt phbEntryExt = new PhbEntryExt();
                    phbEntryExt.readFromParcel(hwParcel);
                    writePhoneBookEntryExt(serial6, phbEntryExt);
                    return;
                case 72:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    queryUPBAvailable(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    readUPBEmailEntry(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 74:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    readUPBSneEntry(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 75:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    readUPBAnrEntry(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 76:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    readUPBAasList(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 77:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setPhonebookReady(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 78:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setModemPower(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 79:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    triggerModeSwitchByEcc(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case NgranBands.BAND_80:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getATR(_hidl_request.readInt32());
                    return;
                case 81:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getIccid(_hidl_request.readInt32());
                    return;
                case NgranBands.BAND_82:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setSimPower(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case NgranBands.BAND_83:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    activateUiccCard(_hidl_request.readInt32());
                    return;
                case NgranBands.BAND_84:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    deactivateUiccCard(_hidl_request.readInt32());
                    return;
                case EutranBands.BAND_85:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getCurrentUiccCardProvisioningStatus(_hidl_request.readInt32());
                    return;
                case NgranBands.BAND_86:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial7 = _hidl_request.readInt32();
                    SimAuthStructure simAuth = new SimAuthStructure();
                    simAuth.readFromParcel(hwParcel);
                    doGeneralSimAuthentication(serial7, simAuth);
                    return;
                case 87:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    queryNetworkLock(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 88:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setNetworkLock(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case NgranBands.BAND_89:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    supplyDepersonalization(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case NgranBands.BAND_90:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendVsimNotification(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 91:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendVsimOperation(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 92:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getSmsParameters(_hidl_request.readInt32());
                    return;
                case NgranBands.BAND_93:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial8 = _hidl_request.readInt32();
                    SmsParams message = new SmsParams();
                    message.readFromParcel(hwParcel);
                    setSmsParameters(serial8, message);
                    return;
                case NgranBands.BAND_94:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getSmsMemStatus(_hidl_request.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setEtws(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 96:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    removeCbMsg(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 97:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setGsmBroadcastLangs(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 98:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getGsmBroadcastLangs(_hidl_request.readInt32());
                    return;
                case 99:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getGsmBroadcastActivation(_hidl_request.readInt32());
                    return;
                case 100:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial9 = _hidl_request.readInt32();
                    ImsSmsMessage message2 = new ImsSmsMessage();
                    message2.readFromParcel(hwParcel);
                    sendImsSmsEx(serial9, message2);
                    return;
                case 101:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    acknowledgeLastIncomingGsmSmsEx(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readInt32());
                    return;
                case 102:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial10 = _hidl_request.readInt32();
                    CdmaSmsAck smsAck = new CdmaSmsAck();
                    smsAck.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSmsEx(serial10, smsAck);
                    return;
                case 103:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendRequestRaw(_hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 104:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendRequestStrings(_hidl_request.readInt32(), _hidl_request.readStringVector());
                    return;
                case 105:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResumeRegistration(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 106:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    modifyModemType(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_REQ:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getSmsRuimMemoryStatus(_hidl_request.readInt32());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_ALS_CARD_MODE_RSP:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setNetworkSelectionModeManualWithAct(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_POLLING_MODE_REQ:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getAvailableNetworksWithAct(_hidl_request.readInt32());
                    return;
                case 110:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getSignalStrengthWithWcdmaEcio(_hidl_request.readInt32());
                    return;
                case 111:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    cancelAvailableNetworks(_hidl_request.readInt32());
                    return;
                case 112:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getFemtocellList(_hidl_request.readInt32());
                    return;
                case 113:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    abortFemtocellList(_hidl_request.readInt32());
                    return;
                case 114:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    selectFemtocell(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 115:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    queryFemtoCellSystemSelectionMode(_hidl_request.readInt32());
                    return;
                case 116:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setFemtoCellSystemSelectionMode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 117:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setServiceStateToModem(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 118:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    cfgA2offset(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 119:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    cfgB1offset(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 120:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    enableSCGfailure(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 121:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    disableNR(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 122:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setTxPower(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case DataCallFailCause.INVALID_DNS_ADDR:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setSearchStoredFreqInfo(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32Vector());
                    return;
                case DataCallFailCause.INVALID_PCSCF_OR_DNS_ADDRESS:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setSearchRat(_hidl_request.readInt32(), _hidl_request.readInt32Vector());
                    return;
                case 125:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setBgsrchDeltaSleepTimer(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 126:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setRxTestConfig(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 127:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getRxTestResult(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 128:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getPOLCapability(_hidl_request.readInt32());
                    return;
                case 129:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getCurrentPOLList(_hidl_request.readInt32());
                    return;
                case 130:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setPOLEntry(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case 131:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setFdMode(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 132:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setTrm(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_CW5:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    handleStkCallSetupRequestFromSimWithResCode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 134:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsForAtci(IAtciResponse.asInterface(_hidl_request.readStrongBinder()), IAtciIndication.asInterface(_hidl_request.readStrongBinder()));
                    return;
                case 135:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendAtciRequest(_hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 136:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    restartRILD(_hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DSW:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    syncDataSettingsToMd(_hidl_request.readInt32(), _hidl_request.readInt32Vector());
                    return;
                case Cea708CCParser.Const.CODE_C1_HDW:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    resetMdDataRetryCount(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case Cea708CCParser.Const.CODE_C1_TGW:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setRemoveRestrictEutranMode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DLW:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setVoiceDomainPreference(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DLY:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setWifiEnabled(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DLC:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setWifiAssociated(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 143:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setWifiSignalLevel(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_SPA:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setWifiIpAddress(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case Cea708CCParser.Const.CODE_C1_SPC:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setWfcConfig(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case Cea708CCParser.Const.CODE_C1_SPL:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    querySsacStatus(_hidl_request.readInt32());
                    return;
                case 147:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setLocationInfo(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 148:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setEmergencyAddressId(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 149:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setNattKeepAliveStatus(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readBool(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case 150:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setWifiPingResult(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_SWA:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setApcMode(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF0:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getApcInfo(_hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF1:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImsBearerNotification(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF2:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImsCfgFeatureValue(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF3:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getImsCfgFeatureValue(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF4:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImsCfgProvisionValue(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF5:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getImsCfgProvisionValue(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case Cea708CCParser.Const.CODE_C1_DF6:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getImsCfgResourceCapValue(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 159:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    dataConnectionAttach(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 160:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    dataConnectionDetach(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 161:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    resetAllConnections(_hidl_request.readInt32());
                    return;
                case 162:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setLteReleaseVersion(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 163:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getLteReleaseVersion(_hidl_request.readInt32());
                    return;
                case 164:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setTxPowerStatus(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 165:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setSuppServProperty(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 166:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    supplyDeviceNetworkDepersonalization(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 167:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    notifyEPDGScreenState(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 168:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    hangupWithReason(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 169:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsSubsidyLock(ISubsidyLockResponse.asInterface(_hidl_request.readStrongBinder()), ISubsidyLockIndication.asInterface(_hidl_request.readStrongBinder()));
                    return;
                case 170:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsRcs(IRcsRadioResponse.asInterface(_hidl_request.readStrongBinder()), IRcsRadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    return;
                case 171:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendSubsidyLockRequest(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 172:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setVendorSetting(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 173:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setRttMode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 174:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendRttModifyRequest(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 175:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendRttText(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 176:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    rttModifyRequestResponse(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 177:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    toggleRttAudioIndication(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 178:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    queryVopsStatus(_hidl_request.readInt32());
                    return;
                case 179:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    notifyImsServiceReady();
                    return;
                case 180:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getPlmnNameFromSE13Table(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 181:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    enableCAPlusBandWidthFilter(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 182:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getVoiceDomainPreference(_hidl_request.readInt32());
                    return;
                case 183:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setSipHeader(_hidl_request.readInt32(), _hidl_request.readStringVector());
                    return;
                case 184:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setSipHeaderReport(_hidl_request.readInt32(), _hidl_request.readStringVector());
                    return;
                case 185:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setImsCallMode(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 186:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setGwsdMode(_hidl_request.readInt32(), _hidl_request.readStringVector());
                    return;
                case 187:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setCallValidTimer(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 188:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setIgnoreSameNumberInterval(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 189:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setKeepAliveByPDCPCtrlPDU(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 190:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setKeepAliveByIpData(_hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 191:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    enableDsdaIndication(_hidl_request.readInt32(), _hidl_request.readBool());
                    return;
                case 192:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getDsdaStatus(_hidl_request.readInt32());
                    return;
                case 193:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    registerCellQltyReport(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString());
                    return;
                case 194:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getSuggestedPlmnList(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 195:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    routeCertificate(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt8Vector(), _hidl_request.readInt8Vector());
                    return;
                case 196:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    routeAuthMessage(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt8Vector());
                    return;
                case 197:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    enableCapabaility(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 198:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    abortCertificate(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 199:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    eccRedialApprove(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case MediaPlayer2.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    deactivateNrScgCommunication(_hidl_request.readInt32(), _hidl_request.readBool(), _hidl_request.readBool());
                    return;
                case NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_REQ:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getDeactivateNrScgCommunication(_hidl_request.readInt32());
                    return;
                case NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_NTF:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setMaxUlSpeed(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case NfcCommand.CommandType.MTK_NFC_FM_SWP_TEST_RSP:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setResponseFunctionsSmartRatSwitch(ISmartRatSwitchRadioResponse.asInterface(_hidl_request.readStrongBinder()), ISmartRatSwitchRadioIndication.asInterface(_hidl_request.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case NfcCommand.CommandType.MTK_NFC_EM_MSG_END:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    smartRatSwitch(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 205:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    getSmartRatSwitch(_hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 206:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setSmartSceneSwitch(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readInt32());
                    return;
                case 207:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendSarIndicator(_hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString());
                    return;
                case 208:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    setCallAdditionalInfo(_hidl_request.readInt32(), _hidl_request.readStringVector());
                    return;
                case 209:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    int serial11 = _hidl_request.readInt32();
                    RsuRequestInfo rri = new RsuRequestInfo();
                    rri.readFromParcel(hwParcel);
                    sendRsuRequest(serial11, rri);
                    return;
                case 210:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendWifiEnabled(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case SmsAcknowledgeFailCause.MEMORY_CAPACITY_EXCEEDED:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendWifiAssociated(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32());
                    return;
                case 212:
                    hwParcel.enforceInterface(IMtkRadioEx.kInterfaceName);
                    sendWifiIpAddress(_hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readInt32(), _hidl_request.readString(), _hidl_request.readString(), _hidl_request.readInt32(), _hidl_request.readString());
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
