package csj.thoughtful.datatree.myarray;

public class ClassDataApp{
        public static void main(String[] args) {
            int maxSize = 100;
            ClassDataArray arr;
            arr=new ClassDataArray(maxSize);

            arr.inset("Evans", "patty",24);
            arr.inset("Smith", "Lorraine",37);
            arr.inset("Yee", "Tom",43);
            arr.inset("Hashimoto","Sato",21);
            arr.inset("Stimson","Henry",29);
            arr.inset("Vang","Jose",72);
            arr.inset("Lamarque","Henry",54);

            arr.displayA();

            String searchKey="Stimson";
            Person found;
            found=arr.find(searchKey);
            if(found!=null){
                System.out.println("Found");
                found.displayPerson();
            }else
                System.out.println("Deleting Smith, Yee,and Creswell");

            arr.displayA();


        }
    }