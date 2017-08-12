import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class TestProducer
{
	public static void main(String args[])
	{
		DefaultMQProducer producer = new DefaultMQProducer("Producer");
		System.out.println("new producer");
		producer.setNamesrvAddr("127.0.0.1:9876");
		try
		{
			producer.start();
			Message msg = new Message("PushTopic", "push", "1234", "Just for test.".getBytes());
			System.out.println(msg.getTopic());
			System.out.println(msg.getTags());
			System.out.println(msg.getKeys());
//			System.out.println(msg.getTopic());
//			System.out.println(msg.getTopic());
			SendResult result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());

			msg = new Message("PushTopic", "push", "2", "Just for test.".getBytes());
			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());

			msg = new Message("PullTopic", "pull", "1", "Just for test.".getBytes());
			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			producer.shutdown();
		}
	}
}
