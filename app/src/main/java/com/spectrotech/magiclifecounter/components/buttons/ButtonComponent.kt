package com.spectrotech.magiclifecounter.components.buttons

import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.spectrotech.magiclifecounter.R
import com.spectrotech.magiclifecounter.components.buttons.enums.ButtonComponentLabelPosition
import com.spectrotech.magiclifecounter.components.buttons.enums.ButtonComponentSize
import com.spectrotech.magiclifecounter.components.buttons.enums.ButtonComponentState
import com.spectrotech.magiclifecounter.components.buttons.enums.ButtonComponentType
import com.spectrotech.magiclifecounter.databinding.ButtonComponentBinding
import com.spectrotech.magiclifecounter.extensions.gone
import com.spectrotech.magiclifecounter.extensions.visible

class ButtonComponent(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    private var binding: ButtonComponentBinding
    private var size = ButtonComponentSize.MEDIUM
    private var type = ButtonComponentType.PRIMARY
    private var state = ButtonComponentState.DEFAULT_ENABLED
    private var labelPosition = ButtonComponentLabelPosition.NO_ICON
    private var attrsTypedArray: TypedArray? = null
    private var text = resources.getString(R.string.button_component_text)
    private var icon: Int? = null
    private var iconColor: Int? = null
    private var textColor: Int? = null
    private var isFontBold: Boolean = false

    init {
        binding = ButtonComponentBinding.inflate(LayoutInflater.from(context), this)
        getPropertiesFromAttrsTypedArray(attrs)
        setButtonProperties(
            size,
            type,
            state,
            labelPosition,
            text,
            icon,
            iconColor,
            textColor,
            isFontBold
        )
    }

    private fun getPropertiesFromAttrsTypedArray(attrs: AttributeSet?) {
        attrsTypedArray = getAttributes(attrs)

        attrsTypedArray?.getInt(R.styleable.ButtonComponent_size, SIZE_DEFAULT_INDEX)?.let {
            size = ButtonComponentSize.values()[it]
        }
        attrsTypedArray?.getInt(R.styleable.ButtonComponent_ydButtonType, TYPE_DEFAULT_INDEX)?.let {
            type = ButtonComponentType.values()[it]
        }
        attrsTypedArray?.getInt(R.styleable.ButtonComponent_state, STATE_DEFAULT_INDEX)?.let {
            state = ButtonComponentState.values()[it]
        }
        attrsTypedArray?.getInt(
            R.styleable.ButtonComponent_labelPosition,
            LABEL_POSITION_DEFAULT_INDEX
        )?.let {
            labelPosition = ButtonComponentLabelPosition.values()[it]
        }
        attrsTypedArray?.getString(R.styleable.ButtonComponent_android_text)?.let {
            text = it
        }
        attrsTypedArray?.getResourceId(R.styleable.ButtonComponent_icon, 0)?.let {
            if (it != 0) {
                icon = it
            }
        }
        attrsTypedArray?.getResourceId(R.styleable.ButtonComponent_iconColor, 0)?.let {
            if (it != 0) {
                iconColor = it
            }
        }
        attrsTypedArray?.getResourceId(R.styleable.ButtonComponent_buttonTextColor, 0)?.let {
            if (it != 0) {
                textColor = it
            }
        }
        attrsTypedArray?.getBoolean(R.styleable.ButtonComponent_isFontBold, false)?.let {
            isFontBold = it
        }
    }

    fun getAttributes(attrs: AttributeSet?): TypedArray {
        return context.obtainStyledAttributes(
            attrs,
            R.styleable.ButtonComponent
        )
    }

    fun setButtonProperties(
        size: ButtonComponentSize = this.size,
        type: ButtonComponentType = this.type,
        state: ButtonComponentState = this.state,
        labelPosition: ButtonComponentLabelPosition = this.labelPosition,
        text: String? = null,
        icon: Int? = null,
        iconColor: Int? = null,
        textColor: Int? = this.textColor,
        isFontBold: Boolean? = null
    ) {
        setSize(size)
        setType(type, state, textColor)
        setLabelPosition(labelPosition, state)
        text?.let {
            binding.text.text = it
        }
        icon?.let {
            binding.rightIcon.setImageResource(it)
            binding.leftIcon.setImageResource(it)
        }
        iconColor?.let {
            binding.leftIcon.setColorFilter(
                ContextCompat.getColor(context, it),
                PorterDuff.Mode.SRC_IN
            )
            binding.leftIcon.setColorFilter(
                ContextCompat.getColor(context, it),
                PorterDuff.Mode.SRC_IN
            )
        }
        isFontBold?.let {
            if (it) {
                setRobotoBoldFont()
            } else {
                setRobotoFont()
            }
        }
        refreshLayout()
    }

    fun getButtonProperties(): ButtonComponentProperties {
        return ButtonComponentProperties(
            this.size,
            this.type,
            this.state,
            this.labelPosition
        )
    }

    private fun setSize(size: ButtonComponentSize) {
        this.size = size

        when (size) {
            ButtonComponentSize.SMALL -> {
                setSizeSmall()
            }
            ButtonComponentSize.MEDIUM -> {
                setSizeMedium()
            }
            ButtonComponentSize.LARGE -> {
                setSizeLarge()
            }
        }
    }

    private fun setSizeSmall() {
        setSizeSmallIcon()
        setSizeSmallDimensions()
        setSizeSmallText()
        setSizeSmallPaddings()
    }

    private fun setSizeSmallDimensions() {
        setHeightDimension(binding.buttonView, R.dimen.button_component_small_size_dimensions)
    }

    private fun setSizeSmallIcon() {
        val smallIconSize = R.dimen.button_component_small_size_icon
        setIconSize(binding.leftIcon, smallIconSize)
        setIconSize(binding.rightIcon, smallIconSize)
    }

    private fun setSizeSmallText() {
        setTextSize(binding.text, R.dimen.button_component_small_size_text)
        setFontRobotoBold(binding.text)
    }

    private fun setSizeSmallPaddings() {
        setPadding(
            binding.buttonView,
            R.dimen.button_component_small_padding_vertical,
            R.dimen.button_component_small_padding_horizontal
        )
    }

    private fun setSizeMedium() {
        setSizeMediumIcon()
        setSizeMediumDimensions()
        setSizeMediumText()
        setSizeMediumPaddings()
    }

    private fun setSizeMediumIcon() {
        val mediumIconSize = R.dimen.button_component_medium_size_icon
        setIconSize(binding.leftIcon, mediumIconSize)
        setIconSize(binding.rightIcon, mediumIconSize)
    }

    private fun setSizeMediumDimensions() {
        setHeightDimension(binding.buttonView, R.dimen.button_component_medium_size_dimensions)
    }

    private fun setSizeMediumText() {
        setTextSize(binding.text, R.dimen.button_component_medium_size_text)
        setFontRobotoBold(binding.text)
    }

    private fun setSizeMediumPaddings() {
        setPadding(
            binding.buttonView,
            R.dimen.button_component_medium_size_padding_vertical,
            R.dimen.button_component_medium_size_padding_horizontal
        )
    }

    private fun setSizeLarge() {
        setSizeLargeIcon()
        setSizeLargeDimensions()
        setSizeLargeText()
        setSizeLargePaddings()
    }

    private fun setSizeLargeIcon() {
        val largeIconSize = R.dimen.button_component_large_size_icon
        setIconSize(binding.leftIcon, largeIconSize)
        setIconSize(binding.rightIcon, largeIconSize)
    }

    private fun setSizeLargeDimensions() {
        setHeightDimension(binding.buttonView, R.dimen.button_component_large_size_dimensions)
    }

    private fun setSizeLargeText() {
        setTextSize(binding.text, R.dimen.button_component_large_size_text)
        setFontRobotoBold(binding.text)
    }

    private fun setSizeLargePaddings() {
        setPadding(
            binding.buttonView,
            R.dimen.button_component_large_size_padding_vertical,
            R.dimen.button_component_large_size_padding_horizontal
        )
    }

    private fun setType(type: ButtonComponentType, state: ButtonComponentState, textColor: Int?) {
        this.type = type
        when (type) {
            ButtonComponentType.PRIMARY -> {
                setTypePrimary(state, textColor)
            }
            ButtonComponentType.SECONDARY -> {
                setTypeSecondary(state, textColor)
            }
            ButtonComponentType.TERTIARY -> {
                setTypeTertiary(state, textColor)
            }
            ButtonComponentType.TRANSPARENT -> {
                setTypeTransparent(state, textColor)
            }
        }
    }

//    private fun setTypePrimary(state: ButtonComponentState, textColor: Int?) {
//        val backgroundColor: Int
//        val textColorId: Int
//        val drawableBackgroundId: Int
//        goneLoadingState()
//        when (state) {
//            ButtonComponentState.DEFAULT_ENABLED -> {
//                backgroundColor = R.color.colorBranding
//                textColorId = textColor ?: R.color.white
//                drawableBackgroundId = R.drawable.background_button_component_primary_button_enabled
//                isEnabled = true
//            }
//            ButtonComponentState.PRESSED -> {
//                backgroundColor = R.color.colorBrandingDark20
//                textColorId = textColor ?: R.color.white
//                drawableBackgroundId = R.drawable.background_button_component_primary_button_pressed
//                isEnabled = false
//            }
//            ButtonComponentState.DISABLED -> {
//                backgroundColor = R.color.colorBrandingLight50
//                textColorId = textColor ?: R.color.colorBrandingLight30
//                drawableBackgroundId =
//                    R.drawable.background_button_component_primary_button_disabled
//
//                isEnabled = false
//            }
//            ButtonComponentState.LOADING -> {
//                backgroundColor = R.color.colorBranding
//                textColorId = textColor ?: R.color.white
//                setLoadingState(R.raw.lottie_loading_primary)
//                drawableBackgroundId = R.drawable.background_button_component_primary_button_loading
//
//                isEnabled = false
//            }
//        }
//
//        setProperties(
//            backgroundColorId = backgroundColor,
//            textColorId = textColorId,
//            backgroundDrawableId = drawableBackgroundId
//        )
//    }

//    private fun setTypeSecondary(state: ButtonComponentState, textColor: Int?) {
//
//        val backgroundColor: Int
//        val textColorId: Int
//        val drawableBackgroundId: Int
//        goneLoadingState()
//        when (state) {
//            ButtonComponentState.DEFAULT_ENABLED -> {
//                backgroundColor = R.color.white
//                textColorId = textColor ?: R.color.colorBranding
//                drawableBackgroundId = R.drawable.background_white_button_outline_red
//                isEnabled = true
//            }
//            ButtonComponentState.PRESSED -> {
//                backgroundColor = R.color.grayLight80
//                textColorId = textColor ?: R.color.colorBrandingDark20
//                drawableBackgroundId = R.drawable.background_button_component_secondary_pressed
//                isEnabled = false
//            }
//            ButtonComponentState.DISABLED -> {
//                backgroundColor = R.color.white
//                textColorId = textColor ?: R.color.colorBrandingLight30
//                drawableBackgroundId = R.drawable.background_button_component_secondary_disabled
//                isEnabled = false
//            }
//            ButtonComponentState.LOADING -> {
//                backgroundColor = R.color.white
//                textColorId = textColor ?: R.color.white
//                drawableBackgroundId = R.drawable.background_white_button_outline_red
//                setLoadingState(R.raw.lottie_loading_secondary)
//                isEnabled = false
//            }
//        }
//
//        setProperties(
//            backgroundColorId = backgroundColor,
//            textColorId = textColorId,
//            backgroundDrawableId = drawableBackgroundId
//        )
//    }
//
//    private fun setTypeTertiary(state: ButtonComponentState, textColor: Int?) {
//
//        val backgroundColor: Int
//        val textColorId: Int
//        val drawableBackgroundId: Int
//        goneLoadingState()
//        when (state) {
//            ButtonComponentState.DEFAULT_ENABLED -> {
//                backgroundColor = R.color.grayDark20
//                textColorId = textColor ?: R.color.grayDark20
//                drawableBackgroundId = R.drawable.background_button_component_tertiary
//                isEnabled = true
//            }
//            ButtonComponentState.PRESSED -> {
//                backgroundColor = R.color.grayLight80
//                textColorId = textColor ?: R.color.grayDark50
//                drawableBackgroundId = R.drawable.background_button_component_tertiary_pressed
//                isEnabled = false
//            }
//            ButtonComponentState.DISABLED -> {
//                backgroundColor = R.color.white
//                textColorId = textColor ?: R.color.grayLight20
//                drawableBackgroundId = R.drawable.background_button_component_tertiary_disabled
//                isEnabled = false
//            }
//            ButtonComponentState.LOADING -> {
//                backgroundColor = R.color.grayDark20
//                textColorId = textColor ?: R.color.grayDark20
//                drawableBackgroundId = R.drawable.background_button_component_tertiary
//                setLoadingState(R.raw.lottie_loading_terttiary)
//                isEnabled = false
//            }
//        }
//
//        setProperties(
//            backgroundColorId = backgroundColor,
//            textColorId = textColorId,
//            backgroundDrawableId = drawableBackgroundId
//        )
//    }
//
//    private fun setTypeTransparent(state: ButtonComponentState, textColor: Int?) {
//
//        val backgroundColor: Int
//        val textColorId: Int
//        val drawableBackgroundId: Int?
//        goneLoadingState()
//        when (state) {
//            ButtonComponentState.DEFAULT_ENABLED -> {
//                backgroundColor = R.color.transparent
//                textColorId = textColor ?: R.color.colorBranding
//                drawableBackgroundId = null
//                isEnabled = true
//            }
//            ButtonComponentState.PRESSED -> {
//                backgroundColor = R.color.grayLight60
//                textColorId = textColor ?: R.color.colorBrandingDark20
//                drawableBackgroundId = null
//                isEnabled = false
//            }
//            ButtonComponentState.DISABLED -> {
//                backgroundColor = R.color.transparent
//                textColorId = textColor ?: R.color.grayLight60
//                drawableBackgroundId = null
//                isEnabled = false
//            }
//            ButtonComponentState.LOADING -> {
//                backgroundColor = R.color.transparent
//                textColorId = textColor ?: R.color.colorBranding
//                drawableBackgroundId = null
//                setLoadingState(R.raw.lottie_loading_transparent)
//                isEnabled = false
//            }
//        }
//
//        setProperties(
//            backgroundColorId = backgroundColor,
//            textColorId = textColorId,
//            backgroundDrawableId = drawableBackgroundId
//        )
//    }
//
//    private fun setTypeDanger(state: ButtonComponentState, textColor: Int?) {
//        val backgroundColor: Int
//        val textColorId: Int
//        val drawableBackgroundId: Int?
//        goneLoadingState()
//        when (state) {
//            ButtonComponentState.DEFAULT_ENABLED -> {
//                backgroundColor = R.color.colorBrandingAlert
//                textColorId = textColor ?: R.color.white
//                drawableBackgroundId = null
//                isEnabled = true
//            }
//            ButtonComponentState.PRESSED -> {
//                backgroundColor = R.color.colorBrandingDark20
//                textColorId = textColor ?: R.color.white
//                drawableBackgroundId = null
//                isEnabled = false
//            }
//            ButtonComponentState.DISABLED -> {
//                backgroundColor = R.color.colorAlertLight50
//                textColorId = textColor ?: R.color.colorAlertLight30
//                drawableBackgroundId = null
//                isEnabled = false
//            }
//            ButtonComponentState.LOADING -> {
//                backgroundColor = R.color.colorBrandingAlert
//                textColorId = textColor ?: R.color.white
//                drawableBackgroundId = null
//                setLoadingState(R.raw.lottie_loading_danger)
//                isEnabled = false
//            }
//        }
//
//        setProperties(
//            backgroundColorId = backgroundColor,
//            textColorId = textColorId,
//            backgroundDrawableId = drawableBackgroundId
//        )
//    }

    private fun setLabelPosition(
        labelPosition: ButtonComponentLabelPosition,
        state: ButtonComponentState
    ) {
        this.labelPosition = labelPosition

        if (state == ButtonComponentState.LOADING) {
            // loading state doesn't allows label or icon
            return
        }

        when (labelPosition) {
            ButtonComponentLabelPosition.LEFT -> {
                setLabelPositionLeft()
            }
            ButtonComponentLabelPosition.RIGHT -> {
                setLabelPositionRight()
            }
            ButtonComponentLabelPosition.NO_ICON -> {
                setLabelPositionNoIcon()
            }
            ButtonComponentLabelPosition.NO_LABEL -> {
                setLabelPositionNoLabel()
            }
        }
    }

    private fun setLabelPositionLeft() {
        binding.leftIcon.gone()
        binding.rightIcon.visible()
        binding.text.visible()
    }

    private fun setLabelPositionRight() {
        binding.leftIcon.visible()
        binding.rightIcon.gone()
        binding.text.visible()
    }

    private fun setLabelPositionNoIcon() {
        binding.leftIcon.gone()
        binding.rightIcon.gone()
        binding.text.visible()
    }

    private fun setLabelPositionNoLabel() {
        binding.leftIcon.visible()
        binding.rightIcon.gone()
        binding.text.gone()

        when (size) {
            ButtonComponentSize.SMALL -> {
                setWidthDimension(
                    binding.buttonView,
                    R.dimen.button_component_small_size_dimensions
                )
            }
            ButtonComponentSize.MEDIUM -> {
                setWidthDimension(
                    binding.buttonView,
                    R.dimen.button_component_medium_size_dimensions
                )
            }
            ButtonComponentSize.LARGE -> {
                setWidthDimension(
                    binding.buttonView,
                    R.dimen.button_component_large_size_dimensions
                )
            }
        }
    }

    private fun refreshLayout() {
        invalidate()
        requestLayout()
    }

    private fun setFontRobotoBold(textView: TextView) {
        try {
            textView.typeface = ResourcesCompat.getFont(context, R.font.roboto_bold)
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                textView.typeface = resources.getFont(R.font.roboto_bold)
            }
        }
    }

    private fun setIconSize(imageView: ImageView, sizeId: Int) {
        val layoutParams = imageView.layoutParams
        val iconSize = resources.getDimension(sizeId).toInt()
        layoutParams.height = iconSize
        layoutParams.width = iconSize
        imageView.layoutParams = layoutParams
    }

    private fun setHeightDimension(cardView: CardView, sizeId: Int) {
        val smallSize = resources.getDimension(sizeId).toInt()
        val layoutParams = cardView.layoutParams
        layoutParams.height = smallSize
        layoutParams.width = LayoutParams.MATCH_PARENT
        cardView.layoutParams = layoutParams
    }

    private fun setWidthDimension(cardView: CardView, sizeId: Int) {
        val smallSize = resources.getDimension(sizeId).toInt()
        val layoutParams = cardView.layoutParams
        layoutParams.width = smallSize
        cardView.layoutParams = layoutParams
    }


    private fun setTextSize(textView: TextView, textSizeId: Int) {
        textView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(textSizeId)
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

    private fun setProperties(
        backgroundColorId: Int,
        textColorId: Int,
        backgroundDrawableId: Int? = null
    ) {
        binding.buttonView.setCardBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                backgroundColorId,
                null
            )
        )
        binding.text.setTextColor(ResourcesCompat.getColor(resources, textColorId, null))

        binding.leftIcon.setColorFilter(
            ContextCompat.getColor(context, textColorId),
            PorterDuff.Mode.SRC_IN
        )
        binding.rightIcon.setColorFilter(
            ContextCompat.getColor(context, textColorId),
            PorterDuff.Mode.SRC_IN
        )

        if (backgroundDrawableId != null) {
            binding.buttonView.background = ResourcesCompat.getDrawable(
                resources,
                backgroundDrawableId,
                null
            )
        }
    }

    private fun goneLoadingState() {
        binding.animationView.gone()
        binding.animationView.pauseAnimation()
    }

    private fun setLoadingState(rawId: Int) {
        binding.text.gone()
        binding.rightIcon.gone()
        binding.leftIcon.gone()
        binding.animationView.visible()
        binding.animationView.setAnimation(rawId)
        binding.animationView.playAnimation()
    }

    private fun setRobotoFont() {
        try {
            binding.text.typeface = ResourcesCompat.getFont(context, R.font.roboto)
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.text.typeface = resources.getFont(R.font.roboto)
            }
        }
    }

    private fun setRobotoBoldFont() {
        try {
            binding.text.typeface = ResourcesCompat.getFont(context, R.font.roboto_bold)
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.text.typeface = resources.getFont(R.font.roboto_bold)
            }
        }
    }

    companion object {
        private const val SIZE_DEFAULT_INDEX = 0
        private const val STATE_DEFAULT_INDEX = 0
        private const val TYPE_DEFAULT_INDEX = 0
        private const val LABEL_POSITION_DEFAULT_INDEX = 0
    }
}