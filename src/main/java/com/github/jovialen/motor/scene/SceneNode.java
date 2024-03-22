package com.github.jovialen.motor.scene;

import com.github.jovialen.motor.scene.renderer.SceneRenderer;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for any node in the scene graph.
 */
public class SceneNode {
    SceneRoot root;
    SceneRoot localRoot;
    SceneNode parent;
    final List<SceneNode> children = new ArrayList<>();

    /**
     * Called once when the node is added to the scene graph. This stage is not
     * called on the render thread.
     */
    public void start() {
        children.forEach(SceneNode::start);
    }

    /**
     * Called once between each frame to synchronize the renderer and scene
     * graph.
     * <br/><br/>
     * It is in this stage that any rendering resources should be created or
     * updated before the renderer accesses them. This is because this is the
     * only stage guaranteed to be called on the same thread as the renderer.
     */
    public void sync() {
        children.forEach(SceneNode::sync);
    }

    /**
     * Stage called whenever the renderer is making a render tree. It is in
     * this stage render commands should be recorded. To trigger it, call the
     * renderers invalidate function.
     * <br/><br/>
     * This function should be called as little as possible, to create as few
     * objects as possible that will last as long as possible.
     *
     * @param renderer Scene renderer
     */
    public void submitToRender(SceneRenderer renderer) {
        for (SceneNode child : children) {
            child.submitToRender(renderer);
        }
    }

    /**
     * Called once per frame before the process stage.
     */
    public void preProcess() {
        children.forEach(SceneNode::preProcess);
    }

    /**
     * Called once per frame.
     */
    public void process() {
        children.forEach(SceneNode::process);
    }

    /**
     * Called once per frame after the process stage.
     */
    public void postProcess() {
        children.forEach(SceneNode::postProcess);
    }

    /**
     * Called once when the node is removed from the scene graph.
     * <br/><br/>
     * This stage will be run on a thread that will take ownership of the
     * render context before calling it.
     */
    public void stop() {
        children.forEach(SceneNode::stop);
    }

    /**
     * Get the root of the entire scene graph.
     *
     * @return Root of the scene graph.
     */
    public SceneRoot getRoot() {
        return root;
    }

    public boolean isRoot() {
        return root == this || !hasParent();
    }

    /**
     * Get the root of the scene which this node was created from or in.
     *
     * @return Root of the local scene graph.
     */
    public SceneRoot getLocalRoot() {
        return localRoot;
    }

    public boolean isLocalRoot() {
        return localRoot == this;
    }

    public SceneNode getParent() {
        return parent;
    }

    /**
     * Get all parents of this node of the given class.
     *
     * @param tClass Desired class of the parent
     * @param breakOnOther Whether to continue past any parent not of tClass
     * @return All parents of the given class.
     */
    public <T extends SceneNode> List<T> getParentsOfClass(Class<T> tClass, boolean breakOnOther) {
        List<T> parents = new ArrayList<>();
        SceneNode current = this;
        while (current != null) {
            if (tClass.isAssignableFrom(current.getClass())) {
                parents.add((T) current);
            } else if (breakOnOther) {
                break;
            }
            current = current.getParent();
        }
        return parents;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public List<SceneNode> getChildren() {
        return children;
    }

    /**
     * Get all children of this node of the given class.
     *
     * @param tClass Desired class of the children.
     * @return All children of tClass
     */
    public <T extends SceneNode> List<T> getChildrenOfClass(Class<T> tClass) {
        return getChildrenOfClass(tClass, Integer.MAX_VALUE, false);
    }

    /**
     * Get all children of this node of the given class to the given depth.
     *
     * @param tClass Desired class of the children.
     * @param maxDepth How deep to search for children of the given class.
     * @param breakOnOther Whether to stop searching the tree below any child not of tClass
     * @return All children of the given path to the given depth.
     */
    public <T extends SceneNode> List<T> getChildrenOfClass(Class<T> tClass, int maxDepth, boolean breakOnOther) {
        List<T> children = new ArrayList<>();
        addChildrenOfClass(tClass, children, maxDepth, breakOnOther);
        return children;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Get all siblings of the node.
     *
     * @return Siblings of the node.
     */
    public List<SceneNode> getSiblings() {
        if (parent == null) {
            Logger.tag("SCENE").warn("Attempting to get siblings of root node");
            return null;
        }
        return parent.getChildren().stream()
                .filter((SceneNode sibling) -> sibling != this)
                .toList();
    }

    public boolean hasSiblings() {
        return !getSiblings().isEmpty();
    }

    /**
     * Add an instance of the given scene to the scene graph as a child of this node.
     *
     * @param localScene Scene to instantiate.
     * @return The root node of the scene instance.
     */
    public SceneNode addChild(Scene localScene) {
        Logger.tag("SCENE").debug("Instantiating local scene {} as child of {}", localScene, this);

        SceneRoot localRoot = localScene.instantiate(getRoot().getApplication());
        localRoot.parent = this;
        localRoot.root = root;
        localRoot.localRoot = localRoot;
        updateChildren();

        children.add(localRoot);

        localRoot.start();
        return localRoot;
    }

    /**
     * Add the given node as a child of this node.
     *
     * @param node Node to add to the scene graph.
     * @return Node added to the scene graph.
     */
    public <T extends SceneNode> T addChild(T node) {
        Logger.tag("SCENE").debug("Adding child {} to {}", node, this);

        node.root = root;
        node.localRoot = localRoot;
        node.parent = this;
        node.updateChildren();

        children.add(node);

        node.start();
        return node;
    }

    void updateChildren() {
        for (SceneNode child : children) {
            child.parent = this;
            child.root = root;
            child.localRoot = localRoot;
            child.updateChildren();
        }
    }

    private <T> void addChildrenOfClass(Class<T> tClass, List<T> children, int maxDepth, boolean breakOnOther) {
        if (maxDepth == 0) {
            return;
        }

        for (SceneNode child : this.children) {
            if (tClass.isAssignableFrom(child.getClass())) {
                T tChild = (T) child;
                children.add(tChild);
            } else if (breakOnOther) {
                continue;
            }

            if (maxDepth > 1) {
                child.addChildrenOfClass(tClass, children, maxDepth - 1, breakOnOther);
            }
        }
    }
}
