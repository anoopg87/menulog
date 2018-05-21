package au.com.menulog.ui.adapter

import android.view.View
import au.com.menulog.BR
import au.com.menulog.R
import au.com.menulog.base.BindingRecyclerViewAdapter
import au.com.menulog.data.Restaurant

class RestaurantAdapter(restaurants: List<Restaurant>) : BindingRecyclerViewAdapter<Restaurant, RestaurantVH>(restaurants) {

    override val layoutId: Int = R.layout.restaurant_view

    override fun createViewHolder(view: View?): RestaurantVH = view?.let { RestaurantVH(it) }!!

    override val variable: Int = BR.viewModel
}