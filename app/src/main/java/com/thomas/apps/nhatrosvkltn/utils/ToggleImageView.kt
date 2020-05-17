package com.thomas.apps.nhatrosvkltn.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.thomas.apps.nhatrosvkltn.R




class ToggleImageView :
    androidx.appcompat.widget.AppCompatImageView, View.OnClickListener {

    private  val TAG = "ToggleImageView"

    private  val STATE_CHECKED = 1
    private  val STATE_UNCHECKED = 2

    private var mState = STATE_UNCHECKED
    private var mCheckedRes = 0
    private var mUncheckedRes = 0

    private var mCallbacks: OnStateChangedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ToggleImageView,
            0, 0
        )
        try {
            mCheckedRes = typedArray.getResourceId(R.styleable.ToggleImageView_src_checked, 0)
            mUncheckedRes = typedArray.getResourceId(R.styleable.ToggleImageView_src_unchecked, 0)
        } finally {
            typedArray.recycle()
        }
        if (mUncheckedRes != 0) setImage(mUncheckedRes)
        setOnClickListener(this)
    }

    interface OnStateChangedListener {
        fun onChecked()
        fun onUnchecked()
    }

    override fun onClick(v: View?) {
        if (mState == STATE_CHECKED) {
            mState = STATE_UNCHECKED
            setImage(mUncheckedRes)
            if (mCallbacks != null) mCallbacks!!.onUnchecked()
        } else {
            mState = STATE_CHECKED
            setImage(mCheckedRes)
            if (mCallbacks != null) mCallbacks!!.onChecked()
        }
    }

    /**
     * Set image from resource
     *
     * @param resID image resource id
     */
    private fun setImage(resID: Int) {
        if (resID != 0) {
            setImageResource(resID)
        } else {
            Log.i(TAG, "setImage: No image resource provided")
        }
    }

    fun addStateListener(l: OnStateChangedListener?) {
        mCallbacks = l
    }

    fun setChecked() {
        setChecked(false)
    }

    fun setUnchecked() {
        setUnchecked(false)
    }

    fun setChecked(callback: Boolean) {
        mState = STATE_CHECKED
        setImage(mCheckedRes)
        if (callback) mCallbacks!!.onChecked()
    }

    fun setUnchecked(callback: Boolean) {
        mState = STATE_UNCHECKED
        setImage(mUncheckedRes)
        if (callback) mCallbacks!!.onUnchecked()
    }

    fun getState(): Int {
        return mState
    }


}