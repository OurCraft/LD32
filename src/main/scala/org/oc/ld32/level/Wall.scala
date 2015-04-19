package org.oc.ld32.level

import org.lengine.maths.Vec2f
import org.lengine.render.{Sprite, Texture, TextureRegion}
import org.oc.ld32.maths.AABB

class Wall(var id: String, val start: Vec2f, val end: Vec2f) {


  val x = (Math.min(start.x, end.x)/16f).toInt * 16f
  val y = (Math.min(start.y, end.y)/16f).toInt * 16f
  val w = Math.abs((Math.max(start.x, end.x)/16f).toInt * 16f - x)
  val h = Math.abs((Math.max(start.y, end.y)/16f).toInt * 16f - y)
  val boundingBox: AABB = new AABB(x,y,w,h)

  val vertical: Boolean = w < h

  val texture: Texture = new Texture(s"assets/textures/levels/walls.png")
  val sprite: Sprite = new Sprite(texture)
  sprite.setPos(x, y)


  def render(delta: Float): Unit = {
    if(vertical) {
      sprite.setAngle((Math.PI/2f).toFloat)
      val frequency: Int = Math.ceil(h/16f).toInt
      for(y <- 0 until frequency) {
        sprite.setPos(x, y*16f + this.y)
        sprite.render(delta)
      }
    } else {
      val frequency: Int = Math.ceil(w/16f).toInt +1
      for(x <- 0 until frequency) {
        sprite.setPos(x*16f+this.x, y)
        sprite.render(delta)
      }
    }
  }

}
