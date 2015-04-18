package org.c1.ld32

import org.lengine.level.Level
import java.util.{List, ArrayList}

class BaguetteLevel extends Level {

  val walls: List[Wall] = new ArrayList[Wall]

  override def render(delta: Float): Unit = {
    super.render(delta)
    // TODO: Render walls
  }

}
