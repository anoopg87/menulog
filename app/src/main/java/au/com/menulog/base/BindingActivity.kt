package au.com.menulog.base

import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem


abstract class BindingActivity<B : ViewDataBinding, VM : ActivityViewModel<*>> : AppCompatActivity() {

    var binding: B? = null
        private set
    var viewModel: VM? = null
        private set
    var savedInstanceState: Bundle? = null
        private set

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    @get:IdRes
    abstract val variable: Int

    /**
     * Override for set layout resource
     *
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        bind()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        viewModel!!.onNewIntent(intent)
    }

    fun bind() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        this.viewModel = if (viewModel == null) onCreate() else viewModel
        binding!!.setVariable(variable, viewModel)
        binding!!.executePendingBindings()
    }

    fun resetViewModel() {
        viewModel = null
        viewModel = onCreate()
        binding!!.setVariable(variable, viewModel)
    }

    override fun onStart() {
        super.onStart()
        viewModel!!.onStart()
    }

    override fun onStop() {
        viewModel!!.onStop()
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPause() {
        viewModel!!.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel!!.onResume()
    }

    override fun onBackPressed() {
        if (!viewModel!!.onBackKeyPress()) {
            super.onBackPressed()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel!!.onDestroy()
    }

    public override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        savedInstanceState?.let { viewModel!!.onPostCreate(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel!!.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel!!.onConfigurationChanged(newConfig)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel!!.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel!!.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        viewModel!!.onCreateOptionsMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        viewModel!!.onPrepareOptionsMenu(menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        viewModel!!.onWindowFocusChanged(hasFocus)
    }

    abstract fun onCreate(): VM


}
