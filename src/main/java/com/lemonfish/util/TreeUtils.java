package com.lemonfish.util;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.util
 * @date 2020/5/7 12:56
 */
public class TreeUtils<T> {

    /**
     * T的父级ID属性
     */
    public static final String PARENT_ID = "parentId";
    /**
     * T的ID属性
     */
    public static final String ID = "id";
    /**
     * T的List集合属性
     */
    public static final String CHILDREN = "children";

    /**
     * 外部调用，直接调用该方法
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> buildTree(List<T> list) {
        if (list != null && list.size() > 0) {
            try {
                return buildTree(list, 0L, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> List<T> buildTreePlus(List<T> list) {
        if (list != null && list.size() > 0) {
            try {
                System.out.println("treePlus");
                return buildTree(list, 0L, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static <T> List<T> buildTree(List<T> list, Long parentId, int type) throws NoSuchFieldException, IllegalAccessException {
        // 创建一个List，用于接收数据，最后return
        List<T> result = new ArrayList<>();
        addToResult(list, parentId, result, type);
        buildTree(list, null, parentId, type);
        return result;
    }


    public static <T> void buildTree(List<T> list, T t, Long parentId, int type) throws NoSuchFieldException, IllegalAccessException {
        List<T> result = new ArrayList<>();
        addToResult(list, parentId, result, type);

        if (result.size() > 0) {
            for (T item : result) {
                Field idField = null;
                if (type == 0) {
                    idField = item.getClass().getSuperclass().getDeclaredField(ID);
                } else {
                    idField = item.getClass().getSuperclass().getSuperclass().getDeclaredField(ID);
                }
                idField.setAccessible(true);
                Long id = (Long) idField.get(item);
                buildTree(list, item, id, type);
            }
        } else {
            result = null;
        }
        // 给属性设置值
        if (t != null) {
            Field f;
            if (type == 0) {
                f = t.getClass().getDeclaredField(CHILDREN);
            } else {
                f = t.getClass().getSuperclass().getDeclaredField(CHILDREN);
            }
            f.setAccessible(true);
            f.set(t, result);
        }
    }

    private static <T> void addToResult(List<T> list, Long parentId, List<T> result, int type) {
        List<T> c = list.stream().filter(item -> {
            try {
                Field parentIdField;
                if (type == 0) {
                    parentIdField = item.getClass().getDeclaredField(PARENT_ID);
                } else {
                    parentIdField = item.getClass().getSuperclass().getDeclaredField(PARENT_ID);
                }
                parentIdField.setAccessible(true);
                Long pId = (Long) parentIdField.get(item);
                return pId.equals(parentId);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
        result.addAll(c);
    }


}
