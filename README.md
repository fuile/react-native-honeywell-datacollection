# React Native Honeywell Barcode Reader for data collection

This package works with Honeywell devices that have an integrated barcode scanner, like the Honeywell EDA50K (tested).

## Installation

```
npm i react-native-honeywell-datacollection -S
```

## Usage

First you'll want to check whether the device is a Honeywell scanner:

```js
import HoneywellBarcodeReader from 'react-native-honeywell-datacollection';

HoneywellBarcodeReader.isCompatible // true or false
```

The barcode reader needs to be "claimed" by your application; meanwhile no other application can use it. You can do that like this:

```js
HoneywellBarcodeReader.register().then((claimed) => {
    console.log(claimed ? 'Barcode reader is claimed' : 'Barcode reader is busy');
});
```

Enable automation the barcode scanner:

```js
HoneywellBarcodeReader.automatic()
```

To get events from the barcode scanner:

```js
HoneywellBarcodeReader.onBarcodeReadSuccess(event => {
    console.log('Received data', event);
});

HoneywellBarcodeReader.onBarcodeReadFail(() => {
    console.log('Barcode read failed');
});
```

To free the claim and stop the reader, also freeing up resources:

```js
HoneywellBarcodeReader.unRegister().then(() => {
    console.log('Freedom!');
});
```

To stop receiving events:

```js
HoneywellBarcodeReader.offBarcodeReadSuccess();
HoneywellBarcodeReader.offBarcodeReadFail();
```

To get events from the barcode software trigger:

```js
HoneywellBarcodeReader.onTriggerStateChange(state => {
    console.log('onTriggerStateChange', state);
});

```

To stop receiving events:

```js
HoneywellBarcodeReader.offTriggerStateChange();
```

Get barcode scanner info:

```js
HoneywellBarcodeReader.barcodeReaderInfo(details => {
    console.log('barcodeReaderClaimed', details);
});
```

