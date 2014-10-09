package eden.sun.childrenguard.comet;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

public class RegisterListener implements ClientSessionChannel.MessageListener
{
    public void onMessage(ClientSessionChannel channel, Message message)
    {
        // Here you received a message on the channel
    	System.out.println("received a message");
    	System.out.println(channel);
    	System.out.println(message);
    }
}