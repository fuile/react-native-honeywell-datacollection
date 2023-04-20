import { NativeModules, DeviceEventEmitter, EmitterSubscription } from 'react-native';

export type ScannerInfo = {
  name: string;
  friendlyName: string;
  scanEngineId: string;
  fullDecodeVersion: string;
  fastDecodeVersion: string;
  controlLogicVersion: string;
  scanEngineVersionNumber: string;
  frameWidth: number;
  frameHeight: number;
}
export type BarcodeReadSuccessEvent = {
  (event: BarcodeReadSuccessResult): void
};
export type BarcodeReadSuccessResult = {
  data: string;
  charset: string;
  codeid: string;
  aimid: string;
  timestamp: string;
  symbology: string;
}
export type HoneywellBarcodeReader = {
  BARCODE_READ_SUCCESS: string;
  BARCODE_READ_FAIL: string;
  BARCODE_TRIGGER_STATE: string;
  isCompatible: boolean;
  barcodeReaderClaimed(handler: (state: boolean) => void): void;
  barcodeReaderInfo(handler: (details: ScannerInfo) => void): void;
  register(): Promise<boolean>;
  automatic(): Promise<boolean>;
  unRegister(): Promise<string | null>;
  onBarcodeReadSuccess(handler: BarcodeReadSuccessEvent): void;
  offBarcodeReadSuccess(): void;
  onBarcodeReadFail(handler: () => void): void;
  offBarcodeReadFail(): void;
  onTriggerStateChange(handler: (state: boolean) => void): void;
  offTriggerStateChange(): void;
}

let subscriptionBarcodeReadSuccess: EmitterSubscription | null = null
let subscriptionBarcodeReadFail: EmitterSubscription | null = null
let subscriptionTriggerState: EmitterSubscription | null = null
const barcodeReader: HoneywellBarcodeReader = NativeModules.HoneywellBarcodeReader || {}

barcodeReader.onBarcodeReadSuccess = (handler) => {
  subscriptionBarcodeReadSuccess?.remove()
  subscriptionBarcodeReadSuccess = DeviceEventEmitter.addListener(barcodeReader.BARCODE_READ_SUCCESS, handler)
}

barcodeReader.offBarcodeReadSuccess = () => {
  subscriptionBarcodeReadSuccess?.remove()
}

barcodeReader.onBarcodeReadFail = (handler) => {
  subscriptionBarcodeReadFail?.remove()
  subscriptionBarcodeReadFail = DeviceEventEmitter.addListener(barcodeReader.BARCODE_READ_FAIL, handler)
}

barcodeReader.offBarcodeReadFail = () => {
  subscriptionBarcodeReadFail?.remove()
}

barcodeReader.onTriggerStateChange = (handler) => {
  subscriptionTriggerState?.remove()
  subscriptionTriggerState = DeviceEventEmitter.addListener(barcodeReader.BARCODE_TRIGGER_STATE, handler)
}

barcodeReader.offTriggerStateChange = () => {
  subscriptionTriggerState?.remove()
}

export default barcodeReader;
