package com.test.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author inggg
 * @version v1.0
 * @since 2018/7/27 10:31
 */

public class RestTemplateUtil {
    private static RestTemplate restTemplate;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);
        requestFactory.setReadTimeout(120000);
        restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * 表单格式请求
     *
     * @param url
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T postFormForObject(String url, Object object, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/x-www-form-urlencoded;charset=UTF-8"));
        return post(url, object, clazz, headers);
    }

    /**
     * json格式请求
     *
     * @param url
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T postJsonForObject(String url, Object object, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));

        HttpEntity requestEntity = new HttpEntity<>(object, headers);
        T result = restTemplate.postForObject(url, requestEntity, clazz);
        return result;
    }

    private static <T> T post(String url, Object object, Class<T> clazz, HttpHeaders headers) {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (object instanceof Map) {
            Map objectMap = (Map) object;
            Set<Map.Entry> entrySet = objectMap.entrySet();
            for (Map.Entry entry : entrySet) {
                Object value = entry.getValue();
                if (Objects.isNull(value) || Objects.isNull(entry.getKey())) {
                    continue;
                }
                map.add(String.valueOf(entry.getKey()), String.valueOf(value));
            }
        } else {
            //获取当前实体类及父类的所有属性
            final Class<?> cls = object.getClass();
            Objects.requireNonNull(cls, "object 并非实体类");
            final List<Field> allFields = new ArrayList<>();
            Class<?> currentClass = cls;
            while (currentClass != null) {
                final Field[] declaredFields = currentClass.getDeclaredFields();
                Collections.addAll(allFields, declaredFields);
                currentClass = currentClass.getSuperclass();
            }
            final List<Field> allFieldsList = allFields;
            Field[] fields = allFieldsList.toArray(allFieldsList.toArray(new Field[0]));


            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (Objects.isNull(value)) {
                        continue;
                    }
                    map.add(field.getName(), String.valueOf(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        HttpEntity requestEntity = new HttpEntity<>(map, headers);
        T result = restTemplate.postForObject(url, requestEntity, clazz);
        return result;
    }

    public static <T> T get(String url, Object object, Class<T> clazz) {
        try {
            if (null == object) {
                return restTemplate.getForObject(url, clazz);
            }
            StringBuilder params = new StringBuilder();
            if (object instanceof Map) {
                Map objectMap = (Map) object;
                Set<Map.Entry> entrySet = objectMap.entrySet();
                for (Map.Entry entry : entrySet) {
                    Object value = entry.getValue();
                    if (Objects.isNull(value) || Objects.isNull(entry.getKey())) {
                        continue;
                    }
                    if (params.length() == 0) {
                        params.append("?");
                    } else {
                        params.append("&");
                    }
                    params.append(entry.getKey()).append("=").append(value);
                }
            } else {
                Field[] fields = object.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    try {
                        Object value = field.get(object);
                        if (Objects.isNull(value)) {
                            continue;
                        }
                        if (params.length() == 0) {
                            params.append("?");
                        } else {
                            params.append("&");
                        }
                        params.append(field.getName()).append("=").append(value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            T t = restTemplate.getForObject(url + params.toString(), clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}