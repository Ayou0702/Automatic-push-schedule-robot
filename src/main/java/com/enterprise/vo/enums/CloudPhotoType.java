package com.enterprise.vo.enums;

import lombok.Getter;

/**
 * 云图库的枚举类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Getter
public enum CloudPhotoType {

    WALLPAPER(1, "wallpaper"),

    AVATAR(2,"avatar"),

    LIVE_WALLPAPER(3, "liveWallpaper"),

    BACKGROUND_IMAGE(4,"backgroundImage"),

    TOOL_PLAY(6,"toolPlay"),

    AI(14,"ai");

    private final int typeId;
    private final String typeName;

    CloudPhotoType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public static String getTypeNameByTypeId(int typeId) {
        for (CloudPhotoType value : CloudPhotoType.values()) {
            if (value.getTypeId() == typeId) {
                return value.getTypeName();
            }
        }
        return null;
    }

}
