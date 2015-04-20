package org.oc.ld32.entity

import org.lengine.maths.Vec2f
import org.lengine.render.{Texture, Sprite}

class EntityAttack(owner: EntityBiped, x: Float, y: Float, w: Float, h: Float) extends BaguetteEntity {


  val testSprite = new Sprite(new Texture("assets/textures/blank.png"))

  testSprite.setPos(x,y)
  testSprite.width = w
  testSprite.height = h

  var countdown = 5f/60f

  override def init: Unit = {
    super.init
    setPos(new Vec2f(x,y))
    boundingBox.x = x
    boundingBox.y = y
    boundingBox.width = w
    boundingBox.height = h
  }

  override def update(delta: Float): Unit = {
    countdown-=delta
    if(countdown < 0)
      level despawn this
    else
      super.update(delta)
  }

  override def render(delta: Float): Unit = {
    testSprite.render(delta)
  }

  override def onCollide(other: BaguetteEntity): Unit = {
    println(other)
    if (other != owner) {
      other match {
        case biped: EntityBiped => {
          biped.die()
          println("ded")
          level despawn this
        }

        case _ =>
      }
    }
  }
}
