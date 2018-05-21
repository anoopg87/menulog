package au.com.menulog.data

import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.widget.ImageView
import android.widget.TextView
import au.com.menulog.MenuLog
import com.squareup.picasso.Picasso


data class Restaurants(
        val Restaurants: List<Restaurant>
)


data class Restaurant(

        val DriveDistance: Double,
        val DriveInfoCalculated: Boolean,
        val DeliveryMenuId: Long,
        val DeliveryOpeningTime: String,
        val DeliveryCost: Double,
        val MinimumDeliveryValue: Double,
        val DeliveryWorkingTimeMinutes: Int,
        val OpeningTimeIso: String,
        val SendsOnItsWayNotifications: Boolean,
        val RatingAverage: Float,
        val Latitude: Double,
        val Longitude: Double,
        val Id: Int,
        val Name: String,
        val Address: String,
        val Postcode: String,
        val City: String,
        val CuisineTypes: List<CuisineTypes>,
        val Url: String,
        val IsOpenNow: Boolean,
        val IsPremier: Boolean,
        val IsSponsored: Boolean,
        val IsTemporaryBoost: Boolean,
        val SponsoredPosition: Int,
        val IsNew: Boolean,
        val IsTemporarilyOffline: Boolean,
        val ReasonWhyTemporarilyOffline: String,
        val UniqueName: String,
        val IsCloseBy: Boolean,
        val IsHalal: Boolean,
        val DefaultDisplayRank: Int,
        val IsOpenNowForDelivery: Boolean,
        val IsOpenNowForCollection: Boolean,
        val RatingStars: Double,
        val Logo: List<Logo>,
        val Deals: List<Deals>,
        val NumberOfRatings: Double
)

data class Logo(
        val StandardResolutionURL: String
)

data class CuisineTypes(
        val Id: Int,
        val Name: String,
        val SeoName: String
)

data class Deals(
        val Description: String,
        val DiscountPercent: String,
        val QualifyingPrice: String

)

@BindingAdapter("android:cuisineType")
fun setCuisine(view: TextView, cuisineTypes: List<CuisineTypes>){
    for (i in 0 until cuisineTypes.size) if(i<cuisineTypes.size-1){
        view.append(cuisineTypes[i].Name +", ")
    }else{
        view.append(cuisineTypes[i].Name)
    }
}


@BindingAdapter("android:src")
fun loadImage(view: ImageView, url: String) {
    val picasso: Picasso? = MenuLog.appComponent.picasso()
    picasso?.load(url)?.fit()?.into(view)
}



