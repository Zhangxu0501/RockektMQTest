import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;  
  

  
public class TestConsumer {
    public static void main(String[] args){
        System.out.println("Consumer start");
        DefaultMQPushConsumer consumer =   
                new DefaultMQPushConsumer("PushConsumer");  
        consumer.setNamesrvAddr("127.0.0.1:9876");   
        try {  
            //订阅PushTopic下Tag为push的消息  
            consumer.subscribe("PushTopic", "push");
            //程序第一次启动从消息队列头取数据  
            consumer.setConsumeFromWhere(  
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            //订阅信息源，传递了一个MessageListenerConcurrently对象，重写public ConsumeConcurrentlyStatus consumeMessage方法
            //在该方法内部写好处理消息的逻辑
            consumer.registerMessageListener(
                    new MessageListenerConcurrently() {
                        public ConsumeConcurrentlyStatus consumeMessage(
                                List<MessageExt> list,
                                ConsumeConcurrentlyContext Context) {
                            Message msg = list.get(0);
                            System.out.println(new String(msg.getBody()));
                            System.out.println(msg.toString());
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    }
            );
            consumer.start();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  