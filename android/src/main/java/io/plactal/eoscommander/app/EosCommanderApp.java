package io.plactal.eoscommander.app;

import android.app.Application;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.plactal.eoscommander.data.EoscDataManager;
import io.plactal.eoscommander.data.remote.model.abi.EosAbiMain;
import io.plactal.eoscommander.data.remote.model.api.EosChainInfo;
import io.plactal.eoscommander.data.remote.model.api.PushTxnResponse;
import io.plactal.eoscommander.di.component.AppComponent;
import io.plactal.eoscommander.di.component.DaggerAppComponent;
import io.plactal.eoscommander.di.module.AppModule;
import io.plactal.eoscommander.ui.base.RxCallbackWrapper;
import io.plactal.eoscommander.util.StringUtils;
import io.plactal.eoscommander.util.rx.EoscSchedulerProvider;
import io.reactivex.functions.Consumer;

/**
 * Created by swapnibble on 2017-11-03.
 */

public class EosCommanderApp extends Application {
    private AppComponent mAppComponent;

    @Inject
    EoscDataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // https://android-developers.googleblog.com/2013/08/some-securerandom-thoughts.html
        PRNGFixes.apply();

    }

    public static EosCommanderApp get( Context context ){
        return (EosCommanderApp) context.getApplicationContext();
    }

    public AppComponent getAppComponent() { return mAppComponent; }
}
