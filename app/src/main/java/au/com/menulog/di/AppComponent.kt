package au.com.menulog.di

import au.com.menulog.MenuLog
import au.com.menulog.repositories.implementation.RestaurantRepoImpl
import au.com.menulog.ui.activity.RestaurantListVM
import com.squareup.picasso.Picasso
import dagger.Component

@Component(modules = [(AppModule::class), (OkHttpModule::class), (PicassoModule::class), (RetrofitModule::class), (WebServiceModule::class)])
@AppScope
interface AppComponent {
    fun inject(app: MenuLog)
    fun picasso(): Picasso
    fun inject(repoImpl: RestaurantRepoImpl)
    fun plus(module: ResturantModule):RestaurantComponent
}