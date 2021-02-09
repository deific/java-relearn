
package learn.jvm.classloader;

public class MyClassLoader {


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
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
}