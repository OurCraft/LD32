package org.c1.ld32

import org.lengine.entity.Entity

abstract class BaguetteEntity extends Entity {
  val boundingBox: AABB = new AABB(0,0,16,16)
}
