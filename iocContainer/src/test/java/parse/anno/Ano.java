package parse.anno;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myd
 * @date 2021/7/30  12:22
 */

public class Ano {







    @Test
    public void testAyy(){

        List<String> s = new ArrayList<>();
        List<String> ss  =new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            s.add(i+"");
            ss.add((i+10)+"");
        }
        hb(s,ss);
        System.out.println(s.size());

    }


    public void hb(List<String> s1,List<String>s2){
        List<String> s = new ArrayList<>();
        s.addAll(s1);
        s.addAll(s2);

//        s1.addAll(s2);
    }

}
