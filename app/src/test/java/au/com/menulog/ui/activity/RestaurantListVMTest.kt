package au.com.menulog.ui.activity

import au.com.menulog.data.Restaurants
import au.com.menulog.repositories.RestaurantRepo
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestaurantListVMTest {

    @Mock
    lateinit var restaurantRepo: RestaurantRepo

    @Mock
    lateinit var restaurantListVM: RestaurantListVM

    @Mock
    lateinit var mockResponse: Single<Restaurants>

    @Mock
    lateinit var mockRestaurants: Restaurants

    private val postCode = "SE19"


    @Test
    fun shouldDisplayRestaurantsForThePostCodeProvided() {

        Mockito.`when`(restaurantRepo.fetchRestaurants(postCode)).thenReturn(mockResponse)
        restaurantListVM.fetchRestaurants(postCode)
        Mockito.verify(restaurantListVM.setRestaurantsToList(mockRestaurants))
    }

    @Test
    fun shouldDisplayErrorMessageOnException() {
        Mockito.`when`(restaurantRepo.fetchRestaurants(postCode)).thenReturn(null)
        restaurantListVM.fetchRestaurants(postCode)
        Mockito.verify(restaurantListVM.showErrorMessage("Error"))
    }


}