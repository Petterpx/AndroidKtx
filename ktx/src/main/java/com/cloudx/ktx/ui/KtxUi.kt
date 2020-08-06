package com.cloudx.ktx.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Checkable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.RecyclerViewDivider

/**
 * @Author petterp
 * @Date 2020/5/29-11:15 PM
 * @Email ShiyihuiCloud@163.com
 * @Function Ktx-UI-core
 */


/**
 * RecyclerView分割线
 */
fun RecyclerView.lineScop(
    lineHiegth: Float = 1f,
    context: Context,
    lineColor: Int = Color.parseColor("#f5f5f5")
) {
    RecyclerViewDivider.with(context)
        .color(lineColor)
        .size(context.dip2px(lineHiegth))
        .build()
        .addTo(this)
}

/** viewGroup遍历添加tag */
fun ViewGroup.viewAllTag(tag: Any, listener: View.OnClickListener) {
    val childCount = childCount
    for (i in 0..childCount) {
        val viewItem = getChildAt(i)
        viewItem?.let {
            if (viewItem is ViewGroup) {
                viewItem.viewAllTag(tag, listener)
            } else {
                viewItem.tag = tag
                viewItem.singleClicks(listener)
            }
        }
    }
}

/**
 * 获取屏幕宽,默认返回px
 * [isDp] 是否返回dp
 */
fun Activity.widthScreen(isDp: Boolean = false): Int {

    val defaultDisplay: Display = windowManager.defaultDisplay
    val point = Point()
    defaultDisplay.getSize(point)
    if (isDp) {
        return px2dip(point.y.toFloat())
    }
    return point.y
}


/**
 * 获取屏幕高,默认返回px
 * [isNavigationBar] 是否需包含底部栏+状态栏
 * [isDp] 是否需要转dp
 */
@JvmOverloads
fun Activity.heightScreen(isDp: Boolean = false, isNavigationBar: Boolean = true): Int {
    val height = if (isNavigationBar) {
        window.decorView.height
    } else {
        val defaultDisplay: Display = windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        point.y
    }
    if (isDp) {
        return px2dip(height.toFloat())
    }
    return height
}


/**
 * dp转px
 */
fun Context.dip2px(dpValue: Float): Int {
    val scale: Float = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


/**
 * px转dp
 */
fun Context.px2dip(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/** 延迟获取view属性，例如当wrap时，我们无法直接在onCreate中获取到真实属性 */
fun <T : View> T.delayView(obj: (T) -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.removeOnPreDrawListener(this)
            }
            obj(this@delayView)
            return true
        }

    })
}

/**
 * 防重复点击,默认延迟800ms
 */
var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

inline fun <T : View> T.singleClick(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

/**
 * 兼容this事件
 */
fun <T : View> T.singleClicks(onClickListener: View.OnClickListener, time: Long = 800) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            onClickListener.onClick(this)
        }
    }
}


interface OnClickListener {
    fun listenerClick(position: Int, v: View)
}

interface OnLongClickListener {
    fun listenerClickLong(position: Int, v: View)
}

/**
 * RecyclerView 移动到当前位置，
 *
 * @param manager       设置RecyclerView对应的manager
 * @param mRecyclerView 当前的RecyclerView
 * @param n             要跳转的位置
 */
fun RecyclerView.moveToPosition(
    n: Int
) {
    val firstItem: Int = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    val lastItem: Int = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
    if (n <= firstItem) {
        scrollToPosition(n)
    } else if (n <= lastItem) {
        val top = getChildAt(n - firstItem).top
        scrollBy(0, top)
    } else {
        scrollToPosition(n)
    }
}


//const val KTX_RV_ITEM_ONCLICK = 88888888
//private var RecyclerView.singleItemListener: OnClickListener?
//    get() {
//        return getTag(KTX_RV_ITEM_ONCLICK) as OnClickListener
//    }
//    set(value) {
//        setTag(KTX_RV_ITEM_ONCLICK, value)
//    }
//
//private var singleItemLongListener: OnLongClickListener? = null
//
//fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.single(listener: OnClickListener) {
//
//}
//
//fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.singleClick(
//    viewHolder: RecyclerView.ViewHolder,
//    time: Long = 800
//) {
//    viewHolder.itemView.singleClick(time) {
//        singleItemListener?.listenerClick(viewHolder.adapterPosition, it)
//    }
//}
//
//fun RecyclerView.ViewHolder.singleLongClick(time: Long = 800) {
//    itemView.singleClick(time) {
//        singleItemLongListener?.listenerClickLong(adapterPosition, it)
//    }
//}

