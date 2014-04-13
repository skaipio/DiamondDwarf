package org.diamonddwarf.ui

import com.badlogic.gdx.graphics.g2d.TextureRegion

class Animation(private val frames : Array[(TextureRegion, Float)]) {
  private var currentFrameIndex = 0
  private var timer = 0.0f
  private var stopped = false
  
  def start {
    stopped = false
  }
  def stop{
    timer = 0.0f
    stopped = true
  }
  def pause{
    stopped = true
  }
  def restart{
    timer = 0.0f
    stopped = false
  }
  
  def update(delta: Float) {
    if (stopped) return
    timer += delta
    val currentFrame = frames(currentFrameIndex)
    if (timer >= currentFrame._2){
      currentFrameIndex += 1
      currentFrameIndex = currentFrameIndex % frames.size
      timer -= currentFrame._2
    
    }
  }
  
  def getCurrentFrame = frames(currentFrameIndex)._1
  
}