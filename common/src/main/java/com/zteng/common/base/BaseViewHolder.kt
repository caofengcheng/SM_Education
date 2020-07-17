package com.zteng.common.base

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView

import com.facebook.drawee.view.SimpleDraweeView
import com.suke.widget.SwitchButton


class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val sparseArray: SparseArray<View> = SparseArray()



    fun getView(viewId: Int): View? {
        var view: View? = this.sparseArray.get(viewId)
        if (view == null) {
            view = this.itemView.findViewById(viewId)
            this.sparseArray.put(viewId, view)
        }
        return view
    }

    fun callOnClick(id: Int): BaseViewHolder {
        getView(id)!!.callOnClick()
        return this
    }

    fun setItemVisibility(isVisible: Boolean) {
        val layoutParams = this.itemView.layoutParams
        if (isVisible) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            this.itemView.visibility = View.VISIBLE
        } else {
            this.itemView.visibility = View.GONE
            layoutParams.height = 0
            layoutParams.width = 0
        }

        this.itemView.layoutParams = layoutParams
    }

    fun setTag(id: Int, tag: Any): BaseViewHolder {
        getView(id)!!.tag = tag
        return this
    }

    fun getText(id: Int): String {
        return (getView(id) as TextView).text.toString()
    }

    fun setText(id: Int, text: String): BaseViewHolder {
        setText(id, text, "--")
        return this
    }

    fun setText(id: Int, text: String, defaultText: String): BaseViewHolder {
        (getView(id) as TextView).text = if (TextUtils.isEmpty(text)) defaultText else text
        return this
    }

    fun appendText(id: Int, text: String): BaseViewHolder {
        val textView = getView(id) as TextView?
        textView!!.text = textView.text.toString() + text
        return this
    }

    fun setTextColor(id: Int, textColor: Int): BaseViewHolder {
        (getView(id) as TextView).setTextColor(textColor)
        return this
    }

    fun setImageDrawable(id: Int, drawable: Drawable): BaseViewHolder {
        (getView(id) as ImageView).setImageDrawable(drawable)
        return this
    }

    fun setImageURI(id: Int, uri: String): BaseViewHolder {
        (getView(id) as SimpleDraweeView).setImageURI(uri)
        return this
    }

    fun setBackgroundColor(id: Int, color: Int): BaseViewHolder {
        getView(id)!!.setBackgroundColor(color)
        return this
    }

    fun setBackgroundResources(id: Int, Resources: Int): BaseViewHolder {
        getView(id)!!.setBackgroundResource(Resources)
        return this
    }

    fun isChecked(id: Int): Boolean {
        return (getView(id) as CompoundButton).isChecked
    }

    fun setChecked(id: Int, checked: Boolean): BaseViewHolder {
        (getView(id) as CompoundButton).isChecked = checked
        return this
    }

    fun setEnabled(id: Int, enable: Boolean): BaseViewHolder {
        getView(id)!!.isEnabled = enable
        return this
    }

    fun setVisibility(id: Int, visibility: Int): BaseViewHolder {
        getView(id)!!.visibility = visibility
        return this
    }

    fun setOnClickListener(id: Int, onclickListener: View.OnClickListener): BaseViewHolder {
        getView(id)!!.setOnClickListener(onclickListener)
        return this
    }

    fun setOnCheckedChangeListener(
        id: Int,
        onCheckedChangeListener: SwitchButton.OnCheckedChangeListener
    ): BaseViewHolder {
        (getView(id) as SwitchButton).setOnCheckedChangeListener(onCheckedChangeListener)
        return this
    }

    fun setOnCheckedChangeListener(
        id: Int,
        onCheckedChangeListener: CompoundButton.OnCheckedChangeListener
    ): BaseViewHolder {
        (getView(id) as CompoundButton).setOnCheckedChangeListener(onCheckedChangeListener)
        return this
    }
}