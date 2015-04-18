package org.c1.ld32.level

import org.c1.ld32.maths.AABB
import org.lengine.maths.Vec2f

class Wall(val start: Vec2f, val end: Vec2f) {

  val x = Math.min(start.x, end.x)
  val y = Math.min(start.y, end.y)
  val w = Math.max(start.x, end.x) - x
  val h = Math.max(start.y, end.y) - y
  val boundingBox: AABB = new AABB(x,y,w,h)

  def render(delta: Float): Unit = {
    // TODO: Implement
  }

}
