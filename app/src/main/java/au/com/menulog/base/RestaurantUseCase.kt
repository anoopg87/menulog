package au.com.menulog.base

import au.com.menulog.data.Restaurants
import au.com.menulog.repositories.RestaurantRepo
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RestaurantUseCase @Inject constructor(rxCallManager: RXCallManager, private val restaurantRepo: RestaurantRepo): BaseUseCase(rxCallManager) {

    fun execute(outCode: String, onNext: (Restaurants)->Unit, onError:(Throwable)->Unit):Disposable{
       return uiSubscribe(restaurantRepo.fetchRestaurants(outCode),onNext,onError)
    }


}