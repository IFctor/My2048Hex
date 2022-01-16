package hu.uni.miskolc.iit.mobile.my2048.app

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:background")
fun View.background(resId: String?) {
    resId?.let {
        val id = resources.getIdentifier(it, "drawable", context.packageName)
        background = context.getDrawable(id)
    }
}