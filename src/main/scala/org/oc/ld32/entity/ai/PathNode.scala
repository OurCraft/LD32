package org.oc.ld32.entity.ai

import org.lengine.maths.Vec2f

class PathNode(var priority: Float, val pos: Vec2f) extends Comparable[PathNode] {
  override def compareTo(o: PathNode): Int = {
    java.lang.Float.compare(priority, o.priority)
  }

  override def hashCode(): Int = {
      val BASE: Int = 17
      val MULTIPLIER: Int = 31
      var result: Int = BASE
      result = MULTIPLIER * result + java.lang.Float.floatToRawIntBits(priority)
      result = MULTIPLIER * result + java.lang.Float.floatToRawIntBits(pos.x)
      result = MULTIPLIER * result + java.lang.Float.floatToRawIntBits(pos.y)
      result
  }

  override def equals(o: Any): Boolean = {
    if(o.isInstanceOf[PathNode]) {
      val other = o.asInstanceOf[PathNode]
      return other.pos == pos
    }
    false
  }
}
