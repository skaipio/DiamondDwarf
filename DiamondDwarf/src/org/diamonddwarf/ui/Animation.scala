package org.diamonddwarf.ui

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.google.gson.JsonDeserializer
import com.google.gson.JsonDeserializationContext
import java.lang.reflect.Type
import com.google.gson.JsonElement
import org.diamonddwarf.resources.ResourceLoader
import com.google.gson.JsonParseException
import scala.collection.mutable.MutableList

class Animation(frames : Array[(TextureRegion, Float)]) {
  private var currentFrameIndex = 0
  private var timer = 0.0f
  private var stopped = false
  
  def start {
    stopped = false
  }
  def stop{
    this.reset
    stopped = true
  }
  def pause{
    stopped = true
  }
  def restart{
    this.reset
    stopped = false
  }
  
  def reset {
    currentFrameIndex = 0
    timer = 0.0f
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

class AnimationTemplate(val frames : Array[(TextureRegion, Float)])

class AnimationTemplateDeserializer(resourceLoader : ResourceLoader) extends JsonDeserializer[AnimationTemplate] {

  @throws[JsonParseException]("Failed to parse an animation")
  override def deserialize(json : JsonElement, typeOfT : Type, context : JsonDeserializationContext) : AnimationTemplate = {
    val jsonObject = json.getAsJsonObject()

    val frames = jsonObject.get("frames").getAsJsonArray()
    val times = jsonObject.get("times").getAsJsonArray()
    val framesWithTimes = MutableList[(TextureRegion, Float)]()
    var i = 0
    var j = 0
    while(i < frames.size()){
      val regionPath = frames.get(i).getAsString()
      val regions = resourceLoader.atlas.findRegions(regionPath)
      var k = 0
      while (k < regions.size){
        if (j < times.size()){
          framesWithTimes += ((regions.get(k), times.get(j).getAsFloat()))
          j+=1
        }else{
          framesWithTimes += ((regions.get(k), times.get(j-1).getAsFloat()))
        }
        k+=1
      }
      i+=1
    }
//    val framesWithTimes = for (i <- 0 until frames.size) yield {
//      val pair = frames.get(i).getAsJsonArray()
//      val regionPath = pair.get(0).getAsString()
//      val time = pair.get(1).getAsFloat()
//      var frame : (TextureRegion, Float) = null
//      val regions = resourceLoader.atlas.findRegions(regionPath)
//      if (regions.size != 0)
//        frame = (region,time)
//      else
//        frame = (resourceLoader.defaultTexture, time)
//      frame
//    }

    new AnimationTemplate(framesWithTimes.toArray)
  }
}