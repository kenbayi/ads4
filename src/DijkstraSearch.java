import java.util.*;

public class DijkstraSearch<V> implements Search<V> {
    private Map<V, Double> distances = new HashMap<>();  // Stores the shortest distance from start to each vertex
    private Map<V, V> edgeTo = new HashMap<>();  // Stores the previous vertex on the shortest path
    private Set<V> visited = new HashSet<>();  // Tracks visited vertices
    private V start;

    public DijkstraSearch(WeightedGraph<V> graph, V start) {
        this.start = start;
        dijkstra(graph, start);
    }

    private void dijkstra(WeightedGraph<V> graph, V start) {
        PriorityQueue<V> queue = new PriorityQueue<>(Comparator.comparing(distances::get));  // Priority queue for Dijkstra's algorithm
        distances.put(start, 0.0);  // Distance to start is 0
        queue.add(start);

        while (!queue.isEmpty()) {
            V current = queue.poll();  // Get vertex with the smallest distance
            visited.add(current);  // Mark current vertex as visited

            for (Map.Entry<Vertex<V>, Double> entry : graph.getVertex(current).getAdjacentVertices().entrySet()) {
                V neighbor = entry.getKey().getData();  // Neighbor vertex
                double weight = entry.getValue();  // Weight of the edge to the neighbor

                if (visited.contains(neighbor)) continue;  // Skip if neighbor is already visited

                double newDist = distances.get(current) + weight;  // Calculate new distance

                // If new distance is shorter, update the distance and path
                if (newDist < distances.getOrDefault(neighbor, Double.POSITIVE_INFINITY)) {
                    distances.put(neighbor, newDist);
                    edgeTo.put(neighbor, current);
                    queue.add(neighbor);  // Add neighbor to the queue for further exploration
                }
            }
        }
    }

    @Override
    public List<V> pathTo(V key) {
        if (!distances.containsKey(key)) return null;  // No path found
        LinkedList<V> path = new LinkedList<>();
        for (V x = key; x != null; x = edgeTo.get(x)) {
            path.addFirst(x);  // Build the path in reverse order
        }
        return path;
    }
}
