package io.plactal.eoscommander.app;

import android.app.Application;
import android.content.Context;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.google.gson.Gson;

import javax.inject.Inject;
import java.lang.StackTraceElement;
import java.lang.StringBuilder;

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
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

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

    public void getInfo(final Promise promise){
        mDataManager
                .getChainInfo()
                .subscribeOn(scheduler.computation())
                .subscribe( new Consumer<EosChainInfo>() {
                    @Override
                    public void accept(EosChainInfo info){
                        promise.resolve(info.getBrief());
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
            .subscribeWith( new Observer<PushTxnResponse>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(PushTxnResponse pushTxnResponse) {
                    try {
                        Gson gson = new Gson();
                        String pushTxnResponseString = gson.toJson(pushTxnResponse);
                        promise.resolve(pushTxnResponseString);
                    } catch(Exception e){
                        if (promise != null) {
                            promise.reject("ERR_UNEXPECTED_EXCEPTION", e);
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof HttpException) {
                        ResponseBody body = ((HttpException) e).response().errorBody();
                        try {
                            if (promise != null) {
                                promise.reject(body.string());
                            }
                        } catch (Exception ex) {
                            if (promise != null) {
                                promise.reject("ERR_UNEXPECTED_EXCEPTION: Problem with the blockchain. No info available.");
                            }
                        }
                    } else {
                        if (promise != null) {
                            promise.reject("ERR_UNEXPECTED_EXCEPTION: Not an error with the blockchain. Probably an error in react native code. Please refer to react-native-eos github page for more info.");
                        }
                    }
                    
                }

                @Override
                public void onComplete() {

                }
            });
}
}