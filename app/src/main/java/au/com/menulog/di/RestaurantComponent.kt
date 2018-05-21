package au.com.menulog.di

import au.com.menulog.ui.activity.RestaurantListVM
import dagger.Subcomponent


@Screen
@Subcomponent(modules = [(ResturantModule::class)])
interface RestaurantComponent {
    fun inject(restaurantListVM: RestaurantListVM)
}