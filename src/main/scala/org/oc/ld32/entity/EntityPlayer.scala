package org.oc.ld32.entity

import org.lengine.render.{Sprite, Texture}
import org.oc.ld32.level.BaguetteLevel

class EntityPlayer extends BaguetteEntity {

  val sprite: Sprite = new Sprite(new Texture("assets/textures/entities/player.png"))
  var baguetteCompletion: Float = 0f

  override def render(delta: Float): Unit = {
    sprite.setPos(getPos.x, getPos.y)
    sprite.setAngle((getAngle - Math.PI/2f).toFloat)
    sprite.render(delta)
  }

  override def init: Unit = {
    super.init
    boundingBox.width = 32f
    boundingBox.height = 32f
  }

  override def update(delta: Float): Unit = {

  }

  def walkLeft(delta: Float, speedMultiplier: Float = 1): Boolean = {
    walkRight(delta, -speedMultiplier)
  }

  def walkRight(delta: Float, speedMultiplier: Float = 1): Boolean = {
    val speed: Float = delta * 60f * 2f * speedMultiplier
    val lvl: BaguetteLevel = level.asInstanceOf[BaguetteLevel]
    if(lvl.canGoTo(boundingBox, getPos.x+speed, getPos.y)) {
      getPos.x += speed
      return true
    }
    false
  }

  def walkUp(delta: Float, speedMultiplier: Float = 1): Boolean = {
    val speed: Float = delta * 60f * 2f * speedMultiplier
    val lvl: BaguetteLevel = level.asInstanceOf[BaguetteLevel]
    if(lvl.canGoTo(boundingBox, getPos.x, getPos.y+speed)) {
      getPos.y += speed
      return true
    }
    false
  }

  def walkDown(delta: Float, speedMultiplier: Float = 1): Boolean = {
    walkUp(delta, -speedMultiplier)
  }
}
