package com.minicloud.common.core.util;

import java.util.Random;

public class GuidUtil {

    private static Random random = new Random();
    public static long longGuid(){
        long currentTime = System.currentTimeMillis();
        return Long.parseLong(currentTime+""+random.nextInt(10000));
    }
}
