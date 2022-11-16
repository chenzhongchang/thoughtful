package csj.thoughtful.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.ObjectUtils;

public class XObjectUtils {

    public static Map<String, List<Object>> getDifferentProperities(Object thisObject, Object anotherObject) {
        if (thisObject == null || anotherObject == null)
            throw new NullPointerException();

        if (thisObject.getClass() != anotherObject.getClass())
            throw new IllegalArgumentException("the two objects are not the same class type");

        Map<String, List<Object>> differentProperities = new HashMap<String, List<Object>>();

        Class<?> clazz = thisObject.getClass();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();

            try {
                AccessibleObject.setAccessible(fields, true);

                for (Field field : fields) {
                    Object obj1 = field.get(thisObject);
                    Object obj2 = field.get(anotherObject);
                    if (!ObjectUtils.equals(obj1, obj2)) {
                        List<Object> list = new ArrayList<Object>();
                        list.add(obj1);
                        list.add(obj2);
                        differentProperities.put(field.getName(), list);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            clazz = clazz.getSuperclass();
        }

        return differentProperities;

    }

    public static <T> void copyProperities(T dest, T src) {
        if (dest == null || src == null)
            throw new NullPointerException();

        if (dest.getClass() != src.getClass())
            throw new IllegalArgumentException("dest and src are not the same class type");

        Class<?> clazz = dest.getClass();

        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            try {
                AccessibleObject.setAccessible(fields, true);

                for (Field field : fields) {
                    Object obj1 = field.get(dest);
                    Object obj2 = field.get(src);
                    if (!org.apache.commons.lang.ObjectUtils.equals(obj1, obj2))
                        field.set(dest, obj2);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            clazz = clazz.getSuperclass();
        }
    }
    
    
    public static <T> void copyProperities2(T dest, T src) {
        if (dest == null || src == null)
            throw new NullPointerException();

        Class<?> clazz = src.getClass();

        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            try {
                AccessibleObject.setAccessible(fields, true);
                Field destField;
                for (Field srcField : fields) {
                    if(java.lang.reflect.Modifier.isFinal(srcField.getModifiers()))
                        continue;
                    Object obj1 = srcField.get(src);
                    try {
                        destField=dest.getClass().getDeclaredField(srcField.getName());
                    if(destField==null)
                        continue;
                    }catch(Exception ex) {
                        continue;
                    }
                    destField.setAccessible(true);
                    Object obj2 = destField.get(dest);
                    if (!org.apache.commons.lang.ObjectUtils.equals(obj1, obj2))
                        destField.set(dest, obj1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            clazz = clazz.getSuperclass();
        }
    }

    public static <T> List<T> newArrayList(T... objs) {
        List<T> results = new ArrayList<T>();
        for (T obj : objs) {
            results.add(obj);
        }
        return results;
    }
    
    public static List<Long> toLongArray(String str,String splitChar){
        String[] strList=str.split(splitChar);
        List<Long> result=new ArrayList<Long>();
        for(String temp:strList) {
            result.add(Long.valueOf(temp));
        }
        return result;
    }
    
    
    public static List<Integer> toIntegerArray(String str,String splitChar){
        String[] strList=str.split(splitChar);
        List<Integer> result=new ArrayList<Integer>();
        for(String temp:strList) {
            result.add(Integer.valueOf(temp));
        }
        return result;
    }

    public static String toString(Object[] objects) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        if (objects == null)
            return "";
        for (Object obj : objects) {
            sb.append(obj);
            if (index != objects.length - 1)
                sb.append(",");
            index++;
        }
        return sb.toString();
    }

    public static <T> void compare(List<T> oldBos, List<T> newBos, List<T> addedBos, List<T> updatedBos,
            List<T> deletedBos, Comparator<T> comparator) {
        List<T> copyOldBos = new CopyOnWriteArrayList<T>(oldBos);
        for (T newBo : newBos) {
            boolean flag = false;
            for (T oldBo : copyOldBos) {
                if (comparator.compare(newBo, oldBo) == 0) {
                    boolean isDifferent = isDifferent(oldBo, newBo);
                    if (isDifferent) {
                        copyProperities(oldBo, newBo);
                        updatedBos.add(oldBo);
                    }
                    flag = true;
                    copyOldBos.remove(oldBo);
                    break;
                }
            }
            if (!flag) {
                addedBos.add(newBo);
            }
            flag = false;
        }
        deletedBos.addAll(copyOldBos);
    }

    public static <T> void compareCopyId(List<T> oldBos, List<T> newBos, List<T> addedBos, List<T> updatedBos,
            List<T> deletedBos, Comparator<T> comparator) {
        List<T> copyOldBos = new CopyOnWriteArrayList<T>(oldBos);
        for (T newBo : newBos) {
            boolean flag = false;
            for (T oldBo : copyOldBos) {
                if (comparator.compare(newBo, oldBo) == 0) {
                    boolean isDifferent = isDifferentNoId(oldBo, newBo);
                    if (isDifferent) {
                        copyProperities(oldBo, newBo);
                        updatedBos.add(oldBo);
                    }
                    flag = true;
                    copyOldBos.remove(oldBo);
                    break;
                }
            }
            if (!flag) {
                addedBos.add(newBo);
            }
            flag = false;
        }
        deletedBos.addAll(copyOldBos);
    }

    public static boolean isDifferentNoId(Object thisBo, Object anotherBo) {
        if (thisBo.getClass() != anotherBo.getClass())
            throw new IllegalArgumentException("tow objects are not the same class type");
        Class<?> clazz = thisBo.getClass();

        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            Field idField;
            String id = "";
            try {
                AccessibleObject.setAccessible(fields, true);
                for (Field field : fields) {
                    if ("id".equals(field.getName()) && field.get(thisBo) != null) {
                        id = field.get(thisBo).toString();
                        idField = anotherBo.getClass().getDeclaredField("id");
                        idField.setAccessible(true);
                        idField.set(anotherBo, id);
                    }
                    Object obj1 = field.get(thisBo);
                    Object obj2 = field.get(anotherBo);
                    if (!ObjectUtils.equals(obj1, obj2)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                System.out.print("faild");
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    public static boolean isDifferent(Object thisBo, Object anotherBo) {
        if (thisBo.getClass() != anotherBo.getClass())
            throw new IllegalArgumentException("tow objects are not the same class type");
        Class<?> clazz = thisBo.getClass();

        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            try {
                AccessibleObject.setAccessible(fields, true);
                for (Field field : fields) {
                    Object obj1 = field.get(thisBo);
                    Object obj2 = field.get(anotherBo);
                    if (!ObjectUtils.equals(obj1, obj2)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                System.out.print("faild");
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    public static Object cloneObject(Object dst) {
        ByteArrayOutputStream byteOut = null;
        ObjectOutputStream objOut = null;
        ByteArrayInputStream byteIn = null;
        ObjectInputStream objIn = null;
        try {
            byteOut = new ByteArrayOutputStream();
            objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(dst);
            byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            objIn = new ObjectInputStream(byteIn);
            return objIn.readObject();

        } catch (IOException e) {
            throw new RuntimeException("Clone Object failed in IO.", e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found.", e);

        } finally {
            try {
                byteIn = null;
                byteOut = null;
                if (objOut != null)
                    objOut.close();
                if (objIn != null)
                    objIn.close();
            } catch (IOException e) {

            }

        }

    }

}
