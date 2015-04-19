package org.oc.ld32.entity.ai

import java.util
import java.util.{ArrayList, HashMap, List, PriorityQueue}

import org.lengine.maths.Vec2f
import org.lengine.render.RenderEngine
import org.oc.ld32.Game
import org.oc.ld32.entity.EntityEnemy
import org.oc.ld32.level.BaguetteLevel
import org.oc.ld32.maths.AABB

class AITrackPlayer(priority: Int, enemy: EntityEnemy, speed: Float = 32f) extends AITask(priority, enemy) {

  var path: List[PathNode] = new ArrayList
  var currentIndex: Float = 0
  var lastUpdated = 0f

  override def shouldContinue: Boolean = enemy.target != null && !enemy.target.isDead()

  override def canExecute: Boolean = enemy.target != null && !enemy.target.isDead()

  def computeNeighbors(node: PathNode): List[PathNode] = {
    val list = new util.ArrayList[PathNode]
    val lvl = enemy.level.asInstanceOf[BaguetteLevel]
    val box = new AABB(node.pos.x, node.pos.y, enemy.boundingBox.width, enemy.boundingBox.height)
    for(i <- 0 until 9) {
      if(i != 4) { // the middle node is the current one
        var x: Float = i % 3 - 1
        var y: Float = i / 3 - 1
        x *= enemy.boundingBox.width/16f
        y *= enemy.boundingBox.height/16f
        val newBox = box.translate(x, y)
        if(lvl.canGoTo(newBox, newBox.x, newBox.y)) {
          list.add(new PathNode(0, new Vec2f(newBox.x, newBox.y)))
        }
      }
    }
    list
  }

  def computeCost(a: PathNode, b: PathNode): Float = {
    ~(a.pos - b.pos)
  }

  def heuristic(goal: Vec2f, node: PathNode) = {
    Math.abs(goal.x - node.pos.x) + Math.abs(goal.y - node.pos.y)
  }

  override def perform(delta: Float): Unit = {
    enemy.attack(enemy.target)

    if(RenderEngine.time - lastUpdated >= 0.5f) {
      lastUpdated = RenderEngine.time
      reset
    }

    if(!path.isEmpty) {
      if(currentIndex.toInt < path.size) {
        currentIndex += speed * delta
        if(currentIndex.toInt +1 < path.size) {
          val prev = path.get(currentIndex.toInt).pos
          val target = path.get(currentIndex.toInt + 1).pos
          val dir = prev - target
          enemy.setAngle(Math.atan2(dir.y, dir.x).toFloat)
          enemy.setPos(prev.lerp(target, currentIndex % 1f))
        }
      } else {
        path.clear
        currentIndex = 0f
      }
    }
  }

  override def reset: Unit = {
    currentIndex = 0

    val start = enemy.getPos
    val end = Game.player.getPos

    val frontier = new PriorityQueue[PathNode]
    val startNode = new PathNode(0, start)
    frontier.add(startNode)
    val costSoFar = new HashMap[PathNode, Float]
    val cameFrom = new HashMap[PathNode, PathNode]
    cameFrom.put(startNode, null)
    costSoFar.put(startNode, 0)


    var goal: PathNode = null

    val maxIterations = 100
    var iterations = 0
    while(!frontier.isEmpty) {
      val current = frontier.poll()
      if(~(current.pos - end) <= 5f) {
        frontier.clear()
        goal = current
      } else {
        val neighbors = computeNeighbors(current)
        for(i <- 0 until neighbors.size) {
          val neighbor = neighbors.get(i)
          val newCost = costSoFar.get(startNode) + computeCost(current, neighbor)
          if(!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
            costSoFar.put(neighbor, newCost)
            val priority = newCost + heuristic(end, neighbor)
            neighbor.priority = priority
            frontier.add(neighbor)
            cameFrom.put(neighbor, current)
          }
        }
      }

      iterations += 1

      if(iterations >= maxIterations)
      frontier.clear()

    }

    val finalList = new util.ArrayList[PathNode]
    var current = goal
    while(current != null) {
      val old = current
      current = cameFrom.get(current)
      if(finalList.contains(current)) // avoid infinites loops
        current = null

      finalList.add(0,old)
    }

    path = finalList
  }
}
