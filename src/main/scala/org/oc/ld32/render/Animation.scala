package org.oc.ld32.render

import org.lengine.maths.Transform
import org.lengine.render.{TextureAtlas, TextureRegion}

class Animation(val atlas: TextureAtlas, var speed: Float = 1f) {

  var rawIndex: Float = 0f
  var index: Int = _
  val transform: Transform = new Transform

  def update(delta : Float): Unit = {
    val amount = delta * speed
    rawIndex += amount
    rawIndex %= atlas.regions.length
    index = rawIndex.toInt
  }

  def render(delta: Float): Unit = {
    val toRender = atlas.sprites(index)
    toRender.setPos(transform.pos.x, transform.pos.y)
    toRender.setAngle(transform.angle)
    toRender.render(delta)
  }
}
