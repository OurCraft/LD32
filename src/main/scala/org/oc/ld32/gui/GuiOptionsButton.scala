package org.oc.ld32.gui

import org.lwjgl.input.Keyboard
import org.oc.ld32.Game
import org.oc.ld32.input.keyboard.KeyControls

class GuiOptionsButton(id: String, i: Int, i1: Int) extends GuiButton(id.toUpperCase + " - " + Keyboard.getKeyName(Integer.valueOf(KeyControls.keys.get(id))), i, i1)
{
  var activated = false


  override def render(delta: Float): Unit = {
    var color = 0xFFFFFFFF
    if(mouseOn || activated)
      color = 0xFFF9FF4C
    val textX = x + w/2f - getWidth(text)/2f
    val textY = y + 10f
    Game.fontRenderer.renderString(text, textX, textY, color)
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    if(mouseOn)
      activated = true
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    if(activated)
    {
      KeyControls.keys.remove(id)
      KeyControls.keys.put(id, keyCode.toString)
      text = id.toUpperCase + " - " + Keyboard.getKeyName(keyCode)
      KeyControls.saveConfig()
      KeyControls.reloadFromConfig()
      activated = false
    }

  }
}
