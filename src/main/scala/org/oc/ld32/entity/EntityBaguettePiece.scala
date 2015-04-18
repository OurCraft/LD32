package org.oc.ld32.entity

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
    boundingBox.width = 32f
    boundingBox.height = 32f
  }

  override def onCollide(other: BaguetteEntity): Unit = {
    println(other)
    other match {
      case player: EntityPlayer =>
        player.baguetteCompletion += value
        level.despawn(this)
      case _ =>
    }
  }
}
