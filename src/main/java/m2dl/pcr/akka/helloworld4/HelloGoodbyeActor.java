package m2dl.pcr.akka.helloworld4;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Procedure;

public class HelloGoodbyeActor extends UntypedActor {

  private ActorRef childRef;

  public HelloGoodbyeActor() {
    childRef = getContext().actorOf(Props.create(ChildActor.class), "child-actor");
  }

  Procedure<Object> hello = new Procedure<Object>() {
    public void apply(Object msg) throws Exception {
      if (msg instanceof String) {
        childRef.tell("Hello "+msg, getSelf());
        getContext().become(goodbye, false);
      } else {
        unhandled(msg);
      }
    }
  };

  Procedure<Object> goodbye = new Procedure<Object>() {
    public void apply(Object msg) throws Exception {
      if (msg instanceof String) {
        childRef.tell("Goodbye "+msg, getSelf());
        getContext().unbecome();
      } else {
        unhandled(msg);
      }
    }
  };

  @Override
  public void onReceive(Object msg) throws Exception {
    hello.apply(msg);
  }

}
