package org.oc.ld32.render

import org.lengine.maths.{Mat4f, Vec2f}
import org.lengine.render.RenderEngine

class Camera {

  val pos: Vec2f = new Vec2f

  def setPos(x: Float, y: Float): Unit = {
    pos.set(x,y)
  }

  def setPos(o: Vec2f): Unit = {
    pos.set(o)
  }

  def update(): Unit = {
    RenderEngine.setTransformMatrix(new Mat4f().translation(-pos.x,-pos.y,0))
  }
}
