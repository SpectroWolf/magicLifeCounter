package com.spectrotech.magiclifecounter.components.buttons

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import com.spectrotech.magiclifecounter.R
import com.spectrotech.magiclifecounter.components.buttons.enums.*
import com.spectrotech.magiclifecounter.databinding.MenuComponentBinding
import com.spectrotech.magiclifecounter.extensions.gone
import com.spectrotech.magiclifecounter.extensions.visible

class MenuComponent(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    private var binding: MenuComponentBinding
    private var state = MenuComponentType.OPEN
    private var attrsTypedArray: TypedArray? = null

    init {
        binding = MenuComponentBinding.inflate(LayoutInflater.from(context), this)
        getPropertiesFromAttrsTypedArray(attrs)
        setMenuProperties(
            state
        )
    }

    fun setMenuProperties(
        state: MenuComponentType = this.state
    ) {
        setState(state)
        refreshLayout()
    }

    private fun setState(state: MenuComponentType) {
        when (state) {
            MenuComponentType.OPEN -> {
                setStateOpen()
            }
            MenuComponentType.CLOSE -> {
                setStateClose()
            }
        }
    }

    private fun getPropertiesFromAttrsTypedArray(attrs: AttributeSet?) {
        attrsTypedArray = getAttributes(attrs)

        attrsTypedArray?.getInt(
            R.styleable.MenuComponent_menuType,
            TYPE_DEFAULT_INDEX
        )?.let {
            state = MenuComponentType.values()[it]
        }
    }

    private fun setStateOpen(){
        binding.menuIcon.gone()
        binding.closeIcon.visible()
        binding.clockIcon.visible()
        binding.diceIcon.visible()
        binding.exitIcon.visible()
        binding.resetIcon.visible()
    }

    private fun setStateClose(){
        binding.menuIcon.visible()
        binding.closeIcon.gone()
        binding.clockIcon.gone()
        binding.diceIcon.gone()
        binding.exitIcon.gone()
        binding.resetIcon.gone()
    }

    fun getAttributes(attrs: AttributeSet?): TypedArray {
        return context.obtainStyledAttributes(
            attrs,
            R.styleable.MenuComponent
        )
    }

    private fun setPadding(cardView: CardView, verticalPaddingId: Int, horizontalPaddingId: Int) {
        val paddingVertical =
            resources.getDimension(verticalPaddingId).toInt()
        val paddingHorizontal =
            resources.getDimension(horizontalPaddingId).toInt()
        cardView.setPadding(
            paddingHorizontal,
            paddingVertical,
            paddingHorizontal,
            paddingVertical
        )
    }

    private fun refreshLayout() {
        invalidate()
        requestLayout()
    }

    companion object {
        private const val TYPE_DEFAULT_INDEX = 0
    }
}