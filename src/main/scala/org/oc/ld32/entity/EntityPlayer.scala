package org.oc.ld32.entity

class EntityPlayer extends EntityBiped("player", 30f) {

  var baguetteCompletion: Float = 0f


  override def getAttackRange(): Float = {
    if(baguetteCompletion != 0f)
      return attackRange + (baguetteCompletion * attackRange)
    else
      super.getAttackRange()
  }

}
