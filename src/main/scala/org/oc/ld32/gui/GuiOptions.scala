package org.oc.ld32.gui

import org.lengine.render.RenderEngine
import org.oc.ld32.input.keyboard.KeyControls

class GuiOptions extends GuiScreen {
  var buttonUp: GuiButton = _

  override def init(): Unit = {
    buttonUp = new GuiButton("UP - " + KeyControls.up, 10, RenderEngine.displayHeight - 60)
    buttonUp.setHandler(button => {
      println("up")
    })
    elements.add(buttonUp)
  }

  override def renderScreen(delta: Float): Unit = {
    buttonUp.render(delta)

    fontRenderer.renderString("Press 'esc' to save and return to the main menu", 10, RenderEngine.displayHeight - 20)
  }
}
