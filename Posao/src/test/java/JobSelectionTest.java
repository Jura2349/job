import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JobSelectionTest {

    @Test
    void isParent_true() {
        JobSelection js = new JobSelection();
        Map< String , List<String>> family = new HashMap< String , List<String>>();
        family.put("Adam", Arrays.asList("Stjepan"));
        family.put("Ivan", Arrays.asList("Adam","Fran"));
        family.put("Luka", Arrays.asList("Leopold"));
        family.put("Stjepan", Arrays.asList("Marko","Robert"));

        js.setFamily(family);

        Assertions.assertTrue(js.isParent("Adam","Stjepan"));

    }

    @Test
    void isParent_false() {
        JobSelection js = new JobSelection();
        Map< String , List<String>> family = new HashMap< String , List<String>>();
        family.put("Adam", Arrays.asList("Stjepan","Jura"));
        family.put("Jura",Arrays.asList("Stjepan"));
        family.put("Ivan", Arrays.asList("Adam","Fran"));
        family.put("Luka", Arrays.asList("Leopold"));
        family.put("Stjepan", Arrays.asList("Marko","Robert"));

        js.setFamily(family);

        Assertions.assertFalse(js.isParent("Adam","Stjepan"));

    }



    @Test
    void cyclicrelationship_exceptionCase(){

        JobSelection js = new JobSelection();


        String name = "Ivan";

        Map< String , List<String>> family = new HashMap< String , List<String>>();
        List<String> list = new ArrayList<>();
        list.add("Jura");
        family.put("Ivan",list);
        list.removeAll(list);
        list.add("Ivan");
        family.put("Jura",list);

        js.setFamily(family);



        Assertions.assertThrows(CyclicRelationshipException.class,()->js.cyclicrelationship(name,family.get(name),new ArrayList<>()));

    }




}