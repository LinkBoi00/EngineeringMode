package com.mediatek.engineermode.mdmcomponent;

public class MDMContentICD {
    public static final MDMHeaderICDStruct ICD_EVENT_V1 = new MDMHeaderICDStruct(new int[]{0, 4}, new int[]{4, 4}, new int[]{8, 16}, new int[]{24, 4}, new int[]{28, 4}, new int[]{0, 0}, new int[]{32, 16}, new int[]{48, 16}, new int[]{64});
    public static final MDMHeaderICDStruct ICD_EVENT_V2 = new MDMHeaderICDStruct(new int[]{0, 4}, new int[]{4, 4}, new int[]{8, 24}, new int[]{32, 4}, new int[]{36, 4}, new int[]{0, 0}, new int[]{48, 16}, new int[]{40, 8}, new int[]{64});
    public static final MDMHeaderICDStruct ICD_RECORD_V1 = new MDMHeaderICDStruct(new int[]{0, 4}, new int[]{4, 4}, new int[]{8, 24}, new int[]{32, 4}, new int[]{36, 4}, new int[]{40, 8}, new int[]{48, 16}, new int[]{64, 32}, new int[]{96});
    public static final MDMHeaderICDStruct ICD_RECORD_V2 = new MDMHeaderICDStruct(new int[]{0, 4}, new int[]{4, 4}, new int[]{8, 24}, new int[]{32, 4}, new int[]{36, 4}, new int[]{40, 8}, new int[]{48, 16}, new int[]{64, 32}, new int[]{96});
    public static final String MSG_ID_EL1_DCI_INFORMATION = "700A";
    public static final String MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT0 = "7007";
    public static final String MSG_ID_EL1_MIMO_PDSCH_THROUGHPUT1 = "7008";
    public static final String MSG_ID_EL1_NEIGHBOR_CELL_MEASUREMENT = "7002";
    public static final String MSG_ID_EL1_PDSCH_DECODING_RESULTS = "700D";
    public static final String MSG_ID_EL1_PUCCH_CSF = "7013";
    public static final String MSG_ID_EL1_PUSCH_REPORT = "7014";
    public static final String MSG_ID_EL1_SERVING_CELL_MEASUREMENT = "7001";
    public static final String MSG_ID_EL1_SRS_TX_INFORMATION = "7009";
    public static final String MSG_ID_EL1_TAS_INFORMATION = "7017";
    public static final String MSG_ID_EL1_UL_THROUGHPUT = "7003";
    public static final String MSG_ID_EL2_MAC_THROUGHPUT_DL = "7080";
    public static final String MSG_ID_EL2_MAC_THROUGHPUT_UL = "7081";
    public static final String MSG_ID_EL2_PDCP_THROUGHPUT_DL = "708B";
    public static final String MSG_ID_EL2_PDCP_THROUGHPUT_UL = "708C";
    public static final String MSG_ID_EL2_RLC_THROUGHPUT_DL = "7086";
    public static final String MSG_ID_EL2_RLC_THROUGHPUT_UL = "7087";
    public static final String MSG_ID_ENAS_EMM_CONTEXT_INFO = "7690";
    public static final String MSG_ID_ERRC_MEAS_REPORT_INFO = "7101";
    public static final String MSG_ID_ERRC_NSA_RECONFIGURATION = "7611";
    public static final String MSG_ID_ERRC_SERVING_CELL_INFO = "7100";
    public static final String MSG_ID_FAILURE_EVENT_CAUSE = "9684";
    public static final String MSG_ID_NL1_CSI_REPORT = "9015";
    public static final String MSG_ID_NL1_DCI_INFORMATION = "9009";
    public static final String MSG_ID_NL1_MIMO_PDSCH_THROUGHPUT = "9007";
    public static final String MSG_ID_NL1_MULTISIM_INFORMATION = "9000";
    public static final String MSG_ID_NL1_NEIGHBOR_CELL_MEASUREMENT = "9002";
    public static final String MSG_ID_NL1_PHYSICAL_CONFIGURATION = "9014";
    public static final String MSG_ID_NL1_PUCCH_POWER_CONTROL = "900E";
    public static final String MSG_ID_NL1_PUCCH_REPORT = "900D";
    public static final String MSG_ID_NL1_PUSCH_POWER_CONTROL = "9011";
    public static final String MSG_ID_NL1_RACH_INFORMATION = "9006";
    public static final String MSG_ID_NL1_SERVING_CELL_CSIRS_MEASUREMENT = "9016";
    public static final String MSG_ID_NL1_SERVING_CELL_MEASUREMENT = "9001";
    public static final String MSG_ID_NL1_SERVING_CELL_RSSI = "9021";
    public static final String MSG_ID_NL1_SRS_TX_INFORMATION = "9008";
    public static final String MSG_ID_NL1_SYNC_SERVING_BEAM_MEASUREMENT = "9033";
    public static final String MSG_ID_NL1_SYNC_SSB_SNR = "9022";
    public static final String MSG_ID_NL1_TAS_INFORMATION = "9013";
    public static final String MSG_ID_NL1_TPC_REPORT = "9025";
    public static final String MSG_ID_NL1_UL_THROUGHPUT = "9003";
    public static final String MSG_ID_NL2_MAC_CARRIER_AGGREGATION_EVENT = "9586";
    public static final String MSG_ID_NL2_MAC_DRX_STATUS_EVENT = "9588";
    public static final String MSG_ID_NL2_MAC_THROUGHPUT_DL = "9080";
    public static final String MSG_ID_NL2_MAC_THROUGHPUT_UL = "9081";
    public static final String MSG_ID_NL2_MAC_TIMING_ADVANCE_EVENT = "9584";
    public static final String MSG_ID_NL2_PDCP_CONFIGURATION_EVENT = "95D4";
    public static final String MSG_ID_NL2_PDCP_THROUGHPUT_DL = "90D4";
    public static final String MSG_ID_NL2_PDCP_THROUGHPUT_UL = "90D5";
    public static final String MSG_ID_NL2_RLC_THROUGHPUT_DL = "90AA";
    public static final String MSG_ID_NL2_RLC_THROUGHPUT_UL = "90AB";
    public static final String MSG_ID_NL2_SDAP_THROUGHPUT_DL = "90ED";
    public static final String MSG_ID_NL2_SDAP_THROUGHPUT_UL = "90EE";
    public static final String MSG_ID_NRRC_CAPABILITY_EVENT = "9611";
    public static final String MSG_ID_NRRC_DYNAMIC_SPECTRUM_SHARING_CONFIGURATION_EVENT = "9613";
    public static final String MSG_ID_NRRC_PROCEDURE_DURATION_EVENT = "961B";
    public static final String MSG_ID_NRRC_RLF_EVENT = "9600";
    public static final String MSG_ID_NRRC_SCG_FAILURE_EVENT = "960F";
    public static final String MSG_ID_NRRC_SCG_RECONFIGURATION_EVENT = "9610";
    public static final String MSG_ID_NRRC_SERVING_CELL_EVENT = "9601";
    public static final String MSG_ID_NRRC_SIB_READ_INFO = "9103";
    public static final String MSG_ID_NRRC_STATE_CHANGE_EVENT = "9602";
    public static final String MSG_ID_VGNAS_MM_FAILURE_EVENT_CAUSE = "9689";
    public static final String MSG_ID_VGNAS_MM_STATE_VALUE = "9685";
    public static final String MSG_ID_VGNAS_SM_CONTEXT_INFO = "9680";
    public static final String MSG_NAME_ERRC_MEAS_REPORT_INFO = "ERRC_MEAS_Report_Info";
    public static final String MSG_NAME_ERRC_SERVING_CELL_INFO = "ERRC_Serving_Cell_Info";
    public static final String MSG_TYPE_ICD_EVENT = "type_icd_event";
    public static final String MSG_TYPE_ICD_RECORD = "type_icd_record";
    public static final String MSG_VALUE_ICD_MCC = "MCC";
    public static final String MSG_VALUE_ICD_MNC = "MNC";
    public static final String MSG_VALUE_PHYSICAL_CELL_ID = "Physical Cell ID";
    public static final String MSG_VALUE_SERV_EARFCN = "Serv Earfcn";

    public static class MDMHeaderICD {
        int check_sum;
        int msg_id;
        int protocol_id;
        int reserved;
        int timeStamp;
        int timestamp_type;
        int total_size;
        int type;
        int version;
    }

    public static class MDMHeaderICDStruct {
        int[] check_sum;
        int[] msg_id;
        int[] protocol_id;
        int[] reserved;
        int[] timeStamp;
        int[] timestamp_type;
        int[] total_size;
        int[] type;
        int[] version;

        public MDMHeaderICDStruct(int[] is, int[] is2, int[] is3, int[] is4, int[] is5, int[] is6, int[] is7, int[] is8, int[] is9) {
            this.type = is;
            this.version = is2;
            this.total_size = is3;
            this.timestamp_type = is4;
            this.protocol_id = is5;
            this.reserved = is6;
            this.msg_id = is7;
            this.check_sum = is8;
            this.timeStamp = is9;
        }
    }
}
