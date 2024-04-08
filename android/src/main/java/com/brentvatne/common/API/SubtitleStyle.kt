package com.brentvatne.common.API

import com.brentvatne.ReactBridgeUtils
import com.facebook.react.bridge.ReadableMap
import com.google.android.exoplayer2.ui.CaptionStyleCompat

/**
 * Helper file to parse SubtitleStyle prop and build a dedicated class
 */
class SubtitleStyle private constructor() {
    var fontSize = -1
        private set
    var paddingLeft = 0
        private set
    var paddingRight = 0
        private set
    var paddingTop = 0
        private set
    var paddingBottom = 0
        private set
    var foregroundColor = "#FFFFFF"
        private set
    var backgroundColor = "#000000"
        private set
    var windowColor = "#00000000"
        private set
    var edgeType = CaptionStyleCompat.EDGE_TYPE_NONE
        private set
    var edgeColor = "#00000000"
        private set
    var fontFamilyPath: String? = null
        private set

    companion object {
        private const val PROP_FONT_SIZE_TRACK = "fontSize"
        private const val PROP_PADDING_BOTTOM = "paddingBottom"
        private const val PROP_PADDING_TOP = "paddingTop"
        private const val PROP_PADDING_LEFT = "paddingLeft"
        private const val PROP_PADDING_RIGHT = "paddingRight"
        private const val PROP_FOREGROUND_COLOR = "foregroundColor";
        private const val PROP_BACKGROUND_COLOR = "backgroundColor";
        private const val PROP_WINDOW_COLOR = "windowColor";
        private const val PROP_EDGE_TYPE = "edgeType";
        private const val PROP_EDGE_COLOR = "edgeColor";
        private const val PROP_FONT_FAMILY_PATH = "fontFamilyPath";
        @JvmStatic
        fun parse(src: ReadableMap?): SubtitleStyle {
            val subtitleStyle = SubtitleStyle()
            subtitleStyle.fontSize = ReactBridgeUtils.safeGetInt(src, PROP_FONT_SIZE_TRACK, -1)
            subtitleStyle.paddingBottom = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_BOTTOM, 0)
            subtitleStyle.paddingTop = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_TOP, 0)
            subtitleStyle.paddingLeft = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_LEFT, 0)
            subtitleStyle.paddingRight = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_RIGHT, 0)
            subtitleStyle.foregroundColor = ReactBridgeUtils.safeGetString(src, PROP_FOREGROUND_COLOR, "#FFFFFF")
            subtitleStyle.backgroundColor = ReactBridgeUtils.safeGetString(src, PROP_BACKGROUND_COLOR, "#000000")
            subtitleStyle.windowColor = ReactBridgeUtils.safeGetString(src, PROP_WINDOW_COLOR, "#00000000")
            subtitleStyle.edgeType = convertStringToEdgeType(ReactBridgeUtils.safeGetString(src, PROP_EDGE_TYPE, "none"))
            subtitleStyle.edgeColor = ReactBridgeUtils.safeGetString(src, PROP_EDGE_COLOR, "#00000000")
            subtitleStyle.fontFamilyPath = ReactBridgeUtils.safeGetString(src, PROP_FONT_FAMILY_PATH, null)
            return subtitleStyle
        }
    }
    private fun convertStringToEdgeType(edgeTypeString: String): Int {
        return when (edgeTypeString) {
            "none" -> CaptionStyleCompat.EDGE_TYPE_NONE
            "outline" -> CaptionStyleCompat.EDGE_TYPE_OUTLINE
            "dropShadow" -> CaptionStyleCompat.EDGE_TYPE_DROP_SHADOW
            "raised" -> CaptionStyleCompat.EDGE_TYPE_RAISED
            "depressed" -> CaptionStyleCompat.EDGE_TYPE_DEPRESSED
            else -> throw IllegalArgumentException("Invalid edge type")
        }
    }
}