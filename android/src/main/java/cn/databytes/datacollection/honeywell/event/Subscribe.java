package cn.databytes.datacollection.honeywell.event;

import android.os.Build;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nullable;

public final class Subscribe {
    public static final String BARCODE_READ_SUCCESS = "barcodeReadSuccess";
    public static final String BARCODE_READ_FAIL = "barcodeReadFail";
    public static final String BARCODE_TRIGGER_STATE = "barcodeTriggerState";
    public static final boolean IS_COMPATIBLE = Build.BRAND.toLowerCase().contains("honeywell");

    public static void sendEvent(ReactApplicationContext context, String eventName, @Nullable WritableMap params) {
        sendObjectEvent(context, eventName, params);
    }

    public static void sendObjectEvent(ReactApplicationContext context, String eventName, @Nullable Object params) {
        if (context.hasActiveReactInstance()) {
            context
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
    }
}
