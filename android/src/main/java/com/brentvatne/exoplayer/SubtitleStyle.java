package com.brentvatne.exoplayer;
import android.graphics.Color;

import com.brentvatne.ReactBridgeUtils;
import com.facebook.react.bridge.ReadableMap;

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
    private static final String PROP_FONT_FAMILY_PATH = "fontFamilyPath";


    private int fontSize = -1;
    private int paddingLeft = 0;
    private int paddingRight = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;

    private String foregroundColor = "#FFFFFF";
    private String backgroundColor = "#000000";
    private String windowColor = "#00000000";
    private String edgeColor = "#00000000";
    private String fontFamilyPath = null;

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
    String getFontFamilyPath() {return fontFamilyPath;}

    public static SubtitleStyle parse(ReadableMap src) {
        SubtitleStyle subtitleStyle = new SubtitleStyle();
        subtitleStyle.fontSize = ReactBridgeUtils.safeGetInt(src, PROP_FONT_SIZE_TRACK, -1);
        subtitleStyle.paddingBottom = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_BOTTOM, 0);
        subtitleStyle.paddingTop = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_TOP, 0);
        subtitleStyle.paddingLeft = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_LEFT, 0);
        subtitleStyle.paddingRight = ReactBridgeUtils.safeGetInt(src, PROP_PADDING_RIGHT, 0);
        subtitleStyle.foregroundColor = ReactBridgeUtils.safeGetString(src, PROP_FOREGROUND_COLOR, "#FFFFFF");
        subtitleStyle.backgroundColor = ReactBridgeUtils.safeGetString(src, PROP_BACKGROUND_COLOR, "#000000");
        subtitleStyle.windowColor = ReactBridgeUtils.safeGetString(src, PROP_WINDOW_COLOR, "#00000000");
        subtitleStyle.edgeColor = ReactBridgeUtils.safeGetString(src, PROP_EDGE_COLOR, "#00000000");
        subtitleStyle.fontFamilyPath = ReactBridgeUtils.safeGetString(src, PROP_FONT_FAMILY_PATH, null);
        return subtitleStyle;
    }

}
