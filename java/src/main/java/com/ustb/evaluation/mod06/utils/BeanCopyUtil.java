package com.ustb.evaluation.mod06.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 蒲锐
 * @CreateTme 2022/12/15 17:56
 * Version1.0.0
 */

public class BeanCopyUtil {

    public static <T> T copyObject(Object source, Class<T> target) {
        T temp = null;
        try {
            temp = target.newInstance();
            if (null != source) {
                org.springframework.beans.BeanUtils.copyProperties(source, temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanCopyUtil.copyObject(obj, target));
            }
        }
        return list;
    }
}
