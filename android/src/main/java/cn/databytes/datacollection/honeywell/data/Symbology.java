package cn.databytes.datacollection.honeywell.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Symbology {
    private static final Map<String, String> codeMapper = new HashMap<>();

    static {
        codeMapper.put(".", "DOTCODE");
        codeMapper.put("1", "CODE1");
        codeMapper.put(";", "MERGED_COUPON");
        codeMapper.put("<", "CODE39_BASE32, CODE32, ITALIAN PHARMACODE, PARAF, LABELCODE_V");
        codeMapper.put(">", "LABELCODE_IV");
        codeMapper.put("=", "TRIOPTIC");
        codeMapper.put("?", "KOREA_POST");
        codeMapper.put(",", "INFOMAIL");
        codeMapper.put("`", "EAN13_ISBN");
        codeMapper.put("[", "SWEEDISH_POST");
        codeMapper.put("|", "RM_MAILMARK");
        codeMapper.put("]", "BRAZIL_POST");
        codeMapper.put("A", "AUS_POST");
        codeMapper.put("B", "BRITISH_POST");
        codeMapper.put("C", "CANADIAN_POST");
        codeMapper.put("D", "EAN8");
        codeMapper.put("E", "UPCE");
        codeMapper.put("G", "BC412");
        codeMapper.put("H", "HAN_XIN_CODE");
        codeMapper.put("I", "GS1_128");
        codeMapper.put("J", "JAPAN_POST");
        codeMapper.put("K", "KIX_CODE");
        codeMapper.put("L", "PLANET_CODE");
        codeMapper.put("M", "USPS_4_STATE, INTELLIGENT_MAIL");
        codeMapper.put("N", "UPU_4_STATE, ID_TAGS");
        codeMapper.put("O", "OCR");
        codeMapper.put("P", "POSTNET");
        codeMapper.put("Q", "HK25, CHINA_POST");
        codeMapper.put("R", "MICROPDF");
        codeMapper.put("S", "SECURE_CODE");
        codeMapper.put("T", "TLC39");
        codeMapper.put("U", "ULTRACODE");
        codeMapper.put("V", "CODABLOCK_A");
        codeMapper.put("W", "POSICODE");
        codeMapper.put("X", "GRID_MATRIX");
        codeMapper.put("Y", "NEC25");
        codeMapper.put("Z", "MESA");
        codeMapper.put("a", "CODABAR");
        codeMapper.put("b", "CODE39");
        codeMapper.put("c", "UPCA");
        codeMapper.put("d", "EAN13");
        codeMapper.put("e", "I25");
        codeMapper.put("f", "S25 (2BAR and 3BAR)");
        codeMapper.put("g", "MSI");
        codeMapper.put("h", "CODE11");
        codeMapper.put("i", "CODE93");
        codeMapper.put("j", "CODE128");
        codeMapper.put("k", "UNUSED");
        codeMapper.put("l", "CODE49");
        codeMapper.put("m", "M25");
        codeMapper.put("n", "PLESSEY");
        codeMapper.put("o", "CODE16K");
        codeMapper.put("p", "CHANNELCODE");
        codeMapper.put("q", "CODABLOCK_F");
        codeMapper.put("r", "PDF417");
        codeMapper.put("s", "QRCODE");
        codeMapper.put("t", "TELEPEN");
        codeMapper.put("u", "CODEZ");
        codeMapper.put("v", "VERICODE");
        codeMapper.put("w", "DATAMATRIX");
        codeMapper.put("x", "MAXICODE");
        codeMapper.put("y", "RSS, GS1_DATABAR, COMPOSITE");
        codeMapper.put("z", "AZTEC_CODE");
        codeMapper.put("-", "MICROQR_ALT");
        codeMapper.put("{", "GS1_DATABAR_LIM");
        codeMapper.put("}", "GS1_DATABAR_EXP");
    }

    public static String getSymbol(String codeId) {
        if (codeMapper.containsKey(codeId)) {
            return codeMapper.get(codeId);
        }
        return "UNDEFINED";
    }
}
