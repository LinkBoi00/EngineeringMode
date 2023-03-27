package com.mediatek.engineermode.mdmcomponent;

import android.app.Activity;
import android.hardware.radio.V1_0.SmsAcknowledgeFailCause;
import android.support.v4.media.subtitle.Cea708CCParser;
import com.mediatek.engineermode.Elog;
import com.mediatek.mdml.Msg;
import java.util.HashMap;

/* compiled from: MDMComponent */
class SpeechCodecInfo extends NormalTableComponent {
    private static final String[] EM_TYPES = {MDMContent.MSG_ID_EM_SPEECH_INFO_SPH_CODEC_IND};
    HashMap<Integer, String> mMappingSpeechCodec = new HashMap<Integer, String>() {
        {
            put(0, "SPH_CODEC_FR");
            put(1, "SPH_CODEC_HR");
            put(2, "SPH_CODEC_EFR");
            put(3, "SPH_CODEC_AMR_12_20");
            put(4, "SPH_CODEC_AMR_10_20");
            put(5, "SPH_CODEC_AMR_7_95");
            put(6, "SPH_CODEC_AMR_7_40");
            put(7, "SPH_CODEC_AMR_6_70");
            put(8, "SPH_CODEC_AMR_5_90");
            put(9, "SPH_CODEC_AMR_5_15");
            put(10, "SPH_CODEC_AMR_4_75");
            put(11, "SPH_CODEC_AMR_SID");
            put(12, "SPH_CODEC_AMR_NODATA");
            put(32, "SPH_CODEC_AMRWB_6_60");
            put(33, "SPH_CODEC_AMRWB_8_85");
            put(34, "SPH_CODEC_AMRWB_12_65");
            put(35, "SPH_CODEC_AMRWB_14_25");
            put(36, "SPH_CODEC_AMRWB_15_85");
            put(37, "SPH_CODEC_AMRWB_18_25");
            put(38, "SPH_CODEC_AMRWB_19_85");
            put(39, "SPH_CODEC_AMRWB_23_05");
            put(40, "SPH_CODEC_AMRWB_23_85");
            put(41, "SPH_CODEC_AMRWB_SID");
            put(42, "SPH_CODEC_LOST_FRAME");
            put(48, "SPH_CODEC_C2K_SO1");
            put(49, "SPH_CODEC_C2K_SO3");
            put(50, "SPH_CODEC_C2K_SO17");
            put(51, "SPH_CODEC_C2K_SO68");
            put(52, "SPH_CODEC_C2K_SO73");
            put(53, "SPH_CODEC_C2K_SO73WB");
            put(96, "SPH_CODEC_G711");
            put(97, "SPH_CODEC_G722");
            put(98, "SPH_CODEC_G723_1");
            put(99, "SPH_CODEC_G726");
            put(100, "SPH_CODEC_G729");
            put(128, "SPH_CODEC_EVS_08K_005_9/SPH_CODEC_EVS_08K_002_8");
            put(129, "SPH_CODEC_EVS_08K_007_2");
            put(130, "SPH_CODEC_EVS_08K_008_0");
            put(131, "SPH_CODEC_EVS_08K_009_6");
            put(132, "SPH_CODEC_EVS_08K_013_2");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_CW5), "SPH_CODEC_EVS_08K_016_4");
            put(134, "SPH_CODEC_EVS_08K_024_4");
            put(135, "SPH_CODEC_EVS_08K_032_0");
            put(136, "SPH_CODEC_EVS_08K_048_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DSW), "SPH_CODEC_EVS_08K_064_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_HDW), "SPH_CODEC_EVS_08K_096_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_TGW), "SPH_CODEC_EVS_08K_128_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DLW), "SPH_CODEC_EVS_08K_002_4_SID");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DLY), "SPH_CODEC_EVS_08K_000_0_REV");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DLC), "SPH_CODEC_EVS_08K_000_0_LOST");
            put(143, "SPH_CODEC_EVS_08K_000_0_NODATA");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_SPA), "SPH_CODEC_EVS_16K_005_9/SPH_CODEC_EVS_16K_002_8");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_SPC), "SPH_CODEC_EVS_16K_007_2");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_SPL), "SPH_CODEC_EVS_16K_008_0");
            put(147, "SPH_CODEC_EVS_16K_009_6");
            put(148, "SPH_CODEC_EVS_16K_013_2");
            put(149, "SPH_CODEC_EVS_16K_016_4");
            put(150, "SPH_CODEC_EVS_16K_024_4");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_SWA), "SPH_CODEC_EVS_16K_032_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF0), "SPH_CODEC_EVS_16K_048_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF1), "SPH_CODEC_EVS_16K_064_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF2), "SPH_CODEC_EVS_16K_096_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF3), "SPH_CODEC_EVS_16K_128_0");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF4), "SPH_CODEC_EVS_16K_002_4_SID");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF5), "SPH_CODEC_EVS_16K_000_0_REV");
            put(Integer.valueOf(Cea708CCParser.Const.CODE_C1_DF6), "SPH_CODEC_EVS_16K_000_0_LOST");
            put(159, "SPH_CODEC_EVS_16K_000_0_NODATA");
            put(160, "SPH_CODEC_EVS_32K_005_9/SPH_CODEC_EVS_32K_002_8");
            put(161, "SPH_CODEC_EVS_32K_007_2");
            put(162, "SPH_CODEC_EVS_32K_008_0");
            put(163, "SPH_CODEC_EVS_32K_009_6");
            put(164, "SPH_CODEC_EVS_32K_013_2");
            put(165, "SPH_CODEC_EVS_32K_016_4");
            put(166, "SPH_CODEC_EVS_32K_024_4");
            put(167, "SPH_CODEC_EVS_32K_032_0");
            put(168, "SPH_CODEC_EVS_32K_048_0");
            put(169, "SPH_CODEC_EVS_32K_064_0");
            put(170, "SPH_CODEC_EVS_32K_096_0");
            put(171, "SPH_CODEC_EVS_32K_128_0");
            put(172, "SPH_CODEC_EVS_32K_002_4_SID");
            put(173, "SPH_CODEC_EVS_32K_000_0_REV");
            put(174, "SPH_CODEC_EVS_32K_000_0_LOST");
            put(175, "SPH_CODEC_EVS_32K_000_0_NODATA");
            put(176, "SPH_CODEC_EVS_48K_005_9/SPH_CODEC_EVS_48K_002_8");
            put(177, "SPH_CODEC_EVS_48K_007_2");
            put(178, "SPH_CODEC_EVS_48K_008_0");
            put(179, "SPH_CODEC_EVS_48K_009_6");
            put(180, "SPH_CODEC_EVS_48K_013_2");
            put(181, "SPH_CODEC_EVS_48K_016_4");
            put(182, "SPH_CODEC_EVS_48K_024_4");
            put(183, "SPH_CODEC_EVS_48K_032_0");
            put(184, "SPH_CODEC_EVS_48K_048_0");
            put(185, "SPH_CODEC_EVS_48K_064_0");
            put(186, "SPH_CODEC_EVS_48K_096_0");
            put(187, "SPH_CODEC_EVS_48K_128_0");
            put(188, "SPH_CODEC_EVS_48K_002_4_SID");
            put(189, "SPH_CODEC_EVS_48K_000_0_REV");
            put(190, "SPH_CODEC_EVS_48K_000_0_LOST");
            put(191, "SPH_CODEC_EVS_48K_000_0_NODATA");
            put(208, "SPH_CODEC_EVS_AWB_06_60");
            put(209, "SPH_CODEC_EVS_AWB_08_85");
            put(210, "SPH_CODEC_EVS_AWB_12_65");
            put(Integer.valueOf(SmsAcknowledgeFailCause.MEMORY_CAPACITY_EXCEEDED), "SPH_CODEC_EVS_AWB_14_25");
            put(212, "SPH_CODEC_EVS_AWB_15_85");
            put(213, "SPH_CODEC_EVS_AWB_18_25");
            put(214, "SPH_CODEC_EVS_AWB_19_85");
            put(215, "SPH_CODEC_EVS_AWB_23_05");
            put(216, "SPH_CODEC_EVS_AWB_23_85");
            put(217, "SPH_CODEC_EVS_AWB_02_00_SID");
            put(218, "SPH_CODEC_EVS_AWB_00_00_REV0");
            put(219, "SPH_CODEC_EVS_AWB_00_00_REV1");
            put(220, "SPH_CODEC_EVS_AWB_00_00_REV2");
            put(221, "SPH_CODEC_EVS_AWB_00_00_REV3");
            put(222, "SPH_CODEC_EVS_AWB_00_00_LOST");
            put(223, "SPH_CODEC_EVS_AWB_00_00_NODATA");
            put(255, "SPH_CODEC_CODEC_NONE");
        }
    };
    HashMap<Integer, String> mMappingSpeechNetwork = new HashMap<Integer, String>() {
        {
            put(0, "SPH_NETWORK_IDLE");
            put(1, "SPH_NETWORK_2G_SPEECH_ON");
            put(2, "SPH_NETWORK_3G_SPEECH_ON");
            put(3, "SPH_NETWORK_3G324M_SPEECH_ON");
            put(4, "SPH_NETWORK_3G_SPEECH_CLOSING");
            put(5, "SPH_NETWORK_4G_SPEECH_ON");
            put(6, "SPH_NETWORK_4G_SPEECH_CLOSING");
        }
    };

    public SpeechCodecInfo(Activity context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public String getName() {
        return "Speech Codec Info";
    }

    /* access modifiers changed from: package-private */
    public String getGroup() {
        return "1. General EM Component";
    }

    /* access modifiers changed from: package-private */
    public String[] getEmComponentName() {
        return EM_TYPES;
    }

    /* access modifiers changed from: package-private */
    public String[] getLabels() {
        return new String[]{"UL speech codec", "DL speech codec", "speech network"};
    }

    /* access modifiers changed from: package-private */
    public boolean supportMultiSIM() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void update(String name, Object msg) {
        Msg data = (Msg) msg;
        clearData();
        int ulSphCodec = getFieldValue(data, MDMContent.EM_SPEECH_INFO_SPH_CODEC_ULSPH_CODEC);
        int dlSphCodec = getFieldValue(data, MDMContent.EM_SPEECH_INFO_SPH_CODEC_DLSPH_CODEC);
        int sphNetwork = getFieldValue(data, MDMContent.EM_SPEECH_INFO_SPH_CODEC_SPH_NETWORK);
        Elog.d("EmInfo/MDMComponent", "ulSphCodec = " + ulSphCodec);
        Elog.d("EmInfo/MDMComponent", "dlSphCodec = " + dlSphCodec);
        Elog.d("EmInfo/MDMComponent", "sphNetwork = " + sphNetwork);
        addData(this.mMappingSpeechCodec.get(Integer.valueOf(ulSphCodec)));
        addData(this.mMappingSpeechCodec.get(Integer.valueOf(dlSphCodec)));
        addData(this.mMappingSpeechNetwork.get(Integer.valueOf(sphNetwork)));
    }
}
