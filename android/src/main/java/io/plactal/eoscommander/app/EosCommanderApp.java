package io.plactal.eoscommander.app;

import android.app.Application;
import android.content.Context;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.plactal.eoscommander.data.EoscDataManager;
import io.plactal.eoscommander.data.remote.model.api.EosChainInfo;
import io.plactal.eoscommander.data.remote.model.api.PushTxnResponse;
import io.plactal.eoscommander.di.ApplicationContext;
import io.plactal.eoscommander.di.component.AppComponent;
import io.plactal.eoscommander.di.component.DaggerAppComponent;
import io.plactal.eoscommander.di.module.AppModule;
import io.plactal.eoscommander.util.StringUtils;
import io.plactal.eoscommander.util.rx.EoscSchedulerProvider;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by swapnibble on 2017-11-03.
 */

public class EosCommanderApp extends Application {
    private final Context reactContext;
    private AppComponent mAppComponent;
    EoscSchedulerProvider scheduler;

    @Inject
    EoscDataManager mDataManager;


    public EosCommanderApp(ReactApplicationContext reactContext) {
        super.onCreate();
        this.reactContext = (Context) reactContext;

        // https://android-developers.googleblog.com/2013/08/some-securerandom-thoughts.html
        PRNGFixes.apply();
        mAppComponent = DaggerAppComponent.builder()
                .appModule( new AppModule(this, reactContext))
                .build();

        mAppComponent.inject( this );
        scheduler = new EoscSchedulerProvider();
    }

    public void setUrl(String scheme, String url, int port){
        mDataManager.getPreferenceHelper().putNodeosConnInfo(scheme, url, port);
    }

    public void getInfo(){
        mDataManager
                .getChainInfo()
                .subscribeOn(scheduler.computation())
                .subscribe( new Consumer<EosChainInfo>() {
                    @Override
                    public void accept(EosChainInfo info){
                        System.out.println(info.getChain_id());
                    }
                });
    }

    public static EosCommanderApp get( Context context ){
        return (EosCommanderApp) context.getApplicationContext();
    }

    public AppComponent getAppComponent() { return mAppComponent; }

    public void pushAction(String contract, String action, String message, String permissionAccount, String permissionType, String privateKey, final Promise promise) {

        String messageReplaced = message.replaceAll("\\r|\\n","");
        String[] permissions = ( StringUtils.isEmpty(permissionAccount) || StringUtils.isEmpty( permissionType))
                ? null : new String[]{permissionAccount + "@" + permissionType };
        mDataManager
                .pushActionNoWallet(contract, action, messageReplaced, permissions, privateKey)
                .subscribeOn(scheduler.computation())
                .subscribe( new Consumer<PushTxnResponse>() {
                    @Override
                    public void accept(PushTxnResponse pushTxnResponse) throws Exception {
                        Gson gson = new Gson();
                        String pushTxnResponseString = gson.toJson(pushTxnResponse);
                        promise.resolve(pushTxnResponseString);
                    }
                });
    }
}