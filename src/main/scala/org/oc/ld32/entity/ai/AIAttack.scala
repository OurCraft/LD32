package org.oc.ld32.entity.ai

import org.lengine.render.RenderEngine
import org.oc.ld32.entity.EntityEnemy

class AIAttack(priority: Int, owner: EntityEnemy) extends AITask(priority, owner) {

  var lastAttackTime = 0f

  override def shouldContinue: Boolean = {
    owner.target != null
  }

  override def canExecute: Boolean = {
    owner.target != null
  }

  override def perform(delta: Float): Unit = {
    val targetPos = owner.target.getPos + (owner.target.boundingBox.width/2f, owner.target.boundingBox.height/2f)
    val pos = owner.getPos + (owner.boundingBox.width/2f, owner.boundingBox.height/2f)
    val dist = ~(targetPos - pos)
    if(dist < owner.getAttackRange()*2f && RenderEngine.time - lastAttackTime >= 1f/2f) {
      lastAttackTime = RenderEngine.time
      owner.attack()
    }
  }

  override def reset: Unit = {
    lastAttackTime = 0f
  }
}
