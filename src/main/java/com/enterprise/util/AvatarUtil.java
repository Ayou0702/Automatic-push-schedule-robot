package com.enterprise.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AvatarUtil {

    public void duplicateChecking(int id, String pre) {

        String[] avatarType = {".png", ".jpg", ".jpeg"};

        for (String type : avatarType) {
            File file = new File(pre + id + type);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }

    }

}
