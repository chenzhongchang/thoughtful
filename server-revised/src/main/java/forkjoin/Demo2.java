package forkjoin;

public class Demo2 {
    public static void main(String[] args) {
        //4
        //6
//        makePwd6();
        countPwd4();

    }


    public static void countPwd4(){

        StringBuffer sb;
        byte a0,a1,a2,a3;
        int num=0;
        for(a0=48;a0<58;a0++) {
            for (a1 = 48; a1 < 58; a1++) {
                for (a2=48;a2<58;a2++){
                    for (a3=48;a3<58;a3++){
                        sb=new StringBuffer();
                        sb.append((char)a0).append((char)a1).append((char)a2).append((char)a3);
                        System.out.println(sb.toString());
                        num++;
                    }
                }
            }
        }
        System.out.println("num="+num);

    }




//    public static void makePwd6(){
//        //setup1
//        int num=0;
//        System.out.println("num="+(10*10*10*10*10*10));
//        //setup2
//        byte a0,a1,a2,a3,a4,a5,a6;
//        for(a0=53;a0<58;a0++) {
//            for (a1 = 48; a1 < 58; a1++) {
//                for (a2=48;a2<58;a2++){
//                    for (a3=48;a3<58;a3++){
//                        for (a4=48;a4<58;a4++){
//                            for (a5=48;a5<58;a5++){
//                                ++num;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println(num);
//    }

//    public static void makePwd4(){
//        //setup1
//        int num=0;
//        for(int i=0;i<4;i++){
//            System.out.println("num="+(10*10*10*10));
//        }
//        //setup2
//        byte a0,a1,a2,a3;
//        for(a0=53;a0<58;a0++) {
//            for (a1 = 48; a1 < 58; a1++) {
//                for (a2=48;a2<58;a2++){
//                    for (a3=48;a3<58;a3++){
//                        System.out.println(++num);
//                    }
//                }
//            }
//        }
//    }
}
