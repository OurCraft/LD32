package org.c1.ld32

import org.c1.ld32.gui.{GuiMainMenu, GuiScreen}
import org.lengine.GameBase
import org.lengine.level.Level
import org.lengine.render.{FontRenderer, TextureAtlas}
import org.lengine.tests.EntityPlayer

object Game extends GameBase("LD32") {

  var level: Level = _

  var fontRenderer: FontRenderer = _

  var player: EntityPlayer = _

  var currentGui: GuiScreen = _

  override def getBaseHeight: Int = 640

  override def update(delta: Float): Unit = {
    level.update(delta)
  }

  override def initGame: Unit = {
    fontRenderer = new FontRenderer(new TextureAtlas("assets/textures/font.png", 16, 16))

    level = new Level
    player = new EntityPlayer
    level spawn player

    if (currentGui != null) currentGui.init()

    openGui(new GuiMainMenu)
  }

  override def onKeyReleased(keyCode: Int, char: Char): Unit = {

  }

  override def onMouseMoved(x: Int, y: Int, dx: Int, dy: Int): Unit = {

  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {

  }

  override def render(delta: Float): Unit = {
    level.render(delta)
    fontRenderer.renderString("LD32: An Unconventional Weapon", 0, getBaseHeight-17, 0xFFFFFFF, 1)
    if (currentGui != null) currentGui.render(delta)
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {

  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {

  }

  override def onScroll(x: Int, y: Int, dir: Int): Unit = {

  }

  def openGui(gui: GuiScreen): Unit = {
    currentGui = gui
    currentGui.init()
  }
}
