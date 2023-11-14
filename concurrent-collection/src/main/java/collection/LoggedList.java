package collection;

import collection.service.LogOperationService;
import lombok.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LoggedList<E> implements List<E>, AutoCloseable{
    private final List<E> list;
    private final LogOperationService logOperationService = new LogOperationService();

    public LoggedList(List<E> list) {
        this.list = list;
    }

    @Override
    public boolean add(E e) {
        synchronized (list) {
            list.add(e);
            logOperationService.logInsert(e);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        synchronized (list) {
            boolean result = list.remove(o);
            if (result) {
                logOperationService.logDelete(o);
            }
            return result;
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    @NonNull
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    @NonNull
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    @NonNull
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    @NonNull
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public void close() throws Exception {
        logOperationService.close();
    }
}
