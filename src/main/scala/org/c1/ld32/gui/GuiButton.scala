package org.c1.ld32.gui

import org.c1.ld32.Game
import org.c1.ld32.gui.action.ActionHandler
import org.lengine.render.{Texture, Sprite}
import org.lwjgl.input.Mouse

class GuiButton(text: String, x: Int, y: Int, width: Int = 200, height: Int = 50, texture: String = "assets/textures/gui/button.png") extends BaguetteGui {
  var sprite = new Sprite(new Texture(texture))
  sprite.setPos(x, y)

  def render(delta: Float): Unit = {
    sprite.render(delta)
    fontRenderer.renderString(text, x + width / 2 - text.length * 5, y + height / 2 - 6, if (hover) 0xff00ff else 0xffffff)
  }

  def hover: Boolean = {
    Mouse.getX >= x && Mouse.getX <= x + width && Mouse.getY >= y && Mouse.getY <= y + height
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    if (hover) Game.currentGui match {
      case handler: ActionHandler => handler.onAction(0)
      case _ =>
    }
  }
}