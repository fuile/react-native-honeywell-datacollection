package cn.databytes.datacollection.honeywell.event;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;

import cn.databytes.datacollection.honeywell.data.Symbology;

public class BarcodeReaderListener implements BarcodeReader.BarcodeListener {
    private final ReactApplicationContext context;

    public BarcodeReaderListener(ReactApplicationContext reactContext) {
        context = reactContext;
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        WritableMap params = Arguments.createMap();
        params.putString("data", barcodeReadEvent.getBarcodeData());
        params.putString("charset", barcodeReadEvent.getCharset().toString());
        params.putString("codeid", barcodeReadEvent.getCodeId());
        params.putString("aimid", barcodeReadEvent.getAimId());
        params.putString("timestamp", barcodeReadEvent.getTimestamp());
        params.putString("symbology", Symbology.getSymbol(barcodeReadEvent.getCodeId()));
        Subscribe.sendEvent(context, Subscribe.BARCODE_READ_SUCCESS, params);
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        Subscribe.sendEvent(context, Subscribe.BARCODE_READ_FAIL, null);
    }
}
