package org.oc.ld32.level

import org.lengine.render.{TextureRegion, Sprite, Texture}

class FloorDecoration(val id: String, x: Float, y: Float, width: Float, height: Float) {

  val texture = new Texture(s"assets/textures/levels/ground/$id.png")
  val region: TextureRegion = new TextureRegion(0,0,width/texture.getWidth, height/texture.getHeight)
  val sprite = new Sprite(texture, region)
  sprite.setPos(x,y)

  def render(delta: Float): Unit = {
    sprite.render(delta)
  }
}
