package au.com.menulog.base

interface OnClickListener<in D : Any> {
    fun onClick(d: D, pos: Int)
}