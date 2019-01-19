
package com.evacoop.eosecc;
 
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;

import java.util.ArrayList;


public class RNEosEccModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNEosEccModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }


  @Override
  public String getName() {
    return "RNEosEcc";
  }

  @ReactMethod
  public void pushAction(String data, String actionName, String contract, String permission, String refBlockSha256, String expiration, String chainId, String privKey, final Promise promise) {
    try {
      promise.resolve("salut");
    } catch (Exception ex) {
      promise.reject("ERR_UNEXPECTED_EXCEPTION", ex);
    }
  }


}