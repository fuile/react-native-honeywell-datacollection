package cn.databytes.datacollection.honeywell;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.AidcManager.CreatedCallback;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.BarcodeReaderInfo;
import com.honeywell.aidc.ScannerUnavailableException;

import java.util.HashMap;
import java.util.Map;

import cn.databytes.datacollection.honeywell.event.BarcodeReaderListener;
import cn.databytes.datacollection.honeywell.event.BarcodeTriggerListener;
import cn.databytes.datacollection.honeywell.event.Subscribe;

@SuppressWarnings("unused")
@ReactModule(name = BarcodeReaderModule.NAME)
public class BarcodeReaderModule extends ReactContextBaseJavaModule {
    private AidcManager manager;
    private BarcodeReader reader;
    private BarcodeReaderListener readerListener;
    private BarcodeTriggerListener triggerListener;
    private final ReactApplicationContext context;
    private boolean BARCODE_READER_CLAIMED = false;
    public static final String NAME = "HoneywellBarcodeReader";

    public BarcodeReaderModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
    }

    @ReactMethod
    public void register(final Promise promise) {
        AidcManager.create(context, new CreatedCallback() {
            @Override
            public void onCreated(AidcManager aidcManager) {
                manager = aidcManager;
                reader = manager.createBarcodeReader();
                readerListener = new BarcodeReaderListener(context);
                triggerListener = new BarcodeTriggerListener(context);
                reader.addBarcodeListener(readerListener);
                reader.addTriggerListener(triggerListener);
                try {
                    reader.claim();
                    BARCODE_READER_CLAIMED = true;
                    promise.resolve(true);
                } catch (Exception e) {
                    BARCODE_READER_CLAIMED = false;
                    promise.resolve(false);
                    stopReader(null);
                    e.printStackTrace();
                }
            }
        });
    }

    @ReactMethod
    public void automatic(final Promise promise) {
        if (reader != null) {
            try {
                reader.aim(true);
                reader.light(true);
                reader.decode(true);
                promise.resolve(true);
            } catch (Exception e) {
                promise.resolve(false);
                e.printStackTrace();
            }
        }
    }

    @ReactMethod
    public void unRegister(Promise promise) {
        stopReader(promise);
    }

    @ReactMethod
    public void barcodeReaderInfo(Callback callback) {
        if (reader != null) {
            WritableMap params = Arguments.createMap();
            try {
                BarcodeReaderInfo info = reader.getInfo();
                params.putString("name", info.getName());
                params.putString("friendlyName", info.getFriendlyName());
                params.putString("scanEngineId", info.getScannerId());
                params.putString("fullDecodeVersion", info.getFullDecodeVersion());
                params.putString("fastDecodeVersion", info.getFastDecodeVersion());
                params.putString("controlLogicVersion", info.getControlLogicVersion());
                params.putString("scanEngineVersionNumber", info.getScannerVersionNumber());
                params.putInt("frameWidth", info.getFrameWidth());
                params.putInt("frameHeight", info.getFrameHeight());
                callback.invoke(params);
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    @ReactMethod
    public void barcodeReaderClaimed(Callback callback) {
        callback.invoke(BARCODE_READER_CLAIMED);
    }

    @NonNull
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put("BARCODE_READ_SUCCESS", Subscribe.BARCODE_READ_SUCCESS);
        constants.put("BARCODE_READ_FAIL", Subscribe.BARCODE_READ_FAIL);
        constants.put("BARCODE_TRIGGER_STATE", Subscribe.BARCODE_TRIGGER_STATE);
        constants.put("isCompatible", Subscribe.IS_COMPATIBLE);
        return constants;
    }

    private void stopReader(Promise promise) {
        try {
            if (reader != null) {
                if (readerListener != null) {
                    reader.removeBarcodeListener(readerListener);
                }
                if (triggerListener != null) {
                    reader.removeTriggerListener(triggerListener);
                }
                reader.close();
            }
            if (manager != null) {
                manager.close();
            }
        } catch (Exception e) {
            if (promise != null) promise.resolve(e);
            e.printStackTrace();
        } finally {
            BARCODE_READER_CLAIMED = false;
            if (promise != null) promise.resolve(null);
        }
    }
}
