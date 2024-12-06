#include <iostream>
#include <vector>
#include <algorithm>
#include <limits.h>
using namespace std;

#define INF INT_MAX

void displayMatrix(vector<vector<int>> &matrix) 
{
    cout << "Adjacency Matrix: " << endl;
  for (auto &row : matrix)
    {
        for (int val : row)
          {
            if (val == INF)
              cout << "INF\t";
            else
              cout << val << "\t";
          }
        cout << endl;
    }
}

//Finding the shortest path using Dijkstra's algorithm
int dijkstra(vector<vector<int>> &graph, int start, vector<int> &path)
{
    int n = graph.size();
    vector<int> dist(n, INF);
    vector<bool> visited(n, false);
    vector<int> prev(n,-1);

   dist[start] = 0;

    for (int i = 0; i < n - 1; i++)
      {
        int u = -1;
        for (int j = 0; j < n; j++)
          {
            if (!visited[j] && (u == -1 || dist[j] < dist[u]))
            {
                u = j;
            }
          }
        visited[u] = true;

        for (int v = 0; v < n; v++)
          {
            if (!visited[v] && graph[u][v] != INF && dist[u] + graph[u][v] < dist[v])
            {
                dist[v] = dist[u] + graph[u][v];
                prev[v] = u;
            }
          }
      }

  int totalDistance = 0;
  int end = start;
  for (int i = 0; i < n; ++i) {
    if (dist[i] < INF && i != start){
      totalDistance += dist[i];
      end = i;
    }
  }

// to trace back the path
  path.clear();
  for (int at = end; at != -1; at = prev[at])
    path.push_back(at);
  reverse(path.begin(), path.end());
  return totalDistance;
}

// calculating the cost of a given trip
int calculateTripCost(const vector<vector<int>> &graph, const vector<int> &route)
{
  int cost = 0;
  for (size_t i = 1; i < route.size(); ++i)
    {
      cost += graph[route[i - 1]][route[i]];
    }
  return cost;
}

// finding the most low cost trip
void findMostLowCostTrips(const vector<vector<int>> &graph, const vector<int> &cities)
{
  vector<int> route = cities;
  int minCost = INF;
  vector<vector<int>> minRoute;

  do {
    int cost = calculateTripCost(graph, route);
    if (cost < minCost) {
      minCost = cost;
      minRoute.clear();
      minRoute.push_back(route);
    } else if (cost == minCost) {
      minRoute.push_back(route);
    }
  } while (next_permutation(route.begin(), route.end()));

  // to display the most low cost

  cout << "\nMost Low Cost Trips: " << endl;
  for (const auto &minRoute : minRoute) {
    for (int city : minRoute) {
      switch (city){
        case 0:
          cout << "Riverside ";
        break;
        case 1:
          cout << "Moreno Valley ";
        break;
        case 2:
          cout << "Perris ";
        break;
        case 3:
          cout << "Hemet ";
        break;
      }
        if (city != minRoute.back()) cout << "-> ";
    }
    cout << "\nTotal distance: " << minCost << " miles\n";
  }
}

//main function

int main()
{
// to determine the shortest path
    vector<vector<int>> graph = {
    {0, 16, 24, 33}, // Riverside
    {16, 0, 18, 26}, // Moreno Valley
    {24, 18, 0, 30}, // Perris
    {33, 26, 30, 0}  // Hemet
    };

  displayMatrix(graph);

  // to find the shortest path FROM riverside
  vector<int> path;
  int shortestPathCost = dijkstra(graph, 0, path);

  cout << "\nShortest path from Riverside: ";
  for (int city : path){
    switch (city){
      case 0:
        cout << "Riverside ";
        break;
      case 1:
        cout << "Moreno Valley ";
        break;
      case 2:
        cout << "Perris ";
        break;
      case 3:
        cout << "Hemet ";
        break;
    }
    if (city != path.back()) cout << "-> ";
  }
  cout << "\nTotal distance: " << shortestPathCost << " miles" << endl;

  // low cost trip
  vector<int> cities = {0, 1, 2, 3}; 
  findMostLowCostTrips(graph, cities);

  return 0;
}
