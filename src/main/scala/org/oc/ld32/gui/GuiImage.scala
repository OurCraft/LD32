package org.oc.ld32.gui

import org.lengine.render.{Sprite, TextureRegion, Texture}

class GuiImage(var x: Float, var y: Float, var w: Float, var h: Float, texture: Texture, region: TextureRegion = new TextureRegion) extends BaguetteGui {

  val sprite = new Sprite(texture, region)

  override def render(delta: Float): Unit = {
    sprite.setPos(x,y)
    sprite.width = w
    sprite.height = h
    sprite.render(delta)
  }
}
