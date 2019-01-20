package io.plactal.eoscommander.di.component;

import android.app.Application;
import android.content.Context;

import com.evacoop.eosecc.RNEosEccModule;

import javax.inject.Singleton;

import dagger.Component;
import io.plactal.eoscommander.app.EosCommanderApp;
import io.plactal.eoscommander.data.EoscDataManager;
import io.plactal.eoscommander.data.remote.HostInterceptor;
import io.plactal.eoscommander.di.ApplicationContext;
import io.plactal.eoscommander.di.module.AppModule;

@Singleton
@Component( modules = AppModule.class)
public interface AppComponent {
    @ApplicationContext
    Context context();

    void inject(EosCommanderApp eosCommanderApp);


    Application application();
    EoscDataManager dataManager();
    HostInterceptor hostInterceptor();
}
