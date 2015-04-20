package org.oc.ld32.gui

import java.util.ArrayList

import org.lengine.render.RenderEngine
import org.oc.ld32.Game
import org.oc.ld32.input.keyboard.KeyControls

import scala.collection.JavaConversions._

class GuiOptions extends GuiScreen {
  var buttonUp: GuiButton = _
  var buttonsList: java.util.List[GuiButton] = new ArrayList[GuiButton]()

  override def init(): Unit = {

    var y: Int = 80;
    for (id: String <- KeyControls.keys.keySet()) {
      buttonsList.add(new GuiOptionsButton(id, 10, RenderEngine.displayHeight - y))
      y += 80
    }

    elements addAll buttonsList

  }

  override def renderScreen(delta: Float): Unit = {
    for (button: GuiButton <- buttonsList) {
      button.render(delta)
    }

    fontRenderer.renderString("Press 'esc' to save and return to the main menu", 10, RenderEngine.displayHeight - 20)
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    val oldReturnKey: Int = KeyControls.pause;
    super.onKeyPressed(keyCode, char)
    if(keyCode == KeyControls.pause && oldReturnKey == KeyControls.pause)
    {
      Game.displayGuiScreen(new GuiMainMenu)
      return
    }


  }

}
