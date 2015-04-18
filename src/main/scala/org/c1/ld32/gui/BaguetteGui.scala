package org.c1.ld32.gui

import org.lengine.render.{FontRenderer, Texture, TextureAtlas}

abstract class BaguetteGui {
  var fontRenderer = new FontRenderer(new TextureAtlas(new Texture("assets/textures/font.png"), 16, 16))

  def render(delta: Float): Unit

  def onMousePressed(x: Int, y: Int, button: Int): Unit = {

  }

  implicit def toTexture(texture: String): Texture = {
    new Texture(texture)
  }
}
