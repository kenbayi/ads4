import java.util.*;

public class BreadthFirstSearch<V> implements Search<V> {
    private final Set<V> visited = new HashSet<>();
    private final Map<V, V> edgeTo = new HashMap<>();
    private final V start;

    public BreadthFirstSearch(WeightedGraph<V> graph, V start) {
        this.start = start;
        bfs(graph, start); // Call the BFS method to traverse the graph
    }

    private void bfs(WeightedGraph<V> graph, V start) {
        Queue<V> queue = new LinkedList<>(); // Create a queue to store vertices to be visited
        queue.add(start); // Add the start vertex to the queue
        visited.add(start); // Mark the start vertex as visited

        while (!queue.isEmpty()) { // Continue until the queue is empty
            V current = queue.poll(); // Remove and get the front vertex from the queue
            for (Vertex<V> neighbor : graph.getVertex(current).getAdjacentVertices().keySet()) {
                V next = neighbor.getData(); // Get the data of the neighbor vertex
                if (!visited.contains(next)) { // Check if the neighbor has not been visited
                    edgeTo.put(next, current); // Record the edge from current to next
                    visited.add(next); // Mark the neighbor as visited
                    queue.add(next); // Add the neighbor to the queue for further exploration
                }
            }
        }
    }

    @Override
    public List<V> pathTo(V key) {
        if (!visited.contains(key)) return null; // If the key hasn't been visited, return null
        LinkedList<V> path = new LinkedList<>();
        for (V x = key; x != null; x = edgeTo.get(x)) {
            path.addFirst(x); // Construct the path by traversing backward from the key using edgeTo map
        }
        return path; // Return the constructed path
    }
}
