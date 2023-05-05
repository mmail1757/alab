package com.example.alab;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Grouper {
    public interface NamedObject {
        public String getName();
    }

    class Item implements NamedObject {

        String name;

        public Item(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    List<Item> objects = Arrays.asList(new Item("item1"), new Item("item1"),
            new Item("item1"), new Item("item2"), new Item("item2"));

    public static void main(String[] args) {
        Grouper grouper = new Grouper();
        Map<String, List<NamedObject>> groups = grouper.objects.stream().collect(
        Collectors.groupingBy(NamedObject::getName, Collectors.toList()));
        System.out.println(groups);
    }
}
