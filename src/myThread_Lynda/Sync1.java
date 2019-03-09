package myThread_Lynda;

class CallMe {

	public CallMe() {
	}

	public void call(String msg) {
		System.out.print("[" + msg);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
		System.out.println("]");
	}

}

class Caller implements Runnable {

	private CallMe target;
	private String msg;

	public Caller(CallMe target, String msg) {
		super();
		this.target = target;
		this.msg = msg;
	}

	public Caller() {
	}

	@Override
	public void run() {
		synchronized (target) {
			target.call(msg);
		}
	}

}

public class Sync1 {
	public static void main(String[] args) {
		CallMe target = new CallMe();
		Thread t1 = new Thread(new Caller(target, "caller1"), "thread1");
		Thread t2 = new Thread(new Caller(target, "caller2"), "thread2");
		Thread t3 = new Thread(new Caller(target, "caller3"), "thread3");

		t1.start();
		t2.start();
		t3.start();
	}
}
