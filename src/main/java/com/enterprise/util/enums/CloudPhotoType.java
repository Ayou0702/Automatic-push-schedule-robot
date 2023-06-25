package com.enterprise.util.enums;

/**
 * 云图库的枚举类
 *
 * @author PrefersMin
 * @version 1.0
 */
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

    public int getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public static String getTypeNameByTypeId(int typeId) {
        for (CloudPhotoType type : CloudPhotoType.values()) {
            if (type.getTypeId() == typeId) {
                return type.getTypeName();
            }
        }
        return null;
    }

}
