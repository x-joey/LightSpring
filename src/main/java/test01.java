import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.concurrent.DelayQueue;

public class test01 {
    public static void main(String[] args) {
        int nums[] = {777,14,8,1};

        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int num:nums){
            System.out.println(num);
        }

    }
}

