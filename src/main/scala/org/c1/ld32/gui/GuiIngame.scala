package org.c1.ld32.gui

import java.util.{HashMap, Map}

import org.c1.ld32.Game
import org.lengine.render.{TextureRegion, Sprite, Texture}

class GuiIngame extends GuiScreen {

  val map: Map[Float, Sprite] = new HashMap
  val texture: Texture = "assets/textures/gui/baguette.png"

  override def init(): Unit = {
  }

  def getSprite(p: Float): Sprite = {
    if(map.containsKey(p)) {
      map.get(p)
    } else {
      val sprite: Sprite = new Sprite(texture, new TextureRegion(0,0,p,1))
      map.put(p, sprite)
      sprite
    }
  }

  override def renderScreen(delta: Float): Unit = {
    if(Game.player != null) {
      val baguette: Sprite = getSprite(Game.player.baguetteCompletion)
      baguette.setPos(0,Game.getBaseHeight-baguette.height)
      baguette.render(delta)
    }
  }
}
