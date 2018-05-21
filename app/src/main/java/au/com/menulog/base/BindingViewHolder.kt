package au.com.menulog.base

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BindingViewHolder<D : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: D, variable: Int, onClickListener: OnClickListener<D>? = null)
}