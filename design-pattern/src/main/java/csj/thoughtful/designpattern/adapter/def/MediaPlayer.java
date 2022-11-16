package csj.thoughtful.designpattern.adapter.def;


//为媒体播放器和更高级的媒体播放器创建接口。
public interface MediaPlayer {
//   MediaPlayer 媒体播放器
//   play 奏响，audioType  音频类型，
   public void play(String audioType, String fileName);
}