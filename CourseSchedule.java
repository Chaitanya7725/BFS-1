import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// TC: O(V + E) as all the vertices and edges are traversed
// SC: O(V + E) as Map and array are used to store the info of 
// vertices and edges but the size/length depends on the vertices and edges itself. 

// This is a topological sort problem in a graph data structure. There are two ways for maintaining the adjacency information
// 1) Adjacency matrix(As it can be mostly sparse, better avoid this but depends on situation) 2) Adjacency list(Map is used here)
public class CourseSchedule {
    public static void main(String[] args) {
        System.out.println(canFinish(2, new int[][] { { 1, 0 } })); // true
        System.out.println(canFinish(2, new int[][] { { 1, 0 }, { 0, 1 } })); // false
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0)
            return false;
        int[] indegrees = new int[numCourses];
        Map<Integer, List<Integer>> map = new HashMap<>();

        // Populating the indegrees array and map parallely.
        // Indegrees is used to see which course is independent.
        // For Adjacency list, map is populated
        for (int[] prerequisite : prerequisites) {
            indegrees[prerequisite[0]]++;
            if (!map.containsKey(indegrees[prerequisite[1]]))
                map.put(indegrees[prerequisite[1]], new ArrayList<>());
            map.get(indegrees[prerequisite[1]]).add(prerequisite[0]);
        }

        // maintaining the count variable to coint the number of insertion. This tells
        // us the number of courses which becomes independent eventually as the problem
        // is solved.
        int insertCount = 0;

        Queue<Integer> queue = new LinkedList<>();
        for (int i : indegrees) {
            if (i == 0) {
                insertCount++;
                queue.offer(i);
            }
        }

        // map is checked against the queue elements and indegrees count is reduced. If
        // it is 0 then offered in a queue.
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (map.containsKey(current)) {
                for (int list : map.get(current)) {
                    indegrees[list]--;
                    if (indegrees[list] == 0)
                        insertCount++;
                    queue.offer(indegrees[list]);
                }
            }
        }
        // Checking if insertion count and number of courses are same.
        return insertCount == numCourses;
    }
}
