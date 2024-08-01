package txu.shop.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TXUtil {
    public static List<String> leftList(List<String> l1, List<String> l2){
        List<String> result = new ArrayList<>();
        for (String s: l1){
            for (String ss: l2){
                if(Objects.equals(s, ss)){
                    break;
                }
            }
            result.add(s);
        }
        return  result;
    }
}
