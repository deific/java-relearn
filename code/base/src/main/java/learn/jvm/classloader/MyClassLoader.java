
package learn.jvm.classloader;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {

    public byte[] getClassFile(String filePath, String name)  {
        try {
            String myPath = "file:///" + filePath + "//" + name.replace(".","//") + ".class";
            return Files.readAllBytes(Paths.get(new URI(myPath)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 重载loadClass方法，打破双亲加载规则，自己加载class
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class clazz = null;
        try {
            clazz = getSystemClassLoader().getParent().loadClass(name);
        } catch (ClassNotFoundException e) {
            byte[] classData = getClassFile("E://temp", name);
            clazz = defineClass(classData, 0, classData.length);
        }
        return clazz;
    }

    public Class<?> loadClassByMySelf(String className) throws ClassNotFoundException {
        return this.loadClass(className);
    }

    public static void loadClassBySystem() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        // 默认应该是ApplicationClassLoader
        System.out.println(loader);
        // 父加载器应该是ExtClassLoader
        System.out.println(loader.getParent());
        // 根加载器是BootstrapClassLoader,是c++实现的本地实现，应用为null
        System.out.println(loader.getParent().getParent());

        Class cls = null;
        // 类加载器直接加载类，由于Test在当前应用程序路径下，实际有ApplicationClassLoader加载
        // loadClass()加载类时，不会只想初始化，即静态代码块
        cls = loader.loadClass("learn.jvm.classloader.Test");
//        System.out.println(cls);
//        // Class.fromName()加载类后会执行初始化方法块
//        cls = Class.forName("com.learn.jvm.classloader.Test");

        // 指定加载器，并控制是否初始化
        cls = Class.forName("learn.jvm.classloader.Test", false, loader);

        Test test = (Test)cls.newInstance();

//        Test[] test2 = new Test[Integer.MAX_VALUE];
        System.out.println(test);
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        loadClassBySystem();
        MyClassLoader myClassLoader = new MyClassLoader();
        Thread.currentThread().setContextClassLoader(myClassLoader);
        Class clazz = myClassLoader.loadClassByMySelf("A");

        System.out.println(clazz.newInstance().getClass().getClassLoader());

    }
}