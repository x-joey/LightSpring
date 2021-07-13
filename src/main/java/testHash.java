import org.junit.Test;

import java.util.*;

public class testHash {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>();

        for(int i = 0;i<1000000;i++){
            list.add(i);
        }

        long startTime = System.currentTimeMillis();
        int xx =0;
        for (int i = 0; i < list.size(); i++) {
            xx += list.get(i);
        }
        System.out.println("普通for循环耗时： " + (System.currentTimeMillis() - startTime));


        startTime = System.currentTimeMillis();
        xx =0;
        for (Integer itr : list) {
            xx += itr;
        }
        System.out.println("增强for循环耗时： " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        Iterator<Integer> iterator = list.iterator();
        xx =0;
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            xx += next;
        }
        System.out.println("iterator耗时： " + (System.currentTimeMillis() - startTime));

//        startTime = System.currentTimeMillis();
//        int xx2 = list.stream().mapToInt(integer -> integer).sum();
//        System.out.println("循环耗时： " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        xx=0;
        xx += list.stream().mapToInt(integer -> integer).sum();
        System.out.println("stream耗时： " + (System.currentTimeMillis() - startTime));

    }

    //测试LinkedList四种初始化方式
    public void testLinkedListInit(){
        //链表初始化的四种方式
        ///1
        LinkedList<String> list01 = new LinkedList<String>();
        list01.add("xu");
        list01.add("jun");
        list01.add("wei");
        System.out.println(list01);
        ///2
        LinkedList<String> list02 = new LinkedList<String>(Arrays.asList("shuai","ku","headsome"));
        System.out.println(list02);
        //3
        LinkedList<String> list03 = new LinkedList<String>(Collections.nCopies(10,"nice"));
        System.out.println(list03);
        ///4 内部类
        LinkedList<String> list04 = new LinkedList<String>(){
            {add("a");add("b");}
        };
        System.out.println(list04);
    }

    //测试hash
    public void testHash(){
        // 初始化一组字符串
        List<String> list = new ArrayList();
        list.add("jlkk");
        list.add("lopi");
        list.add("小傅哥");
        list.add("e4we");
        list.add("alpo");
        list.add("yhjk");
        list.add("plop");
// 定义要存放的数组
        String[] tab = new String[8];
// 循环存放
        for (String key : list) {
            int idx = key.hashCode() & (tab.length - 1); // 计算索引位置
            System.out.println(String.format("key 值=%s Idx=%d", key, idx));
            if (null == tab[idx]) {
                tab[idx] = key;
                continue;
            }
            tab[idx] = tab[idx] + "->" + key;
        }
// 输出测试结果
        System.out.println(tab.toString());
    }
}

