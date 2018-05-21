package au.com.menulog.ui.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import au.com.menulog.base.OnClickListener
import au.com.menulog.data.Restaurant
import au.com.menulog.base.BindingViewHolder

class RestaurantVH(itemView: View) : BindingViewHolder<Restaurant>(itemView) {
    override fun bind(item: Restaurant, variable: Int, onClickListener: OnClickListener<Restaurant>?) {
        val dataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
        dataBinding?.setVariable(variable, item)
        dataBinding?.root?.setOnClickListener({ onClickListener?.onClick(item, adapterPosition) })
    }
}