package org.c1.ld32.entity

import org.c1.ld32.level.BaguetteLevel
import org.lengine.render.{Sprite, Texture}

class EntityPlayer extends BaguetteEntity {

  val sprite: Sprite = new Sprite(new Texture("assets/textures/entities/player.png"))
  var baguetteCompletion: Float = 0f

  override def render(delta: Float): Unit = {
    sprite.setPos(getPos.x, getPos.y)
    sprite.setAngle(getAngle)
    sprite.render(delta)
  }

  override def init: Unit = {
    super.init
  }

  override def update(delta: Float): Unit = {

  }

  def walkLeft(delta: Float): Boolean = {
    val speed: Float = delta * 60f * 2f
    val lvl: BaguetteLevel = level.asInstanceOf[BaguetteLevel]
    if(lvl.canGoTo(boundingBox, getPos.x-speed, getPos.y)) {
      getPos.x -= speed
      return true
    }
    false
  }

  def walkRight(delta: Float): Boolean = {
    val speed: Float = delta * 60f * 2f
    val lvl: BaguetteLevel = level.asInstanceOf[BaguetteLevel]
    if(lvl.canGoTo(boundingBox, getPos.x+speed, getPos.y)) {
      getPos.x += speed
      return true
    }
    false
  }

  def walkUp(delta: Float): Boolean = {
    val speed: Float = delta * 60f * 2f
    val lvl: BaguetteLevel = level.asInstanceOf[BaguetteLevel]
    if(lvl.canGoTo(boundingBox, getPos.x, getPos.y+speed)) {
      getPos.y += speed
      return true
    }
    false
  }

  def walkDown(delta: Float): Boolean = {
    val speed: Float = delta * 60f * 2f
    val lvl: BaguetteLevel = level.asInstanceOf[BaguetteLevel]
    if(lvl.canGoTo(boundingBox, getPos.x, getPos.y-speed)) {
      getPos.y -= speed
      return true
    }
    false
  }
}
