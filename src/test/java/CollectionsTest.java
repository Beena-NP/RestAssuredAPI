import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CollectionsTest {

    //ArrayList
    //Contains duplicates, maintains insertion order(to retrieve the data u may require order)
    @Test
    public static void arrayListTest() {

        List<String> newCompanies = new ArrayList<String>();
        //ArrayList<String> top = new ArrayList<String>();

        System.out.println(newCompanies.isEmpty());
        newCompanies.add("Google");
        newCompanies.add("Amazon");
        newCompanies.add("Apple");
        newCompanies.add("Microsoft");
        System.out.println(newCompanies.isEmpty());

        for (int i = 0; i < newCompanies.size(); i++) {
            System.out.println(newCompanies.get(i));

        }

        Iterator<String> companiesIterator = newCompanies.iterator();
        while (companiesIterator.hasNext()) {
            System.out.println(companiesIterator.next());
        }

    }
        @Test
        public static void linkedListTest()
        {

            List<String> humanSpecies = new LinkedList<String>();
            humanSpecies.add("Home Sapiens");
            humanSpecies.add("Homo Erectus");
            humanSpecies.add("homo Neanderthalensis");
            humanSpecies.add("Homo Habilis");

            Iterator<String> iterator = humanSpecies.listIterator();
            while(iterator.hasNext())
            {
                System.out.println(iterator.next());

            }



        }


    }



