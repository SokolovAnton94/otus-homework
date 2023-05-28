package ru.otus.homework.anton.sokolov.box;

import ru.otus.homework.anton.sokolov.fruit.Fruit;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private final List<T> fruits = new ArrayList<>();

    public void put(T fruit) {
        fruits.add(fruit);
    }

    public int weight() {
        return fruits.stream().mapToInt(Fruit::getWeight).sum();
    }

    public boolean compare(Box<? extends Fruit> box) {
        return this.weight() == box.weight();
    }

    public void pourOver(Box<? super T> box) {
        if (box == null) {
            System.out.println("Fruit will not be spilled. Box must be not null");
            return;
        }
        if (this == box) {
            return;
        }
        List<T> copyFruits = List.copyOf(fruits);
        fruits.clear();
        box.fruits.addAll(copyFruits);
    }
}
