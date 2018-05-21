package au.com.menulog.di

import au.com.menulog.api.RestaurantService
import au.com.menulog.repositories.RestaurantRepo
import au.com.menulog.repositories.implementation.RestaurantRepoImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class WebServiceModule {

    @Provides
    @AppScope
    fun restaurantService(retrofit: Retrofit): RestaurantService = retrofit.create(RestaurantService::class.java)

    @Provides
    @AppScope
    fun provideRestaurantRepo(restaurantService: RestaurantService) : RestaurantRepo{
        return RestaurantRepoImpl(restaurantService)
    }
    
}