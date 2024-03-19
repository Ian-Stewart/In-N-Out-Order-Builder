package di

import org.koin.core.context.startKoin
import org.koin.dsl.module
import repo.CartRepository
import viewmodel.CartViewModel
import viewmodel.ExtrasViewModel
import viewmodel.MenuViewModel
import viewmodel.NewEditViewModel
import viewmodel.OrderViewModel

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}

val appModule = module {
    single<CartRepository> { CartRepository() }
    factory { CartViewModel(get()) }
    factory { ExtrasViewModel(get()) }
    factory { NewEditViewModel(get()) }
    factory { OrderViewModel(get()) }
    factory { MenuViewModel() }
}