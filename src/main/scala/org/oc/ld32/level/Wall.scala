package org.oc.ld32.level

import org.lengine.maths.Vec2f
import org.lengine.render.{TextureRegion, Sprite, Texture}
import org.oc.ld32.maths.AABB

class Wall(val start: Vec2f, val end: Vec2f) {

  val x = Math.min(start.x, end.x)
  val y = Math.min(start.y, end.y)
  val w = Math.max(start.x, end.x) - x
  val h = Math.max(start.y, end.y) - y
  val boundingBox: AABB = new AABB(x,y,w,h)

  private val vertical: Boolean = w < h

  private val levelTexture: Texture = new Texture("assets/textures/level.png")
  private val leftBound: Sprite = new Sprite(levelTexture, new TextureRegion(48f/256f,0,(16f+48f)/256f,16f/256f))
  private val rightBound: Sprite = new Sprite(levelTexture, new TextureRegion(16f/256f,0,(16f+16f)/256f,16f/256f))
  private val upBound: Sprite = new Sprite(levelTexture, new TextureRegion(0,0,16f/256f,16f/256f))
  private val downBound: Sprite = new Sprite(levelTexture, new TextureRegion(32f/256f,0,(16f+32f)/256f,16f/256f))
  private val straightHorizontal: Sprite = new Sprite(levelTexture, new TextureRegion(64f/256f,0,(16f+64f)/256f,16f/256f))
  private val straightVertical: Sprite = new Sprite(levelTexture, new TextureRegion(80f/256f,0,(16f+80f)/256f,16f/256f))

  def render(delta: Float): Unit = {
    if(vertical) {
      val frequency: Int = Math.ceil(h/16f).toInt
      for(y <- 0 until frequency) {
        var sprite: Sprite = null
        if(y == 0) {
          sprite = downBound
        } else if(y == frequency-1) {
          sprite = upBound
        } else {
          sprite = straightVertical
        }
        sprite.setPos(x, y*16f + this.y)
        sprite.render(delta)
      }
    } else {
      val frequency: Int = Math.ceil(w/16f).toInt +1
      for(x <- 0 until frequency) {
        var sprite: Sprite = null
        if(x == 0) {
          sprite = leftBound
        } else if(x == frequency-1) {
          sprite = rightBound
        } else {
          sprite = straightHorizontal
        }
        sprite.setPos(x*16f+this.x, y)
        sprite.render(delta)
      }
    }
  }

}
