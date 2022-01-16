package hu.uni.miskolc.iit.mobile.my2048.app

import android.app.Application
import hu.uni.miskolc.iit.mobile.my2048.app.di.appModule
import hu.uni.miskolc.iit.mobile.my2048.framework.di.daoModule
import hu.uni.miskolc.iit.mobile.my2048.framework.di.dataSourceModule
import hu.uni.miskolc.iit.mobile.my2048.framework.di.interactorModule
import hu.uni.miskolc.iit.mobile.my2048.framework.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GameApplication)
            modules(listOf(appModule, daoModule, dataSourceModule, repositoryModule, interactorModule))
        }
    }
}