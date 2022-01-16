package hu.uni.miskolc.iit.mobile.my2048.app.di

import hu.uni.miskolc.iit.mobile.my2048.app.fragment.LevelSelectorViewModel
import hu.uni.miskolc.iit.mobile.my2048.app.fragment.GameViewModel
import hu.uni.miskolc.iit.mobile.my2048.app.fragment.ResultViewModel
import hu.uni.miskolc.iit.mobile.my2048.app.fragment.StartGameViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { hu.uni.miskolc.iit.mobile.my2048.framework.db.GameDatabase.getInstance(androidContext()) }

    viewModel { GameViewModel(get()) }
    viewModel { ResultViewModel() }
    viewModel { LevelSelectorViewModel() }
    viewModel { StartGameViewModel() }
}
