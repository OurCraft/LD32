package org.oc.ld32.level

import org.lengine.render.{TextureRegion, Sprite, Texture}

class FloorDecoration(val id: String, val x: Float, val y: Float, val width: Float, val height: Float) {

  val texture = new Texture(s"assets/textures/levels/ground/$id.png")
  val region: TextureRegion = new TextureRegion(0,0,width/texture.getWidth, height/texture.getHeight)
  val sprite = new Sprite(texture, region)
  sprite.setPos(x,y)

  def render(delta: Float): Unit = {
    sprite.render(delta)
  }
}
