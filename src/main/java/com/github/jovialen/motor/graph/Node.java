package com.github.jovialen.motor.graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Node<T extends Node<T>> {
    private final T parent;
    private final List<T> children = new ArrayList<>();

    public Node(T parent) {
        this.parent = parent;
    }

    public T getParent() {
        return parent;
    }

    public <U> U getParent(Class<U> uClass) {
        return getParent(uClass, false);
    }

    @SuppressWarnings("unchecked")
    public <U> U getParent(Class<U> uClass, boolean mustBeImmediate) {
        if (mustBeImmediate) {
            return nodeMatchesClass(parent, uClass) ? (U) parent : null;
        }

        T current = parent;
        while (current != null && !nodeMatchesClass(current, uClass)) {
            current = current.getParent();
        }

        return (U) current;
    }

    public <U> List<U> getParents(Class<U> uClass) {
        return getParents(uClass, Integer.MAX_VALUE);
    }

    public <U> List<U> getParents(Class<U> uClass, int maxDepth) {
        return getParents(uClass, maxDepth, false);
    }

    public <U> List<U> getParents(Class<U> uClass, int maxDepth, boolean breakOnOther) {
        List<U> parents = new ArrayList<>();
        U current = getParent(uClass, breakOnOther);
        while (current != null) {
            parents.add(current);
            current = getParent(uClass, breakOnOther);
        }
        parents.add(current);
        return parents;
    }

    public List<T> getChildren() {
        return children;
    }

    public <U> List<U> getChildren(Class<U> uClass) {
        return getChildren(uClass, Integer.MAX_VALUE);
    }

    public <U> List<U> getChildren(Class<U> uClass, int maxDepth) {
        return getChildren(uClass, maxDepth, false, false);
    }


    public <U> List<U> getChildren(Class<U> uClass,
                                             int maxDepth,
                                             boolean breakOnOther,
                                             boolean breakOnMatch) {
        List<U> matches = new ArrayList<>();
        addChildrenOfClass(uClass, matches, maxDepth, breakOnOther, breakOnMatch);
        return matches;
    }

    public <U extends T> U addChild(U child) {
        children.add(child);
        return child;
    }

    public <U extends T> void removeChild(U child) {
        children.remove(child);
    }

    <U> void addChildrenOfClass(Class<U> uClass,
                                          List<U> matches,
                                          int maxDepth,
                                          boolean breakOnOther,
                                          boolean breakOnMatch) {
        if (maxDepth == 0) {
            return;
        }

        for (T child : children) {
            if (nodeMatchesClass(child, uClass)) {
                //noinspection unchecked
                matches.add((U) child);

                if (breakOnMatch) {
                    continue;
                }
            } else if (breakOnOther) {
                continue;
            }

            if (maxDepth > 1) {
                child.addChildrenOfClass(uClass,
                        matches,
                        maxDepth - 1,
                        breakOnOther,
                        false);
            }
        }
    }

    private <U> boolean nodeMatchesClass(T node, Class<U> uClass) {
        return uClass.isAssignableFrom(node.getClass());
    }
}
