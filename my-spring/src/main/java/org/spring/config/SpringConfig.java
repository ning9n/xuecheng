package org.spring.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.lang.annotation.IncompleteAnnotationException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class SpringConfig {
    private Class<?> configClass;
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();


    public SpringConfig(Class<?> configClass) throws FileNotFoundException, ClassNotFoundException {
        this.configClass = configClass;
        // 解析ComponentScan，获取包名
        // getDeclaredAnnotation:不获取父类注解
        if (!configClass.isAnnotationPresent(ComponentScan.class)) {
            throw new RuntimeException();
        }
        ComponentScan annotation = configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = annotation.value();
        if (path.isEmpty()) {
            path = configClass.getPackage().getName();
        }
        path = path.replace('.', '/');
        //通过应用类加载器加载
        //扫描 App->classpath
        ClassLoader classLoader = SpringConfig.class.getClassLoader();
        URL url = classLoader.getResource(path);
        if (url == null) {
            throw new FileNotFoundException();
        }
        // 解码 URL 路径
        String decodedPath;
        try {
            decodedPath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new FileNotFoundException("Failed to decode URL path: " + url.getFile());
        }
        File file = new File(decodedPath);
        if (!file.isDirectory()) {
            throw new FileNotFoundException();
        }
        File[] files = file.listFiles((dir, name) -> name.endsWith(".class"));
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            String className = absolutePath.substring(absolutePath.indexOf(path.replace('/', '\\')), absolutePath.lastIndexOf("."));
            className = className.replace("\\", ".");
            System.out.println(className);
            Class<?> clazz = classLoader.loadClass(className);
            if (clazz.isAnnotationPresent(Component.class)) {
                //判断类的模式，单例、原型
                Component componentAnnotation = clazz.getAnnotation(Component.class);
                String beanName = componentAnnotation.value();
                BeanDefinition beanDefinition = new BeanDefinition();
                if (clazz.isAnnotationPresent(Scope.class)) {
                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                    String scope = scopeAnnotation.value();
                    beanDefinition.setScope(scope);
                }
            }
        }

    }
}
