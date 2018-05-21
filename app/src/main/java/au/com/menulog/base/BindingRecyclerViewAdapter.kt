package au.com.menulog.base

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import au.com.menulog.extentions.inflate

abstract class BindingRecyclerViewAdapter<D : Any, VH : BindingViewHolder<D>>(var dataSet: List<D>) : RecyclerView.Adapter<VH>() {
    @get:LayoutRes
    abstract val layoutId: Int
    var onClickListener: OnClickListener<D>? = null
    abstract fun createViewHolder(view: View?): VH
    @get:IdRes
    abstract val variable: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = createViewHolder(parent.inflate(layoutId))
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(dataSet[position], variable, onClickListener = onClickListener)
    }

    override fun getItemCount(): Int = dataSet.size
}