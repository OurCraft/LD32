package org.c1.ld32

import org.c1.ld32.entity
import org.c1.ld32.level.BaguetteLevel
import org.lengine.GameBase
import org.lengine.level.Level
import org.lengine.render.{FontRenderer, TextureAtlas}
import org.lengine.tests.EntityPlayer
import org.lwjgl.input.{Keyboard, Controllers}

object Game extends GameBase("LD32") {

  var level: BaguetteLevel = _

  var fontRenderer: FontRenderer = _

  var player: entity.EntityPlayer = _

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
    player = new entity.EntityPlayer
    level spawn player
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
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {

  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {

  }

  override def onScroll(x: Int, y: Int, dir: Int): Unit = {

  }
}
