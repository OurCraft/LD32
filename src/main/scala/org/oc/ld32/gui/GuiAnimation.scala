package org.oc.ld32.gui

import org.lengine.render.{Sprite, Texture, TextureRegion}
import org.oc.ld32.render.Animation

class GuiAnimation(var x: Float, var y: Float, anim: Animation) extends BaguetteGui {

  override def render(delta: Float): Unit = {
    anim.transform.pos.set(x,y)
    anim.transform.angle = -(Math.PI/2f).toFloat
    anim.update(delta)
    anim.render(delta)
  }
}