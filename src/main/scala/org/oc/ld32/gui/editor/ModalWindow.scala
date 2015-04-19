package org.oc.ld32.gui

import java.util.{ArrayList, List}

import org.lengine.maths.Quaternion
import org.lengine.render.{Sprite, TextureRegion}

import scala.collection.JavaConversions._

class ModalWindow(var x: Float, var y: Float, var w: Float, var h: Float) extends BaguetteGui {

  val background = new Sprite("assets/textures/blank.png", new TextureRegion, new Quaternion(0.5f,0.5f,0.5f))
  background.setPos(x,y)
  background.width = w
  background.height = h

  val elements = new ArrayList[BaguetteGui]

  override def render(delta: Float): Unit = {
    background.render(delta)
    for(elem <- elements) {
      elem.render(delta)
    }
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    for (element <- elements) {
      element.onMousePressed(x,y,button)
    }
  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {
    for (element <- elements) {
      element.onMouseReleased(x,y,button)
    }
  }

  override def onKeyReleased(keyCode: Int, char: Char): Unit = {
    for (element <- elements) {
      element.onKeyReleased(keyCode, char)
    }
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    for (element <- elements) {
      element.onKeyPressed(keyCode, char)
    }
  }

  override def onMouseMove(x: Int, y: Int, dx: Int, dy: Int) = {
    for (element <- elements) {
      element.onMouseMove(x,y,dx,dy)
    }
  }

  override def onScroll(x: Int, y: Int, dir: Int): Unit = {
    for (element <- elements) {
      element.onScroll(x,y,dir)
    }
  }

  override def onAxisMoved(value: Float, index: Int) = {
    for (element <- elements) {
      element.onAxisMoved(value, index)
    }
  }

  override def onButtonPressed(button: Int) = {
    for (element <- elements) {
      element.onButtonPressed(button)
    }
  }

  override def onButtonReleased(button: Int) = {
    for (element <- elements) {
      element.onButtonReleased(button)
    }
  }

  override def setCursorPos(cx: Float, cy: Float): Unit = {
    for(elem <- elements) {
      elem.setCursorPos(cx, cy)
    }
  }

  def add(elem: BaguetteGui): Unit = {
    elements.add(elem)
  }
}
