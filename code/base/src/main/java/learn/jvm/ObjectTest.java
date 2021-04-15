
package learn.jvm;


import java.util.Random;

public class ObjectTest {

    static class M {
        // int 类型占用4 byte   1byte=8bit
        int a;
        // long 8 byte
        long b;
        // boolean类型，占用1byte
        boolean c;
        // s 是字符串的引用变量，占M对象的空间，值实际存储在堆栈的常量池了
        // 64位操作系统下，s占8 byte,jvm默认会开启oop（普通对象指针压缩）,会压缩到4byte
        // 32G内存以上时，压缩就不起作用了
        String s = "aaaa sddd";

        // 总共M m = new M()所用的内存大小为
        // head = (maskword 4 byte + class point 8 byte) +
        // 成员变量引用a 4byte + b 8byte + c 1byte +
        // s 4byte(原为8byte,jvm默认开启了类指针压缩为4byte
        // = 32 byte
    }

    static class M1 {
        // s 是字符串的引用变量，占M对象的空间，值实际存储在堆栈的常量池了
        // 64位操作系统下，s占8 byte,jvm默认会开启oop（普通对象指针压缩）,会压缩到4byte
        String[] s = new String[1];
    }

    public static void main(String[] args) {
//        M m = new M();
//        System.out.println(ClassLayout.parseInstance(m).toPrintable());

        M1 m1 = new M1();
//        System.out.println(ClassLayout.parseInstance(m1).toPrintable());

//        M1[] m2 = new M1[2];
//        System.out.println(ClassLayout.parseInstance(m2).toPrintable());


//        synchronized (m1) {
//            m1.hashCode();
//            System.out.println(ClassLayout.parseInstance(m1).toPrintable());
//        }
//
//        m1.hashCode();
//        System.out.println(ClassLayout.parseInstance(m1).toPrintable());

        spareArray();
    }


    public static void spareArray() {
        // 1维度稀疏数组
        int[] originArr = new int[1000];
        Random random = new Random();
        // 初始化数组
        for (int i = 1; i <= 10; i++) {
            int index = random.nextInt(1000);
            originArr[index] = i;
        }
        // 转为2维稀疏数组
        int[][] spareArrFor1 = new int[11][2];
        // 取第1行
        int[] row0 = spareArrFor1[0];
        row0[0] = 1000;
        row0[1] = 2;
        int rowIndex = 1;
        for (int i = 0; i < originArr.length; i++) {
            if (originArr[i] != 0) {
                System.out.printf("%s ", originArr[i]);
                spareArrFor1[rowIndex][0] = i;
                spareArrFor1[rowIndex][1] = originArr[i];
                rowIndex++;
            }
        }
        // 输出
        System.out.println("\n----------------------");
        for (int[] ints : spareArrFor1) {
            for (int anInt : ints) {
                System.out.printf("%d\t", anInt);
            }
            System.out.println("");
        }

        /////////////////////////////////
        // n维存在稀疏有效数据的数组，都可以转换为2维的稀疏数组来存储数据，以达到压缩空间的目的
        // 如下 5维10长度数组，共需要 10 * 10 * 10 * 10 * 10 = 100000个空间= 100000 * 2 byte = 200000 byte / 1024 = 195KB
        // 假设只存储了5个有效值
        // 而转为 6 * 6的稀疏矩阵只需要 36 byte空间可以存储。

        // 稀疏矩阵的结构=2维度数组[有效值个数+1][原数组维度+1]


        System.out.println("\n-----------n维数组-----------");
        // n维数组,假设n=5
        int[][][][][] nInts = new int[10][10][10][10][10];
        // 假设只有5个有效值
        for (int i = 0; i <= 5; i++) {
            nInts[i][i][i][i][i] = i;
            System.out.printf("-----> %d %s \n ", i, i);
        }

        System.out.println("nInts = " + 10 * 10 * 10 * 10 * 10);

        // 转为稀疏数组
        int[][] spareArrForN = new int[6][6];
        spareArrForN[0][0] = 10;
        spareArrForN[0][1] = 10;
        spareArrForN[0][2] = 10;
        spareArrForN[0][3] = 10;
        spareArrForN[0][4] = 10;
        spareArrForN[0][5] = 5;
        int rowIndex2 = 1;
        for (int i = 0; i < nInts.length; i++) {
            for (int i1 = 0; i1 < nInts[i].length; i1++) {
                for (int i2 = 0; i2 < nInts[i][i1].length; i2++) {
                    for (int i3 = 0; i3 < nInts[i][i1][i2].length; i3++) {
                        for (int i4 = 0; i4 < nInts[i][i1][i2][i3].length; i4++) {
                            if(nInts[i][i1][i2][i3][i4] != 0) {
                                System.out.printf("----->[%d %d %d %d %d] %s \n", i, i1, i2, i3, i4, nInts[i][i1][i2][i3][i4]);
                                spareArrForN[rowIndex2][0] = i;
                                spareArrForN[rowIndex2][1] = i1;
                                spareArrForN[rowIndex2][2] = i2;
                                spareArrForN[rowIndex2][3] = i3;
                                spareArrForN[rowIndex2][4] = i4;
                                spareArrForN[rowIndex2][5] = nInts[i][i1][i2][i3][i4];
                                rowIndex2 ++;
                            }
                        }
                    }
                }
            }
        }

        // 输出
        System.out.println("\n----------------------");
        for (int[] intN : spareArrForN) {
            for (int anInt : intN) {
                System.out.printf("%d\t", anInt);
            }
            System.out.println("");
        }

    }
}