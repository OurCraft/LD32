package org.oc.ld32.entity

import org.lengine.maths.Vec2f
import org.oc.ld32.Game

class AISpot(priority: Int, entity: EntityEnemy) extends AITask(priority, entity) {

  var countdown = 0f

  override def shouldContinue: Boolean = {
    true
  }

  override def canExecute: Boolean = {
    true
  }

  override def perform(delta: Float): Unit = {
    val player = Game.player
    val dir = entity.getPos + new Vec2f(entity.boundingBox.width/2f, entity.boundingBox.height/2f) - (player.getPos + new Vec2f(player.boundingBox.width/2f, player.boundingBox.height/2f))
    val dist = ~dir
    if(dist <= 10f*64f) {
      val angle = Math.atan2(dir.y, dir.x) - entity.getAngle
      if(Math.abs(angle) <= Math.PI/4f) {
        entity.target = player
        countdown = delta*60f
      } else {
        countdown -= delta
      }
    } else {
      countdown -= delta
    }

    if(countdown < 0) {
      countdown = 0f
      entity.target = null
    }
  }

  override def reset: Unit = {
    countdown = 0f
  }
}
