package org.c1.ld32

import org.c1.ld32.entity.EntityPlayer
import org.c1.ld32.gui.{GuiMainMenu, GuiScreen}
import org.c1.ld32.level.{BaguetteLevel, Wall}
import org.lengine.GameBase
import org.lengine.maths.Vec2f
import org.lengine.render.{FontRenderer, TextureAtlas}
import org.lwjgl.input.Keyboard

import scala.collection.JavaConversions._

object Game extends GameBase("LD32") {

  var level: BaguetteLevel = _

  var fontRenderer: FontRenderer = _

  var player: EntityPlayer = _

  var currentGui: GuiScreen = _

  override def getBaseHeight: Int = 640

  override def update(delta: Float): Unit = {
    level.update(delta)
    if(isKeyPressed(Keyboard.KEY_LEFT)) {
      player.walkLeft(delta)
    }
    if(isKeyPressed(Keyboard.KEY_RIGHT)) {
      player.walkRight(delta)
    }
    if(isKeyPressed(Keyboard.KEY_UP)) {
      player.walkUp(delta)
    }
    if(isKeyPressed(Keyboard.KEY_DOWN)) {
      player.walkDown(delta)
    }
  }

  override def initGame: Unit = {
    fontRenderer = new FontRenderer(new TextureAtlas("assets/textures/font.png", 16, 16))

    level = new BaguetteLevel
    player = new EntityPlayer
    level spawn player

    level.addBaguette(0.25f, 300, 200)
    level.addBaguette(0.25f, 200, 100)
    level.addBaguette(0.25f, 200, 200)
    level.addBaguette(0.25f, 150, 250)
    level.addBaguette(0.25f, 125, 300)


    player.setPos(new Vec2f(300,100))


    level.walls.add(new Wall(new Vec2f(100,20), new Vec2f(116, 500)))
    level.walls.add(new Wall(new Vec2f(100+300,20), new Vec2f(116+300, 500)))

    level.walls.add(new Wall(new Vec2f(100,20), new Vec2f(116+300, 36)))
    level.walls.add(new Wall(new Vec2f(100,484), new Vec2f(116+300, 500)))

    if (currentGui != null) currentGui.init()

    displayGuiScreen(new GuiMainMenu)
  }

  override def onKeyReleased(keyCode: Int, char: Char): Unit = {

  }

  override def onMouseMoved(x: Int, y: Int, dx: Int, dy: Int): Unit = {

  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    if (currentGui != null) {
      for (element <- currentGui.elements) {
        element.onKeyPressed(keyCode, char)
      }
    }
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
}
