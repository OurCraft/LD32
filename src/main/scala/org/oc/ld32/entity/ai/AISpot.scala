package org.oc.ld32.entity.ai

import org.lengine.maths.Vec2f
import org.oc.ld32.Game
import org.oc.ld32.entity.{EntityPlayer, EntityEnemy}
import org.oc.ld32.level.BaguetteLevel
import org.oc.ld32.maths.AABB

class AISpot(priority: Int, entity: EntityEnemy) extends AITask(priority, entity) {

  var countdown = 0f

  override def shouldContinue: Boolean = !Game.player.isDead()

  override def canExecute: Boolean = !Game.player.isDead()

  def hasObstacleBetween(source: EntityEnemy, destination: EntityPlayer): Boolean = {
    val a = source.getPos
    val b = destination.getPos
    val maxDist = ~(a-b)
    val dir = (b-a).norm() * 8f
    val currentPos = a
    val testBox = new AABB(a.x-0.5f, a.y-0.5f, 1f, 1f)
    val lvl = source.level.asInstanceOf[BaguetteLevel]
    while(~(currentPos-a) > maxDist) {
      if(lvl.canGoTo(testBox, currentPos.x-4f, currentPos.y-4f)) {
        currentPos += dir
      } else {
        println(s"nope :( $currentPos")
        return true
      }
    }
    false
  }

  override def perform(delta: Float): Unit = {
    val player = Game.player
    val dir = entity.getPos - player.getPos
    val dist = ~dir
    if(dist <= 100f*64f) {
      val angle = (Math.atan2(dir.y, dir.x) + entity.getAngle) % (Math.PI*2f).toFloat - (Math.PI).toFloat
      if(Math.abs(angle) <= Math.PI/4f) {
        if(!hasObstacleBetween(entity, player)) {
          entity.target = player
          countdown = 3f
        } else {
          countdown -= delta
        }
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
