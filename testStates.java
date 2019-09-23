
import java.io.IOException;
import java.util.List;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.CommandAction;
import org.asteriskjava.manager.response.CommandResponse;

public class testStates1 {

    private static List<String> asterconn() throws IllegalStateException, IOException, AuthenticationFailedException, TimeoutException {

        ManagerConnectionFactory factory = new ManagerConnectionFactory("192.168.10.3", "manager", "pa55");
        ManagerConnection c = factory.createManagerConnection();

        c.login();

        CommandAction action;
        CommandResponse response;
//       List<String> list = new ArrayList<>();

        action = new CommandAction();
        //action.setCommand("core show help");
        action.setCommand("sip show statuses");
        response = (CommandResponse) c.sendAction(action);

//        List<String> list = response.getResult();
//        //System.out.println(list.size());
//        String n = "";
//        int i = 0;
//        while (i < list.size()) {
//
//            n += (list.get(i) + "\n");
//            i++;
//        }
        List<String> n = response.getResult();

        return n;
    }

    public static void main(String[] args) throws IllegalStateException, IOException,
            AuthenticationFailedException, TimeoutException {
        asterconn().forEach((item) -> {
            System.out.println(item);
        });
    }
}
