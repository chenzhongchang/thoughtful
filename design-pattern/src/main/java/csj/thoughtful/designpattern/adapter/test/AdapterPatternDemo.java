package csj.thoughtful.designpattern.adapter.test;

import csj.thoughtful.designpattern.adapter.impl.AudioPlayer;

/**
 * 适配器模式（Adapter Pattern）：将一个类的接口转换成客户希望的另一个接口。
 * 适配器模式让那些接口不兼容的类可以一起工作。
 * */
public class AdapterPatternDemo {
   public static void main(String[] args) {
      //AudioPlayer 音频播放器
      AudioPlayer audioPlayer = new AudioPlayer();
 
      audioPlayer.play("mp3", "beyond the horizon.mp3");
      audioPlayer.play("mp4", "alone.mp4");
      audioPlayer.play("vlc", "far far away.vlc");
      audioPlayer.play("avi", "mind me.avi");
   }
}