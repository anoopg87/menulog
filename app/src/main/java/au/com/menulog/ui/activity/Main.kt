package au.com.menulog.ui.activity

import au.com.menulog.BR
import au.com.menulog.R
import au.com.menulog.databinding.MainBinding
import au.com.menulog.base.BindingActivity

class Main : BindingActivity<MainBinding, MainVM>() {
    override val variable: Int = BR.viewModel
    override val layoutId: Int = R.layout.main
    override fun onCreate(): MainVM = MainVM(this)
}