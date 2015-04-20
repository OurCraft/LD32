package org.oc.ld32.entity

import org.lengine.render.{Texture, TextureAtlas}
import org.oc.ld32.render.Animation

class EntityPlayer extends EntityBiped("player", 30f) {

  var baguetteCompletion: Float = 0f
  val halfBaguette = new Animation(new TextureAtlas(new Texture(s"assets/textures/entities/player_half.png"), 64,64))
  val fullBaguette = new Animation(new TextureAtlas(new Texture(s"assets/textures/entities/player_full.png"), 64,64))
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
    if(baguetteCompletion != 0f)
      return attackRange*2f + (baguetteCompletion * attackRange)
    else
      super.getAttackRange()*2f
  }

}
