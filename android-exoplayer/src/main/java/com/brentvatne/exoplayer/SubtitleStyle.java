package com.brentvatne.exoplayer;
import android.graphics.Color;

import com.brentvatne.ReactBridgeUtils;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.exoplayer2.text.CaptionStyleCompat;

/**
 * Helper file to parse SubtitleStyle prop and build a dedicated class
 */
public class SubtitleStyle {
    private static final String PROP_FONT_SIZE_TRACK = "fontSize";
    private static final String PROP_PADDING_BOTTOM = "paddingBottom";
    private static final String PROP_PADDING_TOP = "paddingTop";
    private static final String PROP_PADDING_LEFT = "paddingLeft";
    private static final String PROP_PADDING_RIGHT = "paddingRight";
    private static final String PROP_FOREGROUND_COLOR = "foregroundColor";
    private static final String PROP_BACKGROUND_COLOR = "backgroundColor";
    private static final String PROP_WINDOW_COLOR = "windowColor";
    private static final String PROP_EDGE_TYPE = "edgeType";
    private static final String PROP_EDGE_COLOR = "edgeColor";


    private int fontSize = -1;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;

    private String foregroundColor = "#FFFFFF";
    private String backgroundColor = "#000000";
    private String windowColor = "#transparent";
    private int edgeType = CaptionStyleCompat.EDGE_TYPE_NONE;
    private String edgeColor = "#transparent";

    private SubtitleStyle() {}

    int getFontSize() {return fontSize;}
    int getPaddingBottom() {return paddingBottom;}
    int getPaddingTop() {return paddingTop;}
    int getPaddingLeft() {return paddingLeft;}
    int getPaddingRight() {return paddingRight;}
    String getForegroundColor() {return foregroundColor;}
    String getBackgroundColor() {return backgroundColor;}
    String getWindowColor() {return windowColor;}
    int getEdgeType() {return edgeType;}
    String getEdgeColor() {return edgeColor;}

    public static SubtitleStyle parse(ReadableMap src) {
        SubtitleStyle subtitleStyle = new SubtitleStyle();
        subtitleStyle.fontSize = ReactBridgeUtils.safeGetInt(src, PROP_FONT_SIZE_TRACK, -1);
        subtitleStyle.paddingBottom = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_BOTTOM, 0);
        subtitleStyle.paddingTop = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_TOP, 0);
        subtitleStyle.paddingLeft = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_LEFT, 0);
        subtitleStyle.paddingRight = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_RIGHT, 0);
        subtitleStyle.foregroundColor = ReactBridgeUtils.safeGetString(src, PROP_FOREGROUND_COLOR, "#FFFFFF");
        subtitleStyle.backgroundColor = ReactBridgeUtils.safeGetString(src, PROP_BACKGROUND_COLOR, "#000000");
        subtitleStyle.windowColor = ReactBridgeUtils.safeGetString(src, PROP_WINDOW_COLOR, "transparent");
        subtitleStyle.edgeType = convertStringToEdgeType(ReactBridgeUtils.safeGetString(src, PROP_EDGE_TYPE, "none"));
        subtitleStyle.edgeColor = ReactBridgeUtils.safeGetString(src, PROP_EDGE_COLOR, "transparent");
        return subtitleStyle;
    }

    private static int convertStringToEdgeType(String edgeTypeString) {
        switch (edgeTypeString) {
            case "none":
                return CaptionStyleCompat.EDGE_TYPE_NONE;
            case "outline":
                return CaptionStyleCompat.EDGE_TYPE_OUTLINE;
            case "dropShadow":
                return CaptionStyleCompat.EDGE_TYPE_DROP_SHADOW;
            case "raised":
                return CaptionStyleCompat.EDGE_TYPE_RAISED;
            case "depressed":
                return CaptionStyleCompat.EDGE_TYPE_DEPRESSED;
            default:
                throw new IllegalArgumentException("Invalid edge type");
        }
    }
}
