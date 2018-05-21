package au.com.menulog.repositories

import au.com.menulog.data.Restaurants
import io.reactivex.Single

interface RestaurantRepo {
     fun fetchRestaurants(outCode: String): Single<Restaurants>
}