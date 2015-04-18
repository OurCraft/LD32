package org.c1.ld32.gui

class GuiLabel(text: String, x: Int, y: Int, color: Int = 0xffffff) extends BaguetteGui {
  def render(delta: Float): Unit = {
    fontRenderer.renderString(text, x, y, color)
  }
}
