package com.mediatek.engineermode.aospradio;

import android.hardware.radio.V1_0.ActivityStatsInfo;
import android.hardware.radio.V1_0.Call;
import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CardStatus;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.DataRegStateResult;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.IccIoResult;
import android.hardware.radio.V1_0.LastCallFailCauseInfo;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.LceStatusInfo;
import android.hardware.radio.V1_0.NeighboringCell;
import android.hardware.radio.V1_0.OperatorInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.V1_0.SendSmsResult;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.SignalStrength;
import android.hardware.radio.V1_0.VoiceRegStateResult;
import android.hardware.radio.V1_1.KeepaliveStatus;
import android.hardware.radio.V1_3.IRadioResponse;
import android.os.AsyncResult;
import android.os.Message;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.RIL;
import com.mediatek.engineermode.Elog;
import java.util.ArrayList;

public class RadioResponse extends IRadioResponse.Stub {
    private static final String TAG = "RadioResponse";
    private boolean isEnabledLast = true;

    public RadioResponse(RIL ril) {
    }

    private void sendMessageResponse(Message msg, Object ret) {
        if (msg != null) {
            AsyncResult.forMessage(msg, ret, (Throwable) null);
            msg.sendToTarget();
        }
    }

    private void sendMessageResponseError(Message msg, int error, Object ret) {
        CommandException ex = CommandException.fromRilErrno(error);
        if (msg != null) {
            AsyncResult.forMessage(msg, ret, ex);
            msg.sendToTarget();
        }
    }

    public void resetModemStatus() {
        this.isEnabledLast = false;
    }

    public void getModemStackStatusResponse(RadioResponseInfo responseInfo, boolean isEnabled) {
        Elog.d(TAG, "getModemStatusResponse isEnabled = " + isEnabled + ", Last: " + this.isEnabledLast);
        Message msg = EmRadioHidlAosp.mHandler.obtainMessage(EmRadioHidlAosp.mWhat);
        if (!isEnabled) {
            this.isEnabledLast = false;
        } else if (isEnabled && !this.isEnabledLast && responseInfo.error == 0) {
            sendMessageResponse(msg, Boolean.valueOf(isEnabled));
            this.isEnabledLast = isEnabled;
        } else if (responseInfo.error != 0) {
            sendMessageResponseError(msg, responseInfo.error, Boolean.valueOf(isEnabled));
        }
    }

    public void enableModemResponse(RadioResponseInfo responseInfo) {
        Elog.d(TAG, "enableModemResponse ");
        Message msg = EmRadioHidlAosp.mRequestList.get(responseInfo.serial);
        if (msg != null) {
            EmRadioHidlAosp.mRequestList.remove(responseInfo.serial);
        }
        if (responseInfo.error == 0) {
            sendMessageResponse(msg, (Object) null);
        } else {
            sendMessageResponseError(msg, responseInfo.error, (Object) null);
        }
    }

    public void acceptCallResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in acceptCallResponse");
    }

    public void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in acknowledgeIncomingGsmSmsWithPduResponse");
    }

    public void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in acknowledgeLastIncomingCdmaSmsResponse");
    }

    public void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in acknowledgeLastIncomingGsmSmsResponse");
    }

    public void acknowledgeRequest(int info) {
        Elog.d(TAG, "No implementation in acknowledgeRequest");
    }

    public void cancelPendingUssdResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in cancelPendingUssdResponse");
    }

    public void changeIccPin2ForAppResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in changeIccPin2ForAppResponse");
    }

    public void changeIccPinForAppResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in changeIccPinForAppResponse");
    }

    public void conferenceResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in conferenceResponse");
    }

    public void deactivateDataCallResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in deactivateDataCallResponse");
    }

    public void deleteSmsOnRuimResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in deleteSmsOnRuimResponse");
    }

    public void deleteSmsOnSimResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in deleteSmsOnSimResponse");
    }

    public void dialResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in dialResponse");
    }

    public void exitEmergencyCallbackModeResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in exitEmergencyCallbackModeResponse");
    }

    public void explicitCallTransferResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in explicitCallTransferResponse");
    }

    public void getAllowedCarriersResponse(RadioResponseInfo info, boolean arg1, CarrierRestrictions arg2) {
        Elog.d(TAG, "No implementation in getAllowedCarriersResponse");
    }

    public void getAvailableBandModesResponse(RadioResponseInfo info, ArrayList<Integer> arrayList) {
        Elog.d(TAG, "No implementation in getAvailableBandModesResponse");
    }

    public void getAvailableNetworksResponse(RadioResponseInfo info, ArrayList<OperatorInfo> arrayList) {
        Elog.d(TAG, "No implementation in getAvailableNetworksResponse");
    }

    public void getBasebandVersionResponse(RadioResponseInfo info, String arg1) {
        Elog.d(TAG, "No implementation in getBasebandVersionResponse");
    }

    public void getCDMASubscriptionResponse(RadioResponseInfo info, String arg1, String arg2, String arg3, String arg4, String arg5) {
        Elog.d(TAG, "No implementation in getCDMASubscriptionResponse");
    }

    public void getCallForwardStatusResponse(RadioResponseInfo info, ArrayList<CallForwardInfo> arrayList) {
        Elog.d(TAG, "No implementation in getCallForwardStatusResponse");
    }

    public void getCallWaitingResponse(RadioResponseInfo info, boolean arg1, int arg2) {
        Elog.d(TAG, "No implementation in getCallWaitingResponse");
    }

    public void getCdmaBroadcastConfigResponse(RadioResponseInfo info, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) {
        Elog.d(TAG, "No implementation in getCdmaBroadcastConfigResponse");
    }

    public void getCdmaRoamingPreferenceResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in getCdmaRoamingPreferenceResponse");
    }

    public void getCdmaSubscriptionSourceResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in getCdmaSubscriptionSourceResponse");
    }

    public void getCellInfoListResponse(RadioResponseInfo info, ArrayList<CellInfo> arrayList) {
        Elog.d(TAG, "No implementation in getCellInfoListResponse");
    }

    public void getClipResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in getClipResponse");
    }

    public void getClirResponse(RadioResponseInfo info, int arg1, int arg2) {
        Elog.d(TAG, "No implementation in getClirResponse");
    }

    public void getCurrentCallsResponse(RadioResponseInfo info, ArrayList<Call> arrayList) {
        Elog.d(TAG, "No implementation in getCurrentCallsResponse");
    }

    public void getDataCallListResponse(RadioResponseInfo info, ArrayList<SetupDataCallResult> arrayList) {
        Elog.d(TAG, "No implementation in getDataCallListResponse");
    }

    public void getDataRegistrationStateResponse(RadioResponseInfo info, DataRegStateResult arg1) {
        Elog.d(TAG, "No implementation in getDataRegistrationStateResponse");
    }

    public void getDeviceIdentityResponse(RadioResponseInfo info, String arg1, String arg2, String arg3, String arg4) {
        Elog.d(TAG, "No implementation in getDeviceIdentityResponse");
    }

    public void getFacilityLockForAppResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in getFacilityLockForAppResponse");
    }

    public void getGsmBroadcastConfigResponse(RadioResponseInfo info, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) {
        Elog.d(TAG, "No implementation in getGsmBroadcastConfigResponse");
    }

    public void getHardwareConfigResponse(RadioResponseInfo info, ArrayList<HardwareConfig> arrayList) {
        Elog.d(TAG, "No implementation in getHardwareConfigResponse");
    }

    public void getIMSIForAppResponse(RadioResponseInfo info, String arg1) {
        Elog.d(TAG, "No implementation in getIMSIForAppResponse");
    }

    public void getIccCardStatusResponse(RadioResponseInfo info, CardStatus arg1) {
        Elog.d(TAG, "No implementation in getIccCardStatusResponse");
    }

    public void getImsRegistrationStateResponse(RadioResponseInfo info, boolean arg1, int arg2) {
        Elog.d(TAG, "No implementation in getImsRegistrationStateResponse");
    }

    public void getLastCallFailCauseResponse(RadioResponseInfo info, LastCallFailCauseInfo arg1) {
        Elog.d(TAG, "No implementation in getLastCallFailCauseResponse");
    }

    public void getModemActivityInfoResponse(RadioResponseInfo info, ActivityStatsInfo arg1) {
        Elog.d(TAG, "No implementation in getModemActivityInfoResponse");
    }

    public void getMuteResponse(RadioResponseInfo info, boolean arg1) {
        Elog.d(TAG, "No implementation in getMuteResponse");
    }

    public void getNeighboringCidsResponse(RadioResponseInfo info, ArrayList<NeighboringCell> arrayList) {
        Elog.d(TAG, "No implementation in getNeighboringCidsResponse");
    }

    public void getNetworkSelectionModeResponse(RadioResponseInfo info, boolean arg1) {
        Elog.d(TAG, "No implementation in getNetworkSelectionModeResponse");
    }

    public void getOperatorResponse(RadioResponseInfo info, String arg1, String arg2, String arg3) {
        Elog.d(TAG, "No implementation in getOperatorResponse");
    }

    public void getPreferredNetworkTypeResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in getPreferredNetworkTypeResponse");
    }

    public void getPreferredVoicePrivacyResponse(RadioResponseInfo info, boolean arg1) {
        Elog.d(TAG, "No implementation in getPreferredVoicePrivacyResponse");
    }

    public void getRadioCapabilityResponse(RadioResponseInfo info, RadioCapability arg1) {
        Elog.d(TAG, "No implementation in getRadioCapabilityResponse");
    }

    public void getSignalStrengthResponse(RadioResponseInfo info, SignalStrength arg1) {
        Elog.d(TAG, "No implementation in getSignalStrengthResponse");
    }

    public void getSmscAddressResponse(RadioResponseInfo info, String arg1) {
        Elog.d(TAG, "No implementation in getSmscAddressResponse");
    }

    public void getTTYModeResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in getTTYModeResponse");
    }

    public void getVoiceRadioTechnologyResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in getVoiceRadioTechnologyResponse");
    }

    public void getVoiceRegistrationStateResponse(RadioResponseInfo info, VoiceRegStateResult arg1) {
        Elog.d(TAG, "No implementation in getVoiceRegistrationStateResponse");
    }

    public void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in handleStkCallSetupRequestFromSimResponse");
    }

    public void hangupConnectionResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in hangupConnectionResponse");
    }

    public void hangupForegroundResumeBackgroundResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in hangupForegroundResumeBackgroundResponse");
    }

    public void hangupWaitingOrBackgroundResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in hangupWaitingOrBackgroundResponse");
    }

    public void iccCloseLogicalChannelResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in iccCloseLogicalChannelResponse");
    }

    public void iccIOForAppResponse(RadioResponseInfo info, IccIoResult arg1) {
        Elog.d(TAG, "No implementation in iccIOForAppResponse");
    }

    public void iccOpenLogicalChannelResponse(RadioResponseInfo info, int arg1, ArrayList<Byte> arrayList) {
        Elog.d(TAG, "No implementation in iccOpenLogicalChannelResponse");
    }

    public void iccTransmitApduBasicChannelResponse(RadioResponseInfo info, IccIoResult arg1) {
        Elog.d(TAG, "No implementation in iccTransmitApduBasicChannelResponse");
    }

    public void iccTransmitApduLogicalChannelResponse(RadioResponseInfo info, IccIoResult arg1) {
        Elog.d(TAG, "No implementation in iccTransmitApduLogicalChannelResponse");
    }

    public void nvReadItemResponse(RadioResponseInfo info, String arg1) {
        Elog.d(TAG, "No implementation in nvReadItemResponse");
    }

    public void nvResetConfigResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in nvResetConfigResponse");
    }

    public void nvWriteCdmaPrlResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in nvWriteCdmaPrlResponse");
    }

    public void nvWriteItemResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in nvWriteItemResponse");
    }

    public void pullLceDataResponse(RadioResponseInfo info, LceDataInfo arg1) {
        Elog.d(TAG, "No implementation in pullLceDataResponse");
    }

    public void rejectCallResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in rejectCallResponse");
    }

    public void reportSmsMemoryStatusResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in reportSmsMemoryStatusResponse");
    }

    public void reportStkServiceIsRunningResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in reportStkServiceIsRunningResponse");
    }

    public void requestIccSimAuthenticationResponse(RadioResponseInfo info, IccIoResult arg1) {
        Elog.d(TAG, "No implementation in requestIccSimAuthenticationResponse");
    }

    public void requestIsimAuthenticationResponse(RadioResponseInfo info, String arg1) {
        Elog.d(TAG, "No implementation in requestIsimAuthenticationResponse");
    }

    public void requestShutdownResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in requestShutdownResponse");
    }

    public void sendBurstDtmfResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in sendBurstDtmfResponse");
    }

    public void sendCDMAFeatureCodeResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in sendCDMAFeatureCodeResponse");
    }

    public void sendCdmaSmsResponse(RadioResponseInfo info, SendSmsResult arg1) {
        Elog.d(TAG, "No implementation in sendCdmaSmsResponse");
    }

    public void sendDeviceStateResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in sendDeviceStateResponse");
    }

    public void sendDtmfResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in sendDtmfResponse");
    }

    public void sendEnvelopeResponse(RadioResponseInfo info, String arg1) {
        Elog.d(TAG, "No implementation in sendEnvelopeResponse");
    }

    public void sendEnvelopeWithStatusResponse(RadioResponseInfo info, IccIoResult arg1) {
        Elog.d(TAG, "No implementation in sendEnvelopeWithStatusResponse");
    }

    public void sendImsSmsResponse(RadioResponseInfo info, SendSmsResult arg1) {
        Elog.d(TAG, "No implementation in sendImsSmsResponse");
    }

    public void sendSMSExpectMoreResponse(RadioResponseInfo info, SendSmsResult arg1) {
        Elog.d(TAG, "No implementation in sendSMSExpectMoreResponse");
    }

    public void sendSmsResponse(RadioResponseInfo info, SendSmsResult arg1) {
        Elog.d(TAG, "No implementation in sendSmsResponse");
    }

    public void sendTerminalResponseToSimResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in sendTerminalResponseToSimResponse");
    }

    public void sendUssdResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in sendUssdResponse");
    }

    public void separateConnectionResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in separateConnectionResponse");
    }

    public void setAllowedCarriersResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in setAllowedCarriersResponse");
    }

    public void setBandModeResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setBandModeResponse");
    }

    public void setBarringPasswordResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setBarringPasswordResponse");
    }

    public void setCallForwardResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setCallForwardResponse");
    }

    public void setCallWaitingResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setCallWaitingResponse");
    }

    public void setCdmaBroadcastActivationResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setCdmaBroadcastActivationResponse");
    }

    public void setCdmaBroadcastConfigResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setCdmaBroadcastConfigResponse");
    }

    public void setCdmaRoamingPreferenceResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setCdmaRoamingPreferenceResponse");
    }

    public void setCdmaSubscriptionSourceResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setCdmaSubscriptionSourceResponse");
    }

    public void setCellInfoListRateResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setCellInfoListRateResponse");
    }

    public void setClirResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setClirResponse");
    }

    public void setDataAllowedResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setDataAllowedResponse");
    }

    public void setDataProfileResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setDataProfileResponse");
    }

    public void setFacilityLockForAppResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in setFacilityLockForAppResponse");
    }

    public void setGsmBroadcastActivationResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setGsmBroadcastActivationResponse");
    }

    public void setGsmBroadcastConfigResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setGsmBroadcastConfigResponse");
    }

    public void setIndicationFilterResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setIndicationFilterResponse");
    }

    public void setInitialAttachApnResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setInitialAttachApnResponse");
    }

    public void setLocationUpdatesResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setLocationUpdatesResponse");
    }

    public void setMuteResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setMuteResponse");
    }

    public void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setNetworkSelectionModeAutomaticResponse");
    }

    public void setNetworkSelectionModeManualResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setNetworkSelectionModeManualResponse");
    }

    public void setPreferredNetworkTypeResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setPreferredNetworkTypeResponse");
    }

    public void setPreferredVoicePrivacyResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setPreferredVoicePrivacyResponse");
    }

    public void setRadioCapabilityResponse(RadioResponseInfo info, RadioCapability arg1) {
        Elog.d(TAG, "No implementation in setRadioCapabilityResponse");
    }

    public void setRadioPowerResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setRadioPowerResponse");
    }

    public void setSimCardPowerResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setSimCardPowerResponse");
    }

    public void setSmscAddressResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setSmscAddressResponse");
    }

    public void setSuppServiceNotificationsResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setSuppServiceNotificationsResponse");
    }

    public void setTTYModeResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setTTYModeResponse");
    }

    public void setUiccSubscriptionResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setUiccSubscriptionResponse");
    }

    public void setupDataCallResponse(RadioResponseInfo info, SetupDataCallResult arg1) {
        Elog.d(TAG, "No implementation in setupDataCallResponse");
    }

    public void startDtmfResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in startDtmfResponse");
    }

    public void startLceServiceResponse(RadioResponseInfo info, LceStatusInfo arg1) {
        Elog.d(TAG, "No implementation in startLceServiceResponse");
    }

    public void stopDtmfResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in stopDtmfResponse");
    }

    public void stopLceServiceResponse(RadioResponseInfo info, LceStatusInfo arg1) {
        Elog.d(TAG, "No implementation in stopLceServiceResponse");
    }

    public void supplyIccPin2ForAppResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in supplyIccPin2ForAppResponse");
    }

    public void supplyIccPinForAppResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in supplyIccPinForAppResponse");
    }

    public void supplyIccPuk2ForAppResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in supplyIccPuk2ForAppResponse");
    }

    public void supplyIccPukForAppResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in supplyIccPukForAppResponse");
    }

    public void supplyNetworkDepersonalizationResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in supplyNetworkDepersonalizationResponse");
    }

    public void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in switchWaitingOrHoldingAndActiveResponse");
    }

    public void writeSmsToRuimResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in writeSmsToRuimResponse");
    }

    public void writeSmsToSimResponse(RadioResponseInfo info, int arg1) {
        Elog.d(TAG, "No implementation in writeSmsToSimResponse");
    }

    public void startNetworkScanResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in startNetworkScanResponse");
    }

    public void stopKeepaliveResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in stopKeepaliveResponse");
    }

    public void setCarrierInfoForImsiEncryptionResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setCarrierInfoForImsiEncryptionResponse");
    }

    public void stopNetworkScanResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in stopNetworkScanResponse");
    }

    public void setSimCardPowerResponse_1_1(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setSimCardPowerResponse_1_1");
    }

    public void startKeepaliveResponse(RadioResponseInfo info, KeepaliveStatus status) {
        Elog.d(TAG, "No implementation in startKeepaliveResponse");
    }

    public void getSignalStrengthResponse_1_2(RadioResponseInfo info, android.hardware.radio.V1_2.SignalStrength signalStrength) {
        Elog.d(TAG, "No implementation in getSignalStrengthResponse_1_2");
    }

    public void getCurrentCallsResponse_1_2(RadioResponseInfo info, ArrayList<android.hardware.radio.V1_2.Call> arrayList) {
        Elog.d(TAG, "No implementation in getCurrentCallsResponse_1_2");
    }

    public void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setLinkCapacityReportingCriteriaResponse");
    }

    public void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo info) {
        Elog.d(TAG, "No implementation in setSignalStrengthReportingCriteriaResponse");
    }

    public void getIccCardStatusResponse_1_2(RadioResponseInfo info, android.hardware.radio.V1_2.CardStatus cardStatus) {
        Elog.d(TAG, "No implementation in getIccCardStatusResponse_1_2");
    }

    public void getCellInfoListResponse_1_2(RadioResponseInfo info, ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList) {
        Elog.d(TAG, "No implementation in getCellInfoListResponse_1_2");
    }

    public void getDataRegistrationStateResponse_1_2(RadioResponseInfo info, android.hardware.radio.V1_2.DataRegStateResult dataRegResponse) {
        Elog.d(TAG, "No implementation in getDataRegistrationStateResponse_1_2");
    }

    public void getVoiceRegistrationStateResponse_1_2(RadioResponseInfo info, android.hardware.radio.V1_2.VoiceRegStateResult voiceRegStateResult) {
        Elog.d(TAG, "No implementation in getVoiceRegistrationStateResponse_1_2");
    }

    public void setSystemSelectionChannelsResponse(RadioResponseInfo responseInfo) {
        Elog.d(TAG, "No implementation in setSystemSelectionChannelsResponse");
    }
}
