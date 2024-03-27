import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static class Node {
        int city;
        int distance;
        int tunnelLeft;

        Node(int city, int distance, int tunnelssLeft) {
            this.city = city;
            this.distance = distance;
            this.tunnelLeft = tunnelssLeft;
        }
    }

    static class Edge {
        int to;
        int time;

        Edge(int to, int time) {
            this.to = to;
            this.time = time;
        }
    }

    public static void main(String[] args) {
        try {
            File inputFile = new File("in.txt");
            Scanner scanner = new Scanner(inputFile);

            int maxCity = scanner.nextInt();
            int totalRoutes = scanner.nextInt();
            int wormholes = scanner.nextInt();

            scanner.nextLine();

            List<Edge>[] graph = new ArrayList[maxCity + 1];
            for (int i = 0; i <= maxCity; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < totalRoutes; i++) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                int time = scanner.nextInt();

                graph[from].add(new Edge(to, time));
                graph[to].add(new Edge(from, time));
            }

            int[] prevCities = new int[maxCity + 1];
            List<Integer> path = ShortPath(graph, 1, maxCity, wormholes, prevCities);
            if (path == null) {
                System.out.println("No path found");
            } else {
                int totalTime = TotalTime(graph, path);
                System.out.println(totalTime);
                System.out.println(String.join(" ", path.stream().map(Object::toString).toArray(String[]::new)));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error file");
        }
    }

    private static List<Integer> ShortPath(List<Edge>[] graph, int start, int end, int wormholes, int[] prevCities) {
        int[] distances = new int[graph.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        boolean[][] visited = new boolean[graph.length][wormholes + 1];

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        queue.add(new Node(start, 0, wormholes));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (currentNode.city == end) {
                return buildPath(prevCities, end);
            }

            if (visited[currentNode.city][currentNode.tunnelLeft]) {
                continue;
            }

            visited[currentNode.city][currentNode.tunnelLeft] = true;

            for (Edge edge : graph[currentNode.city]) {
                int nextCity = edge.to;
                int newDistance = currentNode.distance + edge.time;
                int newWormholesLeft = currentNode.tunnelLeft - (edge.time == 0 ? 1 : 0);

                if (newWormholesLeft >= 0 && newDistance < distances[nextCity]) {
                    distances[nextCity] = newDistance;
                    prevCities[nextCity] = currentNode.city;
                    queue.add(new Node(nextCity, newDistance, newWormholesLeft));
                }
            }
        }
        return null;
    }

    private static List<Integer> buildPath(int[] prevCities, int end) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int at = end; at != 0; at = prevCities[at]) {
            path.addFirst(at);
        }
        return path;
    }

    private static int TotalTime(List<Edge>[] graph, List<Integer> path) {
        int totalTime = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int from = path.get(i);
            int to = path.get(i + 1);
            for (Edge edge : graph[from]) {
                if (edge.to == to) {
                    totalTime += edge.time;
                    break;
                }
            }
        }
        return totalTime;
    }
}