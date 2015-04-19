package org.oc.ld32.gui.editor

import java.util

import org.lengine.maths.Vec2f
import org.lengine.render.{Sprite, TextureRegion, TextureAtlas}
import org.lwjgl.input.Keyboard
import org.oc.ld32.Game
import org.oc.ld32.gui._
import java.util.{List, ArrayList, Map, HashMap, Stack}

import org.oc.ld32.input.gamepad.Controls
import org.oc.ld32.level.{FloorDecoration, Wall}
import org.oc.ld32.render.Animation

import scala.collection.JavaConversions._

class GuiEditor extends GuiScreen {

  val WALL: String = "wall"
  val ENEMY: String = "enemy"
  val FLOOR: String = "floor"

  var currentObject: String = WALL
  var extraData: String = null

  val options: scala.List[String] = scala.List(ENEMY, FLOOR, WALL)

  val walls: List[Wall] = new ArrayList
  val floorDecorations: List[FloorDecoration] = new ArrayList
  val enemyDefinitions: List[EnemyDef] = new ArrayList
  val anims: Map[String, Animation] = new HashMap
  val nortapSprite = new Sprite("assets/textures/entities/nortap.png", new TextureRegion(0,0,1,1f/4f))
  val floorSprite = new Sprite("assets/textures/gui/editorFloor.png")
  val wallSprite = new Sprite("assets/textures/gui/editorWalls.png")
  val cursorSprite = new Sprite("assets/textures/gui/editorCursor.png")
  var showCursor = false
  var dragging = false
  var startDragX = 0f
  var startDragY = 0f
  var modalWindow: ModalWindow = null
  val cursor: Vec2f = new Vec2f
  var shouldRemoveWindow = false
  val actionStack: Stack[String] = new Stack

  val shadowSprite = new Sprite("assets/textures/gui/shadow.png")
  shadowSprite.width = width
  shadowSprite.height = height

  override def init(): Unit = {
    cursor.set(width/2f, height/2f)
    showCursor = false
  }

  def getAnim(id: String): Animation = {
    if(!anims.containsKey(id)) {
      anims.put(id, new Animation(new TextureAtlas(s"assets/textures/entities/$id.png", 64, 64), 0f))
    }
    anims.get(id)
  }

  override def render(delta: Float): Unit = {
    renderScreen(delta)
  }

  def updateCursor(delta: Float) = {
    val speed = 5f
    val threshold = 0.10f
    val xAmount = Game.getAxisValue(Controls.moveX)
    val yAmount = Game.getAxisValue(Controls.moveY)
    if(Math.abs(xAmount) > threshold || Math.abs(yAmount) > threshold)
      cursor.set(cursor.x+xAmount*speed,cursor.y-yAmount*speed)

    if(cursor.x < 0) {
      cursor.x = 0
    }
    if(cursor.y < 0) {
      cursor.y = 0
    }
    if(cursor.x > width) {
      cursor.x = width
    }
    if(cursor.y > height) {
      cursor.y = height
    }

    if(modalWindow != null) {
      modalWindow.setCursorPos(cursor.x, cursor.y)
    }

    if(dragging && modalWindow == null) {
      currentObject match {
        case WALL => {
          val wall = walls.remove(walls.size - 1)
          var minX = 0f
          var minY = 0f
          var maxX = 0f
          var maxY = 0f
          if(cursor.x > startDragX) {
            minX = cursor.x
            maxX = startDragX
          } else if(cursor.x <= startDragX) {
            minX = startDragX
            maxX = cursor.x
          }

          if(cursor.y > startDragY) {
            minY = cursor.y
            maxY = startDragY
          } else if(cursor.y <= startDragY) {
            minY = startDragY
            maxY = cursor.y
          }

          val newWall = new Wall(wall.id, new Vec2f(minX, minY), new Vec2f(maxX, maxY))
          walls.add(newWall)
        }

        case FLOOR => {
          val floor = floorDecorations.remove(floorDecorations.size - 1)
          val minX = (Math.min(cursor.x, startDragX)/16f).toInt * 16f
          val minY = (Math.min(cursor.y, startDragY)/16f).toInt * 16f
          val maxX = (Math.max(cursor.x, startDragX)/16f).toInt * 16f
          val maxY = (Math.max(cursor.y, startDragY)/16f).toInt * 16f
          val newFloor = new FloorDecoration(floor.id, minX, minY, maxX-minX, maxY-minY)
          floorDecorations.add(newFloor)
        }

        case _ =>
      }
    }
  }

  override def renderScreen(delta: Float): Unit = {
    if(shouldRemoveWindow) {
      shouldRemoveWindow = false
      elements.remove(modalWindow)
      modalWindow = null
    }
    updateCursor(delta)

    for(floor <- floorDecorations) {
      floor.render(delta)
    }

    for(enemy <- enemyDefinitions) {
      val anim: Animation = getAnim(enemy.id)
      anim.update(delta)
      anim.transform.pos.set(enemy.x, enemy.y)
      anim.transform.angle = -(Math.PI/2f).toFloat
      anim.render(delta)
    }

    for(wall <- walls) {
      wall.render(delta)
    }

    nortapSprite.setPos(0,height-nortapSprite.height)
    floorSprite.setPos(64f,height-nortapSprite.height)
    wallSprite.setPos(128f,height-nortapSprite.height)

    wallSprite.render(delta)
    floorSprite.render(delta)
    nortapSprite.render(delta)

    if(!dragging && modalWindow == null) {
      var text = currentObject
      if(extraData != null) {
        text += s"($extraData)"
      }
      Game.fontRenderer.renderString(text, cursor.x, cursor.y, 0xFFFFFFFF, 1f)
    }

    if(modalWindow != null) {
      shadowSprite.render(delta)
      modalWindow.render(delta)
    }
    if(showCursor) {
      cursorSprite.setPos(cursor.x - cursorSprite.width/2f, cursor.y - cursorSprite.height/2f)
      cursorSprite.render(delta)
    }
  }

  override def onMousePressed(x: Int, y: Int, button: Int): Unit = {
    super.onMousePressed(x,y,button)
    showCursor = false
    cursor.set(x,y)
    onPressed
  }

  override def onMouseReleased(x: Int, y: Int, button: Int): Unit = {
    super.onMouseReleased(x,y,button)
    showCursor = false
    cursor.set(x,y)
    onReleased
  }

  def rollBack: Unit = {
    if(!actionStack.isEmpty) {
      val lastAction = actionStack.pop
      lastAction match {
        case FLOOR => {
          floorDecorations.remove(floorDecorations.size - 1)
        }

        case WALL => {
          walls.remove(walls.size - 1)
        }

        case ENEMY => {
          enemyDefinitions.remove(enemyDefinitions.size - 1)
        }

        case _ =>
      }
    }
  }

  override def onKeyReleased(keyCode: Int, char: Char): Unit = {
    super.onKeyReleased(keyCode, char)
    if(keyCode == Keyboard.KEY_Z && Game.isKeyPressed(Keyboard.KEY_LCONTROL)) {
      rollBack
    }
  }

  def removeModalWindow(): Unit = {
    shouldRemoveWindow = true
  }

  def showFloorWindow(): Unit = {
    val w = width/2f
    val h = height/2f
    val x = width/2f-w/2f
    val y = height/2f-h/2f
    val window = new ModalWindow(x,y,w,h)
    val title = "Choose floor type"
    val titleLabel = new GuiLabel(title, x + w/2f - Game.fontRenderer.getWidth(title)/2f, y+h-32f)

    // Create list:
    val list = scala.List("carpet", "dirt", "planks", "tiles", "wall")
    val elemSpace = w / list.size
    for(elem <- list) {
      val index = list.indexOf(elem)
      val imgX = index * elemSpace + elemSpace/2f
      val imgY = h/2f
      val image = new GuiImage(x + imgX-64f/2f, y + imgY-64f/2f, 64f, 64f, s"assets/textures/levels/ground/$elem.png")
      window.add(image)

      val button = new GuiButton(elem.capitalize, x + index * elemSpace, y+imgY-64, elemSpace, 64f*2f)
      button.setHandler(button => {
        currentObject = FLOOR
        extraData = elem
        removeModalWindow
      })
      window.add(button)
    }
    val cancelButton = new GuiButton("Cancel",x + w/2f-100f, y +20f)
    cancelButton.setHandler(button => {
      removeModalWindow
    })

    window.add(cancelButton)
    window.add(titleLabel)

    modalWindow = window
    elements.add(modalWindow)
  }

  def showEnemyWindow(): Unit = {
    val w = width/2f
    val h = height/2f
    val x = width/2f-w/2f
    val y = height/2f-h/2f
    val window = new ModalWindow(x,y,w,h)
    val title = "Configure enemy"
    val titleLabel = new GuiLabel(title, x + w/2f - Game.fontRenderer.getWidth(title)/2f, y+h-32f)

    // Create list:
    val idList = scala.List("nortap")
    val aiList = scala.List("patrol", "wander")
    val idElemSpace = w / idList.size
    val aiElemSpace = w / aiList.size
    var id = "nortap"
    var ai = "patrol"
    for(elem <- idList) {
      val index = idList.indexOf(elem)
      val imgX = index * idElemSpace + idElemSpace/2f
      val imgY = h/2f+75f
      val image = new GuiAnimation(x + imgX-64f/2f, y + imgY-64f/2f, new Animation(new TextureAtlas(s"assets/textures/entities/$elem.png", 64,64)))
      window.add(image)

      val button = new GuiButton(elem.capitalize, x + index * idElemSpace, y+imgY-64, idElemSpace, 64f*2f)
      button.setHandler(button => {
        id = elem
      })
      window.add(button)
    }

    for(elem <- aiList) {
      val index = aiList.indexOf(elem)
      val imgY = h/2f
      val button = new GuiButton(elem.capitalize, x + index * aiElemSpace, y+imgY-64, aiElemSpace)
      button.setHandler(button => {
        ai = elem
      })
      window.add(button)
    }

    val confirmButton = new GuiButton("Confirm",x + w/2f-200f-10f, y +20f)
    confirmButton.setHandler(button => {
      currentObject = ENEMY
      extraData = s"$id;$ai"
      removeModalWindow
    })

    val cancelButton = new GuiButton("Cancel",x + w/2f + 10f, y + 20f)
    cancelButton.setHandler(button => {
      removeModalWindow
    })

    window.add(confirmButton)
    window.add(cancelButton)
    window.add(titleLabel)

    modalWindow = window
    elements.add(modalWindow)
  }

  def onPressed: Unit = {
    if(modalWindow != null)
      return
    if(cursor.y < height-64f) {
      dragging = true
      startDragX = cursor.x
      startDragY = cursor.y
      currentObject match {

        case WALL => {
          val wall = new Wall("", new Vec2f(cursor.x, cursor.y), new Vec2f(cursor.x, cursor.y))
          walls.add(wall)
        }

        case FLOOR => {
          val floor = new FloorDecoration(extraData, cursor.x, cursor.y, 16f, 16f)
          floorDecorations.add(floor)
        }

        case ENEMY => {
          val id = extraData.split(";")(0)
          val aiType = extraData.split(";")(1)
          val enemy = new EnemyDef(id, aiType, cursor.x, cursor.y)
          enemyDefinitions.add(enemy)
        }

        case _ =>
      }
    } else {
      val index = (cursor.x/64f).toInt
      if(index < options.size) {
        currentObject = options(index)

        currentObject match {
          case FLOOR => {
            extraData = "carpet"
            showFloorWindow()
          }

          case ENEMY => {
            extraData = "nortap;patrol"
            showEnemyWindow()
          }

          case _ => {
            extraData = null
          }
        }
      }
    }
  }

  def onReleased: Unit = {
    if(modalWindow != null)
      return
    if(cursor.y < height-64f) {
      actionStack.push(currentObject)
    }
    dragging = false
  }

  override def onKeyPressed(keyCode: Int, char: Char): Unit = {
    super.onKeyPressed(keyCode, char)
  }

  override def onMouseMove(x: Int, y: Int, dx: Int, dy: Int) = {
    super.onMouseMove(x,y,dx,dy)
    showCursor = false
    cursor.set(x,y)
  }

  override def onAxisMoved(value: Float, index: Int) = {
    super.onAxisMoved(value, index)
    showCursor = true
  }

  override def onButtonPressed(button: Int) = {
    super.onButtonPressed(button)
    if(Controls.isConfirmButton(button)) {
      onPressed
    }
  }

  override def onButtonReleased(button: Int) = {
    super.onButtonReleased(button)
    if(Controls.isConfirmButton(button)) {
      onReleased
    } else if(button == Controls.throwButton) {
      rollBack
    }
  }
}
