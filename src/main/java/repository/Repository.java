package repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class Repository<T> {

    protected Class<T> elementType;

    protected List<T> elements;

    protected Repository(Class<T> elementType) {
        this.elementType = elementType;
        elements = new ArrayList<>();
    }

    /**
     * Returns the size of the repository.
     * @return the size of the repository
     */
    public int size() {
        return elements.size();
    }

    /**
     * Add elements to the repository.
     */
    public void add(T element) {
        elements.add(element);
    }

    /**
     * Remove elements from the repository.
     */
    public void remove(T element) {
        elements.remove(element);
    }

    /**
     * Delete all the elements from the repository.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Represents a list with the found elements.
     * @param predicate
     * @return
     */
    public List<T> find(Predicate<T> predicate) {
        return elements.stream().filter(predicate).toList();
    }

    /**
     * Represents a list with all the elements.
     */
    public List<T> findAll() {
        return Collections.unmodifiableList(elements);
    }

}