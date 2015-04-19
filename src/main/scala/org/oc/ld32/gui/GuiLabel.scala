package org.oc.ld32.gui

class GuiLabel(text: String, x: Float, y: Float, color: Int = 0xFFFFFFFF) extends BaguetteGui {
  def render(delta: Float): Unit = {
    fontRenderer.renderString(text, x, y, color)
  }
}
