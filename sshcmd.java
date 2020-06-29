import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


    private String exec(String host, int port, String username, String password, String cmd)  {
      try {   
        JSch jSch = new JSch();
        Session session = jSch.getSession(username, host, port);
        session.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(cmd);
        ((ChannelExec) channel).setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);
        InputStream inputStream = channel.getInputStream();
        channel.connect();

//        System.out.format("# successful connect to server [%s:%d]\n", host, port);
//        System.out.format("# exec cmd [%s]\n", cmd);

        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int exitStatus;
        while (true) {
            while (inputStream.available() > 0) {
                int i = inputStream.read(bytes, 0, 1024);
                if (i < 0) {
                    break;
                }
                sb.append(new String(bytes, 0, i, StandardCharsets.UTF_8));
            }
            if (channel.isClosed()) {
                if (inputStream.available() > 0) {
                    continue;
                }
                exitStatus = channel.getExitStatus();
                break;
            }
            Thread.sleep(1000);
        }

           if (sb.length() == 0) {
                sb.append("exitStatus=");
                sb.append(exitStatus);
            }

        channel.disconnect();
        session.disconnect();

        return sb.toString();
        } catch (Exception ex) {
           return ex.getMessage();
        }
    }


public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
  if (first) {
    first = false;

    /* TODO: Your code here. (Using info fields)

    FieldHelper infoField = get(Fields.Info, "info_field_name");

    RowSet infoStream = findInfoRowSet("info_stream_tag");

    Object[] infoRow = null;

    int infoRowCount = 0;

    // Read all rows from info step before calling getRow() method, which returns first row from any
    // input rowset. As rowMeta for info and input steps varies getRow() can lead to errors.
    while((infoRow = getRowFrom(infoStream)) != null){

      // do something with info data
      infoRowCount++;
    }
    */
  }

  Object[] r = getRow();

  if (r == null) {
    setOutputDone();
    return false;
  }

  // It is always safest to call createOutputRow() to ensure that your output row's Object[] is large
  // enough to handle any new fields you are creating in this step.
  r = createOutputRow(r, data.outputRowMeta.size());

  /* TODO: Your code here. (See Sample)

  // Get the value from an input field
  String foobar = get(Fields.In, "a_fieldname").getString(r);

  foobar += "bar";
    
  // Set a value in a new output field
  get(Fields.Out, "output_fieldname").setValue(r, foobar);

  */
  // Send the row on to the next step.
String host = get(Fields.In, "host").getString(r);
String user = get(Fields.In, "user").getString(r);
String pass = get(Fields.In, "pass").getString(r);
String port = get(Fields.In, "port").getString(r);
String command = get(Fields.In, "command").getString(r);
int intport = Integer.parseInt(port);

String res = exec(host, intport, user, pass, command);

get(Fields.Out, "result").setValue(r, res);

  putRow(data.outputRowMeta, r);

  return true;
}
