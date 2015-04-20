package org.oc.ld32.gui

import org.oc.ld32.Game
import org.oc.ld32.input.gamepad.Controls

class GuiButton(var text: String, var x: Float, var y: Float, var w: Float = 200f, var h: Float =  32f, var yOffset: Float = 8f) extends BaguetteGui {

  var mouseOn = false
  var focused = false
  var handler: (GuiButton) => Unit = null

  def getWidth(s: String): Float = {
    s.length * (16f-4f)
  }

  override def render(delta: Float): Unit = {
    var color = 0xFFFFFFFF
    if(mouseOn)
      color = 0xFFF9FF4C
    val textX = x + w/2f - getWidth(text)/2f
    val textY = y + yOffset
    Game.fontRenderer.renderString(text, textX, textY, color)
  }

  override def setCursorPos(cx: Float, cy: Float): Unit = {
    mouseOn = cx >= x && cx < x+w && cy >= y && cy < y+h
  }

  override def onButtonPressed(index: Int): Unit = {
    if(Controls.isConfirmButton(index))
      if(mouseOn) {
        focused = true
      }
  }

  override def onButtonReleased(index: Int): Unit = {
    if(Controls.isConfirmButton(index)) {
      if (focused) {
        if (handler != null) {
          handler.apply(this)
        }
      }
      focused = false
    }
}

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    if(mouseOn) {
      focused = true
    }
  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {
    if(focused) {
      if(handler != null) {
        handler.apply(this)
      }
    }
  }

  def setHandler(handler: (GuiButton) => Unit) = {
    this.handler = handler
  }
}
