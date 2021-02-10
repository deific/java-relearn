package learn.jvm.classloader;

public class Test {

    int count;
    static int age;
    static {
        System.out.println("静态初始化方法被执行了,age = " + age);
    }

    public Test() {
        count = 2;
        age = 3;
        System.out.println("构造初始化方法被执行了,age = " + age);
    }
}
