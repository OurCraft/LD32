package org.c1.ld32.entity

import org.lengine.render.{Sprite, Texture}

class EntityBaguettePiece(val value: Float) extends BaguetteEntity {

  private val sprite: Sprite = new Sprite(new Texture("assets/textures/entities/baguette.png"))

  override def render(delta: Float): Unit = {
    sprite.setPos(getPos.x, getPos.y)
    sprite.render(delta)
  }

  override def update(delta: Float): Unit = {
    boundingBox.x = getPos.x
    boundingBox.y = getPos.y

  }

  override def init: Unit = {
    super.init
    boundingBox.width = 16f
    boundingBox.height = 16f
  }

  override def onCollide(other: BaguetteEntity): Unit = {
    println(other)
    if(other.isInstanceOf[EntityPlayer]) {
      val player: EntityPlayer = other.asInstanceOf[EntityPlayer]
      player.baguetteCompletion += value
      level.despawn(this)
    }
  }
}
