# Shortest Way
This Java program is designed to solve a pathfinding problem in a graph that includes wormholes, representing shortcuts that allow instantaneous travel between two points, alongside regular routes that take time to traverse. The graph is undirected, with cities as vertices and roads (or wormholes) as edges. Each road has an associated travel time, whereas wormholes permit instant travel, effectively making their travel time zero.

The core functionality is to determine the shortest possible time to travel from a starting city to a destination city, given a limited number of wormholes. This problem is an extension of the classic Dijkstra's algorithm for shortest paths, accommodating the additional complexity introduced by wormholes.

The program implements several key components:
* `Node class:` Represents a state in the search, including the current city, the total distance traveled so far, and the number of wormholes left.
* `Edge class:` Represents a connection between two cities and includes the travel time.
* `ShortPath function:` Calculates the shortest path using a priority queue to manage the exploration of paths, considering both the distance traveled and the wormholes used. This function incorporates a variation of Dijkstra's algorithm, modified to account for wormholes.
* `buildPath function:` Constructs the actual path taken from the starting city to the destination city by backtracking through the cities visited.
* `TotalTime function:` Computes the total time taken to travel the path found, summing up the time for each leg of the journey.

Upon execution, the program reads input from a file named "in.txt", which specifies the number of cities, routes, wormholes, and details of each route. The output is the shortest possible travel time and the sequence of cities along the path, demonstrating an efficient route planning that leverages both conventional roads and wormholes.
