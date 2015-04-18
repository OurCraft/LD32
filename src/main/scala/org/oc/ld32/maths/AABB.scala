package org.oc.ld32.maths

class AABB(var x: Float = 0, var y: Float = 0, var width: Float, var height: Float) {

  def collides(other: AABB): Boolean = {
    !(x > other.x + other.width
      || y > other.y + other.height
      || x+width < other.x
      || y+height < other.y
      )
  }
}
