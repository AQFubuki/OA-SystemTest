package com.fubuki.fubukioa;

import java.util.*;

public class Song {
    public static void main(String[] args) {
        List<String> songs = new LinkedList<>();
        songs.add("Do");
        songs.add("Re");
        songs.add("Mi");
        songs.add("Fa");
        songs.add("Sol");
        songs.add("La");
        songs.add("Ti");
        int n = 2;
        //System.out.println("请输入个数：");
        //Scanner scanner=new Scanner(System.in);
        // n=scanner.nextInt();
        Set<Integer> set = new LinkedHashSet<>();
        Random random = new Random();
        while (set.size() < n) {
            int num = random.nextInt(7);
            set.add(num);
        }
        String question1 = "";
        String question2 = "";
        for (Integer integer : set) {
            question1 += (integer + 1);
            question2 += songs.get(integer);
        }
        System.out.println(question1);
        System.out.println("_____________________" + question2);
    }
}
