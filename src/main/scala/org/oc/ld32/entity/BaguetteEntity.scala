package org.oc.ld32.entity

import org.lengine.entity.Entity
import org.lengine.maths.Vec2f
import org.oc.ld32.maths.AABB

abstract class BaguetteEntity extends Entity {

  var boundingBox: AABB = _
  var lastX: Float = 0
  var lastY: Float = 0

  override def init: Unit = {
    boundingBox = new AABB(0,0,16,16)
  }

  def onCollide(other: BaguetteEntity): Unit = {

  }

  override def update(delta: Float): Unit = {
    lastX = getPos.x
    lastY = getPos.y
    boundingBox.x = getPos.x
    boundingBox.y = getPos.y
  }

  def moving: Boolean = {
    !movingDir.isNull()
  }

  def movingDir: Vec2f = {
    new Vec2f(getPos.x - lastX, getPos.y - lastY)
  }
}
