package hu.uni.miskolc.iit.mobile.my2048.framework.di

import hu.uni.miskolc.iit.mobile.my2048.core.data.datasource.GameDataSource
import hu.uni.miskolc.iit.mobile.my2048.core.data.repository.GameRepository
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Game
import hu.uni.miskolc.iit.mobile.my2048.core.interactor.ActualizeGame
import hu.uni.miskolc.iit.mobile.my2048.core.interactor.EndGame
import hu.uni.miskolc.iit.mobile.my2048.core.interactor.GameInteractors
import hu.uni.miskolc.iit.mobile.my2048.core.interactor.StartGame
import hu.uni.miskolc.iit.mobile.my2048.framework.db.GameDatabase
import hu.uni.miskolc.iit.mobile.my2048.framework.db.datasource.RoomGameDataSource
import hu.uni.miskolc.iit.mobile.my2048.framework.db.mapper.GameMapper
import org.koin.dsl.module

val daoModule = module {
    single { get<GameDatabase>().gameDao() }
}

val dataSourceModule = module {
    single<GameDataSource> { RoomGameDataSource(get(), GameMapper()) }
}

val repositoryModule = module {
    single<GameRepository> { GameRepository(get()) }
}

val interactorModule = module {
    single { StartGame(get()) }
    single { EndGame(get()) }
    single { ActualizeGame(get()) }
    single { GameInteractors(get(), get(), get()) }
}
