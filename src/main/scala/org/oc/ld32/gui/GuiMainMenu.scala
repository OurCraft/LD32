package org.oc.ld32.gui

import java.util
import org.lengine.maths.Quaternion
import org.lengine.render.{RenderEngine, Sprite}
import org.oc.ld32.Game
import org.oc.ld32.gui.action.ActionHandler

class GuiMainMenu extends GuiScreen with ActionHandler {

  var lastSelected: Int = 0
  var startSprite: Sprite = _
  var gotoSprite: Sprite = _
  var editorSprite: Sprite = _
  var optionsSprite: Sprite = _
  var quitSprite: Sprite = _

  var spriteList: List[Sprite] = _
  var copies: util.TreeMap[Sprite, Sprite] = new util.TreeMap

  var selector: GuiSelector = _

  var lastTimeAdded: Float = 0

  var trail: util.List[Sprite] = new util.ArrayList
  var unused: util.List[Sprite] = new util.ArrayList

  private val minColor: Int = 0x7C7C7C
  private val maxColor: Int = 0x0F0F0F

  def init(): Unit = {
    val list = new util.ArrayList[String]()
    list.add("Start new game")
    list.add("Goto level")
    list.add("Editor")
    list.add("Options")
    list.add("Quit game")
    selector = new GuiSelector(list, 10, 60)
    selector.shouldRender = false
    elements.add(selector)

    startSprite = new Sprite("assets/textures/gui/startNewGame.png")
    gotoSprite = new Sprite("assets/textures/gui/gotoLevel.png")
    editorSprite = new Sprite("assets/textures/gui/editor.png")
    optionsSprite = new Sprite("assets/textures/gui/options.png");
    quitSprite = new Sprite("assets/textures/gui/quitGame.png")

    spriteList = List(startSprite, gotoSprite, editorSprite, optionsSprite,  quitSprite)
  }

  def createTrailPart(sprite: Sprite): Sprite = {
    val color: Int = (Math.random() * (maxColor - minColor) + minColor).toInt
    val r = (color >> 16 & 0xFF) / 255f
    val g = (color >> 8 & 0xFF) / 255f
    val b = (color & 0xFF) / 255f
    val colorObject = new Quaternion(r,g,b,1f)
    val copy = new Sprite(sprite.texture, sprite.region, colorObject)
    val maxOffset = 4f
    val offset = (Math.cos(RenderEngine.time) * maxOffset - maxOffset/2f).toFloat
    copy.setPos(sprite.getPos.x, sprite.getPos.y + offset)
    copy
  }

  def drawTrail(original: Sprite, delta: Float) = {
    if(RenderEngine.time - lastTimeAdded >= 1f/60f) {
      lastTimeAdded = RenderEngine.time
      if (unused.isEmpty)
        trail.add(createTrailPart(original))
      else {
        val popped: Sprite = unused.remove(0)

        val maxOffset = 4f
        val offset = (Math.cos(RenderEngine.time) * maxOffset - maxOffset/2f).toFloat
        popped.setPos(original.getPos.x, original.getPos.y+offset)
        trail.add(popped)
      }
    }
    for(i <- 0 until trail.size) {
      val trailPart = trail.get(i)
      trailPart.setPos(trailPart.getPos.x-1f*delta*60f, trailPart.getPos.y)
      if(trailPart.getPos().x+trailPart.width < 0f) {
        if(trailPart.texture == original.texture)
          unused.add(trailPart)
      } else {
        trailPart.render(delta)
      }
    }
    trail.removeAll(unused)
  }

  def renderScreen(delta: Float): Unit = {
    val w: Float = RenderEngine.displayWidth
    val h: Float = RenderEngine.displayHeight

    startSprite.setPos(100f, h/2f+startSprite.height/2f + 150)
    gotoSprite.setPos(100f, h/2f-gotoSprite.height/2f + 100)
    editorSprite.setPos(100f, h/2f-editorSprite.height*1.5f + 50)
    optionsSprite.setPos(100f, h/2f-optionsSprite.height*1.5f - 50)
    quitSprite.setPos(100f, h/2f-quitSprite.height*1.5f - 150)

    val selected = spriteList(selector.selectedIndex)
    if(lastSelected != selector.selectedIndex) {
      lastSelected = selector.selectedIndex
      unused.clear
    }
    selected.setPos(selected.getPos().x + 25, selected.getPos().y)

    drawTrail(selected, delta)
    startSprite.render(delta)
    gotoSprite.render(delta)
    editorSprite.render(delta)
    optionsSprite.render(delta)
    quitSprite.render(delta)
  }

  def onAction(source: Object, id: Int): Unit = {
    if (source == selector) {
      if (id == 0) {
        Game.displayGuiScreen(new GuiIngame)
        Game.loadLevel("testLevel0", true)
      } else if (id == 1) {
        // Todo: level selection
      } else if (id == 2) {
        // Todo: editor
      } else if (id == 3) {
        // Todo: options
        Game.displayGuiScreen(new GuiOptions)
      } else if (id == 4) {
        System.exit(0)
      }
    }
  }
}
