package cn.databytes.datacollection.honeywell.event;

import com.facebook.react.bridge.ReactApplicationContext;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

public class BarcodeTriggerListener implements BarcodeReader.TriggerListener {
    private final ReactApplicationContext context;
 
    public BarcodeTriggerListener(ReactApplicationContext reactContext) {
        context = reactContext;
    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        Subscribe.sendObjectEvent(context, Subscribe.BARCODE_READ_FAIL, triggerStateChangeEvent.getState());
    }
}
