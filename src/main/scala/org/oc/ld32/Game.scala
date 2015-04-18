package org.oc.ld32

import org.oc.ld32.gui.GuiIngame
import org.oc.ld32.input.{Controls, LogitechMapping}
import org.oc.ld32.level.BaguetteLevel
import org.lengine.GameBase
import org.lengine.maths.Vec2f
import org.lengine.render.{FontRenderer, TextureAtlas}
import org.lwjgl.input.{Controller, Keyboard}
import org.oc.ld32.entity.EntityPlayer
import org.oc.ld32.gui.{GuiIngame, GuiScreen}
import org.oc.ld32.level.{BaguetteLevel, LevelLoader}

import scala.collection.JavaConversions._

object Game extends GameBase("Baguettes") {

  var level: BaguetteLevel = _
  var fontRenderer: FontRenderer = _
  var player: EntityPlayer = _
  var currentGui: GuiScreen = _
  var usingGamepad = false

  override def getBaseHeight: Int = 640

  override def update(delta: Float): Unit = {
    level.update(delta)

    if(!usingGamepad) {
      if (isKeyPressed(Keyboard.KEY_LEFT)) {
        player.walkLeft(delta)
      }
      if (isKeyPressed(Keyboard.KEY_RIGHT)) {
        player.walkRight(delta)
      }
      if (isKeyPressed(Keyboard.KEY_UP)) {
        player.walkUp(delta)
      }
      if (isKeyPressed(Keyboard.KEY_DOWN)) {
        player.walkDown(delta)
      }
    } else {
      player.walkRight(delta, getAxisValue(Controls.MOVE_X))
      player.walkUp(delta, -getAxisValue(Controls.MOVE_Y))
    }
  }

  override def initGame: Unit = {
    fontRenderer = new FontRenderer(new TextureAtlas("assets/textures/font.png", 16, 16))

    loadLevel("testLevel0", true)

    if (currentGui != null) currentGui.init()

    displayGuiScreen(new GuiIngame)
  }

  def loadLevel(id: String, reloadMusic: Boolean = false): Unit = {
    level = LevelLoader.load(id)
    player = new EntityPlayer
    level spawn player

    player setPos level.spawnpoint

    if(reloadMusic && level.music != null) {
   //   soundManager.play("musics/"+level.music+".ogg")
    }
  }

  override def onKeyReleased(keyCode: Int, char: Char): Unit = {
    usingGamepad = false
  }

  override def onMouseMoved(x: Int, y: Int, dx: Int, dy: Int): Unit = {
    usingGamepad = false
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    if (currentGui != null) {
      for (element <- currentGui.elements) {
        element.onKeyPressed(keyCode, char)
      }
    }
    usingGamepad = false
  }

  override def render(delta: Float): Unit = {
    level.render(delta)
    fontRenderer.renderString("LD32: An Unconventional Weapon", 0, getBaseHeight-17-16, 0xFFFFFFF, 1)
    val x: Float = player.getPos.x
    val y: Float = player.getPos.y
    fontRenderer.renderString(s"pos: $x, $y", 0, getBaseHeight-17, 0xFFFFFFF, 1)
    if (currentGui != null) currentGui.render(delta)
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    if (currentGui != null) {
      for (element <- currentGui.elements) {
        element.onMousePressed(x, y, button)
      }
    }
  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {

  }

  override def onScroll(x: Int, y: Int, dir: Int): Unit = {
    if (currentGui != null) {
      for (element <- currentGui.elements) {
        element.onScroll(x, y, dir)
      }
    }
  }

  def displayGuiScreen(gui: GuiScreen): Unit = {
    currentGui = gui
    currentGui.init()
  }

  override def onAxisMoved(x: Float, y: Float, index: Int, source: Controller): Unit = {
    // TODO: Use for mappings
    usingGamepad = true
  }

  override def onButtonPressed(index: Int, source: Controller): Unit = {
    println(s"button pressed $index")
    usingGamepad = true
  }

  override def onButtonReleased(index: Int, source: Controller): Unit = {
    println(s"button released $index")
    usingGamepad = true
  }

  override def onPovYMoved(value: Float, index: Int, source: Controller): Unit = {
    println(s"pov y $value")
    usingGamepad = true
  }

  override def onPovXMoved(value: Float, index: Int, source: Controller): Unit = {
    println(s"pov x $value")
    usingGamepad = true
  }
}
