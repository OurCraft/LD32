package org.oc.ld32.gui.editor

import org.lengine.maths.Quaternion
import org.lengine.render.{RenderEngine, Sprite, TextureRegion}
import org.lwjgl.input.Keyboard
import org.oc.ld32.Game
import org.oc.ld32.gui.BaguetteGui
import org.oc.ld32.input.gamepad.Controls

class GuiTextField(var x: Float, var y: Float, var w: Float = 400f, var h: Float = 36f) extends BaguetteGui{

  val backgroundLayer0 = new Sprite("assets/textures/blank.png", new TextureRegion, new Quaternion(0.75f,0.75f,0.75f,1))
  val backgroundLayer1 = new Sprite("assets/textures/blank.png", new TextureRegion, new Quaternion(0,0,0,1))
  var text = ""
  var mouseOn = false
  var focused = false
  var lastTyped = 0f

  backgroundLayer0.width = w
  backgroundLayer0.height = h

  backgroundLayer1.width = w-2
  backgroundLayer1.height = h-2

  backgroundLayer0.setPos(x, y)
  backgroundLayer1.setPos(x+1, y+1)


  override def render(delta: Float): Unit = {
    backgroundLayer0.render(delta)
    backgroundLayer1.render(delta)
    Game.fontRenderer.renderString(text, x+4, y+h/2f-8f, 0xFFFFFFFF)
    if(focused) {
      if(RenderEngine.time.toInt % 2 == 0) {
        Game.fontRenderer.renderString("_", x+4+Game.fontRenderer.getWidth(text), y+h/2f-8f, 0xFFFFFFFF)
      }
    }
  }

  def isAllowed(char: Char): Boolean = {
    (char >= '0' && char <= '9') || (char >= 'a' && char <= 'z') || (char >= 'A' && char <= 'Z') || char == ''' || char == '_' || char == '.' || char == '&' || char == ' ' || char == '-' || char == '"' || char == '#' || char == '+' || char == '(' || char == ')' || char == '!'
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    if(focused) {
      if(RenderEngine.time - lastTyped > 1f/50f) {
        // avoid registering the same event multiple times
        lastTyped = RenderEngine.time
        if (isAllowed(char)) {
          text += char
        } else if (keyCode == Keyboard.KEY_BACK) {
          if (!text.isEmpty) {
            text = text.substring(0, text.length - 1)
          }
        }
      }
    }
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
      if(!mouseOn)
        focused = false
    }
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    focused = true
  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {
    if(!mouseOn)
      focused = false
  }
}
