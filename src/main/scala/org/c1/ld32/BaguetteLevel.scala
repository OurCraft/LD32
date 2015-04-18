package org.c1.ld32

import org.lengine.level.Level
import java.util.{List, ArrayList}

class BaguetteLevel extends Level {

  val walls: List[Wall] = new ArrayList[Wall]

  override def render(delta: Float): Unit = {
    super.render(delta)
    // TODO: Render walls
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
