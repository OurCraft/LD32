package org.oc.ld32.entity.ai

import org.lengine.maths.Vec2f
import org.oc.ld32.entity.EntityEnemy
import org.oc.ld32.level.BaguetteLevel

class AIWander(priority: Int, entity: EntityEnemy) extends AITask(priority, entity) {

  var angle = 0f
  var countdown = 0f
  var direction: Vec2f = null

  override def shouldContinue: Boolean = {
    entity.target == null
  }

  override def canExecute: Boolean = {
    entity.target == null
  }

  override def perform(delta: Float): Unit = {
    countdown -= delta
    if(countdown < 0) {
      reset
    } else {
      val lvl = entity.level.asInstanceOf[BaguetteLevel]
      if(!lvl.canGoTo(entity.boundingBox, entity.getPos.x + direction.x, entity.getPos.y)) {
        direction.x = -direction.x
      }

      if(!lvl.canGoTo(entity.boundingBox, entity.getPos.x, entity.getPos.y + direction.y)) {
        direction.y = -direction.y
      }

      entity.setAngle((Math.PI+Math.atan2(direction.y, direction.x)).toFloat)
      if(lvl.canGoTo(entity.boundingBox, entity.getPos.x + direction.x, entity.getPos.y + direction.y)) {
        entity.setPos(new Vec2f(entity.getPos.x + direction.x, entity.getPos.y + direction.y))
      } else {
        reset
      }
    }
  }

  override def reset: Unit = {
    angle = (Math.random * 2f * Math.PI).toFloat
    entity.setAngle(angle)
    direction = new Vec2f(Math.cos(angle).toFloat, Math.sin(angle).toFloat)
    if(Math.random < 0.25) // Has a 1/4 probability to create a pause
      direction.set(0,0)
    val max = 5f.toInt
    val min = 1f.toInt
    countdown = (Math.random * (max-min) + min).toFloat
  }
}
