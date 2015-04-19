package org.oc.ld32.entity

import java.util
import java.util.{PriorityQueue, HashMap, List, ArrayList}

import org.lengine.maths.Vec2f
import org.oc.ld32.Game
import org.oc.ld32.entity.ai.PathNode

class AITrackPlayer(priority: Int, enemy: EntityEnemy) extends AITask(priority, enemy) {

  var path: List[PathNode] = new ArrayList

  override def shouldContinue: Boolean = {
    enemy.target != null
  }

  override def canExecute: Boolean = {
    enemy.target != null
  }

  def computeNeighbors(node: PathNode): List[PathNode] = {
    val list = new util.ArrayList[PathNode]
    list
  }

  def computeCost(a: PathNode, b: PathNode): Float = {
    0
  }

  def heuristic(goal: Vec2f, node: PathNode) = {
    Math.abs(goal.x - node.pos.x) + Math.abs(goal.y - node.pos.y)
  }

  override def perform(delta: Float): Unit = {

  }

  override def reset: Unit = {
    val start = enemy.getPos
    val end = Game.player.getPos
    /*
    frontier = PriorityQueue()
    frontier.put(start, 0)
    came_from = {}
    cost_so_far = {}
    came_from[start] = None
    cost_so_far[start] = 0

    while not frontier.empty():
       current = frontier.get()

       if current == goal:
          break

       for next in graph.neighbors(current):
          new_cost = cost_so_far[current] + graph.cost(current, next)
          if next not in cost_so_far or new_cost < cost_so_far[next]:
             cost_so_far[next] = new_cost
             priority = new_cost + heuristic(goal, next)
             frontier.put(next, priority)
             came_from[next] = current
     */
    val frontier = new PriorityQueue[PathNode]
    val startNode = new PathNode(0, start)
    frontier.add(startNode)
    val costSoFar = new HashMap[PathNode, Float]
    val cameFrom = new HashMap[PathNode, PathNode]
    cameFrom.put(startNode, null)
    costSoFar.put(startNode, 0)


    var goal: PathNode = null
    while(!frontier.isEmpty) {
      val current = frontier.poll()

      if(current.pos == end) {
        frontier.clear()
        goal = current
      } else {
        val neighbors = computeNeighbors(current)
        for(i <- 0 until neighbors.size) {
          val neighbor = neighbors.get(i)
          val newCost = costSoFar.get(startNode) + computeCost(current, neighbor)
          if(costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
            costSoFar.put(neighbor, newCost)
            val priority = newCost + heuristic(end, neighbor)
            frontier.add(new PathNode(priority, neighbor.pos))
            cameFrom.put(neighbor, current)
          }
        }
      }
    }

    val finalList = new util.ArrayList[PathNode]
    var current = goal
    while(current != null) {
      finalList.add(0,current)
      current = cameFrom.get(current)
    }

    path = finalList
  }
}
