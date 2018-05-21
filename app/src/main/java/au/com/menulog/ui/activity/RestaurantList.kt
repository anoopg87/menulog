package au.com.menulog.ui.activity

import au.com.menulog.BR
import au.com.menulog.R
import au.com.menulog.databinding.RestaurantListBinding
import au.com.menulog.base.BindingActivity

class RestaurantList : BindingActivity<RestaurantListBinding, RestaurantListVM>() {
    override val variable: Int = BR.viewModel
    override val layoutId: Int = R.layout.restaurant_list
    override fun onCreate(): RestaurantListVM = RestaurantListVM(this)
}