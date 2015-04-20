package org.oc.ld32.entity

import org.lengine.render.{Texture, TextureAtlas}
import org.oc.ld32.render.Animation

class EntityPlayer extends EntityBiped("player", 30f) {

  var baguetteCompletion: Float = 0f
  val halfBaguette = new Animation(new TextureAtlas(new Texture(s"assets/textures/entities/player_half.png"), 64,64))
  val fullBaguette = new Animation(new TextureAtlas(new Texture(s"assets/textures/entities/player_full.png"), 64,64))

  fullBaguette.speed = 5f
  halfBaguette.speed = 5f
  val none = anim

  override def update(delta: Float): Unit = {
    if(baguetteCompletion < 1f/3f) {
      anim = none
    } else if(baguetteCompletion < 2f/3f) {
      anim = halfBaguette
    } else {
      anim = fullBaguette
    }
    super.update(delta)
  }

  override def getAttackRange(): Float = {
    val multiplier = 1f
    if(baguetteCompletion != 0f)
      attackRange*multiplier + (baguetteCompletion * attackRange/multiplier)
    else
      attackRange*multiplier
  }

}
