package org.oc.ld32.entity.ai

import org.oc.ld32.entity.EntityEnemy

class AIPatrol(priority: Int, entity: EntityEnemy, speed: Float = 0.5f) extends AITask(priority, entity) {

  val LEFT = 0
  val RIGHT = 1
  val UP = 2
  val DOWN = 3
  val list = List(DOWN, RIGHT, UP, LEFT)
  var patrolClockwise = false
  var index = 0
  var direction: Int = DOWN

  override def shouldContinue: Boolean = {
    entity.target == null
  }

  override def canExecute: Boolean = {
    entity.target == null
  }

  def nextDirection = {
    index += (if(patrolClockwise) -1 else 1)
    index %= list.length
    if(index < 0)
      index = list.length+index-1

    list(index) match {
      case LEFT => entity.setAngle(0)

      case RIGHT => entity.setAngle(Math.PI.toFloat)

      case UP => entity.setAngle((-Math.PI/2f).toFloat)

      case DOWN => entity.setAngle((Math.PI/2f).toFloat)
    }
  }

  override def perform(delta: Float): Unit = {
    list(index) match {
      case LEFT => {
        if(!entity.walkLeft(delta, speed)) {
          nextDirection
        }
      }

      case UP => {
        if(!entity.walkUp(delta, speed)) {
          nextDirection
        }
      }

      case RIGHT => {
        if (!entity.walkRight(delta, speed)) {
          nextDirection
        }
      }

      case DOWN => {
        if(!entity.walkDown(delta, speed)) {
          nextDirection
        }
      }
    }
  }

  override def reset: Unit = {

  }
}
