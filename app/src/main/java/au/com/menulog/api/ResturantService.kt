package au.com.menulog.api

import au.com.menulog.data.Restaurants
import au.com.menulog.util.ENDPOINT_RESTAURANTS
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RestaurantService {
    @GET(ENDPOINT_RESTAURANTS)
    fun getRestaurants(@Query("q") outCode: String): Single<Restaurants>
}