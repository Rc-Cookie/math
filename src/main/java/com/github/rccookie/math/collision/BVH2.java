package com.github.rccookie.math.collision;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import com.github.rccookie.math.Ray2;
import com.github.rccookie.math.Rect;
import com.github.rccookie.math.constFloat2;
import com.github.rccookie.math.constRect;
import com.github.rccookie.util.Arguments;
import com.github.rccookie.util.ListStream;
import com.github.rccookie.util.StepIterator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An axis-aligned rectangle based binary bounding volume hierarchy. A bounding volume
 * hierarchy is a tree structure to accelerate intersection and raycast queries against
 * many shapes.
 */
@SuppressWarnings("DuplicatedCode")
public class BVH2 implements Iterable<Shape2D> {

    private Node root = null;
    private final Map<Shape2D, Node> lookup = new HashMap<>();


    /**
     * Inserts the given shape into the bounding volume hierarchy.
     *
     * @param shape The shape to insert, must not yet be contained
     */
    public void insert(Shape2D shape) {
        Arguments.checkNull(shape, "shape");
        if(lookup.containsKey(shape))
            throw new IllegalArgumentException(shape + " is already contained in the BVH");

        Node newRoot = insert(new Node(shape), root);
        if(newRoot != null)
            root = newRoot;
    }

    private Node insert(Node obj, Node tree) {
        lookup.put(obj.primitive, obj);

        // Tree is empty -> new root
        if(tree == null)
            return obj;

        // Is the object outside all bounds?
        if(!tree.bounds.contains(obj.bounds)) {
            // Create a new bounds that contains the old root volume and the new bounds
            // and set it as new root
            return new Node(root, obj);
        }

        // Check that volume is not a leaf
        while(tree.primitive == null) {
            // The object is fully contained within the current volume. Test sub-volumes:
            boolean left = tree.left.bounds.contains(obj.bounds);
            boolean right = tree.right.bounds.contains(obj.bounds);
            if(left) {
                // If both sub-volumes contain the object, expand the smaller subtree to
                // balance the tree:
                if(right && tree.right.depth <= tree.left.depth)
                    tree = tree.right;
                else tree = tree.left;
                continue;
            }
            if(right) {
                // Only right volume contains bounds, continue there
                tree = tree.right;
                continue;
            }

            // None of the child volumes contains the object. Construct potential combined volumes
            // for left and right:
//            Rect lCombined = tree.left.bounds.containing(obj.bounds);
//            Rect rCombined = tree.right.bounds.containing(obj.bounds);
            // Which one would change less?
            float lInc = tree.left.depth; //lCombined.inner();// / lVolume.inner(); //height[leftChild];
            float rInc = tree.right.depth; //rCombined.inner();// / rVolume.inner(); //height[rightChild];

            Node combined;
            if(lInc < rInc) {
                // Insert into left node -> replace left node with new node
                combined = new Node(tree.left, obj);
                tree.left = combined;
            }
            else {
                // Insert into right node -> replace right node with new node
                combined = new Node(tree.right, obj);
                tree.right = combined;
            }
            combined.parent = tree;
            // One of the children may have grown in size
            tree.updateDepth();
            // The parent bounds are still correct, because it fully contains the object
            return null;
        }

        // Reached a volume of a primitive that fully contains this primitive. Insert a new volume identical to
        // the current volume into the tree and connect the two primitives to it
        Node combined = new Node(tree, obj);
        if(tree.parent.left == tree)
            tree.parent.left = combined;
        else tree.parent.right = combined;

        combined.parent = tree.parent;
        tree.parent = combined;
        combined.parent.updateDepth();
        return null;
    }

    /**
     * Removes the given shape from the bounding volume hierarchy.
     *
     * @param shape The shape to remove, must be present in the BVH
     */
    public void remove(Shape2D shape) {
        Node obj = lookup.get(Arguments.checkNull(shape, "shape"));
        if(obj == null)
            throw new IllegalArgumentException(shape + " is not contained in the BVH");

        // Was obj the root <=> BVH is now empty
        if(obj == root) {
            root = null;
            return;
        }

        // Parent exists, but should be removed: Sibling is now at the place of the parent
        Node sibling = obj.parent.left == obj ? obj.parent.right : obj.parent.left;

        // Was the parent the root?
        if(obj.parent == root) {
            // Sibling is new root, nothing else to do
            root = sibling;
            return;
        }

        // Set sibling as direct child of parent's parent
        sibling.parent = sibling.parent.parent;
        if(sibling.parent.left == obj.parent)
            sibling.parent.left = sibling;
        else sibling.parent.right = sibling;

        // Pack volumes upwards
        for(obj = sibling.parent; obj != null; obj = obj.parent)
            // Update volume, one child may have become smaller
            obj.bounds.setContaining(obj.left.bounds, obj.right.bounds);

        sibling.parent.updateDepth();
    }

    /**
     * Updates the given shape in the bounding volume hierarchy. This might be faster than removing and
     * re-inserting the shape into the BVH. If shapes contained in the BVH are changed, querying overlaps
     * or raycasting the BVH might yield incorrect results if the shapes have not been updated.
     *
     * @param shape The shape to update
     */
    public void update(Shape2D shape) {
        Node obj = lookup.get(Arguments.checkNull(shape, "shape"));
        if(obj == null)
            throw new IllegalArgumentException(shape + " is not contained in the BVH");
        update(obj);
    }

    /**
     * Replaces the given shape with the specified new shape. This might be significantly faster than
     * removing the old and inserting the new shape if the two shapes have very similar bounds.
     *
     * @param old The shape to remove, must be present in the BVH
     * @param now The shape to insert instead, must not yet be present in the BVH
     */
    public void replace(Shape2D old, Shape2D now) {
        Arguments.checkNull(old, "old");
        Arguments.checkNull(now, "now");
        if(!contains(old))
            throw new IllegalArgumentException(old + " is not contained in the BVH");
        if(old == now) return;
        if(contains(now))
            throw new IllegalArgumentException(now + " is already contained in the BVH");

        Node node = lookup.remove(Arguments.checkNull(old, "old"));
        node.primitive = now; // Bounds will be updated by update() method
        lookup.put(now, node);
        update(node);
    }

    private void update(Node obj) {

        constRect newBounds = obj.primitive.bounds();
        if(newBounds.equals(obj.bounds)) return;

        // Has the size decreased?
        boolean smaller = obj.bounds.contains(newBounds);
        obj.bounds.set(newBounds);
        if(obj == root) return;

        if(smaller) {
            for(obj = obj.parent; obj != null; obj = obj.parent)
                // Update volume, one child may have become smaller
                obj.bounds.setContaining(obj.left.bounds, obj.right.bounds);
        }
        else {
            // Parent exists, but should be removed: Sibling is now at the place of the parent
            Node node = obj.parent.left == obj ? obj.parent.right : obj.parent.left;

            if(node.parent == root) {
                // Sibling is now root, but it may fit the new bounds, so insert it regularly
                Node newRoot = insert(obj, node);
                // Root is now sibling unless it changed during the insertion
                root = newRoot != null ? newRoot : node;
            }
            else {
                // Disconnect parent; connect sibling directly to parent
                if(node.parent.parent.left == node.parent)
                    node.parent.parent.left = node;
                else node.parent.parent.right = node;
                node.parent = node.parent.parent;

                // Pack upwards until a big enough area is found
                for(node = node.parent; node != null; node = node.parent) {
                    node.bounds.setContaining(node.left.bounds, node.right.bounds);
                    if(node.bounds.contains(newBounds)) {
                        // Node does fit the new bounds, insert there
                        Node parent = node.parent;
                        Node n = insert(obj, node);
                        // Update parent if changed
                        if(n != null) {
                            if(parent == null) root = n;
                            else {
                                if(parent.left == node) parent.left = n;
                                else parent.right = n;
                                n.parent = parent;
                            }
                        }
                        return;
                    }
                }
                // Not contained in existing tree, create new branch from root
                root = new Node(root, obj);
            }
        }
    }

    /**
     * Returns whether the bounding volume hierarchy currently contains the given shape.
     *
     * @param shape The shape to test for
     * @return Whether the shape is contained in this BHV
     */
    public boolean contains(Shape2D shape) {
        return lookup.containsKey(shape);
    }

    /**
     * Returns the number of shape currently in this bounding volume hierarchy.
     *
     * @return The number of shapes in the BVH
     */
    public int size() {
        return lookup.size();
    }

    /**
     * Returns an iterator over the shapes in this bounding volume hierarchy.
     *
     * @return An iterator over the BVH's shapes
     */
    @NotNull
    @Override
    public Iterator<Shape2D> iterator() {
        Iterator<Shape2D> it = lookup.keySet().iterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Shape2D next() {
                return it.next();
            }
        };
    }

    /**
     * Returns the maximum depth of the bounding volume hierarchy, where a depth of n
     * means that there are somewhere n-1 intermediate nodes and a leaf node. The depth
     * is 0 iff the BVH is empty.
     *
     * @return The depth of the BVH tree
     */
    public int depth() {
        return root != null ? root.depth : 0;
    }

    /**
     * Queries all shapes in the bounding volume hierarchy which potentially match the given
     * filter.
     *
     * @param filter The filter for shapes to potentially fulfill to be returned. If the filter
     *               is true for a given shape, it must also be true for any rectangle that fully
     *               contains the shape
     * @return A lazily populated stream over all shapes that match the filter, and potentially
     *         shapes that do not match the filter
     */
    public ListStream<Shape2D> broadQuery(Predicate<? super constRect> filter) {
        if(root == null) return ListStream.empty();
        if(root.primitive != null)
            return filter.test(root.bounds) ? ListStream.of(root.primitive) : ListStream.empty();

        return ListStream.of((Iterator<Shape2D>) new StepIterator<Shape2D>() {
            BVH2.Node n = root;
            @Override
            protected Shape2D getNext() {
                if(n == null) return null;
                // Step at least to next one
                n = n.next();
                while(n != null) {
                    if(!filter.test(n.bounds))
                        // This subtree is irrelevant -> continue with right sibling or right sibling of a parent
                        n = n.next();
                    else if(n.primitive == null)
                        // This subtree is relevant -> continue with left child
                        n = n.left;
                    else return n.primitive; // Relevant and a leaf -> return
                }
                // No more relevant subtrees
                return null;
            }
        });
    }

    /**
     * Queries all shapes in the bounding volume hierarchy which potentially overlap the given shape.
     *
     * @param s The shape to find the overlapping shapes for
     * @return All overlapping shapes and potentially also non-overlapping shapes
     */
    public ListStream<Shape2D> broadQuery(Shape2D s) {
        return broadQuery(s::overlaps);
    }

    /**
     * Queries all shapes in the bounding volume hierarchy which match the given filter.
     *
     * @param filter The filter for shapes to fulfill to be returned. If the filter is true for a
     *               given shape, it must also be true for any rectangle that fully contains the shape
     * @return A lazily populated stream over all shapes that match the filter
     */
    public ListStream<Shape2D> query(Predicate<Shape2D> filter) {
        return broadQuery(filter).filter(filter);
    }

    /**
     * Queries all shapes in the bounding volume hierarchy which match the given filters.
     *
     * @param broadFilter The filter to do the broad query with. If the filter is true for a given
     *                    shape, it must also be trie for any rectangle that fully contains the shape
     * @param narrowFilter The filter to apply to all shapes that matched the broad filter. All non-null
     *                     results of this filter will be returned
     * @return A lazily populated stream over all non-null return values of the second filter which also
     *         matched the broad filter
     * @param <T> The return type of the narrow filter
     */
    public <T> ListStream<T> query(Predicate<? super constRect> broadFilter, Function<? super Shape2D, T> narrowFilter) {
        return broadQuery(broadFilter).map(narrowFilter).filter(Objects::nonNull);
    }

    /**
     * Queries all overlap manifolds between the given shape and the shapes in this bounding volume
     * hierarchy.
     *
     * @param shape The shapes to query contact manifolds with
     * @return A lazily populated stream over all contact manifolds between the shape and a shape from
     *         the BVH
     */
    public ListStream<Manifold2D> query(Shape2D shape) {
        return query(shape::contains, shape::contactWith);
    }

    /**
     * Queries all shapes that contain the given point.
     *
     * @param p The point to be contained
     * @return A lazily populated stream over all shapes that contain the given point
     */
    public ListStream<Shape2D> query(constFloat2 p) {
        return query(shape -> shape.contains(p));
    }

    /**
     * Queries all shapes that are fully contained within the specified shape.
     *
     * @param shape The shape for the returned shapes to be contained within
     * @return A lazily populated stream over all shapes that are contained within the given shape
     */
    public ListStream<Shape2D> queryContained(Shape2D shape) {
        return query(shape::overlaps, s -> shape.contains(s) ? s : null);
    }

    /**
     * Queries all shapes which intersect the given ray.
     *
     * @param ray The ray to be intersected
     * @param maxLength The maximum distance between ray origin and the intersection point
     * @return A lazily populated, <b>unordered</b> stream over all ray hits
     */
    public ListStream<Raycast2D> raycastAll(Ray2 ray, float maxLength) {
        return query(r -> r.intersects(ray, maxLength), s -> s.raycast(ray, maxLength));
    }

    /**
     * Returns the first intersection between the given ray and the shapes in this bounding volume
     * hierarchy which matches the specified filters.
     *
     * @param ray The ray to be intersected
     * @param maxLength The maximum distance between ray origin and the intersection point
     * @param broadFilter A filter to be applied to sort out potential hits early. If the filter returns
     *                    true for a given shape, it must also return true for and rectangle which fully
     *                    contains that shape
     * @param shapeFilter A filter to be applied to each shape which potentially intersects the ray,
     *                    before the raycast has been tested. Using this filter may be faster than first
     *                    performing the raycast calculation against the shape, if the filter itself is
     *                    computationally less expensive than the actual raycast test
     * @param hitFilter A filter that the returned ray hit must match
     * @return The ray intersection closest to the ray origin which matches all filters
     */
    public Raycast2D raycast(Ray2 ray, float maxLength, @Nullable Predicate<? super constRect> broadFilter, @Nullable Predicate<? super Shape2D> shapeFilter, @Nullable Predicate<? super Raycast2D> hitFilter) {
        if(root == null || root.bounds.intersection(ray) > maxLength) return null;
        return root.raycast(ray, maxLength, broadFilter, shapeFilter, hitFilter);
    }

    private static final class Node {

        @NotNull
        Rect bounds;
        Shape2D primitive;
        Node parent;
        Node left;
        Node right;
        int depth;

        Node(Shape2D primitive) {
            this.primitive = primitive;
            bounds = primitive.bounds().clone();
            depth = 1;
        }

        Node(Node left, Node right) {
            this.left = left;
            this.right = right;
            left.parent = right.parent = this;
            bounds = Rect.combining(left.bounds, right.bounds);
            depth = Math.max(left.depth, right.depth);
        }

        void updateDepth() {
            if(primitive != null) {
                depth = 0;
                return;
            }

            Node n = this;
            while(n != null) {
                if(n.left.depth >= n.depth)
                    n.depth = n.left.depth + 1;
                else if(n.right.depth >= n.depth)
                    n.depth = n.right.depth + 1;
                else return;
                n = n.parent;
            }
        }

        Node next() {
            Node n = this;
            while(true) {
                if(n.parent == null) return null;
                if(n.parent.left == this)
                    return n.parent.right;
                n = n.parent;
            }
        }

        Raycast2D raycast(Ray2 ray, float maxLength, @Nullable Predicate<? super constRect> broadFilter, @Nullable Predicate<? super Shape2D> shapeFilter, @Nullable Predicate<? super Raycast2D> hitFilter) {
            assert bounds.intersection(ray) <= maxLength;

            if(primitive != null) {
                if(shapeFilter != null && !shapeFilter.test(primitive)) return null;
                Raycast2D raycast = primitive.raycast(ray, maxLength);
                return raycast != null && (hitFilter == null || hitFilter.test(raycast)) ? raycast : null;
            }

            float lDist = left.bounds.intersection(ray);
            float rDist = right.bounds.intersection(ray);
            if(broadFilter != null) {
                if(lDist != Float.POSITIVE_INFINITY && !broadFilter.test(left.bounds))
                    lDist = Float.POSITIVE_INFINITY;
                if(rDist != Float.POSITIVE_INFINITY && !broadFilter.test(right.bounds))
                    rDist = Float.POSITIVE_INFINITY;
            }

            if(lDist <= rDist) {
                if(lDist > maxLength || lDist == Float.POSITIVE_INFINITY) return null;
                if(rDist > maxLength) return left.raycast(ray, maxLength, broadFilter, shapeFilter, hitFilter);
                return raycast(left, right, rDist, ray, maxLength, broadFilter, shapeFilter, hitFilter);
            }
            else {
                if(rDist > maxLength) return null;
                if(lDist > maxLength) return right.raycast(ray, maxLength, broadFilter, shapeFilter, hitFilter);
                return raycast(right, left, lDist, ray, maxLength, broadFilter, shapeFilter, hitFilter);
            }
        }

        private static Raycast2D raycast(Node near, Node far, float farMinDist, Ray2 ray, float maxLength, @Nullable Predicate<? super constRect> broadFilter, Predicate<? super Shape2D> shapeFilter, Predicate<? super Raycast2D> hitFilter) {
            // Begin by raycasting closer tree
            Raycast2D nearRaycast = near.raycast(ray, maxLength, broadFilter, shapeFilter, hitFilter);

            // If no hit in near tree, simply return possible hit in far tree
            if(nearRaycast == null)
                return far.raycast(ray, maxLength, broadFilter, shapeFilter, hitFilter);

            // If the near hit is closer than the bounds of the far tree, the near hit must be the closest
            if(nearRaycast.distance <= farMinDist) return nearRaycast;

            // Only search for hits closer than the already found hit
            Raycast2D farRaycast = far.raycast(ray, nearRaycast.distance, broadFilter, shapeFilter, hitFilter);
            // If hit is found it must be closer because of shorter maxLength
            return farRaycast != null ? farRaycast : nearRaycast;
        }
    }
}
