package csj.thoughtful.designpattern.adapter.impl;

import csj.thoughtful.designpattern.adapter.def.AdvancedMediaPlayer;

public class Mp4Player implements AdvancedMediaPlayer {
//   mp4Player MP4播放器， AdvancedMediaPlayer 媒体播放适配器
 
   @Override
   public void playVlc(String fileName) {
      //什么也不做
   }
 
   @Override
   public void playMp4(String fileName) {
      System.out.println("Playing mp4 file. Name: "+ fileName);      
   }
}