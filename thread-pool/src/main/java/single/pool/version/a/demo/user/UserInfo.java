package single.pool.version.a.demo.user;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfo implements Serializable {

    private int id;
    private String userName;
    private String userPwd;
    private String idCard;
    private String sex;
    private String birthday;
    private String seq;
    private String version;
    private String remark;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UserInfo(){

    }

    public UserInfo(int index){
        this.id=index;
        this.userName="阳翟"+index;
        this.userPwd="yangdi"+index;
        this.idCard=System.currentTimeMillis()+"-"+index;
        int tn=index%2;
        this.sex=tn==1?"女":"男";
        this.birthday=getBirthday(index);
        String prefix=tn==1?"A":"B";
        this.seq=prefix+index;
        this.version="A."+index;
        this.remark=birthday+"-"+index;
    }

    public String getBirthday(int index){
        String time=System.currentTimeMillis()+"";
        int leng=time.length();
        time=time.substring(0,leng-6);
        String suffix=time.substring(leng-7);
        time=time+index+suffix;
        long dateTime=Long.parseLong(time);
        return sdf.format(dateTime);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPwd() {
        return userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", idCard='" + idCard + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", seq='" + seq + '\'' +
                ", version='" + version + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}