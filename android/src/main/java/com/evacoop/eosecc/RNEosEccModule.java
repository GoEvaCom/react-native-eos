
package com.evacoop.eosecc;
 
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import dagger.Module;
import io.plactal.eoscommander.app.EosCommanderApp;


@Module
public class RNEosEccModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  EosCommanderApp app;

  public RNEosEccModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    app = new EosCommanderApp(reactContext);
  }

  @Override
  public String getName() {
    return "RNEosEcc";
  }

  @ReactMethod
  public void setUrl(String scheme, String url, int port) {
    app.setUrl(scheme, url, port);
  }

  @ReactMethod
  public void getInfo(final Promise promise){
    app.getInfo();
  }

  @ReactMethod
  public void pushAction(String contract, String action, String message, String permissionAccount, String permissionType, String privateKey, final Promise promise) {
    try {
      app.pushAction(contract, action, message, permissionAccount, permissionType, privateKey, promise);
    } catch (Exception ex) {
      promise.reject("ERR_UNEXPECTED_EXCEPTION", ex);
    }
  }
}