package sshAgent;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.*;

public class SSHConnection {

    public static void main(String[] args) {
        String sshPassword = System.getenv("SERVER_PASSWORD");
        String sshHost = System.getenv("SERVER_ADDRESS");
        String sshUsername = System.getenv("SERVER_USERNAME");
        int sshPort = Integer.parseInt(System.getenv("SERVER_PORT"));

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sshUsername, sshHost, sshPort);
            session.setPassword(sshPassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand("echo 'Hello from the remote server!'");
            channelExec.connect();

            InputStream in = channelExec.getInputStream();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channelExec.isClosed()) {
                    if (in.available() > 0) continue;
                    System.out.println("Exit status: " + channelExec.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            channelExec.disconnect();
            session.disconnect();

        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
    }
}
