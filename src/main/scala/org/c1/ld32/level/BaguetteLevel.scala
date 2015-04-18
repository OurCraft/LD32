package org.c1.ld32.level

import java.util.{ArrayList, List}
import org.c1.ld32.maths.AABB
import org.lengine.level.Level

class BaguetteLevel extends Level {

  val walls: List[Wall] = new ArrayList[Wall]

  override def render(delta: Float): Unit = {
    super.render(delta)

    val n: Int = walls.size
    for(i <- 0 until n) {
      val w: Wall = walls.get(i)
      w.render(delta)
    }
  }

  def canGoTo(aabb: AABB, x: Float, y: Float): Boolean = {
    val n: Int = walls.size
    val bbCopy: AABB = new AABB(x,y, aabb.width, aabb.height)
    for(i <- 0 until n) {
      val w: Wall = walls.get(i)
      if(w.boundingBox.collides(bbCopy)) {
        return false
      }
    }
    true
  }

}
