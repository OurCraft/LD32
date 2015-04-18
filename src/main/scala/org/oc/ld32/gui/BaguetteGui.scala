package org.oc.ld32.gui

import org.lengine.render.{FontRenderer, Texture, TextureAtlas}

abstract class BaguetteGui {
  var fontRenderer = new FontRenderer(new TextureAtlas(new Texture("assets/textures/font.png"), 16, 16))

  def render(delta: Float): Unit

  def onMousePressed(x: Int, y: Int, button: Int): Unit = {

  }

  def onMouseReleased(x: Int, y: Int, button: Int): Unit = {

  }

  def onKeyReleased(keyCode: Int, char: Char): Unit = {

  }

  def onKeyPressed(keyCode: Int, char: Char): Unit = {

  }

  def onMouseMove(x: Int, y: Int, dx: Int, dy: Int) = {

  }

  def onScroll(x: Int, y: Int, dir: Int): Unit = {

  }

  implicit def toTexture(texture: String): Texture = {
    new Texture(texture)
  }
}
