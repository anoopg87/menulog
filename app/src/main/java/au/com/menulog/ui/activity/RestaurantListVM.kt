package au.com.menulog.ui.activity

import android.annotation.SuppressLint
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import au.com.menulog.MenuLog
import au.com.menulog.R
import au.com.menulog.base.ActivityViewModel
import au.com.menulog.base.OnClickListener
import au.com.menulog.base.RestaurantUseCase
import au.com.menulog.data.Restaurant
import au.com.menulog.data.Restaurants
import au.com.menulog.di.ResturantModule
import au.com.menulog.repositories.implementation.RestaurantRepoImpl
import au.com.menulog.ui.adapter.RestaurantAdapter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class RestaurantListVM(activity: RestaurantList) : ActivityViewModel<RestaurantList>(activity) {

    companion object {
        const val OUT_CODE = "outCode"
    }

    @Inject
    lateinit var restaurantRepoImpl: RestaurantRepoImpl
    private var outCode = ""
    private val compositeDisposable = CompositeDisposable()
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    init {
        MenuLog.appComponent.plus(ResturantModule()).inject(this)
        initUI()
    }

    private fun initUI() {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.title = activity.getString(R.string.restaurants)
        outCode = activity.intent.getStringExtra(OUT_CODE)
        swipeRefreshLayout = activity.binding?.swipeToRefresh
        swipeRefreshLayout?.post {
            swipeRefreshLayout?.isRefreshing = true
            fetchRestaurants(outCode)
        }
        swipeRefreshLayout?.setOnRefreshListener { fetchRestaurants(outCode) }
    }


    fun fetchRestaurants(outCode: String) {
        restaurantRepoImpl.fetchRestaurants(outCode).subscribe({
            showRestaurants(it)
        }, {
            swipeRefreshLayout?.isRefreshing = false
            showErrorMessage(it.localizedMessage)
        })?.let { compositeDisposable.add(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun showRestaurants(restaurants: Restaurants) {
        activity.binding?.responseCount?.visibility = View.VISIBLE
        activity.binding?.responseCount?.text = "${restaurants.Restaurants.size} ${activity.getString(R.string.restaurants_listed)} $outCode"
        swipeRefreshLayout?.isRefreshing = false
        setRestaurantsToList(restaurants)
    }

    fun showErrorMessage(message: String) {
        activity.binding?.root?.let { Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show() }
    }

    fun setRestaurantsToList(restaurants: Restaurants) {
        val restaurantsListView = activity.binding?.restaurants as RecyclerView
        val adapter = RestaurantAdapter(restaurants.Restaurants)
        adapter.onClickListener = object : OnClickListener<Restaurant> {
            override fun onClick(d: Restaurant, pos: Int) {

            }
        }
        restaurantsListView.layoutManager = LinearLayoutManager(activity)
        restaurantsListView.setHasFixedSize(true)
        restaurantsListView.setItemViewCacheSize(20)
        restaurantsListView.isDrawingCacheEnabled = true
        restaurantsListView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        restaurantsListView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}