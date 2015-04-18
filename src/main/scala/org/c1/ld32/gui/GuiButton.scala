package org.c1.ld32.gui

import org.lengine.render.{Texture, Sprite}
import org.lwjgl.input.Mouse

class GuiButton(text: String, x: Int, y: Int, width: Int = 10, height: Int = 10, texture: String = "assets/textures/gui/button.png") extends BaguetteGui {
  var sprite = new Sprite(new Texture(texture))
  sprite.setPos(x, y)

  def render(delta: Float): Unit = {
    sprite.render(delta)
    fontRenderer.renderString(text, x, y, if (hover) 0xff00ff else 0xffffff)
  }

  def hover: Boolean = {
    Mouse.getX >= x && Mouse.getX <= x + width && Mouse.getY >= y && Mouse.getY <= y + height
  }
}
