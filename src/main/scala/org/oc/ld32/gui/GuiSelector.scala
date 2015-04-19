package org.oc.ld32.gui

import org.lengine.render.RenderEngine
import org.lwjgl.input.Keyboard
import org.oc.ld32.Game
import org.oc.ld32.gui.action.ActionHandler
import org.oc.ld32.input.gamepad.Controls
import org.oc.ld32.input.keyboard.KeyControls

class GuiSelector(val options: java.util.ArrayList[String], x: Int, y: Int) extends BaguetteGui {
  
  var selectedIndex: Int = 0
  var shouldRender = true
  var lastMoved = 0f

  def render(delta: Float): Unit = {
    if (shouldRender) {
      for (i <- 0 until options.size()) {
        if (selectedIndex == i) fontRenderer.renderString("> " + options.get(i), x, y - i * 30, 0xFFFFFFFF)
        else fontRenderer.renderString(options.get(i), x, y - i * 30, 0xFFFFFFFF)
      }
    }
  }

  override def onKeyReleased(keyCode: Int, char: Char): Unit = {
    println(s"$keyCode")
    if(keyCode == KeyControls.down) {
      selectedIndex += 1
      if (selectedIndex == options.size) selectedIndex = 0
    }

    if(keyCode == KeyControls.up) {
      selectedIndex -= 1
      if (selectedIndex < 0) selectedIndex = options.size - 1
    }

    if(keyCode == KeyControls.confirm) {
      Game.currentGui match {
        case handler: ActionHandler => handler.onAction(this, selectedIndex)
        case _ =>
      }
    }

  }

  override def onButtonReleased(button: Int) = {
    if(button == Controls.confirm) {
      Game.currentGui match {
        case handler: ActionHandler => handler.onAction(this, selectedIndex)
        case _ =>
      }
    }
  }

  override def onAxisMoved(value: Float, index: Int) = {
    val threshold = 0.98f
    if(Math.abs(value) > threshold && RenderEngine.time - lastMoved > 1f/20f) {
      lastMoved = RenderEngine.time
      println(s"$value $index")
      if(index == Controls.moveY) {
        if (value < 0f) {
          selectedIndex -= 1
          if (selectedIndex < 0) selectedIndex = options.size - 1
        } else {
          selectedIndex += 1
          if (selectedIndex == options.size) selectedIndex = 0
        }
      }

      if(index == Controls.lookY) {
        if (value < 0f) {
          selectedIndex -= 1
          if (selectedIndex < 0) selectedIndex = options.size - 1
        } else {
          selectedIndex += 1
          if (selectedIndex == options.size) selectedIndex = 0
        }
      }
    }
  }
}
