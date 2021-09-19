import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class JobSelection {

    private static Map< String ,List<String> > family = new HashMap< String , List<String>>();

    public static void setFamily(Map<String, List<String>> family) {
        JobSelection.family = family;
    }

    public static boolean isParent(String parent, String child){

        for(int i = 0; i < family.get(parent).size(); i++){

            if(family.containsKey(family.get(parent).get(i)) && family.get(family.get(parent).get(i)).contains(child))return false;

        }
        return true;
    }



    public static void recursion(String name,List<String> parent,List<String> children,int i){



        String s = "";
        for(int j = 0;j < i;j++){
            s = s + "       ";
        }
        s = s + name;
        System.out.println(s);


        List<String> parent1 = new ArrayList<String>();
        parent1.addAll(parent);

        boolean uvj = true;

        for(int j = 0;j < parent.size(); j++){





            if(family.get(name).contains(parent.get(j))){
                uvj = true;

                //ispituje da li je parentu neko roditelj osim namea??
                for(String key: family.keySet()){

                    if(!key.equals(name) && family.get(key).contains(parent.get(j)))
                             uvj = isParent(name,parent.get(j));
                }

                if(uvj){

                    parent1.remove(name);
                    recursion(parent.get(j),parent1,children,i+1);
                }


            }

        }

        for(int j = 0; j < family.get(name).size(); j++)
        {

            if(!parent.contains(family.get(name).get(j)))
                {
                    s = "";
                    for(int k = 0;k < i+1;k++){
                        s = s + "       ";
                    }
                    s = s + family.get(name).get(j);
                    System.out.println(s);
            }

        }




    }


    public static void cyclicrelationship(String name,List<String> children,List<String> passed) throws CyclicRelationshipException {




        if(passed.contains(name))throw new CyclicRelationshipException();

        passed.add(name);

        for(int i = 0; i < children.size(); i++){
            if(family.containsKey(children.get(i)))
                cyclicrelationship(children.get(i),family.get(children.get(i)),passed);
        }



    }





    public static void main(String[] args) throws CyclicRelationshipException {

        System.out.print("File path: ");


        Scanner sc = new Scanner(System.in);

        String str = sc.nextLine();
        File file = new File(str);

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        List<String> list = new ArrayList<String>();

        Set<String> children = new HashSet<>();
        Set<String> parent = new HashSet<>();


        String s = "";


        while(sc.hasNextLine()){
            s = sc.nextLine();
            if(s.isEmpty())break;

            String  s1[] = s.split("\\ ");

            if(!family.containsKey(s1[1])){
                List<String> pom = new ArrayList<String>();
                pom.add(s1[0]);
                family.put(s1[1],pom);
            }
            else family.get(s1[1]).add(s1[0]);

            children.add(s1[0]);
            parent.add(s1[1]);

        }


        sc.close();



        List<String> parent1 = new ArrayList<>();
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();

        l1.addAll(parent);
        l2.addAll(children);
        parent1.addAll(parent);
        parent1.removeAll(children);



        for(String key: family.keySet())
            {
                cyclicrelationship(key,family.get(key),new ArrayList<>());
        }


        for(int i = 0; i < parent1.size(); i++)
            {
            System.out.println();

            recursion(parent1.get(i),l1,l2,0);
        }





    }

}
