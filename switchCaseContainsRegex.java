
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class switchCaseContainsRegex {

    public static void main(String[] args) {

//        final Matcher m = Pattern.compile("aa|bb|c d").matcher("tgtgtc 1daobbbe");
        final Pattern p = Pattern.compile("aa|bb|c d|c e");

        String someString = "tgtgabrbatc e1dac dob baaa be";

        Matcher m = p.matcher(someString);

        if (m.find()) {
            switch (m.group()) {
                case "aa":
                    System.out.println("aaa");
                    break;
                case "bb":
                    System.out.println("bbb");
                    break;
                case "c e":
                case "c d":
                    System.out.println("c d || c e");
                    break;
            }
        } else {
            System.out.println("default");
        }
    }

}
