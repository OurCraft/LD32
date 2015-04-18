package org.oc.ld32.entity

abstract class AITask(val priority: Int, val owner: BaguetteEntity) {

  var running = false

  def start = running = true

  def stop = running = false

  def shouldContinue: Boolean

  def canExecute: Boolean

  def reset: Unit

  def perform(delta: Float): Unit
}
