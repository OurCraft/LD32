package org.oc.ld32.gui

import java.util

import org.lengine.render.RenderEngine

import scala.collection.JavaConversions._

abstract class GuiScreen extends BaguetteGui {

  var elements = new util.ArrayList[BaguetteGui]()

  def init(): Unit

  def render(delta: Float): Unit = {
    renderScreen(delta)
    for (element <- elements) {
        element.render(delta)
    }
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    for (element <- elements) {
      element.setCursorPos(x,y)
      element.onMousePressed(x,y,button)
    }
  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {
    for (element <- elements) {
      element.setCursorPos(x,y)
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
      element.setCursorPos(x,y)
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

  def renderScreen(delta: Float): Unit

  def width: Float = RenderEngine.displayWidth
  def height: Float = RenderEngine.displayHeight
}
