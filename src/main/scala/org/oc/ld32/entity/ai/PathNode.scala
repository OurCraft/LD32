package org.oc.ld32.entity.ai

import org.lengine.maths.Vec2f

class PathNode(val priority: Float, val pos: Vec2f) extends Comparable[PathNode] {
  override def compareTo(o: PathNode): Int = {
    java.lang.Float.compare(priority, o.priority)
  }
}
