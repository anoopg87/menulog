package au.com.menulog.repositories.implementation

import au.com.menulog.api.RestaurantService
import au.com.menulog.data.Restaurants
import au.com.menulog.repositories.RestaurantRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RestaurantRepoImpl @Inject constructor( private val restaurantService: RestaurantService) : RestaurantRepo {

    override fun fetchRestaurants(outCode: String): Single<Restaurants> {
        return restaurantService.getRestaurants(outCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}