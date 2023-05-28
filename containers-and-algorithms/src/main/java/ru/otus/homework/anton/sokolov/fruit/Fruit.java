package ru.otus.homework.anton.sokolov.fruit;

public abstract class Fruit {
    private final int weight;

    protected Fruit(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
