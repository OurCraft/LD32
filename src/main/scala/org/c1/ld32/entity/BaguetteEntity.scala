package org.c1.ld32.entity

import org.c1.ld32.maths.AABB
import org.lengine.entity.Entity

abstract class BaguetteEntity extends Entity {

  var boundingBox: AABB = _

  override def init(): Unit = {
    boundingBox = new AABB(0,0,16,16)
  }

  def onCollide(other: BaguetteEntity): Unit = {

  }
}
