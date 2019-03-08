package com.billreminder.ui.base.binding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.*
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.google.common.base.Objects
import com.google.common.base.Strings
import com.billreminder.data.converter.Converter
import java.io.File
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.BlurTransformation

private const val MIN_ALPHA = 0.35f
private const val MAX_ALPHA = 1.0f
private const val BLUR_RADIUS = 25

@InverseBindingMethods(
        InverseBindingMethod(type = Spinner::class, attribute = "selectedItem"),
        InverseBindingMethod(type = CalendarView::class, attribute = "date")
)
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("error")
    fun setError(view: TextInputLayout, resId: Int?) {
        view.error = resId?.let { view.resources.getString(it) }
    }

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun changeViewVisibility(view: View, bool: Boolean) {
        view.visibility = if (bool) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["enabledIf", "alpha"], requireAll = false)
    fun enabledIf(view: View, bool: Boolean?, alpha: Float?) {
        var newAlpha = alpha
        if (alpha == null) {
            newAlpha = MIN_ALPHA
        }

        if (bool == null || !bool) {
            view.isEnabled = false
            newAlpha?.let { view.animate().alpha(it) }
        } else {
            view.animate().alpha(MAX_ALPHA).withEndAction { view.isEnabled = true }
        }
    }

    @JvmStatic
    @BindingAdapter("animateVisibleIf")
    fun changeViewVisibilityWithAlphaTransition(view: View, bool: Boolean) {
        if (bool) {
            view.visibility = View.VISIBLE
            view.animate().alpha(1.0f)
        } else {
            view.animate().alpha(0.0f).withEndAction { view.visibility = View.GONE }
        }
    }

    @JvmStatic
    @BindingAdapter("invisibleIf")
    fun viewInvisibleIf(view: View, bool: Boolean) {
        view.visibility = if (bool) View.INVISIBLE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("transparentIf")
    fun changeViewAlpha(view: View, bool: Boolean) {
        view.alpha = if (bool) 0f else 1f
    }

    @JvmStatic
    @BindingAdapter("fadeOutIf")
    fun fadeOutIf(view: View, bool: Boolean) {
        view.alpha = if (bool) MIN_ALPHA else 1f
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bitmapSource(view: ImageView, uri: String?) {
        if (!Strings.isNullOrEmpty(uri)) {
            Glide.clear(view)
            Glide.with(view.context)
                    .load(uri)
                    .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("srcColor")
    fun setSrcColor(view: ImageView, @ColorRes color: Int) {
        val drawable = view.drawable
        drawable.mutate()

        DrawableCompat.setTint(drawable, ResourcesCompat.getColor(view.resources, color, null))
        view.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("drawableColor")
    fun setDrawableColor(textView: TextView, @ColorRes color: Int) {
        val drawables = textView.compoundDrawables
        for (drawable in drawables) {
            if (drawable != null) {
                drawable.mutate()
                DrawableCompat.setTint(drawable, ResourcesCompat.getColor(textView.resources, color, null))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("resTint")
    fun setTintResColor(imageView: ImageView, @ColorRes color: Int) {
        val rgbColor = ContextCompat.getColor(imageView.context, color)
        imageView.setColorFilter(rgbColor)
    }

    @JvmStatic
    @BindingAdapter("circularImage")
    fun circularBitmapSource(view: ImageView, uri: String) {
        Glide.with(view.context).load(uri).bitmapTransform(CropCircleTransformation(
                Glide.get(view.context).bitmapPool)).into(view)
    }

    @JvmStatic
    @BindingAdapter("blurredImage")
    fun blurredBitmapSource(view: ImageView, uri: String) {
        Glide.with(view.context).load(uri).bitmapTransform(BlurTransformation(
                view.context, Glide.get(view.context).bitmapPool, BLUR_RADIUS)).into(view)
    }

    @JvmStatic
    @BindingAdapter("circularImageFile")
    fun circularBitmapFileSource(view: ImageView, f: File?) {
        if (f != null && f.exists()) {
            Glide.with(view.context).load(f).bitmapTransform(CropCircleTransformation(
                    Glide.get(view.context).bitmapPool)).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("circularImageDrawable")
    fun circularBitmapDrawableSource(view: ImageView, @DrawableRes drawableRes: Int) {
        Glide.with(view.context).load(drawableRes).bitmapTransform(CropCircleTransformation(
                Glide.get(view.context).bitmapPool)).into(view)
    }

    @JvmStatic
    @BindingAdapter("blurredImageFile")
    fun blurredBitmapFileSource(view: ImageView, f: File?) {
        if (f != null && f.exists()) {
            Glide.with(view.context).load(f).bitmapTransform(BlurTransformation(
                    view.context, Glide.get(view.context).bitmapPool, BLUR_RADIUS)).into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("alphaCondition", "alphaValue", "enableAnimation")
    fun animateAlpha(view: View, alphaCondition: Boolean, alphaValue: Float, enableAnimation: Boolean) {
        if (alphaCondition) {
            if (enableAnimation) {
                view.animate().alpha(alphaValue)
            } else {
                view.alpha = alphaValue
            }
        } else {
            if (enableAnimation) {
                view.animate().alpha(1.0f)
            } else {
                view.alpha = 1.0f
            }
        }
    }

    @JvmStatic
    @BindingAdapter("converter", "text")
    fun <U, V : CharSequence> applyConversion(view: TextView, converter: Converter<U, V>, value: U?) {
        view.text = converter.convert(value)
    }

    @JvmStatic
    @BindingAdapter("items")
    fun spinnerItems(spinner: Spinner, items: List<String>?) {
        if (items == null || items.isEmpty()) {
            return
        }

        val adapter = ArrayAdapter(spinner.context,
                android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("itemsRes")
    fun spinnerItems(spinner: Spinner, @ArrayRes arrayResId: Int) {
        val adapter = ArrayAdapter.createFromResource(spinner.context,
                arrayResId, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("selectedItemAttrChanged")
    fun setSelectedItemListener(view: Spinner, itemChange: InverseBindingListener?) {
        if (itemChange == null) {
            view.onItemSelectedListener = null
        } else {
            view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    itemChange.onChange()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    @JvmStatic
    @BindingAdapter("selectedItem")
    fun setSelectedItem(view: Spinner, item: String?) {
        if (Strings.isNullOrEmpty(item)) {
            return
        }

        val adapter = view.adapter ?: return

        val selectedItem = view.selectedItem.toString()
        if (Objects.equal(selectedItem, item)) {
            return
        }

        for (i in 0 until adapter.count) {
            if (Objects.equal(adapter.getItem(i), item)) {
                view.setSelection(i)
                break
            }
        }
    }

    @JvmStatic
    @BindingAdapter("reveal")
    fun reveal(view: View, condition: Boolean) {
        if (!view.isAttachedToWindow) {
            view.visibility = if (condition) View.VISIBLE else View.INVISIBLE
            return
        }

        val cx = view.measuredWidth / 2
        val cy = view.measuredHeight / 2

        if (condition) {
            val finalRadius = Math.max(view.width, view.height) / 2

            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius.toFloat())

            view.visibility = View.VISIBLE
            anim.start()
        } else {
            val initialRadius = view.width / 2
            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius.toFloat(), 0f)

            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.INVISIBLE
                }
            })

            anim.start()
        }
    }
}