package org.oc.ld32.entity

import org.lengine.entity.Entity
import org.oc.ld32.maths.AABB

abstract class BaguetteEntity extends Entity {

  var boundingBox: AABB = _

  override def init(): Unit = {
    boundingBox = new AABB(0,0,16,16)
  }

  def onCollide(other: BaguetteEntity): Unit = {

  }
}
