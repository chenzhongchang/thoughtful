package csj.thoughtful.designpattern.adapter.impl;

import csj.thoughtful.designpattern.adapter.def.AdvancedMediaPlayer;
import csj.thoughtful.designpattern.adapter.def.MediaPlayer;

//创建实现了 MediaPlayer 接口的适配器类。
public class MediaAdapter implements MediaPlayer {
 
   AdvancedMediaPlayer advancedMusicPlayer;
//   AdvancedMediaPlayer  高级媒体播放器，  play  奏响
 
   public MediaAdapter(String audioType){
      if(audioType.equalsIgnoreCase("vlc")){
         advancedMusicPlayer = new VlcPlayer();       
      } else if (audioType.equalsIgnoreCase("mp4")){
         advancedMusicPlayer = new Mp4Player();
      }  
   }
 
   @Override
   public void play(String audioType, String fileName) {
      if(audioType.equalsIgnoreCase("vlc")){
         advancedMusicPlayer.playVlc(fileName);
      }else if(audioType.equalsIgnoreCase("mp4")){
         advancedMusicPlayer.playMp4(fileName);
      }
   }
}